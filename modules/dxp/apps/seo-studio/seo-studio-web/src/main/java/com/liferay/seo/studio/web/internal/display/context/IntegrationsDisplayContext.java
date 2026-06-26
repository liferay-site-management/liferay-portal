/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.model.FDSActionDropdownItemList;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.seo.studio.web.integration.SEOStudioIntegration;
import com.liferay.seo.studio.web.internal.constants.SEOStudioFDSNames;
import com.liferay.seo.studio.web.internal.integration.SEOStudioIntegrationRegistry;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashSet;
import java.util.LinkedHashMap;
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
			configuredKeys.add(_getProperty(objectEntry, "type"));
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
		Map<ObjectEntry, SEOStudioIntegration> seoStudioIntegrationsMap =
			new LinkedHashMap<>();

		for (ObjectEntry objectEntry : _seoStudioIntegrationObjectEntries) {
			SEOStudioIntegration seoStudioIntegration =
				_seoStudioIntegrationRegistry.getSEOStudioIntegration(
					_getProperty(objectEntry, "type"));

			if (seoStudioIntegration != null) {
				seoStudioIntegrationsMap.put(objectEntry, seoStudioIntegration);
			}
		}

		return JSONUtil.toJSONArray(
			seoStudioIntegrationsMap.entrySet(),
			entry -> {
				ObjectEntry objectEntry = entry.getKey();
				SEOStudioIntegration seoStudioIntegration = entry.getValue();

				String state = _getProperty(objectEntry, "state");

				return JSONUtil.put(
					"configurationURL",
					seoStudioIntegration.getConfigurationURL()
				).put(
					"dateModified",
					objectEntry.getDateModified(
					).toInstant(
					).toString()
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

	private String _getProperty(ObjectEntry objectEntry, String key) {
		Map<String, Object> properties = objectEntry.getProperties();

		if (properties == null) {
			return null;
		}

		Object value = properties.get(key);

		if (value == null) {
			return null;
		}

		if (value instanceof ListEntry) {
			ListEntry listEntry = (ListEntry)value;

			return listEntry.getKey();
		}

		return String.valueOf(value);
	}

	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final List<ObjectEntry> _seoStudioIntegrationObjectEntries;
	private final SEOStudioIntegrationRegistry _seoStudioIntegrationRegistry;
	private final ThemeDisplay _themeDisplay;
	private final JSONArray _viewsJSONArray;

}