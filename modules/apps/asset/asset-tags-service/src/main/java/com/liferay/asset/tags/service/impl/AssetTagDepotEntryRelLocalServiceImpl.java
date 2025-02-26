/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.service.impl;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.tags.exception.InvalidAssetTagDepotEntryRelException;
import com.liferay.asset.tags.model.AssetTagDepotEntryRel;
import com.liferay.asset.tags.service.base.AssetTagDepotEntryRelLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gislayne Vitorino
 */
@Component(
	property = "model.class.name=com.liferay.asset.tags.model.AssetTagDepotEntryRel",
	service = AopService.class
)
public class AssetTagDepotEntryRelLocalServiceImpl
	extends AssetTagDepotEntryRelLocalServiceBaseImpl {

	@Override
	public AssetTagDepotEntryRel addAssetTagDepotEntryRel(
			long assetTagId, long depotEntryId)
		throws PortalException {

		AssetTagDepotEntryRel assetTagDepotEntryRel =
			assetTagDepotEntryRelPersistence.fetchByAVI_DEI(
				assetTagId, depotEntryId);

		if (assetTagDepotEntryRel != null) {
			return assetTagDepotEntryRel;
		}

		assetTagDepotEntryRel = createAssetTagDepotEntryRel(
			counterLocalService.increment());

		assetTagDepotEntryRel.setAssetTagId(assetTagId);
		assetTagDepotEntryRel.setDepotEntryId(depotEntryId);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			assetTagDepotEntryRel.setUuid(serviceContext.getUuid());
		}

		assetTagDepotEntryRel = addAssetTagDepotEntryRel(assetTagDepotEntryRel);

		_reindexAssetTag(assetTagId);

		return assetTagDepotEntryRel;
	}

	public void deleteAssetTagDepotEntryRelsByAssetTagId(long assetTagId) {
		assetTagDepotEntryRelPersistence.removeByAssetTagId(assetTagId);
	}

	public void deleteAssetTagDepotEntryRelsByDepotEntryId(long depotEntryId) {
		assetTagDepotEntryRelPersistence.removeByDepotEntryId(depotEntryId);
	}

	public List<AssetTagDepotEntryRel> getAssetTagDepotEntryRelsByAssetTagId(
		long assetTagId) {

		return assetTagDepotEntryRelPersistence.findByAssetTagId(assetTagId);
	}

	public List<AssetTagDepotEntryRel> getAssetTagDepotEntryRelsByDepotEntryId(
		long depotEntryId) {

		return assetTagDepotEntryRelPersistence.findByDepotEntryId(
			depotEntryId);
	}

	public void setAssetTagDepotEntryRels(long assetTagId, long[] depotEntryIds)
		throws PortalException {

		if (ArrayUtil.isEmpty(depotEntryIds)) {
			throw new InvalidAssetTagDepotEntryRelException(
				"DepotEntry IDs cannot be empty");
		}

		assetTagDepotEntryRelPersistence.removeByAssetTagId(assetTagId);

		for (long depotEntryId : depotEntryIds) {
			addAssetTagDepotEntryRel(assetTagId, depotEntryId);
		}
	}

	private void _reindexAssetTag(long assetTagId) throws PortalException {
		Indexer<AssetTag> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			AssetTag.class);

		indexer.reindex(AssetTag.class.getName(), assetTagId);
	}

}