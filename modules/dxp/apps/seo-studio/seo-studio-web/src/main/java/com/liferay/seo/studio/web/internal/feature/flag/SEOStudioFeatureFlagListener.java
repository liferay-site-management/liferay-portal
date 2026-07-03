/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.feature.flag;

import com.liferay.ai.hub.cell.configuration.AIHubCellConfiguration;
import com.liferay.oauth2.provider.constants.ClientProfile;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.util.OAuth2SecureRandomGenerator;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagListener;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.seo.studio.web.internal.constants.SEOStudioWebConstants;
import com.liferay.seo.studio.web.internal.util.SiteInitializerUtil;
import com.liferay.site.initializer.SiteInitializer;

import java.util.Arrays;
import java.util.Calendar;
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

		if (!enabled || !Objects.equals(featureFlagKey, "LPD-44511")) {
			return;
		}

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			_addAIHubCellConfiguration(companyId);

			Group group = _groupLocalService.fetchGroup(
				companyId, GroupConstants.SEO_STUDIO);

			if (group != null) {
				return;
			}

			String externalReferenceCode = TextFormatter.format(
				GroupConstants.SEO_STUDIO, TextFormatter.A);

			group = _groupLocalService.addGroup(
				"L_" + externalReferenceCode,
				_userLocalService.getGuestUserId(companyId),
				GroupConstants.DEFAULT_PARENT_GROUP_ID, null, 0,
				GroupConstants.DEFAULT_LIVE_GROUP_ID,
				HashMapBuilder.put(
					LocaleUtil.getDefault(), GroupConstants.SEO_STUDIO
				).build(),
				null, GroupConstants.TYPE_SITE_RESTRICTED, null, true,
				GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
				GroupConstants.SEO_STUDIO_FRIENDLY_URL, false, false, true,
				null);

			SiteInitializerUtil.initialize(companyId, group, _siteInitializer);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	private void _addAIHubCellConfiguration(long companyId) {
		if (!FeatureFlagManagerUtil.isEnabled(companyId, "LPD-62272")) {
			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Skipping the AI Hub Cell connection for company ",
						companyId,
						" because feature flag LPD-62272 is disabled"));
			}

			return;
		}

		try {
			Company company = _companyLocalService.getCompany(companyId);

			OAuth2Application oAuth2Application = _addOAuth2Application(
				company);

			_configurationProvider.saveCompanyConfiguration(
				AIHubCellConfiguration.class, companyId,
				HashMapDictionaryBuilder.<String, Object>put(
					"clientId", oAuth2Application.getClientId()
				).put(
					"clientSecret", oAuth2Application.getClientSecret()
				).put(
					"serviceURL", company.getPortalURL(0)
				).build());
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to add the AI Hub Cell configuration for company " +
					companyId,
				portalException);
		}
	}

	private OAuth2Application _addOAuth2Application(Company company)
		throws PortalException {

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.
				fetchOAuth2ApplicationByExternalReferenceCode(
					_EXTERNAL_REFERENCE_CODE, company.getCompanyId());

		if (oAuth2Application != null) {
			return oAuth2Application;
		}

		User user = _addServiceAccountUser(company);

		return _oAuth2ApplicationLocalService.addOrUpdateOAuth2Application(
			_EXTERNAL_REFERENCE_CODE, user.getUserId(), user.getScreenName(),
			Arrays.asList(GrantType.CLIENT_CREDENTIALS), "client_secret_post",
			user.getUserId(), OAuth2SecureRandomGenerator.generateClientId(),
			ClientProfile.HEADLESS_SERVER.id(),
			OAuth2SecureRandomGenerator.generateClientSecret(), null, null,
			company.getPortalURL(0), 0, null, "SEO Studio AI Hub Cell Client",
			null, Arrays.asList(), false,
			Arrays.asList(
				"Liferay.AI.Hub.REST.everything",
				"Liferay.AI.Hub.REST.everything.read",
				"Liferay.AI.Hub.REST.everything.write"),
			false, new ServiceContext());
	}

	private User _addServiceAccountUser(Company company)
		throws PortalException {

		String screenName =
			SEOStudioWebConstants.SCREEN_NAME_AI_HUB_CELL_SERVICE_ACCOUNT;

		User user = _userLocalService.fetchUserByScreenName(
			company.getCompanyId(), screenName);

		if (user != null) {
			return user;
		}

		return _userLocalService.addUser(
			UserConstants.USER_ID_DEFAULT, company.getCompanyId(), true, null,
			null, false, screenName,
			screenName + StringPool.AT + company.getMx(),
			LocaleUtil.getDefault(), screenName, StringPool.BLANK,
			"Service Account", 0, 0, true, Calendar.JANUARY, 1, 1970,
			StringPool.BLANK, UserConstants.TYPE_SERVICE_ACCOUNT, null, null,
			null, null, false, new ServiceContext());
	}

	private static final String _EXTERNAL_REFERENCE_CODE =
		"SEO-STUDIO-AI-HUB-CELL-CLIENT";

	private static final Log _log = LogFactoryUtil.getLog(
		SEOStudioFeatureFlagListener.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference(
		target = "(site.initializer.key=com.liferay.seo.studio.site.initializer)"
	)
	private SiteInitializer _siteInitializer;

	@Reference
	private UserLocalService _userLocalService;

}