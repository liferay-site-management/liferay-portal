/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.client;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Kiana Suetani
 */
public class LiferayHeadlessClient {

	public LiferayHeadlessClient(
		String authToken, HttpClient httpClient, String portalBaseURL) {

		_authToken = authToken;
		_httpClient = httpClient;
		_portalBaseURL = portalBaseURL;
	}

	public Domain getDomain(long domainId) {
		try {
			String domainJSON = _makeRequest(
				StringBundler.concat(
					_portalBaseURL, "/o/seo-studio/domains/", domainId,
					"?nestedFields=seoStudioInstance"));

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
			return new ArrayList<>();
		}

		String sitemapURL = "https://" + domainHostname + "/sitemap.xml";

		String sitemapXML = _makeRequest(sitemapURL);

		if (Validator.isNull(sitemapXML)) {
			return new ArrayList<>();
		}

		return _parseSitemapURLs(0, maxPages, sitemapXML);
	}

	private String _makeRequest(String url) {
		try {
			HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder(
			).uri(
				URI.create(url)
			).GET();

			if (Validator.isNotNull(_authToken)) {
				httpRequestBuilder.header(
					"Authorization", "Bearer " + _authToken);
			}

			HttpResponse<String> httpResponse = _httpClient.send(
				httpRequestBuilder.build(),
				HttpResponse.BodyHandlers.ofString());

			if (httpResponse.statusCode() != HttpURLConnection.HTTP_OK) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Response code ", httpResponse.statusCode(),
							" for ", url));
				}

				return null;
			}

			return httpResponse.body();
		}
		catch (InterruptedException interruptedException) {
			Thread thread = Thread.currentThread();

			thread.interrupt();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to make request to " + url, interruptedException);
			}

			return null;
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to make request to " + url, ioException);
			}

			return null;
		}
	}

	private List<String> _parseSitemapURLs(
		int depth, int maxPages, String xml) {

		if (maxPages <= 0) {
			return new ArrayList<>();
		}

		if (depth > 3) {
			if (_log.isDebugEnabled()) {
				_log.debug("Maximum sitemap recursion depth exceeded");
			}

			return new ArrayList<>();
		}

		Sitemap sitemap;

		try {
			sitemap = _xmlMapper.readValue(xml, Sitemap.class);
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse sitemap", ioException);
			}

			return new ArrayList<>();
		}

		List<SitemapEntry> sitemapEntries = sitemap.getSitemaps();

		if (ListUtil.isEmpty(sitemapEntries)) {
			List<String> urls = new ArrayList<>();
			List<SitemapEntry> urlSitemapEntries = sitemap.getURLs();

			if (ListUtil.isEmpty(urlSitemapEntries)) {
				return urls;
			}

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

		for (SitemapEntry sitemapEntry : sitemapEntries) {
			String childSitemapURL = sitemapEntry.getLoc();

			if (Validator.isNull(childSitemapURL)) {
				continue;
			}

			String childSitemapXML = _makeRequest(childSitemapURL.trim());

			if (Validator.isNotNull(childSitemapXML)) {
				urls.addAll(
					_parseSitemapURLs(
						depth + 1, maxPages - urls.size(), childSitemapXML));
			}

			if (urls.size() >= maxPages) {
				break;
			}
		}

		return urls;
	}

	private static final Log _log = LogFactory.getLog(
		LiferayHeadlessClient.class);

	private static final XmlMapper _xmlMapper = new XmlMapper();

	private final String _authToken;
	private final HttpClient _httpClient;
	private final String _portalBaseURL;

}