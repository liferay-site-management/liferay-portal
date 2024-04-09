<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<%
String eventName = ParamUtil.getString(request, "eventName");

String redirect = ParamUtil.getString(request, "redirect");

boolean hasAssignableUsers = ParamUtil.getBoolean(renderRequest, "hasAssignableUsers");

long userId = ParamUtil.getLong(request, "userId");

long workflowTaskId = ParamUtil.getLong(renderRequest, "workflowTaskId");

WorkflowTask workflowTask = publicationsDisplayContext.getWorkflowTask(workflowTaskId);
%>

<liferay-portlet:actionURL name="/change_tracking/ct_entry_assign_task" var="assignURL" />

<div class="task-action">
	<aui:form action="<%= assignURL %>" method="post" name="assignFm">
		<div class="modal-body task-action-content">
			<aui:input name="workflowTaskId" type="hidden" value="<%= String.valueOf(workflowTaskId) %>" />

			<c:choose>
				<c:when test='<%= Objects.equals(eventName, "assignToMe") %>'>
					<aui:input name="assigneeUserId" type="hidden" value="<%= String.valueOf(userId) %>" />
				</c:when>
				<c:otherwise>
					<aui:select disabled="<%= !hasAssignableUsers %>" label="assign-to" name="assigneeUserId">

						<%
						for (User assignableUser : WorkflowTaskManagerUtil.getAssignableUsers(workflowTaskId)) {
						%>

							<aui:option label="<%= HtmlUtil.escape(assignableUser.getScreenName()) + StringPool.SPACE + StringPool.OPEN_PARENTHESIS + HtmlUtil.escape(assignableUser.getFullName()) + StringPool.CLOSE_PARENTHESIS %>" selected="<%= workflowTask.getAssigneeUserId() == assignableUser.getUserId() %>" value="<%= String.valueOf(assignableUser.getUserId()) %>" />

						<%
						}
						%>

					</aui:select>
				</c:otherwise>
			</c:choose>

			<aui:input cols="55" cssClass="task-action-comment" disabled='<%= !hasAssignableUsers && Objects.equals(eventName, "assignTo") %>' name="comment" placeholder="comment" rows="1" type="textarea" />
		</div>

		<div class="modal-footer">
			<div class="modal-item-last">
				<div class="btn-group">
					<div class="btn-group-item">
						<aui:button name="close" type="cancel" />
					</div>

					<div class="btn-group-item">
						<aui:button disabled='<%= !hasAssignableUsers && Objects.equals(eventName, "assignTo") %>' name="done" primary="<%= true %>" value="done" />
					</div>
				</div>
			</div>
		</div>
	</aui:form>
</div>

<aui:script use="aui-base">
	var done = A.one('#<portlet:namespace />done');

	if (done) {
		done.on('click', (event) => {
			var data = new FormData(
				document.querySelector('#<portlet:namespace />assignFm')
			);

			Liferay.Util.fetch('<%= assignURL.toString() %>', {
				body: data,
				method: 'POST',
			})
				.then((response) => {
					return response.json();
				})
				.then((json) => {
					const assignMode =
						'<%= ParamUtil.getString(request, "assignMode") %>';

					if (assignMode === 'assignToMe') {
						Liferay.Util.getOpener().<portlet:namespace />refreshPortlet(
							'<%= PortalUtil.escapeRedirect(redirect) %>'
						);
					}
					else {
						Liferay.Util.getOpener().<portlet:namespace />refreshPortlet(
							'<%= PortalUtil.escapeRedirect(redirect) %>'
						);
					}
				});
		});
	}
</aui:script>