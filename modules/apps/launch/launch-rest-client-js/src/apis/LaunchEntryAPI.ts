/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectSerializer} from '../utils/SerDes';

		import {LaunchEntry} from '../models/LaunchEntry';

/**
 * @author David Truong
 * @generated
 */

export class LaunchEntryAPI {
	protected _basePath: string;
	protected _defaultHeaders: any = {};

	constructor(basePath?: string) {
		if (basePath) {
			this._basePath = basePath;
		}
	}

	set defaultHeaders(defaultHeaders: any) {
		this._defaultHeaders = defaultHeaders;
	}

		/**
		 * 
				 * @param ctEntryId
		 * @param headers Optional custom request headers
		 */
		public async getLaunchEntry(
						ctEntryId: number,
			headers?: {[name: string]: string},
		): Promise<{
				body: LaunchEntry;
			response: Response;
		}> {

			const path = this._basePath + "/launch-rest/v1.0/launch-entries/{launchEntryId}"
						.replace("{ctEntryId}",encodeURIComponent(ctEntryId))
				;

			const queryParameters: any = {};

						if (ctEntryId === null || ctEntryId === undefined) {
							throw new Error("Required parameter ctEntryId was null or undefined when calling getLaunchEntry.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
					,headers || {}
					),
				method: "GET",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "LaunchEntry"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

}