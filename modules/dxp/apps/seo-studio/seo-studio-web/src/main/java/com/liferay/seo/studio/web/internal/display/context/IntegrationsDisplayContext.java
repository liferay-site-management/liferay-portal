/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.model.FDSActionDropdownItemList;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.seo.studio.web.internal.constants.SEOStudioFDSNames;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @author Kiana Suetani
 */
public class IntegrationsDisplayContext {

	public IntegrationsDisplayContext(
		HttpServletRequest httpServletRequest, Language language,
		JSONArray viewsJSONArray) {

		_httpServletRequest = httpServletRequest;
		_language = language;
		_viewsJSONArray = viewsJSONArray;
	}

	public Map<String, Object> getReactData() {
		return HashMapBuilder.<String, Object>put(
			"apiURL", "/o/seo-studio/instances"
		).put(
			"fdsId", SEOStudioFDSNames.INTEGRATIONS
		).put(
			"itemsActions", _getFDSActionDropdownItems()
		).put(
			"views", _viewsJSONArray
		).build();
	}

	private List<FDSActionDropdownItem> _getFDSActionDropdownItems() {
		return FDSActionDropdownItemList.of(
			new FDSActionDropdownItem(
				null, null, "edit", _language.get(_httpServletRequest, "edit"),
				null, null, "link"),
			new FDSActionDropdownItem(
				null, null, "remove",
				_language.get(_httpServletRequest, "remove"), null, null,
				"event"));
	}

	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final JSONArray _viewsJSONArray;

}