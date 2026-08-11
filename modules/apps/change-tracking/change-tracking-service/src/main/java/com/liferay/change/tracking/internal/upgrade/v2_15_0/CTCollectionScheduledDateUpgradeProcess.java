/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.upgrade.v2_15_0;

import com.liferay.change.tracking.constants.CTDestinationNames;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kiana Suetani
 */
public class CTCollectionScheduledDateUpgradeProcess extends UpgradeProcess {

	public CTCollectionScheduledDateUpgradeProcess(
		SchedulerEngineHelper schedulerEngineHelper) {

		_schedulerEngineHelper = schedulerEngineHelper;
	}

	@Override
	protected void doUpgrade() throws Exception {
		Map<Long, Date> ctCollectionScheduleDates =
			_getCTCollectionScheduleDates();

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select ctCollectionId from CTCollection where status = ?");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update CTCollection set scheduledDate = ?, status = ? " +
						"where ctCollectionId = ?")) {

			preparedStatement1.setInt(1, WorkflowConstants.STATUS_SCHEDULED);

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					long ctCollectionId = resultSet.getLong("ctCollectionId");

					Date ctCollectionScheduleDate =
						ctCollectionScheduleDates.get(ctCollectionId);

					Timestamp timestamp = null;
					int status = WorkflowConstants.STATUS_DRAFT;

					if (ctCollectionScheduleDate != null) {
						timestamp = new Timestamp(
							ctCollectionScheduleDate.getTime());
						status = WorkflowConstants.STATUS_SCHEDULED;
					}

					preparedStatement2.setTimestamp(1, timestamp);
					preparedStatement2.setInt(2, status);
					preparedStatement2.setLong(3, ctCollectionId);

					preparedStatement2.addBatch();
				}
			}

			preparedStatement2.executeBatch();
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"CTCollection", "scheduledDate DATE null")
		};
	}

	private Map<Long, Date> _getCTCollectionScheduleDates() throws Exception {
		Map<Long, Date> ctCollectionScheduleDates = new HashMap<>();

		List<SchedulerResponse> schedulerResponses =
			_schedulerEngineHelper.getScheduledJobs(
				CTDestinationNames.CT_COLLECTION_SCHEDULED_PUBLISH,
				StorageType.PERSISTED);

		for (SchedulerResponse schedulerResponse : schedulerResponses) {
			Date startDate = _schedulerEngineHelper.getStartDate(
				schedulerResponse);

			if (startDate == null) {
				continue;
			}

			Message message = schedulerResponse.getMessage();

			ctCollectionScheduleDates.put(
				message.getLong("ctCollectionId"), startDate);
		}

		return ctCollectionScheduleDates;
	}

	private final SchedulerEngineHelper _schedulerEngineHelper;

}