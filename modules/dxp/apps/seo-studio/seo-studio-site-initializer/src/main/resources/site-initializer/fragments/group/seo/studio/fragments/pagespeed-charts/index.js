/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	const container = fragmentElement.querySelector('.pagespeed-charts-root');

	if (!container || layoutMode === 'edit') {
		return;
	}

	import('/o/seo-studio-web/__liferay__/index.js').then((module) => {
		const root = module.renderPageSpeedCharts(container);

		if (Liferay && Liferay.on) {
			Liferay.on('beforeNavigate', () => {
				root.unmount();
			});
		}
	});
})();
