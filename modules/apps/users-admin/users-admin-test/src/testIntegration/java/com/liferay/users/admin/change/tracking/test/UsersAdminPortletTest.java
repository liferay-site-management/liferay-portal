/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayActionRequest;
import com.liferay.portal.kernel.portlet.LiferayStateAwareResponse;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.ActionRequestFactory;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.Event;
import javax.portlet.Portlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brooke Dalton
 */
@RunWith(Arquillian.class)
public class UsersAdminPortletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();

		_ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, "P1", null);

		_layout = _layoutLocalService.getLayout(TestPropsValues.getPlid());
	}

	@Test
	public void testNoCTEntryForEditUserMVCAction() throws Exception {
		String newFirstName = RandomTestUtil.randomString();

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			_portlet.processAction(
				_getActionRequest(
					"/users_admin/edit_user",
					HashMapBuilder.put(
						Constants.CMD, Constants.UPDATE
					).put(
						"firstName", newFirstName
					).put(
						"p_u_i_d", String.valueOf(_user.getUserId())
					).build()),
				new CustomMockLiferayPortletActionResponse());
		}

		_assertNoCTEntry();

		_user = _userLocalService.getUser(_user.getUserId());

		Assert.assertEquals(newFirstName, _user.getFirstName());
	}

	private void _assertNoCTEntry() throws Exception {
		Assert.assertEquals(
			0,
			_ctEntryLocalService.getCTCollectionCTEntriesCount(
				_ctCollection.getCtCollectionId()));
	}

	private ActionRequest _getActionRequest(
			String actionName, Map<String, String> params)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			PortletServlet.PORTLET_SERVLET_REQUEST,
			mockLiferayPortletActionRequest.getHttpServletRequest());
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.PORTLET_ID, UsersAdminPortletKeys.USERS_ADMIN);
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		mockLiferayPortletActionRequest.addParameter(
			ActionRequest.ACTION_NAME, actionName);

		for (Map.Entry<String, String> entry : params.entrySet()) {
			mockLiferayPortletActionRequest.addParameter(
				entry.getKey(), entry.getValue());
		}

		LiferayActionRequest liferayActionRequest = ActionRequestFactory.create(
			mockLiferayPortletActionRequest.getHttpServletRequest(),
			_portletLocalService.getPortletById(
				UsersAdminPortletKeys.USERS_ADMIN),
			null, null, null, null, null, TestPropsValues.getPlid());

		liferayActionRequest.setPortletRequestDispatcherRequest(
			mockLiferayPortletActionRequest.getHttpServletRequest());

		return liferayActionRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(_user.getCompanyId()));
		themeDisplay.setLayout(_layout);
		themeDisplay.setLayoutSet(_layout.getLayoutSet());
		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)_layout.getLayoutType());
		themeDisplay.setScopeGroupId(TestPropsValues.getGroupId());
		themeDisplay.setSiteGroupId(TestPropsValues.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private static CTCollection _ctCollection;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTEntryLocalService _ctEntryLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject(
		filter = "component.name=com.liferay.users.admin.web.internal.portlet.UsersAdminPortlet"
	)
	private Portlet _portlet;

	@Inject
	private PortletLocalService _portletLocalService;

	private User _user;

	@Inject
	private UserLocalService _userLocalService;

	private class CustomMockLiferayPortletActionResponse
		extends MockLiferayPortletActionResponse
		implements LiferayStateAwareResponse {

		@Override
		public List<Event> getEvents() {
			return Collections.emptyList();
		}

		@Override
		public String getRedirectLocation() {
			return StringPool.BLANK;
		}

		@Override
		public boolean isCalledSetRenderParameter() {
			return false;
		}

	}

}