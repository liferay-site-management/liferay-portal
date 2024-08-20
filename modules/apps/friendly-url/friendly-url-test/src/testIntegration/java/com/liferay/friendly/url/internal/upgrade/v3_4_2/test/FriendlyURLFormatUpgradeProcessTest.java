/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.internal.upgrade.v3_4_2.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryLocalizationPersistence;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryLocalizationUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Joao Victor Alves
 */
@RunWith(Arquillian.class)
public class FriendlyURLFormatUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() {
		_languageId = LocaleUtil.toLanguageId(_locale);
		_friendlyURLEntryLocalizationPersistence =
			FriendlyURLEntryLocalizationUtil.getPersistence();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		for (long friendlyURLEntryId : _friendlyURLEntryIds) {
			_friendlyURLEntryLocalService.deleteFriendlyURLLocalizationEntry(
				friendlyURLEntryId, _languageId);
		}
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetFriendlyURLWithoutTrailingSlash() throws Exception {
		_createFriendlyURLEntryLocalization("test/");

		_runUpgrade();

		_friendlyURLEntryLocalization =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntryLocalization(
				_friendlyURLEntryId, _languageId);

		Assert.assertEquals(
			"test", _friendlyURLEntryLocalization.getUrlTitle());
	}

	@Test
	public void testGetFriendlyURLWithUniqueURL() throws Exception {
		_createFriendlyURLEntryLocalization("test");

		_createFriendlyURLEntryLocalization("test/");

		_runUpgrade();

		_friendlyURLEntryLocalization =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntryLocalization(
				_friendlyURLEntryId, _languageId);

		Assert.assertEquals(
			"test-1", _friendlyURLEntryLocalization.getUrlTitle());
	}

	private void _createFriendlyURLEntryLocalization(String urlTitle) {
		long friendlyURLEntryLocalizationId = RandomTestUtil.randomLong();

		_friendlyURLEntryLocalization =
			_friendlyURLEntryLocalizationPersistence.create(
				friendlyURLEntryLocalizationId);

		_friendlyURLEntryId = RandomTestUtil.randomLong();

		_friendlyURLEntryIds.add(_friendlyURLEntryId);

		_friendlyURLEntryLocalization.setFriendlyURLEntryId(
			_friendlyURLEntryId);

		_friendlyURLEntryLocalization.setLanguageId(_languageId);
		_friendlyURLEntryLocalization.setUrlTitle(urlTitle);
		_friendlyURLEntryLocalization.setGroupId(_group.getGroupId());
		_friendlyURLEntryLocalization.setClassNameId(
			_classNameLocalService.getClassNameId(FriendlyURLEntry.class));
		_friendlyURLEntryLocalization.setClassPK(RandomTestUtil.randomLong());

		_friendlyURLEntryLocalization =
			_friendlyURLEntryLocalService.updateFriendlyURLLocalization(
				_friendlyURLEntryLocalization);
	}

	private void _runUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.WARN)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 0, logEntries.size());

			_multiVMPool.clear();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.friendly.url.internal.upgrade.v3_4_2." +
			"FriendlyURLFormatUpgradeProcess";

	private static final List<Long> _friendlyURLEntryIds = new ArrayList<>();
	private static FriendlyURLEntryLocalizationPersistence
		_friendlyURLEntryLocalizationPersistence;

	@Inject
	private static FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@DeleteAfterTestRun
	private static Group _group;

	private static String _languageId;
	private static final Locale _locale = LocaleUtil.getSiteDefault();

	@Inject(
		filter = "(&(component.name=com.liferay.friendly.url.internal.upgrade.registry.FriendlyURLServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private long _friendlyURLEntryId;
	private FriendlyURLEntryLocalization _friendlyURLEntryLocalization;

	@Inject
	private MultiVMPool _multiVMPool;

}