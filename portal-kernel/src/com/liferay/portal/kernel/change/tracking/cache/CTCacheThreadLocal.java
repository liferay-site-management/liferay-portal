/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.change.tracking.cache;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author David Truong
 */
public class CTCacheThreadLocal {

	public static boolean isCTCacheEnabled() {
		return _ctCacheEnabled.get();
	}

	public static void setCTCacheEnabled(boolean ctCacheEnabled) {
		_ctCacheEnabled.set(ctCacheEnabled);
	}

	public static SafeCloseable setCTCacheEnabledWithSafeCloseable(
		boolean ctCacheEnabled) {

		return _ctCacheEnabled.setWithSafeCloseable(ctCacheEnabled);
	}

	private CTCacheThreadLocal() {
	}

	private static final CentralizedThreadLocal<Boolean> _ctCacheEnabled =
		new CentralizedThreadLocal<>(
			CTCacheThreadLocal.class + "._ctCacheEnabled", () -> false);

}