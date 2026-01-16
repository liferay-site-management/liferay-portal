/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.rest.client.serdes.v1_0;

import com.liferay.launch.rest.client.dto.v1_0.LaunchSet;
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
public class LaunchSetSerDes {

	public static LaunchSet toDTO(String json) {
		LaunchSetJSONParser launchSetJSONParser = new LaunchSetJSONParser();

		return launchSetJSONParser.parseToDTO(json);
	}

	public static LaunchSet[] toDTOs(String json) {
		LaunchSetJSONParser launchSetJSONParser = new LaunchSetJSONParser();

		return launchSetJSONParser.parseToDTOs(json);
	}

	public static String toJSON(LaunchSet launchSet) {
		if (launchSet == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (launchSet.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(launchSet.getActions()));
		}

		if (launchSet.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(launchSet.getDateCreated()));

			sb.append("\"");
		}

		if (launchSet.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(launchSet.getDateModified()));

			sb.append("\"");
		}

		if (launchSet.getDateScheduled() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateScheduled\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(launchSet.getDateScheduled()));

			sb.append("\"");
		}

		if (launchSet.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(launchSet.getDescription()));

			sb.append("\"");
		}

		if (launchSet.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(launchSet.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (launchSet.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(launchSet.getId());
		}

		if (launchSet.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(launchSet.getName()));

			sb.append("\"");
		}

		if (launchSet.getOwnerName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"ownerName\": ");

			sb.append("\"");

			sb.append(_escape(launchSet.getOwnerName()));

			sb.append("\"");
		}

		if (launchSet.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append(String.valueOf(launchSet.getStatus()));
		}

		if (launchSet.getStatusMessage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"statusMessage\": ");

			sb.append("\"");

			sb.append(_escape(launchSet.getStatusMessage()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		LaunchSetJSONParser launchSetJSONParser = new LaunchSetJSONParser();

		return launchSetJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(LaunchSet launchSet) {
		if (launchSet == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (launchSet.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(launchSet.getActions()));
		}

		if (launchSet.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(launchSet.getDateCreated()));
		}

		if (launchSet.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(launchSet.getDateModified()));
		}

		if (launchSet.getDateScheduled() == null) {
			map.put("dateScheduled", null);
		}
		else {
			map.put(
				"dateScheduled",
				liferayToJSONDateFormat.format(launchSet.getDateScheduled()));
		}

		if (launchSet.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put("description", String.valueOf(launchSet.getDescription()));
		}

		if (launchSet.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(launchSet.getExternalReferenceCode()));
		}

		if (launchSet.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(launchSet.getId()));
		}

		if (launchSet.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(launchSet.getName()));
		}

		if (launchSet.getOwnerName() == null) {
			map.put("ownerName", null);
		}
		else {
			map.put("ownerName", String.valueOf(launchSet.getOwnerName()));
		}

		if (launchSet.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(launchSet.getStatus()));
		}

		if (launchSet.getStatusMessage() == null) {
			map.put("statusMessage", null);
		}
		else {
			map.put(
				"statusMessage", String.valueOf(launchSet.getStatusMessage()));
		}

		return map;
	}

	public static class LaunchSetJSONParser extends BaseJSONParser<LaunchSet> {

		@Override
		protected LaunchSet createDTO() {
			return new LaunchSet();
		}

		@Override
		protected LaunchSet[] createDTOArray(int size) {
			return new LaunchSet[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "actions")) {
				return true;
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "dateScheduled")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "ownerName")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "statusMessage")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			LaunchSet launchSet, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					launchSet.setActions(
						(Map<String, Map<String, String>>)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					launchSet.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					launchSet.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateScheduled")) {
				if (jsonParserFieldValue != null) {
					launchSet.setDateScheduled(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					launchSet.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					launchSet.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					launchSet.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					launchSet.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "ownerName")) {
				if (jsonParserFieldValue != null) {
					launchSet.setOwnerName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					launchSet.setStatus(
						StatusSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "statusMessage")) {
				if (jsonParserFieldValue != null) {
					launchSet.setStatusMessage((String)jsonParserFieldValue);
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