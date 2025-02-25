/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the AssetTagDepotEntryRel service. Represents a row in the &quot;AssetTagDepotEntryRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagDepotEntryRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.asset.tags.model.impl.AssetTagDepotEntryRelImpl"
)
@ProviderType
public interface AssetTagDepotEntryRel
	extends AssetTagDepotEntryRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.asset.tags.model.impl.AssetTagDepotEntryRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AssetTagDepotEntryRel, Long>
		ASSET_TAG_DEPOT_ENTRY_REL_ID_ACCESSOR =
			new Accessor<AssetTagDepotEntryRel, Long>() {

				@Override
				public Long get(AssetTagDepotEntryRel assetTagDepotEntryRel) {
					return assetTagDepotEntryRel.getAssetTagDepotEntryRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<AssetTagDepotEntryRel> getTypeClass() {
					return AssetTagDepotEntryRel.class;
				}

			};

}