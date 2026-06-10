/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayEmptyState from '@clayui/empty-state';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayTable from '@clayui/table';
import ClayTabs from '@clayui/tabs';
import React, {useEffect, useState} from 'react';

import {listLaunches} from './api/launches';

const STATUS_LABELS = {
	0: 'Published',
	1: 'In Progress',
	2: 'In Progress',
	5: 'Failed',
};

function statusBadge(status) {
	const label = STATUS_LABELS[status] ?? 'In Progress';
	const className =
		label === 'Published'
			? 'label label-success'
			: label === 'Failed'
				? 'label label-danger'
				: 'label label-info';

	return <span className={className}>{label}</span>;
}

export default function LaunchesLanding({onNew, onSelect}) {
	const [activeTab, setActiveTab] = useState(0);
	const [launches, setLaunches] = useState([]);
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		listLaunches()
			.then((items) => setLaunches(items))
			.catch(() => setLaunches([]))
			.finally(() => setLoading(false));
	}, []);

	if (loading) {
		return <div className="p-4">Loading launches…</div>;
	}

	return (
		<div className="launch-landing">
			<div className="container-fluid p-4">
				<h1 className="mb-4">Launches</h1>

				<ClayTabs modern>
					<ClayTabs.Item
						active={activeTab === 0}
						innerProps={{
							'aria-controls': 'tabpanel-ongoing',
						}}
						onClick={() => setActiveTab(0)}
					>
						Ongoing
					</ClayTabs.Item>

					<ClayTabs.Item
						active={activeTab === 1}
						innerProps={{
							'aria-controls': 'tabpanel-published',
						}}
						onClick={() => setActiveTab(1)}
					>
						Published
					</ClayTabs.Item>
				</ClayTabs>

				<ClayTabs.Content activeIndex={activeTab} fade>
					<ClayTabs.TabPane aria-labelledby="tab-ongoing">
						{!launches.length ? (
							<ClayEmptyState
								description='Click "New" to create your first launch.'
								imgProps={{
									alt: 'No launches',
								}}
								imgSrc="/o/admin-theme/images/states/empty_state.gif"
								title="No Ongoing Launches"
							>
								<ClayButton
									displayType="primary"
									onClick={onNew}
								>
									New Launch
								</ClayButton>
							</ClayEmptyState>
						) : (
							<LaunchTable
								launches={launches}
								onSelect={onSelect}
							/>
						)}
					</ClayTabs.TabPane>

					<ClayTabs.TabPane aria-labelledby="tab-published">
						<ClayEmptyState
							description="No published launches yet."
							imgProps={{alt: 'No published launches'}}
							imgSrc="/o/admin-theme/images/states/empty_state.gif"
							title="No Published Launches"
						/>
					</ClayTabs.TabPane>
				</ClayTabs.Content>
			</div>

			{launches.length ? (
				<div className="d-flex justify-content-end p-3">
					<ClayButton displayType="primary" onClick={onNew}>
						New Launch
					</ClayButton>
				</div>
			) : null}
		</div>
	);
}

function LaunchTable({launches, onSelect}) {
	return (
		<>
			<ClayTable>
				<ClayTable.Head>
					<ClayTable.Row>
						<ClayTable.Cell headingCell>Name</ClayTable.Cell>

						<ClayTable.Cell headingCell>Description</ClayTable.Cell>

						<ClayTable.Cell headingCell>Modified</ClayTable.Cell>

						<ClayTable.Cell headingCell>Status</ClayTable.Cell>
					</ClayTable.Row>
				</ClayTable.Head>

				<ClayTable.Body>
					{launches.map((launch) => (
						<ClayTable.Row
							key={launch.id}
							onClick={() => onSelect?.(launch)}
							style={{cursor: onSelect ? 'pointer' : 'default'}}
						>
							<ClayTable.Cell>
								<a
									className="text-truncate-inline"
									href="#"
									onClick={(event) => {
										event.preventDefault();
										onSelect?.(launch);
									}}
								>
									{launch.name}
								</a>
							</ClayTable.Cell>

							<ClayTable.Cell>
								{launch.description || '—'}
							</ClayTable.Cell>

							<ClayTable.Cell>
								{launch.dateModified
									? new Date(
											launch.dateModified
										).toLocaleString()
									: '—'}
							</ClayTable.Cell>

							<ClayTable.Cell>
								{statusBadge(launch.status?.code)}
							</ClayTable.Cell>
						</ClayTable.Row>
					))}
				</ClayTable.Body>
			</ClayTable>

			<ClayPaginationBarWithBasicItems
				activeDelta={10}
				activePage={1}
				ellipsisBuffer={3}
				onPageChange={() => {}}
				totalItems={launches.length}
			/>
		</>
	);
}
