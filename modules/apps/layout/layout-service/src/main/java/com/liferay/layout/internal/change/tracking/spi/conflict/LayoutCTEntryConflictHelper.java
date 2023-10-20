/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.change.tracking.spi.conflict;

import com.liferay.change.tracking.conflict.CTEntryConflictHelper;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noor Najjar
 */
@Component(service = CTEntryConflictHelper.class)
public class LayoutCTEntryConflictHelper implements CTEntryConflictHelper {

	@Override
	public Class<? extends CTModel<?>> getModelClass() {
		return Layout.class;
	}

	@Override
	public boolean hasModificationConflict(
		CTEntry ctEntry, long targetCTCollectionId) {

		Layout layout = _layoutLocalService.fetchLayout(
			ctEntry.getModelClassPK());

		if ((layout == null) || !layout.isDraftLayout()) {
			return false;
		}

		if (layout.getStatus() == WorkflowConstants.STATUS_DRAFT) {
			return true;
		}

		return false;
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

}