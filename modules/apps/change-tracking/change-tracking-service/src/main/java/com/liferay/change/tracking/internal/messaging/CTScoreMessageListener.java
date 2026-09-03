/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.messaging;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.constants.CTDestinationNames;
import com.liferay.change.tracking.constants.PublicationRoleConstants;
import com.liferay.change.tracking.internal.notification.CTUserNotificationSender;
import com.liferay.change.tracking.internal.score.CTCollectionScoreCache;
import com.liferay.change.tracking.internal.score.CTScoreCalculator;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(
	property = "destination.name=" + CTDestinationNames.CT_SCORE,
	service = MessageListener.class
)
public class CTScoreMessageListener extends BaseMessageListener {

	@Activate
	protected void activate(BundleContext bundleContext) {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_SERIAL,
				CTDestinationNames.CT_SCORE);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		_serviceRegistration = bundleContext.registerService(
			Destination.class, destination,
			MapUtil.singletonDictionary(
				"destination.name", destination.getName()));
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		long ctCollectionId = message.getLong("ctCollectionId");

		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctCollectionId);

		if ((ctCollection == null) || ctCollection.isReadOnly()) {
			return;
		}

		String originalSizeClassification =
			ctCollection.getScoreSizeClassification();

		if (Objects.equals(
				CTConstants.SCORE_SIZE_CLASSIFICATION_LARGE,
				originalSizeClassification)) {

			return;
		}

		int score = _ctScoreCalculator.calculate(
			message.getLong("modelClassNameId"));

		if (message.getBoolean("increment")) {
			_ctCollectionScoreCache.increment(ctCollectionId, score);
		}
		else {
			_ctCollectionScoreCache.decrement(ctCollectionId, score);
		}

		String sizeClassification =
			_ctCollectionScoreCache.getSizeClassification(
				_ctCollectionScoreCache.getScore(ctCollectionId));

		if (Objects.equals(originalSizeClassification, sizeClassification)) {
			return;
		}

		ctCollection = _ctCollectionLocalService.updateScoreSizeClassification(
			ctCollectionId, sizeClassification);

		_sendUserNotificationEvents(
			ctCollection, originalSizeClassification, sizeClassification);
	}

	private void _sendUserNotificationEvents(
		CTCollection ctCollection, String originalSizeClassification,
		String sizeClassification) {

		try {
			_ctUserNotificationSender.sendUserNotificationEvents(
				ctCollection,
				JSONUtil.put(
					"ctCollectionId", ctCollection.getCtCollectionId()
				).put(
					"notificationType",
					UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY
				).put(
					"originalSizeClassification", originalSizeClassification
				).put(
					"sizeClassification", sizeClassification
				),
				_ctUserNotificationSender.getPublicationRoleUserIds(
					ctCollection, true, PublicationRoleConstants.NAME_ADMIN,
					PublicationRoleConstants.NAME_PUBLISHER));
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to send user notification events", portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTScoreMessageListener.class);

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTCollectionScoreCache _ctCollectionScoreCache;

	@Reference
	private CTScoreCalculator _ctScoreCalculator;

	@Reference
	private CTUserNotificationSender _ctUserNotificationSender;

	@Reference
	private DestinationFactory _destinationFactory;

	private ServiceRegistration<Destination> _serviceRegistration;

}