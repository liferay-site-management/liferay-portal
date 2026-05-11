/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.seo.studio.rest.client.dto.v1_0.AIRequest;
import com.liferay.seo.studio.rest.client.pagination.Page;
import com.liferay.seo.studio.rest.client.pagination.Pagination;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Noor Najjar
 */
@RunWith(Arquillian.class)
public class AIRequestResourceTest extends BaseAIRequestResourceTestCase {

	@Override
	@Test
	public void testGetAIRequestsPage() throws Exception {
		Date today = new Date();
		Date yesterday = _daysAgo(1);
		Date twoDaysAgo = _daysAgo(2);

		_addAIRequestObjectEntry("GPTBot", "/foo", today, 5);
		_addAIRequestObjectEntry("GPTBot", "/bar", yesterday, 3);
		_addAIRequestObjectEntry("ClaudeBot", "/foo", today, 7);
		_addAIRequestObjectEntry("ClaudeBot", "/bar", yesterday, 4);
		_addAIRequestObjectEntry("ClaudeBot", "/baz", twoDaysAgo, 2);

		_testGetAIRequestsPageWithAggregation(
			"agentName", 2,
			HashMapBuilder.put(
				"ClaudeBot", 13
			).put(
				"GPTBot", 8
			).build());

		_testGetAIRequestsPageWithAggregation(
			"pageURL", 3,
			HashMapBuilder.put(
				"/bar", 7
			).put(
				"/baz", 2
			).put(
				"/foo", 12
			).build());
	}

	@Ignore
	@Override
	@Test
	public void testGetAIRequestsPageWithPagination() throws Exception {
	}

	private ObjectEntry _addAIRequestObjectEntry(
			String agentName, String pageURL, Date requestDate, Integer count)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				testCompany.getCompanyId(), "SEOStudioAIRequest");

		String requestDateString =
			FastDateFormatFactoryUtil.getSimpleDateFormat(
				"yyyy-MM-dd"
			).format(
				requestDate
			);

		ObjectEntry objectEntry = new ObjectEntry();

		objectEntry.setProperties(
			() -> HashMapBuilder.<String, Object>put(
				"agentName", agentName
			).put(
				"count", count
			).put(
				"pageURL", pageURL
			).put(
				"requestDate", requestDateString
			).build());

		User adminUser = UserTestUtil.getAdminUser(testCompany.getCompanyId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(adminUser));
		PrincipalThreadLocal.setName(adminUser.getUserId());

		return _objectEntryManager.addObjectEntry(
			new DefaultDTOConverterContext(
				false, Collections.emptyMap(), _dtoConverterRegistry, null,
				LocaleUtil.getDefault(), null, adminUser),
			objectDefinition, objectEntry, null);
	}

	private Date _daysAgo(int days) {
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DATE, -days);

		return calendar.getTime();
	}

	private void _testGetAIRequestsPageWithAggregation(
			String aggregateOn, long expectedCount,
			Map<String, Integer> expectedSumByAggregateTerm)
		throws Exception {

		Page<AIRequest> page = aiRequestResource.getAIRequestsPage(
			aggregateOn, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(expectedCount, page.getTotalCount());

		Map<String, Integer> sumByAggregateTerm = new HashMap<>();

		for (AIRequest aiRequest : page.getItems()) {
			String term;

			if (StringUtil.equals(aggregateOn, "pageURL")) {
				term = aiRequest.getPageURL();
			}
			else {
				term = aiRequest.getAgentName();
			}

			sumByAggregateTerm.put(term, aiRequest.getCount());
		}

		Assert.assertEquals(expectedSumByAggregateTerm, sumByAggregateTerm);
	}

	@Inject
	private DTOConverterRegistry _dtoConverterRegistry;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject(filter = "object.entry.manager.storage.type=default")
	private ObjectEntryManager _objectEntryManager;

}