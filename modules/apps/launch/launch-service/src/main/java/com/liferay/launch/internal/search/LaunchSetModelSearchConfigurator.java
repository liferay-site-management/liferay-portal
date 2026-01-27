/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.internal.search;

import com.liferay.launch.internal.search.spi.model.index.contributor.LaunchSetModelIndexerWriterContributor;
import com.liferay.launch.model.LaunchSet;
import com.liferay.launch.service.LaunchSetLocalService;
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
public class LaunchSetModelSearchConfigurator
	implements ModelSearchConfigurator<LaunchSet> {

	@Override
	public String getClassName() {
		return LaunchSet.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID
		};
	}

	@Override
	public ModelIndexerWriterContributor<LaunchSet>
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
			new LaunchSetModelIndexerWriterContributor(
				_launchSetLocalService,
				_dynamicQueryBatchIndexingActionableFactory);
	}

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	@Reference
	private LaunchSetLocalService _launchSetLocalService;

	private ModelIndexerWriterContributor<LaunchSet>
		_modelIndexWriterContributor;

}