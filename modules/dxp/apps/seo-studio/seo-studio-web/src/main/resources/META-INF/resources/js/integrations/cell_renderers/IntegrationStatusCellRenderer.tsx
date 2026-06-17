/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import React from 'react';

interface Props {
	itemData?: {
		state?: {
			key?: string;
			name?: string;
		};
	};
}

export default function IntegrationStatusCellRenderer({itemData}: Props) {
	const state = itemData?.state;

	if (!state) {
		return null;
	}

	const displayType = state.key === 'active' ? 'success' : 'secondary';

	return (
		<ClayLabel displayType={displayType}>
			{state.name?.toUpperCase()}
		</ClayLabel>
	);
}
