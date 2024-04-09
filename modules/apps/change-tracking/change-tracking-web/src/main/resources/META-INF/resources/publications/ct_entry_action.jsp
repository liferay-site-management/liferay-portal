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

long workflowTaskId = ParamUtil.getLong(renderRequest, "workflowTaskId");
%>

<liferay-portlet:actionURL name="/change_tracking/ct_entry_action_task" var="actionURL" />

<div class="task-action">
	<aui:form action="<%= actionURL %>" method="post" name="actionFm">
		<div class="modal-body task-action-content">
			<aui:input name="workflowTaskId" type="hidden" value="<%= String.valueOf(workflowTaskId) %>" />
			<aui:input name="eventName" type="hidden" value="<%= eventName %>" />

			<aui:input cols="55" cssClass="task-action-comment" name="comment" placeholder="comment" rows="1" type="textarea" />
		</div>

		<div class="modal-footer">
			<div class="modal-item-last">
				<div class="btn-group">
					<div class="btn-group-item">
						<aui:button name="close" type="cancel" />
					</div>

					<div class="btn-group-item">
						<aui:button name="done" primary="<%= true %>" value="done" />
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
				document.querySelector('#<portlet:namespace />actionFm')
			);

			Liferay.Util.fetch('<%= actionURL.toString() %>', {
				body: data,
				method: 'POST',
			})
				.then((response) => {
					return response.json();
				})
				.then((json) => {
					Liferay.Util.getOpener().<portlet:namespace />refreshPortlet(
						'<%= PortalUtil.escapeRedirect(redirect) %>'
					);
				});
		});
	}
</aui:script>