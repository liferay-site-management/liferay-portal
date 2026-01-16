/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */


/**
 * @author David Truong
 * @generated
 */

	export class LaunchEntry {
			"actions"?: {[key: string]: {[key: string]: string;};};
			"classNameId"?: number;
			"classPK"?: number;
			"dateCreated"?: Date;
			"dateModified"?: Date;
			"id"?: number;
			"launchSetId"?: number;

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
			baseName: "classNameId",
			name: "classNameId",
			type: "number",
		},
		{
			baseName: "classPK",
			name: "classPK",
			type: "number",
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
			baseName: "id",
			name: "id",
			type: "number",
		},
		{
			baseName: "launchSetId",
			name: "launchSetId",
			type: "number",
		},
		];

		static getAttributeTypeMap() {
				return LaunchEntry.attributeTypeMap;
		}
	}
