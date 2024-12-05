/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal;

import com.liferay.change.tracking.store.model.CTSContent;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author David Truong
 */
public class CTScoreChecker {

	public CTScoreChecker(
		ClassNameLocalService classNameLocalService,
		CTServiceRegistry ctServiceRegistry) {

		_classNameLocalService = classNameLocalService;
		_ctServiceRegistry = ctServiceRegistry;
	}

	public int calculate(long modelClassNameId) {
		Integer score = _scores.get(modelClassNameId);

		if (score != null) {
			return score;
		}

		score = 4;

		if (modelClassNameId == _classNameLocalService.getClassNameId(
				CTSContent.class)) {

			score += 20;
		}
		else if (modelClassNameId == _classNameLocalService.getClassNameId(
					JournalArticle.class)) {

			score++;
		}

		if (_countTable(modelClassNameId) == _COUNT_LIMIT) {
			score *= 40;
		}

		DB db = DBManagerUtil.getDB();

		DBType dbType = db.getDBType();

		if (dbType.equals(DBType.ORACLE) || dbType.equals(DBType.SQLSERVER)) {
			score *= 2;
		}

		_scores.put(modelClassNameId, score);

		return score;
	}

	private int _countTable(long modelClassNameId) {
		CTService<?> ctService = _ctServiceRegistry.getCTService(
			modelClassNameId);

		if (ctService == null) {
			return 0;
		}

		return ctService.updateWithUnsafeFunction(
			ctPersistence -> {
				Connection connection = CurrentConnectionUtil.getConnection(
					ctPersistence.getDataSource());

				Set<String> primaryKeyNames = ctPersistence.getCTColumnNames(
					CTColumnResolutionType.PK);

				if (primaryKeyNames.size() != 1) {
					throw new IllegalArgumentException(
						StringBundler.concat(
							"{ctPersistence=", ctPersistence,
							", primaryKeyNames=", primaryKeyNames, "}"));
				}

				Iterator<String> iterator = primaryKeyNames.iterator();

				String primaryKeyName = iterator.next();

				try (PreparedStatement preparedStatement =
						connection.prepareStatement(
							StringBundler.concat(
								"select count(", primaryKeyName, ") from ",
								ctPersistence.getTableName(), " limit ",
								_COUNT_LIMIT));
					ResultSet resultSet = preparedStatement.executeQuery()) {

					if (resultSet.next()) {
						return resultSet.getInt(1);
					}

					return 0;
				}
				catch (SQLException sqlException) {
					if (_log.isWarnEnabled()) {
						_log.warn(sqlException);
					}
				}

				return 0;
			});
	}

	private static final int _COUNT_LIMIT = 10000000;

	private static final Log _log = LogFactoryUtil.getLog(CTScoreChecker.class);

	private final ClassNameLocalService _classNameLocalService;
	private final CTServiceRegistry _ctServiceRegistry;
	private final Map<Long, Integer> _scores = new HashMap<>();

}