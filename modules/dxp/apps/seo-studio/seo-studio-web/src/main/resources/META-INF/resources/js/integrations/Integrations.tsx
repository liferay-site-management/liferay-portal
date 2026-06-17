/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {fetch} from 'frontend-js-web';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import IntegrationStatusCellRenderer from './cell_renderers/IntegrationStatusCellRenderer';

import './Integrations.scss';

interface IProps {
	apiURL: string;
	fdsId: string;
	itemsActions: any[];
	views: any[];
}

export default function Integrations({
	apiURL,
	fdsId,
	itemsActions,
	views,
}: IProps) {
	const [active, setActive] = useState(false);
	const [items, setItems] = useState<any[]>([]);

	const abortControllerRef = useRef<AbortController | null>(null);

	const reloadItems = useCallback(() => {
		abortControllerRef.current?.abort();

		const abortController = new AbortController();

		abortControllerRef.current = abortController;

		fetch(apiURL, {
			headers: {Accept: 'application/json'},
			signal: abortController.signal,
		})
			.then((response: Response) => {
				if (!response.ok) {
					throw new Error();
				}

				return response.json();
			})
			.then((data: any) => {
				setItems(data.items || []);
			})
			.catch((error: Error) => {
				if (error.name !== 'AbortError') {
					setItems([]);
				}
			});
	}, [apiURL]);

	useEffect(() => {
		reloadItems();

		return () => abortControllerRef.current?.abort();
	}, [reloadItems]);

	const handleActionClick = () => {};

	return (
		<div className="p-3 p-md-4">
			<div className="sheet">
				<div className="sheet-header">
					<div className="autofit-row autofit-row-center">
						<div className="autofit-col autofit-col-expand">
							<h2 className="sheet-title">
								{Liferay.Language.get('integrations')}
							</h2>
						</div>

						<div className="autofit-col">
							<ClayDropDown
								active={active}
								menuElementAttrs={{
									className: 'integrations-add-menu',
								}}
								onActiveChange={setActive}
								trigger={
									<ClayButton
										className="add-integration-button"
										displayType="primary"
									>
										<span className="inline-item inline-item-before">
											{Liferay.Language.get(
												'add-integration'
											)}
										</span>

										<ClayIcon symbol="caret-bottom" />
									</ClayButton>
								}
							>
								<ClayDropDown.ItemList>
									{[]}
								</ClayDropDown.ItemList>
							</ClayDropDown>
						</div>
					</div>
				</div>

				{items.length ? (
					<FrontendDataSet
						appURL={`${Liferay.ThemeDisplay.getPortalURL()}/o/frontend-data-set-taglib/app`}
						customRenderers={{
							tableCell: [
								{
									component: IntegrationStatusCellRenderer,
									name: 'integrationStatusCellRenderer',
									type: 'internal',
								},
							],
						}}
						id={fdsId}
						items={items}
						itemsActions={itemsActions}
						onActionDropdownItemClick={handleActionClick}
						showManagementBar={false}
						showPagination={false}
						showSearch={false}
						views={views}
					/>
				) : (
					<div className="integrations-empty text-center">
						<img
							alt=""
							className="integrations-empty-image"
							src="/o/cms-theme/images/states/empty_state_reduced_motion.svg"
						/>

						<div className="integrations-empty-title">
							{Liferay.Language.get('no-integration-yet')}
						</div>

						<div className="integrations-empty-description">
							{Liferay.Language.get('add-your-first-integration')}
						</div>
					</div>
				)}
			</div>
		</div>
	);
}
