/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.util;

import com.liferay.change.tracking.web.internal.configuration.CTConfiguration;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;

/**
 * @author Cheryl Tang
 */
public class PublicationUtil {

	public static CTConfiguration getCTConfiguration(long companyId)
		throws ConfigurationException {

		return ConfigurationProviderUtil.getCompanyConfiguration(
			CTConfiguration.class, companyId);
	}

	public static String getCustomProductionName(long companyId)
		throws ConfigurationException {

		return getCTConfiguration(
			companyId
		).customProductionName();
	}

}