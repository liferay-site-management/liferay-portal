/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.change.tracking.web.internal.settings;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.exception.CTStagingEnabledException;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.model.CTPreferencesTable;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.web.internal.configuration.CTSettingsConfiguration;
import com.liferay.change.tracking.web.internal.scheduler.PublishScheduler;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author David Truong
 */
@Component(immediate = true, service = AopService.class)
public class CTSettingsImpl implements AopService, CTSettings {

	@Override
	public boolean enabled(long companyId) {
		CTSettingsConfiguration configuration = _getConfiguration(companyId);

		return configuration.enabled();
	}

	@Override
	public CTSettingsConfiguration getCTSettingsConfiguration(long companyId) {
		return _getConfiguration(companyId);
	}

	@Override
	public boolean sandboxEnabled(long companyId) {
		CTSettingsConfiguration configuration = _getConfiguration(companyId);

		return configuration.sandboxEnabled();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(long companyId, boolean enabled, boolean sandboxEnabled)
		throws PortalException {

		_checkRequirements(companyId, enabled, sandboxEnabled);

		_configurationProvider.saveCompanyConfiguration(
			CTSettingsConfiguration.class, companyId,
			HashMapDictionaryBuilder.<String, Object>put(
				"enabled", enabled
			).put(
				"sandboxEnabled", sandboxEnabled
			).build());

		_cleanUp(companyId, enabled);
	}

	private void _checkRequirements(
			long companyId, boolean enabled, boolean sandboxEnabled)
		throws PortalException {

		_portletPermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			CTPortletKeys.PUBLICATIONS, ActionKeys.CONFIGURATION);

		if (!enabled && sandboxEnabled) {
			throw new PortalException(
				"Sandbox can not be enabled without Publications");
		}

		if (enabled && _checkStagingEnabled(companyId)) {
			throw new CTStagingEnabledException();
		}
	}

	private boolean _checkStagingEnabled(long companyId) {
		for (Group group :
				_groupLocalService.<List<Group>>dslQuery(
					DSLQueryFactoryUtil.select(
						GroupTable.INSTANCE
					).from(
						GroupTable.INSTANCE
					).where(
						GroupTable.INSTANCE.companyId.eq(
							companyId
						).and(
							GroupTable.INSTANCE.liveGroupId.neq(
								GroupConstants.DEFAULT_LIVE_GROUP_ID
							).or(
								GroupTable.INSTANCE.typeSettings.like(
									"%staged=true%")
							).withParentheses()
						)
					))) {

			if (group.isStaged() || group.isStagingGroup()) {
				return true;
			}
		}

		return false;
	}

	private void _cleanCTPreferences(long companyId) {
		for (CTPreferences ctPreferences :
				_ctPreferencesLocalService.<List<CTPreferences>>dslQuery(
					DSLQueryFactoryUtil.select(
						CTPreferencesTable.INSTANCE
					).from(
						CTPreferencesTable.INSTANCE
					).where(
						CTPreferencesTable.INSTANCE.companyId.eq(companyId)
					))) {

			_ctPreferencesLocalService.deleteCTPreferences(ctPreferences);
		}
	}

	private void _cleanUp(long companyId, boolean enabled)
		throws PortalException {

		if (!enabled) {
			_cleanCTPreferences(companyId);

			_cleanUpScheduledPublications(companyId);
		}
	}

	private void _cleanUpScheduledPublications(long companyId)
		throws PortalException {

		if (PropsValues.SCHEDULER_ENABLED) {
			List<CTCollection> ctCollections =
				_ctCollectionLocalService.getCTCollections(
					companyId, WorkflowConstants.STATUS_SCHEDULED,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			for (CTCollection ctCollection : ctCollections) {
				_publishScheduler.unschedulePublish(
					ctCollection.getCtCollectionId());
			}
		}
	}

	private CTSettingsConfiguration _getConfiguration(long companyId) {
		CTSettingsConfiguration configuration =
			ConfigurableUtil.createConfigurable(
				CTSettingsConfiguration.class, Collections.emptyMap());

		try {
			configuration = _configurationProvider.getCompanyConfiguration(
				CTSettingsConfiguration.class, companyId);
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);
		}

		return configuration;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTSettingsImpl.class.getName());

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private PortletPermission _portletPermission;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile PublishScheduler _publishScheduler;

}