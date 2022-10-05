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

package com.liferay.change.tracking.taglib.internal.display.context;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author David Truong
 */
public class TimelineDisplayContext {

	public TimelineDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		String className, long classPK) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_classPK = classPK;

		_classNameId = PortalUtil.getClassNameId(className);
	}

	public List<CTCollection> getCTCollections() throws PortalException {
		return CTCollectionLocalServiceUtil.getExclusivePublishedCTCollections(
			_classNameId, _classPK);
	}

	private final long _classNameId;
	private final long _classPK;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}