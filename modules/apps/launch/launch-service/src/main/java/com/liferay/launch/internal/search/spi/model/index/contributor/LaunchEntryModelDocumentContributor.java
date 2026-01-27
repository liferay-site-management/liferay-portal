/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.internal.search.spi.model.index.contributor;

import com.liferay.launch.model.LaunchEntry;
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
	property = "indexer.class.name=com.liferay.launch.model.LaunchEntry",
	service = ModelDocumentContributor.class
)
public class LaunchEntryModelDocumentContributor
	implements ModelDocumentContributor<LaunchEntry> {

	@Override
	public void contribute(Document document, LaunchEntry launchEntry) {
		document.addKeyword(Field.COMPANY_ID, launchEntry.getCompanyId());
		document.addDate(Field.CREATE_DATE, launchEntry.getCreateDate());
		document.addDate(Field.MODIFIED_DATE, launchEntry.getModifiedDate());

		User user = _userLocalService.fetchUser(launchEntry.getUserId());

		if (user != null) {
			document.addKeyword(Field.USER_ID, user.getUserId());
			document.addText(Field.USER_NAME, user.getFullName());
		}

		document.addKeyword("launchSetId", launchEntry.getLaunchSetId());

		document.addKeyword(
			Field.ENTRY_CLASS_NAME, launchEntry.getClassNameId());
		document.addKeyword(Field.ENTRY_CLASS_PK, launchEntry.getClassNameId());
	}

	@Reference
	private UserLocalService _userLocalService;

}