/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput, ClaySelect} from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {
	bulkCreateHiddenPageInsights,
	bulkDeleteHiddenPageInsights,
} from '../common/services/HiddenPageInsightsService';
import LastCrawlSummary from './LastCrawlSummary';

import type {InsightApiRow} from '../common/types/InsightApiRow';
import type {InsightSummaryRow} from '../common/types/InsightSummaryRow';

type CrawledPage = {
	id: string;
	lastCrawledAt: string;
	links: string[];
	title: string;
	url: string;
};

type CrawlSource = {
	id: number;
	indexName: string;
	url: string;
};

type ElasticsearchSearchResponse = {
	hits: {
		hits: Array<{
			_id: string;
			_source: {
				last_crawled_at?: string;
				links?: string[];
				title?: string;
				url?: string;
			};
		}>;
	};
};

const ISSUES_TABLE_VIEWS = [
	{
		contentRenderer: 'table',
		name: 'table',
		schema: {
			fields: [
				{
					contentRenderer: 'link',
					fieldName: 'issueLink',
					label: Liferay.Language.get('issue'),
				},
				{
					fieldName: 'category',
					label: Liferay.Language.get('category'),
				},
				{
					fieldName: 'affectedPages',
					label: Liferay.Language.get('affected-pages'),
				},
			],
		},
	},
];

const ISSUE_LINKS: Record<string, {href: string; label: string}> = {
	'crawlability|hidden_page': {
		href: '/web/seo-studio/hidden-pages',
		label: `${Liferay.Language.get('hidden')} ${Liferay.Language.get('pages')}`,
	},
};

function normalize(url: string): string {
	const trimmed = url.trim();

	if (trimmed.length > 1 && trimmed.endsWith('/')) {
		return trimmed.slice(0, -1);
	}

	return trimmed;
}

async function fetchCrawlSources(): Promise<CrawlSource[]> {
	const response = await fetch('/o/c/crawlsources');

	if (!response.ok) {
		throw new Error(
			`Crawl Sources request failed: ${response.status} ${response.statusText}`
		);
	}

	const payload: {items?: CrawlSource[]} = await response.json();

	return payload.items ?? [];
}

async function fetchCrawledPages(indexName: string): Promise<CrawledPage[]> {
	const response = await fetch(
		`http://localhost:9200/${encodeURIComponent(indexName)}/_search?size=1000`,
		{
			credentials: 'omit',
		}
	);

	if (!response.ok) {
		throw new Error(
			`Elasticsearch request failed: ${response.status} ${response.statusText}`
		);
	}

	const payload: ElasticsearchSearchResponse = await response.json();

	return payload.hits.hits.map((hit) => ({
		id: hit._id,
		lastCrawledAt: hit._source.last_crawled_at || '',
		links: hit._source.links || [],
		title: hit._source.title || '',
		url: hit._source.url || '',
	}));
}

function computeHiddenPages(pages: CrawledPage[]): CrawledPage[] {
	const linkedUrls = new Set<string>();

	pages.forEach((page) => {
		page.links.forEach((link) => {
			linkedUrls.add(normalize(link));
		});
	});

	return pages.filter((page) => !linkedUrls.has(normalize(page.url)));
}

async function fetchInsightsSummary(): Promise<InsightSummaryRow[]> {
	const response = await fetch(
		'/o/c/insights?fields=category,insightType,url&pageSize=-1'
	);

	if (!response.ok) {
		throw new Error(
			`Insights request failed: ${response.status} ${response.statusText}`
		);
	}

	const {items = []}: {items?: InsightApiRow[]} = await response.json();

	const groups = new Map<string, {category: string; urls: Set<string>}>();

	items.forEach(({category, insightType, url}) => {
		const key = `${category}|${insightType}`;
		const group = groups.get(key) ?? {
			category,
			urls: new Set<string>(),
		};

		group.urls.add(url);
		groups.set(key, group);
	});

	return [...groups.entries()].flatMap(([id, {category, urls}]) => {
		const issueLink = ISSUE_LINKS[id];

		if (!issueLink) {
			return [];
		}

		return [
			{
				affectedPages: urls.size,
				category: <ClayLabel displayType="info">{category}</ClayLabel>,
				id,
				issueLink,
			},
		];
	});
}

export default function ViewSiteSEO() {
	const [crawlSources, setCrawlSources] = useState<CrawlSource[]>([]);
	const [selectedSourceId, setSelectedSourceId] = useState<number | null>(
		null
	);
	const [pages, setPages] = useState<CrawledPage[]>([]);
	const [isLoading, setIsLoading] = useState<boolean>(false);
	const [insights, setInsights] = useState<InsightSummaryRow[]>([]);

	useEffect(() => {
		fetchCrawlSources().then((sources) => {
			setCrawlSources(sources);

			if (sources.length) {
				setSelectedSourceId(sources[0].id);
			}
		});

		fetchInsightsSummary().then(setInsights);
	}, []);

	const selectedSource =
		crawlSources.find((source) => source.id === selectedSourceId) ?? null;

	const loadCrawlerData = async () => {
		if (!selectedSource) {
			return;
		}

		setIsLoading(true);

		try {
			const crawledPages = await fetchCrawledPages(
				selectedSource.indexName
			);

			setPages(crawledPages);

			await bulkDeleteHiddenPageInsights();

			await bulkCreateHiddenPageInsights(
				computeHiddenPages(crawledPages)
			);

			setInsights(await fetchInsightsSummary());
		}
		finally {
			setIsLoading(false);
		}
	};

	return (
		<div className="p-4">
			<div className="align-items-center d-flex justify-content-between mb-4">
				<div>
					<div className="small text-muted">
						Site health check
					</div>

					<h2 className="font-weight-bold mb-0">Crawl Results</h2>
				</div>

				<ClayButton
					disabled={isLoading || !selectedSource}
					displayType="primary"
					onClick={loadCrawlerData}
				>
					{isLoading
						? Liferay.Language.get('loading')
						: Liferay.Language.get('run-health-check')}
				</ClayButton>
			</div>

			{!crawlSources.length && (
				<p>{Liferay.Language.get('no-crawl-sources-found')}</p>
			)}

			{!!crawlSources.length && (
				<ClayInput.Group className="mb-3">
					<ClayInput.GroupItem>
						<label htmlFor="crawlSourceSelect">
							{Liferay.Language.get('crawl-source')}
						</label>

						<ClaySelect
							id="crawlSourceSelect"
							onChange={(event) =>
								setSelectedSourceId(Number(event.target.value))
							}
							value={selectedSourceId ?? ''}
						>
							{crawlSources.map((source) => (
								<ClaySelect.Option
									key={source.id}
									label={`${source.url}`}
									value={source.id}
								/>
							))}
						</ClaySelect>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			)}

			{isLoading && (
				<div className="mt-4">
					<ClayLoadingIndicator />
				</div>
			)}

			{!isLoading && !!pages.length && (
				<LastCrawlSummary
					insights={insights}
					pageCount={pages.length}
				/>
			)}

			{!isLoading && !!insights.length && (
				<div className="mt-n4">
					<FrontendDataSet
						id="seoIssuesTable"
						items={insights}
						showPagination={false}
						showSearch={false}
						views={ISSUES_TABLE_VIEWS}
					/>
				</div>
			)}
		</div>
	);
}
