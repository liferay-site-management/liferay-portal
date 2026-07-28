/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.item.selector.web.internal.display.context;

import com.liferay.object.constants.ObjectPortletKeys;
import com.liferay.object.definition.setting.util.ObjectDefinitionSettingUtil;
import com.liferay.object.item.selector.ObjectDefinitionItemSelectorCriterion;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectDefinitionSetting;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import jakarta.portlet.PortletURL;
import jakarta.portlet.RenderRequest;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Jonathan McCann
 */
public class ObjectDefinitionDisplayContext {

	public ObjectDefinitionDisplayContext(
		HttpServletRequest httpServletRequest,
		ObjectDefinitionItemSelectorCriterion
			objectDefinitionItemSelectorCriterion,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectDefinitionSettingLocalService objectDefinitionSettingLocalService,
		PortletURL portletURL, RenderRequest renderRequest) {

		_httpServletRequest = httpServletRequest;
		_objectDefinitionItemSelectorCriterion =
			objectDefinitionItemSelectorCriterion;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectDefinitionSettingLocalService =
			objectDefinitionSettingLocalService;
		_portletURL = portletURL;
		_renderRequest = renderRequest;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public SearchContainer<ObjectDefinition>
			getObjectDefinitionSearchContainer()
		throws PortalException {

		if (_objectDefinitionSearchContainer != null) {
			return _objectDefinitionSearchContainer;
		}

		SearchContainer<ObjectDefinition> objectDefinitionSearchContainer =
			new SearchContainer<>(
				_renderRequest, _portletURL, null, "there-are-no-objects");

		objectDefinitionSearchContainer.setId("selectObjectDefinition");
		objectDefinitionSearchContainer.setOrderByCol(_getOrderByCol());
		objectDefinitionSearchContainer.setOrderByType(_getOrderByType());

		String columnName = objectDefinitionSearchContainer.getOrderByCol();

		if (columnName.equals("modified-date")) {
			columnName = "modifiedDate";
		}

		objectDefinitionSearchContainer.setResultsAndTotal(
			_getObjectDefinitions(
				OrderByComparatorFactoryUtil.create(
					"ObjectDefinition", columnName,
					Objects.equals(_getOrderByType(), "asc"))));

		_objectDefinitionSearchContainer = objectDefinitionSearchContainer;

		return _objectDefinitionSearchContainer;
	}

	private List<ObjectDefinition> _getObjectDefinitions(
		OrderByComparator<ObjectDefinition> orderByComparator) {

		String objectDefinitionSettingName =
			_objectDefinitionItemSelectorCriterion.
				getObjectDefinitionSettingName();

		Map<Long, ObjectDefinitionSetting> objectDefinitionSettingsMap =
			_getObjectDefinitionSettingsMap(objectDefinitionSettingName);

		return ListUtil.sort(
			ListUtil.filter(
				_objectDefinitionLocalService.getObjectDefinitions(
					_themeDisplay.getCompanyId(), true,
					WorkflowConstants.STATUS_APPROVED),
				objectDefinition -> ObjectDefinitionSettingUtil.isEnabled(
					objectDefinitionSettingName, objectDefinition,
					objectDefinitionSettingsMap)),
			orderByComparator);
	}

	private Map<Long, ObjectDefinitionSetting> _getObjectDefinitionSettingsMap(
		String objectDefinitionSettingName) {

		if (Validator.isNull(objectDefinitionSettingName)) {
			return Collections.emptyMap();
		}

		return _objectDefinitionSettingLocalService.
			getObjectDefinitionSettingsMap(
				_themeDisplay.getCompanyId(), objectDefinitionSettingName);
	}

	private String _getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_httpServletRequest, ObjectPortletKeys.OBJECT_DEFINITIONS,
			"object-definition-order-by-col", "label");

		return _orderByCol;
	}

	private String _getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_httpServletRequest, ObjectPortletKeys.OBJECT_DEFINITIONS,
			"object-definition-order-by-type", "asc");

		return _orderByType;
	}

	private final HttpServletRequest _httpServletRequest;
	private final ObjectDefinitionItemSelectorCriterion
		_objectDefinitionItemSelectorCriterion;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private SearchContainer<ObjectDefinition> _objectDefinitionSearchContainer;
	private final ObjectDefinitionSettingLocalService
		_objectDefinitionSettingLocalService;
	private String _orderByCol;
	private String _orderByType;
	private final PortletURL _portletURL;
	private final RenderRequest _renderRequest;
	private final ThemeDisplay _themeDisplay;

}