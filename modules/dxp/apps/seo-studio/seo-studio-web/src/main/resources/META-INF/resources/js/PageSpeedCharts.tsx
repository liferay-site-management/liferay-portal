/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useRef, useState} from 'react';

import GaugeChart from './GaugeChart';

const POLL_INTERVAL_MS = 4000;

const SCANNER_RESULTS_API = '/o/seo-studio/pagespeed-results';

const STALE_THRESHOLD_MS = 10 * 60 * 1000;

interface ScanResult {
	accessibilityScore: number;
	bestPracticesScore: number;
	dateCreated: string;
	dateModified: string;
	errorMessage?: string;
	pagesErrored: number;
	pagesScanned: number;
	pagesTotal: number;
	performanceScore: number;
	seoScore: number;
}

function formatDate(dateString: string): string {
	const date = new Date(dateString);

	const formattedDate = date.toLocaleDateString(undefined, {
		day: 'numeric',
		month: 'short',
		year: 'numeric',
	});

	const formattedTime = date.toLocaleTimeString(undefined, {
		hour: '2-digit',
		minute: '2-digit',
	});

	return `Last checked: ${formattedDate} ${formattedTime}`;
}

function isInProgress(item: ScanResult): boolean {
	return (
		item.pagesTotal > 0 &&
		item.pagesScanned < item.pagesTotal &&
		!item.errorMessage
	);
}

function isStale(item: ScanResult): boolean {
	const timeSinceUpdate = Date.now() - new Date(item.dateModified).getTime();

	return timeSinceUpdate > STALE_THRESHOLD_MS;
}

export default function PageSpeedCharts() {
	const [result, setResult] = useState<ScanResult | null>(null);

	const timerRef = useRef<ReturnType<typeof setTimeout> | null>(null);

	useEffect(() => {
		let cancelled = false;

		function fetchResult() {
			fetch(SCANNER_RESULTS_API + '?sort=dateCreated:desc&pageSize=1', {
				headers: {
					'Accept': 'application/json',
					'x-csrf-token': globalThis.Liferay?.authToken || '',
				},
			})
				.then((response) => (response.ok ? response.json() : null))
				.then((data) => {
					if (cancelled) {
						return;
					}

					if (!data?.items?.length) {
						stopPolling();

						return;
					}

					const item = data.items[0] as ScanResult;

					if (isInProgress(item) && !isStale(item)) {
						scheduleNext();
					}
					else {
						stopPolling();

						if (item.pagesScanned > 0) {
							setResult(item);
						}
					}
				})
				.catch(() => {
					if (!cancelled) {
						stopPolling();
					}
				});
		}

		function scheduleNext() {
			if (!cancelled && !timerRef.current) {
				timerRef.current = setTimeout(() => {
					timerRef.current = null;
					fetchResult();
				}, POLL_INTERVAL_MS);
			}
		}

		function stopPolling() {
			if (timerRef.current) {
				clearTimeout(timerRef.current);
				timerRef.current = null;
			}
		}

		fetchResult();

		return () => {
			cancelled = true;
			stopPolling();
		};
	}, []);

	if (!result) {
		return (
			<div className="pagespeed-charts">
				<div className="pagespeed-charts-header">
					<h2 className="pagespeed-charts-title">
						Google PageSpeed Metrics
					</h2>
				</div>

				<div className="pagespeed-charts-empty">
					<h3 className="pagespeed-charts-empty-title">
						No Data Available Yet
					</h3>

					<p className="pagespeed-charts-empty-description">
						There's no data available for the applied filters or
						from the data source.
					</p>
				</div>
			</div>
		);
	}

	return (
		<div className="pagespeed-charts">
			<div className="pagespeed-charts-header">
				<div>
					<h2 className="pagespeed-charts-title">
						Google PageSpeed Metrics
					</h2>

					{result.dateCreated && (
						<span className="pagespeed-charts-timestamp">
							{formatDate(result.dateCreated)}
						</span>
					)}
				</div>
			</div>

			<div className="pagespeed-charts-results">
				<GaugeChart
					label="Performance"
					score={result.performanceScore || 0}
				/>

				<GaugeChart
					label="Accessibility"
					score={result.accessibilityScore || 0}
				/>

				<GaugeChart
					label="Best Practices"
					score={result.bestPracticesScore || 0}
				/>

				<GaugeChart label="SEO" score={result.seoScore || 0} />
			</div>
		</div>
	);
}
