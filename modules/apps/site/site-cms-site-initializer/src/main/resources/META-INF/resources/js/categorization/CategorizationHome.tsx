/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';

import '../../css/categorization/Categorization.scss';
import CategorizationToolbar from './CategorizationToolbar';
import TagsView from './tags/TagsView';
import {AssetType} from './types/AssetType';
import VocabulariesView from './vocabulary/VocabulariesView';

const TABS: string[] = [
	Liferay.Language.get('vocabularies'),
	Liferay.Language.get('tags'),
];

export default function CategorizationHome({
	onChangeActiveSection,
	siteId,
	vocabularyAssetTypes,
}: {
	onChangeActiveSection: Function;
	siteId: number;
	vocabularyAssetTypes: AssetType[];
}) {
	const [tab, setTab] = useState(TABS[0]);

	const handleTabChange = (tab: string) => {
		setTab(tab);
	};

	const renderTabContent = () => {
		switch (tab) {
			case Liferay.Language.get('tags'):
				return <TagsView />;
			default:
				return (
					<>
						<VocabulariesView
							assetTypes={vocabularyAssetTypes}
							onChangeActiveSection={onChangeActiveSection}
							siteId={siteId}
						/>
					</>
				);
		}
	};

	return (
		<>
			<CategorizationToolbar
				onChangeTab={handleTabChange}
				tabs={TABS}
			/>

			{renderTabContent()}
		</>
	);
}
