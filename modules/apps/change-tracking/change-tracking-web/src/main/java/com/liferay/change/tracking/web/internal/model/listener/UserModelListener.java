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

package com.liferay.change.tracking.web.internal.model.listener;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(immediate = true, service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

	@Override
	public void onAfterAddAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		try {
			if (associationClassName.equals(Role.class.getName())) {
				Long roleId = (Long)associationClassPK;

				Role role = _roleLocalService.getRole(roleId);

				if (!Objects.equals(
						role.getName(), RoleConstants.PUBLICATIONS_USER)) {

					return;
				}

				Long userId = (Long)classPK;

				CTPreferences ctPreferences =
					_ctPreferencesLocalService.getCTPreferences(
						role.getCompanyId(), userId);

				if (ctPreferences.getCtCollectionId() !=
						CTConstants.CT_COLLECTION_ID_PRODUCTION) {

					return;
				}

				User user = _userLocalService.getUser(userId);

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
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
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
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}