/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * @author Kiana Suetani
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SitemapEntry {

	public String getLoc() {
		return _loc;
	}

	@JacksonXmlProperty(localName = "loc")
	private String _loc;

}