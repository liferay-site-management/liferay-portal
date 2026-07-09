/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.feature.flag.test;

import com.liferay.ai.hub.cell.configuration.AIHubCellConfiguration;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.configuration.admin.util.ConfigurationFilterStringUtil;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.feature.flag.FeatureFlagListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Arrays;
import java.util.Dictionary;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author David Truong
 */
@FeatureFlags(
	featureFlags = {@FeatureFlag("LPD-44511"), @FeatureFlag("LPD-62272")}
)
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
		_deleteOAuth2Application();

		_configurationProvider.deleteCompanyConfiguration(
			AIHubCellConfiguration.class, TestPropsValues.getCompanyId());
	}

	@After
	public void tearDown() throws Exception {
		_deleteOAuth2Application();

		_configurationProvider.deleteCompanyConfiguration(
			AIHubCellConfiguration.class, TestPropsValues.getCompanyId());
	}

	@Test
	public void testOnValue() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		_seoStudioFeatureFlagListener.onValue(companyId, "LPD-44511", true);

		Configuration firstConfiguration = _getConfiguration(companyId);

		Dictionary<String, ?> properties = firstConfiguration.getProperties();

		Company company = _companyLocalService.getCompany(companyId);

		Assert.assertEquals(
			company.getPortalURL(0), properties.get("serviceURL"));

		String clientId = (String)properties.get("clientId");
		String clientSecret = (String)properties.get("clientSecret");

		Assert.assertFalse(Validator.isBlank(clientId));
		Assert.assertFalse(Validator.isBlank(clientSecret));

		OAuth2Application firstOAuth2Application =
			_oAuth2ApplicationLocalService.
				fetchOAuth2ApplicationByExternalReferenceCode(
					_EXTERNAL_REFERENCE_CODE, companyId);

		Assert.assertEquals(firstOAuth2Application.getClientId(), clientId);
		Assert.assertEquals(
			firstOAuth2Application.getClientSecret(), clientSecret);

		_seoStudioFeatureFlagListener.onValue(companyId, "LPD-44511", true);

		Configuration secondConfiguration = _getConfiguration(companyId);

		Assert.assertEquals(properties, secondConfiguration.getProperties());

		OAuth2Application secondOAuth2Application =
			_oAuth2ApplicationLocalService.
				fetchOAuth2ApplicationByExternalReferenceCode(
					_EXTERNAL_REFERENCE_CODE, companyId);

		Assert.assertEquals(
			firstOAuth2Application.getOAuth2ApplicationId(),
			secondOAuth2Application.getOAuth2ApplicationId());
	}

	private void _deleteOAuth2Application() throws Exception {
		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.
				fetchOAuth2ApplicationByExternalReferenceCode(
					_EXTERNAL_REFERENCE_CODE, TestPropsValues.getCompanyId());

		if (oAuth2Application != null) {
			_oAuth2ApplicationLocalService.deleteOAuth2Application(
				oAuth2Application.getOAuth2ApplicationId());
		}
	}

	private Configuration _getConfiguration(long companyId) throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			ConfigurationFilterStringUtil.getScopedFilterString(
				null,
				"com.liferay.ai.hub.cell.configuration.AIHubCellConfiguration",
				ExtendedObjectClassDefinition.Scope.COMPANY, companyId));

		Assert.assertEquals(
			Arrays.toString(configurations), 1, configurations.length);

		return configurations[0];
	}

	private static final String _EXTERNAL_REFERENCE_CODE = "AI-HUB-CELL";

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	@Inject
	private ConfigurationProvider _configurationProvider;

	@Inject
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Inject(filter = "feature.flag.key=LPD-44511")
	private FeatureFlagListener _seoStudioFeatureFlagListener;

}