/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.internal.search.spi.model.index.contributor;

import com.liferay.launch.model.LaunchSet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(
	property = "indexer.class.name=com.liferay.launch.model.LaunchSet",
	service = ModelDocumentContributor.class
)
public class LaunchSetModelDocumentContributor
	implements ModelDocumentContributor<LaunchSet> {

	@Override
	public void contribute(Document document, LaunchSet launchSet) {
		document.addKeyword(Field.COMPANY_ID, launchSet.getCompanyId());
		document.addDate(Field.CREATE_DATE, launchSet.getCreateDate());
		document.addText(Field.DESCRIPTION, launchSet.getDescription());
		document.addDate(Field.MODIFIED_DATE, launchSet.getModifiedDate());
		document.addText(Field.NAME, launchSet.getName());
		document.addKeyword(Field.STATUS, launchSet.getStatus());

		User user = _userLocalService.fetchUser(launchSet.getUserId());

		if (user != null) {
			document.addKeyword(Field.USER_ID, user.getUserId());
			document.addText(Field.USER_NAME, user.getFullName());
		}
	}

	@Reference
	private UserLocalService _userLocalService;

}