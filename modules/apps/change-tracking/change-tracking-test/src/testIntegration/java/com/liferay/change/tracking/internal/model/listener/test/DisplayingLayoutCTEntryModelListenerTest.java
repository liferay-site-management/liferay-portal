/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Truong
 */
@FeatureFlags(featureFlags = @FeatureFlag("LPD-89487"))
@RunWith(Arquillian.class)
public class DisplayingLayoutCTEntryModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		_group = GroupTestUtil.addGroup();

		_blogsEntry = _blogsEntryLocalService.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	@Test
	public void testOnAfterCreateAndPublish() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		_addLayoutClassedModelUsage(_blogsEntry, layout);

		_updateBlogsEntryInsidePublication();

		Assert.assertNotNull(_fetchLayoutCTEntry(layout));

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		BlogsEntry publishedBlogsEntry = _blogsEntryLocalService.getEntry(
			_blogsEntry.getEntryId());

		Assert.assertEquals(
			_blogsEntry.getTitle(), publishedBlogsEntry.getTitle());
	}

	@Test
	public void testOnAfterCreateWithMultipleLayouts() throws Exception {
		Layout layout1 = LayoutTestUtil.addTypePortletLayout(_group);
		Layout layout2 = LayoutTestUtil.addTypePortletLayout(_group);

		_addLayoutClassedModelUsage(_blogsEntry, layout1);
		_addLayoutClassedModelUsage(_blogsEntry, layout2);

		_updateBlogsEntryInsidePublication();

		Assert.assertNotNull(_fetchLayoutCTEntry(layout1));
		Assert.assertNotNull(_fetchLayoutCTEntry(layout2));
	}

	@Test
	public void testOnAfterCreateWithNoLayouts() throws Exception {
		_updateBlogsEntryInsidePublication();

		List<CTEntry> ctEntries = _ctEntryLocalService.getCTCollectionCTEntries(
			_ctCollection.getCtCollectionId());

		long layoutClassNameId = _classNameLocalService.getClassNameId(
			Layout.class);

		for (CTEntry ctEntry : ctEntries) {
			Assert.assertNotEquals(
				layoutClassNameId, ctEntry.getModelClassNameId());
		}
	}

	@Test
	public void testOnAfterCreateWithoutPublication() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		_addLayoutClassedModelUsage(_blogsEntry, layout);

		_updateBlogsEntry();

		Assert.assertNull(_fetchLayoutCTEntry(layout));
	}

	@Test
	public void testOnAfterCreateWithRepeatedUpdates() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		_addLayoutClassedModelUsage(_blogsEntry, layout);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			_updateBlogsEntry();
			_updateBlogsEntry();
		}

		Assert.assertNotNull(_fetchLayoutCTEntry(layout));
	}

	private void _addLayoutClassedModelUsage(
			BlogsEntry blogsEntry, Layout layout)
		throws Exception {

		long blogsEntryClassNameId = _classNameLocalService.getClassNameId(
			BlogsEntry.class);

		_layoutClassedModelUsageLocalService.addLayoutClassedModelUsage(
			_group.getGroupId(), StringPool.BLANK, blogsEntryClassNameId,
			blogsEntry.getEntryId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomLong(), layout.getPlid(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	private CTEntry _fetchLayoutCTEntry(Layout layout) {
		long layoutClassNameId = _classNameLocalService.getClassNameId(
			Layout.class);

		return _ctEntryLocalService.fetchCTEntry(
			_ctCollection.getCtCollectionId(), layoutClassNameId,
			layout.getPlid());
	}

	private void _updateBlogsEntry() throws Exception {
		_blogsEntry = _blogsEntryLocalService.updateEntry(
			TestPropsValues.getUserId(), _blogsEntry.getEntryId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	private void _updateBlogsEntryInsidePublication() throws Exception {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			_updateBlogsEntry();
		}
	}

	@DeleteAfterTestRun
	private BlogsEntry _blogsEntry;

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private CTEntryLocalService _ctEntryLocalService;

	@Inject
	private CTProcessLocalService _ctProcessLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

}