/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.configuration.helper;

import com.liferay.change.tracking.configuration.CTConflictConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(
	configurationPid = "com.liferay.change.tracking.configuration.CTConflictConfiguration",
	service = CTConflictConfigurationHelper.class
)
public class CTConflictConfigurationHelper {

	public CTConflictConfiguration getCTConflictConfiguration(long companyId) {
		return _getCTConflictConfiguration(companyId);
	}

	public boolean isEnabled(long companyId) {
		CTConflictConfiguration ctConflictConfiguration =
			_getCTConflictConfiguration(companyId);

		return ctConflictConfiguration.outOfDateAllowed();
	}

	public void save(long companyId, Map<String, Object> properties)
		throws PortalException {

		CTConflictConfiguration ctConflictConfiguration =
			_getCTConflictConfiguration(companyId);

		properties.putIfAbsent(
			"outOfDateAllowed", ctConflictConfiguration.outOfDateAllowed());

		_configurationProvider.saveCompanyConfiguration(
			CTConflictConfiguration.class, companyId,
			HashMapDictionaryBuilder.putAll(
				properties
			).build());
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_defaultCTConflictConfiguration = ConfigurableUtil.createConfigurable(
			CTConflictConfiguration.class, properties);
	}

	private CTConflictConfiguration _getCTConflictConfiguration(
		long companyId) {

		try {
			return _configurationProvider.getCompanyConfiguration(
				CTConflictConfiguration.class, companyId);
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);
		}

		return _defaultCTConflictConfiguration;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTConflictConfigurationHelper.class.getName());

	@Reference
	private ConfigurationProvider _configurationProvider;

	private volatile CTConflictConfiguration _defaultCTConflictConfiguration;

}