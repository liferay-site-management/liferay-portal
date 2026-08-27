/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {changeTrackingPagesTest} from '../../../fixtures/changeTrackingPagesTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {performLoginViaApi, performLogout} from '../../../utils/performLogin';

export const test = mergeTests(apiHelpersTest, changeTrackingPagesTest);

test.describe('Share the publication link', () => {
	let user;

	test.afterEach(async ({apiHelpers, page}) => {
		try {
			await performLogout(page);
		}
		finally {
			await performLoginViaApi({page, screenName: 'test'});
		}

		await apiHelpers.headlessAdminUser.deleteUserAccount(Number(user.id));
	});

	test(
		'Hide the share link tab and button from a viewer',
		{tag: '@LPD-102935'},
		async ({changeTrackingPage, ctCollection, page}) => {
			user = await changeTrackingPage.addUserWithPublicationsUserRole();

			await changeTrackingPage.addUserToPublication(
				ctCollection.body.name,
				'Viewer',
				user
			);

			const collaboratorsButton = page.getByLabel('View Collaborators');
			const modal = page.locator('.publications-invite-users-modal');
			const modalTitle = modal.locator('.modal-title').first();
			const shareLinkButton = page.getByRole('button', {
				name: /Publication Sharing/,
			});
			const shareLinkTab = modal.getByRole('tab', {name: 'Share Link'});

			await changeTrackingPage.goToReviewChanges(ctCollection.body.name);

			await expect(collaboratorsButton).toBeVisible();

			await expect(shareLinkButton).toBeVisible();

			await clickAndExpectToBeVisible({
				target: modalTitle,
				trigger: collaboratorsButton,
			});

			await expect(shareLinkTab).toBeVisible();

			await expect(modalTitle).toHaveText('Share Access');

			await performLogout(page);

			await performLoginViaApi({page, screenName: user.alternateName});

			await changeTrackingPage.goToReviewChanges(ctCollection.body.name);

			await expect(collaboratorsButton).toBeVisible();

			await expect(shareLinkButton).toBeHidden();

			await clickAndExpectToBeVisible({
				target: modalTitle,
				trigger: collaboratorsButton,
			});

			await expect(shareLinkTab).toBeHidden();

			await expect(modalTitle).not.toHaveText('Share Access');
		}
	);
});
