/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.cache.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.persistence.impl.LayoutPersistenceImpl;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Truong
 */
@RunWith(Arquillian.class)
public class CTCacheTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testEntityCacheResults() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		Layout cachedLayout = (Layout)EntityCacheUtil.getResult(
			LayoutImpl.class, layout.getPrimaryKey());

		Assert.assertEquals(
			layout.getCtCollectionId(), cachedLayout.getCtCollectionId());

		Assert.assertEquals(
			layout.getMvccVersion(), cachedLayout.getMvccVersion());

		CTCollection ctCollection1 = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Layout publicationLayout1 = null;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection1.getCtCollectionId())) {

			publicationLayout1 = _layoutLocalService.updateLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), new Date());

			Assert.assertEquals(
				layout.getPrimaryKey(), publicationLayout1.getPrimaryKey());

			Assert.assertNotEquals(
				layout.getCtCollectionId(),
				publicationLayout1.getCtCollectionId());

			cachedLayout = (Layout)EntityCacheUtil.getResult(
				LayoutImpl.class, layout.getPrimaryKey());

			Assert.assertNotEquals(
				layout.getCtCollectionId(), cachedLayout.getCtCollectionId());

			Assert.assertEquals(
				publicationLayout1.getCtCollectionId(),
				cachedLayout.getCtCollectionId());
		}

		cachedLayout = (Layout)EntityCacheUtil.getResult(
			LayoutImpl.class, layout.getPrimaryKey());

		Assert.assertEquals(
			layout.getCtCollectionId(), cachedLayout.getCtCollectionId());

		Assert.assertNotEquals(
			publicationLayout1.getCtCollectionId(),
			cachedLayout.getCtCollectionId());

		CTCollection ctCollection2 = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Layout publicationLayout2 = null;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection2.getCtCollectionId())) {

			publicationLayout2 = _layoutLocalService.updateLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), new Date());

			Assert.assertEquals(
				layout.getPrimaryKey(), publicationLayout2.getPrimaryKey());

			Assert.assertNotEquals(
				layout.getCtCollectionId(),
				publicationLayout2.getCtCollectionId());

			cachedLayout = (Layout)EntityCacheUtil.getResult(
				LayoutImpl.class, layout.getPrimaryKey());

			Assert.assertNotEquals(
				layout.getCtCollectionId(), cachedLayout.getCtCollectionId());

			Assert.assertNotEquals(
				publicationLayout1.getCtCollectionId(),
				cachedLayout.getCtCollectionId());

			Assert.assertEquals(
				publicationLayout2.getCtCollectionId(),
				cachedLayout.getCtCollectionId());
		}
	}

	@Ignore
	@Test
	public void testFinderCacheResults() throws Exception {
		LayoutTestUtil.addTypePortletLayout(_group);

		CTCollection ctCollection1 = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), RandomTestUtil.randomString());

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection1.getCtCollectionId())) {

			LayoutTestUtil.addTypePortletLayout(_group);

			List<Layout> layouts = _layoutLocalService.getLayouts(
				TestPropsValues.getCompanyId());

			FinderPath finderPath = new FinderPath(
				LayoutPersistenceImpl.FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCompanyId", new String[] {Long.class.getName()},
				new String[] {"companyId"}, true);

			Object[] finderArgs = {TestPropsValues.getCompanyId()};

			List<Layout> cachedLayouts =
				(List<Layout>)FinderCacheUtil.getResult(
					finderPath, finderArgs,
					_layoutLocalService.getBasePersistence());

			Assert.assertEquals(
				cachedLayouts.toString(), layouts.size(), cachedLayouts.size());
		}
	}

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

}