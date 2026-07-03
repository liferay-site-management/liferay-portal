/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.feature.flag.test;

import com.liferay.ai.hub.cell.configuration.AIHubCellConfiguration;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.feature.flag.constants.FeatureFlagConstants;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.FeatureFlagTestUtil;
import com.liferay.portal.props.test.util.PropsTemporarySwapper;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Truong
 */
@RunWith(Arquillian.class)
public class SEOStudioFeatureFlagListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();
	}

	@After
	public void tearDown() throws Exception {
		_configurationProvider.deleteCompanyConfiguration(
			AIHubCellConfiguration.class, _company.getCompanyId());
	}

	@Test
	public void testOnValue() throws Exception {
		try (PropsTemporarySwapper propsTemporarySwapper =
				new PropsTemporarySwapper(
					FeatureFlagConstants.getKey("LPD-62272"),
					Boolean.TRUE.toString())) {

			FeatureFlagTestUtil.invokeFeatureFlagListeners(
				_company.getCompanyId(), true, "LPD-44511");

			OAuth2Application oAuth2Application =
				_oAuth2ApplicationLocalService.
					fetchOAuth2ApplicationByExternalReferenceCode(
						"SEO-STUDIO-AI-HUB-CELL-CLIENT",
						_company.getCompanyId());

			Assert.assertNotNull(oAuth2Application);

			Assert.assertEquals(
				Arrays.asList(GrantType.CLIENT_CREDENTIALS),
				oAuth2Application.getAllowedGrantTypesList());

			User user = _userLocalService.getUserById(
				oAuth2Application.getClientCredentialUserId());

			Assert.assertEquals(
				"seo-studio-ai-hub-cell-service-account", user.getScreenName());
			Assert.assertEquals(
				UserConstants.TYPE_SERVICE_ACCOUNT, user.getType());

			AIHubCellConfiguration aiHubCellConfiguration =
				IdempotentRetryAssert.retryAssert(
					10, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
					() -> {
						AIHubCellConfiguration currentAIHubCellConfiguration =
							_configurationProvider.getCompanyConfiguration(
								AIHubCellConfiguration.class,
								_company.getCompanyId());

						Assert.assertEquals(
							oAuth2Application.getClientId(),
							currentAIHubCellConfiguration.clientId());

						return currentAIHubCellConfiguration;
					});

			Assert.assertEquals(
				oAuth2Application.getClientSecret(),
				aiHubCellConfiguration.clientSecret());
			Assert.assertEquals(
				_company.getPortalURL(0), aiHubCellConfiguration.serviceURL());

			Assert.assertNotNull(
				_oAuth2ApplicationLocalService.
					fetchOAuth2ApplicationByExternalReferenceCode(
						"AI-HUB-CELL", _company.getCompanyId()));
			Assert.assertNotNull(
				_sapEntryLocalService.fetchSAPEntry(
					_company.getCompanyId(), "AI_HUB_CELL_TOKEN"));

			FeatureFlagTestUtil.invokeFeatureFlagListeners(
				_company.getCompanyId(), true, "LPD-44511");

			IdempotentRetryAssert.retryAssert(
				10, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
				() -> {
					AIHubCellConfiguration currentAIHubCellConfiguration =
						_configurationProvider.getCompanyConfiguration(
							AIHubCellConfiguration.class,
							_company.getCompanyId());

					Assert.assertEquals(
						oAuth2Application.getClientId(),
						currentAIHubCellConfiguration.clientId());

					return currentAIHubCellConfiguration;
				});
		}
	}

	@DeleteAfterTestRun
	private Company _company;

	@Inject
	private ConfigurationProvider _configurationProvider;

	@Inject
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Inject
	private SAPEntryLocalService _sapEntryLocalService;

	@Inject
	private UserLocalService _userLocalService;

}