/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

			import {Status} from './Status';

/**
 * @author David Truong
 * @generated
 */

	/**
	* Represents a set of content that will be released together.
	*/
	export class LaunchSet {
			"actions"?: {[key: string]: {[key: string]: string;};};
			"dateCreated"?: Date;
			"dateModified"?: Date;
			"description"?: string;
			"externalReferenceCode"?: string;
			"id"?: number;
			"name"?: string;
			"status"?: Status;

		static "discriminator": string | undefined = undefined;

	static "attributeTypeMap": Array<{
		baseName: string;
		name: string;
		type: string;
	}> = [
		{
			baseName: "actions",
			name: "actions",
			type: "{[key: string]: {[key: string]: string;};}",
		},
		{
			baseName: "dateCreated",
			name: "dateCreated",
			type: "Date",
		},
		{
			baseName: "dateModified",
			name: "dateModified",
			type: "Date",
		},
		{
			baseName: "description",
			name: "description",
			type: "string",
		},
		{
			baseName: "externalReferenceCode",
			name: "externalReferenceCode",
			type: "string",
		},
		{
			baseName: "id",
			name: "id",
			type: "number",
		},
		{
			baseName: "name",
			name: "name",
			type: "string",
		},
		{
			baseName: "status",
			name: "status",
			type: "Status",
		},
		];

		static getAttributeTypeMap() {
				return LaunchSet.attributeTypeMap;
		}
	}
