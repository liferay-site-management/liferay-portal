/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen, waitFor} from '@testing-library/react';
import {fetch} from 'frontend-js-web';
import React from 'react';

import Integrations from '../../../js/integrations/Integrations';

jest.mock('frontend-js-web', () => ({
	fetch: jest.fn(),
}));

jest.mock('@liferay/frontend-data-set-web', () => ({
	FrontendDataSet: () => <div data-testid="frontend-data-set" />,
}));

type IntegrationsProps = React.ComponentProps<typeof Integrations>;

function renderIntegrations(props: Partial<IntegrationsProps> = {}) {
	return render(
		<Integrations
			apiURL="/o/seo-studio/instances"
			fdsId="integrations"
			itemsActions={[]}
			views={[]}
			{...props}
		/>
	);
}

function mockFetch(items: any[]) {
	(fetch as jest.Mock).mockResolvedValue({
		json: () => Promise.resolve({items}),
		ok: true,
	});
}

describe('Integrations', () => {
	beforeEach(() => {
		(fetch as jest.Mock).mockClear();
	});

	it('renders the empty state when no items are returned', async () => {
		mockFetch([]);

		renderIntegrations();

		await waitFor(() => {
			expect(screen.getByText('no-integration-yet')).toBeInTheDocument();
		});

		expect(
			screen.getByText('add-your-first-integration')
		).toBeInTheDocument();

		expect(
			screen.queryByTestId('frontend-data-set')
		).not.toBeInTheDocument();
	});

	it('renders the data set when items are returned', async () => {
		mockFetch([{id: 1, name: 'one'}]);

		renderIntegrations();

		await waitFor(() => {
			expect(screen.getByTestId('frontend-data-set')).toBeInTheDocument();
		});

		expect(
			screen.queryByText('no-integration-yet')
		).not.toBeInTheDocument();
	});

	it('renders the Add Integration trigger', () => {
		mockFetch([]);

		renderIntegrations();

		expect(
			screen.getByRole('button', {name: /add-integration/})
		).toBeInTheDocument();
	});

	it('falls back to an empty list when the fetch fails', async () => {
		(fetch as jest.Mock).mockRejectedValue(new Error('network error'));

		renderIntegrations();

		await waitFor(() => {
			expect(screen.getByText('no-integration-yet')).toBeInTheDocument();
		});
	});

	it('falls back to an empty list on a non-ok HTTP response', async () => {
		(fetch as jest.Mock).mockResolvedValue({
			json: () => Promise.resolve({items: [{id: 1}]}),
			ok: false,
		});

		renderIntegrations();

		await waitFor(() => {
			expect(screen.getByText('no-integration-yet')).toBeInTheDocument();
		});

		expect(
			screen.queryByTestId('frontend-data-set')
		).not.toBeInTheDocument();
	});
});
