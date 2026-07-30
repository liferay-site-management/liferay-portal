/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.definition.setting.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectDefinitionSetting;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Cheryl Tang
 */
public class ObjectDefinitionSettingUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testIsEnabled() {
		String name = RandomTestUtil.randomString();

		long objectDefinitionId = RandomTestUtil.randomLong();

		ObjectDefinition objectDefinition = _mockObjectDefinition(
			objectDefinitionId, false);

		Assert.assertTrue(
			ObjectDefinitionSettingUtil.isEnabled(
				name, objectDefinition, Collections.emptyMap()));

		objectDefinition = _mockObjectDefinition(objectDefinitionId, true);

		Assert.assertFalse(
			ObjectDefinitionSettingUtil.isEnabled(
				name, objectDefinition, Collections.emptyMap()));

		Assert.assertFalse(
			ObjectDefinitionSettingUtil.isEnabled(
				name, objectDefinition,
				_getObjectDefinitionSettingsMap(
					name, objectDefinitionId, StringPool.FALSE)));

		Assert.assertTrue(
			ObjectDefinitionSettingUtil.isEnabled(
				name, objectDefinition,
				_getObjectDefinitionSettingsMap(
					name, objectDefinitionId, StringPool.TRUE)));

		// Object definition settings map built for a different setting

		Assert.assertFalse(
			ObjectDefinitionSettingUtil.isEnabled(
				name, objectDefinition,
				_getObjectDefinitionSettingsMap(
					RandomTestUtil.randomString(), objectDefinitionId,
					StringPool.TRUE)));
	}

	private Map<Long, ObjectDefinitionSetting> _getObjectDefinitionSettingsMap(
		String name, long objectDefinitionId, String value) {

		ObjectDefinitionSetting objectDefinitionSetting = Mockito.mock(
			ObjectDefinitionSetting.class);

		Mockito.when(
			objectDefinitionSetting.getName()
		).thenReturn(
			name
		);

		Mockito.when(
			objectDefinitionSetting.getValue()
		).thenReturn(
			value
		);

		return Collections.singletonMap(
			objectDefinitionId, objectDefinitionSetting);
	}

	private ObjectDefinition _mockObjectDefinition(
		long objectDefinitionId, boolean system) {

		ObjectDefinition objectDefinition = Mockito.mock(
			ObjectDefinition.class);

		Mockito.when(
			objectDefinition.getObjectDefinitionId()
		).thenReturn(
			objectDefinitionId
		);

		Mockito.when(
			objectDefinition.isSystem()
		).thenReturn(
			system
		);

		return objectDefinition;
	}

}