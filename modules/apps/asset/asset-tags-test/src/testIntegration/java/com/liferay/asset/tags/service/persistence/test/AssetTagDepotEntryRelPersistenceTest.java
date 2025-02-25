/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.tags.exception.NoSuchDepotEntryRelException;
import com.liferay.asset.tags.model.AssetTagDepotEntryRel;
import com.liferay.asset.tags.service.AssetTagDepotEntryRelLocalServiceUtil;
import com.liferay.asset.tags.service.persistence.AssetTagDepotEntryRelPersistence;
import com.liferay.asset.tags.service.persistence.AssetTagDepotEntryRelUtil;
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
public class AssetTagDepotEntryRelPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.asset.tags.service"));

	@Before
	public void setUp() {
		_persistence = AssetTagDepotEntryRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AssetTagDepotEntryRel> iterator =
			_assetTagDepotEntryRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetTagDepotEntryRel assetTagDepotEntryRel = _persistence.create(pk);

		Assert.assertNotNull(assetTagDepotEntryRel);

		Assert.assertEquals(assetTagDepotEntryRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetTagDepotEntryRel newAssetTagDepotEntryRel =
			addAssetTagDepotEntryRel();

		_persistence.remove(newAssetTagDepotEntryRel);

		AssetTagDepotEntryRel existingAssetTagDepotEntryRel =
			_persistence.fetchByPrimaryKey(
				newAssetTagDepotEntryRel.getPrimaryKey());

		Assert.assertNull(existingAssetTagDepotEntryRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetTagDepotEntryRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetTagDepotEntryRel newAssetTagDepotEntryRel = _persistence.create(
			pk);

		newAssetTagDepotEntryRel.setMvccVersion(RandomTestUtil.nextLong());

		newAssetTagDepotEntryRel.setCtCollectionId(RandomTestUtil.nextLong());

		newAssetTagDepotEntryRel.setUuid(RandomTestUtil.randomString());

		newAssetTagDepotEntryRel.setCompanyId(RandomTestUtil.nextLong());

		newAssetTagDepotEntryRel.setAssetTagId(RandomTestUtil.nextLong());

		newAssetTagDepotEntryRel.setDepotEntryId(RandomTestUtil.nextLong());

		_assetTagDepotEntryRels.add(
			_persistence.update(newAssetTagDepotEntryRel));

		AssetTagDepotEntryRel existingAssetTagDepotEntryRel =
			_persistence.findByPrimaryKey(
				newAssetTagDepotEntryRel.getPrimaryKey());

		Assert.assertEquals(
			existingAssetTagDepotEntryRel.getMvccVersion(),
			newAssetTagDepotEntryRel.getMvccVersion());
		Assert.assertEquals(
			existingAssetTagDepotEntryRel.getCtCollectionId(),
			newAssetTagDepotEntryRel.getCtCollectionId());
		Assert.assertEquals(
			existingAssetTagDepotEntryRel.getUuid(),
			newAssetTagDepotEntryRel.getUuid());
		Assert.assertEquals(
			existingAssetTagDepotEntryRel.getAssetTagDepotEntryRelId(),
			newAssetTagDepotEntryRel.getAssetTagDepotEntryRelId());
		Assert.assertEquals(
			existingAssetTagDepotEntryRel.getCompanyId(),
			newAssetTagDepotEntryRel.getCompanyId());
		Assert.assertEquals(
			existingAssetTagDepotEntryRel.getAssetTagId(),
			newAssetTagDepotEntryRel.getAssetTagId());
		Assert.assertEquals(
			existingAssetTagDepotEntryRel.getDepotEntryId(),
			newAssetTagDepotEntryRel.getDepotEntryId());
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
	public void testCountByAssetTagId() throws Exception {
		_persistence.countByAssetTagId(RandomTestUtil.nextLong());

		_persistence.countByAssetTagId(0L);
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
		AssetTagDepotEntryRel newAssetTagDepotEntryRel =
			addAssetTagDepotEntryRel();

		AssetTagDepotEntryRel existingAssetTagDepotEntryRel =
			_persistence.findByPrimaryKey(
				newAssetTagDepotEntryRel.getPrimaryKey());

		Assert.assertEquals(
			existingAssetTagDepotEntryRel, newAssetTagDepotEntryRel);
	}

	@Test(expected = NoSuchDepotEntryRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<AssetTagDepotEntryRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"AssetTagDepotEntryRel", "mvccVersion", true, "ctCollectionId",
			true, "uuid", true, "assetTagDepotEntryRelId", true, "companyId",
			true, "assetTagId", true, "depotEntryId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetTagDepotEntryRel newAssetTagDepotEntryRel =
			addAssetTagDepotEntryRel();

		AssetTagDepotEntryRel existingAssetTagDepotEntryRel =
			_persistence.fetchByPrimaryKey(
				newAssetTagDepotEntryRel.getPrimaryKey());

		Assert.assertEquals(
			existingAssetTagDepotEntryRel, newAssetTagDepotEntryRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetTagDepotEntryRel missingAssetTagDepotEntryRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetTagDepotEntryRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		AssetTagDepotEntryRel newAssetTagDepotEntryRel1 =
			addAssetTagDepotEntryRel();
		AssetTagDepotEntryRel newAssetTagDepotEntryRel2 =
			addAssetTagDepotEntryRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetTagDepotEntryRel1.getPrimaryKey());
		primaryKeys.add(newAssetTagDepotEntryRel2.getPrimaryKey());

		Map<Serializable, AssetTagDepotEntryRel> assetTagDepotEntryRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, assetTagDepotEntryRels.size());
		Assert.assertEquals(
			newAssetTagDepotEntryRel1,
			assetTagDepotEntryRels.get(
				newAssetTagDepotEntryRel1.getPrimaryKey()));
		Assert.assertEquals(
			newAssetTagDepotEntryRel2,
			assetTagDepotEntryRels.get(
				newAssetTagDepotEntryRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AssetTagDepotEntryRel> assetTagDepotEntryRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetTagDepotEntryRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		AssetTagDepotEntryRel newAssetTagDepotEntryRel =
			addAssetTagDepotEntryRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetTagDepotEntryRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AssetTagDepotEntryRel> assetTagDepotEntryRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetTagDepotEntryRels.size());
		Assert.assertEquals(
			newAssetTagDepotEntryRel,
			assetTagDepotEntryRels.get(
				newAssetTagDepotEntryRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AssetTagDepotEntryRel> assetTagDepotEntryRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetTagDepotEntryRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		AssetTagDepotEntryRel newAssetTagDepotEntryRel =
			addAssetTagDepotEntryRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetTagDepotEntryRel.getPrimaryKey());

		Map<Serializable, AssetTagDepotEntryRel> assetTagDepotEntryRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetTagDepotEntryRels.size());
		Assert.assertEquals(
			newAssetTagDepotEntryRel,
			assetTagDepotEntryRels.get(
				newAssetTagDepotEntryRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			AssetTagDepotEntryRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<AssetTagDepotEntryRel>() {

				@Override
				public void performAction(
					AssetTagDepotEntryRel assetTagDepotEntryRel) {

					Assert.assertNotNull(assetTagDepotEntryRel);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		AssetTagDepotEntryRel newAssetTagDepotEntryRel =
			addAssetTagDepotEntryRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetTagDepotEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assetTagDepotEntryRelId",
				newAssetTagDepotEntryRel.getAssetTagDepotEntryRelId()));

		List<AssetTagDepotEntryRel> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetTagDepotEntryRel existingAssetTagDepotEntryRel = result.get(0);

		Assert.assertEquals(
			existingAssetTagDepotEntryRel, newAssetTagDepotEntryRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetTagDepotEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assetTagDepotEntryRelId", RandomTestUtil.nextLong()));

		List<AssetTagDepotEntryRel> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		AssetTagDepotEntryRel newAssetTagDepotEntryRel =
			addAssetTagDepotEntryRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetTagDepotEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("assetTagDepotEntryRelId"));

		Object newAssetTagDepotEntryRelId =
			newAssetTagDepotEntryRel.getAssetTagDepotEntryRelId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"assetTagDepotEntryRelId",
				new Object[] {newAssetTagDepotEntryRelId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAssetTagDepotEntryRelId = result.get(0);

		Assert.assertEquals(
			existingAssetTagDepotEntryRelId, newAssetTagDepotEntryRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetTagDepotEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("assetTagDepotEntryRelId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"assetTagDepotEntryRelId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AssetTagDepotEntryRel newAssetTagDepotEntryRel =
			addAssetTagDepotEntryRel();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newAssetTagDepotEntryRel.getPrimaryKey()));
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

		AssetTagDepotEntryRel newAssetTagDepotEntryRel =
			addAssetTagDepotEntryRel();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetTagDepotEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assetTagDepotEntryRelId",
				newAssetTagDepotEntryRel.getAssetTagDepotEntryRelId()));

		List<AssetTagDepotEntryRel> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		AssetTagDepotEntryRel assetTagDepotEntryRel) {

		Assert.assertEquals(
			Long.valueOf(assetTagDepotEntryRel.getAssetTagId()),
			ReflectionTestUtil.<Long>invoke(
				assetTagDepotEntryRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "assetTagId"));
		Assert.assertEquals(
			Long.valueOf(assetTagDepotEntryRel.getDepotEntryId()),
			ReflectionTestUtil.<Long>invoke(
				assetTagDepotEntryRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "depotEntryId"));
	}

	protected AssetTagDepotEntryRel addAssetTagDepotEntryRel()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		AssetTagDepotEntryRel assetTagDepotEntryRel = _persistence.create(pk);

		assetTagDepotEntryRel.setMvccVersion(RandomTestUtil.nextLong());

		assetTagDepotEntryRel.setCtCollectionId(RandomTestUtil.nextLong());

		assetTagDepotEntryRel.setUuid(RandomTestUtil.randomString());

		assetTagDepotEntryRel.setCompanyId(RandomTestUtil.nextLong());

		assetTagDepotEntryRel.setAssetTagId(RandomTestUtil.nextLong());

		assetTagDepotEntryRel.setDepotEntryId(RandomTestUtil.nextLong());

		_assetTagDepotEntryRels.add(_persistence.update(assetTagDepotEntryRel));

		return assetTagDepotEntryRel;
	}

	private List<AssetTagDepotEntryRel> _assetTagDepotEntryRels =
		new ArrayList<AssetTagDepotEntryRel>();
	private AssetTagDepotEntryRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}