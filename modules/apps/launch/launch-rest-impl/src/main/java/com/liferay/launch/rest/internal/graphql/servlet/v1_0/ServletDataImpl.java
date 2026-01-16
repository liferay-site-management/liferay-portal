/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.rest.internal.graphql.servlet.v1_0;

import com.liferay.launch.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.launch.rest.internal.graphql.query.v1_0.Query;
import com.liferay.launch.rest.internal.resource.v1_0.LaunchEntryResourceImpl;
import com.liferay.launch.rest.internal.resource.v1_0.LaunchSetResourceImpl;
import com.liferay.launch.rest.resource.v1_0.LaunchEntryResource;
import com.liferay.launch.rest.resource.v1_0.LaunchSetResource;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import jakarta.annotation.Generated;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author David Truong
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setLaunchSetResourceComponentServiceObjects(
			_launchSetResourceComponentServiceObjects);

		Query.setLaunchEntryResourceComponentServiceObjects(
			_launchEntryResourceComponentServiceObjects);
		Query.setLaunchSetResourceComponentServiceObjects(
			_launchSetResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Launch.REST";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/launch-rest-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodObjectValuePair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodObjectValuePairs.get(
				"mutation#" + methodName);
		}

		return _resourceMethodObjectValuePairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodObjectValuePairs =
			new HashMap<String, ObjectValuePair<Class<?>, String>>() {
				{
					put(
						"mutation#deleteLaunchSet",
						new ObjectValuePair<>(
							LaunchSetResourceImpl.class, "deleteLaunchSet"));
					put(
						"mutation#deleteLaunchSetBatch",
						new ObjectValuePair<>(
							LaunchSetResourceImpl.class,
							"deleteLaunchSetBatch"));
					put(
						"mutation#deleteLaunchSetByExternalReferenceCode",
						new ObjectValuePair<>(
							LaunchSetResourceImpl.class,
							"deleteLaunchSetByExternalReferenceCode"));
					put(
						"mutation#patchLaunchSet",
						new ObjectValuePair<>(
							LaunchSetResourceImpl.class, "patchLaunchSet"));
					put(
						"mutation#patchLaunchSetByExternalReferenceCode",
						new ObjectValuePair<>(
							LaunchSetResourceImpl.class,
							"patchLaunchSetByExternalReferenceCode"));
					put(
						"mutation#createLaunchSetsPageExportBatch",
						new ObjectValuePair<>(
							LaunchSetResourceImpl.class,
							"postLaunchSetsPageExportBatch"));
					put(
						"mutation#updateLaunchSet",
						new ObjectValuePair<>(
							LaunchSetResourceImpl.class, "putLaunchSet"));
					put(
						"mutation#updateLaunchSetBatch",
						new ObjectValuePair<>(
							LaunchSetResourceImpl.class, "putLaunchSetBatch"));

					put(
						"query#launchEntry",
						new ObjectValuePair<>(
							LaunchEntryResourceImpl.class, "getLaunchEntry"));
					put(
						"query#launchSet",
						new ObjectValuePair<>(
							LaunchSetResourceImpl.class, "getLaunchSet"));
					put(
						"query#launchSetByExternalReferenceCode",
						new ObjectValuePair<>(
							LaunchSetResourceImpl.class,
							"getLaunchSetByExternalReferenceCode"));
					put(
						"query#launchSets",
						new ObjectValuePair<>(
							LaunchSetResourceImpl.class, "getLaunchSetsPage"));

					put(
						"query#LaunchEntry.launchSet",
						new ObjectValuePair<>(
							LaunchSetResourceImpl.class, "getLaunchSet"));
				}
			};

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<LaunchSetResource>
		_launchSetResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<LaunchEntryResource>
		_launchEntryResourceComponentServiceObjects;

}