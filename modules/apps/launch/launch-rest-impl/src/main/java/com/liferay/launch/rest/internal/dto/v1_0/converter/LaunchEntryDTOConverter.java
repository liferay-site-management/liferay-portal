/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.rest.internal.dto.v1_0.converter;

import com.liferay.launch.rest.dto.v1_0.LaunchEntry;
import com.liferay.launch.rest.dto.v1_0.Status;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.BaseModel;
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
	property = "dto.class.name=com.liferay.launch.model.LaunchEntry",
	service = DTOConverter.class
)
public class LaunchEntryDTOConverter
	implements DTOConverter<com.liferay.launch.model.LaunchEntry, LaunchEntry> {

	@Override
	public String getContentType() {
		return LaunchEntry.class.getSimpleName();
	}

	@Override
	public LaunchEntry toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.launch.model.LaunchEntry launchEntry)
		throws Exception {

		if (launchEntry == null) {
			return null;
		}

		return _toLaunchEntry(dtoConverterContext, launchEntry);
	}

	private <T extends BaseModel<T>> LaunchEntry _toLaunchEntry(
			DTOConverterContext dtoConverterContext,
			com.liferay.launch.model.LaunchEntry launchEntry)
		throws Exception {

		return new LaunchEntry() {
			{
				setActions(dtoConverterContext::getActions);
				setClassNameId(launchEntry::getClassNameId);
				setClassPK(launchEntry::getClassPK);
				setDateCreated(launchEntry::getCreateDate);
				setDateModified(launchEntry::getModifiedDate);
				setId(launchEntry::getLaunchEntryId);
				setLaunchSetId(launchEntry::getLaunchSetId);
				setStatus(
					() -> _toStatus(
						dtoConverterContext.getLocale(),
						launchEntry.getStatus()));
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