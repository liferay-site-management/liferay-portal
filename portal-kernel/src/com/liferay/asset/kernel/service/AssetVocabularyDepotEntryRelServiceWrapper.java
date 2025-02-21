/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetVocabularyDepotEntryRelService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyDepotEntryRelService
 * @generated
 */
public class AssetVocabularyDepotEntryRelServiceWrapper
	implements AssetVocabularyDepotEntryRelService,
			   ServiceWrapper<AssetVocabularyDepotEntryRelService> {

	public AssetVocabularyDepotEntryRelServiceWrapper() {
		this(null);
	}

	public AssetVocabularyDepotEntryRelServiceWrapper(
		AssetVocabularyDepotEntryRelService
			assetVocabularyDepotEntryRelService) {

		_assetVocabularyDepotEntryRelService =
			assetVocabularyDepotEntryRelService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetVocabularyDepotEntryRelService.getOSGiServiceIdentifier();
	}

	@Override
	public AssetVocabularyDepotEntryRelService getWrappedService() {
		return _assetVocabularyDepotEntryRelService;
	}

	@Override
	public void setWrappedService(
		AssetVocabularyDepotEntryRelService
			assetVocabularyDepotEntryRelService) {

		_assetVocabularyDepotEntryRelService =
			assetVocabularyDepotEntryRelService;
	}

	private AssetVocabularyDepotEntryRelService
		_assetVocabularyDepotEntryRelService;

}