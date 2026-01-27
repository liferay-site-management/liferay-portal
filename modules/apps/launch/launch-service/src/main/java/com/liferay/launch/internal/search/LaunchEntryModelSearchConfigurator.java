/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.internal.search;

import com.liferay.launch.internal.search.spi.model.index.contributor.LaunchEntryModelIndexerWriterContributor;
import com.liferay.launch.model.LaunchEntry;
import com.liferay.launch.service.LaunchEntryLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = ModelSearchConfigurator.class)
public class LaunchEntryModelSearchConfigurator
	implements ModelSearchConfigurator<LaunchEntry> {

	@Override
	public String getClassName() {
		return LaunchEntry.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<LaunchEntry>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public boolean isSearchResultPermissionFilterSuppressed() {
		return true;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new LaunchEntryModelIndexerWriterContributor(
				_launchEntryLocalService,
				_dynamicQueryBatchIndexingActionableFactory);
	}

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	@Reference
	private LaunchEntryLocalService _launchEntryLocalService;

	private ModelIndexerWriterContributor<LaunchEntry>
		_modelIndexWriterContributor;

}