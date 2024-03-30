/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {workflowPagesTest} from '../../fixtures/workflowPagesTest';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	journalPagesTest,
	changeTrackingPagesTest,
	workflowPagesTest,
	loginTest()
);

test('LPD-19748 Add workflow info to the View Change screen', async ({
	apiHelpers,
	changeTrackingPage,
	journalEditArticlePage,
	page,
	workflowPage,
}) => {
	await workflowPage.goto();

	await workflowPage.changeWorkflow('Web Content Article', 'Single Approver');

	await changeTrackingPage.enablePublications();

	const ctCollection =
		await apiHelpers.headlessChangeTracking.createCTCollection(
			'Publication Name'
		);

	await apiHelpers.headlessChangeTracking.checkoutCTCollection(
		ctCollection.id
	);

	await journalEditArticlePage.goto();

	await journalEditArticlePage.fillTitle('Basic Web Content Article');

	const submitForWorkflowButton = page.getByRole('button', {
		name: 'Submit for Workflow',
	});

	await submitForWorkflowButton.click();

	await page
		.locator(
			'#_com_liferay_journal_web_portlet_JournalPortlet_articlesSearchContainer .list-group-item'
		)
		.filter({hasText: 'Basic Web Content Article'})
		.waitFor();

	await changeTrackingPage.goToReviewChanges('Publication Name');

	await page
		.getByRole('link', {name: 'Basic Web Content Article'})
		.first()
		.click();

	await changeTrackingPage.selectTab('Data');

	await page.getByText('Create Date', {exact: true}).isVisible();

	await apiHelpers.headlessChangeTracking.deleteCTCollection(ctCollection.id);

	await workflowPage.goto();

	await workflowPage.changeWorkflow('Web Content Article', 'No Workflow', {
		disable: true,
	});
});
