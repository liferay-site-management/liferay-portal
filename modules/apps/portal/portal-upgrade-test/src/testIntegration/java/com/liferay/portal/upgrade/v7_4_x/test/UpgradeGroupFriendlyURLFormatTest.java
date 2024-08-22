/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.v7_4_x.UpgradeGroupFriendlyURLFormat;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Joao Victor Alves
 */
@RunWith(Arquillian.class)
public class UpgradeGroupFriendlyURLFormatTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpgrade() throws UpgradeException {
		Group group = _groupLocalService.createGroup(0);

		group.setFriendlyURL("test/");

		_groupLocalService.addGroup(group);

		UpgradeProcess upgradeProcess = new UpgradeGroupFriendlyURLFormat();

		upgradeProcess.upgrade();

		group = _groupLocalService.fetchFriendlyURLGroup(
			group.getCompanyId(), "test");

		Assert.assertNotNull(group);
	}

	@Inject
	private GroupLocalService _groupLocalService;

}