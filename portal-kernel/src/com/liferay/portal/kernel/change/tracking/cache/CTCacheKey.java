/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.change.tracking.cache;

import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

/**
 * @author David Truong
 */
public class CTCacheKey implements Serializable {

	public CTCacheKey(
		String className, Long ctCollectionId, Serializable primaryKey) {

		_className = className;
		_ctCollectionId = ctCollectionId;
		_primaryKey = primaryKey;
	}

	@Override
	public boolean equals(Object object) {
		CTCacheKey ctCacheKey = (CTCacheKey)object;

		if (ctCacheKey._className.equals(_className) &&
			ctCacheKey._ctCollectionId.equals(_ctCollectionId) &&
			ctCacheKey._primaryKey.equals(_primaryKey)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return (_className.hashCode() * 11) + _ctCollectionId.hashCode() +
			_primaryKey.hashCode();
	}

	public String toString() {
		return StringBundler.concat(
			_className, "_", String.valueOf(_ctCollectionId), "_",
			String.valueOf(_primaryKey));
	}

	private static final long serialVersionUID = 1L;

	private final String _className;
	private final Long _ctCollectionId;
	private final Serializable _primaryKey;

}