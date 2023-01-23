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

<%@ include file="/publications/init.jsp" %>

<liferay-portlet:renderURL var="backURL" />

<%
String redirect = ParamUtil.getString(request, "redirect");

CTCollectionTemplate ctCollectionTemplate = (CTCollectionTemplate)request.getAttribute(CTWebKeys.CT_COLLECTION_TEMPLATE);

long ctCollectionTemplateId = 0;
String description = StringPool.BLANK;
String name = StringPool.BLANK;
String publicationDescription = StringPool.BLANK;
String publicationName = StringPool.BLANK;
String saveButtonLabel = "create";

if (ctCollectionTemplate != null) {
	ctCollectionTemplateId = ctCollectionTemplate.getCtCollectionTemplateId();
	description = ctCollectionTemplate.getDescription();
	name = ctCollectionTemplate.getName();
	publicationDescription = ctCollectionTemplate.getPublicationDescription();
	publicationName = ctCollectionTemplate.getPublicationName();
	saveButtonLabel = "save";

	renderResponse.setTitle(StringBundler.concat(LanguageUtil.format(resourceBundle, "edit-x", new Object[] {ctCollectionTemplate.getName()})));
}
else {
	renderResponse.setTitle(LanguageUtil.get(request, "create-a-new-publication-template"));
}

HashMapBuilder.HashMapWrapper<String, Object> hashMapWrapper = new HashMapBuilder.HashMapWrapper<>();

portletDisplay.setURLBack(backURL);
portletDisplay.setShowBackIcon(true);
%>

<clay:container-fluid
	cssClass="container-form-lg edit-publication-template-container"
>
	<liferay-portlet:actionURL name="/change_tracking/edit_ct_collection_template" var="actionURL">
		<liferay-portlet:param name="redirect" value="<%= redirect %>" />
	</liferay-portlet:actionURL>

	<c:if test="<%= ctCollectionTemplateId != 0 %>">
		<liferay-portlet:resourceURL id="/change_tracking/get_template_collaborators" var="getTemplateCollaboratorsURL">
			<portlet:param name="ctCollectionTemplateId" value="<%= String.valueOf(ctCollectionTemplateId) %>" />
		</liferay-portlet:resourceURL>

		<%
		hashMapWrapper.put("getTemplateCollaboratorsURL", getTemplateCollaboratorsURL);
		%>

	</c:if>

	<%
	hashMapWrapper.put("actionUrl", actionURL);
	hashMapWrapper.put("collaboratorsProps", publicationsDisplayContext.getCollaboratorsReactData(ctCollectionTemplateId, true));
	hashMapWrapper.put("ctCollectionTemplateId", ctCollectionTemplateId);
	hashMapWrapper.put("description", description);
	hashMapWrapper.put("name", name);
	hashMapWrapper.put("namespace", liferayPortletResponse.getNamespace());
	hashMapWrapper.put("publicationDescription", publicationDescription);
	hashMapWrapper.put("publicationName", publicationName);
	hashMapWrapper.put("redirect", redirect);
	hashMapWrapper.put("saveButtonLabel", LanguageUtil.get(request, saveButtonLabel));
	hashMapWrapper.put("tokens", CTCollectionTemplateLocalServiceUtil.getTokens());
	%>

	<react:component
		module="publications/js/views/PublicationTemplateEditView"
		props="<%= hashMapWrapper.build() %>"
	/>
</clay:container-fluid>