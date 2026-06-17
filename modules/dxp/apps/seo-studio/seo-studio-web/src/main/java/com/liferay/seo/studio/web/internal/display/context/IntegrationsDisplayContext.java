/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.model.FDSActionDropdownItemList;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.seo.studio.web.integration.SEOStudioIntegration;
import com.liferay.seo.studio.web.integration.SEOStudioIntegrationRegistry;
import com.liferay.seo.studio.web.internal.constants.SEOStudioFDSNames;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Kiana Suetani
 */
public class IntegrationsDisplayContext {

	public IntegrationsDisplayContext(
		HttpServletRequest httpServletRequest, Language language,
		SEOStudioIntegrationRegistry seoStudioIntegrationRegistry,
		List<ObjectEntry> seoStudioIntegrationObjectEntries,
		JSONArray viewsJSONArray) {

		_httpServletRequest = httpServletRequest;
		_language = language;
		_seoStudioIntegrationRegistry = seoStudioIntegrationRegistry;
		_seoStudioIntegrationObjectEntries = seoStudioIntegrationObjectEntries;
		_viewsJSONArray = viewsJSONArray;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getReactData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"fdsId", SEOStudioFDSNames.INTEGRATIONS
		).put(
			"integrationsURL", _getIntegrationsURL()
		).put(
			"integrationTypes", _getIntegrationTypesJSONArray()
		).put(
			"items", _getItemsJSONArray()
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
				null, null, null),
			new FDSActionDropdownItem(
				null, null, "remove",
				_language.get(_httpServletRequest, "remove"), null, null,
				null));
	}

	private String _getIntegrationsURL() {
		return _themeDisplay.getPathContext() + "/o/seo-studio/integrations";
	}

	private JSONArray _getIntegrationTypesJSONArray() throws Exception {
		Set<String> configuredKeys = new HashSet<>();

		for (ObjectEntry objectEntry : _seoStudioIntegrationObjectEntries) {
			configuredKeys.add(_getType(objectEntry));
		}

		Map<String, SEOStudioIntegration> seoStudioIntegrationsMap =
			_seoStudioIntegrationRegistry.getSEOStudioIntegrations();

		return JSONUtil.toJSONArray(
			seoStudioIntegrationsMap.values(),
			seoStudioIntegration -> JSONUtil.put(
				"configurationURL", seoStudioIntegration.getConfigurationURL()
			).put(
				"disabled",
				configuredKeys.contains(seoStudioIntegration.getKey())
			).put(
				"id", seoStudioIntegration.getKey()
			).put(
				"name", seoStudioIntegration.getLabel(_themeDisplay.getLocale())
			));
	}

	private JSONArray _getItemsJSONArray() throws Exception {
		List<ObjectEntry> objectEntries = new ArrayList<>();

		for (ObjectEntry objectEntry : _seoStudioIntegrationObjectEntries) {
			SEOStudioIntegration seoStudioIntegration =
				_seoStudioIntegrationRegistry.getSEOStudioIntegration(
					_getType(objectEntry));

			if (seoStudioIntegration != null) {
				objectEntries.add(objectEntry);
			}
		}

		return JSONUtil.toJSONArray(
			objectEntries,
			objectEntry -> {
				SEOStudioIntegration seoStudioIntegration =
					_seoStudioIntegrationRegistry.getSEOStudioIntegration(
						_getType(objectEntry));

				String state = _getState(objectEntry);

				return JSONUtil.put(
					"configurationURL",
					seoStudioIntegration.getConfigurationURL()
				).put(
					"dateModified", objectEntry.getDateModified()
				).put(
					"id", objectEntry.getId()
				).put(
					"name",
					seoStudioIntegration.getLabel(_themeDisplay.getLocale())
				).put(
					"state",
					JSONUtil.put(
						"key", state
					).put(
						"name", _language.get(_httpServletRequest, state)
					)
				);
			});
	}

	private String _getState(ObjectEntry objectEntry) {
		Map<String, Object> properties = objectEntry.getProperties();

		return GetterUtil.getString(properties.get("state"));
	}

	private String _getType(ObjectEntry objectEntry) {
		Map<String, Object> properties = objectEntry.getProperties();

		return GetterUtil.getString(properties.get("type"));
	}

	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final List<ObjectEntry> _seoStudioIntegrationObjectEntries;
	private final SEOStudioIntegrationRegistry _seoStudioIntegrationRegistry;
	private final ThemeDisplay _themeDisplay;
	private final JSONArray _viewsJSONArray;

}