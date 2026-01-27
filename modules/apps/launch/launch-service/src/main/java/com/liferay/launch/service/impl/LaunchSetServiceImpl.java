/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.service.impl;

import com.liferay.launch.constants.LaunchConstants;
import com.liferay.launch.model.LaunchSet;
import com.liferay.launch.service.base.LaunchSetServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(
	property = {
		"json.web.service.context.name=launch",
		"json.web.service.context.path=LaunchSet"
	},
	service = AopService.class
)
public class LaunchSetServiceImpl extends LaunchSetServiceBaseImpl {

	@Override
	public LaunchSet addLaunchSet(
			String externalReferenceCode, String description, String name)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null, ActionKeys.ADD_ENTRY);

		return launchSetLocalService.addLaunchSet(
			externalReferenceCode, getUserId(), description, name);
	}

	public LaunchSet deleteLaunchSet(long launchSetId) throws PortalException {
		_portletResourcePermission.check(
			getPermissionChecker(), null, ActionKeys.ADD_ENTRY);

		return launchSetLocalService.deleteLaunchSet(launchSetId);
	}

	public LaunchSet deleteLaunchSet(
			String externalReferenceCode, long companyId)
		throws PortalException {

		LaunchSet launchSet = launchSetPersistence.findByERC_C(
			externalReferenceCode, companyId);

		_launchSetModelResourcePermission.check(
			getPermissionChecker(), launchSet, ActionKeys.DELETE);

		return launchSetLocalService.deleteLaunchSet(
			externalReferenceCode, companyId);
	}

	@Override
	public LaunchSet updateLaunchSet(
			String externalReferenceCode, long launchSetId, String description,
			String name)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null, ActionKeys.UPDATE);

		return launchSetLocalService.updateLaunchSet(
			externalReferenceCode, launchSetId, getUserId(), description, name);
	}

	@Reference(target = "(model.class.name=com.liferay.launch.model.LaunchSet)")
	private ModelResourcePermission<LaunchSet>
		_launchSetModelResourcePermission;

	@Reference(target = "(resource.name=" + LaunchConstants.RESOURCE_NAME + ")")
	private volatile PortletResourcePermission _portletResourcePermission;

}