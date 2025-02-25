/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.service.persistence.impl;

import com.liferay.asset.tags.model.AssetTagDepotEntryRelTable;
import com.liferay.asset.tags.model.impl.AssetTagDepotEntryRelImpl;
import com.liferay.asset.tags.model.impl.AssetTagDepotEntryRelModelImpl;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from AssetTagDepotEntryRel.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	property = {
		"class.name=com.liferay.asset.tags.model.impl.AssetTagDepotEntryRelImpl",
		"table.name=AssetTagDepotEntryRel"
	},
	service = ArgumentsResolver.class
)
public class AssetTagDepotEntryRelModelArgumentsResolver
	implements ArgumentsResolver {

	@Override
	public Object[] getArguments(
		FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
		boolean original) {

		String[] columnNames = finderPath.getColumnNames();

		if ((columnNames == null) || (columnNames.length == 0)) {
			if (baseModel.isNew()) {
				return new Object[0];
			}

			return null;
		}

		AssetTagDepotEntryRelModelImpl assetTagDepotEntryRelModelImpl =
			(AssetTagDepotEntryRelModelImpl)baseModel;

		long columnBitmask = assetTagDepotEntryRelModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(
				assetTagDepotEntryRelModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					assetTagDepotEntryRelModelImpl.getColumnBitmask(columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(
				assetTagDepotEntryRelModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return AssetTagDepotEntryRelImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return AssetTagDepotEntryRelTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		AssetTagDepotEntryRelModelImpl assetTagDepotEntryRelModelImpl,
		String[] columnNames, boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] =
					assetTagDepotEntryRelModelImpl.getColumnOriginalValue(
						columnName);
			}
			else {
				arguments[i] = assetTagDepotEntryRelModelImpl.getColumnValue(
					columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}