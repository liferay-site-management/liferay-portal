/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.launch.rest.client.dto.v1_0.LaunchEntry;
import com.liferay.launch.rest.client.http.HttpInvoker;
import com.liferay.launch.rest.client.pagination.Page;
import com.liferay.launch.rest.client.pagination.Pagination;
import com.liferay.launch.rest.client.resource.v1_0.LaunchEntryResource;
import com.liferay.launch.rest.client.serdes.v1_0.LaunchEntrySerDes;
import com.liferay.oauth2.provider.scope.ScopeChecker;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.test.rule.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.crud.VulcanCRUDItemDelegate;
import com.liferay.portal.vulcan.crud.VulcanCRUDItemDelegateBuilderRegistry;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import jakarta.annotation.Generated;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.PathSegment;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.lang.reflect.Method;

import java.net.URI;

import java.text.Format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author David Truong
 * @generated
 */
@Generated("")
public abstract class BaseLaunchEntryResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_format = FastDateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	@Before
	public void setUp() throws Exception {
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_launchEntryResource.setContextCompany(testCompany);

		_testCompanyAdminUser = UserTestUtil.getAdminUser(
			testCompany.getCompanyId());

		launchEntryResource = LaunchEntryResource.builder(
		).authentication(
			_testCompanyAdminUser.getEmailAddress(),
			PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			testCompany.getVirtualHostname(), 8080, "http"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = getClientSerDesObjectMapper();

		LaunchEntry launchEntry1 = randomLaunchEntry();

		String json = objectMapper.writeValueAsString(launchEntry1);

		LaunchEntry launchEntry2 = LaunchEntrySerDes.toDTO(json);

		Assert.assertTrue(equals(launchEntry1, launchEntry2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = getClientSerDesObjectMapper();

		LaunchEntry launchEntry = randomLaunchEntry();

		String json1 = objectMapper.writeValueAsString(launchEntry);
		String json2 = LaunchEntrySerDes.toJSON(launchEntry);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	protected ObjectMapper getClientSerDesObjectMapper() {
		return new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		LaunchEntry launchEntry = randomLaunchEntry();

		String json = LaunchEntrySerDes.toJSON(launchEntry);

		Assert.assertFalse(json.contains(regex));

		launchEntry = LaunchEntrySerDes.toDTO(json);
	}

	@Test
	public void testGetLaunchEntry() throws Exception {
		LaunchEntry postLaunchEntry = testGetLaunchEntry_addLaunchEntry();

		LaunchEntry getLaunchEntry = launchEntryResource.getLaunchEntry(
			postLaunchEntry.getId());

		assertEquals(postLaunchEntry, getLaunchEntry);
		assertValid(getLaunchEntry);
	}

	@Test
	public void testVulcanCRUDItemDelegateGetItem() throws Exception {
		LaunchEntry postLaunchEntry = testGetLaunchEntry_addLaunchEntry();

		LaunchEntry getLaunchEntry = launchEntryResource.getLaunchEntry(
			postLaunchEntry.getId());

		VulcanCRUDItemDelegate vulcanCRUDItemDelegate =
			_vulcanCRUDItemDelegateBuilderRegistry.builder(
				testCompany, "com.liferay.launch.rest.dto.v1_0.LaunchEntry"
			).acceptLanguage(
				new AcceptLanguage() {

					@Override
					public List<Locale> getLocales() {
						return Arrays.asList(LocaleUtil.getDefault());
					}

					@Override
					public String getPreferredLanguageId() {
						return LocaleUtil.toLanguageId(LocaleUtil.getDefault());
					}

					@Override
					public Locale getPreferredLocale() {
						return LocaleUtil.getDefault();
					}

				}
			).groupLocalService(
				_groupLocalService
			).httpServletRequest(
				testVulcanCRUDItemDelegate_getHttpServletRequest()
			).httpServletResponse(
				new MockHttpServletResponse()
			).resourceActionLocalService(
				_resourceActionLocalService
			).resourcePermissionLocalService(
				_resourcePermissionLocalService
			).roleLocalService(
				_roleLocalService
			).scopeChecker(
				_scopeChecker
			).uriInfo(
				testVulcanCRUDItemDelegate_getUriInfo()
			).user(
				testVulcanCRUDItemDelegate_getUser()
			).build();

		Object item = vulcanCRUDItemDelegate.getItem(postLaunchEntry.getId());

		assertEquals(getLaunchEntry, LaunchEntrySerDes.toDTO(item.toString()));
	}

	protected HttpServletRequest
		testVulcanCRUDItemDelegate_getHttpServletRequest() {

		return new MockHttpServletRequest() {

			@Override
			public StringBuffer getRequestURL() {
				return new StringBuffer(
					StringBundler.concat(
						"http://localhost:8080/o/v1.0/",
						RandomTestUtil.randomString(), "/",
						RandomTestUtil.randomString()));
			}

		};
	}

	protected UriInfo testVulcanCRUDItemDelegate_getUriInfo() {
		String applicationPath = RandomTestUtil.randomString() + "/";
		String resourcePath = RandomTestUtil.randomString();

		return new UriInfo() {

			@Override
			public String getPath() {
				return resourcePath;
			}

			@Override
			public String getPath(boolean decode) {
				return getPath();
			}

			@Override
			public List<PathSegment> getPathSegments() {
				return Collections.emptyList();
			}

			@Override
			public List<PathSegment> getPathSegments(boolean decode) {
				return getPathSegments();
			}

			@Override
			public URI getRequestUri() {
				return URI.create(
					"http://localhost:8080/o/" + applicationPath +
						resourcePath);
			}

			@Override
			public UriBuilder getRequestUriBuilder() {
				return UriBuilder.fromUri(getRequestUri());
			}

			@Override
			public URI getAbsolutePath() {
				return getRequestUri();
			}

			@Override
			public UriBuilder getAbsolutePathBuilder() {
				return getRequestUriBuilder();
			}

			@Override
			public URI getBaseUri() {
				return URI.create("http://localhost:8080/o/" + applicationPath);
			}

			@Override
			public UriBuilder getBaseUriBuilder() {
				return UriBuilder.fromUri(getBaseUri());
			}

			@Override
			public MultivaluedMap<String, String> getPathParameters() {
				return new MultivaluedHashMap<>();
			}

			@Override
			public MultivaluedMap<String, String> getPathParameters(
				boolean decode) {

				return getPathParameters();
			}

			@Override
			public MultivaluedMap<String, String> getQueryParameters() {
				return new MultivaluedHashMap<>();
			}

			@Override
			public MultivaluedMap<String, String> getQueryParameters(
				boolean decode) {

				return getQueryParameters();
			}

			@Override
			public List<String> getMatchedURIs() {
				return Collections.emptyList();
			}

			@Override
			public List<String> getMatchedURIs(boolean decode) {
				return getMatchedURIs();
			}

			@Override
			public List<Object> getMatchedResources() {
				return Collections.emptyList();
			}

			@Override
			public URI resolve(URI requestUri) {
				return getBaseUri().resolve(requestUri);
			}

			@Override
			public URI relativize(URI uri) {
				return getBaseUri().relativize(uri);
			}

		};
	}

	protected com.liferay.portal.kernel.model.User
		testVulcanCRUDItemDelegate_getUser() {

		return _testCompanyAdminUser;
	}

	protected LaunchEntry testGetLaunchEntry_addLaunchEntry() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetLaunchEntry() throws Exception {
		LaunchEntry launchEntry = testGraphQLGetLaunchEntry_addLaunchEntry();

		// No namespace

		Assert.assertTrue(
			equals(
				launchEntry,
				LaunchEntrySerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"launchEntry",
								new HashMap<String, Object>() {
									{
										put(
											"launchEntryId",
											launchEntry.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/launchEntry"))));

		// Using the namespace launch_v1_0

		Assert.assertTrue(
			equals(
				launchEntry,
				LaunchEntrySerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"launch_v1_0",
								new GraphQLField(
									"launchEntry",
									new HashMap<String, Object>() {
										{
											put(
												"launchEntryId",
												launchEntry.getId());
										}
									},
									getGraphQLFields()))),
						"JSONObject/data", "JSONObject/launch_v1_0",
						"Object/launchEntry"))));
	}

	@Test
	public void testGraphQLGetLaunchEntryNotFound() throws Exception {
		Long irrelevantLaunchEntryId = RandomTestUtil.randomLong();

		// No namespace

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"launchEntry",
						new HashMap<String, Object>() {
							{
								put("launchEntryId", irrelevantLaunchEntryId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));

		// Using the namespace launch_v1_0

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"launch_v1_0",
						new GraphQLField(
							"launchEntry",
							new HashMap<String, Object>() {
								{
									put(
										"launchEntryId",
										irrelevantLaunchEntryId);
								}
							},
							getGraphQLFields()))),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected LaunchEntry testGraphQLGetLaunchEntry_addLaunchEntry()
		throws Exception {

		return testGraphQLLaunchEntry_addLaunchEntry();
	}

	@Test
	public void testGetLaunchSetLaunchEntriesPage() throws Exception {
		Long launchSetId = testGetLaunchSetLaunchEntriesPage_getLaunchSetId();
		Long irrelevantLaunchSetId =
			testGetLaunchSetLaunchEntriesPage_getIrrelevantLaunchSetId();

		Page<LaunchEntry> page =
			launchEntryResource.getLaunchSetLaunchEntriesPage(
				launchSetId, null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		if (irrelevantLaunchSetId != null) {
			LaunchEntry irrelevantLaunchEntry =
				testGetLaunchSetLaunchEntriesPage_addLaunchEntry(
					irrelevantLaunchSetId, randomIrrelevantLaunchEntry());

			page = launchEntryResource.getLaunchSetLaunchEntriesPage(
				irrelevantLaunchSetId, null, null,
				Pagination.of(1, (int)totalCount + 1), null);

			Assert.assertEquals(totalCount + 1, page.getTotalCount());

			assertContains(
				irrelevantLaunchEntry, (List<LaunchEntry>)page.getItems());
			assertValid(
				page,
				testGetLaunchSetLaunchEntriesPage_getExpectedActions(
					irrelevantLaunchSetId));
		}

		LaunchEntry launchEntry1 =
			testGetLaunchSetLaunchEntriesPage_addLaunchEntry(
				launchSetId, randomLaunchEntry());

		LaunchEntry launchEntry2 =
			testGetLaunchSetLaunchEntriesPage_addLaunchEntry(
				launchSetId, randomLaunchEntry());

		page = launchEntryResource.getLaunchSetLaunchEntriesPage(
			launchSetId, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(launchEntry1, (List<LaunchEntry>)page.getItems());
		assertContains(launchEntry2, (List<LaunchEntry>)page.getItems());
		assertValid(
			page,
			testGetLaunchSetLaunchEntriesPage_getExpectedActions(launchSetId));
	}

	protected Map<String, Map<String, String>>
			testGetLaunchSetLaunchEntriesPage_getExpectedActions(
				Long launchSetId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetLaunchSetLaunchEntriesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long launchSetId = testGetLaunchSetLaunchEntriesPage_getLaunchSetId();

		LaunchEntry launchEntry1 = randomLaunchEntry();

		launchEntry1 = testGetLaunchSetLaunchEntriesPage_addLaunchEntry(
			launchSetId, launchEntry1);

		for (EntityField entityField : entityFields) {
			Page<LaunchEntry> page =
				launchEntryResource.getLaunchSetLaunchEntriesPage(
					launchSetId, null,
					getFilterString(entityField, "between", launchEntry1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(launchEntry1),
				(List<LaunchEntry>)page.getItems());
		}
	}

	@Test
	public void testGetLaunchSetLaunchEntriesPageWithFilterDoubleEquals()
		throws Exception {

		testGetLaunchSetLaunchEntriesPageWithFilter(
			"eq", EntityField.Type.DOUBLE);
	}

	@Test
	public void testGetLaunchSetLaunchEntriesPageWithFilterStringContains()
		throws Exception {

		testGetLaunchSetLaunchEntriesPageWithFilter(
			"contains", EntityField.Type.STRING);
	}

	@Test
	public void testGetLaunchSetLaunchEntriesPageWithFilterStringEquals()
		throws Exception {

		testGetLaunchSetLaunchEntriesPageWithFilter(
			"eq", EntityField.Type.STRING);
	}

	@Test
	public void testGetLaunchSetLaunchEntriesPageWithFilterStringStartsWith()
		throws Exception {

		testGetLaunchSetLaunchEntriesPageWithFilter(
			"startswith", EntityField.Type.STRING);
	}

	protected void testGetLaunchSetLaunchEntriesPageWithFilter(
			String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long launchSetId = testGetLaunchSetLaunchEntriesPage_getLaunchSetId();

		LaunchEntry launchEntry1 =
			testGetLaunchSetLaunchEntriesPage_addLaunchEntry(
				launchSetId, randomLaunchEntry());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		LaunchEntry launchEntry2 =
			testGetLaunchSetLaunchEntriesPage_addLaunchEntry(
				launchSetId, randomLaunchEntry());

		for (EntityField entityField : entityFields) {
			Page<LaunchEntry> page =
				launchEntryResource.getLaunchSetLaunchEntriesPage(
					launchSetId, null,
					getFilterString(entityField, operator, launchEntry1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(launchEntry1),
				(List<LaunchEntry>)page.getItems());
		}
	}

	@Test
	public void testGetLaunchSetLaunchEntriesPageWithPagination()
		throws Exception {

		Long launchSetId = testGetLaunchSetLaunchEntriesPage_getLaunchSetId();

		Page<LaunchEntry> launchEntriesPage =
			launchEntryResource.getLaunchSetLaunchEntriesPage(
				launchSetId, null, null, null, null);

		int totalCount = GetterUtil.getInteger(
			launchEntriesPage.getTotalCount());

		LaunchEntry launchEntry1 =
			testGetLaunchSetLaunchEntriesPage_addLaunchEntry(
				launchSetId, randomLaunchEntry());

		LaunchEntry launchEntry2 =
			testGetLaunchSetLaunchEntriesPage_addLaunchEntry(
				launchSetId, randomLaunchEntry());

		LaunchEntry launchEntry3 =
			testGetLaunchSetLaunchEntriesPage_addLaunchEntry(
				launchSetId, randomLaunchEntry());

		// See com.liferay.portal.vulcan.internal.configuration.HeadlessAPICompanyConfiguration#pageSizeLimit

		int pageSizeLimit = 500;

		if (totalCount >= (pageSizeLimit - 2)) {
			Page<LaunchEntry> page1 =
				launchEntryResource.getLaunchSetLaunchEntriesPage(
					launchSetId, null, null,
					Pagination.of(
						(int)Math.ceil((totalCount + 1.0) / pageSizeLimit),
						pageSizeLimit),
					null);

			Assert.assertEquals(totalCount + 3, page1.getTotalCount());

			assertContains(launchEntry1, (List<LaunchEntry>)page1.getItems());

			Page<LaunchEntry> page2 =
				launchEntryResource.getLaunchSetLaunchEntriesPage(
					launchSetId, null, null,
					Pagination.of(
						(int)Math.ceil((totalCount + 2.0) / pageSizeLimit),
						pageSizeLimit),
					null);

			assertContains(launchEntry2, (List<LaunchEntry>)page2.getItems());

			Page<LaunchEntry> page3 =
				launchEntryResource.getLaunchSetLaunchEntriesPage(
					launchSetId, null, null,
					Pagination.of(
						(int)Math.ceil((totalCount + 3.0) / pageSizeLimit),
						pageSizeLimit),
					null);

			assertContains(launchEntry3, (List<LaunchEntry>)page3.getItems());
		}
		else {
			Page<LaunchEntry> page1 =
				launchEntryResource.getLaunchSetLaunchEntriesPage(
					launchSetId, null, null, Pagination.of(1, totalCount + 2),
					null);

			List<LaunchEntry> launchEntries1 =
				(List<LaunchEntry>)page1.getItems();

			Assert.assertEquals(
				launchEntries1.toString(), totalCount + 2,
				launchEntries1.size());

			Page<LaunchEntry> page2 =
				launchEntryResource.getLaunchSetLaunchEntriesPage(
					launchSetId, null, null, Pagination.of(2, totalCount + 2),
					null);

			Assert.assertEquals(totalCount + 3, page2.getTotalCount());

			List<LaunchEntry> launchEntries2 =
				(List<LaunchEntry>)page2.getItems();

			Assert.assertEquals(
				launchEntries2.toString(), 1, launchEntries2.size());

			Page<LaunchEntry> page3 =
				launchEntryResource.getLaunchSetLaunchEntriesPage(
					launchSetId, null, null,
					Pagination.of(1, (int)totalCount + 3), null);

			assertContains(launchEntry1, (List<LaunchEntry>)page3.getItems());
			assertContains(launchEntry2, (List<LaunchEntry>)page3.getItems());
			assertContains(launchEntry3, (List<LaunchEntry>)page3.getItems());
		}
	}

	@Test
	public void testGetLaunchSetLaunchEntriesPageWithSortDateTime()
		throws Exception {

		testGetLaunchSetLaunchEntriesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, launchEntry1, launchEntry2) -> {
				BeanTestUtil.setProperty(
					launchEntry1, entityField.getName(),
					new Date(System.currentTimeMillis() - (2 * Time.MINUTE)));
			});
	}

	@Test
	public void testGetLaunchSetLaunchEntriesPageWithSortDouble()
		throws Exception {

		testGetLaunchSetLaunchEntriesPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, launchEntry1, launchEntry2) -> {
				BeanTestUtil.setProperty(
					launchEntry1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					launchEntry2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetLaunchSetLaunchEntriesPageWithSortInteger()
		throws Exception {

		testGetLaunchSetLaunchEntriesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, launchEntry1, launchEntry2) -> {
				BeanTestUtil.setProperty(
					launchEntry1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					launchEntry2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetLaunchSetLaunchEntriesPageWithSortString()
		throws Exception {

		testGetLaunchSetLaunchEntriesPageWithSort(
			EntityField.Type.STRING,
			(entityField, launchEntry1, launchEntry2) -> {
				Class<?> clazz = launchEntry1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						launchEntry1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						launchEntry2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						launchEntry1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						launchEntry2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						launchEntry1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						launchEntry2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetLaunchSetLaunchEntriesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, LaunchEntry, LaunchEntry, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long launchSetId = testGetLaunchSetLaunchEntriesPage_getLaunchSetId();

		LaunchEntry launchEntry1 = randomLaunchEntry();
		LaunchEntry launchEntry2 = randomLaunchEntry();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, launchEntry1, launchEntry2);
		}

		launchEntry1 = testGetLaunchSetLaunchEntriesPage_addLaunchEntry(
			launchSetId, launchEntry1);

		launchEntry2 = testGetLaunchSetLaunchEntriesPage_addLaunchEntry(
			launchSetId, launchEntry2);

		Page<LaunchEntry> page =
			launchEntryResource.getLaunchSetLaunchEntriesPage(
				launchSetId, null, null, null, null);

		for (EntityField entityField : entityFields) {
			Page<LaunchEntry> ascPage =
				launchEntryResource.getLaunchSetLaunchEntriesPage(
					launchSetId, null, null,
					Pagination.of(1, (int)page.getTotalCount() + 1),
					entityField.getName() + ":asc");

			assertContains(launchEntry1, (List<LaunchEntry>)ascPage.getItems());
			assertContains(launchEntry2, (List<LaunchEntry>)ascPage.getItems());

			Page<LaunchEntry> descPage =
				launchEntryResource.getLaunchSetLaunchEntriesPage(
					launchSetId, null, null,
					Pagination.of(1, (int)page.getTotalCount() + 1),
					entityField.getName() + ":desc");

			assertContains(
				launchEntry2, (List<LaunchEntry>)descPage.getItems());
			assertContains(
				launchEntry1, (List<LaunchEntry>)descPage.getItems());
		}
	}

	protected LaunchEntry testGetLaunchSetLaunchEntriesPage_addLaunchEntry(
			Long launchSetId, LaunchEntry launchEntry)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetLaunchSetLaunchEntriesPage_getLaunchSetId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetLaunchSetLaunchEntriesPage_getIrrelevantLaunchSetId()
		throws Exception {

		return null;
	}

	@Test
	public void testBatchEngineDeleteImportTask() throws Exception {
		Assert.assertTrue(true);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected LaunchEntry testGraphQLLaunchEntry_addLaunchEntry()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		LaunchEntry launchEntry, List<LaunchEntry> launchEntries) {

		boolean contains = false;

		for (LaunchEntry item : launchEntries) {
			if (equals(launchEntry, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			launchEntries + " does not contain " + launchEntry, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		LaunchEntry launchEntry1, LaunchEntry launchEntry2) {

		Assert.assertTrue(
			launchEntry1 + " does not equal " + launchEntry2,
			equals(launchEntry1, launchEntry2));
	}

	protected void assertEquals(
		List<LaunchEntry> launchEntries1, List<LaunchEntry> launchEntries2) {

		Assert.assertEquals(launchEntries1.size(), launchEntries2.size());

		for (int i = 0; i < launchEntries1.size(); i++) {
			LaunchEntry launchEntry1 = launchEntries1.get(i);
			LaunchEntry launchEntry2 = launchEntries2.get(i);

			assertEquals(launchEntry1, launchEntry2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<LaunchEntry> launchEntries1, List<LaunchEntry> launchEntries2) {

		Assert.assertEquals(launchEntries1.size(), launchEntries2.size());

		for (LaunchEntry launchEntry1 : launchEntries1) {
			boolean contains = false;

			for (LaunchEntry launchEntry2 : launchEntries2) {
				if (equals(launchEntry1, launchEntry2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				launchEntries2 + " does not contain " + launchEntry1, contains);
		}
	}

	protected void assertValid(LaunchEntry launchEntry) throws Exception {
		boolean valid = true;

		if (launchEntry.getDateCreated() == null) {
			valid = false;
		}

		if (launchEntry.getDateModified() == null) {
			valid = false;
		}

		if (launchEntry.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (launchEntry.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("classNameId", additionalAssertFieldName)) {
				if (launchEntry.getClassNameId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("classPK", additionalAssertFieldName)) {
				if (launchEntry.getClassPK() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("launchSetId", additionalAssertFieldName)) {
				if (launchEntry.getLaunchSetId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (launchEntry.getStatus() == null) {
					valid = false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Page<LaunchEntry> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<LaunchEntry> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<LaunchEntry> launchEntries = page.getItems();

		int size = launchEntries.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);

		assertValid(page.getActions(), expectedActions);
	}

	protected void assertValid(
		Map<String, Map<String, String>> actions1,
		Map<String, Map<String, String>> actions2) {

		for (String key : actions2.keySet()) {
			Map action = actions1.get(key);

			Assert.assertNotNull(key + " does not contain an action", action);

			Map<String, String> expectedAction = actions2.get(key);

			Assert.assertEquals(
				expectedAction.get("method"), action.get("method"));
			Assert.assertEquals(expectedAction.get("href"), action.get("href"));
		}
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(new GraphQLField("id"));

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.launch.rest.dto.v1_0.LaunchEntry.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(
			java.lang.reflect.Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		LaunchEntry launchEntry1, LaunchEntry launchEntry2) {

		if (launchEntry1 == launchEntry2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)launchEntry1.getActions(),
						(Map)launchEntry2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("classNameId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						launchEntry1.getClassNameId(),
						launchEntry2.getClassNameId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("classPK", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						launchEntry1.getClassPK(), launchEntry2.getClassPK())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						launchEntry1.getDateCreated(),
						launchEntry2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						launchEntry1.getDateModified(),
						launchEntry2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						launchEntry1.getId(), launchEntry2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("launchSetId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						launchEntry1.getLaunchSetId(),
						launchEntry2.getLaunchSetId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						launchEntry1.getStatus(), launchEntry2.getStatus())) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
	}

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	protected java.lang.reflect.Field[] getDeclaredFields(Class clazz)
		throws Exception {

		if (clazz.getClassLoader() == null) {
			return new java.lang.reflect.Field[0];
		}

		return TransformUtil.transform(
			ReflectionUtil.getDeclaredFields(clazz),
			field -> {
				if (field.isSynthetic()) {
					return null;
				}

				return field;
			},
			java.lang.reflect.Field.class);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_launchEntryResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_launchEntryResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		if (entityModel == null) {
			return Collections.emptyList();
		}

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		return TransformUtil.transform(
			getEntityFields(),
			entityField -> {
				if (!Objects.equals(entityField.getType(), type) ||
					ArrayUtil.contains(
						getIgnoredEntityFieldNames(), entityField.getName())) {

					return null;
				}

				return entityField;
			});
	}

	protected String getFilterString(
		EntityField entityField, String operator, LaunchEntry launchEntry) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("classNameId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("classPK")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				Date date = launchEntry.getDateCreated();

				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(_format.format(date.getTime() - (2 * Time.SECOND)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(_format.format(date.getTime() + (2 * Time.SECOND)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_format.format(launchEntry.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			if (operator.equals("between")) {
				Date date = launchEntry.getDateModified();

				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(_format.format(date.getTime() - (2 * Time.SECOND)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(_format.format(date.getTime() + (2 * Time.SECOND)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_format.format(launchEntry.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("launchSetId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("status")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword(
			"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD);

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected LaunchEntry randomLaunchEntry() throws Exception {
		return new LaunchEntry() {
			{
				classNameId = RandomTestUtil.randomLong();
				classPK = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				launchSetId = RandomTestUtil.randomLong();
			}
		};
	}

	protected LaunchEntry randomIrrelevantLaunchEntry() throws Exception {
		LaunchEntry randomIrrelevantLaunchEntry = randomLaunchEntry();

		return randomIrrelevantLaunchEntry;
	}

	protected LaunchEntry randomPatchLaunchEntry() throws Exception {
		return randomLaunchEntry();
	}

	protected LaunchEntryResource launchEntryResource;
	protected com.liferay.portal.kernel.model.Group irrelevantGroup;
	protected com.liferay.portal.kernel.model.Company testCompany;
	protected com.liferay.portal.kernel.model.Group testGroup;

	protected static class BeanTestUtil {

		public static void copyProperties(Object source, Object target)
			throws Exception {

			Class<?> sourceClass = source.getClass();

			Class<?> targetClass = target.getClass();

			for (java.lang.reflect.Field field :
					_getAllDeclaredFields(sourceClass)) {

				if (field.isSynthetic()) {
					continue;
				}

				Method getMethod = _getMethod(
					sourceClass, field.getName(), "get");

				try {
					Method setMethod = _getMethod(
						targetClass, field.getName(), "set",
						getMethod.getReturnType());

					setMethod.invoke(target, getMethod.invoke(source));
				}
				catch (Exception e) {
					continue;
				}
			}
		}

		public static boolean hasProperty(Object bean, String name) {
			Method setMethod = _getMethod(
				bean.getClass(), "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod != null) {
				return true;
			}

			return false;
		}

		public static void setProperty(Object bean, String name, Object value)
			throws Exception {

			Class<?> clazz = bean.getClass();

			Method setMethod = _getMethod(
				clazz, "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod == null) {
				throw new NoSuchMethodException();
			}

			Class<?>[] parameterTypes = setMethod.getParameterTypes();

			setMethod.invoke(bean, _translateValue(parameterTypes[0], value));
		}

		private static List<java.lang.reflect.Field> _getAllDeclaredFields(
			Class<?> clazz) {

			List<java.lang.reflect.Field> fields = new ArrayList<>();

			while ((clazz != null) && (clazz != Object.class)) {
				for (java.lang.reflect.Field field :
						clazz.getDeclaredFields()) {

					fields.add(field);
				}

				clazz = clazz.getSuperclass();
			}

			return fields;
		}

		private static Method _getMethod(Class<?> clazz, String name) {
			for (Method method : clazz.getMethods()) {
				if (name.equals(method.getName()) &&
					(method.getParameterCount() == 1) &&
					_parameterTypes.contains(method.getParameterTypes()[0])) {

					return method;
				}
			}

			return null;
		}

		private static Method _getMethod(
				Class<?> clazz, String fieldName, String prefix,
				Class<?>... parameterTypes)
			throws Exception {

			return clazz.getMethod(
				prefix + StringUtil.upperCaseFirstLetter(fieldName),
				parameterTypes);
		}

		private static Object _translateValue(
			Class<?> parameterType, Object value) {

			if ((value instanceof Integer) &&
				parameterType.equals(Long.class)) {

				Integer intValue = (Integer)value;

				return intValue.longValue();
			}

			return value;
		}

		private static final Set<Class<?>> _parameterTypes = new HashSet<>(
			Arrays.asList(
				Boolean.class, Date.class, Double.class, Integer.class,
				Long.class, Map.class, String.class));

	}

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseLaunchEntryResourceTestCase.class);

	private static Format _format;

	private com.liferay.portal.kernel.model.User _testCompanyAdminUser;

	@Inject
	private com.liferay.launch.rest.resource.v1_0.LaunchEntryResource
		_launchEntryResource;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private ResourceActionLocalService _resourceActionLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private ScopeChecker _scopeChecker;

	@Inject
	private UserLocalService _userLocalService;

	@Inject
	private VulcanCRUDItemDelegateBuilderRegistry
		_vulcanCRUDItemDelegateBuilderRegistry;

}