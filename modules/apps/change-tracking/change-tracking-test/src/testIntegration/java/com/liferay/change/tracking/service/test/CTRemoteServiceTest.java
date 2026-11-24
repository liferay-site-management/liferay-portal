/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTRemote;
import com.liferay.change.tracking.service.CTRemoteLocalService;
import com.liferay.change.tracking.service.CTRemoteService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Noor Najjar
 */
@RunWith(Arquillian.class)
public class CTRemoteServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testDeleteCTRemote() throws Exception {
		CTRemote ctRemote = _ctRemoteLocalService.addCTRemote(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		User user = UserTestUtil.addUser();

		UserTestUtil.setUser(user);

		ctRemote.setUserId(user.getUserId());

		try {
			_ctRemoteService.deleteCTRemote(ctRemote);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			Assert.assertNotNull(
				_ctRemoteLocalService.fetchCTRemote(ctRemote.getCtRemoteId()));
		}

		UserTestUtil.setUser(TestPropsValues.getUser());

		_ctRemoteService.deleteCTRemote(ctRemote);

		Assert.assertNull(
			_ctRemoteLocalService.fetchCTRemote(ctRemote.getCtRemoteId()));
	}

	@Inject
	private CTRemoteLocalService _ctRemoteLocalService;

	@Inject
	private CTRemoteService _ctRemoteService;

}