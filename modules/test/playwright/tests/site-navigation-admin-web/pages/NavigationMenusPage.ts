/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {DisplayPageTemplatesPage} from '../../../pages/layout-page-template-admin-web/DisplayPageTemplatesPage';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../../utils/portletUrls';
import {waitForAlert} from '../../../utils/waitForAlert';

export class NavigationMenusPage {
	readonly page: Page;

	readonly addItemButton: Locator;
	readonly blogsModal: FrameLocator;
	readonly categoriesModal: FrameLocator;
	readonly journalArticleModal: FrameLocator;
	readonly newButton: Locator;
	readonly pagesModal: FrameLocator;
	readonly selectButton: Locator;
	readonly submenuModal: FrameLocator;
	readonly urlModal: FrameLocator;
	readonly vocabulariesModal: FrameLocator;

	constructor(page: Page) {
		this.page = page;

		this.addItemButton = page.getByLabel('Add Menu Item');
		this.blogsModal = page.frameLocator(
			'iframe[title="Select Blogs Entry"]'
		);
		this.categoriesModal = page.frameLocator(
			'iframe[title="Select Categories"]'
		);
		this.journalArticleModal = page.frameLocator(
			'iframe[title="Select Web Content Article"]'
		);
		this.newButton = page.getByRole('button', {name: 'Add'});
		this.pagesModal = page.frameLocator('iframe[title="Select Pages"]');
		this.selectButton = page.getByRole('button', {name: 'Select'});
		this.submenuModal = page.frameLocator('iframe[title="Add Submenu"]');
		this.urlModal = page.frameLocator('iframe[title="Add URL"]');
		this.vocabulariesModal = page.frameLocator(
			'iframe[title="Select Vocabularies"]'
		);
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.navigationMenus}`
		);
	}

	async addWebContentArticleItem(name: string) {
		await this.page.getByLabel('Add Menu Item').click();

		await this.page
			.getByRole('menuitem', {name: 'Web Content Article'})
			.click();

		await this.page.waitForTimeout(300);

		const journalArticleItemButton =
			this.journalArticleModal.getByText(name);

		await expect(journalArticleItemButton).toBeVisible();

		await journalArticleItemButton.hover();

		await journalArticleItemButton.click();
	}

	async addBlogItem(name: string) {
		await this.page.getByLabel('Add Menu Item').click();

		await this.page.getByRole('menuitem', {name: 'Blogs Entry'}).click();

		await this.page.waitForTimeout(300);

		const blogItemButton = this.blogsModal.getByRole('button', {
			name,
		});

		await expect(blogItemButton).toBeVisible();

		await blogItemButton.hover();

		await blogItemButton.click();
	}

	async addSubmenuItem(name: string) {
		await this.page.getByLabel('Add Menu Item').click();

		await this.page.getByRole('menuitem', {name: 'Submenu'}).click();

		await this.page.waitForSelector('iframe', {state: 'attached'});

		const textBox = this.submenuModal.getByPlaceholder('Name');

		await textBox.click();

		await textBox.fill(name);

		await this.submenuModal.getByRole('button', {name: 'Add'}).click();
	}

	async addWidgetToPageTemplate(templateName: string) {
		const displayPageTemplatesPage = new DisplayPageTemplatesPage(
			this.page
		);

		await displayPageTemplatesPage.editTemplate(templateName);

		await this.page.getByLabel('Search Fragments and Widgets').click();

		await this.page
			.getByLabel('Search Fragments and Widgets')
			.fill('display page content');

		await this.page.waitForTimeout(300);

		await this.page
			.getByRole('menuitem', {name: 'Display Page Content Add'})
			.locator('div')
			.first()
			.dragTo(this.page.locator('#page-editor div').nth(1));

		await this.page.getByLabel('Publish').click();

		await displayPageTemplatesPage.markAsDefault(templateName);
	}

	async createNavigationMenu(name: string) {
		await this.newButton.click();

		const input = this.page.getByPlaceholder('Name');

		await input.waitFor();

		await input.fill(name);

		await this.page.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(this.page);
	}

	async openAddPageModal() {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				exact: true,
				name: 'Page',
			}),
			trigger: this.addItemButton,
		});

		await this.pagesModal.getByPlaceholder('Search').waitFor();
	}

	async openAddCategoryModal() {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				exact: true,
				name: 'Category',
			}),
			trigger: this.addItemButton,
		});

		await this.categoriesModal.getByPlaceholder('Search').waitFor();
	}

	async openAddVocabularyModal() {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				exact: true,
				name: 'Vocabulary',
			}),
			trigger: this.addItemButton,
		});

		await this.vocabulariesModal.getByPlaceholder('Search').waitFor();
	}
}
