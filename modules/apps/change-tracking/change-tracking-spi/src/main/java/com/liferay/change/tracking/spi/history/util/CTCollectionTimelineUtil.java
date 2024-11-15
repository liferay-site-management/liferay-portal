/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.spi.history.util;

import com.liferay.change.tracking.spi.constants.CTTimelineKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cheryl Tang
 */
public class CTCollectionTimelineUtil {

	public static void setClassName(Class<?> clazz) {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		HttpServletRequest httpServletRequest = serviceContext.getRequest();

		httpServletRequest.setAttribute(
			CTTimelineKeys.CLASS_NAME, clazz.getName());
	}

	public static void setCTTimelineKeys(Class<?> clazz, long classPK) {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		HttpServletRequest httpServletRequest = serviceContext.getRequest();

		setClassName(clazz);

		httpServletRequest.setAttribute(CTTimelineKeys.CLASS_PK, classPK);
	}

}