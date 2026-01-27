/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.rest.internal.graphql.mutation.v1_0;

import com.liferay.launch.rest.dto.v1_0.LaunchSet;
import com.liferay.launch.rest.resource.v1_0.LaunchEntryResource;
import com.liferay.launch.rest.resource.v1_0.LaunchSetResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineExportTaskResource;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import jakarta.annotation.Generated;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.function.BiFunction;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author David Truong
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setLaunchEntryResourceComponentServiceObjects(
		ComponentServiceObjects<LaunchEntryResource>
			launchEntryResourceComponentServiceObjects) {

		_launchEntryResourceComponentServiceObjects =
			launchEntryResourceComponentServiceObjects;
	}

	public static void setLaunchSetResourceComponentServiceObjects(
		ComponentServiceObjects<LaunchSetResource>
			launchSetResourceComponentServiceObjects) {

		_launchSetResourceComponentServiceObjects =
			launchSetResourceComponentServiceObjects;
	}

	@GraphQLField
	public Response createLaunchSetLaunchEntriesPageExportBatch(
			@GraphQLName("launchSetId") Long launchSetId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("sort") String sortsString,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("contentType") String contentType,
			@GraphQLName("fieldNames") String fieldNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_launchEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			launchEntryResource ->
				launchEntryResource.postLaunchSetLaunchEntriesPageExportBatch(
					launchSetId, search,
					_filterBiFunction.apply(launchEntryResource, filterString),
					_sortsBiFunction.apply(launchEntryResource, sortsString),
					callbackURL, contentType, fieldNames));
	}

	@GraphQLField
	public boolean deleteLaunchSet(@GraphQLName("launchSetId") Long launchSetId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_launchSetResourceComponentServiceObjects,
			this::_populateResourceContext,
			launchSetResource -> launchSetResource.deleteLaunchSet(
				launchSetId));

		return true;
	}

	@GraphQLField
	public Response deleteLaunchSetBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_launchSetResourceComponentServiceObjects,
			this::_populateResourceContext,
			launchSetResource -> launchSetResource.deleteLaunchSetBatch(
				callbackURL, object));
	}

	@GraphQLField
	public boolean deleteLaunchSetByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_launchSetResourceComponentServiceObjects,
			this::_populateResourceContext,
			launchSetResource ->
				launchSetResource.deleteLaunchSetByExternalReferenceCode(
					externalReferenceCode));

		return true;
	}

	@GraphQLField
	public LaunchSet patchLaunchSet(
			@GraphQLName("launchSetId") Long launchSetId,
			@GraphQLName("launchSet") LaunchSet launchSet)
		throws Exception {

		return _applyComponentServiceObjects(
			_launchSetResourceComponentServiceObjects,
			this::_populateResourceContext,
			launchSetResource -> launchSetResource.patchLaunchSet(
				launchSetId, launchSet));
	}

	@GraphQLField
	public LaunchSet patchLaunchSetByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("launchSet") LaunchSet launchSet)
		throws Exception {

		return _applyComponentServiceObjects(
			_launchSetResourceComponentServiceObjects,
			this::_populateResourceContext,
			launchSetResource ->
				launchSetResource.patchLaunchSetByExternalReferenceCode(
					externalReferenceCode, launchSet));
	}

	@GraphQLField
	public Response createLaunchSetsPageExportBatch(
			@GraphQLName("search") String search,
			@GraphQLName("status") Integer[] status,
			@GraphQLName("filter") String filterString,
			@GraphQLName("sort") String sortsString,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("contentType") String contentType,
			@GraphQLName("fieldNames") String fieldNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_launchSetResourceComponentServiceObjects,
			this::_populateResourceContext,
			launchSetResource ->
				launchSetResource.postLaunchSetsPageExportBatch(
					search, status,
					_filterBiFunction.apply(launchSetResource, filterString),
					_sortsBiFunction.apply(launchSetResource, sortsString),
					callbackURL, contentType, fieldNames));
	}

	@GraphQLField
	public LaunchSet updateLaunchSet(
			@GraphQLName("launchSetId") Long launchSetId,
			@GraphQLName("launchSet") LaunchSet launchSet)
		throws Exception {

		return _applyComponentServiceObjects(
			_launchSetResourceComponentServiceObjects,
			this::_populateResourceContext,
			launchSetResource -> launchSetResource.putLaunchSet(
				launchSetId, launchSet));
	}

	@GraphQLField
	public Response updateLaunchSetBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_launchSetResourceComponentServiceObjects,
			this::_populateResourceContext,
			launchSetResource -> launchSetResource.putLaunchSetBatch(
				callbackURL, object));
	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(
			LaunchEntryResource launchEntryResource)
		throws Exception {

		launchEntryResource.setContextAcceptLanguage(_acceptLanguage);
		launchEntryResource.setContextCompany(_company);
		launchEntryResource.setContextHttpServletRequest(_httpServletRequest);
		launchEntryResource.setContextHttpServletResponse(_httpServletResponse);
		launchEntryResource.setContextUriInfo(_uriInfo);
		launchEntryResource.setContextUser(_user);
		launchEntryResource.setGroupLocalService(_groupLocalService);
		launchEntryResource.setRoleLocalService(_roleLocalService);

		launchEntryResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		launchEntryResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(LaunchSetResource launchSetResource)
		throws Exception {

		launchSetResource.setContextAcceptLanguage(_acceptLanguage);
		launchSetResource.setContextCompany(_company);
		launchSetResource.setContextHttpServletRequest(_httpServletRequest);
		launchSetResource.setContextHttpServletResponse(_httpServletResponse);
		launchSetResource.setContextUriInfo(_uriInfo);
		launchSetResource.setContextUser(_user);
		launchSetResource.setGroupLocalService(_groupLocalService);
		launchSetResource.setRoleLocalService(_roleLocalService);

		launchSetResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		launchSetResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private static ComponentServiceObjects<LaunchEntryResource>
		_launchEntryResourceComponentServiceObjects;
	private static ComponentServiceObjects<LaunchSetResource>
		_launchSetResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction
		<Object, String, com.liferay.portal.kernel.search.filter.Filter>
			_filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, com.liferay.portal.kernel.search.Sort[]>
		_sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;
	private VulcanBatchEngineExportTaskResource
		_vulcanBatchEngineExportTaskResource;
	private VulcanBatchEngineImportTaskResource
		_vulcanBatchEngineImportTaskResource;

}