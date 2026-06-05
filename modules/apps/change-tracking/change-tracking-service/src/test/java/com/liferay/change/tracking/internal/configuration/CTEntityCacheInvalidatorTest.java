/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.configuration;

import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Dave Truong
 */
public class CTEntityCacheInvalidatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_ctPersistence = Mockito.mock(CTPersistence.class);
	}

	@Test
	public void testClearCacheAboveThresholdClearsClassRegion() {
		CTEntityCacheInvalidator ctEntityCacheInvalidator =
			_createCTEntityCacheInvalidator(2);

		ctEntityCacheInvalidator.clearCache(
			_ctPersistence, _createPrimaryKeys(3));

		Mockito.verify(
			_ctPersistence, Mockito.times(1)
		).clearCache();

		Mockito.verify(
			_ctPersistence, Mockito.never()
		).clearCache(
			Mockito.anySet()
		);
	}

	@Test
	public void testClearCacheAtThresholdClearsPrimaryKeys() {
		CTEntityCacheInvalidator ctEntityCacheInvalidator =
			_createCTEntityCacheInvalidator(2);

		Set<Serializable> primaryKeys = _createPrimaryKeys(2);

		ctEntityCacheInvalidator.clearCache(_ctPersistence, primaryKeys);

		Mockito.verify(
			_ctPersistence, Mockito.times(1)
		).clearCache(
			primaryKeys
		);

		Mockito.verify(
			_ctPersistence, Mockito.never()
		).clearCache();
	}

	@Test
	public void testClearCacheEmptyPrimaryKeysDoesNothing() {
		CTEntityCacheInvalidator ctEntityCacheInvalidator =
			_createCTEntityCacheInvalidator(2);

		ctEntityCacheInvalidator.clearCache(
			_ctPersistence, Collections.<Serializable>emptySet());

		Mockito.verifyNoInteractions(_ctPersistence);
	}

	@Test
	public void testClearCacheZeroThresholdClearsPrimaryKeys() {
		CTEntityCacheInvalidator ctEntityCacheInvalidator =
			_createCTEntityCacheInvalidator(0);

		Set<Serializable> primaryKeys = _createPrimaryKeys(100);

		ctEntityCacheInvalidator.clearCache(_ctPersistence, primaryKeys);

		Mockito.verify(
			_ctPersistence, Mockito.times(1)
		).clearCache(
			primaryKeys
		);

		Mockito.verify(
			_ctPersistence, Mockito.never()
		).clearCache();
	}

	private CTEntityCacheInvalidator _createCTEntityCacheInvalidator(
		int classPKThreshold) {

		CTEntityCacheInvalidator ctEntityCacheInvalidator =
			new CTEntityCacheInvalidator();

		ctEntityCacheInvalidator.activate(
			HashMapBuilder.<String, Object>put(
				"entityCacheClassPKThreshold", classPKThreshold
			).build());

		return ctEntityCacheInvalidator;
	}

	private Set<Serializable> _createPrimaryKeys(int size) {
		Set<Serializable> primaryKeys = new HashSet<>();

		for (long i = 0; i < size; i++) {
			primaryKeys.add(i);
		}

		return primaryKeys;
	}

	private CTPersistence<? extends CTModel<?>> _ctPersistence;

}