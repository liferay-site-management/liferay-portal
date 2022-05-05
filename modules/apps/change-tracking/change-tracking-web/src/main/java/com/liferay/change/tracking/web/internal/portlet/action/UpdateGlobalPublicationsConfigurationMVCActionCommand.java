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
import com.liferay.change.tracking.exception.CTStagingEnabledException;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.model.CTPreferencesTable;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.service.CTPreferencesService;
import com.liferay.change.tracking.web.internal.scheduler.PublishScheduler;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.Users_RolesTable;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/update_global_publications_configuration"
	},
	service = MVCActionCommand.class
)
public class UpdateGlobalPublicationsConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL redirectURL = PortletURLFactoryUtil.create(
			actionRequest, CTPortletKeys.PUBLICATIONS,
			PortletRequest.RENDER_PHASE);

		boolean enablePublications = ParamUtil.getBoolean(
			actionRequest, "enablePublications");

		CTPreferences globalCTPreferences =
			_ctPreferencesLocalService.fetchCTPreferences(
				themeDisplay.getCompanyId(), 0);

		if ((globalCTPreferences != null) || !enablePublications) {
			redirectURL.setParameter(
				"mvcRenderCommandName", "/change_tracking/view_settings");
		}

		try {
			globalCTPreferences = _ctPreferencesService.enablePublications(
				themeDisplay.getCompanyId(), enablePublications);
		}
		catch (CTStagingEnabledException ctStagingEnabledException) {
			SessionErrors.add(actionRequest, "stagingEnabled");

			redirectURL.setParameter(
				"mvcRenderCommandName", "/change_tracking/view_settings");

			sendRedirect(actionRequest, actionResponse, redirectURL.toString());

			return;
		}

		boolean doSandbox = false;

		if (enablePublications) {
			boolean enableSandboxOnly = ParamUtil.getBoolean(
				actionRequest, "enableSandboxOnly");

			if (!globalCTPreferences.isSandboxOnlyEnabled() &&
				enableSandboxOnly) {

				doSandbox = true;
			}

			globalCTPreferences.setSandboxOnlyEnabled(enableSandboxOnly);

			_ctPreferencesLocalService.updateCTPreferences(globalCTPreferences);
		}
		else if (PropsValues.SCHEDULER_ENABLED) {
			List<CTCollection> ctCollections =
				_ctCollectionLocalService.getCTCollections(
					themeDisplay.getCompanyId(),
					WorkflowConstants.STATUS_SCHEDULED, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

			for (CTCollection ctCollection : ctCollections) {
				_publishScheduler.unschedulePublish(
					ctCollection.getCtCollectionId());
			}
		}

		if (doSandbox) {
			Role publicationsUserRole = _roleLocalService.getRole(
				themeDisplay.getCompanyId(), RoleConstants.PUBLICATIONS_USER);

			for (CTPreferences ctPreferences :
					_ctPreferencesLocalService.<List<CTPreferences>>dslQuery(
						DSLQueryFactoryUtil.select(
							CTPreferencesTable.INSTANCE
						).from(
							CTPreferencesTable.INSTANCE
						).innerJoinON(
							Users_RolesTable.INSTANCE,
							Users_RolesTable.INSTANCE.roleId.eq(
								publicationsUserRole.getRoleId()
							).and(
								Users_RolesTable.INSTANCE.userId.eq(
									CTPreferencesTable.INSTANCE.userId)
							)
						).where(
							CTPreferencesTable.INSTANCE.companyId.eq(
								themeDisplay.getCompanyId()
							).and(
								CTPreferencesTable.INSTANCE.ctCollectionId.eq(
									CTConstants.CT_COLLECTION_ID_PRODUCTION)
							)
						))) {

				User user = _userLocalService.getUser(
					ctPreferences.getUserId());

				if ((ctPreferences.getPreviousCtCollectionId() !=
						CTConstants.CT_COLLECTION_ID_PRODUCTION) &&
					_ctCollectionModelResourcePermission.contains(
						PermissionCheckerFactoryUtil.create(user),
						ctPreferences.getPreviousCtCollectionId(),
						ActionKeys.UPDATE)) {

					ctPreferences.setCtCollectionId(
						ctPreferences.getPreviousCtCollectionId());
				}
				else {
					CTCollection sandboxCTCollection =
						_ctCollectionLocalService.addSandboxCTCollection(
							ctPreferences.getUserId());

					ctPreferences.setCtCollectionId(
						sandboxCTCollection.getCtCollectionId());
				}

				ctPreferences.setPreviousCtCollectionId(
					CTConstants.CT_COLLECTION_ID_PRODUCTION);

				_ctPreferencesLocalService.updateCTPreferences(ctPreferences);
			}
		}

		hideDefaultSuccessMessage(actionRequest);

		SessionMessages.add(
			_portal.getHttpServletRequest(actionRequest), "requestProcessed",
			_language.get(
				themeDisplay.getLocale(), "the-configuration-has-been-saved"));

		sendRedirect(actionRequest, actionResponse, redirectURL.toString());
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.change.tracking.model.CTCollection)"
	)
	private ModelResourcePermission<CTCollection>
		_ctCollectionModelResourcePermission;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private CTPreferencesService _ctPreferencesService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile PublishScheduler _publishScheduler;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}