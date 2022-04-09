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

package com.liferay.change.tracking.internal.model.listener;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = ModelListener.class)
public class CompanyModelListener extends BaseModelListener<Company> {

	@Override
	public void onAfterCreate(Company company) throws ModelListenerException {
		try {
			Role role = _roleLocalService.getRole(
				company.getCompanyId(), RoleConstants.PUBLICATIONS_USER);

			_resourcePermissionLocalService.addResourcePermission(
				company.getCompanyId(), CTPortletKeys.PUBLICATIONS,
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(company.getCompanyId()), role.getRoleId(),
				ActionKeys.ACCESS_IN_CONTROL_PANEL);
			_resourcePermissionLocalService.addResourcePermission(
				company.getCompanyId(), CTPortletKeys.PUBLICATIONS,
				ResourceConstants.SCOPE_COMPANY,
				String.valueOf(company.getCompanyId()), role.getRoleId(),
				ActionKeys.VIEW);
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Override
	public void onAfterRemove(Company company) {
		CTPreferences ctPreferences =
			_ctPreferencesLocalService.fetchCTPreferences(
				company.getCompanyId(), 0);

		if (ctPreferences != null) {
			_ctPreferencesLocalService.deleteCTPreferences(ctPreferences);
		}
	}

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}