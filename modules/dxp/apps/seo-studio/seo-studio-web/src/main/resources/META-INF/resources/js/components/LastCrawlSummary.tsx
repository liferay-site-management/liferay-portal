/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import type {InsightSummaryRow} from '../common/types/InsightSummaryRow';

type Props = {
	insights: InsightSummaryRow[];
	pageCount: number;
};

const ISSUE_SCORING: Record<
	string,
	{maxDeduction: number; severityWeight: number}
> = {
	'crawlability|hidden_page': {maxDeduction: 30, severityWeight: 1},
};

export default function LastCrawlSummary({insights, pageCount}: Props) {
	if (!pageCount) {
		return null;
	}

	const totalIssues = insights.reduce(
		(sum, insight) => sum + insight.affectedPages,
		0
	);

	const deduction = insights.reduce((sum, {affectedPages, id}) => {
		const scoring = ISSUE_SCORING[id];

		if (!scoring) {
			return sum;
		}

		return (
			sum +
			scoring.severityWeight *
				(affectedPages / pageCount) *
				scoring.maxDeduction
		);
	}, 0);

	const healthScore = Math.max(0, Math.round(100 - deduction));

	const tiles = [
		{label: 'Pages scanned', value: pageCount},
		{label: 'Total issues', value: totalIssues},
		{
			label: 'Health score',
			value: (
				<>
					{healthScore}
					<span className="text-muted">{' / 100'}</span>
				</>
			),
		},
		{label: 'Critical', value: <span className="text-danger">{0}</span>},
	];

	return (
		<div className="d-flex flex-wrap mt-4">
			{tiles.map(({label, value}) => (
				<div
					className="border flex-fill mb-3 mr-3 p-3 rounded"
					key={label}
				>
					<div className="small text-muted text-uppercase">
						{label}
					</div>

					<div className="font-weight-bold h3 mb-0 mt-2">{value}</div>
				</div>
			))}
		</div>
	);
}
