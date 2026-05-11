/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.rest.internal.resource.v1_0;

import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.odata.entity.v1_0.provider.EntityModelProvider;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.seo.studio.rest.dto.v1_0.AIRequest;
import com.liferay.seo.studio.rest.resource.v1_0.AIRequestResource;

import jakarta.ws.rs.core.MultivaluedMap;

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
	public Page<AIRequest> getAIRequestsPage(Pagination pagination)
		throws Exception {

		Page<ObjectEntry> objectEntriesPage =
			_objectEntryManager.getObjectEntries(
				contextCompany.getCompanyId(),
				_objectDefinitionLocalService.getObjectDefinition(
					contextCompany.getCompanyId(), "SEOStudioAIAgent"),
				null, null, _createDTOConverterContext(), null, pagination,
				null, null);

		return Page.of(
			transform(objectEntriesPage.getItems(), this::_toAIRequest),
			pagination, objectEntriesPage.getTotalCount());
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModelProvider.getEntityModel(
			_objectDefinitionLocalService.getObjectDefinition(
				contextCompany.getCompanyId(), "SEOStudioAIAgent"));
	}

	private DTOConverterContext _createDTOConverterContext() {
		return new DefaultDTOConverterContext(
			contextAcceptLanguage.isAcceptAllLanguages(), null,
			_dtoConverterRegistry, contextHttpServletRequest, null,
			contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
			contextUser);
	}

	private AIRequest _toAIRequest(ObjectEntry agentObjectEntry) {
		AIRequest aiRequest = new AIRequest();

		aiRequest.setAgentName(
			() -> GetterUtil.getString(
				agentObjectEntry.getPropertyValue("name")));
		aiRequest.setCount(
			() -> GetterUtil.getInteger(
				agentObjectEntry.getPropertyValue("totalCount")));

		return aiRequest;
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private EntityModelProvider _entityModelProvider;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference(target = "(object.entry.manager.storage.type=default)")
	private ObjectEntryManager _objectEntryManager;

}