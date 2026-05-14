/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.pagespeed.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Kiana Suetani
 */
public class PageSpeedScanner {

	public PageSpeedScanResult getScanResult(String key) {
		return _scanResults.get(key);
	}

	public PageSpeedScanResult scan(
			String apiKey, String authToken, String portalURL, String strategy)
		throws Exception {

		LiferayHeadlessClient liferayHeadlessClient = new LiferayHeadlessClient(
			authToken, portalURL);

		List<String> virtualHosts = liferayHeadlessClient.getVirtualHosts();

		String key = StringBundler.concat(
			portalURL, ":", String.join(",", virtualHosts), ":", strategy);

		_scanResults.put(
			key,
			new PageSpeedScanResult(
				null, null, 0, 0, PageSpeedScanResult.STATUS_RUNNING));

		if (virtualHosts.isEmpty()) {
			PageSpeedScanResult pageSpeedScanResult = new PageSpeedScanResult(
				"No virtual hosts found", null, 0, 0,
				PageSpeedScanResult.STATUS_FAILED);

			_scanResults.put(key, pageSpeedScanResult);

			return pageSpeedScanResult;
		}

		LinkedHashSet<String> urlSet = new LinkedHashSet<>();

		for (String virtualHost : virtualHosts) {
			urlSet.addAll(liferayHeadlessClient.getPageURLs(virtualHost));
		}

		List<String> urls = new ArrayList<>(urlSet);

		if (urls.isEmpty()) {
			PageSpeedScanResult pageSpeedScanResult = new PageSpeedScanResult(
				null, new PageSpeedScores(0, 0, 0, 0), 0, 0,
				PageSpeedScanResult.STATUS_COMPLETED);

			_scanResults.put(key, pageSpeedScanResult);

			return pageSpeedScanResult;
		}

		if (urls.size() > _PAGE_LIMIT) {
			urls = urls.subList(0, _PAGE_LIMIT);
		}

		PageSpeedScoreProvider pageSpeedScoreProvider =
			new PageSpeedScoreProvider(apiKey, strategy);

		if (!pageSpeedScoreProvider.isValidConnection()) {
			PageSpeedScanResult pageSpeedScanResult = new PageSpeedScanResult(
				"Google PageSpeed API key is not configured", null, 0, 0,
				PageSpeedScanResult.STATUS_FAILED);

			_scanResults.put(key, pageSpeedScanResult);

			return pageSpeedScanResult;
		}

		PageSpeedScanResult pageSpeedScanResult = _scanURLs(
			pageSpeedScoreProvider, urls);

		_scanResults.put(key, pageSpeedScanResult);

		return pageSpeedScanResult;
	}

	private PageSpeedScores _computeAverageScores(
		List<PageSpeedScores> scoresList) {

		if (scoresList.isEmpty()) {
			return new PageSpeedScores(0, 0, 0, 0);
		}

		int count = scoresList.size();

		int totalAccessibility = 0;
		int totalBestPractices = 0;
		int totalPerformance = 0;
		int totalSeo = 0;

		for (PageSpeedScores pageSpeedScores : scoresList) {
			totalAccessibility += pageSpeedScores.getAccessibility();
			totalBestPractices += pageSpeedScores.getBestPractices();
			totalPerformance += pageSpeedScores.getPerformance();
			totalSeo += pageSpeedScores.getSeo();
		}

		return new PageSpeedScores(
			Math.round((float)totalAccessibility / count),
			Math.round((float)totalBestPractices / count),
			Math.round((float)totalPerformance / count),
			Math.round((float)totalSeo / count));
	}

	private PageSpeedScanResult _scanURLs(
		PageSpeedScoreProvider pageSpeedScoreProvider, List<String> urls) {

		AtomicBoolean quotaExceeded = new AtomicBoolean(false);
		AtomicInteger pagesErrored = new AtomicInteger(0);

		CopyOnWriteArrayList<PageSpeedScores> scoresList =
			new CopyOnWriteArrayList<>();

		ExecutorService executorService = Executors.newFixedThreadPool(
			_WORKER_COUNT);

		List<Future<?>> futures = new ArrayList<>();

		for (String url : urls) {
			futures.add(
				executorService.submit(
					() -> {
						if (quotaExceeded.get()) {
							return;
						}

						try {
							scoresList.add(
								pageSpeedScoreProvider.getScores(url));
						}
						catch (PageSpeedScoreProvider.
									PageSpeedScoreProviderException
										pageSpeedScoreProviderException) {

							if (pageSpeedScoreProviderException.
									isQuotaExceeded()) {

								quotaExceeded.set(true);
							}
							else {
								pagesErrored.incrementAndGet();

								if (_log.isDebugEnabled()) {
									_log.debug(
										"Unable to get PageSpeed scores for " +
											url,
										pageSpeedScoreProviderException);
								}
							}
						}
					}));
		}

		executorService.shutdown();

		for (Future<?> future : futures) {
			try {
				future.get();
			}
			catch (Exception exception) {
				pagesErrored.incrementAndGet();

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to complete PageSpeed scan task", exception);
				}
			}
		}

		String errorMessage = null;
		String status = PageSpeedScanResult.STATUS_COMPLETED;

		int pagesScanned = scoresList.size();

		if (quotaExceeded.get()) {
			errorMessage = StringBundler.concat(
				"Google PageSpeed API quota exceeded after scanning ",
				pagesScanned, " of ", urls.size(), " pages");
		}

		if ((pagesScanned == 0) && (pagesErrored.get() > 0)) {
			errorMessage = "All pages failed to scan";
			status = PageSpeedScanResult.STATUS_FAILED;
		}

		return new PageSpeedScanResult(
			errorMessage, _computeAverageScores(scoresList), pagesErrored.get(),
			pagesScanned, status);
	}

	private static final int _PAGE_LIMIT = 100;

	private static final int _WORKER_COUNT = 5;

	private static final Log _log = LogFactoryUtil.getLog(
		PageSpeedScanner.class);

	private final Map<String, PageSpeedScanResult> _scanResults =
		new ConcurrentHashMap<>();

}