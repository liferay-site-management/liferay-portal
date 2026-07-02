/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.model.listener;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = ModelListener.class)
public class DisplayingLayoutCTEntryModelListener
	extends BaseModelListener<CTEntry> {

	@Override
	public void onAfterCreate(CTEntry ctEntry) {
		if (!FeatureFlagManagerUtil.isEnabled(
				ctEntry.getCompanyId(), "LPD-89487")) {

			return;
		}

		try {
			long layoutClassNameId = _classNameLocalService.getClassNameId(
				Layout.class);
			long modelClassNameId = ctEntry.getModelClassNameId();

			if (modelClassNameId == layoutClassNameId) {
				return;
			}

			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassNameId(modelClassNameId);

			if (assetRendererFactory == null) {
				return;
			}

			List<LayoutClassedModelUsage> layoutClassedModelUsages =
				_layoutClassedModelUsageLocalService.
					getLayoutClassedModelUsages(
						modelClassNameId, ctEntry.getModelClassPK());

			for (LayoutClassedModelUsage layoutClassedModelUsage :
					layoutClassedModelUsages) {

				_touchLayout(
					ctEntry, layoutClassNameId,
					layoutClassedModelUsage.getPlid());
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to touch displaying layouts for model class ",
						"name ID ", ctEntry.getModelClassNameId(),
						", and model class PK ", ctEntry.getModelClassPK()),
					exception);
			}
		}
	}

	private void _touchLayout(
		CTEntry ctEntry, long layoutClassNameId, long plid) {

		CTEntry existingCTEntry = _ctEntryLocalService.fetchCTEntry(
			ctEntry.getCtCollectionId(), layoutClassNameId, plid);

		if (existingCTEntry != null) {
			return;
		}

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctEntry.getCtCollectionId())) {

			Layout layout = _layoutLocalService.fetchLayout(plid);

			if (layout == null) {
				return;
			}

			_layoutLocalService.updateLayout(layout);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DisplayingLayoutCTEntryModelListener.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

}