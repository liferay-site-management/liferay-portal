/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {invokeAgent} from '../../agent/invokeAgent';
import {TitleCandidate} from '../types/AutoFix';

const SEO_STUDIO_TITLE_GENERATOR_ERC = 'L_SEO_STUDIO_TITLE_GENERATOR';

// State is a Liferay workflow status: approved means fixed, pending means
// still open.

export const WORKFLOW_STATUS_APPROVED = 0;
export const WORKFLOW_STATUS_PENDING = 1;

// The only insight type the Title Generator agent knows how to fix.

export const MISSING_OR_EMPTY_TITLE_TAG_INSIGHT_TYPE_NAME =
	'missingOrEmptyTitleTag';

function parseCandidates(response: string): TitleCandidate[] | undefined {
	const braceMatch = response.match(/\{[\s\S]*\}/);
	const fenceMatch = response.match(/```(?:json)?\s*([\s\S]*?)```/);

	for (const candidate of [fenceMatch?.[1], response, braceMatch?.[0]]) {
		if (!candidate) {
			continue;
		}

		try {
			const candidates = JSON.parse(candidate)?.candidates;

			if (Array.isArray(candidates)) {
				return candidates.filter((item) => Boolean(item?.title));
			}
		}
		catch {
			continue;
		}
	}

	return undefined;
}

// The Title Generator agent returns a JSON object shaped
// {"candidates":[{"title","rationale"}]}, sometimes wrapped in a markdown
// code fence.

export async function generateTitleCandidates(
	pageContent: string,
	signal?: AbortSignal
): Promise<TitleCandidate[]> {
	const response = await invokeAgent({
		agentExternalReferenceCode: SEO_STUDIO_TITLE_GENERATOR_ERC,
		context: {pageContent},
		signal,
	});

	const candidates = parseCandidates(response);

	if (!Array.isArray(candidates)) {

		// Surface the agent's raw text (a quota or guardrail rejection
		// message, for example) rather than a generic error.

		throw new Error(response.trim());
	}

	return candidates;
}

// The SEO Studio backend signs the request with the connection credentials;
// the connection secret never reaches the browser.

export async function applyTitle({
	htmlTitle,
	pageURL,
}: {
	htmlTitle: string;
	pageURL: string;
}): Promise<void> {
	const body = new URLSearchParams();

	body.append('htmlTitle', htmlTitle);
	body.append('pageURL', pageURL);

	const response = await fetch('/o/seo-studio-auto-fix/apply-title', {
		body,
		headers: new Headers({
			'Accept': 'application/json',
			'Content-Type': 'application/x-www-form-urlencoded',
		}),
		method: 'POST',
	});

	if (!response.ok) {
		throw new Error('Unable to apply the title');
	}
}

export async function resolveInsight(scanInsightId: number): Promise<void> {
	const response = await fetch(
		`/o/seo-studio/scan-insights/${scanInsightId}`,
		{
			body: JSON.stringify({
				resolvedDate: new Date().toISOString(),
				state: WORKFLOW_STATUS_APPROVED,
			}),
			headers: new Headers({
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			}),
			method: 'PATCH',
		}
	);

	if (!response.ok) {
		throw new Error('Unable to mark the insight as resolved');
	}
}
