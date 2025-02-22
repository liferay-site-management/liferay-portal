/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.service.impl;

import com.liferay.asset.model.AssetVocabularyDepotEntryRel;
import com.liferay.asset.service.base.AssetVocabularyDepotEntryRelServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portlet.asset.service.permission.AssetVocabularyPermission;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=asset",
		"json.web.service.context.path=AssetVocabularyDepotEntryRel"
	},
	service = AopService.class
)
public class AssetVocabularyDepotEntryRelServiceImpl
	extends AssetVocabularyDepotEntryRelServiceBaseImpl {

	@Override
	public AssetVocabularyDepotEntryRel addAssetVocabularyDepotEntryRel(
			long assetVocabularyId, long depotEntryId)
		throws PortalException {

		AssetVocabularyPermission.check(
			getPermissionChecker(), assetVocabularyId, ActionKeys.UPDATE);

		return assetVocabularyDepotEntryRelLocalService.
			addAssetVocabularyDepotEntryRel(assetVocabularyId, depotEntryId);
	}

	public List<AssetVocabularyDepotEntryRel>
			getAssetVocabularyDepotEntryRelsByAssetVocabularyId(
				long assetVocabularyId)
		throws PortalException {

		AssetVocabularyPermission.check(
			getPermissionChecker(), assetVocabularyId, ActionKeys.VIEW);

		return assetVocabularyDepotEntryRelLocalService.
			getAssetVocabularyDepotEntryRelsByAssetVocabularyId(
				assetVocabularyId);
	}

	public void setAssetVocabularyDepotEntryRels(
			long assetVocabularyId, long[] depotEntryIds)
		throws PortalException {

		AssetVocabularyPermission.check(
			getPermissionChecker(), assetVocabularyId, ActionKeys.UPDATE);

		assetVocabularyDepotEntryRelLocalService.
			setAssetVocabularyDepotEntryRels(assetVocabularyId, depotEntryIds);
	}

}