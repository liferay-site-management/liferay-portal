/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.change.tracking.spi.resolver.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.conflict.ConflictInfo;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Noor Najjar
 */
@RunWith(Arquillian.class)
public class DLFileEntryFileNameConstraintResolverTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_folder = _dlAppLocalService.addFolder(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test
	public void testResolveConflict() throws Exception {
		String sharedFileName = RandomTestUtil.randomString() + ".txt";

		FileEntry fileEntry1 = _addFileEntry(
			RandomTestUtil.randomString() + ".txt",
			RandomTestUtil.randomString());
		FileEntry fileEntry2 = _addFileEntry(
			RandomTestUtil.randomString() + ".txt",
			RandomTestUtil.randomString());

		CTCollection ctCollection1 = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection1.getCtCollectionId())) {

			_renameFileEntryFileName(fileEntry1, sharedFileName);
		}

		CTCollection ctCollection2 = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection2.getCtCollectionId())) {

			_renameFileEntryFileName(fileEntry2, sharedFileName);
		}

		_ctCollectionService.publishCTCollection(
			TestPropsValues.getUserId(), ctCollection1.getCtCollectionId());

		Map<Long, List<ConflictInfo>> conflictInfoMap =
			_ctCollectionLocalService.checkConflicts(ctCollection2);

		List<ConflictInfo> conflictInfos = conflictInfoMap.get(
			_classNameLocalService.getClassNameId(DLFileEntry.class));

		Assert.assertNotNull(
			"checkConflicts did not detect the duplicate file name",
			conflictInfos);
		Assert.assertEquals(conflictInfos.toString(), 1, conflictInfos.size());

		ConflictInfo conflictInfo = conflictInfos.get(0);

		Assert.assertTrue(
			"File name conflict was detected but not resolved",
			conflictInfo.isResolved());

		_ctCollectionService.publishCTCollection(
			TestPropsValues.getUserId(), ctCollection2.getCtCollectionId());

		DLFileEntry publishedDLFileEntry =
			_dlFileEntryLocalService.getDLFileEntry(
				fileEntry2.getFileEntryId());

		Assert.assertNotEquals(
			sharedFileName, publishedDLFileEntry.getFileName());
	}

	private FileEntry _addFileEntry(String fileName, String title)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		return _dlAppLocalService.addFileEntry(
			null, serviceContext.getUserId(), _group.getGroupId(),
			_folder.getFolderId(), fileName, ContentTypes.TEXT_PLAIN, title,
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			"liferay".getBytes(), null, null, null, serviceContext);
	}

	private void _renameFileEntryFileName(
			FileEntry fileEntry, String newFileName)
		throws Exception {

		_dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), newFileName, fileEntry.getMimeType(),
			fileEntry.getTitle(), newFileName, fileEntry.getDescription(),
			StringPool.BLANK, DLVersionNumberIncrease.MINOR, null,
			fileEntry.getSize(), fileEntry.getDisplayDate(),
			fileEntry.getExpirationDate(), fileEntry.getReviewDate(),
			ServiceContextTestUtil.getServiceContext(fileEntry.getGroupId()));
	}

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private CTCollectionService _ctCollectionService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLAppService _dlAppService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	private Folder _folder;

	@DeleteAfterTestRun
	private Group _group;

}