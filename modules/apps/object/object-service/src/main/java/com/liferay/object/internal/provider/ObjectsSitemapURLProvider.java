/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.provider;

import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.site.configuration.manager.SitemapConfigurationManager;
import com.liferay.site.manager.SitemapManager;
import com.liferay.site.provider.SitemapURLProvider;
import com.liferay.site.provider.helper.SitemapURLProviderHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 */
@Component(service = SitemapURLProvider.class)
public class ObjectsSitemapURLProvider implements SitemapURLProvider {

	@Override
	public String getClassName() {
		return ObjectEntry.class.getName();
	}

	@Override
	public boolean isInclude(long companyId, Layout layout)
		throws PortalException {

		if (layout == null) {
			return false;
		}

		ObjectDefinition objectDefinition =
			_getObjectDefinitionFromLayoutPageTemplateEntry(companyId, layout);

		if (objectDefinition == null) {
			return false;
		}

		return _sitemapConfigurationManager.includeObjectsCompanyEnabled(
			companyId,
			String.valueOf(objectDefinition.getObjectDefinitionId()));
	}

	@Override
	public void visitLayout(
			Element element, String layoutUuid, LayoutSet layoutSet,
			ThemeDisplay themeDisplay)
		throws PortalException {

		Layout layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
			layoutUuid, layoutSet.getGroupId(), layoutSet.isPrivateLayout());

		if ((layout == null) || !layout.isTypeAssetDisplay() ||
			_sitemapURLProviderHelper.isExcludeLayoutFromSitemap(layout)) {

			return;
		}

		List<ObjectEntry> objectEntries = _getApprovedObjectEntries(layout);

		if (objectEntries.isEmpty()) {
			return;
		}

		ObjectDefinition objectDefinition =
			_getObjectDefinitionFromLayoutPageTemplateEntry(
				CompanyThreadLocal.getCompanyId(), layout);

		Set<Locale> objectDefinitionAvailableLocales = _getAvailableLocales(
			objectDefinition,
			_language.getAvailableLocales(themeDisplay.getScopeGroupId()));

		String url;

		Map<Locale, String> alternateURLs;

		UnicodeProperties typeSettingsUnicodeProperties =
			layout.getTypeSettingsProperties();

		for (ObjectEntry objectEntry : objectEntries) {
			url = _portal.getCanonicalURL(
				_getFriendlyURL(objectEntry, themeDisplay), themeDisplay,
				layout);

			alternateURLs = _portal.getAlternateURLs(
				url, themeDisplay, layout, objectDefinitionAvailableLocales);

			for (String alternateURL : alternateURLs.values()) {
				_sitemapManager.addURLElement(
					element, alternateURL, typeSettingsUnicodeProperties,
					objectEntry.getModifiedDate(), url, alternateURLs);
			}
		}
	}

	@Override
	public void visitLayoutSet(
			Element element, LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException {
	}

	private List<ObjectEntry> _getApprovedObjectEntries(Layout layout) {
		ObjectDefinition objectDefinition =
			_getObjectDefinitionFromLayoutPageTemplateEntry(
				CompanyThreadLocal.getCompanyId(), layout);

		if (objectDefinition == null) {
			return Collections.emptyList();
		}

		List<ObjectEntry> objectEntries =
			_objectEntryLocalService.getObjectEntries(
				0, objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		List<ObjectEntry> processedObjectEntries = new ArrayList<>();

		for (ObjectEntry objectEntry : objectEntries) {
			if (objectEntry.isApproved()) {
				processedObjectEntries.add(objectEntry);
			}
		}

		return processedObjectEntries;
	}

	private Set<Locale> _getAvailableLocales(
		ObjectDefinition objectDefinition, Set<Locale> siteAvailableLocales) {

		Set<Locale> availableLocales = new HashSet<>();

		if (SetUtil.isEmpty(siteAvailableLocales)) {
			return availableLocales;
		}

		for (String availableLanguageId :
				objectDefinition.getAvailableLanguageIds()) {

			Locale locale = LocaleUtil.fromLanguageId(availableLanguageId);

			if (siteAvailableLocales.contains(locale)) {
				availableLocales.add(locale);
			}
		}

		return availableLocales;
	}

	private String _getFriendlyURL(
		ObjectEntry entry, ThemeDisplay themeDisplay) {

		return themeDisplay.getPortalURL() +
			themeDisplay.getPathFriendlyURLPublic() +
				FriendlyURLResolverConstants.URL_SEPARATOR_OBJECT_ENTRY +
					entry.getExternalReferenceCode();
	}

	private ObjectDefinition _getObjectDefinitionFromLayoutPageTemplateEntry(
		long companyId, Layout layout) {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByPlid(layout.getPlid());

		if (layoutPageTemplateEntry == null) {
			return null;
		}

		return _objectDefinitionLocalService.fetchObjectDefinitionByClassName(
			companyId,
			_portal.getClassName(layoutPageTemplateEntry.getClassNameId()));
	}

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SitemapConfigurationManager _sitemapConfigurationManager;

	@Reference
	private SitemapManager _sitemapManager;

	@Reference
	private SitemapURLProviderHelper _sitemapURLProviderHelper;

}