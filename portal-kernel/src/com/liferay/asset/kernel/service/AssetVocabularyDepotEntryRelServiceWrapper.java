/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.service;

import com.liferay.asset.kernel.model.AssetVocabularyDepotEntryRel;
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

	@Override
	public AssetVocabularyDepotEntryRel addAssetVocabularyDepotEntryRel(
			long assetVocabularyId, long depotEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetVocabularyDepotEntryRelService.
			addAssetVocabularyDepotEntryRel(assetVocabularyId, depotEntryId);
	}

	@Override
	public java.util.List<AssetVocabularyDepotEntryRel>
			getAssetVocabularyDepotEntryRelsByAssetVocabularyId(
				long assetVocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetVocabularyDepotEntryRelService.
			getAssetVocabularyDepotEntryRelsByAssetVocabularyId(
				assetVocabularyId);
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
	public void setAssetVocabularyDepotEntryRels(
			long assetVocabularyId, long[] depotEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetVocabularyDepotEntryRelService.setAssetVocabularyDepotEntryRels(
			assetVocabularyId, depotEntryIds);
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