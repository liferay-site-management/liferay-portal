/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.rest.internal.resource.v1_0;

import com.liferay.seo.studio.rest.resource.v1_0.AIRequestResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Noor Najjar
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/ai-request.properties",
	scope = ServiceScope.PROTOTYPE, service = AIRequestResource.class
)
public class AIRequestResourceImpl extends BaseAIRequestResourceImpl {
}

// LIFERAY-REST-BUILDER-HASH:-1307856981