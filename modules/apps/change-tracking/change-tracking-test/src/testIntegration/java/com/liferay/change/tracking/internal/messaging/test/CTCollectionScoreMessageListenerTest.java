/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.messaging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTDestinationNames;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.test.CTCollectionLocalServiceTest;
import com.liferay.journal.model.JournalFolder;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Truong
 */
@RunWith(Arquillian.class)
public class CTCollectionScoreMessageListenerTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, CTCollectionLocalServiceTest.class.getSimpleName(), null);
	}

	@Test
	public void testCTCollectionScore() throws Exception {
		_invokeMessageListener();

		_ctCollection = _ctCollectionLocalService.fetchCTCollection(
			_ctCollection.getCtCollectionId());

		Assert.assertEquals(4, _ctCollection.getScore());
	}

	private void _invokeMessageListener() throws Exception {
		Message message = new Message();

		message.put("ctCollectionId", _ctCollection.getCtCollectionId());
		message.put(
			"modelClassNameId",
			_classNameLocalService.getClassNameId(JournalFolder.class));
		message.put("increment", true);

		_messageListener.receive(message);
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject(
		filter = "destination.name=" + CTDestinationNames.CT_COLLECTION_SCORE
	)
	private MessageListener _messageListener;

}