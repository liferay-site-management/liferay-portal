/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.util;

import com.liferay.change.tracking.web.internal.configuration.CTConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;

import java.util.Collections;
import java.util.Locale;

/**
 * @author Cheryl Tang
 */
public class PublicationUtil {

	public static CTConfiguration getCTConfiguration(long companyId)
		throws ConfigurationException {

		try {
			return ConfigurationProviderUtil.getCompanyConfiguration(
				CTConfiguration.class, companyId);
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);
		}

		return ConfigurableUtil.createConfigurable(
			CTConfiguration.class, Collections.emptyMap());
	}

	public static String getCustomProductionName(long companyId, Locale locale)
		throws ConfigurationException {

		CTConfiguration ctConfiguration = getCTConfiguration(companyId);

		LocalizedValuesMap localizedValuesMap =
			ctConfiguration.customProductionName();

		return localizedValuesMap.get(locale);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PublicationUtil.class);

}