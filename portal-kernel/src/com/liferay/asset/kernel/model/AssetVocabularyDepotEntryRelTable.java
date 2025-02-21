/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;AssetVocabularyDepotEntryRel&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyDepotEntryRel
 * @generated
 */
public class AssetVocabularyDepotEntryRelTable
	extends BaseTable<AssetVocabularyDepotEntryRelTable> {

	public static final AssetVocabularyDepotEntryRelTable INSTANCE =
		new AssetVocabularyDepotEntryRelTable();

	public final Column<AssetVocabularyDepotEntryRelTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AssetVocabularyDepotEntryRelTable, Long>
		ctCollectionId = createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetVocabularyDepotEntryRelTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetVocabularyDepotEntryRelTable, Long>
		assetVocabularyDepotEntryRelId = createColumn(
			"assetVocabularyDepotEntryRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<AssetVocabularyDepotEntryRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetVocabularyDepotEntryRelTable, Long>
		assetVocabularyId = createColumn(
			"assetVocabularyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetVocabularyDepotEntryRelTable, Long> depotEntryId =
		createColumn(
			"depotEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private AssetVocabularyDepotEntryRelTable() {
		super(
			"AssetVocabularyDepotEntryRel",
			AssetVocabularyDepotEntryRelTable::new);
	}

}