/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.rest.client.serdes.v1_0;

import com.liferay.seo.studio.rest.client.dto.v1_0.AIRequest;
import com.liferay.seo.studio.rest.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Noor Najjar
 * @generated
 */
@Generated("")
public class AIRequestSerDes {

	public static AIRequest toDTO(String json) {
		AIRequestJSONParser aiRequestJSONParser = new AIRequestJSONParser();

		return aiRequestJSONParser.parseToDTO(json);
	}

	public static AIRequest[] toDTOs(String json) {
		AIRequestJSONParser aiRequestJSONParser = new AIRequestJSONParser();

		return aiRequestJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AIRequest aiRequest) {
		if (aiRequest == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (aiRequest.getAgentName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"agentName\": ");

			sb.append("\"");

			sb.append(_escape(aiRequest.getAgentName()));

			sb.append("\"");
		}

		if (aiRequest.getCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"count\": ");

			sb.append(aiRequest.getCount());
		}

		if (aiRequest.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(aiRequest.getDateCreated()));

			sb.append("\"");
		}

		if (aiRequest.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(aiRequest.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (aiRequest.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(aiRequest.getId());
		}

		if (aiRequest.getRequestDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"requestDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(aiRequest.getRequestDate()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AIRequestJSONParser aiRequestJSONParser = new AIRequestJSONParser();

		return aiRequestJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(AIRequest aiRequest) {
		if (aiRequest == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (aiRequest.getAgentName() == null) {
			map.put("agentName", null);
		}
		else {
			map.put("agentName", String.valueOf(aiRequest.getAgentName()));
		}

		if (aiRequest.getCount() == null) {
			map.put("count", null);
		}
		else {
			map.put("count", String.valueOf(aiRequest.getCount()));
		}

		if (aiRequest.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(aiRequest.getDateCreated()));
		}

		if (aiRequest.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(aiRequest.getExternalReferenceCode()));
		}

		if (aiRequest.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(aiRequest.getId()));
		}

		if (aiRequest.getRequestDate() == null) {
			map.put("requestDate", null);
		}
		else {
			map.put(
				"requestDate",
				liferayToJSONDateFormat.format(aiRequest.getRequestDate()));
		}

		return map;
	}

	public static class AIRequestJSONParser extends BaseJSONParser<AIRequest> {

		@Override
		protected AIRequest createDTO() {
			return new AIRequest();
		}

		@Override
		protected AIRequest[] createDTOArray(int size) {
			return new AIRequest[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "agentName")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "count")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "requestDate")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			AIRequest aiRequest, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "agentName")) {
				if (jsonParserFieldValue != null) {
					aiRequest.setAgentName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "count")) {
				if (jsonParserFieldValue != null) {
					aiRequest.setCount(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					aiRequest.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					aiRequest.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					aiRequest.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "requestDate")) {
				if (jsonParserFieldValue != null) {
					aiRequest.setRequestDate(
						toDate((String)jsonParserFieldValue));
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
// LIFERAY-REST-BUILDER-HASH:-39085529