/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.internal.search.spi.model.index.contributor;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.model.AssetVocabularyDepotEntryRel;
import com.liferay.asset.service.AssetVocabularyDepotEntryRelLocalService;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "indexer.class.name=com.liferay.asset.kernel.model.AssetVocabulary",
	service = ModelDocumentContributor.class
)
public class AssetVocabularyModelDocumentContributor
	implements ModelDocumentContributor<AssetVocabulary> {

	@Override
	public void contribute(Document document, AssetVocabulary assetVocabulary) {
		document.addKeyword(
			"depotEntryIds",
			_getDepotEntryIds(assetVocabulary.getVocabularyId()));
	}

	private long[] _getDepotEntryIds(long vocabularyId) {
		return ListUtil.toLongArray(
			_assetVocabularyDepotEntryRelLocalService.
				getAssetVocabularyDepotEntryRelsByAssetVocabularyId(
					vocabularyId),
			AssetVocabularyDepotEntryRel::getDepotEntryId);
	}

	@Reference
	private AssetVocabularyDepotEntryRelLocalService
		_assetVocabularyDepotEntryRelLocalService;

}