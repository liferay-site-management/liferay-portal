/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/get_page_preview"
	},
	service = MVCActionCommand.class
)
public class GetPagePreviewMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long ctCollectionId = ParamUtil.getLong(
			actionRequest, "ctCollectionId", -1);

		if (ctCollectionId < 0) {
			return;
		}

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		ThemeDisplay originalThemeDisplay =
			(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollectionId)) {

			ThemeDisplay themeDisplay =
				(ThemeDisplay)originalThemeDisplay.clone();

			themeDisplay.setLayouts(
				_getLayouts(ParamUtil.getLong(actionRequest, "selPlid", -1)));

			User user = _getAdminUser(themeDisplay.getCompanyId());

			themeDisplay.setRealUser(user);

			themeDisplay.setPermissionChecker(
				_permissionCheckerFactory.create(user));

			httpServletRequest.setAttribute(
				WebKeys.THEME_DISPLAY, themeDisplay);

			_getPagePreviewStrutsAction.execute(
				httpServletRequest,
				_portal.getHttpServletResponse(actionResponse));
		}
		catch (Exception exception) {
			_portal.sendError(exception, actionRequest, actionResponse);
		}
		finally {
			httpServletRequest.setAttribute(
				WebKeys.THEME_DISPLAY, originalThemeDisplay);
		}

		hideDefaultErrorMessage(actionRequest);
		hideDefaultSuccessMessage(actionRequest);
	}

	private User _getAdminUser(long companyId) throws PortalException {
		Role role = _roleLocalService.getRole(
			companyId, RoleConstants.ADMINISTRATOR);

		List<User> users = _userLocalService.getRoleUsers(role.getRoleId());

		return users.get(0);
	}

	private List<Layout> _getLayouts(long plid) throws PortalException {
		if (plid < 0) {
			return null;
		}

		Layout layout = _layoutLocalService.getLayout(plid);

		List<Layout> layouts = new ArrayList<>();

		for (Layout curLayout :
				_layoutLocalService.getLayouts(
					layout.getGroupId(), layout.isPrivateLayout(),
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID)) {

			if (!curLayout.isHidden()) {
				layouts.add(curLayout);
			}
		}

		return layouts;
	}

	@Reference(target = "(path=/portal/get_page_preview)")
	private StrutsAction _getPagePreviewStrutsAction;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private Portal _portal;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}