/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.service.impl;

import com.liferay.asset.kernel.exception.InvalidAssetVocabularyDepotEntryRelException;
import com.liferay.asset.kernel.model.AssetVocabularyDepotEntryRel;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portlet.asset.service.base.AssetVocabularyDepotEntryRelLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Pei-Jung Lan
 */
public class AssetVocabularyDepotEntryRelLocalServiceImpl
	extends AssetVocabularyDepotEntryRelLocalServiceBaseImpl {

	@Override
	public AssetVocabularyDepotEntryRel addAssetVocabularyDepotEntryRel(
			long assetVocabularyId, long depotEntryId)
		throws PortalException {

		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
			assetVocabularyDepotEntryRelPersistence.fetchByAVI_DEI(
				assetVocabularyId, depotEntryId);

		if (assetVocabularyDepotEntryRel != null) {
			return assetVocabularyDepotEntryRel;
		}

		assetVocabularyDepotEntryRel = createAssetVocabularyDepotEntryRel(
			counterLocalService.increment());

		assetVocabularyDepotEntryRel.setAssetVocabularyId(assetVocabularyId);
		assetVocabularyDepotEntryRel.setDepotEntryId(depotEntryId);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			assetVocabularyDepotEntryRel.setUuid(serviceContext.getUuid());
		}

		return addAssetVocabularyDepotEntryRel(assetVocabularyDepotEntryRel);
	}

	public void deleteAssetVocabularyDepotEntryRelsByAssetVocabularyId(
		long assetVocabularyId) {

		assetVocabularyDepotEntryRelPersistence.removeByAssetVocabularyId(
			assetVocabularyId);
	}

	public void deleteAssetVocabularyDepotEntryRelsByDepotEntryId(
		long depotEntryId) {

		assetVocabularyDepotEntryRelPersistence.removeByDepotEntryId(
			depotEntryId);
	}

	public List<AssetVocabularyDepotEntryRel>
		getAssetVocabularyDepotEntryRelsByAssetVocabularyId(
			long assetVocabularyId) {

		return assetVocabularyDepotEntryRelPersistence.findByAssetVocabularyId(
			assetVocabularyId);
	}

	public List<AssetVocabularyDepotEntryRel>
		getAssetVocabularyDepotEntryRelsByDepotEntryId(long depotEntryId) {

		return assetVocabularyDepotEntryRelPersistence.findByDepotEntryId(
			depotEntryId);
	}

	public void setAssetVocabularyDepotEntryRels(
			long assetVocabularyId, long[] depotEntryIds)
		throws PortalException {

		if (ArrayUtil.isEmpty(depotEntryIds)) {
			throw new InvalidAssetVocabularyDepotEntryRelException(
				"DepotEntry IDs cannot be empty");
		}

		assetVocabularyDepotEntryRelPersistence.removeByAssetVocabularyId(
			assetVocabularyId);

		for (long depotEntryId : depotEntryIds) {
			addAssetVocabularyDepotEntryRel(assetVocabularyId, depotEntryId);
		}
	}

	@BeanReference(type = AssetVocabularyLocalService.class)
	private AssetVocabularyLocalService _assetVocabularyLocalService;

}