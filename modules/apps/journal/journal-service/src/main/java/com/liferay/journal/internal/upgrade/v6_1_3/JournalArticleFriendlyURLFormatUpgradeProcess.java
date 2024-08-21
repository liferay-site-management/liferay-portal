/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v6_1_3;

import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Joao Victor Alves
 */
public class JournalArticleFriendlyURLFormatUpgradeProcess
	extends UpgradeProcess {

	public JournalArticleFriendlyURLFormatUpgradeProcess(
		JournalArticleLocalService journalArticleLocalService,
		ClassNameLocalService classNameLocalService,
		FriendlyURLEntryLocalService friendlyURLEntryLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
		_classNameLocalService = classNameLocalService;
		_friendlyURLEntryLocalService = friendlyURLEntryLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select id_ from JournalArticle where urlTitle like '%/'");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				JournalArticle journalArticle =
					_journalArticleLocalService.fetchJournalArticle(
						resultSet.getLong(1));

				String urlTitle = journalArticle.getUrlTitle();

				urlTitle = urlTitle.substring(0, urlTitle.length() - 1);

				JournalArticle duplicatedJournalArticle =
					_journalArticleLocalService.fetchArticleByUrlTitle(
						journalArticle.getGroupId(), urlTitle);

				if (duplicatedJournalArticle != null) {
					urlTitle = _friendlyURLEntryLocalService.getUniqueUrlTitle(
						journalArticle.getGroupId(),
						_classNameLocalService.getClassNameId(
							JournalArticle.class),
						journalArticle.getClassPK(), urlTitle,
						journalArticle.getDefaultLanguageId());
				}

				journalArticle.setUrlTitle(urlTitle);

				journalArticle =
					_journalArticleLocalService.updateJournalArticle(
						journalArticle);

				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setWorkflowAction(journalArticle.getStatus());

				_friendlyURLEntryLocalService.addFriendlyURLEntry(
					journalArticle.getGroupId(), JournalArticle.class,
					journalArticle.getClassPK(), urlTitle, serviceContext);
			}
		}
	}

	private final ClassNameLocalService _classNameLocalService;
	private final FriendlyURLEntryLocalService _friendlyURLEntryLocalService;
	private final JournalArticleLocalService _journalArticleLocalService;

}