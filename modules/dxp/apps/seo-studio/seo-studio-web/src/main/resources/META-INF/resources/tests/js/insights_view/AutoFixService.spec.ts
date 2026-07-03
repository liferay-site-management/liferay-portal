/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	WORKFLOW_STATUS_APPROVED,
	applyTitle,
	generateTitleCandidates,
	resolveInsight,
} from '../../../js/insights_view/services/AutoFixService';

const mockFetch = jest.fn();
const mockInvokeAgent = jest.fn();

jest.mock('../../../js/agent/invokeAgent', () => ({
	invokeAgent: (...args: unknown[]) => mockInvokeAgent(...args),
}));

jest.mock('frontend-js-web', () => ({
	fetch: (...args: unknown[]) => mockFetch(...args),
}));

describe('generateTitleCandidates', () => {
	beforeEach(() => {
		mockInvokeAgent.mockReset();
	});

	it('parses candidates with their rationale', async () => {
		mockInvokeAgent.mockResolvedValue(
			JSON.stringify({
				candidates: [
					{rationale: 'r1', title: 'A'},
					{rationale: 'r2', title: 'B'},
				],
			})
		);

		const candidates = await generateTitleCandidates('content');

		expect(candidates).toHaveLength(2);
		expect(candidates[0]).toEqual({rationale: 'r1', title: 'A'});
	});

	it('parses candidates wrapped in a markdown code fence', async () => {
		mockInvokeAgent.mockResolvedValue(
			'```json\n' +
				JSON.stringify({
					candidates: [{rationale: 'r1', title: 'A'}],
				}) +
				'\n```'
		);

		const candidates = await generateTitleCandidates('content');

		expect(candidates).toHaveLength(1);
		expect(candidates[0]).toEqual({rationale: 'r1', title: 'A'});
	});

	it('parses candidates wrapped in a code fence with trailing prose', async () => {
		mockInvokeAgent.mockResolvedValue(
			'```json\n' +
				JSON.stringify({
					candidates: [{rationale: 'r1', title: 'A'}],
				}) +
				'\n```\nLet me know if you need more options!'
		);

		const candidates = await generateTitleCandidates('content');

		expect(candidates).toHaveLength(1);
		expect(candidates[0]).toEqual({rationale: 'r1', title: 'A'});
	});

	it('throws with the raw response text when the response is not JSON', async () => {
		mockInvokeAgent.mockResolvedValue(
			'You have exceeded your monthly token quota.'
		);

		await expect(generateTitleCandidates('content')).rejects.toThrow(
			'You have exceeded your monthly token quota.'
		);
	});

	it('filters out candidates without a title', async () => {
		mockInvokeAgent.mockResolvedValue(
			JSON.stringify({
				candidates: [{title: 'A'}, {rationale: 'no title'}],
			})
		);

		const candidates = await generateTitleCandidates('content');

		expect(candidates).toHaveLength(1);
	});

	it('throws when the response has no candidates array', async () => {
		mockInvokeAgent.mockResolvedValue(JSON.stringify({foo: 'bar'}));

		await expect(generateTitleCandidates('content')).rejects.toThrow();
	});

	it('invokes the title generator agent with the page content', async () => {
		mockInvokeAgent.mockResolvedValue(
			JSON.stringify({candidates: [{title: 'A'}]})
		);

		await generateTitleCandidates('my page content');

		expect(mockInvokeAgent).toHaveBeenCalledWith({
			agentExternalReferenceCode: 'L_SEO_STUDIO_TITLE_GENERATOR',
			context: {pageContent: 'my page content'},
		});
	});
});

describe('applyTitle', () => {
	beforeEach(() => {
		mockFetch.mockReset();
	});

	it('posts the title and page URL to the SEO Studio Auto Fix backend', async () => {
		mockFetch.mockResolvedValue({ok: true});

		await applyTitle({
			htmlTitle: 'New Title',
			pageURL: 'http://example.com/web/customer/products',
		});

		const [url, options] = mockFetch.mock.calls[0];

		expect(url).toBe('/o/seo-studio-auto-fix/apply-title');
		expect(options.method).toBe('POST');
		expect(options.body.get('htmlTitle')).toBe('New Title');
		expect(options.body.get('pageURL')).toBe(
			'http://example.com/web/customer/products'
		);
	});

	it('throws when the response is not ok', async () => {
		mockFetch.mockResolvedValue({ok: false});

		await expect(
			applyTitle({
				htmlTitle: 't',
				pageURL: 'http://example.com/web/c/p',
			})
		).rejects.toThrow();
	});
});

describe('resolveInsight', () => {
	beforeEach(() => {
		mockFetch.mockReset();
	});

	it('marks the scan insight approved through the SEO Studio REST API', async () => {
		mockFetch.mockResolvedValue({ok: true});

		await resolveInsight(123);

		const [url, options] = mockFetch.mock.calls[0];

		expect(url).toBe('/o/seo-studio/scan-insights/123');
		expect(options.method).toBe('PATCH');

		const body = JSON.parse(options.body);

		expect(body.state).toBe(WORKFLOW_STATUS_APPROVED);
		expect(body.resolvedDate).toEqual(expect.any(String));
	});

	it('throws when the response is not ok', async () => {
		mockFetch.mockResolvedValue({ok: false});

		await expect(resolveInsight(1)).rejects.toThrow();
	});
});
