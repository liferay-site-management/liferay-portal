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

		_addAIRequestObjectEntry("GPTBot", today, 5);
		_addAIRequestObjectEntry("GPTBot", yesterday, 3);
		_addAIRequestObjectEntry("ClaudeBot", today, 7);
		_addAIRequestObjectEntry("ClaudeBot", yesterday, 4);
		_addAIRequestObjectEntry("ClaudeBot", twoDaysAgo, 2);

		Page<AIRequest> page = aiRequestResource.getAIRequestsPage(
			Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		Map<String, Integer> sumByAgentName = new HashMap<>();

		for (AIRequest aiRequest : page.getItems()) {
			sumByAgentName.put(aiRequest.getAgentName(), aiRequest.getCount());
		}

		Assert.assertEquals(
			sumByAgentName.toString(), Integer.valueOf(8),
			sumByAgentName.get("GPTBot"));
		Assert.assertEquals(
			sumByAgentName.toString(), Integer.valueOf(13),
			sumByAgentName.get("ClaudeBot"));
	}

	@Ignore
	@Override
	@Test
	public void testGetAIRequestsPageWithPagination() throws Exception {
	}

	private ObjectEntry _addAIRequestObjectEntry(
			String agentName, Date requestDate, Integer count)
		throws Exception {

		long agentObjectEntryId = _addOrGetAgentObjectEntryId(agentName);

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
				"count", count
			).put(
				"r_seoStudioAIAgentToAIRequests_l_sEOStudioAIAgentId",
				agentObjectEntryId
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

	private long _addOrGetAgentObjectEntryId(String name) throws Exception {
		Long cachedId = _agentObjectEntryIds.get(name);

		if (cachedId != null) {
			return cachedId;
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				testCompany.getCompanyId(), "SEOStudioAIAgent");

		ObjectEntry objectEntry = new ObjectEntry();

		objectEntry.setProperties(
			() -> HashMapBuilder.<String, Object>put(
				"name", name
			).build());

		User adminUser = UserTestUtil.getAdminUser(testCompany.getCompanyId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(adminUser));
		PrincipalThreadLocal.setName(adminUser.getUserId());

		ObjectEntry createdAgent = _objectEntryManager.addObjectEntry(
			new DefaultDTOConverterContext(
				false, Collections.emptyMap(), _dtoConverterRegistry, null,
				LocaleUtil.getDefault(), null, adminUser),
			objectDefinition, objectEntry, null);

		long agentObjectEntryId = createdAgent.getId();

		_agentObjectEntryIds.put(name, agentObjectEntryId);

		return agentObjectEntryId;
	}

	private Date _daysAgo(int days) {
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DATE, -days);

		return calendar.getTime();
	}

	private final Map<String, Long> _agentObjectEntryIds = new HashMap<>();

	@Inject
	private DTOConverterRegistry _dtoConverterRegistry;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject(filter = "object.entry.manager.storage.type=default")
	private ObjectEntryManager _objectEntryManager;

}