/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.internal.upgrade.v3_4_2;

import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Joao Victor Alves
 */
public class FriendlyURLFormatUpgradeProcess extends UpgradeProcess {

	public FriendlyURLFormatUpgradeProcess(
		FriendlyURLEntryLocalService friendlyURLEntryLocalService) {

		_friendlyURLEntryLocalService = friendlyURLEntryLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select friendlyURLEntryId, languageId from " +
					"FriendlyURLEntryLocalization where urlTitle like '%/'");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				_friendlyURLEntryLocalService.
					deleteFriendlyURLLocalizationEntry(
						resultSet.getLong(1), resultSet.getString(2));
			}
		}
	}

	private final FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

}