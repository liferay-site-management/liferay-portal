package com.liferay.change.tracking.spi.settings;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Dictionary;

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