/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import jakarta.annotation.Generated;

import jakarta.validation.Valid;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
@GraphQLName(
	description = "Type-specific settings for a \"URL\" navigation menu item type.",
	value = "UrlNavigationMenuItemSettings"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "UrlNavigationMenuItemSettings")
public class UrlNavigationMenuItemSettings implements Serializable {

	public static UrlNavigationMenuItemSettings toDTO(String json) {
		return ObjectMapperUtil.readValue(
			UrlNavigationMenuItemSettings.class, json);
	}

	public static UrlNavigationMenuItemSettings unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			UrlNavigationMenuItemSettings.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The localized names of the entity that this navigation menu item is set to."
	)
	@Valid
	public Map<String, String> getLocalizedNames() {
		if (_localizedNamesSupplier != null) {
			localizedNames = _localizedNamesSupplier.get();

			_localizedNamesSupplier = null;
		}

		return localizedNames;
	}

	public void setLocalizedNames(Map<String, String> localizedNames) {
		this.localizedNames = localizedNames;

		_localizedNamesSupplier = null;
	}

	@JsonIgnore
	public void setLocalizedNames(
		UnsafeSupplier<Map<String, String>, Exception>
			localizedNamesUnsafeSupplier) {

		_localizedNamesSupplier = () -> {
			try {
				return localizedNamesUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "The localized names of the entity that this navigation menu item is set to."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Map<String, String> localizedNames;

	@JsonIgnore
	private Supplier<Map<String, String>> _localizedNamesSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The URL that this navigation menu item will take the user to."
	)
	public String getUrl() {
		if (_urlSupplier != null) {
			url = _urlSupplier.get();

			_urlSupplier = null;
		}

		return url;
	}

	public void setUrl(String url) {
		this.url = url;

		_urlSupplier = null;
	}

	@JsonIgnore
	public void setUrl(UnsafeSupplier<String, Exception> urlUnsafeSupplier) {
		_urlSupplier = () -> {
			try {
				return urlUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "The URL that this navigation menu item will take the user to."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String url;

	@JsonIgnore
	private Supplier<String> _urlSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Whether the target URL will open in a new tab instead of the same tab."
	)
	public Boolean getUseNewTab() {
		if (_useNewTabSupplier != null) {
			useNewTab = _useNewTabSupplier.get();

			_useNewTabSupplier = null;
		}

		return useNewTab;
	}

	public void setUseNewTab(Boolean useNewTab) {
		this.useNewTab = useNewTab;

		_useNewTabSupplier = null;
	}

	@JsonIgnore
	public void setUseNewTab(
		UnsafeSupplier<Boolean, Exception> useNewTabUnsafeSupplier) {

		_useNewTabSupplier = () -> {
			try {
				return useNewTabUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "Whether the target URL will open in a new tab instead of the same tab."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean useNewTab;

	@JsonIgnore
	private Supplier<Boolean> _useNewTabSupplier;

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
		StringBundler sb = new StringBundler();

		sb.append("{");

		Map<String, String> localizedNames = getLocalizedNames();

		if (localizedNames != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"localizedNames\": ");

			sb.append(_toJSON(localizedNames));
		}

		String url = getUrl();

		if (url != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"url\": ");

			sb.append("\"");

			sb.append(_escape(url));

			sb.append("\"");
		}

		Boolean useNewTab = getUseNewTab();

		if (useNewTab != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"useNewTab\": ");

			sb.append(useNewTab);
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.admin.site.dto.v1_0.UrlNavigationMenuItemSettings",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof Map) {
						sb.append(_toJSON((Map<String, ?>)valueArray[i]));
					}
					else if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

	private Map<String, Serializable> _extendedProperties;

}