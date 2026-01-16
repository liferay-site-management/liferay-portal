/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectSerializer} from '../utils/SerDes';

		import {LaunchSet} from '../models/LaunchSet';
		import {PageLaunchSet} from '../models/PageLaunchSet';

/**
 * @author David Truong
 * @generated
 */

export class LaunchSetAPI {
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
				 * @param launchSetId
		 * @param headers Optional custom request headers
		 */
		public async deleteLaunchSet(
						launchSetId: number,
			headers?: {[name: string]: string},
		): Promise<{
				body?: any;
			response: Response;
		}> {

			const path = this._basePath + "/launch-rest/v1.0/launch-sets/{launchSetId}"
						.replace("{launchSetId}",encodeURIComponent(launchSetId))
				;

			const queryParameters: any = {};

						if (launchSetId === null || launchSetId === undefined) {
							throw new Error("Required parameter launchSetId was null or undefined when calling deleteLaunchSet.");
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
				method: "DELETE",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: await response.json(), response};
					}
					else {
						return {body: await response.text(), response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

		/**
		 * 
				 * @param externalReferenceCode
		 * @param headers Optional custom request headers
		 */
		public async deleteLaunchSetByExternalReferenceCode(
						externalReferenceCode: string,
			headers?: {[name: string]: string},
		): Promise<{
				body?: any;
			response: Response;
		}> {

			const path = this._basePath + "/launch-rest/v1.0/launch-sets/by-external-reference-code/{externalReferenceCode}"
						.replace("{externalReferenceCode}",encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error("Required parameter externalReferenceCode was null or undefined when calling deleteLaunchSetByExternalReferenceCode.");
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
				method: "DELETE",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: await response.json(), response};
					}
					else {
						return {body: await response.text(), response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

		/**
		 * 
				 * @param launchSetId
		 * @param headers Optional custom request headers
		 */
		public async getLaunchSet(
						launchSetId: number,
			headers?: {[name: string]: string},
		): Promise<{
				body: LaunchSet;
			response: Response;
		}> {

			const path = this._basePath + "/launch-rest/v1.0/launch-sets/{launchSetId}"
						.replace("{launchSetId}",encodeURIComponent(launchSetId))
				;

			const queryParameters: any = {};

						if (launchSetId === null || launchSetId === undefined) {
							throw new Error("Required parameter launchSetId was null or undefined when calling getLaunchSet.");
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
						return {body: ObjectSerializer.deserialize(await response.json(), "LaunchSet"), response};
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
				 * @param externalReferenceCode
		 * @param headers Optional custom request headers
		 */
		public async getLaunchSetByExternalReferenceCode(
						externalReferenceCode: string,
			headers?: {[name: string]: string},
		): Promise<{
				body: LaunchSet;
			response: Response;
		}> {

			const path = this._basePath + "/launch-rest/v1.0/launch-sets/by-external-reference-code/{externalReferenceCode}"
						.replace("{externalReferenceCode}",encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error("Required parameter externalReferenceCode was null or undefined when calling getLaunchSetByExternalReferenceCode.");
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
						return {body: ObjectSerializer.deserialize(await response.json(), "LaunchSet"), response};
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
				 * @param filter
				 * @param page
				 * @param pageSize
				 * @param search
				 * @param sort
				 * @param status
		 * @param headers Optional custom request headers
		 */
		public async getLaunchSetsPage(
						filter?: string,
						page?: number,
						pageSize?: number,
						search?: string,
						sort?: string,
						status?: Array<number>,
			headers?: {[name: string]: string},
		): Promise<{
				body: PageLaunchSet;
			response: Response;
		}> {

			const path = this._basePath + "/launch-rest/v1.0/launch-sets"
																								;

			const queryParameters: any = {};

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

						if (status !== undefined) {
							queryParameters["status"] = ObjectSerializer.serialize(status, "Array<number>");
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
						return {body: ObjectSerializer.deserialize(await response.json(), "PageLaunchSet"), response};
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
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async patchLaunchSetWithContentType(
						launchSetId: number,
					requestBody:
							{
								parameters: {
										launchSet?: LaunchSet
								},
								type: "application/json"
							}
								|
							{
								parameters: {
										launchSet?: LaunchSet
								},
								type: "application/xml"
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: LaunchSet;
			response: Response;
		}> {
				let body;
						if (requestBody.type === "application/json") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.launchSet, "LaunchSet"));
						}
						if (requestBody.type === "application/xml") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.launchSet, "LaunchSet"));
						}

			const path = this._basePath + "/launch-rest/v1.0/launch-sets/{launchSetId}"
						.replace("{launchSetId}",encodeURIComponent(launchSetId))
				;

			const queryParameters: any = {};

						if (launchSetId === null || launchSetId === undefined) {
							throw new Error("Required parameter launchSetId was null or undefined when calling patchLaunchSet.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
					body: body,
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
								,{"Content-Type": requestBody.type}
					,headers || {}
					),
				method: "PATCH",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "LaunchSet"), response};
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
					 *  - Default method for JSON body
							 * @param launchSetId
						 * @param launchSet
					 */
					public async patchLaunchSet(
									launchSetId: number,
							launchSet?: LaunchSet,
						headers?: {[name: string]: string}
					): Promise<{
							body: LaunchSet;
						response: Response;
					}> {
						return this.patchLaunchSetWithContentType(
										launchSetId,
							{
								parameters: {
										launchSet: launchSet
								},
								type: "application/json"
							},
							headers
						);
					}
		/**
		 * 
				 * @param externalReferenceCode
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async patchLaunchSetByExternalReferenceCodeWithContentType(
						externalReferenceCode: string,
					requestBody:
							{
								parameters: {
										launchSet?: LaunchSet
								},
								type: "application/json"
							}
								|
							{
								parameters: {
										launchSet?: LaunchSet
								},
								type: "application/xml"
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: LaunchSet;
			response: Response;
		}> {
				let body;
						if (requestBody.type === "application/json") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.launchSet, "LaunchSet"));
						}
						if (requestBody.type === "application/xml") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.launchSet, "LaunchSet"));
						}

			const path = this._basePath + "/launch-rest/v1.0/launch-sets/by-external-reference-code/{externalReferenceCode}"
						.replace("{externalReferenceCode}",encodeURIComponent(externalReferenceCode))
				;

			const queryParameters: any = {};

						if (externalReferenceCode === null || externalReferenceCode === undefined) {
							throw new Error("Required parameter externalReferenceCode was null or undefined when calling patchLaunchSetByExternalReferenceCode.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
					body: body,
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
								,{"Content-Type": requestBody.type}
					,headers || {}
					),
				method: "PATCH",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "LaunchSet"), response};
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
					 *  - Default method for JSON body
							 * @param externalReferenceCode
						 * @param launchSet
					 */
					public async patchLaunchSetByExternalReferenceCode(
									externalReferenceCode: string,
							launchSet?: LaunchSet,
						headers?: {[name: string]: string}
					): Promise<{
							body: LaunchSet;
						response: Response;
					}> {
						return this.patchLaunchSetByExternalReferenceCodeWithContentType(
										externalReferenceCode,
							{
								parameters: {
										launchSet: launchSet
								},
								type: "application/json"
							},
							headers
						);
					}
		/**
		 * 
				 * @param launchSetId
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async putLaunchSetWithContentType(
						launchSetId: number,
					requestBody:
							{
								parameters: {
										launchSet?: LaunchSet
								},
								type: "application/json"
							}
								|
							{
								parameters: {
										launchSet?: LaunchSet
								},
								type: "application/xml"
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: LaunchSet;
			response: Response;
		}> {
				let body;
						if (requestBody.type === "application/json") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.launchSet, "LaunchSet"));
						}
						if (requestBody.type === "application/xml") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.launchSet, "LaunchSet"));
						}

			const path = this._basePath + "/launch-rest/v1.0/launch-sets/{launchSetId}"
						.replace("{launchSetId}",encodeURIComponent(launchSetId))
				;

			const queryParameters: any = {};

						if (launchSetId === null || launchSetId === undefined) {
							throw new Error("Required parameter launchSetId was null or undefined when calling putLaunchSet.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
					body: body,
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
								,{"Content-Type": requestBody.type}
					,headers || {}
					),
				method: "PUT",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "LaunchSet"), response};
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
					 *  - Default method for JSON body
							 * @param launchSetId
						 * @param launchSet
					 */
					public async putLaunchSet(
									launchSetId: number,
							launchSet?: LaunchSet,
						headers?: {[name: string]: string}
					): Promise<{
							body: LaunchSet;
						response: Response;
					}> {
						return this.putLaunchSetWithContentType(
										launchSetId,
							{
								parameters: {
										launchSet: launchSet
								},
								type: "application/json"
							},
							headers
						);
					}
}