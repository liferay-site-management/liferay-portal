/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.internal.change.tracking.spi.reference;

import com.liferay.asset.kernel.model.AssetVocabularyDepotEntryRelTable;
import com.liferay.asset.kernel.model.AssetVocabularyTable;
import com.liferay.asset.kernel.service.persistence.AssetVocabularyDepotEntryRelPersistence;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.depot.model.DepotEntryTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = TableReferenceDefinition.class)
public class AssetVocabularyDepotEntryRelTableReferenceDefinition
	implements TableReferenceDefinition<AssetVocabularyDepotEntryRelTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AssetVocabularyDepotEntryRelTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AssetVocabularyDepotEntryRelTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			AssetVocabularyDepotEntryRelTable.INSTANCE.assetVocabularyId,
			AssetVocabularyTable.INSTANCE.vocabularyId
		).singleColumnReference(
			AssetVocabularyDepotEntryRelTable.INSTANCE.depotEntryId,
			DepotEntryTable.INSTANCE.depotEntryId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _assetVocabularyDepotEntryRelPersistence;
	}

	@Override
	public AssetVocabularyDepotEntryRelTable getTable() {
		return AssetVocabularyDepotEntryRelTable.INSTANCE;
	}

	@Reference
	private AssetVocabularyDepotEntryRelPersistence
		_assetVocabularyDepotEntryRelPersistence;

}