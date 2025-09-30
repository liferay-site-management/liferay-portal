/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.change.tracking.spi.listener;

import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.spi.listener.CTEventListener;
import com.liferay.dynamic.data.mapping.model.DDMField;
import com.liferay.dynamic.data.mapping.model.DDMFieldAttribute;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalService;
import com.liferay.portal.kernel.util.Portal;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.htmlparser.jericho.Attributes;
import net.htmlparser.jericho.OutputDocument;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brooke Dalton
 */
@Component(service = CTEventListener.class)
public class DDMFieldAttributeCTEventListener implements CTEventListener {

	@Override
	public void onAfterPublish(long ctCollectionId) {
		List<CTEntry> ctEntries = _ctEntryLocalService.getCTEntries(
			ctCollectionId, _portal.getClassNameId(DDMField.class.getName()));

		if (ctEntries.isEmpty()) {
			return;
		}

		Set<DDMFieldAttribute> ddmFieldAttributes = new HashSet<>();

		for (CTEntry ctEntry : ctEntries) {
			DDMField ddmField = _ddmFieldLocalService.fetchDDMField(
				ctEntry.getModelClassPK());

			if (ddmField != null) {
				ddmFieldAttributes.addAll(
					_ddmFieldLocalService.getDDMFieldAttributes(
						ddmField.getStorageId(), null));
			}
		}

		for (DDMFieldAttribute ddmFieldAttribute : ddmFieldAttributes) {
			String output = ddmFieldAttribute.getAttributeValue();

			if (!output.contains("previewCTCollectionId")) {
				continue;
			}

			Source source = new Source(output);

			List<StartTag> imgTags = source.getAllStartTags("img");

			OutputDocument outputDocument = new OutputDocument(source);

			for (StartTag imgTag : imgTags) {
				Attributes attributes = imgTag.getAttributes();

				Map<String, String> map = outputDocument.replace(
					attributes, false);

				String src = attributes.getValue("src");

				int index = src.indexOf("previewCTCollectionId=");

				if (index == -1) {
					continue;
				}

				map.put("src", src.substring(0, index));
			}

			ddmFieldAttribute.setAttributeValue(outputDocument.toString());

			_ddmFieldLocalService.updateDDMFieldAttribute(ddmFieldAttribute);
		}
	}

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private DDMFieldLocalService _ddmFieldLocalService;

	@Reference
	private Portal _portal;

}