/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';

export const test = mergeTests(
	featureFlagsTest({
		'LPD-11212': true,
	}),
	changeTrackingPagesTest
);

test('LPD-28956 Verify the configuration fields are displayed', async ({
	ChangeTrackingInstanceSettingsPage,
	page,
}) => {
	await ChangeTrackingInstanceSettingsPage.goto(
		'Publications Email Notifications'
	);

	await expect(
		page.getByRole('heading', {name: 'Publications Email Notifications'})
	).toBeVisible();

	const displayData = [
		'Invitation Email Sender Email Address',
		'Invitation Email Sender Name',
		'Invitation Email Subject',
		'Invitation Email Body',
	];

	for (const data of displayData) {
		await expect(page.getByText(data, {exact: true})).toBeVisible();
	}

	const subject =
		'[$PORTAL_URL$]: You Have Been Invited to Work on a Publication';
	const body =
		'Dear [$TO_NAME$],<br /><br /> You have been invited to work on a publication. ' +
		'For further information, please visit [$PORTAL_PUBLICATION_REVIEW_CHANGES_URL$].' +
		'<br /><br /> Sincerely,<br /> [$FROM_NAME$]<br /> [$FROM_ADDRESS$]<br /> [$PORTAL_URL$]<br />';

	await expect(page.getByText(subject)).toBeVisible();

	await expect(page.getByText(body)).toBeVisible();
});
