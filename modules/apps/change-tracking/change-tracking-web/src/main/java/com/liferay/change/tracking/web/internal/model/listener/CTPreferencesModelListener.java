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

package com.liferay.change.tracking.web.internal.model.listener;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.web.internal.settings.CTSettings;
import com.liferay.change.tracking.web.internal.util.SandboxUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(immediate = true, service = ModelListener.class)
public class CTPreferencesModelListener
	extends BaseModelListener<CTPreferences> {

	@Reference
	private CTSettings _ctSettings;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Override
	public void onAfterUpdate(
			CTPreferences originalCTPreferences, CTPreferences ctPreferences)
		throws ModelListenerException {

		if ((ctPreferences.getCtCollectionId() !=
				CTConstants.CT_COLLECTION_ID_PRODUCTION) ||
			!_ctSettings.sandboxEnabled(ctPreferences.getCompanyId())) {

			return;
		}

		if (ctPreferences.getPreviousCtCollectionId() !=
				CTConstants.CT_COLLECTION_ID_PRODUCTION) {

			ctPreferences.setCtCollectionId(
				ctPreferences.getPreviousCtCollectionId());
		}
		else {
			try {
				CTCollection sandboxCTCollection =
					SandboxUtil.addSandboxCTCollection(
						ctPreferences.getUserId());

				ctPreferences.setCtCollectionId(
					sandboxCTCollection.getCtCollectionId());
			}
			catch (PortalException portalException) {
				new ModelListenerException(portalException);
			}
		}

		ctPreferences.setPreviousCtCollectionId(
			CTConstants.CT_COLLECTION_ID_PRODUCTION);

		_ctPreferencesLocalService.updateCTPreferences(ctPreferences);
	}

}