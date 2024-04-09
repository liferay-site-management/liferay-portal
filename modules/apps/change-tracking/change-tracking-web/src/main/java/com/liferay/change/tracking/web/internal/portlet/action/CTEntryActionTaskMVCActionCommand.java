/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR
 * LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.workflow.security.permission.WorkflowTaskPermission;

import java.io.Serializable;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gislayne Vitorino
 */
@Component(
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/ct_entry_action_task"
	},
	service = MVCActionCommand.class
)
public class CTEntryActionTaskMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long workflowTaskId = ParamUtil.getLong(
			actionRequest, "workflowTaskId");

		WorkflowTask workflowTask = _workflowTaskManager.getWorkflowTask(
			workflowTaskId);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = MapUtil.getLong(
			workflowTask.getOptionalAttributes(), "groupId",
			themeDisplay.getSiteGroupId());

		_workflowTaskPermission.check(
			themeDisplay.getPermissionChecker(), workflowTask, groupId);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse,
			JSONUtil.put(
				"hasPermission",
				_workflowTaskPermission.contains(
					themeDisplay.getPermissionChecker(),
					_workflowTaskManager.completeWorkflowTask(
						themeDisplay.getCompanyId(), themeDisplay.getUserId(),
						workflowTaskId,
						ParamUtil.getString(actionRequest, "eventName"),
						ParamUtil.getString(actionRequest, "comment"),
						_getWorkflowContext(themeDisplay, workflowTaskId),
						true),
					groupId)));

		SessionMessages.add(actionRequest, "requestProcessed", "");
	}

	private Map<String, Serializable> _getWorkflowContext(
			ThemeDisplay themeDisplay, long workflowTaskId)
		throws Exception {

		WorkflowTask workflowTask = _workflowTaskManager.getWorkflowTask(
			workflowTaskId);

		WorkflowInstance workflowInstance =
			_workflowInstanceManager.getWorkflowInstance(
				themeDisplay.getCompanyId(),
				workflowTask.getWorkflowInstanceId());

		Map<String, Serializable> workflowContext =
			workflowInstance.getWorkflowContext();

		workflowContext.put(
			WorkflowConstants.CONTEXT_USER_ID,
			String.valueOf(themeDisplay.getUserId()));

		return workflowContext;
	}

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

	@Reference
	private WorkflowTaskPermission _workflowTaskPermission;

}