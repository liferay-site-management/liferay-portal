/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.messaging;

import com.liferay.change.tracking.constants.CTDestinationNames;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(
	property = "destination.name=" + CTDestinationNames.CT_COLLECTION_SCORE,
	service = MessageListener.class
)
public class CTCollectionScoreMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		long ctCollectionId = message.getLong("ctCollectionId");
		long modelClassNameId = message.getLong("modelClassNameId");
		boolean increment = message.getBoolean("increment");

		_ctCollectionLocalService.updateScore(
			ctCollectionId, modelClassNameId, increment);
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

}