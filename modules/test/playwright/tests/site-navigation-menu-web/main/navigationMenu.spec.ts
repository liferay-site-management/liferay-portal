/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {customFieldsPagesTest} from '../../../fixtures/customFieldsPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../../fixtures/pageViewModePagesTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import {TCustomField} from '../../../helpers/CustomFieldTypesHelper';
import getRandomString from '../../../utils/getRandomString';
import {pagesPagesTest} from '../../layout-admin-web/main/fixtures/pagesPagesTest';
import {templatesPageTest} from '../../template-web/main/fixtures/templatesPageTest';
import {navigationMenusPagesTest} from './../../site-navigation-admin-web/main/fixtures/navigationMenusPagesTest';
import {navigationMenuWidgetPagesTest} from './fixtures/navigationMenuWidgetPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	customFieldsPagesTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	navigationMenusPagesTest,
	pagesAdminPagesTest,
	pagesPagesTest,
	templatesPageTest,
	pageViewModePagesTest,
	navigationMenuWidgetPagesTest
);

test(
	'Select page as root menu item for Navigation Menu widget',
	{
		tag: '@LPD-50258',
	},
	async ({apiHelpers, page, site, widgetPagePage}) => {
		const parentLayout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		const childLayout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			parentLayoutId: parentLayout.layoutId,
			title: getRandomString(),
		});

		await widgetPagePage.goto(parentLayout, site.friendlyUrlPath);

		await widgetPagePage.clickOnAction('Menu Display', 'Configuration');

		const configurationIFrame = page.frameLocator(
			'iframe[title*="Menu Display"]'
		);

		await configurationIFrame
			.getByLabel('Start with Menu Items In')
			.selectOption('Select Parent');

		await configurationIFrame
			.getByRole('button', {name: 'Menu Item'})
			.click();

		await configurationIFrame
			.frameLocator('iframe[title="Select Site Navigation Menu Item"]')
			.getByText('Pages Hierarchy')
			.click();
		await configurationIFrame
			.frameLocator('iframe[title="Select Site Navigation Menu Item"]')
			.getByText(parentLayout.nameCurrentValue)
			.click();

		await widgetPagePage.saveAndClose('Menu Display');

		await expect(
			page.getByRole('menuitem', {name: childLayout.nameCurrentValue})
		).toBeVisible();

		await widgetPagePage.clickOnAction('Menu Display', 'Configuration');

		await expect(
			configurationIFrame.getByText(parentLayout.nameCurrentValue)
		).toBeVisible();
	}
);

test(
	'Add URL type Navigation Menu Item with "open in a new tab" checkbox unchecked',
	{
		tag: '@LPD-50258',
	},
	async ({apiHelpers, navigationMenusPage, page, site, widgetPagePage}) => {
		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		await navigationMenusPage.goto(site.friendlyUrlPath);

		const navigationMenuName = getRandomString();

		await navigationMenusPage.createNavigationMenu(navigationMenuName);

		const urlItemName = getRandomString();

		await navigationMenusPage.addURLItem(urlItemName);

		await widgetPagePage.goto(layout, site.friendlyUrlPath);

		await page.waitForTimeout(300);

		await widgetPagePage.clickOnAction('Menu Display', 'Configuration');

		const configurationIFrame = page.frameLocator(
			'iframe[title*="Menu Display"]'
		);

		await page.waitForTimeout(300);

		await configurationIFrame.getByLabel('Choose Menu').check();

		await configurationIFrame.getByRole('button', {name: 'Select'}).click();

		await page.waitForTimeout(300);

		await configurationIFrame
			.frameLocator('iframe[title="Select Site Navigation Menu"]')
			.getByRole('cell', {name: navigationMenuName})
			.click();

		await configurationIFrame.getByRole('button', {name: 'Save'}).click();

		await widgetPagePage.saveAndClose('Menu Display');

		await page.getByText(urlItemName).click();

		const currentURL = page.url();

		expect(currentURL).toContain('https://www.liferay.com');
	}
);

test(
	'Add URL type Navigation Menu Item with "open in a new tab" checkbox checked',
	{
		tag: '@LPD-50258',
	},
	async ({apiHelpers, navigationMenusPage, page, site, widgetPagePage}) => {
		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		await navigationMenusPage.goto(site.friendlyUrlPath);

		const navigationMenuName = getRandomString();

		await navigationMenusPage.createNavigationMenu(navigationMenuName);

		const urlItemName = getRandomString();

		await navigationMenusPage.addURLItem(urlItemName, undefined, true);

		await widgetPagePage.goto(layout, site.friendlyUrlPath);

		await page.waitForTimeout(300);

		await widgetPagePage.clickOnAction('Menu Display', 'Configuration');

		const configurationIFrame = page.frameLocator(
			'iframe[title*="Menu Display"]'
		);

		await page.waitForTimeout(300);

		await configurationIFrame.getByLabel('Choose Menu').check();

		await configurationIFrame.getByRole('button', {name: 'Select'}).click();

		await page.waitForTimeout(300);

		await configurationIFrame
			.frameLocator('iframe[title="Select Site Navigation Menu"]')
			.getByRole('cell', {name: navigationMenuName})
			.click();

		await configurationIFrame.getByRole('button', {name: 'Save'}).click();

		await widgetPagePage.saveAndClose('Menu Display');

		await page.getByText(urlItemName).click();

		const [newPage] = await Promise.all([
			await page.context().waitForEvent('page'),
			await page.getByText(urlItemName).click(),
		]);

		await newPage.waitForLoadState();

		const newTabURL = newPage.url();

		expect(newTabURL).toContain('https://www.liferay.com');
	}
);

test(
	'Show more than two sublevels when select custom menu and bar minimally styled',
	{
		tag: '@LPD-50258',
	},
	async ({
		apiHelpers,
		navigationMenuWidgetPage,
		navigationMenusPage,
		page,
		site,
		widgetPagePage,
	}) => {

		// Create Layouts in the same level

		const layoutName1 = getRandomString();

		const layoutName2 = getRandomString();

		const layoutName3 = getRandomString();

		const layoutName4 = getRandomString();

		const layoutNames = [layoutName2, layoutName3, layoutName4];

		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			externalReferenceCode: getRandomString(),
			groupId: site.id,
			title: layoutName1,
		});

		for (let index = 0; index < 3; index++) {
			await apiHelpers.jsonWebServicesLayout.addLayout({
				externalReferenceCode: getRandomString(),
				groupId: site.id,
				title: layoutNames[index],
			});
		}

		// Create Navigation Menu

		await navigationMenusPage.goto(site.friendlyUrlPath);

		const navigationMenuName = getRandomString();

		await navigationMenusPage.createNavigationMenu(navigationMenuName);

		// Create Page Navigation Menu Items on different levels

		await navigationMenusPage.addPageItem([layoutName1]);

		await navigationMenusPage.addChildPage(layoutName1, layoutName2);

		await navigationMenusPage.addChildPage(layoutName2, layoutName3);

		await navigationMenusPage.addChildPage(layoutName3, layoutName4);

		// Select the created Navigation Menu in the Navigation Menu Widget

		await widgetPagePage.goto(layout, site.friendlyUrlPath);

		await navigationMenuWidgetPage.openConfigurationModal(
			layout.nameCurrentValue
		);

		await navigationMenuWidgetPage.selectCustomNavigationMenu(
			navigationMenuName
		);

		await navigationMenuWidgetPage.saveAndCloseConfigurationModal();

		// Assert that the Navigation Menu Items are viewable when the mouse hove over the first Page Menu Item

		await expect(
			page.getByRole('menuitem', {name: layoutName1})
		).toBeVisible();

		await page.getByRole('menuitem', {name: layoutName1}).hover();

		await expect(
			page.getByRole('menuitem', {name: layoutName2})
		).toBeVisible();

		await expect(page.getByRole('link', {name: layoutName3})).toBeVisible();

		await expect(page.getByRole('link', {name: layoutName4})).toBeVisible();
	}
);

test(
	'View custom field of Navigation Menu',
	{
		tag: '@LPD-50258',
	},
	async ({
		addCustomFieldPage,
		apiHelpers,
		navigationMenuWidgetPage,
		navigationMenusPage,
		page,
		site,
		templatesPage,
		viewAttributesPage,
		widgetPagePage,
	}) => {

		// Create Custom Field for Navigation Menu Items

		const customFieldName = 'Subtitle';

		const customField: TCustomField = {
			fieldName: customFieldName,
			fieldType: 'inputField',
			resource: 'Site Navigation Menu Item',
		};

		await addCustomFieldPage.addCustomField(customField);

		// Create layout

		const layoutName = getRandomString();

		const submenuItemName = getRandomString();

		const urlItemName = getRandomString();

		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			externalReferenceCode: getRandomString(),
			groupId: site.id,
			title: layoutName,
		});

		// Create Navigation Menu

		await navigationMenusPage.goto(site.friendlyUrlPath);

		const navigationMenuName = getRandomString();

		await navigationMenusPage.createNavigationMenu(navigationMenuName);

		// Create Navigation Menu Items

		await navigationMenusPage.addPageItem([layoutName]);

		await navigationMenusPage.addSubmenuItem(submenuItemName);

		await navigationMenusPage.addURLItem(urlItemName);

		// Fill the Custom Fields of the Navigation Menu Items and store its value

		const value1 =
			await navigationMenusPage.fillNavagationMenuItemCustomField(
				layoutName,
				customFieldName
			);

		const value2 =
			await navigationMenusPage.fillNavagationMenuItemCustomField(
				submenuItemName,
				customFieldName
			);

		const value3 =
			await navigationMenusPage.fillNavagationMenuItemCustomField(
				urlItemName,
				customFieldName
			);

		// Create Widget Template with a custom template script

		await templatesPage.gotoWidgetTemplates(site.friendlyUrlPath);

		const widgetTemplateName = getRandomString();

		await templatesPage.createWidgetTemplate(
			widgetTemplateName,
			'Menu Display Template'
		);

		await templatesPage.editTemplate(widgetTemplateName);

		await templatesPage.importInformationTemplate(
			__dirname,
			'custom_field_template.ftl'
		);

		await templatesPage.saveTemplate(widgetTemplateName);

		// Use the created Navigation Menu in the Navigation Menu Widget and apply the created Widget Template

		await widgetPagePage.goto(layout, site.friendlyUrlPath);

		await navigationMenuWidgetPage.openConfigurationModal(
			layout.nameCurrentValue
		);

		await navigationMenuWidgetPage.selectCustomNavigationMenu(
			navigationMenuName
		);

		await page.waitForTimeout(1500);

		await navigationMenuWidgetPage.selectDisplayTemplate(
			widgetTemplateName
		);

		await navigationMenuWidgetPage.saveAndCloseConfigurationModal();

		// Verify that the changes where applied

		await expect(page.getByText(value1)).toBeVisible();

		await expect(page.getByText(value2)).toBeVisible();

		await expect(page.getByText(value3)).toBeVisible();

		// Cleanup

		await viewAttributesPage.goto('Site Navigation Menu Item');

		await page.getByLabel('Select All Items on the Page').check();

		await page.getByRole('button', {name: 'Delete'}).click();
	}
);
