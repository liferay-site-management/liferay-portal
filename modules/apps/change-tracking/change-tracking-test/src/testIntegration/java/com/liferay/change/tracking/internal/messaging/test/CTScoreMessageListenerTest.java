/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.messaging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.constants.CTDestinationNames;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.test.util.DLTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationStatistics;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Truong
 */
@RunWith(Arquillian.class)
public class CTScoreMessageListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testCTEntryCreateIncrementsScore() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_ctCollection1 = _addCTCollection();

		_addDLFileEntry(group, _ctCollection1);

		_awaitCTScoreMessages();

		Assert.assertTrue(_getScore(_ctCollection1.getCtCollectionId()) > 0);

		GroupTestUtil.deleteGroup(group);
	}

	@Test
	public void testCTEntryCrossCollectionMoveUpdatesScores() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_ctCollection1 = _addCTCollection();
		_ctCollection2 = _addCTCollection();

		_addDLFileEntry(group, _ctCollection1);

		_awaitCTScoreMessages();

		int score1 = _getScore(_ctCollection1.getCtCollectionId());
		int score2 = _getScore(_ctCollection2.getCtCollectionId());

		List<CTEntry> ctEntries = _ctEntryLocalService.getCTCollectionCTEntries(
			_ctCollection1.getCtCollectionId());

		CTEntry ctEntry = ctEntries.get(0);

		ctEntry.setCtCollectionId(_ctCollection2.getCtCollectionId());

		_ctEntryLocalService.updateCTEntry(ctEntry);

		_awaitCTScoreMessages();

		int movedScore1 = _getScore(_ctCollection1.getCtCollectionId());
		int movedScore2 = _getScore(_ctCollection2.getCtCollectionId());

		Assert.assertTrue(movedScore1 < score1);
		Assert.assertEquals(score1 - movedScore1, movedScore2 - score2);

		GroupTestUtil.deleteGroup(group);
	}

	@Test
	public void testCTEntryDeleteDecrementsScore() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_ctCollection1 = _addCTCollection();

		DLFileEntry dlFileEntry = _addDLFileEntry(group, _ctCollection1);

		_awaitCTScoreMessages();

		int score1 = _getScore(_ctCollection1.getCtCollectionId());

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection1.getCtCollectionId())) {

			_dlFileEntryLocalService.deleteFileEntry(
				dlFileEntry.getFileEntryId());
		}

		_awaitCTScoreMessages();

		Assert.assertTrue(
			_getScore(_ctCollection1.getCtCollectionId()) < score1);

		GroupTestUtil.deleteGroup(group);
	}

	@Test
	public void testCTEntryEscalatesFromMediumToLargeClassification()
		throws Exception {

		_ctCollection1 = _addCTCollection();

		long ctCollectionId = _ctCollection1.getCtCollectionId();

		_ctCollectionLocalService.updateScoreSizeClassification(
			ctCollectionId, CTConstants.SCORE_SIZE_CLASSIFICATION_MEDIUM);

		_setScore(ctCollectionId, 40000);

		int notificationCount = _getSizeClassificationNotificationJSONObjects(
			ctCollectionId
		).size();

		_sendCTScoreMessage(ctCollectionId, true);

		_awaitCTScoreMessages();

		Assert.assertEquals(
			CTConstants.SCORE_SIZE_CLASSIFICATION_LARGE,
			_getScoreSizeClassification(ctCollectionId));

		List<JSONObject> jsonObjects =
			_getSizeClassificationNotificationJSONObjects(ctCollectionId);

		Assert.assertEquals(
			jsonObjects.toString(), notificationCount + 1, jsonObjects.size());

		JSONObject jsonObject = jsonObjects.get(jsonObjects.size() - 1);

		Assert.assertEquals(
			CTConstants.SCORE_SIZE_CLASSIFICATION_MEDIUM,
			jsonObject.getString("originalSizeClassification"));
		Assert.assertEquals(
			CTConstants.SCORE_SIZE_CLASSIFICATION_LARGE,
			jsonObject.getString("sizeClassification"));
	}

	@Test
	public void testCTEntryEscalatesFromSmallToMediumClassification()
		throws Exception {

		_ctCollection1 = _addCTCollection();

		long ctCollectionId = _ctCollection1.getCtCollectionId();

		_setScore(ctCollectionId, 15000);

		int notificationCount = _getSizeClassificationNotificationJSONObjects(
			ctCollectionId
		).size();

		_sendCTScoreMessage(ctCollectionId, true);

		_awaitCTScoreMessages();

		Assert.assertEquals(
			CTConstants.SCORE_SIZE_CLASSIFICATION_MEDIUM,
			_getScoreSizeClassification(ctCollectionId));

		List<JSONObject> jsonObjects =
			_getSizeClassificationNotificationJSONObjects(ctCollectionId);

		Assert.assertEquals(
			jsonObjects.toString(), notificationCount + 1, jsonObjects.size());

		JSONObject jsonObject = jsonObjects.get(jsonObjects.size() - 1);

		Assert.assertEquals(
			CTConstants.SCORE_SIZE_CLASSIFICATION_SMALL,
			jsonObject.getString("originalSizeClassification"));
		Assert.assertEquals(
			CTConstants.SCORE_SIZE_CLASSIFICATION_MEDIUM,
			jsonObject.getString("sizeClassification"));
	}

	@Test
	public void testCTEntryOnceLargeSkipsCacheEntirely() throws Exception {
		_ctCollection1 = _addCTCollection();

		long ctCollectionId = _ctCollection1.getCtCollectionId();

		_ctCollectionLocalService.updateScoreSizeClassification(
			ctCollectionId, CTConstants.SCORE_SIZE_CLASSIFICATION_LARGE);

		Assert.assertFalse(_hasScoreCacheEntry(ctCollectionId));

		_sendCTScoreMessage(ctCollectionId, true);

		_awaitCTScoreMessages();

		Assert.assertFalse(_hasScoreCacheEntry(ctCollectionId));
		Assert.assertEquals(
			CTConstants.SCORE_SIZE_CLASSIFICATION_LARGE,
			_getScoreSizeClassification(ctCollectionId));
	}

	@Test
	public void testCTEntryWithinSameTierDoesNotRewriteClassification()
		throws Exception {

		_ctCollection1 = _addCTCollection();

		long ctCollectionId = _ctCollection1.getCtCollectionId();

		_setScore(ctCollectionId, 15000);

		_sendCTScoreMessage(ctCollectionId, true);

		_awaitCTScoreMessages();

		Assert.assertEquals(
			CTConstants.SCORE_SIZE_CLASSIFICATION_MEDIUM,
			_getScoreSizeClassification(ctCollectionId));

		int notificationCount = _getSizeClassificationNotificationJSONObjects(
			ctCollectionId
		).size();

		_sendCTScoreMessage(ctCollectionId, true);

		_awaitCTScoreMessages();

		Assert.assertEquals(
			CTConstants.SCORE_SIZE_CLASSIFICATION_MEDIUM,
			_getScoreSizeClassification(ctCollectionId));
		Assert.assertEquals(
			notificationCount,
			_getSizeClassificationNotificationJSONObjects(
				ctCollectionId
			).size());
	}

	@Test
	public void testReadOnlyCollectionIgnoresScoreUpdateMessage()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		_ctCollection1 = _addCTCollection();

		_addDLFileEntry(group, _ctCollection1);

		_awaitCTScoreMessages();

		int score1 = _getScore(_ctCollection1.getCtCollectionId());

		_ctCollection1.setStatus(WorkflowConstants.STATUS_APPROVED);

		_ctCollection1 = _ctCollectionLocalService.updateCTCollection(
			_ctCollection1);

		_sendCTScoreMessage(_ctCollection1.getCtCollectionId(), true);

		_awaitCTScoreMessages();

		Assert.assertEquals(
			score1, _getScore(_ctCollection1.getCtCollectionId()));

		GroupTestUtil.deleteGroup(group);
	}

	private CTCollection _addCTCollection() throws Exception {
		return _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);
	}

	private DLFileEntry _addDLFileEntry(Group group, CTCollection ctCollection)
		throws Exception {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			DLFolder dlFolder = DLTestUtil.addDLFolder(group.getGroupId());

			return DLTestUtil.addDLFileEntry(dlFolder.getFolderId());
		}
	}

	private void _awaitCTScoreMessages() throws Exception {
		Destination destination = MessageBusUtil.getDestination(
			CTDestinationNames.CT_SCORE);

		DestinationStatistics destinationStatistics =
			destination.getDestinationStatistics();

		int i = 0;

		while ((destinationStatistics.getActiveThreadCount() > 0) ||
			   (destinationStatistics.getPendingMessageCount() > 0)) {

			if (i++ > 60) {
				break;
			}

			Thread.sleep(500);

			destinationStatistics = destination.getDestinationStatistics();
		}
	}

	private Object _getCTCollectionScoreCache() {
		return ReflectionTestUtil.getFieldValue(
			_messageListener, "_ctCollectionScoreCache");
	}

	private int _getScore(long ctCollectionId) {
		return ReflectionTestUtil.invoke(
			_getCTCollectionScoreCache(), "getScore",
			new Class<?>[] {long.class}, ctCollectionId);
	}

	private String _getScoreSizeClassification(long ctCollectionId) {
		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctCollectionId);

		return ctCollection.getScoreSizeClassification();
	}

	private List<JSONObject> _getSizeClassificationNotificationJSONObjects(
			long ctCollectionId)
		throws Exception {

		List<JSONObject> jsonObjects = new ArrayList<>();

		List<UserNotificationEvent> userNotificationEvents =
			_userNotificationEventLocalService.getUserNotificationEvents(
				TestPropsValues.getUserId());

		for (UserNotificationEvent userNotificationEvent :
				userNotificationEvents) {

			if (!Objects.equals(
					CTPortletKeys.PUBLICATIONS,
					userNotificationEvent.getType())) {

				continue;
			}

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				userNotificationEvent.getPayload());

			if ((jsonObject.getLong("ctCollectionId") == ctCollectionId) &&
				(jsonObject.getInt("notificationType") ==
					UserNotificationDefinition.
						NOTIFICATION_TYPE_UPDATE_ENTRY)) {

				jsonObjects.add(jsonObject);
			}
		}

		return jsonObjects;
	}

	private boolean _hasScoreCacheEntry(long ctCollectionId) {
		PortalCache<Long, Integer> portalCache =
			ReflectionTestUtil.getFieldValue(
				_getCTCollectionScoreCache(), "_portalCache");

		if (portalCache.get(ctCollectionId) != null) {
			return true;
		}

		return false;
	}

	private void _sendCTScoreMessage(long ctCollectionId, boolean increment) {
		Message message = new Message();

		message.setValues(
			HashMapBuilder.<String, Object>put(
				"ctCollectionId", ctCollectionId
			).put(
				"increment", increment
			).put(
				"modelClassNameId",
				_classNameLocalService.getClassNameId(DLFileEntry.class)
			).build());

		MessageBusUtil.sendMessage(CTDestinationNames.CT_SCORE, message);
	}

	private void _setScore(long ctCollectionId, int score) {
		ReflectionTestUtil.invoke(
			_getCTCollectionScoreCache(), "setScore",
			new Class<?>[] {long.class, int.class}, ctCollectionId, score);
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private CTCollection _ctCollection1;

	@DeleteAfterTestRun
	private CTCollection _ctCollection2;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private CTEntryLocalService _ctEntryLocalService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject(
		filter = "(&(component.name=com.liferay.change.tracking.internal.messaging.CTScoreMessageListener))"
	)
	private MessageListener _messageListener;

	@Inject
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}