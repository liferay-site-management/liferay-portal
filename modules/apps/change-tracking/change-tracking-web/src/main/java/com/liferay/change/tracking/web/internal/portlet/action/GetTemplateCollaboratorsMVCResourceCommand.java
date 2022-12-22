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

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollectionTemplate;
import com.liferay.change.tracking.service.CTCollectionTemplateLocalService;
import com.liferay.change.tracking.web.internal.constants.PublicationRoleConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/get_template_collaborators"
	},
	service = MVCResourceCommand.class
)
public class GetTemplateCollaboratorsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long ctCollectionTemplateId = ParamUtil.getLong(
			resourceRequest, "ctCollectionTemplateId");

		if (ctCollectionTemplateId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				_jsonFactory.createJSONArray());

			return;
		}

		CTCollectionTemplate ctCollectionTemplate =
			_ctCollectionTemplateLocalService.fetchCTCollectionTemplate(
				ctCollectionTemplateId);

		if (ctCollectionTemplate == null) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				_jsonFactory.createJSONArray());

			return;
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		User owner = _userLocalService.fetchUser(
			ctCollectionTemplate.getUserId());

		Group group = owner.getGroup();

		if (group == null) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse, jsonArray);

			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		JSONObject collaboratorDataJSONObject =
			ctCollectionTemplate.getJSONObject();

		JSONArray userIdsJSONArray = collaboratorDataJSONObject.getJSONArray(
			"userIds");
		JSONArray roleValuesJSONArray = collaboratorDataJSONObject.getJSONArray(
			"roleValues");

		for (int i = 0; i < userIdsJSONArray.length(); i++) {
			long userId = userIdsJSONArray.getLong(i);

			User user = _userLocalService.getUser(userId);

			if (user == null) {
				continue;
			}

			long roleValue = roleValuesJSONArray.getLong(i);

			String portraitURL = StringPool.BLANK;

			if (user.getPortraitId() > 0) {
				portraitURL = user.getPortraitURL(themeDisplay);
			}

			jsonArray.put(
				JSONUtil.put(
					"emailAddress", user.getEmailAddress()
				).put(
					"fullName", user.getFullName()
				).put(
					"isCurrentUser",
					user.getUserId() == themeDisplay.getUserId()
				).put(
					"isOwner", false
				).put(
					"portraitURL", portraitURL
				).put(
					"roleLabel",
					_language.get(httpServletRequest, _getRoleLabel(roleValue))
				).put(
					"roleValue", roleValue
				).put(
					"userId", user.getUserId()
				));
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonArray);
	}

	private String _getRoleLabel(long name) {
		if (name == PublicationRoleConstants.ROLE_ADMIN) {
			return PublicationRoleConstants.LABEL_ADMIN;
		}
		else if (name == PublicationRoleConstants.ROLE_EDITOR) {
			return PublicationRoleConstants.LABEL_EDITOR;
		}
		else if (name == PublicationRoleConstants.ROLE_PUBLISHER) {
			return PublicationRoleConstants.LABEL_PUBLISHER;
		}

		return PublicationRoleConstants.LABEL_VIEWER;
	}

	@Reference
	private CTCollectionTemplateLocalService _ctCollectionTemplateLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}