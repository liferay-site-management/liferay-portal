/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.score;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author David Truong
 */
public class CTCollectionScoreCacheTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_ctCollectionScoreCache = new CTCollectionScoreCache();

		_ctScoreCalculator = Mockito.mock(CTScoreCalculator.class);

		ReflectionTestUtil.setFieldValue(
			_ctCollectionScoreCache, "_ctScoreCalculator", _ctScoreCalculator);
	}

	@Test
	public void testSumScoreWithMultipleModelClassNameIds() {
		Mockito.when(
			_ctScoreCalculator.calculate(_MODEL_CLASS_NAME_ID_1)
		).thenReturn(
			4
		);

		Mockito.when(
			_ctScoreCalculator.calculate(_MODEL_CLASS_NAME_ID_2)
		).thenReturn(
			24
		);

		List<Long> modelClassNameIds = ListUtil.concat(
			Collections.nCopies(3, _MODEL_CLASS_NAME_ID_1),
			Collections.nCopies(2, _MODEL_CLASS_NAME_ID_2));

		int score = _sumScore(modelClassNameIds);

		Assert.assertEquals((4 * 3) + (24 * 2), score);
	}

	@Test
	public void testSumScoreWithNoRows() {
		Assert.assertEquals(0, _sumScore(Collections.emptyList()));
	}

	@Test
	public void testSumScoreWithSingleModelClassNameId() {
		Mockito.when(
			_ctScoreCalculator.calculate(_MODEL_CLASS_NAME_ID_1)
		).thenReturn(
			5
		);

		int score = _sumScore(Collections.nCopies(7, _MODEL_CLASS_NAME_ID_1));

		Assert.assertEquals(5 * 7, score);
	}

	private int _sumScore(List<Long> modelClassNameIds) {
		return ReflectionTestUtil.invoke(
			_ctCollectionScoreCache, "_sumScore", new Class<?>[] {List.class},
			(Object)modelClassNameIds);
	}

	private static final Long _MODEL_CLASS_NAME_ID_1 =
		RandomTestUtil.randomLong();

	private static final Long _MODEL_CLASS_NAME_ID_2 =
		RandomTestUtil.randomLong();

	private CTCollectionScoreCache _ctCollectionScoreCache;
	private CTScoreCalculator _ctScoreCalculator;

}