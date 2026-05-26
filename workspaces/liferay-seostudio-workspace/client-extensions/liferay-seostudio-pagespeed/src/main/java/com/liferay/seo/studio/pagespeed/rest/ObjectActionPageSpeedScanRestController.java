/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.pagespeed.rest;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.seo.studio.pagespeed.PageSpeedConstants;
import com.liferay.seo.studio.pagespeed.scanner.LiferayHeadlessClient;
import com.liferay.seo.studio.pagespeed.scanner.PageSpeedScanner;

import java.net.http.HttpClient;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kiana Suetani
 */
@RequestMapping("/object/action/pagespeed/scan")
@RestController
public class ObjectActionPageSpeedScanRestController
	extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(@RequestBody String jsonString) {
		if (_log.isDebugEnabled()) {
			_log.debug(jsonString);
		}

		JSONObject jsonObject = new JSONObject(jsonString);

		JSONObject objectEntryJSONObject = jsonObject.getJSONObject(
			"objectEntry");

		JSONObject valuesJSONObject = objectEntryJSONObject.getJSONObject(
			"values");

		String scanName = _getScanName(valuesJSONObject);

		if (!Objects.equals("pageSpeed", scanName)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Ignoring scan with name: " + scanName);
			}

			return ResponseEntity.ok(
			).build();
		}

		long scanId = objectEntryJSONObject.getLong("id");

		long domainId = valuesJSONObject.optLong(
			"r_seoStudioDomainToSEOStudioScans_seoStudioDomainId", 0);

		if (domainId <= 0) {
			_scanResultWriter.updateScanState(
				scanId, "Scan is missing a domain", "failed");

			return new ResponseEntity<>(
				"Scan is missing a domain", HttpStatus.BAD_REQUEST);
		}

		if (domainId > 0) {
			Long existingScanId = _runningScansByDomain.putIfAbsent(
				domainId, scanId);

			if (existingScanId != null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"PageSpeed scan already running for domain ",
							domainId, " (scan ", existingScanId, ")"));
				}

				_scanResultWriter.updateScanState(
					scanId,
					"A PageSpeed scan is already running for this domain",
					"failed");

				return new ResponseEntity<>(
					"A PageSpeed scan is already running for this domain",
					HttpStatus.CONFLICT);
			}
		}

		String portalBaseURL =
			lxcDXPServerProtocol + "://" + lxcDXPMainDomain;

		try {
			_scanResultWriter.updateScanState(scanId, null, "running");

			String authToken = _liferayOAuth2AccessTokenManager.getTokenValue(
				PageSpeedConstants.OAHS_EXTERNAL_REFERENCE_CODE);

			LiferayHeadlessClient liferayHeadlessClient =
				new LiferayHeadlessClient(
					authToken, _httpClient, portalBaseURL);

			String[] domainInfo = liferayHeadlessClient.getDomainInfo(domainId);

			String domainHostname = domainInfo[0];
			String pageSpeedAPIKey = domainInfo[1];

			_pageSpeedScanner.scanAsync(
				domainHostname, _httpClient, _liferayOAuth2AccessTokenManager,
				pageSpeedScanResult -> {
					_scanResultWriter.updateScanState(
						scanId, null, "completed");

					if (domainId > 0) {
						_runningScansByDomain.remove(domainId, scanId);
					}
				},
				errorMessage -> {
					_scanResultWriter.updateScanState(
						scanId, errorMessage, "failed");

					if (domainId > 0) {
						_runningScansByDomain.remove(domainId, scanId);
					}
				},
				pageSpeedAPIKey, portalBaseURL,
				() -> _scanResultWriter.createProgressConsumer(scanId),
				"DESKTOP");

			return ResponseEntity.ok(
			).build();
		}
		catch (Exception exception) {
			if (exception instanceof InterruptedException) {
				Thread currentThread = Thread.currentThread();

				currentThread.interrupt();
			}

			_log.error("Unable to start PageSpeed scan", exception);

			_scanResultWriter.updateScanState(
				scanId, exception.getMessage(), "failed");

			if (domainId > 0) {
				_runningScansByDomain.remove(domainId, scanId);
			}

			return new ResponseEntity<>(
				"Unable to start PageSpeed scan",
				HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private String _getScanName(JSONObject valuesJSONObject) {
		Object nameValue = valuesJSONObject.opt("name");

		if (nameValue instanceof JSONObject) {
			JSONObject nameJSONObject = (JSONObject)nameValue;

			return nameJSONObject.optString("key", "");
		}

		return valuesJSONObject.optString("name", "");
	}

	private static final Log _log = LogFactory.getLog(
		ObjectActionPageSpeedScanRestController.class);

	private static final HttpClient _httpClient = HttpClient.newHttpClient();
	private static final ConcurrentHashMap<Long, Long> _runningScansByDomain =
		new ConcurrentHashMap<>();

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Autowired
	private PageSpeedScanner _pageSpeedScanner;

	@Autowired
	private ScanResultWriter _scanResultWriter;

}
