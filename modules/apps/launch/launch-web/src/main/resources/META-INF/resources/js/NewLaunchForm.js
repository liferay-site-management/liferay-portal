/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import React, {useState} from 'react';

import {createLaunch} from './api/launches';

export default function NewLaunchForm({onCancel, onCreated}) {
	const [description, setDescription] = useState('');
	const [error, setError] = useState(null);
	const [name, setName] = useState('');
	const [submitting, setSubmitting] = useState(false);

	const handleCreate = async () => {
		const trimmed = name.trim();

		if (!trimmed) {
			setError('Name is a required field');

			return;
		}

		setError(null);
		setSubmitting(true);

		try {
			const launch = await createLaunch({
				description: description.trim(),
				name: trimmed,
			});

			onCreated(launch);
		}
		catch (exception) {
			setError(exception.message);
		}
		finally {
			setSubmitting(false);
		}
	};

	return (
		<div className="launch-new-form">
			<div className="management-bar management-bar-light navbar">
				<div className="align-items-center container-fluid d-flex">
					<ClayButton
						aria-label="Back"
						borderless
						className="mr-2"
						displayType="secondary"
						onClick={onCancel}
					>
						←
					</ClayButton>

					<span className="font-weight-bold">New Launch</span>

					<div className="ml-auto">
						<ClayButton
							className="mr-2"
							disabled={submitting}
							displayType="secondary"
							onClick={onCancel}
						>
							Cancel
						</ClayButton>

						<ClayButton
							disabled={submitting}
							displayType="primary"
							onClick={handleCreate}
						>
							Create
						</ClayButton>
					</div>
				</div>
			</div>

			<div className="container py-5">
				<ClayForm.Group>
					<ClayInput
						aria-label="Launch name"
						className="border-0 form-control-lg px-0 shadow-none"
						onChange={(event) => setName(event.target.value)}
						placeholder="Untitled Launch"
						style={{
							fontSize: '2rem',
							fontWeight: 600,
						}}
						value={name}
					/>
				</ClayForm.Group>

				<ClayForm.Group className="mt-5">
					<label htmlFor="launch-description">Description</label>

					<ClayInput
						component="textarea"
						id="launch-description"
						onChange={(event) => setDescription(event.target.value)}
						placeholder="Enter Description"
						rows={4}
						value={description}
					/>
				</ClayForm.Group>
			</div>

			{error ? (
				<div
					style={{
						bottom: '1rem',
						left: '1rem',
						position: 'fixed',
						zIndex: 1050,
					}}
				>
					<ClayAlert
						displayType="danger"
						onClose={() => setError(null)}
						title="Name"
					>
						{error.startsWith('Name') ? error.substring(5) : error}
					</ClayAlert>
				</div>
			) : null}
		</div>
	);
}
