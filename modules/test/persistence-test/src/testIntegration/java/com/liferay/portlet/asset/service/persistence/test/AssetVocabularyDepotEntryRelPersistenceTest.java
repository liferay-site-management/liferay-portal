/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.exception.NoSuchVocabularyDepotEntryRelException;
import com.liferay.asset.kernel.model.AssetVocabularyDepotEntryRel;
import com.liferay.asset.kernel.service.AssetVocabularyDepotEntryRelLocalServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetVocabularyDepotEntryRelPersistence;
import com.liferay.asset.kernel.service.persistence.AssetVocabularyDepotEntryRelUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class AssetVocabularyDepotEntryRelPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = AssetVocabularyDepotEntryRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AssetVocabularyDepotEntryRel> iterator =
			_assetVocabularyDepotEntryRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
			_persistence.create(pk);

		Assert.assertNotNull(assetVocabularyDepotEntryRel);

		Assert.assertEquals(assetVocabularyDepotEntryRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetVocabularyDepotEntryRel newAssetVocabularyDepotEntryRel =
			addAssetVocabularyDepotEntryRel();

		_persistence.remove(newAssetVocabularyDepotEntryRel);

		AssetVocabularyDepotEntryRel existingAssetVocabularyDepotEntryRel =
			_persistence.fetchByPrimaryKey(
				newAssetVocabularyDepotEntryRel.getPrimaryKey());

		Assert.assertNull(existingAssetVocabularyDepotEntryRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetVocabularyDepotEntryRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetVocabularyDepotEntryRel newAssetVocabularyDepotEntryRel =
			_persistence.create(pk);

		newAssetVocabularyDepotEntryRel.setMvccVersion(
			RandomTestUtil.nextLong());

		newAssetVocabularyDepotEntryRel.setCtCollectionId(
			RandomTestUtil.nextLong());

		newAssetVocabularyDepotEntryRel.setUuid(RandomTestUtil.randomString());

		newAssetVocabularyDepotEntryRel.setCompanyId(RandomTestUtil.nextLong());

		newAssetVocabularyDepotEntryRel.setAssetVocabularyId(
			RandomTestUtil.nextLong());

		newAssetVocabularyDepotEntryRel.setDepotEntryId(
			RandomTestUtil.nextLong());

		_assetVocabularyDepotEntryRels.add(
			_persistence.update(newAssetVocabularyDepotEntryRel));

		AssetVocabularyDepotEntryRel existingAssetVocabularyDepotEntryRel =
			_persistence.findByPrimaryKey(
				newAssetVocabularyDepotEntryRel.getPrimaryKey());

		Assert.assertEquals(
			existingAssetVocabularyDepotEntryRel.getMvccVersion(),
			newAssetVocabularyDepotEntryRel.getMvccVersion());
		Assert.assertEquals(
			existingAssetVocabularyDepotEntryRel.getCtCollectionId(),
			newAssetVocabularyDepotEntryRel.getCtCollectionId());
		Assert.assertEquals(
			existingAssetVocabularyDepotEntryRel.getUuid(),
			newAssetVocabularyDepotEntryRel.getUuid());
		Assert.assertEquals(
			existingAssetVocabularyDepotEntryRel.
				getAssetVocabularyDepotEntryRelId(),
			newAssetVocabularyDepotEntryRel.
				getAssetVocabularyDepotEntryRelId());
		Assert.assertEquals(
			existingAssetVocabularyDepotEntryRel.getCompanyId(),
			newAssetVocabularyDepotEntryRel.getCompanyId());
		Assert.assertEquals(
			existingAssetVocabularyDepotEntryRel.getAssetVocabularyId(),
			newAssetVocabularyDepotEntryRel.getAssetVocabularyId());
		Assert.assertEquals(
			existingAssetVocabularyDepotEntryRel.getDepotEntryId(),
			newAssetVocabularyDepotEntryRel.getDepotEntryId());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByAssetVocabularyId() throws Exception {
		_persistence.countByAssetVocabularyId(RandomTestUtil.nextLong());

		_persistence.countByAssetVocabularyId(0L);
	}

	@Test
	public void testCountByDepotEntryId() throws Exception {
		_persistence.countByDepotEntryId(RandomTestUtil.nextLong());

		_persistence.countByDepotEntryId(0L);
	}

	@Test
	public void testCountByAVI_DEI() throws Exception {
		_persistence.countByAVI_DEI(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByAVI_DEI(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetVocabularyDepotEntryRel newAssetVocabularyDepotEntryRel =
			addAssetVocabularyDepotEntryRel();

		AssetVocabularyDepotEntryRel existingAssetVocabularyDepotEntryRel =
			_persistence.findByPrimaryKey(
				newAssetVocabularyDepotEntryRel.getPrimaryKey());

		Assert.assertEquals(
			existingAssetVocabularyDepotEntryRel,
			newAssetVocabularyDepotEntryRel);
	}

	@Test(expected = NoSuchVocabularyDepotEntryRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<AssetVocabularyDepotEntryRel>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"AssetVocabularyDepotEntryRel", "mvccVersion", true,
			"ctCollectionId", true, "uuid", true,
			"assetVocabularyDepotEntryRelId", true, "companyId", true,
			"assetVocabularyId", true, "depotEntryId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetVocabularyDepotEntryRel newAssetVocabularyDepotEntryRel =
			addAssetVocabularyDepotEntryRel();

		AssetVocabularyDepotEntryRel existingAssetVocabularyDepotEntryRel =
			_persistence.fetchByPrimaryKey(
				newAssetVocabularyDepotEntryRel.getPrimaryKey());

		Assert.assertEquals(
			existingAssetVocabularyDepotEntryRel,
			newAssetVocabularyDepotEntryRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetVocabularyDepotEntryRel missingAssetVocabularyDepotEntryRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetVocabularyDepotEntryRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		AssetVocabularyDepotEntryRel newAssetVocabularyDepotEntryRel1 =
			addAssetVocabularyDepotEntryRel();
		AssetVocabularyDepotEntryRel newAssetVocabularyDepotEntryRel2 =
			addAssetVocabularyDepotEntryRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetVocabularyDepotEntryRel1.getPrimaryKey());
		primaryKeys.add(newAssetVocabularyDepotEntryRel2.getPrimaryKey());

		Map<Serializable, AssetVocabularyDepotEntryRel>
			assetVocabularyDepotEntryRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, assetVocabularyDepotEntryRels.size());
		Assert.assertEquals(
			newAssetVocabularyDepotEntryRel1,
			assetVocabularyDepotEntryRels.get(
				newAssetVocabularyDepotEntryRel1.getPrimaryKey()));
		Assert.assertEquals(
			newAssetVocabularyDepotEntryRel2,
			assetVocabularyDepotEntryRels.get(
				newAssetVocabularyDepotEntryRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AssetVocabularyDepotEntryRel>
			assetVocabularyDepotEntryRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(assetVocabularyDepotEntryRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		AssetVocabularyDepotEntryRel newAssetVocabularyDepotEntryRel =
			addAssetVocabularyDepotEntryRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetVocabularyDepotEntryRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AssetVocabularyDepotEntryRel>
			assetVocabularyDepotEntryRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, assetVocabularyDepotEntryRels.size());
		Assert.assertEquals(
			newAssetVocabularyDepotEntryRel,
			assetVocabularyDepotEntryRels.get(
				newAssetVocabularyDepotEntryRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AssetVocabularyDepotEntryRel>
			assetVocabularyDepotEntryRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(assetVocabularyDepotEntryRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		AssetVocabularyDepotEntryRel newAssetVocabularyDepotEntryRel =
			addAssetVocabularyDepotEntryRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetVocabularyDepotEntryRel.getPrimaryKey());

		Map<Serializable, AssetVocabularyDepotEntryRel>
			assetVocabularyDepotEntryRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, assetVocabularyDepotEntryRels.size());
		Assert.assertEquals(
			newAssetVocabularyDepotEntryRel,
			assetVocabularyDepotEntryRels.get(
				newAssetVocabularyDepotEntryRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			AssetVocabularyDepotEntryRelLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<AssetVocabularyDepotEntryRel>() {

				@Override
				public void performAction(
					AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel) {

					Assert.assertNotNull(assetVocabularyDepotEntryRel);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		AssetVocabularyDepotEntryRel newAssetVocabularyDepotEntryRel =
			addAssetVocabularyDepotEntryRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetVocabularyDepotEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assetVocabularyDepotEntryRelId",
				newAssetVocabularyDepotEntryRel.
					getAssetVocabularyDepotEntryRelId()));

		List<AssetVocabularyDepotEntryRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetVocabularyDepotEntryRel existingAssetVocabularyDepotEntryRel =
			result.get(0);

		Assert.assertEquals(
			existingAssetVocabularyDepotEntryRel,
			newAssetVocabularyDepotEntryRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetVocabularyDepotEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assetVocabularyDepotEntryRelId", RandomTestUtil.nextLong()));

		List<AssetVocabularyDepotEntryRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		AssetVocabularyDepotEntryRel newAssetVocabularyDepotEntryRel =
			addAssetVocabularyDepotEntryRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetVocabularyDepotEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("assetVocabularyDepotEntryRelId"));

		Object newAssetVocabularyDepotEntryRelId =
			newAssetVocabularyDepotEntryRel.getAssetVocabularyDepotEntryRelId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"assetVocabularyDepotEntryRelId",
				new Object[] {newAssetVocabularyDepotEntryRelId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAssetVocabularyDepotEntryRelId = result.get(0);

		Assert.assertEquals(
			existingAssetVocabularyDepotEntryRelId,
			newAssetVocabularyDepotEntryRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetVocabularyDepotEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("assetVocabularyDepotEntryRelId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"assetVocabularyDepotEntryRelId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AssetVocabularyDepotEntryRel newAssetVocabularyDepotEntryRel =
			addAssetVocabularyDepotEntryRel();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newAssetVocabularyDepotEntryRel.getPrimaryKey()));
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromDatabase()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(true);
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromSession()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(false);
	}

	private void _testResetOriginalValuesWithDynamicQuery(boolean clearSession)
		throws Exception {

		AssetVocabularyDepotEntryRel newAssetVocabularyDepotEntryRel =
			addAssetVocabularyDepotEntryRel();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetVocabularyDepotEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assetVocabularyDepotEntryRelId",
				newAssetVocabularyDepotEntryRel.
					getAssetVocabularyDepotEntryRelId()));

		List<AssetVocabularyDepotEntryRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel) {

		Assert.assertEquals(
			Long.valueOf(assetVocabularyDepotEntryRel.getAssetVocabularyId()),
			ReflectionTestUtil.<Long>invoke(
				assetVocabularyDepotEntryRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "assetVocabularyId"));
		Assert.assertEquals(
			Long.valueOf(assetVocabularyDepotEntryRel.getDepotEntryId()),
			ReflectionTestUtil.<Long>invoke(
				assetVocabularyDepotEntryRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "depotEntryId"));
	}

	protected AssetVocabularyDepotEntryRel addAssetVocabularyDepotEntryRel()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
			_persistence.create(pk);

		assetVocabularyDepotEntryRel.setMvccVersion(RandomTestUtil.nextLong());

		assetVocabularyDepotEntryRel.setCtCollectionId(
			RandomTestUtil.nextLong());

		assetVocabularyDepotEntryRel.setUuid(RandomTestUtil.randomString());

		assetVocabularyDepotEntryRel.setCompanyId(RandomTestUtil.nextLong());

		assetVocabularyDepotEntryRel.setAssetVocabularyId(
			RandomTestUtil.nextLong());

		assetVocabularyDepotEntryRel.setDepotEntryId(RandomTestUtil.nextLong());

		_assetVocabularyDepotEntryRels.add(
			_persistence.update(assetVocabularyDepotEntryRel));

		return assetVocabularyDepotEntryRel;
	}

	private List<AssetVocabularyDepotEntryRel> _assetVocabularyDepotEntryRels =
		new ArrayList<AssetVocabularyDepotEntryRel>();
	private AssetVocabularyDepotEntryRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}