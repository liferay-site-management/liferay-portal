/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.score.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTDestinationNames;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.test.util.DLTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationStatistics;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Truong
 */
@RunWith(Arquillian.class)
public class CTCollectionScoreCacheTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testCTCollectionDeletionRemovesCacheEntry() throws Exception {
		Group group = GroupTestUtil.addGroup();

		CTCollection ctCollection = _addCTCollectionWithDLFileEntries(group, 1);

		long ctCollectionId = ctCollection.getCtCollectionId();

		_setScore(ctCollectionId, RandomTestUtil.randomInt());

		_ctCollectionLocalService.deleteCTCollection(ctCollection);

		Assert.assertFalse(_hasScoreCacheEntry(ctCollectionId));

		GroupTestUtil.deleteGroup(group);
	}

	@Test
	public void testGetScoreComputesOnDemandFromCTEntryCounts()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		_ctCollection1 = _addCTCollectionWithDLFileEntries(group, 1);
		_ctCollection2 = _addCTCollectionWithDLFileEntries(group, 5);

		int score1 = _getScore(_ctCollection1.getCtCollectionId());
		int score2 = _getScore(_ctCollection2.getCtCollectionId());

		Assert.assertTrue(score1 > 0);
		Assert.assertTrue(score2 > score1);

		GroupTestUtil.deleteGroup(group);
	}

	@Test
	public void testGetScoreIgnoresAmbientCTContext() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_ctCollection1 = _addCTCollectionWithDLFileEntries(group, 3);
		_ctCollection2 = _addCTCollectionWithDLFileEntries(group, 3);

		int score1 = _getScore(_ctCollection1.getCtCollectionId());

		int score2;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection1.getCtCollectionId())) {

			score2 = _getScore(_ctCollection2.getCtCollectionId());
		}

		Assert.assertEquals(score1, score2);

		GroupTestUtil.deleteGroup(group);
	}

	private CTCollection _addCTCollectionWithDLFileEntries(
			Group group, int count)
		throws Exception {

		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			DLFolder dlFolder = DLTestUtil.addDLFolder(group.getGroupId());

			for (int i = 0; i < count; i++) {
				DLTestUtil.addDLFileEntry(dlFolder.getFolderId());
			}
		}

		_awaitCTScoreMessages();

		return ctCollection;
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

	private boolean _hasScoreCacheEntry(long ctCollectionId) {
		PortalCache<Long, Integer> portalCache =
			ReflectionTestUtil.getFieldValue(
				_getCTCollectionScoreCache(), "_portalCache");

		if (portalCache.get(ctCollectionId) != null) {
			return true;
		}

		return false;
	}

	private void _setScore(long ctCollectionId, int score) {
		ReflectionTestUtil.invoke(
			_getCTCollectionScoreCache(), "setScore",
			new Class<?>[] {long.class, int.class}, ctCollectionId, score);
	}

	@DeleteAfterTestRun
	private CTCollection _ctCollection1;

	@DeleteAfterTestRun
	private CTCollection _ctCollection2;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject(
		filter = "(&(component.name=com.liferay.change.tracking.internal.messaging.CTScoreMessageListener))"
	)
	private MessageListener _messageListener;

}