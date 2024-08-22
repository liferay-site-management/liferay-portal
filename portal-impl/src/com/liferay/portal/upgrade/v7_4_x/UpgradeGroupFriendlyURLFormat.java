/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Joao Victor Alves
 */
public class UpgradeGroupFriendlyURLFormat extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
			"select friendlyURL, groupId, companyId, ctCollectionId from " +
			"Group_ where friendlyURL like '%/'");
			 PreparedStatement preparedStatement2 = connection.prepareStatement(
				 "select * from Group_ where companyId = ? and " +
				 "friendlyURL = ?");
			 PreparedStatement preparedStatement3 =
				 connection.prepareStatement(
					 "update Group_ set friendlyURL = ? where groupId = ? " +
					 "and ctCollectionId = ? and companyId = ?");

			 ResultSet resultSet1 = preparedStatement1.executeQuery()
			 ) {

			while (resultSet1.next()) {
				String friendlyURL = resultSet1.getString(1);
				long groupId = resultSet1.getLong(2);
				long companyId = resultSet1.getLong(3);
				long ctCollectionId = resultSet1.getLong(4);

				friendlyURL = friendlyURL.substring(
					0, friendlyURL.length() - 1);

				preparedStatement2.setLong(1, companyId);
				preparedStatement2.setString(2, friendlyURL);

				ResultSet resultSet2 = preparedStatement2.executeQuery();

				friendlyURL = _getUniqueURL(
					resultSet2.next(), friendlyURL, preparedStatement2);

				preparedStatement3.setString(1, friendlyURL);

				preparedStatement3.setLong(2, groupId);

				preparedStatement3.setLong(3, ctCollectionId);

				preparedStatement3.setLong(4, companyId);

				preparedStatement3.addBatch();
			}

			preparedStatement3.executeBatch();
		}
	}

	private String _getUniqueURL(
			boolean hasNext, String friendlyURL,
			PreparedStatement preparedStatement)
		throws SQLException {

		String tempFriendlyURL = friendlyURL;

		for (int i = 1; hasNext; i++) {
			tempFriendlyURL = friendlyURL + StringPool.DASH + i;

			preparedStatement.setString(2, tempFriendlyURL);

			ResultSet resultSet1 = preparedStatement.executeQuery();

			hasNext = resultSet1.next();
		}

		return tempFriendlyURL;
	}

}