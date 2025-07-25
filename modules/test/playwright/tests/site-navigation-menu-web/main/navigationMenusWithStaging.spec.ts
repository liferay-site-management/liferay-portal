/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../../fixtures/pageViewModePagesTest';
import {productMenuPageTest} from '../../../fixtures/productMenuPageTest';
import {siteStagingPageTest} from '../../../fixtures/siteStagingPageTest';
import getRandomString from '../../../utils/getRandomString';
import {stagingPageTest} from '../../export-import-web/main/fixtures/stagingPageTest';
import {stagingConfigurationPageTest} from '../../staging-configuration-web/main/fixtures/stagingConfigurationPageTest';
import {navigationMenusPagesTest} from './../../site-navigation-admin-web/main/fixtures/navigationMenusPagesTest';
import {navigationMenuWidgetPagesTest} from './fixtures/navigationMenuWidgetPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	isolatedSiteTest,
	loginTest(),
	navigationMenusPagesTest,
	pageViewModePagesTest,
	stagingConfigurationPageTest,
	stagingPageTest,
	navigationMenuWidgetPagesTest,
	siteStagingPageTest,
	productMenuPageTest
);

test(
	'Publish Edited Navigation Menu To Live',
	{
		tag: '@LPD-59713',
	},

	async ({
		apiHelpers,
		navigationMenuWidgetPage,
		navigationMenusPage,
		page,
		productMenuPage,
		site,
		siteStagingPage,
		stagingPage,
		widgetPagePage,
	}) => {
		await stagingPage.goto(site.name);

		await page.getByLabel('Local Live:').check();

		page.on('dialog', (dialog) => dialog.accept());

		await siteStagingPage.saveButton.click();

		await page.waitForTimeout(300);

		const stagingSite =
			await apiHelpers.headlessAdminUser.getSiteByFriendlyUrlPath(
				`${site.friendlyUrlPath}-staging`
			);

		const layout1 = await apiHelpers.jsonWebServicesLayout.addLayout({
			externalReferenceCode: getRandomString(),
			groupId: stagingSite.id,
			title: getRandomString(),
		});

		const layout2 = await apiHelpers.jsonWebServicesLayout.addLayout({
			externalReferenceCode: getRandomString(),
			groupId: stagingSite.id,
			title: getRandomString(),
		});

		await navigationMenusPage.goto(stagingSite.friendlyUrlPath);

		const navigationMenuName = getRandomString();

		await navigationMenusPage.createNavigationMenu(navigationMenuName);

		await navigationMenusPage.addPageItem([layout1.nameCurrentValue]);

		const urlName = getRandomString();

		await navigationMenusPage.addURLItem(urlName);

		await widgetPagePage.goto(layout1, stagingSite.friendlyUrlPath);

		await navigationMenuWidgetPage.openConfigurationModal(
			layout1.nameCurrentValue
		);

		await navigationMenuWidgetPage.selectCustomNavigationMenu(
			navigationMenuName
		);

		await navigationMenuWidgetPage.saveAndCloseConfigurationModal();

		await stagingPage.goto(site.name + '-staging');
		await stagingPage.publish();

		await productMenuPage.openProductMenuIfClosed();

		await productMenuPage.siteBuilderButton.click();

		await page.getByText('Navigation Menus').click();

		await page.getByText(navigationMenuName).click();

		await page.waitForTimeout(1000);

		await navigationMenusPage.addPageItem([layout2.nameCurrentValue]);

		await page.waitForTimeout(1000);

		await navigationMenusPage.deleteNavigationMenuItem(
			layout1.nameCurrentValue
		);

		await widgetPagePage.goto(layout1, stagingSite.friendlyUrlPath);

		await expect(
			page.getByRole('menuitem', {name: layout1.nameCurrentValue})
		).toBeHidden();
		await expect(
			page.getByRole('menuitem', {name: layout2.nameCurrentValue})
		).toBeVisible();
		await expect(page.getByRole('menuitem', {name: urlName})).toBeVisible();

		await stagingPage.goto(site.name + '-staging');

		await page.getByRole('link', {name: 'Custom Publish Process'}).click();

		await page
			.getByPlaceholder('Enter the name of the process')
			.fill(getRandomString());
		await page.getByLabel('Replicate Individual').check();

		await page.getByRole('button', {name: 'Publish to Live'}).click();

		await expect(
			page
				.locator(
					'[id="_com_liferay_staging_processes_web_portlet_StagingProcessesPortlet_publishLayoutProcesses_1"]'
				)
				.locator('span')
				.filter({hasText: 'Successful'})
				.first()
		).toBeVisible();

		await widgetPagePage.goto(layout1, `${site.friendlyUrlPath}`);

		await expect(
			page.getByRole('menuitem', {name: layout1.nameCurrentValue})
		).toBeHidden();
		await expect(
			page.getByRole('menuitem', {name: layout2.nameCurrentValue})
		).toBeVisible();
		await expect(page.getByRole('menuitem', {name: urlName})).toBeVisible();
	}
);

test(
	'Publish Edited Primary Navigation Menu To Live',
	{
		tag: '@LPD-59713',
	},
	async ({
		apiHelpers,
		navigationMenuWidgetPage,
		navigationMenusPage,
		page,
		site,
		siteStagingPage,
		stagingPage,
		widgetPagePage,
	}) => {
		await stagingPage.goto(site.name);

		await page.getByLabel('Local Live:').check();

		page.on('dialog', (dialog) => dialog.accept());

		await siteStagingPage.saveButton.click();

		await page.waitForTimeout(300);

		const stagingSite =
			await apiHelpers.headlessAdminUser.getSiteByFriendlyUrlPath(
				`${site.friendlyUrlPath}-staging`
			);

		const layout1 = await apiHelpers.jsonWebServicesLayout.addLayout({
			externalReferenceCode: getRandomString(),
			groupId: stagingSite.id,
			title: getRandomString(),
		});

		const layout2 = await apiHelpers.jsonWebServicesLayout.addLayout({
			externalReferenceCode: getRandomString(),
			groupId: stagingSite.id,
			title: getRandomString(),
		});

		await navigationMenusPage.goto(stagingSite.friendlyUrlPath);

		const navigationMenuName = getRandomString();

		await navigationMenusPage.createNavigationMenu(navigationMenuName);

		await navigationMenusPage.addPageItem([layout1.nameCurrentValue]);

		const urlName = getRandomString();

		await navigationMenusPage.addURLItem(urlName);

		await navigationMenusPage.goto(stagingSite.friendlyUrlPath);

		await (
			await navigationMenusPage.getNavigationMenuActionMenu(
				navigationMenuName
			)
		).click();

		await (
			await navigationMenusPage.getMenuItem('Primary Navigation')
		).click();

		await widgetPagePage.goto(layout1, stagingSite.friendlyUrlPath);

		await navigationMenuWidgetPage.openConfigurationModal(
			layout1.nameCurrentValue
		);

		await page
			.frameLocator('iframe[title=" Menu Display  - Configuration"]')
			.locator('[title="select-site-navigation-menu-type"]')
			.selectOption('1');

		await navigationMenuWidgetPage.saveAndCloseConfigurationModal();

		await stagingPage.goto(site.name + '-staging');
		await stagingPage.publish();

		await navigationMenusPage.goto(stagingSite.friendlyUrlPath);

		await page.getByText(navigationMenuName).click();

		await navigationMenusPage.addPageItem([layout2.nameCurrentValue]);

		await navigationMenusPage.deleteNavigationMenuItem(
			layout1.nameCurrentValue
		);

		await page.waitForTimeout(500);

		await widgetPagePage.goto(layout1, stagingSite.friendlyUrlPath);

		await expect(
			page.getByRole('menuitem', {name: layout1.nameCurrentValue})
		).toBeHidden();
		await expect(
			page.getByRole('menuitem', {name: layout2.nameCurrentValue})
		).toBeVisible();
		await expect(page.getByRole('menuitem', {name: urlName})).toBeVisible();

		await stagingPage.goto(site.name + '-staging');

		await page.getByRole('link', {name: 'Custom Publish Process'}).click();

		await page
			.getByPlaceholder('Enter the name of the process')
			.fill(getRandomString());
		await page.getByLabel('Replicate Individual').check();

		await page.getByRole('button', {name: 'Publish to Live'}).click();

		await expect(
			page
				.locator(
					'[id="_com_liferay_staging_processes_web_portlet_StagingProcessesPortlet_publishLayoutProcesses_1"]'
				)
				.locator('span')
				.filter({hasText: 'Successful'})
				.first()
		).toBeVisible();

		await widgetPagePage.goto(layout1, `${site.friendlyUrlPath}`);

		await expect(
			page.getByRole('menuitem', {name: layout1.nameCurrentValue})
		).toBeHidden();
		await expect(
			page.getByRole('menuitem', {name: layout2.nameCurrentValue})
		).toBeVisible();
		await expect(page.getByRole('menuitem', {name: urlName})).toBeVisible();
	}
);
