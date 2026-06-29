/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.test.util.FragmentEntryTestUtil;
import com.liferay.fragment.test.util.FragmentTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTRequiredModelException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kiana Suetani
 */
@RunWith(Arquillian.class)
public class FragmentEntryLinkDeletionProtectionTest {

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
			0, FragmentEntryLinkDeletionProtectionTest.class.getSimpleName(),
			null);

		_ctCollections.add(_ctCollection);

		_group = GroupTestUtil.addGroup();

		_fragmentEntryLinkClassNameId = _classNameLocalService.getClassNameId(
			FragmentEntryLink.class);
	}

	@Test
	public void testUpdateDeletedBlockedWhenModifiedInPublication()
		throws Exception {

		FragmentEntryLink fragmentEntryLink = _addFragmentEntryLink();

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			_fragmentEntryLinkLocalService.updateFragmentEntryLink(
				TestPropsValues.getUserId(),
				fragmentEntryLink.getFragmentEntryLinkId(),
				"{\"key\":\"" + RandomTestUtil.randomString() + "\"}", false);
		}

		CTEntry ctEntry = _ctEntryLocalService.fetchCTEntry(
			_ctCollection.getCtCollectionId(), _fragmentEntryLinkClassNameId,
			fragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertEquals(
			CTConstants.CT_CHANGE_TYPE_MODIFICATION, ctEntry.getChangeType());

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"CHANGE_TRACKING_DELETION_PROTECTION_ENABLED", true,
					false)) {

			try {
				_fragmentEntryLinkLocalService.updateDeleted(
					TestPropsValues.getUserId(),
					fragmentEntryLink.getFragmentEntryLinkId(), true);

				Assert.fail(
					"Expected CTRequiredModelException because the fragment " +
						"entry link is being modified in a publication");
			}
			catch (Exception exception) {
				Throwable throwable = exception;

				while ((throwable != null) &&
					   !(throwable instanceof CTRequiredModelException)) {

					throwable = throwable.getCause();
				}

				Assert.assertNotNull(
					"Expected CTRequiredModelException in the cause chain " +
						"but got: " + exception,
					throwable);
			}
		}

		FragmentEntryLink reloadedFragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLink(
				fragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertFalse(reloadedFragmentEntryLink.isDeleted());
	}

	@Test
	public void testUpdateDeletedSucceedsWithoutPublicationModification()
		throws Exception {

		FragmentEntryLink fragmentEntryLink = _addFragmentEntryLink();

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"CHANGE_TRACKING_DELETION_PROTECTION_ENABLED", true,
					false)) {

			FragmentEntryLink updatedFragmentEntryLink =
				_fragmentEntryLinkLocalService.updateDeleted(
					TestPropsValues.getUserId(),
					fragmentEntryLink.getFragmentEntryLinkId(), true);

			Assert.assertTrue(updatedFragmentEntryLink.isDeleted());
		}
	}

	private FragmentEntryLink _addFragmentEntryLink() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection.getFragmentCollectionId());

		return FragmentTestUtil.addFragmentEntryLink(
			_group.getGroupId(), fragmentEntry.getFragmentEntryId(),
			layout.getPlid());
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private CTCollection _ctCollection;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@DeleteAfterTestRun
	private final List<CTCollection> _ctCollections = new ArrayList<>();

	@Inject
	private CTEntryLocalService _ctEntryLocalService;

	private long _fragmentEntryLinkClassNameId;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@DeleteAfterTestRun
	private Group _group;

}