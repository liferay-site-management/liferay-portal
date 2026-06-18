/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.seo.studio.model.Domain;
import com.liferay.seo.studio.model.Sitemap;
import com.liferay.seo.studio.model.SitemapEntry;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Kiana Suetani
 */
@Component
public class LiferayHeadlessService extends BaseService {

	public Domain getDomain(long domainId) {
		try {
			UriComponents uriComponents = UriComponentsBuilder.fromPath(
				"/o/seo-studio/domains/" + domainId
			).queryParam(
				"nestedFields", "seoStudioInstance"
			).build();

			String domainJSON = get(
				_liferayOAuth2AccessTokenManager.getAuthorization(
					"liferay-seostudio-etc-pagespeed-oahs"),
				uriComponents.toUri());

			if (Validator.isNull(domainJSON)) {
				throw new IllegalArgumentException(
					"Domain " + domainId + " was not found");
			}

			return new Domain(new JSONObject(domainJSON));
		}
		catch (JSONException jsonException) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to read domain " + domainId, jsonException);
			}

			throw new IllegalArgumentException(
				"Domain " + domainId + " was not found", jsonException);
		}
	}

	public List<String> getSitemapPageURLs(
		String domainHostname, int maxPages) {

		if ((maxPages <= 0) || Validator.isNull(domainHostname)) {
			return Collections.emptyList();
		}

		String sitemapURL = "https://" + domainHostname + "/sitemap.xml";

		String sitemapXML = _getSitemapXML(sitemapURL);

		if (Validator.isNull(sitemapXML)) {
			return Collections.emptyList();
		}

		return _parseSitemapURLs(0, maxPages, sitemapXML);
	}

	private String _getSitemapXML(String url) {
		try {
			HttpResponse<String> httpResponse = _httpClient.send(
				HttpRequest.newBuilder(
				).uri(
					URI.create(url)
				).timeout(
					Duration.ofSeconds(10)
				).GET(
				).build(),
				HttpResponse.BodyHandlers.ofString());

			if (httpResponse.statusCode() != HttpURLConnection.HTTP_OK) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Unable to fetch sitemap ", url, ", HTTP ",
							httpResponse.statusCode()));
				}

				return null;
			}

			return httpResponse.body();
		}
		catch (InterruptedException | IOException exception) {
			if (exception instanceof InterruptedException) {
				Thread thread = Thread.currentThread();

				thread.interrupt();
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Unable to fetch sitemap " + url, exception);
			}

			return null;
		}
	}

	private List<String> _parseSitemapURLs(
		int depth, int maxPages, String xml) {

		if (maxPages <= 0) {
			return Collections.emptyList();
		}

		if (depth > _MAX_SITEMAP_RECURSION_DEPTH) {
			if (_log.isDebugEnabled()) {
				_log.debug("Maximum sitemap recursion depth exceeded");
			}

			return Collections.emptyList();
		}

		Sitemap sitemap;

		try {
			sitemap = _xmlMapper.readValue(xml, Sitemap.class);
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse sitemap", ioException);
			}

			return Collections.emptyList();
		}

		List<SitemapEntry> indexSitemapEntries =
			sitemap.getIndexSitemapEntries();

		if (ListUtil.isEmpty(indexSitemapEntries)) {
			List<SitemapEntry> urlSitemapEntries =
				sitemap.getURLSitemapEntries();

			if (ListUtil.isEmpty(urlSitemapEntries)) {
				return Collections.emptyList();
			}

			List<String> urls = new ArrayList<>();

			for (SitemapEntry sitemapEntry : urlSitemapEntries) {
				String loc = sitemapEntry.getLoc();

				if (Validator.isNotNull(loc)) {
					urls.add(loc.trim());

					if (urls.size() >= maxPages) {
						break;
					}
				}
			}

			return urls;
		}

		List<String> urls = new ArrayList<>();

		for (SitemapEntry sitemapEntry : indexSitemapEntries) {
			String loc = sitemapEntry.getLoc();

			if (Validator.isNull(loc)) {
				continue;
			}

			String sitemapXML = _getSitemapXML(loc.trim());

			if (Validator.isNotNull(sitemapXML)) {
				urls.addAll(
					_parseSitemapURLs(
						depth + 1, maxPages - urls.size(), sitemapXML));
			}

			if (urls.size() >= maxPages) {
				break;
			}
		}

		return urls;
	}

	private static final int _MAX_SITEMAP_RECURSION_DEPTH = 3;

	private static final Log _log = LogFactory.getLog(
		LiferayHeadlessService.class);

	private final HttpClient _httpClient = HttpClient.newBuilder(
	).connectTimeout(
		Duration.ofSeconds(5)
	).build();

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	private final XmlMapper _xmlMapper = new XmlMapper();

}