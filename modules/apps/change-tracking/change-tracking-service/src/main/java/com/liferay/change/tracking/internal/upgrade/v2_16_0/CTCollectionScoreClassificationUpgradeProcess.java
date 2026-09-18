/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.upgrade.v2_16_0;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author David Truong
 */
public class CTCollectionScoreClassificationUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasTable("CTScore")) {
			try (PreparedStatement selectPreparedStatement =
					connection.prepareStatement(
						"select ctCollectionId, score from CTScore")) {

				try (ResultSet resultSet =
						selectPreparedStatement.executeQuery();
					PreparedStatement updatePreparedStatement =
						AutoBatchPreparedStatementUtil.autoBatch(
							connection,
							"update CTCollection set scoreSizeClassification " +
								"= ? where ctCollectionId = ?")) {

					while (resultSet.next()) {
						updatePreparedStatement.setString(
							1,
							_getScoreSizeClassification(
								resultSet.getInt("score")));
						updatePreparedStatement.setLong(
							2, resultSet.getLong("ctCollectionId"));

						updatePreparedStatement.addBatch();
					}

					updatePreparedStatement.executeBatch();
				}
			}

			dropTable("CTScore");
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update CTCollection set scoreSizeClassification = ? where " +
					"scoreSizeClassification is null")) {

			preparedStatement.setString(
				1, CTConstants.SCORE_SIZE_CLASSIFICATION_SMALL);

			preparedStatement.executeUpdate();
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"CTCollection", "scoreSizeClassification VARCHAR(75) null")
		};
	}

	private String _getScoreSizeClassification(int score) {

		// Duplicated from CTCollectionScoreCache's thresholds on purpose:
		// this upgrade step is a frozen snapshot of the migration that ran
		// at this schema version and must not silently change behavior if
		// the live thresholds are ever retuned.

		if (score > 20000) {
			return CTConstants.SCORE_SIZE_CLASSIFICATION_LARGE;
		}
		else if (score > 10000) {
			return CTConstants.SCORE_SIZE_CLASSIFICATION_MEDIUM;
		}

		return CTConstants.SCORE_SIZE_CLASSIFICATION_SMALL;
	}

}