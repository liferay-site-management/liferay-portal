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

package com.liferay.change.tracking.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.change.tracking.store.model.CTSContent;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gislayne Vitorino
 */
@Component(service = CTDisplayRenderer.class)
public class CTSContentCTDisplayRenderer
	extends BaseCTDisplayRenderer<CTSContent> {

	@Override
	public Class<CTSContent> getModelClass() {
		return CTSContent.class;
	}

	@Override
	public String getTitle(Locale locale, CTSContent ctsContent)
		throws PortalException {

		return StringBundler.concat(
			ctsContent.getModelClassName(), " ", ctsContent.getCtsContentId());
	}

	@Override
	public boolean isHideable(CTSContent ctsContent) {
		return true;
	}

	@Override
	protected void buildDisplay(DisplayBuilder<CTSContent> displayBuilder) {
		CTSContent ctsContent = displayBuilder.getModel();

		displayBuilder.display(
			"mvcc-version", ctsContent.getMvccVersion()
		).display(
			"ct-collection-id", ctsContent.getCtCollectionId()
		).display(
			"cts-content-id", ctsContent.getCtsContentId()
		).display(
			"company-id", ctsContent.getCompanyId()
		).display(
			"repository-id", ctsContent.getRepositoryId()
		).display(
			"path", ctsContent.getPath()
		).display(
			"version", ctsContent.getVersion()
		).display(
			"data", ctsContent.getData()
		).display(
			"size", ctsContent.getSize()
		).display(
			"store-type", ctsContent.getStoreType()
		);
	}

}