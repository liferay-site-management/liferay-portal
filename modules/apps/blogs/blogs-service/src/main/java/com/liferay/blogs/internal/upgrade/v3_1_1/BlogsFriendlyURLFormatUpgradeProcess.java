/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.internal.upgrade.v3_1_1;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Joao Victor Alves
 */
public class BlogsFriendlyURLFormatUpgradeProcess extends UpgradeProcess {

	public BlogsFriendlyURLFormatUpgradeProcess(
		BlogsEntryLocalService blogsEntryLocalService,
		ClassNameLocalService classNameLocalService,
		FriendlyURLEntryLocalService friendlyURLEntryLocalService) {

		_blogsEntryLocalService = blogsEntryLocalService;
		_classNameLocalService = classNameLocalService;
		_friendlyURLEntryLocalService = friendlyURLEntryLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select entryId from BlogsEntry where urlTitle like '%/'");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				BlogsEntry blogsEntry = _blogsEntryLocalService.fetchBlogsEntry(
					resultSet.getLong(1));

				String urlTitle = blogsEntry.getUrlTitle();

				urlTitle = urlTitle.substring(0, urlTitle.length() - 1);

				BlogsEntry duplicatedBlogsEntry =
					_blogsEntryLocalService.fetchEntry(
						blogsEntry.getGroupId(), urlTitle);

				if (duplicatedBlogsEntry != null) {
					urlTitle = _friendlyURLEntryLocalService.getUniqueUrlTitle(
						blogsEntry.getGroupId(),
						_classNameLocalService.getClassNameId(BlogsEntry.class),
						blogsEntry.getEntryId(), urlTitle, null);
				}

				blogsEntry.setUrlTitle(urlTitle);

				blogsEntry = _blogsEntryLocalService.updateBlogsEntry(
					blogsEntry);

				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setWorkflowAction(blogsEntry.getStatus());

				_friendlyURLEntryLocalService.addFriendlyURLEntry(
					blogsEntry.getGroupId(), BlogsEntry.class,
					blogsEntry.getEntryId(), urlTitle, serviceContext);
			}
		}
	}

	private final BlogsEntryLocalService _blogsEntryLocalService;
	private final ClassNameLocalService _classNameLocalService;
	private final FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

}