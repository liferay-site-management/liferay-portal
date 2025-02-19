/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.exception.InvalidAssetVocabularyDepotEntryRelException;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyDepotEntryRel;
import com.liferay.asset.kernel.service.AssetVocabularyDepotEntryRelLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
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
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class AssetVocabularyDepotEntryRelLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_assetVocabulary = AssetTestUtil.addVocabulary(_group.getGroupId());
	}

	@Test
	public void testGetAssetVocabularyDepotEntryRelsByAssetVocabularyId()
		throws Exception {

		DepotEntry depotEntry1 = _addDepotEntry();
		DepotEntry depotEntry2 = _addDepotEntry();

		long[] depotEntryIds = {
			depotEntry1.getDepotEntryId(), depotEntry2.getDepotEntryId()
		};

		_assetVocabularyDepotEntryRelLocalService.
			setAssetVocabularyDepotEntryRels(
				_assetVocabulary.getVocabularyId(), depotEntryIds);

		List<AssetVocabularyDepotEntryRel> assetVocabularyDepotEntryRels =
			_assetVocabularyDepotEntryRelLocalService.
				getAssetVocabularyDepotEntryRelsByAssetVocabularyId(
					_assetVocabulary.getVocabularyId());

		Assert.assertEquals(
			assetVocabularyDepotEntryRels.toString(), depotEntryIds.length,
			assetVocabularyDepotEntryRels.size());

		for (AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel :
				assetVocabularyDepotEntryRels) {

			Assert.assertEquals(
				_assetVocabulary.getVocabularyId(),
				assetVocabularyDepotEntryRel.getAssetVocabularyId());
			Assert.assertTrue(
				ArrayUtil.contains(
					depotEntryIds,
					assetVocabularyDepotEntryRel.getDepotEntryId()));
		}

		_assetVocabularyLocalService.deleteVocabulary(_assetVocabulary);

		assetVocabularyDepotEntryRels =
			_assetVocabularyDepotEntryRelLocalService.
				getAssetVocabularyDepotEntryRelsByAssetVocabularyId(
					_assetVocabulary.getVocabularyId());

		Assert.assertTrue(assetVocabularyDepotEntryRels.isEmpty());
	}

	@Test
	public void testGetAssetVocabularyDepotEntryRelsByDepotEntryId()
		throws Exception {

		AssetVocabulary assetVocabulary1 = AssetTestUtil.addVocabulary(
			_group.getGroupId());
		AssetVocabulary assetVocabulary2 = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		long[] assetVocabularyIds = {
			assetVocabulary1.getVocabularyId(),
			assetVocabulary2.getVocabularyId()
		};

		DepotEntry depotEntry = _addDepotEntry();

		for (long assetVocabularyId : assetVocabularyIds) {
			_assetVocabularyDepotEntryRelLocalService.
				addAssetVocabularyDepotEntryRel(
					assetVocabularyId, depotEntry.getDepotEntryId());
		}

		List<AssetVocabularyDepotEntryRel> assetVocabularyDepotEntryRels =
			_assetVocabularyDepotEntryRelLocalService.
				getAssetVocabularyDepotEntryRelsByDepotEntryId(
					depotEntry.getDepotEntryId());

		Assert.assertEquals(
			assetVocabularyDepotEntryRels.toString(), assetVocabularyIds.length,
			assetVocabularyDepotEntryRels.size());

		for (AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel :
				assetVocabularyDepotEntryRels) {

			Assert.assertEquals(
				depotEntry.getDepotEntryId(),
				assetVocabularyDepotEntryRel.getDepotEntryId());
			Assert.assertTrue(
				ArrayUtil.contains(
					assetVocabularyIds,
					assetVocabularyDepotEntryRel.getAssetVocabularyId()));
		}

		_depotEntryLocalService.deleteDepotEntry(depotEntry);

		assetVocabularyDepotEntryRels =
			_assetVocabularyDepotEntryRelLocalService.
				getAssetVocabularyDepotEntryRelsByDepotEntryId(
					depotEntry.getDepotEntryId());

		Assert.assertTrue(assetVocabularyDepotEntryRels.isEmpty());
	}

	@Test
	public void testSetAssetVocabularyDepotEntryRels() throws Exception {
		DepotEntry depotEntry1 = _addDepotEntry();

		_assetVocabularyDepotEntryRelLocalService.
			setAssetVocabularyDepotEntryRels(
				_assetVocabulary.getVocabularyId(),
				new long[] {depotEntry1.getDepotEntryId()});

		List<AssetVocabularyDepotEntryRel> assetVocabularyDepotEntryRels =
			_assetVocabularyDepotEntryRelLocalService.
				getAssetVocabularyDepotEntryRelsByAssetVocabularyId(
					_assetVocabulary.getVocabularyId());

		Assert.assertEquals(
			assetVocabularyDepotEntryRels.toString(), 1,
			assetVocabularyDepotEntryRels.size());

		_assertAssetVocabularyDepotEntryRel(
			assetVocabularyDepotEntryRels.get(0),
			_assetVocabulary.getVocabularyId(), depotEntry1.getDepotEntryId());

		DepotEntry depotEntry2 = _addDepotEntry();

		_assetVocabularyDepotEntryRelLocalService.
			setAssetVocabularyDepotEntryRels(
				_assetVocabulary.getVocabularyId(),
				new long[] {depotEntry2.getDepotEntryId()});

		assetVocabularyDepotEntryRels =
			_assetVocabularyDepotEntryRelLocalService.
				getAssetVocabularyDepotEntryRelsByAssetVocabularyId(
					_assetVocabulary.getVocabularyId());

		Assert.assertEquals(
			assetVocabularyDepotEntryRels.toString(), 1,
			assetVocabularyDepotEntryRels.size());

		_assertAssetVocabularyDepotEntryRel(
			assetVocabularyDepotEntryRels.get(0),
			_assetVocabulary.getVocabularyId(), depotEntry2.getDepotEntryId());
	}

	@Test(expected = InvalidAssetVocabularyDepotEntryRelException.class)
	public void testSetAssetVocabularyDepotEntryRelsWithEmptyDepotEntryIds()
		throws Exception {

		_assetVocabularyDepotEntryRelLocalService.
			setAssetVocabularyDepotEntryRels(
				_assetVocabulary.getVocabularyId(), new long[0]);
	}

	private DepotEntry _addDepotEntry() throws Exception {
		return _depotEntryLocalService.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext());
	}

	private void _assertAssetVocabularyDepotEntryRel(
			AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel,
			long expectedAssetVocabularyId, long expectedDepotEntryId)
		throws Exception {

		Assert.assertEquals(
			expectedAssetVocabularyId,
			assetVocabularyDepotEntryRel.getAssetVocabularyId());
		Assert.assertEquals(
			expectedDepotEntryId,
			assetVocabularyDepotEntryRel.getDepotEntryId());
	}

	private AssetVocabulary _assetVocabulary;

	@Inject
	private AssetVocabularyDepotEntryRelLocalService
		_assetVocabularyDepotEntryRelLocalService;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	private Group _group;

}