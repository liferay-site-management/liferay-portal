/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import getRandomString from '../../utils/getRandomString';
import {waitForAlert} from '../../utils/waitForAlert';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	changeTrackingPagesTest,
	dataApiHelpersTest,
	journalPagesTest
);

test('LPD-48185 Can discard web content created and modified in Publication', async ({
	changeTrackingPage,
	ctCollection,
	journalEditArticlePage,
	journalPage,
	page,
	site,
}) => {
	await journalEditArticlePage.goto({siteUrl: site.friendlyUrlPath});

	const title1 = getRandomString();

	await journalEditArticlePage.fillTitle(title1);

	await journalEditArticlePage.publishArticle();

	await page.waitForTimeout(300);

	await journalPage.goto(site.friendlyUrlPath);

	await page.getByText(title1).click();

	const title2 = getRandomString();

	await journalEditArticlePage.fillTitle(title2);

	await page.getByLabel('Select and Confirm Publish').click();

	await page.getByRole('menuitem', {name: 'Publish'}).click();

	await waitForAlert(page, `Success:${title2} was updated successfully.`);

	await changeTrackingPage.goToReviewChanges(ctCollection.body.name);

	await changeTrackingPage.reviewChange(title2);

	await page.getByLabel('more-actions').click();

	await page.getByRole('menuitem', {name: 'Discard'}).click();

	await page.getByLabel('Items Per Page').click();

	await page.getByRole('option', {name: '40 items'}).click();

	await expect(
		page.getByRole('cell', {exact: true, name: 'Web Content Article'})
	).toBeVisible();

	await page.getByRole('button', {name: 'Discard'}).click();

	await changeTrackingPage.goToReviewChanges(ctCollection.body.name);

	await expect(page.getByText('No Results Found')).toBeVisible();

	await journalPage.goto(site.friendlyUrlPath);

	await expect(page.getByRole('link', {name: title2})).toBeHidden();
});

test('LPD-48185 Can discard web content created in Production and modified in Publication', async ({
	changeTrackingPage,
	ctCollection,
	journalEditArticlePage,
	journalPage,
	page,
	site,
}) => {
	await changeTrackingPage.workOnProduction();

	await journalEditArticlePage.goto({siteUrl: site.friendlyUrlPath});

	const title1 = getRandomString();

	await journalEditArticlePage.fillTitle(title1);

	await journalEditArticlePage.publishArticle();

	await page.waitForTimeout(500);

	await changeTrackingPage.workOnPublication(ctCollection);

	await page.getByText(title1).click();

	const title2 = getRandomString();

	await journalEditArticlePage.fillTitle(title2);

	await page.getByLabel('Select and Confirm Publish').click();

	await page.getByRole('menuitem', {name: 'Publish'}).click();

	await waitForAlert(page, `Success:${title2} was updated successfully.`);

	await changeTrackingPage.goToReviewChanges(ctCollection.body.name);

	await changeTrackingPage.reviewChange(title2);

	await page.getByLabel('more-actions').click();

	await page.getByRole('menuitem', {name: 'Discard'}).click();

	await expect(
		page.getByRole('cell', {exact: true, name: 'Web Content Article'})
	).toBeVisible();

	await page.getByRole('button', {name: 'Discard'}).click();

	await changeTrackingPage.goToReviewChanges(ctCollection.body.name);

	await expect(page.getByText('No Results Found')).toBeVisible();

	await journalPage.goto(site.friendlyUrlPath);

	await expect(page.getByRole('link', {name: title1})).toBeVisible();
});

test('LPD-48185 Journal Article is added as system change', async ({
	changeTrackingPage,
	ctCollection,
	journalEditArticlePage,
	page,
	site,
}) => {
	await journalEditArticlePage.goto({siteUrl: site.friendlyUrlPath});

	const title = getRandomString();

	await journalEditArticlePage.fillTitle(title);

	await journalEditArticlePage.publishArticle();

	await page.waitForTimeout(300);

	await changeTrackingPage.goToReviewChanges(ctCollection.body.name);

	const webContentArticleCell = page.getByRole('cell', {
		exact: true,
		name: 'Web Content Article',
	});

	await expect(webContentArticleCell).toBeHidden();

	await page.getByLabel('More Actions').click();

	await page.getByRole('menuitem', {name: 'Show System Changes'}).click();

	await expect(webContentArticleCell).toBeVisible();
});

test('LPD-48185 Journal Article Resource is displayed with Journal Article informations', async ({
	changeTrackingPage,
	ctCollection,
	journalEditArticlePage,
	page,
	site,
}) => {
	await journalEditArticlePage.goto({siteUrl: site.friendlyUrlPath});

	const title = getRandomString();

	await journalEditArticlePage.fillTitle(title);

	await journalEditArticlePage.publishArticle();

	await page.waitForTimeout(300);

	await changeTrackingPage.goToReviewChanges(ctCollection.body.name);

	await expect(
		page.getByRole('cell', {name: 'Web Content Article Resource'})
	).toBeVisible();

	await changeTrackingPage.reviewChange(title);

	await changeTrackingPage.selectTab('Data');

	const displayData = [
		'Name',
		'Description',
		'Created By',
		'Create Date',
		'Last Modified	',
		'Version',
		'Structure',
		'Template',
	];

	for (const data of displayData) {
		await expect(page.getByText(data, {exact: true})).toBeVisible();
	}

	await changeTrackingPage.selectTab('Parents');

	await expect(
		page.getByText('Web Content Article', {exact: true})
	).toBeHidden();

	await changeTrackingPage.selectTab('Children');

	await expect(
		page.getByRole('cell', {exact: true, name: 'Permissions'})
	).toBeVisible();

	await expect(
		page.getByRole('cell', {exact: true, name: 'Web Content Article'})
	).toBeVisible();

	await expect(page.getByRole('table').getByText(title)).toBeVisible();
});
