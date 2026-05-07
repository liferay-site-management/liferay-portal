/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

type Insight = {
	category: string;
	id: number;
	insightType: string;
	title: string;
	url: string;
};

const TABLE_VIEWS = [
	{
		contentRenderer: 'table',
		name: 'table',
		schema: {
			fields: [
				{
					fieldName: 'title',
					label: Liferay.Language.get('title'),
				},
				{
					fieldName: 'url',
					label: Liferay.Language.get('url'),
				},
			],
		},
	},
];

async function fetchHiddenPageInsights(): Promise<Insight[]> {
	const response = await fetch(
		"/o/c/insights?filter=insightType eq 'hidden_page'&pageSize=200"
	);

	if (!response.ok) {
		throw new Error(
			`Insights request failed: ${response.status} ${response.statusText}`
		);
	}

	const payload: {items?: Insight[]} = await response.json();

	return payload.items ?? [];
}

export default function ViewHiddenPages() {
	const [insights, setInsights] = useState<Insight[]>([]);
	const [isLoading, setIsLoading] = useState<boolean>(true);

	useEffect(() => {
		fetchHiddenPageInsights()
			.then(setInsights)
			.catch((error: unknown) => {
				console.error('Failed to load hidden page insights:', error);
			})
			.finally(() => {
				setIsLoading(false);
			});
	}, []);

	return (
		<div className="p-4">
			<h2>{`${Liferay.Language.get('hidden')} ${Liferay.Language.get('pages')}`}</h2>

			{isLoading && (
				<div className="mt-4">
					<ClayLoadingIndicator />
				</div>
			)}

			{!isLoading &&
				(insights.length ? (
					<FrontendDataSet
						id="hiddenPagesTable"
						items={insights}
						pagination={{initialDelta: 20}}
						showPagination={true}
						showSearch={false}
						views={TABLE_VIEWS}
					/>
				) : (
					<p>{Liferay.Language.get('no-results-were-found')}</p>
				))}
		</div>
	);
}
