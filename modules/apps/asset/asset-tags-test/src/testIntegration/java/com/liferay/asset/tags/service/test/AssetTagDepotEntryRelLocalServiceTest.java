/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.tags.exception.InvalidAssetTagDepotEntryRelException;
import com.liferay.asset.tags.model.AssetTagDepotEntryRel;
import com.liferay.asset.tags.service.AssetTagDepotEntryRelLocalService;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.List;

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
public class AssetTagDepotEntryRelLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_assetTag = AssetTestUtil.addTag(_group.getGroupId());
	}

	@Test
	public void testGetAssetTagDepotEntryRelsByAssetTagId() throws Exception {
		DepotEntry depotEntry1 = _addDepotEntry();
		DepotEntry depotEntry2 = _addDepotEntry();

		long[] depotEntryIds = {
			depotEntry1.getDepotEntryId(), depotEntry2.getDepotEntryId()
		};

		_assetTagDepotEntryRelLocalService.setAssetTagDepotEntryRels(
			_assetTag.getTagId(), depotEntryIds);

		List<AssetTagDepotEntryRel> assetTagDepotEntryRels =
			_assetTagDepotEntryRelLocalService.
				getAssetTagDepotEntryRelsByAssetTagId(_assetTag.getTagId());

		Assert.assertEquals(
			assetTagDepotEntryRels.toString(), depotEntryIds.length,
			assetTagDepotEntryRels.size());

		for (AssetTagDepotEntryRel assetTagDepotEntryRel :
				assetTagDepotEntryRels) {

			Assert.assertEquals(
				_assetTag.getTagId(), assetTagDepotEntryRel.getAssetTagId());
			Assert.assertTrue(
				ArrayUtil.contains(
					depotEntryIds, assetTagDepotEntryRel.getDepotEntryId()));
		}

		_assetTagLocalService.deleteTag(_assetTag);

		assetTagDepotEntryRels =
			_assetTagDepotEntryRelLocalService.
				getAssetTagDepotEntryRelsByAssetTagId(_assetTag.getTagId());

		Assert.assertTrue(assetTagDepotEntryRels.isEmpty());
	}

	@Test
	public void testGetAssetTagDepotEntryRelsByDepotEntryId() throws Exception {
		AssetTag assetTag1 = AssetTestUtil.addTag(_group.getGroupId());
		AssetTag assetTag2 = AssetTestUtil.addTag(_group.getGroupId());

		long[] assetTagIds = {assetTag1.getTagId(), assetTag2.getTagId()};

		DepotEntry depotEntry = _addDepotEntry();

		for (long assetTagId : assetTagIds) {
			_assetTagDepotEntryRelLocalService.addAssetTagDepotEntryRel(
				assetTagId, depotEntry.getDepotEntryId());
		}

		List<AssetTagDepotEntryRel> assetTagDepotEntryRels =
			_assetTagDepotEntryRelLocalService.
				getAssetTagDepotEntryRelsByDepotEntryId(
					depotEntry.getDepotEntryId());

		Assert.assertEquals(
			assetTagDepotEntryRels.toString(), assetTagIds.length,
			assetTagDepotEntryRels.size());

		for (AssetTagDepotEntryRel assetTagDepotEntryRel :
				assetTagDepotEntryRels) {

			Assert.assertEquals(
				depotEntry.getDepotEntryId(),
				assetTagDepotEntryRel.getDepotEntryId());
			Assert.assertTrue(
				ArrayUtil.contains(
					assetTagIds, assetTagDepotEntryRel.getAssetTagId()));
		}

		_depotEntryLocalService.deleteDepotEntry(depotEntry);

		assetTagDepotEntryRels =
			_assetTagDepotEntryRelLocalService.
				getAssetTagDepotEntryRelsByDepotEntryId(
					depotEntry.getDepotEntryId());

		Assert.assertTrue(assetTagDepotEntryRels.isEmpty());
	}

	@Test
	public void testSetAssetTagDepotEntryRels() throws Exception {
		DepotEntry depotEntry1 = _addDepotEntry();

		_assetTagDepotEntryRelLocalService.setAssetTagDepotEntryRels(
			_assetTag.getTagId(), new long[] {depotEntry1.getDepotEntryId()});

		List<AssetTagDepotEntryRel> assetTagDepotEntryRels =
			_assetTagDepotEntryRelLocalService.
				getAssetTagDepotEntryRelsByAssetTagId(_assetTag.getTagId());

		Assert.assertEquals(
			assetTagDepotEntryRels.toString(), 1,
			assetTagDepotEntryRels.size());

		_assertAssetTagDepotEntryRel(
			assetTagDepotEntryRels.get(0), _assetTag.getTagId(),
			depotEntry1.getDepotEntryId());

		DepotEntry depotEntry2 = _addDepotEntry();

		_assetTagDepotEntryRelLocalService.setAssetTagDepotEntryRels(
			_assetTag.getTagId(), new long[] {depotEntry2.getDepotEntryId()});

		assetTagDepotEntryRels =
			_assetTagDepotEntryRelLocalService.
				getAssetTagDepotEntryRelsByAssetTagId(_assetTag.getTagId());

		Assert.assertEquals(
			assetTagDepotEntryRels.toString(), 1,
			assetTagDepotEntryRels.size());

		_assertAssetTagDepotEntryRel(
			assetTagDepotEntryRels.get(0), _assetTag.getTagId(),
			depotEntry2.getDepotEntryId());
	}

	@Test(expected = InvalidAssetTagDepotEntryRelException.class)
	public void testSetAssetVTagDepotEntryRelsWithEmptyDepotEntryIds()
		throws Exception {

		_assetTagDepotEntryRelLocalService.setAssetTagDepotEntryRels(
			_assetTag.getTagId(), new long[0]);
	}

	private DepotEntry _addDepotEntry() throws Exception {
		return _depotEntryLocalService.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext());
	}

	private void _assertAssetTagDepotEntryRel(
			AssetTagDepotEntryRel assetTagDepotEntryRel,
			long expectedAssetTagId, long expectedDepotEntryId)
		throws Exception {

		Assert.assertEquals(
			expectedAssetTagId, assetTagDepotEntryRel.getAssetTagId());
		Assert.assertEquals(
			expectedDepotEntryId, assetTagDepotEntryRel.getDepotEntryId());
	}

	private AssetTag _assetTag;

	@Inject
	private AssetTagDepotEntryRelLocalService
		_assetTagDepotEntryRelLocalService;

	@Inject
	private AssetTagLocalService _assetTagLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	private Group _group;

}