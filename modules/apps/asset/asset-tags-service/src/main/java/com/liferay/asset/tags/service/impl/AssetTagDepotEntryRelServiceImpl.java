/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.service.impl;

import com.liferay.asset.tags.model.AssetTagDepotEntryRel;
import com.liferay.asset.tags.service.base.AssetTagDepotEntryRelServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portlet.asset.service.permission.AssetTagsPermission;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gislayne Vitorino
 */
@Component(
	property = {
		"json.web.service.context.name=assettag",
		"json.web.service.context.path=AssetTagDepotEntryRel"
	},
	service = AopService.class
)
public class AssetTagDepotEntryRelServiceImpl
	extends AssetTagDepotEntryRelServiceBaseImpl {

	@Override
	public AssetTagDepotEntryRel addAssetTagDepotEntryRel(
			long assetTagId, long depotEntryId)
		throws PortalException {

		AssetTagsPermission.check(
			getPermissionChecker(), assetTagId, ActionKeys.UPDATE);

		return assetTagDepotEntryRelLocalService.addAssetTagDepotEntryRel(
			assetTagId, depotEntryId);
	}

	public List<AssetTagDepotEntryRel> getAssetTagDepotEntryRelsByAssetTagId(
			long assetTagId)
		throws PortalException {

		AssetTagsPermission.check(
			getPermissionChecker(), assetTagId, ActionKeys.VIEW);

		return assetTagDepotEntryRelLocalService.
			getAssetTagDepotEntryRelsByAssetTagId(assetTagId);
	}

	public void setAssetTagDepotEntryRels(long assetTagId, long[] depotEntryIds)
		throws PortalException {

		AssetTagsPermission.check(
			getPermissionChecker(), assetTagId, ActionKeys.UPDATE);

		assetTagDepotEntryRelLocalService.setAssetTagDepotEntryRels(
			assetTagId, depotEntryIds);
	}

}