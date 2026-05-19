/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.rest.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
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
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Noor Najjar
 */
@RunWith(Arquillian.class)
public class AIRequestResourceTest extends BaseAIRequestResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_adminUser = UserTestUtil.getAdminUser(testCompany.getCompanyId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_adminUser));
		PrincipalThreadLocal.setName(_adminUser.getUserId());

		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			null, _adminUser.getUserId(),
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT, "Test Account",
			null, null, "test-account@example.com", null, null,
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED, null);

		ObjectEntry domainObjectEntry = _addObjectEntry(
			"SEOStudioDomain",
			HashMapBuilder.<String, Object>put(
				"defaultScanScope", "entireDomain"
			).put(
				"hostname", "domain-" + RandomTestUtil.randomString()
			).put(
				"name", "Test Domain"
			).put(
				"r_seoStudioInstanceToSEOStudioDomains_seoStudioInstanceId",
				() -> {
					ObjectEntry instanceObjectEntry = _addObjectEntry(
						"SEOStudioInstance",
						HashMapBuilder.<String, Object>put(
							"hostname",
							"instance-" + RandomTestUtil.randomString()
						).put(
							"name", "Test Instance"
						).put(
							"r_accountToSEOStudioInstances_accountEntryId",
							accountEntry.getAccountEntryId()
						).build());

					return instanceObjectEntry.getId();
				}
			).build());

		_domainId = domainObjectEntry.getId();

		ObjectEntry scanObjectEntry = _addObjectEntry(
			"SEOStudioScan",
			HashMapBuilder.<String, Object>put(
				"r_seoStudioDomainToSEOStudioScans_seoStudioDomainId", _domainId
			).put(
				"requestDate",
				FastDateFormatFactoryUtil.getSimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss'Z'"
				).format(
					new Date()
				)
			).put(
				"scanScope", "entireDomain"
			).put(
				"scanType", "full"
			).put(
				"triggeredBy", "manual"
			).build());

		_scanId = scanObjectEntry.getId();
	}

	@Override
	@Test
	public void testGetDomainAIRequestsPage() throws Exception {
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
	public void testGetDomainAIRequestsPageWithPagination() throws Exception {
	}

	private ObjectEntry _addAIRequestObjectEntry(
			String agentName, String pageURL, Date requestDate, Integer count)
		throws Exception {

		String requestDateString =
			FastDateFormatFactoryUtil.getSimpleDateFormat(
				"yyyy-MM-dd"
			).format(
				requestDate
			);

		return _addObjectEntry(
			"SEOStudioAIRequest",
			HashMapBuilder.<String, Object>put(
				"agentName", agentName
			).put(
				"count", count
			).put(
				"pageURL", pageURL
			).put(
				"r_seoStudioDomainToSEOStudioAIRequests_seoStudioDomainId",
				_domainId
			).put(
				"r_seoStudioScanToSEOStudioAIRequests_seoStudioScanId", _scanId
			).put(
				"requestDate", requestDateString
			).build());
	}

	private ObjectEntry _addObjectEntry(
			String objectDefinitionName, Map<String, Object> properties)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				testCompany.getCompanyId(), objectDefinitionName);

		ObjectEntry objectEntry = new ObjectEntry();

		objectEntry.setProperties(() -> properties);

		return _objectEntryManager.addObjectEntry(
			new DefaultDTOConverterContext(
				false, Collections.emptyMap(), _dtoConverterRegistry, null,
				LocaleUtil.getDefault(), null, _adminUser),
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

		Page<AIRequest> page = aiRequestResource.getDomainAIRequestsPage(
			_domainId, aggregateOn, null, null, Pagination.of(1, 10), null);

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
	private AccountEntryLocalService _accountEntryLocalService;

	private User _adminUser;
	private long _domainId;

	@Inject
	private DTOConverterRegistry _dtoConverterRegistry;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject(filter = "object.entry.manager.storage.type=default")
	private ObjectEntryManager _objectEntryManager;

	private long _scanId;

}