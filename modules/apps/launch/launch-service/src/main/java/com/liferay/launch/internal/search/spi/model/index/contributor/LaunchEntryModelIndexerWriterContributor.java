/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.internal.search.spi.model.index.contributor;

import com.liferay.launch.model.LaunchEntry;
import com.liferay.launch.service.LaunchEntryLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author David Truong
 */
public class LaunchEntryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<LaunchEntry> {

	public LaunchEntryModelIndexerWriterContributor(
		LaunchEntryLocalService launchSetLocalService,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory) {

		_launchSetLocalService = launchSetLocalService;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(LaunchEntry launchSet) -> batchIndexingActionable.addDocuments(
				modelIndexerWriterDocumentHelper.getDocument(launchSet)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_launchSetLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(LaunchEntry launchSet) {
		return launchSet.getCompanyId();
	}

	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;
	private final LaunchEntryLocalService _launchSetLocalService;

}