/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kiana Suetani
 */
@JacksonXmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sitemap {

	public List<SitemapEntry> getSitemaps() {
		return _sitemaps;
	}

	public List<SitemapEntry> getURLs() {
		return _urls;
	}

	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "sitemap")
	private List<SitemapEntry> _sitemaps = new ArrayList<>();

	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "url")
	private List<SitemapEntry> _urls = new ArrayList<>();

}