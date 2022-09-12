/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import React from 'react';

import CollapsablePanel from './components/form/CollapsablePanel';
import TextField from './components/form/TextField';

export default function PublicationTemplateEditView({
	ctCollectionTemplateId,
	description,
	name,
	publicationDescription,
	publicationName,
	saveButtonLabel,
}) {
	return (
		<div className="sheet sheet-lg">
			<TextField
				ariaLabel={Liferay.Language.get(
					'publication-template-name-placeholder'
				)}
				componentType="input"
				fieldValue={name}
				label="Name"
				placeholderValue={Liferay.Language.get(
					'publication-template-name-placeholder'
				)}
				required={true}
			/>

			<TextField
				ariaLabel={Liferay.Language.get(
					'publication-template-description-placeholder'
				)}
				componentType="textarea"
				fieldValue={description}
				label="Description"
				placeholderValue={Liferay.Language.get(
					'publication-template-description-placeholder'
				)}
				required={false}
			/>

			<CollapsablePanel title="Publication Information">
				<TextField
					ariaLabel={Liferay.Language.get(
						'publication-name-placeholder'
					)}
					componentType="input"
					fieldValue={name}
					label="Publication Name"
					placeholderValue={Liferay.Language.get(
						'publication-name-placeholder'
					)}
					required={true}
				/>

				<TextField
					ariaLabel={Liferay.Language.get(
						'publication-description-placeholder'
					)}
					componentType="textarea"
					fieldValue={description}
					label="Publication Description"
					placeholderValue={Liferay.Language.get(
						'publication-description-placeholder'
					)}
					required={false}
				/>
			</CollapsablePanel>

			<CollapsablePanel
				helpTooltip="publication-collaborators-help"
				title="Publication Collaborators"
			></CollapsablePanel>

			<div className="button-holder">
				<ClayButton
					displayType="primary"
					id="saveButton"
					type="submit"
					value={saveButtonLabel}
				>
					{saveButtonLabel}
				</ClayButton>

				<ClayButton displayType="secondary" type="cancel">
					Cancel
				</ClayButton>
			</div>
		</div>
	);
}
