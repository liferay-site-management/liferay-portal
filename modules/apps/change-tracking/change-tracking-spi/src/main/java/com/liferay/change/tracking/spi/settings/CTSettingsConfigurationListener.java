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

package com.liferay.change.tracking.spi.settings;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Dictionary;

/**
 * @author David Truong
 */
public interface CTSettingsConfigurationListener {

	public default void onAfterDelete(long companyId) throws PortalException {
	}

	public default void onAfterSave(
			long companyId, Dictionary<String, Object> properties)
		throws PortalException {
	}

	public default void onBeforeDelete(long companyId) throws PortalException {
	}

	public default void onBeforeSave(
			long companyId, Dictionary<String, Object> properties)
		throws PortalException {
	}

}