/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {workflowPagesTest} from '../../fixtures/workflowPagesTest';
import getRandomString from '../../utils/getRandomString';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';

export const test = mergeTests(
	featureFlagsTest({
		'LPD-10703': true,
	}),
	journalPagesTest,
	changeTrackingPagesTest,
	workflowPagesTest
);

let journalName;

test.beforeEach(async ({journalEditArticlePage, workflowPage}) => {
	await workflowPage.goto();

	await workflowPage.changeWorkflow('Web Content Article', 'Single Approver');

	journalName = getRandomString();

	await journalEditArticlePage.goto();

	await journalEditArticlePage.submitArticleForWorkflow(journalName);
});

test('LPD-19748 Add workflow info to the View Change screen', async ({
	changeTrackingPage,
	ctCollection,
	page,
}) => {
	await changeTrackingPage.goToReviewChanges(ctCollection.name);

	await changeTrackingPage.reviewChange(journalName);

	await expect(page.getByText(`Pending`)).toBeVisible();

	await changeTrackingPage.viewDisplayTab('Workflow');
});

test('LPD-19748 Workflow data is displayed in tab', async ({
	changeTrackingPage,
	ctCollection,
	page,
}) => {
	const displayData = [
		'Status',
		'Assigned to',
		'Task Name',
		'Create Date',
		'Due Date',
	];

	await changeTrackingPage.goToReviewChanges(ctCollection.name);

	await changeTrackingPage.reviewChange(journalName);

	await changeTrackingPage.selectTab('Workflow');

	for (const data of displayData) {
		await expect(page.getByText(data, {exact: true})).toBeVisible();
	}
});

test('LPD-19748 Only workflow status is displayed when workflow is disabled', async ({
	changeTrackingPage,
	ctCollection,
	page,
	workflowPage,
}) => {
	await workflowPage.goto();

	await workflowPage.changeWorkflow('Web Content Article', 'No Workflow', {
		disable: true,
	});

	await changeTrackingPage.goToReviewChanges(ctCollection.name);

	await changeTrackingPage.reviewChange(journalName);

	await expect(page.getByText(`Pending`)).toBeVisible();
	await changeTrackingPage.viewDisplayTab('Workflow', {isHidden: true});
});

test('LPD-19763 Workflow assign actions are displayed in dropdown', async ({
	changeTrackingPage,
	ctCollection,
	page,
}) => {
	await changeTrackingPage.goToReviewChanges(ctCollection.name);

	await changeTrackingPage.reviewChange(journalName);

	await expect(page.getByText(`Pending`)).toBeVisible();

	const button = page.getByLabel('more-actions');

	await button.click();

	await expect(
		page.getByRole('menuitem', {
			name: 'Assign to me',
		})
	).toBeVisible();

	await expect(
		page.getByRole('menuitem', {
			name: 'Assign to...',
		})
	).toBeVisible();
});

test('LPD-19763 Workflow review actions are displayed in dropdown', async ({
	changeTrackingPage,
	ctCollection,
	page,
}) => {
	await changeTrackingPage.goToReviewChanges(ctCollection.name);

	await changeTrackingPage.reviewChange(journalName);

	await expect(page.getByText(`Pending`)).toBeVisible();

	const moreActionsbutton = page.getByLabel('more-actions');

	await moreActionsbutton.click();

	const assignToMeMenuItem = page.getByRole('menuitem', {
		name: 'Assign to me',
	});

	await assignToMeMenuItem.click();

	await expect(page.getByText(`CloseAssign to Me`)).toBeVisible();

	const doneButton = page
		.frameLocator('iframe[title="Assign to Me"]')
		.getByRole('button', {exact: true, name: 'Done'});

	await doneButton.click();

	await moreActionsbutton.click();

	await expect(
		page.getByRole('menuitem', {
			name: 'Approve',
		})
	).toBeVisible();

	await expect(
		page.getByRole('menuitem', {
			name: 'Reject',
		})
	).toBeVisible();

	await expect(
		page.getByRole('menuitem', {
			name: 'Assign to...',
		})
	).toBeVisible();
});
