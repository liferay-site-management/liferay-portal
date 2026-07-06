/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.display.context;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.seo.studio.web.internal.constants.SEOStudioFDSNames;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @author Noor Najjar
 */
public class ViewOnPageInsightDetailsDisplayContext {

	public ViewOnPageInsightDetailsDisplayContext(
		HttpServletRequest httpServletRequest, Language language,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryLocalService objectEntryLocalService,
		ThemeDisplay themeDisplay, JSONArray viewsJSONArray) {

		_httpServletRequest = httpServletRequest;
		_language = language;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryLocalService = objectEntryLocalService;
		_themeDisplay = themeDisplay;
		_viewsJSONArray = viewsJSONArray;
	}

	public List<Map<String, Object>> getBreadcrumbItems() throws Exception {
		return List.of(
			HashMapBuilder.<String, Object>put(
				"href", _getBackURL()
			).put(
				"label", _language.get(_httpServletRequest, "on-page")
			).build());
	}

	public Map<String, Object> getReactData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"apiURL", _getAPIURL()
		).put(
			"breadcrumbItems", getBreadcrumbItems()
		).put(
			"externalReferenceCode", _getObjectEntryExternalReferenceCode()
		).put(
			"fdsId", SEOStudioFDSNames.AFFECTED_PAGES_SECTION
		).put(
			"views", _viewsJSONArray
		).build();
	}

	private String _getAPIURL() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_SEO_STUDIO_INSIGHT_TYPE", _themeDisplay.getCompanyId());

		ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
			_getObjectEntryExternalReferenceCode(),
			_themeDisplay.getScopeGroupId(),
			objectDefinition.getObjectDefinitionId());

		return StringBundler.concat(
			"/o/seo-studio/insight-types/", objectEntry.getObjectEntryId(),
			"/seoStudioInsightTypeToScanInsights?filter=",
			URLCodec.encodeURL(
				"state eq " + WorkflowConstants.STATUS_PENDING, true),
			"&nestedFields=",
			URLCodec.encodeURL(
				"r_seoStudioPageToSEOStudioScanInsights_seoStudioPage", true));
	}

	private String _getBackURL() throws Exception {
		String backURL = PortalUtil.escapeRedirect(
			ParamUtil.getString(_httpServletRequest, "backURL"));

		if (Validator.isNotNull(backURL)) {
			return backURL;
		}

		return PortalUtil.getLayoutFullURL(
			LayoutLocalServiceUtil.getLayoutByFriendlyURL(
				_themeDisplay.getScopeGroupId(), false, "/content-seo"),
			_themeDisplay);
	}

	private String _getObjectEntryExternalReferenceCode() {
		return ParamUtil.getString(
			_httpServletRequest, "objectEntryExternalReferenceCode");
	}

	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ThemeDisplay _themeDisplay;
	private final JSONArray _viewsJSONArray;

}