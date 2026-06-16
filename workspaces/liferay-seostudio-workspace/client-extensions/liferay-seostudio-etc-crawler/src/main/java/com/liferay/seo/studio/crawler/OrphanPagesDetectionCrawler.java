/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.crawler;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.seo.studio.model.CrawlHit;
import com.liferay.seo.studio.service.SEOStudioService;

import java.net.URI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.stereotype.Component;

/**
 * @author Brooke Dalton
 */
@Component
public class OrphanPagesDetectionCrawler extends BaseDetectionCrawler {

	@Override
	public void detect(
			long accountEntryId, List<CrawlHit> crawlHits, URI hostname,
			long seoStudioScanId)
		throws Exception {

		Set<String> canonicalURLs = new LinkedHashSet<>();
		Set<String> linkedURLs = new HashSet<>();

		for (CrawlHit crawlHit : crawlHits) {
			String canonicalURL = crawlHit.getCanonicalURL();

			if (Validator.isNull(canonicalURL)) {
				continue;
			}

			canonicalURLs.add(canonicalURL);

			for (String linkURL : crawlHit.getLinks()) {
				if (Validator.isNotNull(linkURL) &&
					!linkURL.equals(canonicalURL)) {

					linkedURLs.add(linkURL);
				}
			}
		}

		List<String> orphanPageURLs = new ArrayList<>();

		String domainURL = SEOStudioService.toDomainURL(hostname);

		for (String canonicalURL : canonicalURLs) {
			if (canonicalURL.equals(domainURL) ||
				linkedURLs.contains(canonicalURL)) {

				continue;
			}

			orphanPageURLs.add(canonicalURL);
		}

		if (ListUtil.isEmpty(orphanPageURLs)) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"No orphan pages were detected for scan " +
						seoStudioScanId);
			}

			return;
		}

		JSONObject definitionJSONObject = new JSONObject();

		definitionJSONObject.put(
			"category", "linksAndURLs"
		).put(
			"classification", "problem"
		).put(
			"name", "orphanPages"
		).put(
			"severity", "2"
		);

		writeInsights(
			accountEntryId, definitionJSONObject,
			ensurePages(accountEntryId, orphanPageURLs, seoStudioScanId),
			orphanPageURLs, seoStudioScanId);
	}

	private static final Log _log = LogFactory.getLog(
		OrphanPagesDetectionCrawler.class);

}