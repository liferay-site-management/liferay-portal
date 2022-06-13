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

package com.liferay.change.tracking.internal.settings;

import com.liferay.change.tracking.configuration.CTSettingsConfiguration;
import com.liferay.change.tracking.settings.CTSettings;
import com.liferay.change.tracking.spi.settings.CTSettingsConfigurationListener;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Collections;
import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(immediate = true, service = AopService.class)
public class CTSettingsImpl implements AopService, CTSettings {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(long companyId) throws PortalException {
		_onBeforeDelete(companyId);

		_configurationProvider.deleteCompanyConfiguration(
			CTSettingsConfiguration.class, companyId);

		_onAfterDelete(companyId);
	}

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

		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"enabled", enabled
			).put(
				"sandboxEnabled", sandboxEnabled
			).build();

		_onBeforeSave(companyId, properties);

		_configurationProvider.saveCompanyConfiguration(
			CTSettingsConfiguration.class, companyId, properties);

		_onAfterSave(companyId, properties);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_ctSettingsConfigurationListenerServiceTracker =
			ServiceTrackerListFactory.open(
				bundleContext, CTSettingsConfigurationListener.class);
	}

	@Deactivate
	protected void deactivate() {
		_ctSettingsConfigurationListenerServiceTracker.close();
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

	private void _onAfterDelete(long companyId) throws PortalException {
		for (CTSettingsConfigurationListener ctSettingsConfigurationListener :
				_ctSettingsConfigurationListenerServiceTracker) {

			ctSettingsConfigurationListener.onAfterDelete(companyId);
		}
	}

	private void _onAfterSave(
			long companyId, Dictionary<String, Object> properties)
		throws PortalException {

		for (CTSettingsConfigurationListener ctSettingsConfigurationListener :
				_ctSettingsConfigurationListenerServiceTracker) {

			ctSettingsConfigurationListener.onAfterSave(companyId, properties);
		}
	}

	private void _onBeforeDelete(long companyId) throws PortalException {
		for (CTSettingsConfigurationListener ctSettingsConfigurationLister :
				_ctSettingsConfigurationListenerServiceTracker) {

			ctSettingsConfigurationLister.onBeforeDelete(companyId);
		}
	}

	private void _onBeforeSave(
			long companyId, Dictionary<String, Object> properties)
		throws PortalException {

		for (CTSettingsConfigurationListener ctSettingsConfigurationLister :
				_ctSettingsConfigurationListenerServiceTracker) {

			ctSettingsConfigurationLister.onBeforeSave(companyId, properties);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTSettingsImpl.class.getName());

	@Reference
	private ConfigurationProvider _configurationProvider;

	private ServiceTrackerList<CTSettingsConfigurationListener>
		_ctSettingsConfigurationListenerServiceTracker;

}