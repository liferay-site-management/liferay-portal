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
import com.liferay.portal.kernel.model.role.RoleConstants;
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
import com.liferay.portal.workflow.kaleo.definition.Assignment;
import com.liferay.portal.workflow.kaleo.definition.RoleAssignment;
import com.liferay.portal.workflow.kaleo.definition.Task;
import com.liferay.portal.workflow.kaleo.definition.TaskForm;
import com.liferay.portal.workflow.kaleo.definition.TaskFormReference;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskForm;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskFormInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskFormLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskLocalService;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brooke Dalton
 */
@RunWith(Arquillian.class)
public class KaleoTaskFormInstanceTableReferenceDefinitionTest
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

		Task task = new Task(RandomTestUtil.randomString(), StringPool.BLANK);

		task.setAssignments(
			new HashSet<Assignment>() {
				{
					add(
						new RoleAssignment(
							RoleConstants.ADMINISTRATOR,
							RoleConstants.TYPE_REGULAR_LABEL));
				}
			});

		KaleoNode kaleoNode = _kaleoNodeLocalService.addKaleoNode(
			_kaleoInstance.getKaleoDefinitionId(),
			_kaleoInstance.getKaleoDefinitionVersionId(), task,
			_serviceContext);

		KaleoInstanceToken kaleoInstanceToken =
			_kaleoInstanceTokenLocalService.addKaleoInstanceToken(
				kaleoNode.getKaleoNodeId(),
				_kaleoInstance.getKaleoDefinitionId(),
				_kaleoInstance.getKaleoDefinitionVersionId(),
				_kaleoInstance.getKaleoInstanceId(), 0,
				WorkflowContextUtil.convert(
					_kaleoInstance.getWorkflowContext()),
				_serviceContext);

		KaleoTask kaleoTask = _kaleoTaskLocalService.addKaleoTask(
			_kaleoInstance.getKaleoDefinitionId(),
			_kaleoInstance.getKaleoDefinitionVersionId(),
			kaleoNode.getKaleoNodeId(), task, _serviceContext);

		_kaleoTaskInstanceToken =
			_kaleoTaskInstanceTokenLocalService.addKaleoTaskInstanceToken(
				kaleoInstanceToken.getKaleoInstanceTokenId(),
				kaleoTask.getKaleoTaskId(), kaleoTask.getName(),
				Collections.emptyList(), null,
				WorkflowContextUtil.convert(
					_kaleoInstance.getWorkflowContext()),
				_serviceContext);

		TaskForm taskForm = new TaskForm(RandomTestUtil.randomString(), 0);

		taskForm.setTaskFormReference(new TaskFormReference());

		taskForm.setFormDefinition(RandomTestUtil.randomString());

		_kaleoTaskForm = _kaleoTaskFormLocalService.addKaleoTaskForm(
			_kaleoInstance.getKaleoDefinitionId(),
			_kaleoInstance.getKaleoDefinitionVersionId(),
			kaleoNode.getKaleoNodeId(), kaleoTask, taskForm, _serviceContext);
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return _kaleoTaskFormInstanceLocalService.addKaleoTaskFormInstance(
			_kaleoInstance.getGroupId(), _kaleoTaskForm.getKaleoTaskFormId(),
			RandomTestUtil.randomString(), _kaleoTaskInstanceToken,
			_serviceContext);
	}

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	private KaleoInstance _kaleoInstance;

	@Inject
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	@Inject
	private KaleoInstanceTokenLocalService _kaleoInstanceTokenLocalService;

	@Inject
	private KaleoNodeLocalService _kaleoNodeLocalService;

	private KaleoTaskForm _kaleoTaskForm;

	@Inject
	private KaleoTaskFormInstanceLocalService
		_kaleoTaskFormInstanceLocalService;

	@Inject
	private KaleoTaskFormLocalService _kaleoTaskFormLocalService;

	private KaleoTaskInstanceToken _kaleoTaskInstanceToken;

	@Inject
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

	@Inject
	private KaleoTaskLocalService _kaleoTaskLocalService;

	private ServiceContext _serviceContext;

}