/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.test.service.impl;

import com.liferay.change.tracking.test.model.Parent;
import com.liferay.change.tracking.test.service.base.ParentLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.change.tracking.test.model.Parent",
	service = AopService.class
)
public class ParentLocalServiceImpl extends ParentLocalServiceBaseImpl {

	@Override
	public Parent addParent(long companyId, long grandParentId) {
		long parentId = counterLocalService.increment(Parent.class.getName());

		Parent parent = parentPersistence.create(parentId);

		parent.setCompanyId(companyId);
		parent.setName(RandomTestUtil.randomString());
		parent.setGrandParentId(grandParentId);

		return parentPersistence.update(parent);
	}

	@Override
	public void deleteParents(long companyId) {
		parentPersistence.removeByCompanyId(companyId);
	}

	@Override
	public void deleteParentsByGrandParentId(
		long companyId, long grandParentId) {

		parentPersistence.removeByC_G(companyId, grandParentId);
	}

	@Override
	public List<Parent> getParents(long companyId) {
		return parentPersistence.findByCompanyId(companyId);
	}

	@Override
	public Parent updateParent(long parentId) throws PortalException {
		Parent parent = parentPersistence.findByPrimaryKey(parentId);

		parent.setName(RandomTestUtil.randomString());

		return parentPersistence.update(parent);
	}

}