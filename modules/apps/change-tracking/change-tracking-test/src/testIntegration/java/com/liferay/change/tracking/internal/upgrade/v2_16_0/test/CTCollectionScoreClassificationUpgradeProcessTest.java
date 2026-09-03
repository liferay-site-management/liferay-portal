/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.upgrade.v2_16_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.After;
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
public class CTCollectionScoreClassificationUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_db = DBManagerUtil.getDB();

		_db.runSQL("DROP_TABLE_IF_EXISTS(CTScore)");

		_db.runSQL(
			StringBundler.concat(
				"create table CTScore (mvccVersion LONG default 0 not ",
				"null,ctScoreId LONG not null primary key,companyId LONG,",
				"ctCollectionId LONG,score INTEGER)"));

		_upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.change.tracking.internal.upgrade.v2_16_0." +
				"CTCollectionScoreClassificationUpgradeProcess");
	}

	@After
	public void tearDown() throws Exception {
		_db.runSQL("DROP_TABLE_IF_EXISTS(CTScore)");
	}

	@Test
	public void testUpgrade() throws Exception {
		CTCollection smallCTCollection = _addCTCollection();
		CTCollection mediumCTCollection = _addCTCollection();
		CTCollection largeCTCollection = _addCTCollection();
		CTCollection nullClassificationCTCollection = _addCTCollection();

		_addCTScore(smallCTCollection.getCtCollectionId(), 1000);
		_addCTScore(mediumCTCollection.getCtCollectionId(), 15000);
		_addCTScore(largeCTCollection.getCtCollectionId(), 25000);

		_clearScoreSizeClassification(
			nullClassificationCTCollection.getCtCollectionId());

		_upgradeProcess.upgrade();

		CacheRegistryUtil.clear();

		Assert.assertEquals(
			CTConstants.SCORE_SIZE_CLASSIFICATION_SMALL,
			_getScoreSizeClassification(smallCTCollection));
		Assert.assertEquals(
			CTConstants.SCORE_SIZE_CLASSIFICATION_MEDIUM,
			_getScoreSizeClassification(mediumCTCollection));
		Assert.assertEquals(
			CTConstants.SCORE_SIZE_CLASSIFICATION_LARGE,
			_getScoreSizeClassification(largeCTCollection));
		Assert.assertEquals(
			CTConstants.SCORE_SIZE_CLASSIFICATION_SMALL,
			_getScoreSizeClassification(nullClassificationCTCollection));

		try (Connection connection = DataAccess.getConnection()) {
			Assert.assertFalse(
				new DBInspector(
					connection
				).hasTable(
					"CTScore"
				));
		}
	}

	private CTCollection _addCTCollection() throws Exception {
		return _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);
	}

	private void _addCTScore(long ctCollectionId, int score) throws Exception {
		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				"insert into CTScore (ctScoreId, companyId, ctCollectionId, " +
					"score) values (?, ?, ?, ?)")) {

			preparedStatement.setLong(1, _counterLocalService.increment());
			preparedStatement.setLong(2, TestPropsValues.getCompanyId());
			preparedStatement.setLong(3, ctCollectionId);
			preparedStatement.setInt(4, score);

			preparedStatement.executeUpdate();
		}
	}

	private void _clearScoreSizeClassification(long ctCollectionId)
		throws Exception {

		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				"update CTCollection set scoreSizeClassification = null " +
					"where ctCollectionId = ?")) {

			preparedStatement.setLong(1, ctCollectionId);

			preparedStatement.executeUpdate();
		}
	}

	private String _getScoreSizeClassification(CTCollection ctCollection)
		throws Exception {

		CTCollection latestCTCollection =
			_ctCollectionLocalService.getCTCollection(
				ctCollection.getCtCollectionId());

		return latestCTCollection.getScoreSizeClassification();
	}

	@Inject
	private CounterLocalService _counterLocalService;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	private DB _db;
	private UpgradeProcess _upgradeProcess;

	@Inject(
		filter = "(&(component.name=com.liferay.change.tracking.internal.upgrade.registry.ChangeTrackingServiceUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}