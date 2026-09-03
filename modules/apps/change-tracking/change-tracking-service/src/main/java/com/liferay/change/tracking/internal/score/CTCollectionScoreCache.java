/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.score;

import com.liferay.change.tracking.model.CTEntryTable;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = CTCollectionScoreCache.class)
public class CTCollectionScoreCache {

	public void decrement(long ctCollectionId, int score) {
		_updateScore(ctCollectionId, -1 * score);
	}

	public int getScore(long ctCollectionId) {
		Integer score = _portalCache.get(ctCollectionId);

		if (score != null) {
			return score;
		}

		Object lock = _getLock(ctCollectionId);

		synchronized (lock) {
			score = _portalCache.get(ctCollectionId);

			if (score != null) {
				return score;
			}

			score = _calculateScore(ctCollectionId);

			_portalCache.put(ctCollectionId, score);
		}

		return score;
	}

	public String getSizeClassification(int score) {
		if (score > _LARGE_THRESHOLD) {
			return "large";
		}
		else if (score > _MEDIUM_THRESHOLD) {
			return "medium";
		}

		return "small";
	}

	public void increment(long ctCollectionId, int score) {
		_updateScore(ctCollectionId, score);
	}

	public void remove(long ctCollectionId) {
		_portalCache.remove(ctCollectionId);

		_locks.remove(ctCollectionId);
	}

	public void setScore(long ctCollectionId, int score) {
		_portalCache.put(ctCollectionId, score);
	}

	@Activate
	protected void activate() {
		_portalCache = (PortalCache<Long, Integer>)_multiVMPool.getPortalCache(
			CTCollectionScoreCache.class.getName());
	}

	private int _calculateScore(long ctCollectionId) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			List<Long> modelClassNameIds = _ctEntryLocalService.dslQuery(
				DSLQueryFactoryUtil.select(
					CTEntryTable.INSTANCE.modelClassNameId
				).from(
					CTEntryTable.INSTANCE
				).where(
					CTEntryTable.INSTANCE.ctCollectionId.eq(ctCollectionId)
				));

			return _sumScore(modelClassNameIds);
		}
	}

	private Object _getLock(long ctCollectionId) {
		return _locks.computeIfAbsent(ctCollectionId, key -> new Object());
	}

	private int _sumScore(List<Long> modelClassNameIds) {
		int score = 0;

		for (long modelClassNameId : modelClassNameIds) {
			score += _ctScoreCalculator.calculate(modelClassNameId);
		}

		return score;
	}

	private void _updateScore(long ctCollectionId, int delta) {
		Object lock = _getLock(ctCollectionId);

		synchronized (lock) {
			Integer cachedScore = _portalCache.get(ctCollectionId);

			int score;

			if (cachedScore == null) {

				// The on-demand calculation already reflects the CTEntry that
				// triggered this update, so no delta is added on top of it.

				score = _calculateScore(ctCollectionId);
			}
			else {
				score = cachedScore + delta;

				if (score < 0) {
					score = 0;
				}
			}

			_portalCache.put(ctCollectionId, score);
		}
	}

	private static final int _LARGE_THRESHOLD = 40000;

	private static final int _MEDIUM_THRESHOLD = 15000;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private CTScoreCalculator _ctScoreCalculator;

	private final ConcurrentHashMap<Long, Object> _locks =
		new ConcurrentHashMap<>();

	@Reference
	private MultiVMPool _multiVMPool;

	private PortalCache<Long, Integer> _portalCache;

}