/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.SubmenuNavigationMenuItemSettingsSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class SubmenuNavigationMenuItemSettings
	implements Cloneable, Serializable {

	public static SubmenuNavigationMenuItemSettings toDTO(String json) {
		return SubmenuNavigationMenuItemSettingsSerDes.toDTO(json);
	}

	public Map<String, String> getLocalizedNames() {
		return localizedNames;
	}

	public void setLocalizedNames(Map<String, String> localizedNames) {
		this.localizedNames = localizedNames;
	}

	public void setLocalizedNames(
		UnsafeSupplier<Map<String, String>, Exception>
			localizedNamesUnsafeSupplier) {

		try {
			localizedNames = localizedNamesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> localizedNames;

	@Override
	public SubmenuNavigationMenuItemSettings clone()
		throws CloneNotSupportedException {

		return (SubmenuNavigationMenuItemSettings)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SubmenuNavigationMenuItemSettings)) {
			return false;
		}

		SubmenuNavigationMenuItemSettings submenuNavigationMenuItemSettings =
			(SubmenuNavigationMenuItemSettings)object;

		return Objects.equals(
			toString(), submenuNavigationMenuItemSettings.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SubmenuNavigationMenuItemSettingsSerDes.toJSON(this);
	}

}