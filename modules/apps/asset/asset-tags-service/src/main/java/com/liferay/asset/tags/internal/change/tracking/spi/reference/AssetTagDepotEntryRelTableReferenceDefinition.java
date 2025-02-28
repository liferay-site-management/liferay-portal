/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.internal.change.tracking.spi.reference;

import com.liferay.asset.kernel.model.AssetTagTable;
import com.liferay.asset.tags.model.AssetTagDepotEntryRelTable;
import com.liferay.asset.tags.service.persistence.AssetTagDepotEntryRelPersistence;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.depot.model.DepotEntryTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gislayne Vitorino
 */
@Component(service = TableReferenceDefinition.class)
public class AssetTagDepotEntryRelTableReferenceDefinition
	implements TableReferenceDefinition<AssetTagDepotEntryRelTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AssetTagDepotEntryRelTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AssetTagDepotEntryRelTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			AssetTagDepotEntryRelTable.INSTANCE.assetTagId,
			AssetTagTable.INSTANCE.tagId
		).singleColumnReference(
			AssetTagDepotEntryRelTable.INSTANCE.depotEntryId,
			DepotEntryTable.INSTANCE.depotEntryId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _assetTagDepotEntryRelPersistence;
	}

	@Override
	public AssetTagDepotEntryRelTable getTable() {
		return AssetTagDepotEntryRelTable.INSTANCE;
	}

	@Reference
	private AssetTagDepotEntryRelPersistence _assetTagDepotEntryRelPersistence;

}