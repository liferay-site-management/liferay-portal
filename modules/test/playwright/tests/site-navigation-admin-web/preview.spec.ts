/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {displayPageTemplatesPagesTest} from '../../fixtures/displayPageTemplatesPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageSelectorPagesTest} from '../../fixtures/pageSelectorPagesTest';
import {pageViewModePagesTest} from '../../fixtures/pageViewModePagesTest';
import {createCategories} from '../../helpers/CreateCategories';
import getRandomString from '../../utils/getRandomString';
import getBasicWebContentStructureId from '../../utils/structured-content/getBasicWebContentStructureId';
import getFragmentDefinition from '../layout-content-page-editor-web/utils/getFragmentDefinition';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import {navigationMenusPagesTest} from './fixtures/navigationMenusPagesTest';

const test = mergeTests(
	apiHelpersTest,
	displayPageTemplatesPagesTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	navigationMenusPagesTest,
	pageSelectorPagesTest,
	pageViewModePagesTest
);

test('Ensure that the navigation menu admin cannot view empty vocabulary on preview menu', async ({
	apiHelpers,
	navigationMenusPage,
	page,
	site,
}) => {

	// Create Categories and Vocabularies

	const categoryName = getRandomString();

	const vocabularyName1 = getRandomString();

	const vocabularyName2 = getRandomString();

	await createCategories({
		apiHelpers,
		categoryNames: [{name: categoryName}],
		siteId: site.id,
		vocabularyName: vocabularyName1,
	});

	await apiHelpers.headlessAdminTaxonomy.postSiteTaxonomyVocabulary({
		name: vocabularyName2,
		siteId: site.id,
	});

	// Create a Navigation Menu and add Vocabularies to it

	await navigationMenusPage.goto(site.friendlyUrlPath);

	await navigationMenusPage.createNavigationMenu(getRandomString());

	await navigationMenusPage.openAddVocabularyModal();

	await navigationMenusPage.vocabulariesModal
		.getByLabel(vocabularyName1)
		.check();

	await navigationMenusPage.vocabulariesModal
		.getByLabel(vocabularyName2)
		.check();

	await navigationMenusPage.selectButton.click();

	// Assert that the alert icon could be viewed only in on the empty vocabulary item

	await page.getByText(vocabularyName1, {exact: true}).click();

	await expect(page.getByText('No categories inside')).not.toBeVisible();

	await page.getByText(vocabularyName2, {exact: true}).click();

	await expect(page.getByText('No categories inside')).toBeVisible();

	// Assert that, when the preview option is selected, only the non-empty category could be viewed in List Menu mode

	await page.getByRole('button', {name: 'Preview'}).click();

	await page.getByLabel('Display Template').selectOption('LIST-MENU-FTL');

	await expect(
		page
			.frameLocator('iframe')
			.getByRole('listitem')
			.getByText(categoryName)
	).toBeVisible();
});

test('Ensure that the navigation menu admin could choose display template when preview navigation menu', async ({
	apiHelpers,
	navigationMenusPage,
	page,
	site,
}) => {

	// Create a Page and add a Heading Fragment to it

	const pageName = getRandomString();

	await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getFragmentDefinition({
				id: getRandomString(),
				key: 'BASIC_COMPONENT-heading',
			}),
		]),
		siteId: site.id,
		title: pageName,
	});

	// Create a Navigation Menu and Add the Page to it

	await navigationMenusPage.goto(site.friendlyUrlPath);

	await navigationMenusPage.createNavigationMenu(getRandomString());

	await navigationMenusPage.openAddPageModal();

	await navigationMenusPage.pagesModal
		.getByText(pageName, {exact: true})
		.click();

	await navigationMenusPage.selectButton.click();

	// Assert that, when the preview option is selected, the default option for the display template is Bar minimally styled

	await page.getByRole('button', {name: 'Preview'}).click();

	await expect(page.getByLabel('Display Template')).toHaveValue(
		'NAVBAR-BLANK-FTL'
	);

	// Assert that, when the List Menu option is selected, the item is displayed as List Menu

	await page.getByLabel('Display Template').selectOption('LIST-MENU-FTL');

	await expect(
		page.frameLocator('iframe').getByRole('listitem').getByText(pageName)
	).toBeVisible();
});

test('Ensure that the navigation menu admin could preview menu in navigation menu editor', async ({
	apiHelpers,
	displayPageTemplatesPage,
	navigationMenusPage,
	page,
	site,
}) => {

	// Create a Display Page Template for a Basic Web Content and add a Widget to it

	await displayPageTemplatesPage.goto(site.friendlyUrlPath);

	const webContentDisplayPageTemplateName = getRandomString();

	await displayPageTemplatesPage.createTemplate({
		contentSubtype: 'Basic Web Content',
		contentType: 'Web Content Article',
		name: webContentDisplayPageTemplateName,
	});

	await navigationMenusPage.addWidgetToPageTemplate(
		webContentDisplayPageTemplateName
	);

	// Create a Display Page Template for a Blog and add a Widget to it

	const blogsDisplayPageTemplateName = getRandomString();

	await displayPageTemplatesPage.createTemplate({
		contentType: 'Blogs Entry',
		name: blogsDisplayPageTemplateName,
	});

	await navigationMenusPage.addWidgetToPageTemplate(
		blogsDisplayPageTemplateName
	);

	// Create a Blog

	const blogTitle = getRandomString();

	await apiHelpers.headlessDelivery.postBlog(site.id, {
		headline: blogTitle,
	});

	// Create a Web Content

	const journalArticleTitle = getRandomString();

	await apiHelpers.headlessDelivery.postStructuredContent({
		contentStructureId: await getBasicWebContentStructureId(apiHelpers),
		datePublished: null,
		siteId: site.id,
		title: journalArticleTitle,
	});

	// Create a Navigation Menu and add Items to it

	await navigationMenusPage.goto(site.friendlyUrlPath);

	await navigationMenusPage.createNavigationMenu(getRandomString());

	// Add a Submenu Item

	const submenuItemName = getRandomString();

	await navigationMenusPage.addSubmenuItem(submenuItemName);

	// Add a URL Item

	const urlName = getRandomString();

	await page
		.locator('p.card-title')
		.filter({hasText: submenuItemName})
		.hover();

	await page.getByLabel('View ' + submenuItemName + ' Options').click();

	await page.locator('#clay-dropdown-menu-4').getByText('Add Child').hover();

	await page
		.locator(
			'div:nth-child(2) > div > .dropdown-menu > .list-unstyled > li:nth-child(9) > .dropdown-item'
		)
		.first()
		.click();

	await page.waitForTimeout(1000);

	await navigationMenusPage.urlModal
		.getByPlaceholder('http://')
		.fill('https://www.liferay.com');

	await navigationMenusPage.urlModal.getByPlaceholder('Name').fill(urlName);

	await navigationMenusPage.urlModal
		.getByRole('button', {name: 'Add'})
		.click();

	// Add a Blog Item

	await navigationMenusPage.addBlogItem(blogTitle);

	// Add a Web Content Item

	await navigationMenusPage.addWebContentArticleItem(journalArticleTitle);

	// Open the Preview

	await page.getByRole('button', {name: 'Preview'}).click();

	// Expect to see the previously created items in the preview modal

	await expect(
		page
			.frameLocator('iframe')
			.getByRole('menuitem', {name: submenuItemName})
	).toBeVisible();

	await expect(
		page.frameLocator('iframe').getByRole('menuitem', {name: blogTitle})
	).toBeVisible();

	await expect(
		page
			.frameLocator('iframe')
			.getByRole('menuitem', {name: journalArticleTitle})
	).toBeVisible();

	await page
		.frameLocator('iframe')
		.getByRole('menuitem', {name: submenuItemName})
		.hover();

	await expect(
		page.frameLocator('iframe').getByRole('menuitem', {name: urlName})
	).toBeVisible();
});
