/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';

import '../../css/categorization/Categorization.scss';
import CategorizationHome from './CategorizationHome';
import EditVocabulary from './vocabulary/EditVocabulary';

const SECTIONS = {
	EDIT_VOCABULARY: 'edit-vocabulary',
	HOME: 'home',
};

export default function CategorizationMainView(){

	const [activeSection, setActiveSection] = useState(
		SECTIONS.HOME
	);

	return (
		<div className="categorization-section">
			{activeSection === SECTIONS.HOME && (
				<CategorizationHome onChangeActiveSection={setActiveSection} />
			)}

			{activeSection === SECTIONS.EDIT_VOCABULARY && (
				<EditVocabulary
					onChangeActiveSection={setActiveSection}
				/>
			)}
		</div>
	);
}
