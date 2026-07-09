/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.sitemap.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.configuration.manager.SitemapConfigurationManager;
import com.liferay.site.manager.SitemapManager;
import com.liferay.site.storage.helper.SitemapStorageHelper;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;
import jakarta.portlet.PortletException;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = {
		"jakarta.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"mvc.command.name=/site_sitemap/save_company_configuration"
	},
	service = MVCActionCommand.class
)
public class SaveCompanyConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		long companyId = themeDisplay.getCompanyId();

		if (!permissionChecker.isCompanyAdmin(companyId)) {
			PrincipalException principalException =
				new PrincipalException.MustBeCompanyAdmin(
					permissionChecker.getUserId());

			throw new PortletException(principalException);
		}

		boolean cachedGenerationEnabled = ParamUtil.getBoolean(
			actionRequest, "cachedGenerationEnabled");

		_sitemapConfigurationManager.saveSitemapCompanyConfiguration(
			cachedGenerationEnabled, companyId,
			ArrayUtil.filter(
				ArrayUtil.unique(
					ParamUtil.getLongValues(
						actionRequest, "groupsSearchContainerPrimaryKeys")),
				groupId -> {
					Group group = _groupLocalService.fetchGroup(groupId);

					if ((group == null) || group.isGuest()) {
						return false;
					}

					return true;
				}),
			ArrayUtil.filter(
				ArrayUtil.unique(
					ParamUtil.getLongValues(
						actionRequest,
						"objectDefinitionsSearchContainerPrimaryKeys")),
				objectDefinitionId -> {
					ObjectDefinition objectDefinition =
						_objectDefinitionLocalService.fetchObjectDefinition(
							objectDefinitionId);

					if ((objectDefinition == null) ||
						!objectDefinition.isActive() ||
						objectDefinition.isSystem()) {

						return false;
					}

					return true;
				}),
			ParamUtil.getBoolean(actionRequest, "includeCategories"),
			ParamUtil.getBoolean(actionRequest, "includePages"),
			ParamUtil.getBoolean(actionRequest, "includeWebContent"),
			ParamUtil.getBoolean(actionRequest, "xmlSitemapIndexEnabled"),
			ParamUtil.getString(
				actionRequest, "xmlSitemapIndexMode",
				_sitemapConfigurationManager.xmlSitemapIndexMode(companyId)),
			ParamUtil.getString(
				actionRequest, "xmlSitemapRegenerationDay",
				_sitemapConfigurationManager.xmlSitemapRegenerationDay(
					companyId)),
			ParamUtil.getString(
				actionRequest, "xmlSitemapRegenerationFrequency",
				_sitemapConfigurationManager.xmlSitemapRegenerationFrequency(
					companyId)),
			ParamUtil.getString(
				actionRequest, "xmlSitemapRegenerationTime",
				_sitemapConfigurationManager.xmlSitemapRegenerationTime(
					companyId)),
			ParamUtil.getString(
				actionRequest, "xmlSitemapRegenerationTimeZoneId",
				_sitemapConfigurationManager.xmlSitemapRegenerationTimeZoneId(
					companyId)));

		String successMessage = "xml-sitemap-settings-have-been-saved";

		if (ParamUtil.getBoolean(actionRequest, "saveAndGenerate") ||
			(cachedGenerationEnabled &&
			 !_sitemapStorageHelper.hasSitemapFiles(companyId))) {

			Map<Long, String> assetTypeKeys =
				_sitemapManager.getAssetTypeKeys();

			for (String assetTypeKey : assetTypeKeys.values()) {
				_sitemapManager.scheduleRegenerateSitemap(
					assetTypeKey, companyId, 0, new Date());
			}

			successMessage =
				"xml-sitemap-has-been-cached-and-settings-have-been-saved";
		}

		SessionMessages.add(
			actionRequest, "requestProcessed",
			_language.get(themeDisplay.getLocale(), successMessage));

		sendRedirect(actionRequest, actionResponse);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private SitemapConfigurationManager _sitemapConfigurationManager;

	@Reference
	private SitemapManager _sitemapManager;

	@Reference
	private SitemapStorageHelper _sitemapStorageHelper;

}