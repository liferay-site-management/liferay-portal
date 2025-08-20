/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service.impl;

import com.liferay.change.tracking.constants.PublicationRoleConstants;
import com.liferay.change.tracking.internal.helper.CTUserNotificationHelper;
import com.liferay.change.tracking.internal.score.CTScoreHelper;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTScore;
import com.liferay.change.tracking.model.impl.CTScoreImpl;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.base.CTScoreLocalServiceBaseImpl;
import com.liferay.change.tracking.service.persistence.CTCollectionPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.LockMode;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.increment.BufferedIncrement;
import com.liferay.portal.kernel.increment.NumberIncrement;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.service.SQLStateAcceptor;
import com.liferay.portal.kernel.spring.aop.Property;
import com.liferay.portal.kernel.spring.aop.Retry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(
	property = "model.class.name=com.liferay.change.tracking.model.CTScore",
	service = AopService.class
)
@CTAware(onProduction = true)
public class CTScoreLocalServiceImpl extends CTScoreLocalServiceBaseImpl {

	@Override
	public CTScore addCTScore(long ctCollectionId) {
		CTCollection ctCollection = _ctCollectionPersistence.fetchByPrimaryKey(
			ctCollectionId);

		if (ctCollection == null) {
			return null;
		}

		List<CTEntry> ctEntries = _ctEntryLocalService.getCTCollectionCTEntries(
			ctCollectionId);

		int score = 0;

		for (CTEntry ctEntry : ctEntries) {
			score += _ctScoreHelper.calculate(ctEntry.getModelClassNameId());
		}

		long ctScoreId = counterLocalService.increment(CTScore.class.getName());

		CTScore ctScore = ctScorePersistence.create(ctScoreId);

		ctScore.setCompanyId(ctCollection.getCompanyId());
		ctScore.setCtCollectionId(ctCollectionId);
		ctScore.setScore(score);

		return ctScorePersistence.update(ctScore);
	}

	@BufferedIncrement(incrementClass = NumberIncrement.class)
	@Override
	@Retry(
		acceptor = SQLStateAcceptor.class,
		properties = {
			@Property(
				name = SQLStateAcceptor.SQLSTATE,
				value = SQLStateAcceptor.SQLSTATE_INTEGRITY_CONSTRAINT_VIOLATION + "," + SQLStateAcceptor.SQLSTATE_TRANSACTION_ROLLBACK
			)
		}
	)
	public CTScore decrementScore(long ctCollectionId, int score) {
		return _updateScore(ctCollectionId, -1 * score);
	}

	@Override
	public CTScore fetchCTScoreByCTCollectionId(long ctCollectionId) {
		return ctScorePersistence.fetchByCtCollectionId(ctCollectionId);
	}

	@BufferedIncrement(incrementClass = NumberIncrement.class)
	@Override
	@Retry(
		acceptor = SQLStateAcceptor.class,
		properties = {
			@Property(
				name = SQLStateAcceptor.SQLSTATE,
				value = SQLStateAcceptor.SQLSTATE_INTEGRITY_CONSTRAINT_VIOLATION + "," + SQLStateAcceptor.SQLSTATE_TRANSACTION_ROLLBACK
			)
		}
	)
	public CTScore incrementScore(long ctCollectionId, int score) {
		return _updateScore(ctCollectionId, score);
	}

	private void _sendUserNotificationEvents(
		CTScore ctScore, CTScore originalCTScore) {

		String originalSizeClassification =
			originalCTScore.getSizeClassification();
		String sizeClassification = ctScore.getSizeClassification();

		if (Objects.equals(originalSizeClassification, sizeClassification)) {
			return;
		}

		long ctCollectionId = ctScore.getCtCollectionId();

		try {
			CTCollection ctCollection =
				_ctCollectionPersistence.findByPrimaryKey(ctCollectionId);

			Set<Long> userIds = SetUtil.fromArray(
				_ctUserNotificationHelper.getPublicationRoleUserIds(
					ctCollection, true, PublicationRoleConstants.NAME_ADMIN,
					PublicationRoleConstants.NAME_PUBLISHER));

			_ctUserNotificationHelper.sendUserNotificationEvents(
				ctCollection,
				JSONUtil.put(
					"ctCollectionId", ctCollectionId
				).put(
					"notificationType",
					UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY
				).put(
					"originalSizeClassification", originalSizeClassification
				).put(
					"sizeClassification", sizeClassification
				),
				ArrayUtil.toLongArray(userIds));
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to send user notification events", portalException);
		}
	}

	private CTScore _updateScore(long ctCollectionId, int score) {
		CTCollection ctCollection = _ctCollectionPersistence.fetchByPrimaryKey(
			ctCollectionId);

		if (ctCollection == null) {
			return null;
		}

		CTScore originalCTScore = ctScorePersistence.fetchByCtCollectionId(
			ctCollectionId);

		if (ctCollection.isReadOnly()) {
			return originalCTScore;
		}

		if (originalCTScore == null) {
			return addCTScore(ctCollectionId);
		}

		Session session = ctScorePersistence.openSession();

		CTScore ctScore = null;

		try {
			ctScore = (CTScore)session.get(
				CTScoreImpl.class, originalCTScore.getCtScoreId(),
				LockMode.UPGRADE);

			if (ctScore == null) {
				return ctScore;
			}

			score = ctScore.getScore() + score;

			if (score < 0) {
				score = 0;
			}

			ctScore.setScore(score);

			ctScore = (CTScore)session.merge(ctScore);
		}
		finally {
			ctScorePersistence.closeSession(session);
		}

		_entityCache.putResult(CTScoreImpl.class, ctScore, false, true);

		ctScore.resetOriginalValues();

		_sendUserNotificationEvents(ctScore, originalCTScore);

		return ctScore;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTScoreLocalServiceImpl.class);

	@Reference
	private CTCollectionPersistence _ctCollectionPersistence;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private CTScoreHelper _ctScoreHelper;

	@Reference
	private CTUserNotificationHelper _ctUserNotificationHelper;

	@Reference
	private EntityCache _entityCache;

}