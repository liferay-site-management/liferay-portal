/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.feature.flag.test.util.FeatureFlagTestHelper;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.test.util.JournalFolderFixture;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gislayne Vitorino
 */
@RunWith(Arquillian.class)
public class CTProcessLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_featureFlagTestHelper = new FeatureFlagTestHelper();

		_guestUserId = _userLocalService.getGuestUserId(
			TestPropsValues.getCompanyId());

		_journalFolderClassNameId = _classNameLocalService.getClassNameId(
			JournalFolder.class);
	}

	@After
	public void tearDown() throws Exception {
		_featureFlagTestHelper.setFeatureFlagValue(
			TestPropsValues.getCompanyId(), _FEATURE_FLAG_KEY, false);

		_featureFlagTestHelper.tearDown();

		CTPreferences guestPreferences =
			_ctPreferencesLocalService.fetchCTPreferences(
				TestPropsValues.getCompanyId(), _guestUserId);

		if (guestPreferences != null) {
			guestPreferences.setCtCollectionId(
				CTConstants.CT_COLLECTION_ID_PRODUCTION);

			_ctPreferencesLocalService.updateCTPreferences(guestPreferences);
		}
	}

	@Test
	public void testAddCTProcessWithInstantPublish() throws Exception {
		_featureFlagTestHelper.setFeatureFlagValue(
			TestPropsValues.getCompanyId(), _FEATURE_FLAG_KEY, true);

		CTCollection ctCollection = _addCTCollectionWithContent();

		try (LogCapture ctProcessLogCapture =
				LoggerTestUtil.configureLog4JLogger(
					"com.liferay.change.tracking.service.impl." +
						"CTProcessLocalServiceImpl",
					LoggerTestUtil.DEBUG);
			LogCapture ctPreferencesLogCapture =
				LoggerTestUtil.configureLog4JLogger(
					"com.liferay.change.tracking.internal.spi.listener." +
						"CTPreferencesEventListener",
					LoggerTestUtil.INFO)) {

			_ctProcessLocalService.addCTProcess(
				TestPropsValues.getUserId(), ctCollection.getCtCollectionId());

			List<LogEntry> ctProcessLogEntries =
				ctProcessLogCapture.getLogEntries();

			Assert.assertEquals(
				ctProcessLogEntries.toString(), 1, ctProcessLogEntries.size());

			LogEntry ctProcessLogEntry = ctProcessLogEntries.get(0);

			Assert.assertEquals(
				"Using publication " + ctCollection.getCtCollectionId() +
					" temporarily in place of production",
				ctProcessLogEntry.getMessage());

			CTPreferences userPreferences =
				_ctPreferencesLocalService.getCTPreferences(
					TestPropsValues.getCompanyId(),
					TestPropsValues.getUserId());

			Assert.assertEquals(
				CTConstants.CT_COLLECTION_ID_PRODUCTION,
				userPreferences.getCtCollectionId());

			BackgroundTask backgroundTask =
				_backgroundTaskLocalService.getBackgroundTask(
					_ctProcessLocalService.getCTProcesses(
						ctCollection.getCtCollectionId()
					).get(
						0
					).getBackgroundTaskId());

			Assert.assertEquals(
				BackgroundTaskConstants.STATUS_SUCCESSFUL,
				backgroundTask.getStatus());

			List<LogEntry> ctPreferencesLogEntries =
				ctPreferencesLogCapture.getLogEntries();

			Assert.assertEquals(
				ctPreferencesLogEntries.toString(), 1,
				ctPreferencesLogEntries.size());

			LogEntry ctPreferencesLogEntry = ctPreferencesLogEntries.get(0);

			Assert.assertTrue(
				ctPreferencesLogEntry.getMessage(
				).contains(
					"was published. Production is live."
				));

			CTPreferences guestPreferences =
				_ctPreferencesLocalService.getCTPreferences(
					TestPropsValues.getCompanyId(), _guestUserId);

			Assert.assertEquals(
				CTConstants.CT_COLLECTION_ID_PRODUCTION,
				guestPreferences.getCtCollectionId());
		}
	}

	@Test
	public void testAddCTProcessWithInstantPublishConflict() throws Exception {
		_featureFlagTestHelper.setFeatureFlagValue(
			TestPropsValues.getCompanyId(), _FEATURE_FLAG_KEY, true);

		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		String conflictingFolderName = RandomTestUtil.randomString();

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			_journalFolderFixture.addFolder(
				_group.getGroupId(), conflictingFolderName);
		}

		_journalFolderFixture.addFolder(
			_group.getGroupId(), conflictingFolderName);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.background.task.internal.messaging." +
					"BackgroundTaskMessageListener",
				LoggerTestUtil.ERROR)) {

			CTProcess ctProcess = _ctProcessLocalService.addCTProcess(
				TestPropsValues.getUserId(), ctCollection.getCtCollectionId());

			BackgroundTask backgroundTask =
				_backgroundTaskLocalService.getBackgroundTask(
					ctProcess.getBackgroundTaskId());

			Assert.assertEquals(
				BackgroundTaskConstants.STATUS_FAILED,
				backgroundTask.getStatus());
		}

		CTPreferences guestPreferences =
			_ctPreferencesLocalService.getCTPreferences(
				TestPropsValues.getCompanyId(), _guestUserId);

		Assert.assertEquals(
			CTConstants.CT_COLLECTION_ID_PRODUCTION,
			guestPreferences.getCtCollectionId());
	}

	@Test
	public void testAddCTProcessWithInstantPublishUserInOtherPublication()
		throws Exception {

		_featureFlagTestHelper.setFeatureFlagValue(
			TestPropsValues.getCompanyId(), _FEATURE_FLAG_KEY, true);

		CTCollection otherCTCollection =
			_ctCollectionLocalService.addCTCollection(
				null, TestPropsValues.getCompanyId(),
				TestPropsValues.getUserId(), 0, RandomTestUtil.randomString(),
				null);

		CTPreferences userPreferences =
			_ctPreferencesLocalService.getCTPreferences(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		userPreferences.setCtCollectionId(
			otherCTCollection.getCtCollectionId());

		_ctPreferencesLocalService.updateCTPreferences(userPreferences);

		CTCollection ctCollection = _addCTCollectionWithContent();

		_ctProcessLocalService.addCTProcess(
			TestPropsValues.getUserId(), ctCollection.getCtCollectionId());

		userPreferences = _ctPreferencesLocalService.getCTPreferences(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		Assert.assertEquals(
			otherCTCollection.getCtCollectionId(),
			userPreferences.getCtCollectionId());
	}

	@Test
	public void testCannotAddCTProcessWithEmptyCTCollection()
		throws PortalException {

		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, CTCollectionLocalServiceTest.class.getSimpleName(), null);

		try {
			_ctProcessLocalService.addCTProcess(
				ctCollection.getUserId(), ctCollection.getCtCollectionId());

			Assert.fail();
		}
		catch (IllegalStateException illegalStateException) {
			Assert.assertEquals(
				"Change tracking collection is empty " + ctCollection,
				illegalStateException.getMessage());
		}
	}

	@Test
	public void testDeleteCTCollectionWithCTProcess() throws Exception {
		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, CTCollectionLocalServiceTest.class.getSimpleName(), null);

		String conflictingFolderName = "conflictingFolderName";

		JournalFolder ctJournalFolder = null;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			ctJournalFolder = _journalFolderFixture.addFolder(
				_group.getGroupId(), conflictingFolderName);

			_journalFolderFixture.addFolder(
				_group.getGroupId(), RandomTestUtil.randomString());
		}

		_journalFolderFixture.addFolder(
			_group.getGroupId(), conflictingFolderName);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.background.task.internal.messaging." +
					"BackgroundTaskMessageListener",
				LoggerTestUtil.ERROR)) {

			CTProcess ctProcess = _ctProcessLocalService.addCTProcess(
				ctCollection.getUserId(), ctCollection.getCtCollectionId());

			BackgroundTask backgroundTask =
				_backgroundTaskLocalService.getBackgroundTask(
					ctProcess.getBackgroundTaskId());

			Assert.assertEquals(
				BackgroundTaskConstants.STATUS_FAILED,
				backgroundTask.getStatus());

			ctProcess = _ctProcessLocalService.deleteCTProcess(
				ctProcess.getCtProcessId());

			ctCollection = _ctCollectionLocalService.fetchCTCollection(
				ctProcess.getCtCollectionId());

			Assert.assertNotNull(ctCollection);

			_ctCollectionLocalService.discardCTEntry(
				ctCollection.getCtCollectionId(), _journalFolderClassNameId,
				ctJournalFolder.getFolderId(), false);

			ctProcess = _ctProcessLocalService.addCTProcess(
				ctCollection.getUserId(), ctCollection.getCtCollectionId());

			backgroundTask = _backgroundTaskLocalService.getBackgroundTask(
				ctProcess.getBackgroundTaskId());

			Assert.assertEquals(
				BackgroundTaskConstants.STATUS_SUCCESSFUL,
				backgroundTask.getStatus());

			ctProcess = _ctProcessLocalService.deleteCTProcess(
				ctProcess.getCtProcessId());

			ctCollection = _ctCollectionLocalService.fetchCTCollection(
				ctProcess.getCtProcessId());

			Assert.assertNull(ctCollection);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Unable to execute background task", logEntry.getMessage());
		}
	}

	private CTCollection _addCTCollectionWithContent() throws Exception {
		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			_journalFolderFixture.addFolder(
				_group.getGroupId(), RandomTestUtil.randomString());
		}

		return ctCollection;
	}

	private static final String _FEATURE_FLAG_KEY = "LPD-39203";

	@Inject
	private static JournalFolderLocalService _journalFolderLocalService;

	@Inject
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Inject
	private CTProcessLocalService _ctProcessLocalService;

	private FeatureFlagTestHelper _featureFlagTestHelper;

	@DeleteAfterTestRun
	private Group _group;

	private long _guestUserId;
	private long _journalFolderClassNameId;
	private final JournalFolderFixture _journalFolderFixture =
		new JournalFolderFixture(_journalFolderLocalService);

	@Inject
	private UserLocalService _userLocalService;

}