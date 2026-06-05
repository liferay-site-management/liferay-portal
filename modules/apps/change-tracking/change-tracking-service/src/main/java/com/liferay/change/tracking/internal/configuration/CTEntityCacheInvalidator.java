/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.configuration;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Dave Truong
 */
@Component(
	configurationPid = "com.liferay.change.tracking.internal.configuration.CTEntityCacheConfiguration",
	service = CTEntityCacheInvalidator.class
)
public class CTEntityCacheInvalidator {

	public void clearCache(
		CTPersistence<?> ctPersistence, Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return;
		}

		if ((_classPKThreshold > 0) &&
			(primaryKeys.size() > _classPKThreshold)) {

			if (_log.isDebugEnabled()) {
				Class<?> modelClass = ctPersistence.getModelClass();

				_log.debug(
					StringBundler.concat(
						"Clearing the entity cache for ", modelClass.getName(),
						" because ", primaryKeys.size(),
						" primary keys exceed the threshold of ",
						_classPKThreshold));
			}

			ctPersistence.clearCache();
		}
		else {
			ctPersistence.clearCache(primaryKeys);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		CTEntityCacheConfiguration ctEntityCacheConfiguration =
			ConfigurableUtil.createConfigurable(
				CTEntityCacheConfiguration.class, properties);

		_classPKThreshold =
			ctEntityCacheConfiguration.entityCacheClassPKThreshold();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTEntityCacheInvalidator.class);

	private volatile int _classPKThreshold;

}