/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class ProcessBuilderPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly page: Page;
	readonly processBuilderConfigurationTab: Locator;
	readonly searchInput: Locator;
	readonly searchResultText: Locator;
	readonly searchSubmit: Locator;
	readonly submitForWorkflowButton: Locator;
	readonly workflowDefinitionLinkCancelButton: Locator;
	readonly workflowDefinitionLinkEditButton: Locator;
	readonly workflowDefinitionLinkSaveButton: Locator;
	readonly workflowDefinitionLinkSelectButton: Locator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.page = page;
		this.processBuilderConfigurationTab = page.getByRole('link', {
			name: 'Configuration',
		});
		this.searchInput = page
			.locator(
				'#_com_liferay_portal_workflow_web_portlet_ControlPanelWorkflowPortlet_fm_search'
			)
			.getByPlaceholder(/^Search/);
		this.searchResultText = page.getByTestId('searchResultText');
		this.searchSubmit = page
			.locator(
				'#_com_liferay_portal_workflow_web_portlet_ControlPanelWorkflowPortlet_fm_search'
			)
			.getByLabel('Search', {exact: true});
		this.submitForWorkflowButton = page.getByRole('link', {
			name: 'Submit for Workflow',
		});
		this.workflowDefinitionLinkCancelButton = page.getByRole('button', {
			name: 'Cancel',
		});
		this.workflowDefinitionLinkEditButton = page.getByRole('button', {
			name: 'Edit',
		});
		this.workflowDefinitionLinkSaveButton = page.getByRole('button', {
			name: 'Save',
		});
		this.workflowDefinitionLinkSelectButton = page.getByTitle(
			'Workflow Definition'
		);
	}

	async disableSingleApproverWorkflow(assetType: string) {
		await this.goToProcessBuilderConfigurationTab();
		await this.searchInput.fill(assetType);
		await this.searchSubmit.click();
		await this.searchResultText.waitFor({
			state: 'attached',
		});
		await this.workflowDefinitionLinkEditButton.click();
		await this.workflowDefinitionLinkSelectButton.selectOption(
			'No Workflow'
		);
		await this.workflowDefinitionLinkSaveButton.click();
	}

	async enableSingleApproverWorkflow(assetType: string) {
		await this.goToProcessBuilderConfigurationTab();
		await this.searchInput.fill(assetType);
		await this.searchSubmit.click();
		await this.searchResultText.waitFor({
			state: 'attached',
		});
		await this.workflowDefinitionLinkEditButton.click();
		await this.workflowDefinitionLinkSelectButton.selectOption(
			'Single Approver'
		);
		await this.workflowDefinitionLinkSaveButton.click();
	}

	async goToProcessBuilderConfigurationTab() {
		await this.applicationsMenuPage.goToProcessBuilder();
		await Promise.all([
			this.processBuilderConfigurationTab.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp
						.url()
						.includes(
							'_com_liferay_portal_workflow_web_portlet_ControlPanelWorkflowPortlet_tab=configuration'
						)
			),
		]);
	}
}
