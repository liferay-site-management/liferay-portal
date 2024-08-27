<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<portlet:defineObjects />

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<aui:fieldset>
		<aui:select label="silencePopoverTime" name="silencePopoverTime" value='<%= (String)portletPreferences.getValue("silencePopoverTime", "1 hour") %>'>
			<aui:option label="1 hour" value="1 hour" />
			<aui:option label="4 hours" value="4 hours" />
			<aui:option label="24 hours" value="24 hours" />
			<aui:option label="Forever" value="Forever" />
		</aui:select>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>