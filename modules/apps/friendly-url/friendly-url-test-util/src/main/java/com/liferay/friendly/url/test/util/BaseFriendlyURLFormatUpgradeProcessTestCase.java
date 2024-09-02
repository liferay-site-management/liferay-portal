/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.test.util;

import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryLocalizationPersistence;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryLocalizationUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author Joao Victor Alves
 */
public abstract class BaseFriendlyURLFormatUpgradeProcessTestCase
	<T extends BaseModel<T>> {

	@BeforeClass
	public static void setUpClass() {
		languageId = LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault());
		_friendlyURLEntryLocalizationPersistence =
			FriendlyURLEntryLocalizationUtil.getPersistence();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		for (Long friendlyURLEntryId : friendlyURLEntryIds) {
			friendlyURLEntryLocalService.deleteFriendlyURLLocalizationEntry(
				friendlyURLEntryId,
				LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()));
		}

		friendlyURLEntryIds.clear();
	}

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();
	}

	protected void createFriendlyURLEntryLocalization(
		String urlTitle, Class<T> modelClass) {

		modelId = RandomTestUtil.randomLong();

		friendlyURLEntryIds.add(modelId);

		friendlyURLEntryLocalization =
			_friendlyURLEntryLocalizationPersistence.create(modelId);

		friendlyURLEntryLocalization.setFriendlyURLEntryId(modelId);

		friendlyURLEntryLocalization.setLanguageId(languageId);
		friendlyURLEntryLocalization.setUrlTitle(urlTitle);
		friendlyURLEntryLocalization.setGroupId(group.getGroupId());
		friendlyURLEntryLocalization.setClassNameId(
			_classNameLocalService.getClassNameId(modelClass));
		friendlyURLEntryLocalization.setClassPK(RandomTestUtil.randomLong());

		friendlyURLEntryLocalization =
			friendlyURLEntryLocalService.updateFriendlyURLLocalization(
				friendlyURLEntryLocalization);
	}

	protected static final List<Long> friendlyURLEntryIds = new ArrayList<>();

	@Inject
	protected static FriendlyURLEntryLocalService friendlyURLEntryLocalService;

	@DeleteAfterTestRun
	protected static Group group;

	protected static String languageId;

	protected FriendlyURLEntryLocalization friendlyURLEntryLocalization;
	protected long modelId;

	private static FriendlyURLEntryLocalizationPersistence
		_friendlyURLEntryLocalizationPersistence;

	@Inject
	private ClassNameLocalService _classNameLocalService;

}