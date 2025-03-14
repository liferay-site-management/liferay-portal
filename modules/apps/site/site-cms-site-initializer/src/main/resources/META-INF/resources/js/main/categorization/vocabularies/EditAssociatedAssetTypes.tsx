/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayForm, {ClayCheckbox, ClayToggle} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayMultiSelect from '@clayui/multi-select';
import ClayTable from '@clayui/table';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {AssetType} from '../types/AssetType';
import {IVocabulary} from '../types/IVocabulary';

const ALL_ASSET_TYPES: AssetType[] = [
	{
		label: Liferay.Language.get('all-asset-types'),
		required: false,
		value: '0',
	},
];

export default function EditAssociatedAssetTypes({
	assetTypes,
	vocabulary,
}: {
	assetTypes: AssetType[];
	vocabulary: IVocabulary;
}) {
	const [allAssetTypesSelected, setAllAssetTypesSelected] =
		useState<boolean>(true);
	const [inputError, setInputError] = useState<string>('');

	const [selectedAssetTypes, setSelectedAssetTypes] = useState<AssetType[]>(
		vocabulary ? vocabulary.assetTypes : ALL_ASSET_TYPES
	);

	const isChecked = (items: AssetType[], item: AssetType) => {
		return !!items.find((val) => val.value === item.value);
	};

	const onChangeAllAssetTypes = () => {
		setAllAssetTypesSelected(!allAssetTypesSelected);

		if (allAssetTypesSelected) {
			setSelectedAssetTypes([]);
		}
		else {
			setSelectedAssetTypes(ALL_ASSET_TYPES);
		}
	};

	const onItemsChange = (selectedItems: AssetType[]) => {
		setSelectedAssetTypes(
			selectedItems.filter((item, i, self) => i === self.indexOf(item))
		);

		if (selectedItems.length) {
			setInputError('');
		}
		else {
			setInputError(
				sub(
					Liferay.Language.get('the-x-field-is-required'),
					Liferay.Language.get('asset-types')
				)
			);
		}
	};

	const toggleItemChecked = (item: AssetType) => {
		if (!isChecked(selectedAssetTypes, item)) {
			onItemsChange([
				...selectedAssetTypes,
				{
					icon: item.icon,
					label: item.label,
					required: false,
					value: item.value,
				},
			]);
		}
		else {
			onItemsChange(
				selectedAssetTypes.filter((entry) => item.value !== entry.value)
			);
		}
	};

	const setRequiredOnAssetType = (item) => {
		const updatedSelectedAssetTypes = selectedAssetTypes.map(
			(assetType) => {
				if (assetType.value === item.value) {
					return {
						...assetType,
						restricted: !item.restricted,
					};
				}
				else {
					return assetType;
				}
			}
		);

		setSelectedAssetTypes(updatedSelectedAssetTypes);
	};

	return (
		<div className="vertical-nav-content-wrapper">
			<ClayForm.Group className="c-gap-4 d-flex flex-column p-4">
				<div className="form-title">
					{Liferay.Language.get('associated-asset-types')}
				</div>

				<p className="text-secondary">
					{Liferay.Language.get(
						'choose-the-asset-types-this-vocabulary-is-associated-with-and-whether-it-is-required'
					)}
				</p>

				<div>
					<label>{Liferay.Language.get('asset-types')}</label>

					<ClayMultiSelect
						disabled={allAssetTypesSelected}
						items={allAssetTypesSelected ? [] : selectedAssetTypes}
						onItemsChange={(items: AssetType[]) => {
							onItemsChange(items);
						}}
						placeholder={
							allAssetTypesSelected
								? Liferay.Language.get('all-asset-types')
								: ''
						}
						sourceItems={assetTypes}
					>
						{(item: any) => (
							<ClayMultiSelect.Item
								key={item.value}
								onClick={() => {
									toggleItemChecked(item);
								}}
								textValue={item.label}
							>
								<div className="autofit-row autofit-row-center">
									<div className="autofit-col mr-3">
										<ClayCheckbox
											aria-label={item.label}
											checked={isChecked(
												selectedAssetTypes,
												item
											)}
											className="invisible"
											onChange={() => {
												toggleItemChecked(item);
											}}
										/>
									</div>

									<span className="asset-icon">
										<ClayIcon symbol={item.icon} />
									</span>

									<div className="autofit-col">
										<span>{item.label}</span>
									</div>
								</div>
							</ClayMultiSelect.Item>
						)}
					</ClayMultiSelect>

					{inputError && (
						<ClayAlert displayType="danger" variant="feedback">
							{inputError}
						</ClayAlert>
					)}
				</div>

				<ClayCheckbox
					checked={allAssetTypesSelected}
					label="Make this vocabulary available in all asset types, including those yet to be created"
					onChange={onChangeAllAssetTypes}
				/>

				{!!selectedAssetTypes.length && (
					<ClayTable striped>
						<ClayTable.Head>
							<ClayTable.Row>
								<ClayTable.Cell className="text-secondary">
									{Liferay.Language.get('title')}
								</ClayTable.Cell>

								<ClayTable.Cell className="text-secondary">
									{Liferay.Language.get('required')}
								</ClayTable.Cell>
							</ClayTable.Row>
						</ClayTable.Head>

						<ClayTable.Body>
							{selectedAssetTypes.map((assetType: AssetType) => (
								<ClayTable.Row key={assetType.value}>
									<ClayTable.Cell>
										<span className="asset-icon">
											<ClayIcon symbol={assetType.icon} />
										</span>

										{assetType.label}
									</ClayTable.Cell>

									<ClayTable.Cell>
										<ClayToggle
											onToggle={() => {
												setRequiredOnAssetType(
													assetType
												);
											}}
											toggled={assetType.required}
										/>
									</ClayTable.Cell>
								</ClayTable.Row>
							))}
						</ClayTable.Body>
					</ClayTable>
				)}
			</ClayForm.Group>
		</div>
	);
}
