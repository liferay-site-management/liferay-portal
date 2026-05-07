/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.seo.studio.web.internal.display.context.ViewSiteSEODisplayContext;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brooke Dalton
 */
@Component(service = FragmentRenderer.class)
public class ViewSiteSEOFragmentRenderer
	extends BaseFragmentRenderer<ViewSiteSEODisplayContext> {

	@Override
	public String getCollectionKey() {
		return "site-seo";
	}

	@Override
	protected ViewSiteSEODisplayContext getDisplayContext(
		HttpServletRequest httpServletRequest) {

		return new ViewSiteSEODisplayContext(httpServletRequest);
	}

	@Override
	protected String getJSPPath() {
		return "/view_site_seo.jsp";
	}

}