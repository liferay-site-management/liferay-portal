/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.pagespeed.internal;

/**
 * @author Kiana Suetani
 */
public class PageSpeedScanResult {

	public static final String STATUS_COMPLETED = "completed";

	public static final String STATUS_FAILED = "failed";

	public static final String STATUS_RUNNING = "running";

	public PageSpeedScanResult(
		String errorMessage, PageSpeedScores pageSpeedScores, int pagesErrored,
		int pagesScanned, String status) {

		_errorMessage = errorMessage;
		_pageSpeedScores = pageSpeedScores;
		_pagesErrored = pagesErrored;
		_pagesScanned = pagesScanned;
		_status = status;
	}

	public String getErrorMessage() {
		return _errorMessage;
	}

	public int getPagesErrored() {
		return _pagesErrored;
	}

	public PageSpeedScores getPageSpeedScores() {
		return _pageSpeedScores;
	}

	public int getPagesScanned() {
		return _pagesScanned;
	}

	public String getStatus() {
		return _status;
	}

	private final String _errorMessage;
	private final int _pagesErrored;
	private final PageSpeedScores _pageSpeedScores;
	private final int _pagesScanned;
	private final String _status;

}