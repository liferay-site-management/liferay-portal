/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {ReactNode} from 'react';

export type InsightSummaryRow = {
	affectedPages: number;
	category: ReactNode;
	id: string;
	issueLink: {
		href: string;
		label: string;
	};
};
