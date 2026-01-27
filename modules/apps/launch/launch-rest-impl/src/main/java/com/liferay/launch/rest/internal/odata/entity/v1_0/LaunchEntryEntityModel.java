/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.rest.internal.odata.entity.v1_0;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IdEntityField;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.Map;

/**
 * @author David Truong
 */
public class LaunchEntryEntityModel implements EntityModel {

	public LaunchEntryEntityModel() {
		_entityFieldsMap = EntityModel.toEntityFieldsMap(
			new DateTimeEntityField(
				"dateCreated",
				locale -> Field.getSortableFieldName(Field.CREATE_DATE),
				locale -> Field.CREATE_DATE),
			new DateTimeEntityField(
				"dateModified",
				locale -> Field.getSortableFieldName(Field.MODIFIED_DATE),
				locale -> Field.MODIFIED_DATE),
			new EntityField(
				"status", EntityField.Type.INTEGER,
				locale -> Field.getSortableFieldName(
					"statusLabel_".concat(LocaleUtil.toLanguageId(locale))),
				locale -> "status", String::valueOf),
			new IdEntityField(
				"classNameId", locale -> Field.ENTRY_CLASS_NAME,
				String::valueOf),
			new IdEntityField(
				"ownerId", locale -> Field.USER_ID, String::valueOf),
			new StringEntityField("ownerName", locale -> Field.USER_NAME),
			new StringEntityField(
				"title",
				locale -> Field.getSortableFieldName(
					"title_".concat(LocaleUtil.toLanguageId(locale))),
				locale -> Field.TITLE));
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	private final Map<String, EntityField> _entityFieldsMap;

}