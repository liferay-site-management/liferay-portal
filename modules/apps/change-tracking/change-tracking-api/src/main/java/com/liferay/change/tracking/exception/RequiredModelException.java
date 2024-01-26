/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class RequiredModelException extends PortalException {

	public RequiredModelException() {
	}

	public RequiredModelException(String msg) {
		super(msg);
	}

	public RequiredModelException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public RequiredModelException(Throwable throwable) {
		super(throwable);
	}

}