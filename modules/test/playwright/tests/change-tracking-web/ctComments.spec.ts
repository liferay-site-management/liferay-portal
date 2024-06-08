/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import {ApiHelpers} from '../../helpers/ApiHelpers';
import getRandomString from '../../utils/getRandomString';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';
import performLogin, {performLogout} from '../../utils/performLogin';
import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';

export const test = mergeTests(
    journalPagesTest,
    changeTrackingPagesTest,
);

let journalName;

test.beforeEach(async ({journalEditArticlePage, page,}) => {
    journalName = getRandomString();

    await journalEditArticlePage.goto();

    await journalEditArticlePage.fillTitle(journalName);

    await page.getByRole('button', {name: 'Publish'}).waitFor();

    await page.getByRole('button', {name: 'Publish'}).click();

    await waitForSuccessAlert(
        page,
        `Success:${journalName} was created successfully.`
    );
});

test('LPD-17130 Only comment owners are allowed to perform actions on the comment', async ({
    changeTrackingPage,
    ctCollection,
    page,
}) => {
    await changeTrackingPage.goToReviewChanges(ctCollection.name);

    const commentsIcon = page.getByLabel('Comments');

    await commentsIcon.click();

    const commentTextBox = page.getByRole('textbox', {name: 'Comment'});

    const content = getRandomString();

    await commentTextBox.fill(content);

    await page.getByRole('button', {name: 'Reply'}).waitFor();

    await page.getByRole('button', {name: 'Reply'}).click();

    await expect(page.getByText('1 Comment')).toBeVisible();

    const dropdownMenu = page.locator('.comment-row button');

    await expect(dropdownMenu).toBeVisible();

    const apiHelpers = new ApiHelpers(page);

    const user =
        await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
            'demo.unprivileged@liferay.com'
        );

    const adminRole = await apiHelpers.headlessAdminUser.getRoles('Administrator');

    await apiHelpers.headlessAdminUser.assingUserToRole(adminRole.name, user.id);

    await performLogout(page);

    await performLogin(page, user.alternateName);

    await changeTrackingPage.goToReviewChanges(ctCollection.name);

    await commentsIcon.click();

    await expect(dropdownMenu).toBeVisible({visible: false});
});