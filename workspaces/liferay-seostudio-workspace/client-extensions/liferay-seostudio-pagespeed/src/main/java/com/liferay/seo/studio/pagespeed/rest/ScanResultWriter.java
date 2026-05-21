/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.pagespeed.rest;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.seo.studio.pagespeed.PageSpeedConstants;
import com.liferay.seo.studio.pagespeed.scanner.PageSpeedScanResult;
import com.liferay.seo.studio.pagespeed.scanner.PageSpeedScores;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Kiana Suetani
 */
@Service
public class ScanResultWriter {

	public Consumer<PageSpeedScanResult> createProgressConsumer(long scanId) {
		AtomicLong resultEntryId = new AtomicLong(0);

		return pageSpeedScanResult -> {
			try {
				String authorization =
					_liferayOAuth2AccessTokenManager.getAuthorization(
						PageSpeedConstants.OAHS_EXTERNAL_REFERENCE_CODE);

				JSONObject jsonObject = _toJSONObject(
					pageSpeedScanResult, scanId);

				String portalBaseURL = _getPortalBaseURL();

				long currentEntryId = resultEntryId.get();

				if (currentEntryId == 0) {
					HttpRequest httpRequest = HttpRequest.newBuilder(
					).POST(
						HttpRequest.BodyPublishers.ofString(
							jsonObject.toString())
					).header(
						"Accept", "application/json"
					).header(
						"Authorization", authorization
					).header(
						"Content-Type", "application/json"
					).uri(
						URI.create(portalBaseURL + _SCANNER_RESULTS_PATH)
					).build();

					HttpResponse<String> httpResponse = _httpClient.send(
						httpRequest, HttpResponse.BodyHandlers.ofString());

					if (httpResponse.statusCode() >= 400) {
						_log.error(
							StringBundler.concat(
								"POST failed: ", httpResponse.statusCode(), " ",
								httpResponse.body()));

						return;
					}

					JSONObject responseJSONObject = new JSONObject(
						httpResponse.body());

					resultEntryId.compareAndSet(
						0, responseJSONObject.getLong("id"));

					if (_log.isDebugEnabled()) {
						_log.debug(
							"Created scanner result entry " +
								resultEntryId.get());
					}
				}
				else {
					HttpRequest httpRequest = HttpRequest.newBuilder(
					).method(
						"PATCH",
						HttpRequest.BodyPublishers.ofString(
							jsonObject.toString())
					).header(
						"Accept", "application/json"
					).header(
						"Authorization", authorization
					).header(
						"Content-Type", "application/json"
					).uri(
						URI.create(
							StringBundler.concat(
								portalBaseURL, _SCANNER_RESULTS_PATH, "/",
								currentEntryId))
					).build();

					HttpResponse<String> httpResponse = _httpClient.send(
						httpRequest, HttpResponse.BodyHandlers.ofString());

					if (httpResponse.statusCode() >= 400) {
						_log.error(
							StringBundler.concat(
								"PATCH failed: ", httpResponse.statusCode(),
								" ", httpResponse.body()));

						return;
					}

					if (_log.isDebugEnabled()) {
						_log.debug(
							"Updated scanner result entry " + currentEntryId);
					}
				}
			}
			catch (Exception exception) {
				if (exception instanceof InterruptedException) {
					Thread currentThread = Thread.currentThread();

					currentThread.interrupt();
				}

				_log.error(
					"Unable to write scanner result to Liferay", exception);
			}
		};
	}

	public void updateScanState(
		long scanId, String errorMessage, String stateKey) {

		try {
			String authorization =
				_liferayOAuth2AccessTokenManager.getAuthorization(
					PageSpeedConstants.OAHS_EXTERNAL_REFERENCE_CODE);

			String portalBaseURL = _lxcDXPServerProtocol + "://" + _lxcDXPMainDomain;

			JSONObject jsonObject = new JSONObject();

			if (errorMessage != null) {
				jsonObject.put("errorMessage", errorMessage);
			}

			JSONObject stateJSONObject = new JSONObject();

			stateJSONObject.put("key", stateKey);

			jsonObject.put("state", stateJSONObject);

			HttpRequest httpRequest = HttpRequest.newBuilder(
			).method(
				"PATCH",
				HttpRequest.BodyPublishers.ofString(jsonObject.toString())
			).header(
				"Accept", "application/json"
			).header(
				"Authorization", authorization
			).header(
				"Content-Type", "application/json"
			).uri(
				URI.create(
					StringBundler.concat(portalBaseURL, _SCANS_PATH, "/", scanId))
			).build();

			HttpResponse<String> httpResponse = _httpClient.send(
				httpRequest, HttpResponse.BodyHandlers.ofString());

			if (httpResponse.statusCode() >= 400) {
				_log.error(
					StringBundler.concat(
						"Unable to update scan ", scanId, " state: ",
						httpResponse.statusCode(), " ", httpResponse.body()));
			}
		}
		catch (Exception exception) {
			if (exception instanceof InterruptedException) {
				Thread currentThread = Thread.currentThread();

				currentThread.interrupt();
			}

			_log.error(
				StringBundler.concat(
					"Unable to update scan ", scanId, " state"),
				exception);
		}
	}

	private String _getPortalBaseURL() {
		return _lxcDXPServerProtocol + "://" + _lxcDXPMainDomain;
	}

	private JSONObject _toJSONObject(
		PageSpeedScanResult pageSpeedScanResult, long scanId) {

		JSONObject jsonObject = new JSONObject();

		String errorMessage = pageSpeedScanResult.getErrorMessage();

		if (errorMessage != null) {
			jsonObject.put("errorMessage", errorMessage);
		}

		jsonObject.put(
			"pagesErrored", pageSpeedScanResult.getPagesErrored()
		).put(
			"pagesScanned", pageSpeedScanResult.getPagesScanned()
		).put(
			"pagesTotal", pageSpeedScanResult.getPagesTotal()
		).put(
			"r_seoStudioScanToSEOStudioPageSpeedResults_seoStudioScanId", scanId
		).put(
			"strategy", pageSpeedScanResult.getStrategy()
		);

		PageSpeedScores pageSpeedScores =
			pageSpeedScanResult.getAverageScores();

		if (pageSpeedScores != null) {
			jsonObject.put(
				"accessibilityScore", pageSpeedScores.getAccessibility()
			).put(
				"bestPracticesScore", pageSpeedScores.getBestPractices()
			).put(
				"performanceScore", pageSpeedScores.getPerformance()
			).put(
				"seoScore", pageSpeedScores.getSeo()
			);
		}

		return jsonObject;
	}

	private static final String _SCANNER_RESULTS_PATH =
		"/o/seo-studio/pagespeed-results";

	private static final String _SCANS_PATH = "/o/seo-studio/scans";

	private static final Log _log = LogFactory.getLog(ScanResultWriter.class);

	private final HttpClient _httpClient = HttpClient.newHttpClient();

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

}