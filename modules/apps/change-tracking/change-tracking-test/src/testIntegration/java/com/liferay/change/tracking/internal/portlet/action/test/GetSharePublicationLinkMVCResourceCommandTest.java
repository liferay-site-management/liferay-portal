/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.ByteArrayOutputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Noor Najjar
 */
@RunWith(Arquillian.class)
public class GetSharePublicationLinkMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);
	}

	@Test
	public void testServeResource() throws Exception {
		JSONObject jsonObject = _serveResource(TestPropsValues.getUser());

		Assert.assertTrue(jsonObject.getBoolean("shareable"));

		String sharePublicationLink = jsonObject.getString(
			"sharePublicationLink");

		Assert.assertFalse(sharePublicationLink.isEmpty());

		CTCollection ctCollection = _ctCollectionLocalService.getCTCollection(
			_ctCollection.getCtCollectionId());

		Assert.assertTrue(ctCollection.isShareable());
	}

	@Test
	public void testServeResourceWithoutInviteUsersPermission()
		throws Exception {

		_user = UserTestUtil.addUser();

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.PUBLICATIONS_VIEWER);

		_roleLocalService.addUserRole(_user.getUserId(), role.getRoleId());

		JSONObject jsonObject = _serveResource(_user);

		Assert.assertEquals(
			_language.get(
				LocaleUtil.US, "you-do-not-have-the-required-permissions"),
			jsonObject.getString("errorMessage"));

		Assert.assertFalse(jsonObject.has("sharePublicationLink"));

		CTCollection ctCollection = _ctCollectionLocalService.getCTCollection(
			_ctCollection.getCtCollectionId());

		Assert.assertFalse(ctCollection.isShareable());
	}

	private ThemeDisplay _getThemeDisplay(User user) throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.fetchCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLocale(LocaleUtil.US);
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));
		themeDisplay.setSiteGroupId(TestPropsValues.getGroupId());
		themeDisplay.setUser(user);

		return themeDisplay;
	}

	private JSONObject _serveResource(User user) throws Exception {
		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.setAttribute(
			JavaConstants.JAKARTA_PORTLET_CONFIG, null);
		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(user));
		mockLiferayResourceRequest.setParameter(
			"ctCollectionId",
			String.valueOf(_ctCollection.getCtCollectionId()));
		mockLiferayResourceRequest.setParameter("shareable", "true");

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		_mvcResourceCommand.serveResource(
			mockLiferayResourceRequest, mockLiferayResourceResponse);

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		return JSONFactoryUtil.createJSONObject(
			new String(byteArrayOutputStream.toByteArray()));
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private Language _language;

	@Inject(
		filter = "mvc.command.name=/change_tracking/get_share_publication_link"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private User _user;

}