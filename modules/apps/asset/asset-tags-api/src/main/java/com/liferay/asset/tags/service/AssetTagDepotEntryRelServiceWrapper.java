/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.service;

import com.liferay.asset.tags.model.AssetTagDepotEntryRel;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetTagDepotEntryRelService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagDepotEntryRelService
 * @generated
 */
public class AssetTagDepotEntryRelServiceWrapper
	implements AssetTagDepotEntryRelService,
			   ServiceWrapper<AssetTagDepotEntryRelService> {

	public AssetTagDepotEntryRelServiceWrapper() {
		this(null);
	}

	public AssetTagDepotEntryRelServiceWrapper(
		AssetTagDepotEntryRelService assetTagDepotEntryRelService) {

		_assetTagDepotEntryRelService = assetTagDepotEntryRelService;
	}

	@Override
	public AssetTagDepotEntryRel addAssetTagDepotEntryRel(
			long assetTagId, long depotEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetTagDepotEntryRelService.addAssetTagDepotEntryRel(
			assetTagId, depotEntryId);
	}

	@Override
	public java.util.List<AssetTagDepotEntryRel>
			getAssetTagDepotEntryRelsByAssetTagId(long assetTagId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetTagDepotEntryRelService.
			getAssetTagDepotEntryRelsByAssetTagId(assetTagId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetTagDepotEntryRelService.getOSGiServiceIdentifier();
	}

	@Override
	public void setAssetTagDepotEntryRels(long assetTagId, long[] depotEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetTagDepotEntryRelService.setAssetTagDepotEntryRels(
			assetTagId, depotEntryIds);
	}

	@Override
	public AssetTagDepotEntryRelService getWrappedService() {
		return _assetTagDepotEntryRelService;
	}

	@Override
	public void setWrappedService(
		AssetTagDepotEntryRelService assetTagDepotEntryRelService) {

		_assetTagDepotEntryRelService = assetTagDepotEntryRelService;
	}

	private AssetTagDepotEntryRelService _assetTagDepotEntryRelService;

}