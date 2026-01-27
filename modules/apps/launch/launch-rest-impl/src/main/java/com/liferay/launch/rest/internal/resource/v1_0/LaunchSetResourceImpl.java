/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.rest.internal.resource.v1_0;

import com.liferay.launch.rest.dto.v1_0.LaunchSet;
import com.liferay.launch.rest.internal.odata.entity.v1_0.LaunchSetEntityModel;
import com.liferay.launch.rest.resource.v1_0.LaunchSetResource;
import com.liferay.launch.service.LaunchSetLocalService;
import com.liferay.launch.service.LaunchSetService;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ArrayUtil;
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
	properties = "OSGI-INF/liferay/rest/v1_0/launch-set.properties",
	scope = ServiceScope.PROTOTYPE, service = LaunchSetResource.class
)
public class LaunchSetResourceImpl extends BaseLaunchSetResourceImpl {

	@Override
	public void deleteLaunchSet(Long launchSetId) throws Exception {
		_launchSetService.deleteLaunchSet(launchSetId);
	}

	@Override
	public void deleteLaunchSetByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		_launchSetService.deleteLaunchSet(
			externalReferenceCode, contextCompany.getCompanyId());
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public LaunchSet getLaunchSet(Long launchSetId) throws Exception {
		return _toLaunchSet(launchSetId);
	}

	@Override
	public LaunchSet getLaunchSetByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		com.liferay.launch.model.LaunchSet launchSet =
			_launchSetLocalService.fetchLaunchSetByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		return _toLaunchSet(launchSet.getLaunchSetId());
	}

	@Override
	public Page<LaunchSet> getLaunchSetsPage(
			String search, Integer[] statuses, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		if (ArrayUtil.isEmpty(sorts)) {
			sorts = new Sort[] {
				new Sort(Field.getSortableFieldName(Field.MODIFIED_DATE), true)
			};
		}

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> booleanQuery.getPreBooleanFilter(), filter,
			com.liferay.launch.model.LaunchSet.class.getName(), search,
			pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute("statuses", statuses);
				searchContext.setCompanyId(contextCompany.getCompanyId());

				if (Validator.isNotNull(search)) {
					searchContext.setKeywords(search);
				}
			},
			sorts,
			document -> _toLaunchSet(
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
	}

	@Override
	public LaunchSet patchLaunchSet(Long launchSetId, LaunchSet launchSet)
		throws Exception {

		return _toLaunchSet(
			_launchSetService.updateLaunchSet(
				launchSet.getExternalReferenceCode(), launchSetId,
				launchSet.getDescription(), launchSet.getName()));
	}

	@Override
	public LaunchSet patchLaunchSetByExternalReferenceCode(
			String externalReferenceCode, LaunchSet launchSet)
		throws Exception {

		com.liferay.launch.model.LaunchSet originalLaunchSet =
			_launchSetLocalService.fetchLaunchSetByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		return patchLaunchSet(originalLaunchSet.getLaunchSetId(), launchSet);
	}

	@Override
	public LaunchSet putLaunchSet(Long launchSetId, LaunchSet launchSet)
		throws Exception {

		return patchLaunchSet(launchSetId, launchSet);
	}

	private <T extends BaseModel<T>> DefaultDTOConverterContext
		_getDTOConverterContext(com.liferay.launch.model.LaunchSet launchSet) {

		return new DefaultDTOConverterContext(
			contextAcceptLanguage.isAcceptAllLanguages(),
			HashMapBuilder.put(
				"delete",
				addAction(
					ActionKeys.DELETE, launchSet.getLaunchSetId(),
					"getLaunchEntry", _launchSetModelResourcePermission)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, launchSet.getLaunchSetId(),
					"getLaunchEntry", _launchSetModelResourcePermission)
			).put(
				"update",
				addAction(
					ActionKeys.UPDATE, launchSet.getLaunchSetId(),
					"getLaunchEntry", _launchSetModelResourcePermission)
			).build(),
			null, contextHttpServletRequest, launchSet.getLaunchSetId(),
			contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
			contextUser);
	}

	private LaunchSet _toLaunchSet(com.liferay.launch.model.LaunchSet launchSet)
		throws Exception {

		return _launchSetDTOConverter.toDTO(
			_getDTOConverterContext(launchSet), launchSet);
	}

	private LaunchSet _toLaunchSet(Long launchSetId) throws Exception {
		return _toLaunchSet(_launchSetLocalService.fetchLaunchSet(launchSetId));
	}

	private static final EntityModel _entityModel = new LaunchSetEntityModel();

	@Reference(
		target = "(component.name=com.liferay.launch.rest.internal.dto.v1_0.converter.LaunchSetDTOConverter)"
	)
	private DTOConverter<com.liferay.launch.model.LaunchSet, LaunchSet>
		_launchSetDTOConverter;

	@Reference
	private LaunchSetLocalService _launchSetLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.launch.model.LaunchSet)"
	)
	private volatile ModelResourcePermission<com.liferay.launch.model.LaunchSet>
		_launchSetModelResourcePermission;

	@Reference
	private LaunchSetService _launchSetService;

}