/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {ClayVerticalNav} from '@clayui/nav';
import {ManagementToolbar} from 'frontend-js-components-web';
import {navigate, sub} from 'frontend-js-web';
import React, {useState} from 'react';

import VocabularyService from '../services/VocabularyService';
import {AssetType} from '../types/AssetType';
import {IVocabulary} from '../types/IVocabulary';
import EditAssociatedAssetTypes from './EditAssociatedAssetTypes';
import EditGeneralInfo from './EditGeneralInfo';

export default function EditVocabulary({
	assetTypes,
	backURL,
	defaultLanguageId,
	locales,
	siteId,
	spritemap,
	vocabulary,
}: {
	assetTypes: AssetType[];
	backURL: string;
	defaultLanguageId: string;
	locales: any[];
	siteId: number;
	spritemap: string;
	vocabulary: IVocabulary;
}) {
	const [activeVerticalNavKey, setActiveVerticalNavKey] = useState('general');
	const [initialItemData, setInitialItemData] = useState<IVocabulary>(
		vocabulary
			? vocabulary
			: {
					description: '',
					name: '',
					name_i18n: {
						'en-US': '',
					},
				}
	);

	const handleVerticalNavChange = (verticalNav: string) => {
		setActiveVerticalNavKey(verticalNav);
	};

	const onSave = async () => {
		try {

			await VocabularyService.createVocabulary(siteId, initialItemData);

			await navigate(backURL);

			Liferay.Util.openToast({
				message: Liferay.Util.sub(
					Liferay.Language.get('x-was-published-successfully'),
					initialItemData.name
				),
				type: 'success',
			});
		}
		catch (error) {
			Liferay.Util.openToast({
				message: Liferay.Language.get(
					'an-unexpected-system-error-occurred'
				),
				type: 'danger',
			});
		}
	};

	return (
		<div className="categorization-section">
			<div className="d-flex edit-vocabulary flex-column">
				<ManagementToolbar.Container>
					<ManagementToolbar.ItemList className="c-gap-3" expand>
						<ManagementToolbar.Item>
							<ClayButton
								aria-label={Liferay.Language.get('back')}
								className="btn btn-monospaced btn-outline-borderless btn-outline-secondary btn-sm"
								onClick={() => navigate(backURL)}
							>
								<ClayIcon symbol="angle-left" />
							</ClayButton>
						</ManagementToolbar.Item>

						<ManagementToolbar.Item className="nav-item-expand">
							<h2 className="font-weight-semi-bold m-0 text-5">
								{vocabulary
									? sub(
											Liferay.Language.get('edit-x'),
											vocabulary.name
										)
									: Liferay.Language.get('new-vocabulary')}
							</h2>
						</ManagementToolbar.Item>

						<ManagementToolbar.Item>
							<ClayButton
								className="btn btn-outline-borderless btn-outline-secondary btn-sm"
								onClick={() => navigate(backURL)}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>
						</ManagementToolbar.Item>

						<ManagementToolbar.Item>
							<ClayButton
								displayType="primary"
								onClick={onSave}
								size="sm"
							>
								{Liferay.Language.get('save')}
							</ClayButton>
						</ManagementToolbar.Item>
					</ManagementToolbar.ItemList>
				</ManagementToolbar.Container>

				<ClayLayout.ContainerFluid className="m-0" size={false}>
					<ClayLayout.Row>
						<ClayLayout.Col
							className="categorization-vertical-nav p-0"
							md={3}
							sm={12}
						>
							<div className="p-4">
								<ClayVerticalNav
									items={[
										{
											active:
												activeVerticalNavKey ===
												'general',
											label: Liferay.Language.get(
												'general'
											),
											onClick: () =>
												handleVerticalNavChange(
													'general'
												),
										},
										{
											active:
												activeVerticalNavKey ===
												'assetTypes',
											label: Liferay.Language.get(
												'associated-asset-types'
											),
											onClick: () =>
												handleVerticalNavChange(
													'assetTypes'
												),
										},
									]}
								/>
							</div>
						</ClayLayout.Col>

						<ClayLayout.Col md={9} sm={12}>
							{activeVerticalNavKey === 'general' && (
								<EditGeneralInfo
									defaultLanguageId={defaultLanguageId}
									locales={locales}
									spritemap={spritemap}
									updateVocabulary={setInitialItemData}
									vocabulary={initialItemData}
								/>
							)}

							{activeVerticalNavKey === 'assetTypes' && (
								<EditAssociatedAssetTypes
									assetTypes={assetTypes}
								/>
							)}
						</ClayLayout.Col>
					</ClayLayout.Row>
				</ClayLayout.ContainerFluid>
			</div>
		</div>
	);
}
