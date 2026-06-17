/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.seo.studio.web.integration.SEOStudioIntegrationRegistry;
import com.liferay.seo.studio.web.internal.constants.SEOStudioFDSNames;
import com.liferay.seo.studio.web.internal.display.context.IntegrationsDisplayContext;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kiana Suetani
 */
@Component(service = FragmentRenderer.class)
public class IntegrationsFragmentRenderer
	extends BaseFragmentRenderer<IntegrationsDisplayContext> {

	@Override
	public String getCollectionKey() {
		return "sections";
	}

	@Override
	public String getLabel(Locale locale) {
		return language.get(locale, "integrations");
	}

	@Override
	protected IntegrationsDisplayContext getDisplayContext(
		HttpServletRequest httpServletRequest) {

		JSONArray viewsJSONArray = fdsSerializer.serializeViews(
			SEOStudioFDSNames.INTEGRATIONS, httpServletRequest);

		List<ObjectEntry> seoStudioIntegrationObjectEntries =
			_fetchSEOStudioIntegrationObjectEntries(httpServletRequest);

		return new IntegrationsDisplayContext(
			httpServletRequest, language, _seoStudioIntegrationRegistry,
			seoStudioIntegrationObjectEntries, viewsJSONArray);
	}

	@Override
	protected String getJSPPath() {
		return "/integrations.jsp";
	}

	private List<ObjectEntry> _fetchSEOStudioIntegrationObjectEntries(
		HttpServletRequest httpServletRequest) {

		try {
			long companyId = portal.getCompanyId(httpServletRequest);

			ObjectDefinition objectDefinition =
				objectDefinitionLocalService.
					fetchObjectDefinitionByExternalReferenceCode(
						"L_SEO_STUDIO_INTEGRATION", companyId);

			if (objectDefinition == null) {
				return Collections.emptyList();
			}

			Page<ObjectEntry> page = objectEntryManager.getObjectEntries(
				companyId, objectDefinition, null, null,
				getDTOConverterContext(objectDefinition), null,
				Pagination.of(1, 100), null, null);

			return new ArrayList<>(page.getItems());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			return Collections.emptyList();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IntegrationsFragmentRenderer.class);

	@Reference
	private SEOStudioIntegrationRegistry _seoStudioIntegrationRegistry;

}