/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.rest.internal.graphql.query.v1_0;

import com.liferay.launch.rest.dto.v1_0.LaunchEntry;
import com.liferay.launch.rest.dto.v1_0.LaunchSet;
import com.liferay.launch.rest.resource.v1_0.LaunchEntryResource;
import com.liferay.launch.rest.resource.v1_0.LaunchSetResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import jakarta.annotation.Generated;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.ws.rs.core.UriInfo;

import java.util.Map;
import java.util.function.BiFunction;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author David Truong
 * @generated
 */
@Generated("")
public class Query {

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

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {launchEntry(ctEntryId: ___){actions, classNameId, classPK, dateCreated, dateModified, id, launchSetId}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public LaunchEntry launchEntry(@GraphQLName("ctEntryId") Long ctEntryId)
		throws Exception {

		return _applyComponentServiceObjects(
			_launchEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			launchEntryResource -> launchEntryResource.getLaunchEntry(
				ctEntryId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {launchSet(launchSetId: ___){actions, dateCreated, dateModified, dateScheduled, description, externalReferenceCode, id, name, ownerName, status, statusMessage}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public LaunchSet launchSet(@GraphQLName("launchSetId") Long launchSetId)
		throws Exception {

		return _applyComponentServiceObjects(
			_launchSetResourceComponentServiceObjects,
			this::_populateResourceContext,
			launchSetResource -> launchSetResource.getLaunchSet(launchSetId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {launchSetByExternalReferenceCode(externalReferenceCode: ___){actions, dateCreated, dateModified, dateScheduled, description, externalReferenceCode, id, name, ownerName, status, statusMessage}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public LaunchSet launchSetByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_launchSetResourceComponentServiceObjects,
			this::_populateResourceContext,
			launchSetResource ->
				launchSetResource.getLaunchSetByExternalReferenceCode(
					externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {launchSets(filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___, status: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public LaunchSetPage launchSets(
			@GraphQLName("search") String search,
			@GraphQLName("status") Integer[] status,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_launchSetResourceComponentServiceObjects,
			this::_populateResourceContext,
			launchSetResource -> new LaunchSetPage(
				launchSetResource.getLaunchSetsPage(
					search, status,
					_filterBiFunction.apply(launchSetResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(launchSetResource, sortsString))));
	}

	@GraphQLTypeExtension(LaunchEntry.class)
	public class GetLaunchSetTypeExtension {

		public GetLaunchSetTypeExtension(LaunchEntry launchEntry) {
			_launchEntry = launchEntry;
		}

		@GraphQLField
		public LaunchSet launchSet() throws Exception {
			return _applyComponentServiceObjects(
				_launchSetResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				launchSetResource -> launchSetResource.getLaunchSet(
					_launchEntry.getLaunchSetId()));
		}

		private LaunchEntry _launchEntry;

	}

	@GraphQLName("LaunchEntryPage")
	public class LaunchEntryPage {

		public LaunchEntryPage(Page launchEntryPage) {
			actions = launchEntryPage.getActions();

			items = launchEntryPage.getItems();
			lastPage = launchEntryPage.getLastPage();
			page = launchEntryPage.getPage();
			pageSize = launchEntryPage.getPageSize();
			totalCount = launchEntryPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<LaunchEntry> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("LaunchSetPage")
	public class LaunchSetPage {

		public LaunchSetPage(Page launchSetPage) {
			actions = launchSetPage.getActions();

			items = launchSetPage.getItems();
			lastPage = launchSetPage.getLastPage();
			page = launchSetPage.getPage();
			pageSize = launchSetPage.getPageSize();
			totalCount = launchSetPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<LaunchSet> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

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
		launchEntryResource.setResourceActionLocalService(
			_resourceActionLocalService);
		launchEntryResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		launchEntryResource.setRoleLocalService(_roleLocalService);
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
		launchSetResource.setResourceActionLocalService(
			_resourceActionLocalService);
		launchSetResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		launchSetResource.setRoleLocalService(_roleLocalService);
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
	private ResourceActionLocalService _resourceActionLocalService;
	private ResourcePermissionLocalService _resourcePermissionLocalService;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, com.liferay.portal.kernel.search.Sort[]>
		_sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}