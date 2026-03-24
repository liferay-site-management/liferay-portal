/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.seo.management.site.initializer.internal.feature.flag;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.seo.management.site.initializer.internal.constants.SEOManagementConstants;
import com.liferay.site.seo.management.site.initializer.internal.util.SiteInitializerUtil;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brooke Dalton
 */
@Component(
	property = "feature.flag.key=LPD-44511", service = FeatureFlagListener.class
)
public class SEOManagementFeatureFlagListener implements FeatureFlagListener {

	@Override
	public void onValue(
		long companyId, String featureFlagKey, boolean enabled) {

		if (!enabled || !Objects.equals(featureFlagKey, "LPD-44511")) {
			return;
		}

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			Group group = _groupLocalService.fetchGroup(
				companyId, GroupConstants.SEO_MANAGEMENT);

			if (group == null) {
				group = _groupLocalService.addGroup(
					"L_" + SEOManagementConstants.SEO_MANAGEMENT,
					_userLocalService.getGuestUserId(companyId),
					GroupConstants.DEFAULT_PARENT_GROUP_ID, null, 0,
					GroupConstants.DEFAULT_LIVE_GROUP_ID,
					HashMapBuilder.put(
						LocaleUtil.getDefault(), GroupConstants.SEO_MANAGEMENT
					).build(),
					null, GroupConstants.TYPE_SITE_PRIVATE, null, true,
					GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
					SEOManagementConstants.SEO_MANAGEMENT_FRIENDLY_URL, false,
					false, true, null);
			}

			SiteInitializerUtil.initialize(companyId, group, _siteInitializer);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SEOManagementFeatureFlagListener.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(
		target = "(site.initializer.key=com.liferay.site.initializer.seo.management)"
	)
	private SiteInitializer _siteInitializer;

	@Reference
	private UserLocalService _userLocalService;

}