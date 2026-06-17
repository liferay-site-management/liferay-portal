/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.integration;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.seo.studio.web.integration.SEOStudioIntegration;
import com.liferay.seo.studio.web.integration.SEOStudioIntegrationRegistry;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Kiana Suetani
 */
@Component(service = SEOStudioIntegrationRegistry.class)
public class SEOStudioIntegrationRegistryImpl
	implements SEOStudioIntegrationRegistry {

	@Override
	public SEOStudioIntegration getSEOStudioIntegration(String key) {
		return _serviceTrackerMap.getService(key);
	}

	@Override
	public Map<String, SEOStudioIntegration> getSEOStudioIntegrations() {
		Map<String, SEOStudioIntegration> seoStudioIntegrationsMap =
			new HashMap<>();

		for (String key : _serviceTrackerMap.keySet()) {
			SEOStudioIntegration seoStudioIntegration =
				_serviceTrackerMap.getService(key);

			if (seoStudioIntegration != null) {
				seoStudioIntegrationsMap.put(key, seoStudioIntegration);
			}
		}

		return Collections.unmodifiableMap(seoStudioIntegrationsMap);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, SEOStudioIntegration.class, null,
			(serviceReference, emitter) -> {
				SEOStudioIntegration seoStudioIntegration =
					bundleContext.getService(serviceReference);

				if (seoStudioIntegration == null) {
					return;
				}

				try {
					if (seoStudioIntegration.getKey() != null) {
						emitter.emit(seoStudioIntegration.getKey());
					}
				}
				finally {
					bundleContext.ungetService(serviceReference);
				}
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, SEOStudioIntegration> _serviceTrackerMap;

}