/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.pagespeed.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.xml.SAXReaderImpl;

import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Kiana Suetani
 */
public class LiferayHeadlessClientTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		SAXReaderUtil saxReaderUtil = new SAXReaderUtil();

		saxReaderUtil.setSAXReader(new SAXReaderImpl());
	}

	@Before
	public void setUp() {
		_httpUtilMockedStatic = Mockito.mockStatic(HttpUtil.class);
	}

	@After
	public void tearDown() {
		_httpUtilMockedStatic.close();
	}

	@Test
	public void testGetChildSitemapURLsFromAssetTypeIndex() throws Exception {
		Document document = SAXReaderUtil.read(
			StringBundler.concat(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
				"<sitemapindex xmlns=",
				"\"http://www.sitemaps.org/schemas/sitemap/0.9\">",
				"<sitemap><loc>https://example.com/sitemap-pages.xml",
				"</loc></sitemap><sitemap><loc>",
				"https://example.com/sitemap-web-content.xml",
				"</loc></sitemap><sitemap><loc>",
				"https://example.com/sitemap-categories.xml",
				"</loc></sitemap></sitemapindex>"));

		List<String> childSitemapURLs = _getChildSitemapURLs(
			document.getRootElement());

		Assert.assertEquals(
			childSitemapURLs.toString(), 3, childSitemapURLs.size());
		Assert.assertEquals(
			"https://example.com/sitemap-pages.xml", childSitemapURLs.get(0));
	}

	@Test
	public void testGetChildSitemapURLsFromPageLayoutIndex() throws Exception {
		Document document = SAXReaderUtil.read(
			StringBundler.concat(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
				"<sitemapindex xmlns=",
				"\"http://www.sitemaps.org/schemas/sitemap/0.9\">",
				"<sitemap><loc>https://example.com/sitemap.xml",
				"?layoutUuid=abc&amp;groupId=123",
				"</loc></sitemap><sitemap><loc>",
				"https://example.com/sitemap.xml",
				"?layoutUuid=def&amp;groupId=123",
				"</loc></sitemap></sitemapindex>"));

		List<String> childSitemapURLs = _getChildSitemapURLs(
			document.getRootElement());

		Assert.assertEquals(
			childSitemapURLs.toString(), 2, childSitemapURLs.size());
		Assert.assertTrue(
			childSitemapURLs.get(
				0
			).contains(
				"layoutUuid=abc"
			));
	}

	@Test
	public void testGetPageURLs() throws Exception {
		String sitemapXML = StringBundler.concat(
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><urlset xmlns=",
			"\"http://www.sitemaps.org/schemas/sitemap/0.9\">",
			"<url><loc>https://example.com/home</loc></url>",
			"<url><loc>https://example.com/about</loc></url></urlset>");

		Http.Options[] capturedOptions = new Http.Options[1];

		Http.Response httpResponse = Mockito.mock(Http.Response.class);

		Mockito.when(
			httpResponse.getResponseCode()
		).thenReturn(
			200
		);

		_httpUtilMockedStatic.when(
			() -> HttpUtil.URLtoString(Mockito.any(Http.Options.class))
		).thenAnswer(
			invocation -> {
				Http.Options options = invocation.getArgument(0);

				capturedOptions[0] = options;

				options.setResponse(httpResponse);

				return sitemapXML;
			}
		);

		LiferayHeadlessClient liferayHeadlessClient = new LiferayHeadlessClient(
			null, "https://portal.example.com");

		List<String> urls = liferayHeadlessClient.getPageURLs("example.com");

		Assert.assertEquals(
			"https://example.com/sitemap.xml",
			capturedOptions[0].getLocation());
		Assert.assertEquals(urls.toString(), 2, urls.size());
		Assert.assertEquals("https://example.com/home", urls.get(0));
		Assert.assertEquals("https://example.com/about", urls.get(1));
	}

	@Test
	public void testIsPageLayoutModeWithAssetTypeSlugs() throws Exception {
		Assert.assertFalse(
			_isPageLayoutMode(
				Arrays.asList(
					"https://example.com/sitemap-pages.xml",
					"https://example.com/sitemap-web-content.xml")));
	}

	@Test
	public void testIsPageLayoutModeWithEmptyList() throws Exception {
		Assert.assertTrue(_isPageLayoutMode(Collections.emptyList()));
	}

	@Test
	public void testIsPageLayoutModeWithLayoutUuids() throws Exception {
		Assert.assertTrue(
			_isPageLayoutMode(
				Arrays.asList(
					"https://example.com/sitemap.xml" +
						"?layoutUuid=abc&groupId=123",
					"https://example.com/sitemap.xml" +
						"?layoutUuid=def&groupId=123")));
	}

	@SuppressWarnings("unchecked")
	private List<String> _getChildSitemapURLs(Element rootElement)
		throws Exception {

		LiferayHeadlessClient liferayHeadlessClient = new LiferayHeadlessClient(
			null, "https://example.com");

		Method method = LiferayHeadlessClient.class.getDeclaredMethod(
			"_getChildSitemapURLs", Element.class);

		method.setAccessible(true);

		return (List<String>)method.invoke(liferayHeadlessClient, rootElement);
	}

	private boolean _isPageLayoutMode(List<String> childSitemapURLs)
		throws Exception {

		LiferayHeadlessClient liferayHeadlessClient = new LiferayHeadlessClient(
			null, "https://example.com");

		Method method = LiferayHeadlessClient.class.getDeclaredMethod(
			"_isPageLayoutMode", List.class);

		method.setAccessible(true);

		return (boolean)method.invoke(liferayHeadlessClient, childSitemapURLs);
	}

	private MockedStatic<HttpUtil> _httpUtilMockedStatic;

}