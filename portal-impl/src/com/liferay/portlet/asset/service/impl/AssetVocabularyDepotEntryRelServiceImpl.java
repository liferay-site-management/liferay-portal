/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.service.impl;

import com.liferay.asset.kernel.model.AssetVocabularyDepotEntryRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portlet.asset.service.base.AssetVocabularyDepotEntryRelServiceBaseImpl;
import com.liferay.portlet.asset.service.permission.AssetVocabularyPermission;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
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