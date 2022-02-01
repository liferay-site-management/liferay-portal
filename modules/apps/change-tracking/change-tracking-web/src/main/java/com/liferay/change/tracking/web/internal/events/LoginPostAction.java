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

package com.liferay.change.tracking.web.internal.events;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true, property = "key=login.events.post",
	service = LifecycleAction.class
)
public class LoginPostAction extends Action {

	@Override
	public void run(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws ActionException {

		try {
			User user = _portal.getUser(httpServletRequest);

			CTPreferences ctPreferences =
				_ctPreferencesLocalService.fetchCTPreferences(
					user.getCompanyId(), 0);

			if (ctPreferences == null) {
				return;
			}

			Role publicationsUserRole = _roleLocalService.getRole(
				user.getCompanyId(), RoleConstants.PUBLICATIONS_USER);

			if (!_roleLocalService.hasUserRole(
					user.getUserId(), publicationsUserRole.getRoleId())) {

				return;
			}

			ctPreferences = _ctPreferencesLocalService.getCTPreferences(
				user.getCompanyId(), user.getUserId());

			if (ctPreferences.getCtCollectionId() !=
					CTConstants.CT_COLLECTION_ID_PRODUCTION) {

				return;
			}

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
				CTCollection ctCollection =
					_ctCollectionLocalService.addSandboxCTCollection(
						user.getUserId());

				ctPreferences.setCtCollectionId(
					ctCollection.getCtCollectionId());
			}

			ctPreferences.setPreviousCtCollectionId(
				CTConstants.CT_COLLECTION_ID_PRODUCTION);

			_ctPreferencesLocalService.updateCTPreferences(ctPreferences);
		}
		catch (Exception exception) {
			throw new ActionException(exception);
		}
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
	private Portal _portal;

	@Reference
	private RoleLocalService _roleLocalService;

}