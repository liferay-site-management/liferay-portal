/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.rest.internal.dto.v1_0.converter;

import com.liferay.launch.rest.dto.v1_0.LaunchSet;
import com.liferay.launch.rest.dto.v1_0.Status;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(
	property = "dto.class.name=com.liferay.launch.model.LaunchSet",
	service = DTOConverter.class
)
public class LaunchSetDTOConverter
	implements DTOConverter<com.liferay.launch.model.LaunchSet, LaunchSet> {

	@Override
	public String getContentType() {
		return LaunchSet.class.getSimpleName();
	}

	@Override
	public LaunchSet toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.launch.model.LaunchSet launchSet)
		throws Exception {

		if (launchSet == null) {
			return null;
		}

		return new LaunchSet() {
			{
				setActions(dtoConverterContext::getActions);
				setDateCreated(launchSet::getCreateDate);
				setDateModified(launchSet::getModifiedDate);
				setDescription(launchSet::getDescription);
				setExternalReferenceCode(launchSet::getExternalReferenceCode);
				setId(launchSet::getLaunchSetId);
				setName(launchSet::getName);
				setStatus(
					() -> _toStatus(
						dtoConverterContext.getLocale(),
						launchSet.getStatus()));
			}
		};
	}

	private Status _toStatus(Locale locale, int status) throws Exception {
		return new Status() {
			{
				setCode(() -> status);
				setLabel(() -> WorkflowConstants.getStatusLabel(status));
				setLabel_i18n(
					() -> _language.get(
						locale, WorkflowConstants.getStatusLabel(status)));
			}
		};
	}

	@Reference
	private Language _language;

}