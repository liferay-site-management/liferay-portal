/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectSerializer} from '../utils/SerDes';

		import {LaunchEntry} from '../models/LaunchEntry';
		import {PageLaunchEntry} from '../models/PageLaunchEntry';

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
				 * @param launchEntryId
		 * @param headers Optional custom request headers
		 */
		public async getLaunchEntry(
						launchEntryId: number,
			headers?: {[name: string]: string},
		): Promise<{
				body: LaunchEntry;
			response: Response;
		}> {

			const path = this._basePath + "/launch-rest/v1.0/launch-entries/{launchEntryId}"
						.replace("{launchEntryId}",encodeURIComponent(launchEntryId))
				;

			const queryParameters: any = {};

						if (launchEntryId === null || launchEntryId === undefined) {
							throw new Error("Required parameter launchEntryId was null or undefined when calling getLaunchEntry.");
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

		/**
		 * 
				 * @param launchSetId
				 * @param filter
				 * @param page
				 * @param pageSize
				 * @param search
				 * @param sort
		 * @param headers Optional custom request headers
		 */
		public async getLaunchSetLaunchEntriesPage(
						launchSetId: number,
						filter?: string,
						page?: number,
						pageSize?: number,
						search?: string,
						sort?: string,
			headers?: {[name: string]: string},
		): Promise<{
				body: PageLaunchEntry;
			response: Response;
		}> {

			const path = this._basePath + "/launch-rest/v1.0/launch-sets/{launchSetId}/launch-entries"
						.replace("{launchSetId}",encodeURIComponent(launchSetId))
																								;

			const queryParameters: any = {};

						if (launchSetId === null || launchSetId === undefined) {
							throw new Error("Required parameter launchSetId was null or undefined when calling getLaunchSetLaunchEntriesPage.");
						}

						if (filter !== undefined) {
							queryParameters["filter"] = ObjectSerializer.serialize(filter, "string");
						}

						if (page !== undefined) {
							queryParameters["page"] = ObjectSerializer.serialize(page, "number");
						}

						if (pageSize !== undefined) {
							queryParameters["pageSize"] = ObjectSerializer.serialize(pageSize, "number");
						}

						if (search !== undefined) {
							queryParameters["search"] = ObjectSerializer.serialize(search, "string");
						}

						if (sort !== undefined) {
							queryParameters["sort"] = ObjectSerializer.serialize(sort, "string");
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
						return {body: ObjectSerializer.deserialize(await response.json(), "PageLaunchEntry"), response};
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