/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;AssetTagDepotEntryRel&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagDepotEntryRel
 * @generated
 */
public class AssetTagDepotEntryRelTable
	extends BaseTable<AssetTagDepotEntryRelTable> {

	public static final AssetTagDepotEntryRelTable INSTANCE =
		new AssetTagDepotEntryRelTable();

	public final Column<AssetTagDepotEntryRelTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AssetTagDepotEntryRelTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AssetTagDepotEntryRelTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AssetTagDepotEntryRelTable, Long>
		assetTagDepotEntryRelId = createColumn(
			"assetTagDepotEntryRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<AssetTagDepotEntryRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetTagDepotEntryRelTable, Long> assetTagId =
		createColumn(
			"assetTagId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AssetTagDepotEntryRelTable, Long> depotEntryId =
		createColumn(
			"depotEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private AssetTagDepotEntryRelTable() {
		super("AssetTagDepotEntryRel", AssetTagDepotEntryRelTable::new);
	}

}