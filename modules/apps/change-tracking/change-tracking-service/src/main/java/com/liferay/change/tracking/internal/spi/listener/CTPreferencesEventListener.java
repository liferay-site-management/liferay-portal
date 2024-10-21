/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.spi.listener;

import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.spi.listener.CTEventListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = CTEventListener.class)
public class CTPreferencesEventListener implements CTEventListener {

	@Override
	public void onAfterPublish(long ctCollectionId) {
		_ctPreferencesLocalService.resetCTPreferences(ctCollectionId);
	}

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

}