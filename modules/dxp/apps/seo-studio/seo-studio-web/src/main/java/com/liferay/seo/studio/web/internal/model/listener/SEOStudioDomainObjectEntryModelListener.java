/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.model.listener;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.listener.RelevantObjectEntryModelListener;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noor Najjar
 */
@Component(service = RelevantObjectEntryModelListener.class)
public class SEOStudioDomainObjectEntryModelListener
	extends BaseModelListener<ObjectEntry>
	implements RelevantObjectEntryModelListener {

	@Override
	public String getObjectDefinitionExternalReferenceCode() {
		return "L_SEO_STUDIO_DOMAIN";
	}

	@Override
	public void onAfterCreate(ObjectEntry domainObjectEntry)
		throws ModelListenerException {

		try {
			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					domainObjectEntry.getCompanyId(),
					"SEOStudioAIBotConfiguration");

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			for (String agentName : _DEFAULT_AGENT_NAMES) {
				_objectEntryLocalService.addObjectEntry(
					domainObjectEntry.getGroupId(),
					domainObjectEntry.getUserId(),
					objectDefinition.getObjectDefinitionId(), 0, null,
					HashMapBuilder.<String, Serializable>put(
						"agentName", agentName
					).put(
						"enabled", true
					).put(
						"r_seoStudioDomainToSEOStudioAIBotConfigs_" +
							"seoStudioDomainId",
						domainObjectEntry.getObjectEntryId()
					).build(),
					serviceContext);
			}
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	private static final String[] _DEFAULT_AGENT_NAMES = {
		"Applebot-Extended", "Bytespider", "CCBot", "ChatGPT-User",
		"Claude-Web", "ClaudeBot", "GPTBot", "Google-Extended", "OAI-SearchBot",
		"PerplexityBot"
	};

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}