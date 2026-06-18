/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.model;

import org.json.JSONObject;

/**
 * @author Kiana Suetani
 */
public class Domain {

	public Domain(JSONObject jsonObject) {
		_hostname = jsonObject.optString("domainHostname", null);

		JSONObject seoStudioInstanceJSONObject = jsonObject.optJSONObject(
			"seoStudioInstance");

		if (seoStudioInstanceJSONObject != null) {
			_pageSpeedAPIKey = seoStudioInstanceJSONObject.optString(
				"googlePageSpeedAPIKey", null);
		}
		else {
			_pageSpeedAPIKey = null;
		}
	}

	public String getHostname() {
		return _hostname;
	}

	public String getPageSpeedAPIKey() {
		return _pageSpeedAPIKey;
	}

	private final String _hostname;
	private final String _pageSpeedAPIKey;

}