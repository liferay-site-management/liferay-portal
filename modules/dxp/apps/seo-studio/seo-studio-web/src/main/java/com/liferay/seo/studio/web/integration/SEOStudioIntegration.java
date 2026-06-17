/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.integration;

import java.util.Locale;

/**
 * @author Kiana Suetani
 */
public interface SEOStudioIntegration {

	public String getConfigurationURL();

	public String getKey();

	public String getLabel(Locale locale);

	public void remove(long seoStudioInstanceId);

}