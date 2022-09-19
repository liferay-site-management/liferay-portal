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

package com.liferay.change.tracking.taglib.servlet.taglib;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Noor Najjar
 */
public class CTTimelineTag extends IncludeTag {

	public List<CTCollection> getCtCollections() {
		return _ctCollections;
	}

	public long getModelClassNameId() {
		return _modelClassNameId;
	}

	public long getModelClassPK() {
		return _modelClassPK;
	}

	public void setCtCollections(List<CTCollection> ctCollections) {
		_ctCollections = ctCollections;
	}

	public void setModelClassNameId(long modelClassNameId) {
		_modelClassNameId = modelClassNameId;
	}

	public void setModelClassPK(long modelClassPK) {
		_modelClassPK = modelClassPK;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"change-tracking:timeline:modelClassNameId",
			String.valueOf(_modelClassNameId));
		httpServletRequest.setAttribute(
			"change-tracking:timeline:modelClassPK",
			String.valueOf(_modelClassPK));
	}

	private static final String _PAGE = "/timeline/page.jsp";

	private List<CTCollection> _ctCollections;
	private long _modelClassNameId;
	private long _modelClassPK;

}