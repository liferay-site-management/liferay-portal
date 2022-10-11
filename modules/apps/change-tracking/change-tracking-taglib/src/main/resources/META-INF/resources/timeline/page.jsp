<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>

<%
String className = (String)request.getAttribute("change-tracking:timeline:className");
long classPK = GetterUtil.getLong(request.getAttribute("change-tracking:timeline:classPK"));

TimelineDisplayContext timelineDisplayContext = new TimelineDisplayContext(renderRequest, renderResponse, className, classPK);
%>

<clay:content-row
	cssClass="sidebar-section"
>
	<clay:content-col
		expand="<%= true %>"
	>
		<h1 class="component-title"><liferay:ui:message key="timeline" /></h1>
	</clay:content-col>

	<%-- TODO
				<clay:content-col>
					For add button
				</clay:content-col>
	--%>

</clay:content-row>

<clay:content-row
	cssClass="sidebar-section"
>
	<%-- TODO
		<clay:content-col>
			Icon
		</clay:content-col>
	--%>

	<clay:content-col
		expand="<%= true %>"
	>
		<h1 class="component-title">Current Draft</h1>
	</clay:content-col>
</clay:content-row>

<%
for (CTCollection ctCollection : timelineDisplayContext.getCTCollections()) {
%>
	<clay:content-row
		cssClass="sidebar-section"
	>
		<%-- TODO
				<clay:content-col>
					Icon
				</clay:content-col>
		--%>

		<clay:content-col
			expand="<%= true %>"
		>
			<div class="dropdown">
				<h1 class="component-title"><%= ctCollection.getName() %></h1>
			</div>

			<%
			Date modifiedDate = ctCollection.getModifiedDate();
			%>

			<div class="text-secondary">
				Published <liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
			</div>
		</clay:content-col>

		<clay:content-col>
			<div>
				<div class="dropdown">
					<button class="btn btn-monospaced btn-sm btn-unstyled dropdown-toggle hidden" type="button">
						<svg class="lexicon-icon lexicon-icon-ellipsis-v publications-hidden" role="presentation">
							<use xlink:href="<%= FrontendIconsUtil.getSpritemap(themeDisplay) %>#ellipsis-v" />
						</svg>
					</button>
				</div>

				<react:component
					module="timeline/js/TimelineDropdownMenu"
					props="<%= timelineDisplayContext.getDropdownReactData(ctCollection) %>"
				/>
			</div>
		</clay:content-col>
	</clay:content-row>
<%
}
%>