/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.seo.studio.site.initializer.internal.feature.flag;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.seo.studio.site.initializer.internal.util.SiteInitializerUtil;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brooke Dalton
 */
@Component(
	property = "feature.flag.key=LPD-44511", service = FeatureFlagListener.class
)
public class SEOStudioFeatureFlagListener implements FeatureFlagListener {

	@Override
	public void onValue(
		long companyId, String featureFlagKey, boolean enabled) {

		if (!Objects.equals(featureFlagKey, "LPD-44511")) {
			return;
		}

		if (enabled) {
			_activateSEOStudioGroup(companyId);
		}
		else {
			_deactivateSEOStudioGroup(companyId);
		}
	}

	private void _activateSEOStudioGroup(long companyId) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			_groupLocalService.checkSystemGroups(companyId);

			Group group = _groupLocalService.getGroup(
				companyId, GroupConstants.SEO_STUDIO);

			if (!group.isActive()) {
				_groupLocalService.updateGroup(
					group.getGroupId(), group.getParentGroupId(),
					group.getNameMap(), group.getDescriptionMap(),
					group.getType(), null, group.isManualMembership(),
					group.getMembershipRestriction(), group.getFriendlyURL(),
					group.isInheritContent(), true, null);
			}

			SiteInitializerUtil.initialize(companyId, group, _siteInitializer);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	private void _deactivateSEOStudioGroup(long companyId) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			_groupLocalService.checkSystemGroups(companyId);

			Group group = _groupLocalService.getGroup(
				companyId, GroupConstants.SEO_STUDIO);

			if (group.isActive()) {
				_groupLocalService.updateGroup(
					group.getGroupId(), group.getParentGroupId(),
					group.getNameMap(), group.getDescriptionMap(),
					group.getType(), null, group.isManualMembership(),
					group.getMembershipRestriction(), group.getFriendlyURL(),
					group.isInheritContent(), false, null);
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SEOStudioFeatureFlagListener.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(
		target = "(site.initializer.key=com.liferay.site.initializer.seo.studio)"
	)
	private SiteInitializer _siteInitializer;

}