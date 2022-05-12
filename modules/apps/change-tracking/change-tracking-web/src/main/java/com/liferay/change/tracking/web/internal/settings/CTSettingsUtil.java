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

import com.liferay.change.tracking.web.internal.configuration.CTSettingsConfiguration;
import com.liferay.change.tracking.web.internal.settings.CTSettings;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(immediate = true, service = {})
public class CTSettingsUtil {

	public static boolean enabled(long companyId) {
		return _ctSettings.enabled(companyId);
	}

	public static CTSettingsConfiguration getCTSettingsConfiguration(
		long companyId) {

		return _ctSettings.getCTSettingsConfiguration(companyId);
	}

	public static boolean sandboxEnabled(long companyId) {
		return _ctSettings.sandboxEnabled(companyId);
	}

	public static void save(
			long companyId, boolean enabled, boolean sandboxEnabled)
		throws PortalException {

		_ctSettings.save(companyId, enabled, sandboxEnabled);
	}

	@Reference(unbind = "-")
	public void setCTSettings(CTSettings ctSettings) {
		_ctSettings = ctSettings;
	}

	private static CTSettings _ctSettings;

}