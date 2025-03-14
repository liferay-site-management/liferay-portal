/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import React from 'react';

import {AssetType} from '../types/AssetType';

export default function VocabulariesView({
	assetTypes,
	onChangeActiveSection,
	siteId,
}: {
	assetTypes: AssetType[];
	onChangeActiveSection: Function;
	siteId: number;
}) {
	const creationMenu = {
		primaryItems: [
			{
				label: Liferay.Language.get('add-vocabulary'),
				onClick: () => onChangeActiveSection('edit-vocabulary'),
			},
		],
	};

	const filters = [
		{
			id: 'assetTypes',
			items: assetTypes,
			label: 'Asset Types',
			multiple: true,
			type: 'selection',
		},
	];

	const views = [
		{
			contentRenderer: 'table',
			default: true,
			label: Liferay.Language.get('table'),
			name: 'table',
			schema: {
				fields: [
					{
						fieldName: 'name',
						label: Liferay.Language.get('title'),
						sortable: true,
					},
					{
						fieldName: 'numberOfTaxonomyCategories',
						label: Liferay.Language.get('categories'),
						sortable: true,
					},
					{
						fieldName: 'assetTypes.type',
						label: Liferay.Language.get('type'),
						sortable: true,
					},
					{
						fieldName: 'dateModified',
						label: Liferay.Language.get('modified'),
						sortable: true,
					},
				],
			},
			thumbnail: 'table',
		},
	];

	const emptyState = {
		description: Liferay.Language.get(
			'vocabularies-are-needed-to-create-categories'
		),
		image: '/states/cms_empty_state.svg',
		title: Liferay.Language.get('no-vocabularies-yet'),
	};

	return (
		<FrontendDataSet
			apiURL={`/o/headless-admin-taxonomy/v1.0/sites/${siteId}/taxonomy-vocabularies`}
			creationMenu={creationMenu}
			emptyState={emptyState}
			filters={filters}
			id="VocabulariesView"
			showManagementBar={true}
			showSearch={true}
			views={views}
		/>
	);
}
