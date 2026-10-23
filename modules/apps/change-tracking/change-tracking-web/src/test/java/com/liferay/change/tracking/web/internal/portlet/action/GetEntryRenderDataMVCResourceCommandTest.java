/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Noor Najjar
 */
public class GetEntryRenderDataMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = Mockito.mock(Portal.class);

		Mockito.doAnswer(
			invocation -> new String[] {
				invocation.getArgument(0, String.class), StringPool.BLANK
			}
		).when(
			portal
		).stripURLAnchor(
			Mockito.anyString(), Mockito.anyString()
		);

		portalUtil.setPortal(portal);
	}

	@Test
	public void testAddPreviewCTCollectionId() {
		String html = "<p><img src=\"/documents/d/guest/image\" /></p>";

		Assert.assertEquals(
			StringBundler.concat(
				"<p><img src=\"/documents/d/guest/image?previewCTCollectionId=",
				_CT_COLLECTION_ID, "\" /></p>"),
			_addPreviewCTCollectionId(_CT_COLLECTION_ID, html));
		Assert.assertEquals(
			html,
			_addPreviewCTCollectionId(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, html));

		html =
			"<a href=\"http://localhost:8080/documents/20/0/image/uuid?t=1\">" +
				"image</a>";

		Assert.assertEquals(
			StringBundler.concat(
				"<a href=\"http://localhost:8080/documents/20/0/image/uuid?t=1",
				"&previewCTCollectionId=", _CT_COLLECTION_ID, "\">image</a>"),
			_addPreviewCTCollectionId(_CT_COLLECTION_ID, html));
		Assert.assertEquals(
			html,
			_addPreviewCTCollectionId(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, html));
	}

	@Test
	public void testAddPreviewCTCollectionIdWithDocumentsInURL() {
		String html = "<a href=\"/web/guest/documents/reports\">reports</a>";

		Assert.assertEquals(
			html, _addPreviewCTCollectionId(_CT_COLLECTION_ID, html));
	}

	@Test
	public void testAddPreviewCTCollectionIdWithExistingParameter() {
		String html =
			"<img src=\"/documents/d/guest/image?previewCTCollectionId=1\" />";

		Assert.assertEquals(
			html, _addPreviewCTCollectionId(_CT_COLLECTION_ID, html));

		html =
			"<img src=\"/documents/d/guest/image?t=1&" +
				"previewCTCollectionId=1\" />";

		Assert.assertEquals(
			html, _addPreviewCTCollectionId(_CT_COLLECTION_ID, html));

		html =
			"<img src=\"/documents/d/guest/image?t=1&amp;" +
				"previewCTCollectionId=1\" />";

		Assert.assertEquals(
			html, _addPreviewCTCollectionId(_CT_COLLECTION_ID, html));
	}

	@Test
	public void testAddPreviewCTCollectionIdWithNullHTML() {
		Assert.assertNull(_addPreviewCTCollectionId(_CT_COLLECTION_ID, null));
	}

	private String _addPreviewCTCollectionId(long ctCollectionId, String html) {
		return ReflectionTestUtil.invoke(
			_getEntryRenderDataMVCResourceCommand, "_addPreviewCTCollectionId",
			new Class<?>[] {long.class, String.class}, ctCollectionId, html);
	}

	private static final long _CT_COLLECTION_ID = RandomTestUtil.randomLong();

	private final GetEntryRenderDataMVCResourceCommand
		_getEntryRenderDataMVCResourceCommand =
			new GetEntryRenderDataMVCResourceCommand();

}