/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {backendPageTest} from '../../../../fixtures/backendPageTest';
import {ApiHelpers} from '../../../../helpers/ApiHelpers';

const test = mergeTests(backendPageTest);

export const seoStudioSiteTest = test.extend<{
	seoStudioSite: Site;
}>({
	seoStudioSite: [
		async ({backendPage}, use) => {
			test.setTimeout(180000);

			await backendPage.goto('/');

			const apiHelpers = new ApiHelpers(backendPage);

			try {
				await apiHelpers.featureFlag.updateFeatureFlag(
					'LPD-44511',
					true
				);

				await use(
					await apiHelpers.headlessAdminSite.getSite('L_SEO_STUDIO')
				);
			}
			finally {
				await apiHelpers.featureFlag.updateFeatureFlag(
					'LPD-44511',
					false
				);
			}
		},
		{scope: 'test'},
	],
});
