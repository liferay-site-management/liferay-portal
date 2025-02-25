/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.service;

import com.liferay.asset.tags.model.AssetTagDepotEntryRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.List;

/**
 * Provides the remote service utility for AssetTagDepotEntryRel. This utility wraps
 * <code>com.liferay.asset.tags.service.impl.AssetTagDepotEntryRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagDepotEntryRelService
 * @generated
 */
public class AssetTagDepotEntryRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.asset.tags.service.impl.AssetTagDepotEntryRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static AssetTagDepotEntryRel addAssetTagDepotEntryRel(
			long assetTagId, long depotEntryId)
		throws PortalException {

		return getService().addAssetTagDepotEntryRel(assetTagId, depotEntryId);
	}

	public static List<AssetTagDepotEntryRel>
			getAssetTagDepotEntryRelsByAssetTagId(long assetTagId)
		throws PortalException {

		return getService().getAssetTagDepotEntryRelsByAssetTagId(assetTagId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void setAssetTagDepotEntryRels(
			long assetTagId, long[] depotEntryIds)
		throws PortalException {

		getService().setAssetTagDepotEntryRels(assetTagId, depotEntryIds);
	}

	public static AssetTagDepotEntryRelService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<AssetTagDepotEntryRelService>
		_serviceSnapshot = new Snapshot<>(
			AssetTagDepotEntryRelServiceUtil.class,
			AssetTagDepotEntryRelService.class);

}