/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.change.tracking.taglib.internal.display.context;

import com.liferay.change.tracking.configuration.CTSettingsConfiguration;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.spi.history.CTCollectionHistoryProvider;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author David Truong
 */
public class TimelineDisplayContext {

	public TimelineDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		String className, long classPK) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_classPK = classPK;

		_classNameId = PortalUtil.getClassNameId(className);

		_themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<CTCollection> getCTCollections() throws PortalException {
		CTCollectionHistoryProvider<Object> ctCollectionHistoryProvider =
			(CTCollectionHistoryProvider<Object>)
				_ctCollectionHistoryProviderServiceTrackerMap.getService(
					_classNameId);

		if (ctCollectionHistoryProvider == null) {
			return _defaultCTCollectionHistoryProvider.getCTCollections(
				_classNameId, _classPK);
		}

		return ctCollectionHistoryProvider.getCTCollections(
			_classNameId, _classPK);
	}

	public Map<String, Object> getDropdownReactData(CTCollection ctCollection)
		throws Exception {

		Map<String, Object> data = new HashMap<>();

		if (ctCollection.getStatus() != WorkflowConstants.STATUS_EXPIRED) { //&&
			//			CTCollectionPermission.contains(
			//				permissionChecker, ctCollection, ActionKeys.UPDATE)) {

			data.put(
				"editURL",
				PortletURLBuilder.create(
					PortalUtil.getControlPanelPortletURL(
						PortalUtil.getHttpServletRequest(_renderRequest),
						_themeDisplay.getScopeGroup(),
						CTPortletKeys.PUBLICATIONS, 0, 0,
						PortletRequest.RENDER_PHASE)
				).setMVCRenderCommandName(
					"/change_tracking/edit_ct_collection"
				).setRedirect(
					_themeDisplay.getURLCurrent()
				).setParameter(
					"ctCollectionId", ctCollection.getCtCollectionId()
				).buildString());
		}

		data.put(
			"reviewURL",
			PortletURLBuilder.create(
				PortalUtil.getControlPanelPortletURL(
					PortalUtil.getHttpServletRequest(_renderRequest),
					_themeDisplay.getScopeGroup(), CTPortletKeys.PUBLICATIONS,
					0, 0, PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/change_tracking/view_changes"
			).setRedirect(
				_themeDisplay.getURLCurrent()
			).setParameter(
				"ctCollectionId", ctCollection.getCtCollectionId()
			).buildString());

		data.put(
			"revertURL",
			PortletURLBuilder.create(
				PortalUtil.getControlPanelPortletURL(
					PortalUtil.getHttpServletRequest(_renderRequest),
					_themeDisplay.getScopeGroup(), CTPortletKeys.PUBLICATIONS,
					0, 0, PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/change_tracking/undo_ct_collection"
			).setRedirect(
				_themeDisplay.getURLCurrent()
			).setParameter(
				"ctCollectionId", ctCollection.getCtCollectionId()
			).setParameter(
				"revert", Boolean.TRUE
			).buildString());

		//		if (CTCollectionPermission.contains(
		//			permissionChecker, ctCollection, ActionKeys.DELETE)) {

		data.put(
			"deleteURL",
			_getDeleteHref(
				PortalUtil.getHttpServletRequest(_renderRequest),
				_themeDisplay.getURLCurrent(),
				ctCollection.getCtCollectionId()));
		//		}

		return data;
	}

	public boolean isPublicationsEnabled() {
		boolean publicationsEnabled = false;

		try {
			CTSettingsConfiguration ctSettingsConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					CTSettingsConfiguration.class,
					_themeDisplay.getCompanyId());

			publicationsEnabled = ctSettingsConfiguration.enabled();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return publicationsEnabled;
	}

	private String _getDeleteHref(
		HttpServletRequest httpServletRequest, String backURL,
		long ctCollectionId) {

		return StringBundler.concat(
			"javascript:Liferay.Util.openConfirmModal({message: '",
			LanguageUtil.get(
				httpServletRequest,
				"are-you-sure-you-want-to-delete-this-publication"),
			"', onConfirm: (isConfirmed) => {if (isConfirmed) {",
			"submitForm(document.hrefFm, '",
			PortletURLBuilder.create(
				PortalUtil.getControlPanelPortletURL(
					httpServletRequest, _themeDisplay.getScopeGroup(),
					CTPortletKeys.PUBLICATIONS, 0, 0,
					PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/change_tracking/delete_ct_collection"
			).setRedirect(
				backURL
			).setParameter(
				"ctCollectionId", ctCollectionId
			).buildString(),
			"');} else {self.focus();}}});");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TimelineDisplayContext.class);

	private static ServiceTrackerMap<Long, CTCollectionHistoryProvider<?>>
		_ctCollectionHistoryProviderServiceTrackerMap;
	private static CTCollectionHistoryProvider<?>
		_defaultCTCollectionHistoryProvider;

	static {
		Bundle bundle = FrameworkUtil.getBundle(TimelineDisplayContext.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_ctCollectionHistoryProviderServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext,
				(Class<CTCollectionHistoryProvider<?>>)
					(Class<?>)CTCollectionHistoryProvider.class,
				null,
				(serviceReference, emitter) -> {
					CTCollectionHistoryProvider<?> ctCollectionHistoryProvider =
						bundleContext.getService(serviceReference);

					try {
						Class<?> modelClass =
							ctCollectionHistoryProvider.getModelClass();

						if (modelClass != null) {
							emitter.emit(
								ClassNameLocalServiceUtil.getClassNameId(
									modelClass));
						}
						else {
							_defaultCTCollectionHistoryProvider =
								ctCollectionHistoryProvider;
						}
					}
					finally {
						bundleContext.ungetService(serviceReference);
					}
				});
	}

	private final long _classNameId;
	private final long _classPK;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}