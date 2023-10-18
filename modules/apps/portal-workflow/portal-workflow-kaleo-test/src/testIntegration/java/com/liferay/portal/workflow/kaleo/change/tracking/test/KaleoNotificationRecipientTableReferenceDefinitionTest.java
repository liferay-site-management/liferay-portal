/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.change.tracking.test.util.BaseTableReferenceDefinitionTestCase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.workflow.kaleo.definition.Notification;
import com.liferay.portal.workflow.kaleo.definition.NotificationReceptionType;
import com.liferay.portal.workflow.kaleo.definition.Recipient;
import com.liferay.portal.workflow.kaleo.definition.Task;
import com.liferay.portal.workflow.kaleo.definition.UserRecipient;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoNotification;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNotificationLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNotificationRecipientLocalService;

import java.io.Serializable;

import java.util.Date;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brooke Dalton
 */
@RunWith(Arquillian.class)
public class KaleoNotificationRecipientTableReferenceDefinitionTest
	extends BaseTableReferenceDefinitionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_serviceContext = ServiceContextTestUtil.getServiceContext();

		BlogsEntry blogsEntry = _blogsEntryLocalService.addEntry(
			TestPropsValues.getUserId(), StringUtil.randomString(),
			StringUtil.randomString(), new Date(), _serviceContext);

		_kaleoInstance = _kaleoInstanceLocalService.addKaleoInstance(
			1, 1, RandomTestUtil.randomString(), 1,
			HashMapBuilder.<String, Serializable>put(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME,
				(Serializable)BlogsEntry.class.getName()
			).put(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK,
				String.valueOf(blogsEntry.getEntryId())
			).put(
				WorkflowConstants.CONTEXT_SERVICE_CONTEXT,
				(Serializable)_serviceContext
			).build(),
			_serviceContext);

		KaleoNode kaleoNode = _kaleoNodeLocalService.addKaleoNode(
			_kaleoInstance.getKaleoDefinitionId(),
			_kaleoInstance.getKaleoDefinitionVersionId(),
			new Task(RandomTestUtil.randomString(), StringPool.BLANK),
			_serviceContext);

		_kaleoNotification =
			_kaleoNotificationLocalService.addKaleoNotification(
				KaleoNode.class.getName(), _kaleoInstance.getClassPK(),
				_kaleoInstance.getKaleoDefinitionId(),
				_kaleoInstance.getKaleoDefinitionVersionId(),
				kaleoNode.getName(),
				new Notification(
					StringUtil.randomString(), StringUtil.randomString(),
					"onAssignment", StringPool.BLANK, "freemarker"),
				_serviceContext);
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		Recipient recipient = new UserRecipient();

		recipient.setNotificationReceptionType(
			NotificationReceptionType.parse("email"));

		return _kaleoNotificationRecipientLocalService.
			addKaleoNotificationRecipient(
				_kaleoInstance.getKaleoDefinitionId(),
				_kaleoInstance.getKaleoDefinitionVersionId(),
				_kaleoNotification.getKaleoNotificationId(), recipient,
				_serviceContext);
	}

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	private KaleoInstance _kaleoInstance;

	@Inject
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	@Inject
	private KaleoNodeLocalService _kaleoNodeLocalService;

	private KaleoNotification _kaleoNotification;

	@Inject
	private KaleoNotificationLocalService _kaleoNotificationLocalService;

	@Inject
	private KaleoNotificationRecipientLocalService
		_kaleoNotificationRecipientLocalService;

	private ServiceContext _serviceContext;

}