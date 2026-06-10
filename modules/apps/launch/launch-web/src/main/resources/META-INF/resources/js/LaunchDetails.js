/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React, {useEffect, useState} from 'react';

import {getLaunch} from './api/launches';

export default function LaunchDetails({launchId, onBack}) {
	const [launch, setLaunch] = useState(null);
	const [error, setError] = useState(null);

	useEffect(() => {
		getLaunch(launchId)
			.then(setLaunch)
			.catch((exception) => setError(exception.message));
	}, [launchId]);

	if (error) {
		return (
			<div className="p-4">
				<p className="text-danger">{error}</p>

				<ClayButton displayType="link" onClick={onBack}>
					← Back to Launches
				</ClayButton>
			</div>
		);
	}

	if (!launch) {
		return <div className="p-4">Loading…</div>;
	}

	return (
		<div className="launch-details">
			<div className="container-fluid p-4">
				<nav aria-label="breadcrumb">
					<ol className="breadcrumb">
						<li className="breadcrumb-item">
							<a
								href="#"
								onClick={(event) => {
									event.preventDefault();
									onBack();
								}}
							>
								Launches
							</a>
						</li>

						<li
							aria-current="page"
							className="active breadcrumb-item"
						>
							{launch.name}
						</li>
					</ol>
				</nav>

				<h1 className="mb-4">{launch.name}</h1>

				{launch.description ? (
					<p className="text-secondary">{launch.description}</p>
				) : null}
			</div>
		</div>
	);
}
