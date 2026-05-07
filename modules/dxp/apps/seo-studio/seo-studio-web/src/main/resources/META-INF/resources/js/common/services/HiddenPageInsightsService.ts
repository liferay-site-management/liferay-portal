/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

type HiddenPageInsightInput = {
	title: string;
	url: string;
};

async function awaitImportTask(taskId: number): Promise<void> {
	while (true) {
		const response = await fetch(
			`/o/headless-batch-engine/v1.0/import-task/${taskId}`
		);

		if (!response.ok) {
			throw new Error(
				`Import task ${taskId} lookup failed: ${response.status} ${response.statusText}`
			);
		}

		const {executeStatus}: {executeStatus: string} = await response.json();

		if (executeStatus === 'STARTED' || executeStatus === 'INITIAL') {
			await new Promise((resolve) => setTimeout(resolve, 500));

			continue;
		}

		if (executeStatus === 'COMPLETED') {
			return;
		}

		throw new Error(`Import task ${taskId} failed: ${executeStatus}`);
	}
}

export async function bulkDeleteHiddenPageInsights(): Promise<void> {
	const response = await fetch(
		`/o/c/insights?fields=id&pageSize=-1&filter=${encodeURIComponent("insightType eq 'hidden_page'")}`
	);

	const {items = []}: {items?: Array<{id: number}>} = await response.json();

	if (!items.length) {
		return;
	}

	const batchResponse = await fetch('/o/c/insights/batch', {
		body: JSON.stringify(items.map(({id}) => ({id}))),
		headers: {
			'Content-Type': 'application/json',
		},
		method: 'DELETE',
	});

	const {id: taskId}: {id: number} = await batchResponse.json();

	await awaitImportTask(taskId);
}

export async function bulkCreateHiddenPageInsights(
	pages: HiddenPageInsightInput[]
): Promise<void> {
	if (!pages.length) {
		return;
	}

	const response = await fetch('/o/c/insights/batch', {
		body: JSON.stringify(
			pages.map(({title, url}) => ({
				category: 'crawlability',
				insightType: 'hidden_page',
				title,
				url,
			}))
		),
		headers: {
			'Content-Type': 'application/json',
		},
		method: 'POST',
	});

	const {id: taskId}: {id: number} = await response.json();

	await awaitImportTask(taskId);
}
