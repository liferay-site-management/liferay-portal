/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.upgrade.v2_16_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
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
public class CTMappingTableUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_draftCTCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		_publishedCTCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		_publishedCTCollection.setStatus(WorkflowConstants.STATUS_APPROVED);

		_publishedCTCollection = _ctCollectionLocalService.updateCTCollection(
			_publishedCTCollection);

		_orphanCTCollectionId =
			_publishedCTCollection.getCtCollectionId() + 1000000;

		_upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.change.tracking.internal.upgrade.v2_16_0." +
				"CTMappingTableUpgradeProcess");

		_userGroupId = RandomTestUtil.randomLong();

		_addUserGroupRow(0);
		_addUserGroupRow(_draftCTCollection.getCtCollectionId());
		_addUserGroupRow(_orphanCTCollectionId);
		_addUserGroupRow(_publishedCTCollection.getCtCollectionId());
	}

	@After
	public void tearDown() throws Exception {
		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				"delete from Users_UserGroups where userGroupId = ?")) {

			preparedStatement.setLong(1, _userGroupId);

			preparedStatement.executeUpdate();
		}

		_ctCollectionLocalService.deleteCTCollection(_draftCTCollection);
		_ctCollectionLocalService.deleteCTCollection(_publishedCTCollection);
	}

	@Test
	public void testUpgrade() throws Exception {
		Assert.assertEquals(
			Arrays.asList(
				0L, _draftCTCollection.getCtCollectionId(),
				_publishedCTCollection.getCtCollectionId(),
				_orphanCTCollectionId),
			_getCTCollectionIds());

		_upgradeProcess.upgrade();

		Assert.assertEquals(
			Arrays.asList(0L, _draftCTCollection.getCtCollectionId()),
			_getCTCollectionIds());
	}

	private void _addUserGroupRow(long ctCollectionId) throws Exception {
		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				"insert into Users_UserGroups (companyId, userId, " +
					"userGroupId, ctCollectionId) values (?, ?, ?, ?)")) {

			preparedStatement.setLong(1, TestPropsValues.getCompanyId());
			preparedStatement.setLong(2, TestPropsValues.getUserId());
			preparedStatement.setLong(3, _userGroupId);
			preparedStatement.setLong(4, ctCollectionId);

			preparedStatement.executeUpdate();
		}
	}

	private List<Long> _getCTCollectionIds() throws Exception {
		List<Long> ctCollectionIds = new ArrayList<>();

		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				"select ctCollectionId from Users_UserGroups where " +
					"userGroupId = ? order by ctCollectionId")) {

			preparedStatement.setLong(1, _userGroupId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					ctCollectionIds.add(resultSet.getLong("ctCollectionId"));
				}
			}
		}

		return ctCollectionIds;
	}

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	private CTCollection _draftCTCollection;
	private long _orphanCTCollectionId;
	private CTCollection _publishedCTCollection;
	private UpgradeProcess _upgradeProcess;

	@Inject(
		filter = "(&(component.name=com.liferay.change.tracking.internal.upgrade.registry.ChangeTrackingServiceUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

	private long _userGroupId;

}