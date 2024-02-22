<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<%
long backgroundTaskId = GetterUtil.getLong(renderRequest.getParameter("backgroundTaskId"));
String ctCollectionName = GetterUtil.getString(renderRequest.getParameter("ctCollectionName"));

BackgroundTask backgroundTask = BackgroundTaskLocalServiceUtil.getBackgroundTask(backgroundTaskId);
%>

<div class="sheet sheet-lg">
	<clay:container-fluid>
		<div class="sheet-header">
			<h6 class="sheet-title"> <%= ctCollectionName %> failed to publish </h6>
			<div class="sheet-text">
				<liferay-ui:message key="<%= backgroundTask.getStatusMessage() %>" />
			</div>
		</div>
	</clay:container-fluid>
</div>