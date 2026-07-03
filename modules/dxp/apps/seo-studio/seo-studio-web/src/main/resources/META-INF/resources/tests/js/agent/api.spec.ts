/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EventSource} from 'eventsource';

import {
	createAgentInvocationEventSource,
	postAgentInvocation,
} from '../../../js/agent/api';

const mockFetch = jest.fn();

jest.mock('frontend-js-web', () => ({
	fetch: (...args: unknown[]) => mockFetch(...args),
}));

jest.mock('eventsource', () => ({EventSource: jest.fn()}));

const AUTHORIZATION_TOKEN = {
	accessToken: 'access-token-1',
	serviceURL: 'http://localhost:8080',
	userToken: 'user-token-1',
};

function mockAuthorizationTokenResponse(ok = true) {
	mockFetch.mockResolvedValueOnce({
		json: () => Promise.resolve(AUTHORIZATION_TOKEN),
		ok,
		statusText: 'Unauthorized',
	});
}

describe('postAgentInvocation', () => {
	beforeEach(() => {
		mockFetch.mockReset();
	});

	it('resolves with the response when the request succeeds', async () => {
		mockAuthorizationTokenResponse();

		const response = {ok: true};

		mockFetch.mockResolvedValueOnce(response);

		await expect(
			postAgentInvocation({
				agentExternalReferenceCode: 'L_TITLE_GENERATOR',
				context: {},
				sseEventSinkKey: 'sink-1',
			})
		).resolves.toBe(response);

		expect(mockFetch).toHaveBeenNthCalledWith(
			1,
			'/o/ai-hub-cell/v1.0/authorization-tokens',
			expect.objectContaining({method: 'POST'})
		);
		expect(mockFetch).toHaveBeenNthCalledWith(
			2,
			`${AUTHORIZATION_TOKEN.serviceURL}/o/ai-hub/v1.0/agent-instances`,
			expect.objectContaining({method: 'POST'})
		);
	});

	it('throws when the authorization token request fails', async () => {
		mockAuthorizationTokenResponse(false);

		await expect(
			postAgentInvocation({
				agentExternalReferenceCode: 'L_TITLE_GENERATOR',
				context: {},
				sseEventSinkKey: 'sink-1',
			})
		).rejects.toThrow('Unable to generate authorization token');
	});

	it('throws when the agent invocation request fails', async () => {
		mockAuthorizationTokenResponse();

		mockFetch.mockResolvedValueOnce({
			ok: false,
			statusText: 'Not Found',
		});

		await expect(
			postAgentInvocation({
				agentExternalReferenceCode: 'L_TITLE_GENERATOR',
				context: {},
				sseEventSinkKey: 'sink-1',
			})
		).rejects.toThrow('Unable to invoke agent');
	});
});

describe('createAgentInvocationEventSource', () => {
	beforeEach(() => {
		mockFetch.mockReset();

		(EventSource as unknown as jest.Mock).mockReset();
	});

	it('constructs an EventSource against the authorized service URL', async () => {
		mockAuthorizationTokenResponse();

		await createAgentInvocationEventSource();

		expect(EventSource).toHaveBeenCalledWith(
			`${AUTHORIZATION_TOKEN.serviceURL}/o/ai-hub/v1.0/agent-instances/subscribe`,
			expect.objectContaining({withCredentials: true})
		);
	});

	it('throws when the authorization token request fails', async () => {
		mockAuthorizationTokenResponse(false);

		await expect(createAgentInvocationEventSource()).rejects.toThrow(
			'Unable to generate authorization token'
		);
	});
});
