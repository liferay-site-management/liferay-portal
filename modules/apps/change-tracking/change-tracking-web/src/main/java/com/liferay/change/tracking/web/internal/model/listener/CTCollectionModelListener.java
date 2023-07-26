/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.model.listener;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTRemote;
import com.liferay.change.tracking.rest.client.resource.v1_0.CTCollectionResource;
import com.liferay.change.tracking.service.CTRemoteLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = ModelListener.class)
public class CTCollectionModelListener extends BaseModelListener<CTCollection> {

	@Override
	public void onAfterCreate(CTCollection ctCollection)
		throws ModelListenerException {

		if (ctCollection.getCtRemoteId() == 0) {
			return;
		}

		CTRemote ctRemote = _ctRemoteLocalService.fetchCTRemote(
			ctCollection.getCtRemoteId());

		CTCollectionResource.Builder builder = CTCollectionResource.builder();

		CTCollectionResource ctCollectionResource = builder.endpoint(
			ctRemote.getUrl(), "http"
		).authentication(
			"test@liferay.com", "test"
		).build();

		try {
			ctCollectionResource.postCTCollection(
				new com.liferay.change.tracking.rest.client.dto.v1_0.
					CTCollection() {

					{
						description = ctCollection.getDescription();
						externalReferenceCode =
							ctCollection.getExternalReferenceCode();
						name = ctCollection.getName();
					}
				});
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Override
	public void onAfterRemove(CTCollection ctCollection)
		throws ModelListenerException {

		if (ctCollection.getCtRemoteId() == 0) {
			return;
		}

		CTRemote ctRemote = _ctRemoteLocalService.fetchCTRemote(
			ctCollection.getCtRemoteId());

		CTCollectionResource.Builder builder = CTCollectionResource.builder();

		CTCollectionResource ctCollectionResource = builder.endpoint(
			ctRemote.getUrl(), "http"
		).authentication(
			"test@liferay.com", "test"
		).build();

		try {
			ctCollectionResource.deleteCTCollectionByExternalReferenceCode(
				ctCollection.getExternalReferenceCode());
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Override
	public void onAfterUpdate(
			CTCollection oldCTCollection, CTCollection ctCollection)
		throws ModelListenerException {

		if (ctCollection.getCtRemoteId() == 0) {
			return;
		}

		CTRemote ctRemote = _ctRemoteLocalService.fetchCTRemote(
			ctCollection.getCtRemoteId());

		CTCollectionResource.Builder builder = CTCollectionResource.builder();

		CTCollectionResource ctCollectionResource = builder.endpoint(
			ctRemote.getUrl(), "http"
		).authentication(
			"test@liferay.com", "test"
		).build();

		try {
			ctCollectionResource.patchCTCollectionByExternalReferenceCode(
				ctCollection.getExternalReferenceCode(),
				new com.liferay.change.tracking.rest.client.dto.v1_0.
					CTCollection() {

					{
						description = ctCollection.getDescription();
						externalReferenceCode =
							ctCollection.getExternalReferenceCode();
						name = ctCollection.getName();
					}
				});
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Reference
	private CTRemoteLocalService _ctRemoteLocalService;

}