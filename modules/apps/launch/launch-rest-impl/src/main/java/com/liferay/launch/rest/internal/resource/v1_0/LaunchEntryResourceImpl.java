/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.rest.internal.resource.v1_0;

import com.liferay.launch.model.LaunchSet;
import com.liferay.launch.rest.dto.v1_0.LaunchEntry;
import com.liferay.launch.rest.internal.odata.entity.v1_0.LaunchEntryEntityModel;
import com.liferay.launch.rest.resource.v1_0.LaunchEntryResource;
import com.liferay.launch.service.LaunchEntryLocalService;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import jakarta.ws.rs.core.MultivaluedMap;

import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author David Truong
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/launch-entry.properties",
	scope = ServiceScope.PROTOTYPE, service = LaunchEntryResource.class
)
public class LaunchEntryResourceImpl extends BaseLaunchEntryResourceImpl {

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public LaunchEntry getLaunchEntry(Long launchEntryId) throws Exception {
		return _toLaunchEntry(launchEntryId);
	}

	@Override
	public Page<LaunchEntry> getLaunchSetLaunchEntriesPage(
			Long launchSetId, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> booleanQuery.getPreBooleanFilter(), filter,
			com.liferay.launch.model.LaunchEntry.class.getName(), search,
			pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK, Field.UID),
			searchContext -> {
				searchContext.setAttribute("launchSetId", launchSetId);
				searchContext.setCompanyId(contextCompany.getCompanyId());

				if (Validator.isNotNull(search)) {
					searchContext.setKeywords(search);
				}
			},
			sorts,
			document -> {
				long launchEntryId = GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK));

				com.liferay.launch.model.LaunchEntry launchEntry =
					_launchEntryLocalService.fetchLaunchEntry(launchEntryId);

				return _launchEntryDTOConverter.toDTO(
					_getDTOConverterContext(launchEntry), launchEntry);
			});
	}

	private <T extends BaseModel<T>> DefaultDTOConverterContext
		_getDTOConverterContext(
			com.liferay.launch.model.LaunchEntry launchEntry) {

		return new DefaultDTOConverterContext(
			contextAcceptLanguage.isAcceptAllLanguages(),
			HashMapBuilder.put(
				"delete",
				addAction(
					ActionKeys.DELETE, launchEntry.getLaunchSetId(),
					"getLaunchEntry", _launchSetModelResourcePermission)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, launchEntry.getLaunchSetId(),
					"getLaunchEntry", _launchSetModelResourcePermission)
			).put(
				"update",
				addAction(
					ActionKeys.UPDATE, launchEntry.getLaunchSetId(),
					"getLaunchEntry", _launchSetModelResourcePermission)
			).build(),
			null, contextHttpServletRequest, launchEntry.getLaunchSetId(),
			contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
			contextUser);
	}

	private LaunchEntry _toLaunchEntry(Long launchEntryId) throws Exception {
		com.liferay.launch.model.LaunchEntry launchEntry =
			_launchEntryLocalService.getLaunchEntry(launchEntryId);

		return _launchEntryDTOConverter.toDTO(
			_getDTOConverterContext(launchEntry), launchEntry);
	}

	private static final EntityModel _entityModel =
		new LaunchEntryEntityModel();

	@Reference(
		target = "(component.name=com.liferay.launch.rest.internal.dto.v1_0.converter.LaunchEntryDTOConverter)"
	)
	private DTOConverter<com.liferay.launch.model.LaunchEntry, LaunchEntry>
		_launchEntryDTOConverter;

	@Reference
	private LaunchEntryLocalService _launchEntryLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.launch.model.LaunchSet)"
	)
	private volatile ModelResourcePermission<LaunchSet>
		_launchSetModelResourcePermission;

}