/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.service;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.seo.studio.constants.SEOStudioScanConstants;
import com.liferay.seo.studio.crawler.OrphanPagesDetectionCrawler;
import com.liferay.seo.studio.model.CrawlHit;

import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.fabric8.kubernetes.api.model.batch.v1.JobCondition;
import io.fabric8.kubernetes.api.model.batch.v1.JobStatus;

import java.net.URI;

import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author Brooke Dalton
 */
@Service
public class CrawlerJobStatusService {

	@Scheduled(fixedDelay = 60000)
	public void updateStatuses() {
		JSONArray itemsJSONArray = new JSONObject(
			_seoStudioService.fetchActiveScans()
		).optJSONArray(
			"items"
		);

		if (itemsJSONArray == null) {
			return;
		}

		for (Object scanObject : itemsJSONArray) {
			JSONObject scanJSONObject = (JSONObject)scanObject;

			long seoStudioScanId = scanJSONObject.getLong("id");

			try {
				String executionId = scanJSONObject.optString("executionId");

				if (Validator.isNull(executionId)) {
					continue;
				}

				Job job = _kubernetesJobService.getJob(executionId);

				String state = _getScanState(job);

				if (Validator.isNull(state) ||
					state.equals(scanJSONObject.optString("state"))) {

					continue;
				}

				if (state.equals(SEOStudioScanConstants.STATE_COMPLETED)) {
					String errorMessage = _detectOrphanPages(
						scanJSONObject, seoStudioScanId);

					if (Validator.isNotNull(errorMessage)) {
						_seoStudioService.updateScan(
							errorMessage, seoStudioScanId,
							SEOStudioScanConstants.STATE_FAILED);
					}
					else {
						_seoStudioService.updateScan(
							null, seoStudioScanId,
							SEOStudioScanConstants.STATE_COMPLETED);
					}
				}
				else if (state.equals(SEOStudioScanConstants.STATE_FAILED)) {
					_seoStudioService.updateScan(
						_getErrorMessage(job), seoStudioScanId,
						SEOStudioScanConstants.STATE_FAILED);
				}
			}
			catch (Exception exception) {
				_log.error(
					"Unable to update status of scan " + seoStudioScanId,
					exception);
			}
		}
	}

	private String _detectOrphanPages(
			JSONObject scanJSONObject, long seoStudioScanId)
		throws Exception {

		long seoStudioDomainId = scanJSONObject.getLong(
			"r_seoStudioDomainToSEOStudioScans_seoStudioDomainId");

		String domainJSON = _seoStudioService.fetchDomain(seoStudioDomainId);

		if (Validator.isNull(domainJSON)) {
			return "No domain was found for SEO Studio domain ID " +
				seoStudioDomainId;
		}

		List<CrawlHit> crawlHits = _seoStudioService.fetchCrawlHits(
			seoStudioDomainId);

		if (ListUtil.isEmpty(crawlHits)) {
			return "No crawl hits were found for SEO Studio domain ID " +
				seoStudioDomainId;
		}

		URI hostname = SEOStudioService.toCrawlURI(
			new JSONObject(
				domainJSON
			).getString(
				"hostname"
			));

		_orphanPagesDetectionCrawler.detect(
			scanJSONObject.getLong("r_accountToSEOStudioScans_accountEntryId"),
			crawlHits, hostname, seoStudioScanId);

		return null;
	}

	private String _getErrorMessage(Job job) {
		if (job == null) {
			return "Kubernetes job does not exist";
		}

		JobStatus jobStatus = job.getStatus();

		List<JobCondition> jobConditions = jobStatus.getConditions();

		if (ListUtil.isEmpty(jobConditions)) {
			return "Kubernetes job failed";
		}

		for (JobCondition jobCondition : jobConditions) {
			if (!Objects.equals(jobCondition.getType(), "Failed") ||
				!Objects.equals(jobCondition.getStatus(), "True")) {

				continue;
			}

			String errorMessage = jobCondition.getMessage();

			if (Validator.isNotNull(errorMessage)) {
				return errorMessage;
			}
		}

		return "Kubernetes job failed";
	}

	private String _getScanState(Job job) {
		if (job == null) {
			return SEOStudioScanConstants.STATE_FAILED;
		}

		JobStatus jobStatus = job.getStatus();

		if (jobStatus == null) {
			return null;
		}

		Integer active = jobStatus.getActive();

		if ((active != null) && (active > 0)) {
			return SEOStudioScanConstants.STATE_RUNNING;
		}

		Integer failed = jobStatus.getFailed();

		if ((failed != null) && (failed > 0)) {
			return SEOStudioScanConstants.STATE_FAILED;
		}

		Integer succeeded = jobStatus.getSucceeded();

		if ((succeeded != null) && (succeeded > 0)) {
			return SEOStudioScanConstants.STATE_COMPLETED;
		}

		return null;
	}

	private static final Log _log = LogFactory.getLog(
		CrawlerJobStatusService.class);

	@Autowired
	private KubernetesJobService _kubernetesJobService;

	@Autowired
	private OrphanPagesDetectionCrawler _orphanPagesDetectionCrawler;

	@Autowired
	private SEOStudioService _seoStudioService;

}