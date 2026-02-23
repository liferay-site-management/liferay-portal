/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.product.navigation.control.menu;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.product.navigation.control.menu.BaseInfoMessageProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.InfoMessageProductNavigationControlMenuEntryTypeConstants;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.TOOLS,
		"product.navigation.control.menu.entry.order:Integer=250"
	},
	service = ProductNavigationControlMenuEntry.class
)
public class MaintenanceInfoMessageProductNavigationControlMenuEntry
	extends BaseInfoMessageProductNavigationControlMenuEntry {

	@Override
	protected String getPortletName() {
		return CTPortletKeys.PUBLICATIONS;
	}

	@Override
	protected String getType() {
		return InfoMessageProductNavigationControlMenuEntryTypeConstants.
			MAINTENANCE;
	}

}