/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.upgrade.v2_16_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Noor Najjar
 */
public class CTMappingTableUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		for (String tableName : _TABLE_NAMES) {
			runSQL(
				StringBundler.concat(
					"delete from ", tableName, " where ctCollectionId != 0 ",
					"and ctCollectionId not in (select ctCollectionId from ",
					"CTCollection where status = ",
					WorkflowConstants.STATUS_DRAFT, " or status = ",
					WorkflowConstants.STATUS_SCHEDULED, ")"));
		}
	}

	private static final String[] _TABLE_NAMES = {
		"Groups_Roles", "Groups_UserGroups", "Users_Roles", "Users_UserGroups"
	};

}