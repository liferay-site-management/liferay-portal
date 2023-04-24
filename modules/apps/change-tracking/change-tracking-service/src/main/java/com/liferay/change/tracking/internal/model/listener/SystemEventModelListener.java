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

package com.liferay.change.tracking.internal.model.listener;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.SystemEvent;
import com.liferay.portal.kernel.service.SystemEventLocalService;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = ModelListener.class)
public class SystemEventModelListener extends BaseModelListener<SystemEvent> {

	@Override
	public void onAfterCreate(SystemEvent systemEvent) {
		if (systemEvent.getCtCollectionId() ==
				CTConstants.CT_COLLECTION_ID_PRODUCTION) {

			return;
		}

		CTEntry ctEntry = _ctEntryLocalService.fetchCTEntry(
			systemEvent.getCtCollectionId(), systemEvent.getClassNameId(),
			systemEvent.getClassPK());

		if (ctEntry == null) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				_ctCollectionLocalService.discardCTEntry(
					systemEvent.getCtCollectionId(),
					_portal.getClassNameId(SystemEvent.class),
					systemEvent.getSystemEventId(), false);
			}
			catch (PortalException portalException) {
				if (_log.isWarnEnabled()) {
					_log.warn(portalException);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SystemEventModelListener.class);

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SystemEventLocalService _systemEventLocalService;

}