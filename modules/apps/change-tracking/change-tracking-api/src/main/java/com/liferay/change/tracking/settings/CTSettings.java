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

package com.liferay.change.tracking.settings;

import com.liferay.change.tracking.configuration.CTSettingsConfiguration;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author David Truong
 */
public interface CTSettings {

	public void delete(long companyId) throws PortalException;

	public boolean enabled(long companyId);

	public CTSettingsConfiguration getCTSettingsConfiguration(long companyId);

	public boolean sandboxEnabled(long companyId);

	public void save(long companyId, boolean enabled, boolean sandboxEnabled)
		throws PortalException;

}