/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {fireEvent, render, screen, waitFor} from '@testing-library/react';
import {openToast} from 'frontend-js-components-web';
import React from 'react';

import Integrations from '../../../js/integrations/Integrations';

jest.mock('@liferay/frontend-data-set-web', () => ({
	FrontendDataSet: jest.fn(() => <div data-testid="frontend-data-set" />),
}));

jest.mock('frontend-js-components-web', () => ({
	openToast: jest.fn(),
}));

type IntegrationsProps = React.ComponentProps<typeof Integrations>;

function renderIntegrations(props: Partial<IntegrationsProps> = {}) {
	return render(
		<Integrations
			fdsId="integrations"
			integrationTypes={[]}
			integrationsURL="/o/seo-studio/integrations"
			items={[]}
			itemsActions={[]}
			views={[]}
			{...props}
		/>
	);
}

beforeEach(() => {
	(FrontendDataSet as jest.Mock).mockClear();
	(openToast as jest.Mock).mockClear();

	(Liferay.Util as unknown) = {
		fetch: jest.fn(),
	};

	delete (window as any).location;

	(window as any).location = {
		assign: jest.fn(),
		href: '',
		reload: jest.fn(),
	};
});

describe('Integrations', () => {
	it('renders the empty state when no items are provided', () => {
		renderIntegrations();

		expect(screen.getByText('no-integration-yet')).toBeInTheDocument();
		expect(
			screen.getByText('add-your-first-integration')
		).toBeInTheDocument();

		expect(
			screen.queryByTestId('frontend-data-set')
		).not.toBeInTheDocument();
	});

	it('renders the data set when items are provided', () => {
		renderIntegrations({items: [{id: 'a', name: 'one'}]});

		expect(screen.getByTestId('frontend-data-set')).toBeInTheDocument();

		expect(
			screen.queryByText('no-integration-yet')
		).not.toBeInTheDocument();
	});

	it('renders the Add Integration trigger', () => {
		renderIntegrations();

		expect(
			screen.getByRole('button', {name: /add-integration/})
		).toBeInTheDocument();
	});

	it('renders an enabled Add Integration menu item for an unconfigured type', () => {
		renderIntegrations({
			integrationTypes: [
				{
					configurationURL: '/back/here',
					disabled: false,
					id: 'a',
					name: 'Display Name',
				},
			],
		});

		fireEvent.click(screen.getByRole('button', {name: /add-integration/}));

		const link = screen.getByRole('menuitem', {name: 'Display Name'});

		expect(link).toHaveAttribute('href', '/back/here');
	});

	it('renders a disabled Add Integration menu item for a configured type', () => {
		renderIntegrations({
			integrationTypes: [
				{
					configurationURL: '/back/here',
					disabled: true,
					id: 'a',
					name: 'Display Name',
				},
			],
		});

		fireEvent.click(screen.getByRole('button', {name: /add-integration/}));

		const item = screen.getByRole('menuitem', {name: 'Display Name'});

		expect(item).not.toHaveAttribute('href');
	});

	it('navigates to the row configuration URL on Edit action click', () => {
		renderIntegrations({
			items: [{configurationURL: '/back/here', id: 'a'}],
		});

		const fdsProps = (FrontendDataSet as jest.Mock).mock.calls[0][0];

		fdsProps.onActionDropdownItemClick({
			action: {data: {id: 'edit'}},
			itemData: {configurationURL: '/back/here'},
		});

		expect(window.location.assign).toHaveBeenCalledWith('/back/here');
	});

	it('deletes the integration row and reloads on Remove action click', async () => {
		const fetchMock = jest.fn().mockResolvedValue({ok: true});

		(Liferay.Util as unknown) = {fetch: fetchMock};

		renderIntegrations({
			integrationsURL: '/o/seo-studio/integrations',
			items: [{id: 42}],
		});

		const fdsProps = (FrontendDataSet as jest.Mock).mock.calls[0][0];

		fdsProps.onActionDropdownItemClick({
			action: {data: {id: 'remove'}},
			itemData: {id: 42},
		});

		await waitFor(() => {
			expect(window.location.reload).toHaveBeenCalled();
		});

		expect(fetchMock).toHaveBeenCalledWith(
			'/o/seo-studio/integrations/42',
			{method: 'DELETE'}
		);

		expect(openToast).toHaveBeenCalledWith(
			expect.objectContaining({type: 'success'})
		);
	});

	it('shows an error toast when the Remove DELETE fails', async () => {
		const fetchMock = jest.fn().mockResolvedValue({ok: false});

		(Liferay.Util as unknown) = {fetch: fetchMock};

		renderIntegrations({items: [{id: 42}]});

		const fdsProps = (FrontendDataSet as jest.Mock).mock.calls[0][0];

		fdsProps.onActionDropdownItemClick({
			action: {data: {id: 'remove'}},
			itemData: {id: 42},
		});

		await waitFor(() => {
			expect(openToast).toHaveBeenCalledWith(
				expect.objectContaining({type: 'danger'})
			);
		});

		expect(window.location.reload).not.toHaveBeenCalled();
	});
});
