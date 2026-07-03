/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {
	applyTitle,
	generateTitleCandidates,
	resolveInsight,
} from './services/AutoFixService';
import {ScanInsightItem, TitleCandidate} from './types/AutoFix';

import './AutoFixPanel.scss';

function getPageContent(item: ScanInsightItem): string {
	const page = item.r_seoStudioPageToSEOStudioScanInsights_seoStudioPage;

	return [
		page?.title ? `Title: ${page.title}` : '',
		page?.pageURL ? `URL: ${page.pageURL}` : '',
		page?.type ? `Type: ${page.type}` : '',
	]
		.filter(Boolean)
		.join('\n');
}

function getPageName(item: ScanInsightItem): string {
	const page = item.r_seoStudioPageToSEOStudioScanInsights_seoStudioPage;

	return page?.title || page?.pageURL || '';
}

export default function AutoFixPanel({
	item,
	onClose,
	onResolved,
}: {
	item: ScanInsightItem;
	onClose: () => void;
	onResolved: () => void;
}) {
	const [applying, setApplying] = useState(false);
	const [candidates, setCandidates] = useState<TitleCandidate[]>([]);
	const [generating, setGenerating] = useState(true);

	const pageName = getPageName(item);

	useEffect(() => {
		const controller = new AbortController();

		setGenerating(true);

		generateTitleCandidates(getPageContent(item), controller.signal)
			.then((titleCandidates) => {
				setCandidates(titleCandidates);
				setGenerating(false);
			})
			.catch((error) => {
				if (controller.signal.aborted) {
					return;
				}

				setGenerating(false);

				openToast({
					message:
						error?.message ||
						Liferay.Language.get(
							'unable-to-generate-title-suggestions'
						),
					type: 'danger',
				});
			});

		return () => {
			controller.abort();
		};
	}, [item]);

	const handleApply = async (htmlTitle: string) => {
		const pageURL =
			item.r_seoStudioPageToSEOStudioScanInsights_seoStudioPage?.pageURL;

		if (!pageURL) {
			openToast({
				message: Liferay.Language.get('unable-to-apply-the-title'),
				type: 'danger',
			});

			return;
		}

		setApplying(true);

		try {
			await applyTitle({htmlTitle, pageURL});
		}
		catch {
			setApplying(false);

			openToast({
				message: Liferay.Language.get('unable-to-apply-the-title'),
				type: 'danger',
			});

			return;
		}

		try {
			await resolveInsight(item.id);
		}
		catch {
			setApplying(false);

			openToast({
				message: Liferay.Language.get(
					'the-title-tag-was-applied-but-the-insight-could-not-be-marked-as-resolved'
				),
				type: 'danger',
			});

			return;
		}

		openToast({
			message: Liferay.Language.get(
				'the-title-tag-was-applied-and-the-insight-was-resolved'
			),
			type: 'success',
		});

		onResolved();
		onClose();
	};

	return (
		<div className="seo-studio-auto-fix-panel">
			<div className="seo-studio-auto-fix-panel-header">
				<h4 className="mb-0">{Liferay.Language.get('ai-assistant')}</h4>

				<ClayButton
					aria-label={Liferay.Language.get('close')}
					borderless
					displayType="secondary"
					monospaced
					onClick={onClose}
				>
					<ClayIcon symbol="times" />
				</ClayButton>
			</div>

			<div className="seo-studio-auto-fix-panel-body">
				<div className="seo-studio-auto-fix-message seo-studio-auto-fix-message-user">
					{sub(
						Liferay.Language.get(
							'help-me-create-a-title-tag-for-the-page-x'
						),
						pageName
					)}
				</div>

				{generating && (
					<div className="seo-studio-auto-fix-generating seo-studio-auto-fix-message seo-studio-auto-fix-message-assistant">
						<span
							aria-hidden="true"
							className="loading-animation loading-animation-secondary mr-2"
						/>

						{Liferay.Language.get('generating')}
					</div>
				)}

				{!generating && !!candidates.length && (
					<div className="seo-studio-auto-fix-message seo-studio-auto-fix-message-assistant">
						<p className="mb-3">
							{Liferay.Language.get(
								'here-are-some-optimized-title-options-for-this-page'
							)}
						</p>

						{candidates.map((candidate, index) => (
							<div
								className="seo-studio-auto-fix-candidate"
								key={index}
							>
								<p className="font-weight-semi-bold mb-1">
									{candidate.title}
								</p>

								{candidate.rationale && (
									<p className="text-2 text-secondary">
										{candidate.rationale}
									</p>
								)}

								<ClayButton
									disabled={applying}
									displayType="secondary"
									onClick={() => handleApply(candidate.title)}
									small
								>
									{sub(
										Liferay.Language.get('apply-option-x'),
										String(index + 1)
									)}
								</ClayButton>
							</div>
						))}
					</div>
				)}

				{!generating && !candidates.length && (
					<div className="seo-studio-auto-fix-message seo-studio-auto-fix-message-assistant">
						{Liferay.Language.get('no-suggestions-were-generated')}
					</div>
				)}
			</div>
		</div>
	);
}
