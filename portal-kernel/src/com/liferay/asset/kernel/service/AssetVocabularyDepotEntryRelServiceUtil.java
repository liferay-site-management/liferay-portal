/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.service;

/**
 * Provides the remote service utility for AssetVocabularyDepotEntryRel. This utility wraps
 * <code>com.liferay.portlet.asset.service.impl.AssetVocabularyDepotEntryRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyDepotEntryRelService
 * @generated
 */
public class AssetVocabularyDepotEntryRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.asset.service.impl.AssetVocabularyDepotEntryRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static AssetVocabularyDepotEntryRelService getService() {
		return _service;
	}

	public static void setService(AssetVocabularyDepotEntryRelService service) {
		_service = service;
	}

	private static volatile AssetVocabularyDepotEntryRelService _service;

}