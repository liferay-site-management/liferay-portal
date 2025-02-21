/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchVocabularyDepotEntryRelException
	extends NoSuchModelException {

	public NoSuchVocabularyDepotEntryRelException() {
	}

	public NoSuchVocabularyDepotEntryRelException(String msg) {
		super(msg);
	}

	public NoSuchVocabularyDepotEntryRelException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchVocabularyDepotEntryRelException(Throwable throwable) {
		super(throwable);
	}

}