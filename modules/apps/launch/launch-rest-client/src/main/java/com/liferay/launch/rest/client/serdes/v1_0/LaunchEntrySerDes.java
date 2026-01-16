/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.rest.client.serdes.v1_0;

import com.liferay.launch.rest.client.dto.v1_0.LaunchEntry;
import com.liferay.launch.rest.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author David Truong
 * @generated
 */
@Generated("")
public class LaunchEntrySerDes {

	public static LaunchEntry toDTO(String json) {
		LaunchEntryJSONParser launchEntryJSONParser =
			new LaunchEntryJSONParser();

		return launchEntryJSONParser.parseToDTO(json);
	}

	public static LaunchEntry[] toDTOs(String json) {
		LaunchEntryJSONParser launchEntryJSONParser =
			new LaunchEntryJSONParser();

		return launchEntryJSONParser.parseToDTOs(json);
	}

	public static String toJSON(LaunchEntry launchEntry) {
		if (launchEntry == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (launchEntry.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(launchEntry.getActions()));
		}

		if (launchEntry.getClassNameId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"classNameId\": ");

			sb.append(launchEntry.getClassNameId());
		}

		if (launchEntry.getClassPK() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"classPK\": ");

			sb.append(launchEntry.getClassPK());
		}

		if (launchEntry.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(launchEntry.getDateCreated()));

			sb.append("\"");
		}

		if (launchEntry.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(launchEntry.getDateModified()));

			sb.append("\"");
		}

		if (launchEntry.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(launchEntry.getId());
		}

		if (launchEntry.getLaunchSetId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"launchSetId\": ");

			sb.append(launchEntry.getLaunchSetId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		LaunchEntryJSONParser launchEntryJSONParser =
			new LaunchEntryJSONParser();

		return launchEntryJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(LaunchEntry launchEntry) {
		if (launchEntry == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (launchEntry.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(launchEntry.getActions()));
		}

		if (launchEntry.getClassNameId() == null) {
			map.put("classNameId", null);
		}
		else {
			map.put(
				"classNameId", String.valueOf(launchEntry.getClassNameId()));
		}

		if (launchEntry.getClassPK() == null) {
			map.put("classPK", null);
		}
		else {
			map.put("classPK", String.valueOf(launchEntry.getClassPK()));
		}

		if (launchEntry.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(launchEntry.getDateCreated()));
		}

		if (launchEntry.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(launchEntry.getDateModified()));
		}

		if (launchEntry.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(launchEntry.getId()));
		}

		if (launchEntry.getLaunchSetId() == null) {
			map.put("launchSetId", null);
		}
		else {
			map.put(
				"launchSetId", String.valueOf(launchEntry.getLaunchSetId()));
		}

		return map;
	}

	public static class LaunchEntryJSONParser
		extends BaseJSONParser<LaunchEntry> {

		@Override
		protected LaunchEntry createDTO() {
			return new LaunchEntry();
		}

		@Override
		protected LaunchEntry[] createDTOArray(int size) {
			return new LaunchEntry[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "actions")) {
				return true;
			}
			else if (Objects.equals(jsonParserFieldName, "classNameId")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "classPK")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "launchSetId")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			LaunchEntry launchEntry, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					launchEntry.setActions(
						(Map<String, Map<String, String>>)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "classNameId")) {
				if (jsonParserFieldValue != null) {
					launchEntry.setClassNameId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "classPK")) {
				if (jsonParserFieldValue != null) {
					launchEntry.setClassPK(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					launchEntry.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					launchEntry.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					launchEntry.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "launchSetId")) {
				if (jsonParserFieldValue != null) {
					launchEntry.setLaunchSetId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
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
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value == null) {
			return "null";
		}

		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}