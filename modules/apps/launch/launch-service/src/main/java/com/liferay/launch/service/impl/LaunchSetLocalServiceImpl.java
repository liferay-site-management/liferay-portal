/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.service.impl;

import com.liferay.launch.model.LaunchSet;
import com.liferay.launch.service.base.LaunchSetLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.launch.model.LaunchSet",
	service = AopService.class
)
public class LaunchSetLocalServiceImpl extends LaunchSetLocalServiceBaseImpl {

	@Override
	public LaunchSet addLaunchSet(
		long companyId, String description, String name, long userId) {

		LaunchSet launchSet = launchSetPersistence.create(
			counterLocalService.increment());

		launchSet.setCompanyId(companyId);
		launchSet.setUserId(userId);
		launchSet.setName(name);
		launchSet.setDescription(description);

		return launchSetPersistence.update(launchSet);
	}

}