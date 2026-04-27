/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.language.LanguageResources;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = ConstraintResolver.class)
public class DLFileEntryFileNameConstraintResolver
	implements ConstraintResolver<DLFileEntry> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-file-name";
	}

	@Override
	public Class<DLFileEntry> getModelClass() {
		return DLFileEntry.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		return "rename-the-document-in-the-publication";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return LanguageResources.getResourceBundle(locale);
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"groupId", "folderId", "fileName"};
	}

	@Override
	public void resolveConflict(
			ConstraintResolverContext<DLFileEntry> constraintResolverContext)
		throws PortalException {

		DLFileEntry sourceDLFileEntry =
			constraintResolverContext.getSourceCTModel();

		String uniqueFileName = constraintResolverContext.getInTarget(
			() -> DLUtil.getUniqueFileName(
				sourceDLFileEntry.getGroupId(), sourceDLFileEntry.getFolderId(),
				sourceDLFileEntry.getFileName(), true));

		sourceDLFileEntry.setFileName(uniqueFileName);

		_dlFileEntryLocalService.updateDLFileEntry(sourceDLFileEntry);

		DLFileVersion latestDLFileVersion;

		try (SafeCloseable safeCloseable1 =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					sourceDLFileEntry.getCtCollectionId());
			SafeCloseable safeCloseable2 =
				CTSQLModeThreadLocal.setCTSQLModeWithSafeCloseable(
					CTSQLModeThreadLocal.CTSQLMode.CT_ONLY)) {

			latestDLFileVersion =
				_dlFileVersionLocalService.fetchLatestFileVersion(
					sourceDLFileEntry.getFileEntryId(), false);
		}

		if ((latestDLFileVersion != null) &&
			constraintResolverContext.isSourceCTModel(latestDLFileVersion)) {

			latestDLFileVersion.setFileName(uniqueFileName);

			_dlFileVersionLocalService.updateDLFileVersion(latestDLFileVersion);
		}
	}

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

}