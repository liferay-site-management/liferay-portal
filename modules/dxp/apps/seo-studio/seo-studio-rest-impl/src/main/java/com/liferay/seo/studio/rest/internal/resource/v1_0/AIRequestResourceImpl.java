/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.rest.internal.resource.v1_0;

import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.odata.entity.v1_0.provider.EntityModelProvider;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.seo.studio.rest.dto.v1_0.AIRequest;
import com.liferay.seo.studio.rest.resource.v1_0.AIRequestResource;

import jakarta.ws.rs.core.MultivaluedMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Noor Najjar
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/ai-request.properties",
	scope = ServiceScope.PROTOTYPE, service = AIRequestResource.class
)
public class AIRequestResourceImpl extends BaseAIRequestResourceImpl {

	@Override
	public Page<AIRequest> getDomainAIRequestsPage(
			Long domainId, String aggregateOn, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		if (Validator.isNull(aggregateOn) ||
			(!StringUtil.equals(aggregateOn, "agentName") &&
			 !StringUtil.equals(aggregateOn, "pageURL"))) {

			return Page.of(Collections.emptyList());
		}

		Page<ObjectEntry> objectEntriesPage =
			_objectEntryManager.getObjectEntries(
				contextCompany.getCompanyId(),
				_objectDefinitionLocalService.getObjectDefinition(
					contextCompany.getCompanyId(), "SEOStudioAIRequest"),
				null, null, _createDTOConverterContext(),
				_getFilterString(domainId), Pagination.of(1, _DELTA), search,
				sorts);

		Map<String, Integer> countsByGroupKey = new LinkedHashMap<>();

		for (ObjectEntry objectEntry : objectEntriesPage.getItems()) {
			String groupKey = GetterUtil.getString(
				objectEntry.getPropertyValue(aggregateOn));

			countsByGroupKey.merge(
				groupKey,
				GetterUtil.getInteger(objectEntry.getPropertyValue("count")),
				Integer::sum);
		}

		List<AIRequest> aiRequests = new ArrayList<>(countsByGroupKey.size());

		for (Map.Entry<String, Integer> entry : countsByGroupKey.entrySet()) {
			aiRequests.add(
				_toAggregatedAIRequest(
					aggregateOn, entry.getKey(), entry.getValue()));
		}

		return Page.of(aiRequests, pagination, aiRequests.size());
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModelProvider.getEntityModel(
			_objectDefinitionLocalService.getObjectDefinition(
				contextCompany.getCompanyId(), "SEOStudioAIRequest"));
	}

	private DTOConverterContext _createDTOConverterContext() {
		return new DefaultDTOConverterContext(
			contextAcceptLanguage.isAcceptAllLanguages(), null,
			_dtoConverterRegistry, contextHttpServletRequest, null,
			contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
			contextUser);
	}

	private String _getFilterString(Long domainId) {
		StringBundler sb = new StringBundler(6);

		sb.append(
			"r_seoStudioDomainToSEOStudioAIRequests_seoStudioDomainId eq '");
		sb.append(domainId);
		sb.append("'");

		if (contextUriInfo == null) {
			return sb.toString();
		}

		String filter = contextUriInfo.getQueryParameters(
		).getFirst(
			"filter"
		);

		if (Validator.isNull(filter)) {
			return sb.toString();
		}

		sb.append(" and (");
		sb.append(filter);
		sb.append(")");

		return sb.toString();
	}

	private AIRequest _toAggregatedAIRequest(
		String aggregateOn, String groupKey, Integer count) {

		AIRequest aiRequest = new AIRequest();

		aiRequest.setCount(() -> count);

		if (StringUtil.equals(aggregateOn, "pageURL")) {
			aiRequest.setPageURL(() -> groupKey);
		}
		else {
			aiRequest.setAgentName(() -> groupKey);
		}

		return aiRequest;
	}

	private static final int _DELTA = 10000;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private EntityModelProvider _entityModelProvider;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference(target = "(object.entry.manager.storage.type=default)")
	private ObjectEntryManager _objectEntryManager;

}