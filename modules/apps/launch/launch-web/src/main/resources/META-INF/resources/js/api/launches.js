/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

const BASE_URL = '/o/launch-plans';

const DEFAULT_HEADERS = {
	'Content-Type': 'application/json',
};

export async function createLaunch({description, name}) {
	const response = await fetch(BASE_URL, {
		body: JSON.stringify({description, name, status: {code: 2}}),
		headers: DEFAULT_HEADERS,
		method: 'POST',
	});

	if (!response.ok) {
		const error = await response.json().catch(() => ({}));

		throw new Error(
			error.title || `Unable to create launch (HTTP ${response.status})`
		);
	}

	return response.json();
}

export async function getLaunch(id) {
	const response = await fetch(`${BASE_URL}/${id}`, {
		headers: DEFAULT_HEADERS,
	});

	if (!response.ok) {
		throw new Error(
			`Unable to load launch ${id} (HTTP ${response.status})`
		);
	}

	return response.json();
}

export async function listLaunches({pageSize = 50} = {}) {
	const response = await fetch(`${BASE_URL}?pageSize=${pageSize}`, {
		headers: DEFAULT_HEADERS,
	});

	if (!response.ok) {
		throw new Error(`Unable to list launches (HTTP ${response.status})`);
	}

	const data = await response.json();

	return data.items || [];
}
