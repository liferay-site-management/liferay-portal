/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR
 * LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.base.BaseTable;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Groups_RolesTable;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cheryl Tang
 */
@RunWith(Arquillian.class)
public class CTTableMapperExclusionsTest {

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
			0, CTTableMapperExclusionsTest.class.getName(), null);

		_group = GroupTestUtil.addGroup();

		_userGroup = UserGroupTestUtil.addUserGroup(_group.getGroupId());

		_role = _roleLocalService.getRole(
			_group.getCompanyId(), "Publications User");
	}

	@Test
	public void testUnsetRoleGroups() throws PortalException {
		Groups_RolesTable groups_rolesTable = Groups_RolesTable.INSTANCE;

		Column<Groups_RolesTable, Long> groupIdColumn =
			groups_rolesTable.groupId;

		long leftPrimaryKey = _role.getRoleId();
		long rightPrimaryKey = _userGroup.getGroupId();
		long ctCollectionId = _ctCollection.getCtCollectionId();

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollectionId)) {

			_groupService.addRoleGroups(
				leftPrimaryKey, new long[] {rightPrimaryKey});

			List<Object> ctCollectionRowsAfterAdd = _roleLocalService.dslQuery(
				_getMappingTableDSLQuery(
					groupIdColumn, groups_rolesTable, "roleId", "groupId",
					leftPrimaryKey, rightPrimaryKey, ctCollectionId));

			Assert.assertEquals(
				ctCollectionRowsAfterAdd.toString(), 0,
				ctCollectionRowsAfterAdd.size());

			List<Object> productionRowsAfterAdd = _roleLocalService.dslQuery(
				_getMappingTableDSLQuery(
					groupIdColumn, groups_rolesTable, "roleId", "groupId",
					leftPrimaryKey, rightPrimaryKey,
					CTConstants.CT_COLLECTION_ID_PRODUCTION));

			Assert.assertEquals(
				productionRowsAfterAdd.toString(), 1,
				productionRowsAfterAdd.size());

			_groupService.unsetRoleGroups(
				leftPrimaryKey, new long[] {rightPrimaryKey});

			List<Object> ctCollectionRowsAfterDelete =
				_roleLocalService.dslQuery(
					_getMappingTableDSLQuery(
						groupIdColumn, groups_rolesTable, "roleId", "groupId",
						leftPrimaryKey, rightPrimaryKey, ctCollectionId));

			Assert.assertEquals(
				ctCollectionRowsAfterDelete.toString(), 0,
				ctCollectionRowsAfterDelete.size());

			List<Object> productionRowsAfterDelete = _roleLocalService.dslQuery(
				_getMappingTableDSLQuery(
					groupIdColumn, groups_rolesTable, "roleId", "groupId",
					leftPrimaryKey, rightPrimaryKey,
					CTConstants.CT_COLLECTION_ID_PRODUCTION));

			Assert.assertEquals(
				productionRowsAfterDelete.toString(), 0,
				productionRowsAfterDelete.size());
		}
	}

	private DSLQuery _getMappingTableDSLQuery(
		Expression<?> selectExpression, BaseTable<?> table,
		String leftPKColumnName, String rightPKColumnName, long leftPrimaryKey,
		long rightPrimaryKey, long ctCollectionId) {

		Column<?, Long> leftColumn = table.getColumn(
			leftPKColumnName, Long.class);
		Column<?, Long> rightColumn = table.getColumn(
			rightPKColumnName, Long.class);
		Column<?, Long> ctCollectionIdColumn = table.getColumn(
			"ctCollectionId", Long.class);
		Column<?, Long> companyIdColumn = table.getColumn(
			"companyId", Long.class);

		return DSLQueryFactoryUtil.selectDistinct(
			selectExpression
		).from(
			table
		).where(
			leftColumn.eq(
				leftPrimaryKey
			).and(
				rightColumn.eq(
					rightPrimaryKey
				).and(
					ctCollectionIdColumn.eq(ctCollectionId)
				).and(
					companyIdColumn.eq(_ctCollection.getCompanyId())
				)
			)
		);
	}

	private static CTCollection _ctCollection;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	private static Group _group;

	@Inject
	private static GroupService _groupService;

	@Inject
	private static RoleLocalService _roleLocalService;

	private static UserGroup _userGroup;

	private Role _role;

}