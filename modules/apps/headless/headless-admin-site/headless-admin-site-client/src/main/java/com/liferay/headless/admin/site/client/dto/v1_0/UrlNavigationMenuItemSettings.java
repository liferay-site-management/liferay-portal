/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.UrlNavigationMenuItemSettingsSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class UrlNavigationMenuItemSettings implements Cloneable, Serializable {

	public static UrlNavigationMenuItemSettings toDTO(String json) {
		return UrlNavigationMenuItemSettingsSerDes.toDTO(json);
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUrl(UnsafeSupplier<String, Exception> urlUnsafeSupplier) {
		try {
			url = urlUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String url;

	public Boolean getUseNewTab() {
		return useNewTab;
	}

	public void setUseNewTab(Boolean useNewTab) {
		this.useNewTab = useNewTab;
	}

	public void setUseNewTab(
		UnsafeSupplier<Boolean, Exception> useNewTabUnsafeSupplier) {

		try {
			useNewTab = useNewTabUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean useNewTab;

	@Override
	public UrlNavigationMenuItemSettings clone()
		throws CloneNotSupportedException {

		return (UrlNavigationMenuItemSettings)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof UrlNavigationMenuItemSettings)) {
			return false;
		}

		UrlNavigationMenuItemSettings urlNavigationMenuItemSettings =
			(UrlNavigationMenuItemSettings)object;

		return Objects.equals(
			toString(), urlNavigationMenuItemSettings.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return UrlNavigationMenuItemSettingsSerDes.toJSON(this);
	}

}