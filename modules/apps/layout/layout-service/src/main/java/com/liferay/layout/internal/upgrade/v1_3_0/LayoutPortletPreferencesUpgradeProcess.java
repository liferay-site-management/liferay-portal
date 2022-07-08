/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.internal.upgrade.v1_3_0;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author Cheryl Tang
 */
public class LayoutPortletPreferencesUpgradeProcess extends UpgradeProcess {

	public LayoutPortletPreferencesUpgradeProcess(
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService) {

		_fragmentEntryLinkLocalService = fragmentEntryLinkLocalService;
	}

	/**
	 * This upgrade deletes orphaned PortletPreferences created by non-instanceable widgets failing to be fully removed (see LPS-156786). The upgrade does the following:
	 * Gets all FragmentEntryLinks associated with any non-private 'content'-type Layout,
	 * add PortletIds associated with a FragmentEntryLink (aka not-orphaned) to a map keyed by plid,
	 * gets PortletPreferences for all plid keys in plidPortletIdsMap, ther
	 * delete PortletPreferences that are not associated with a portlet that exists on a Layout
	 *
	 * @throws Exception
	 */
	@Override
	protected void doUpgrade() throws Exception {
		List<FragmentEntryLink> fragmentEntryLinkList = new ArrayList<>();

		Map<Long, HashSet<String>> plidPortletIdsMap = new HashMap<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select Layout.plid, Layout.classNameId, Layout.classPK ",
					"from Layout where Layout.privateLayout = ", false,
					" and Layout.type_ = '", LayoutConstants.TYPE_CONTENT,
					"' and ctCollectionId = ",
					CTConstants.CT_COLLECTION_ID_PRODUCTION));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				plidPortletIdsMap.put(
					resultSet.getLong("plid"), new HashSet<>());
			}
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select FragmentEntryLink.fragmentEntryLinkId, ",
					"FragmentEntryLink.plid from FragmentEntryLink join ",
					"Layout on FragmentEntryLink.plid = Layout.plid and ",
					"FragmentEntryLink.groupId = Layout.groupId where ",
					"Layout.privateLayout = ", false, " and Layout.type_ = '",
					LayoutConstants.TYPE_CONTENT, "'"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				fragmentEntryLinkList.add(
					_fragmentEntryLinkLocalService.getFragmentEntryLink(
						resultSet.getLong("fragmentEntryLinkId")));
			}
		}

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinkList) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				fragmentEntryLink.getEditableValues());
			String html = fragmentEntryLink.getHtml();

			if (jsonObject.has("portletId")) {
				plidPortletIdsMap.merge(
					fragmentEntryLink.getPlid(), new HashSet<>(),
					(val1, val2) -> {
						val1.add((String)jsonObject.get("portletId"));

						return val1;
					});
			}
			else if (html.contains("lfr-widget-")) {
				plidPortletIdsMap.merge(
					fragmentEntryLink.getPlid(), new HashSet<>(),
					(val1, val2) -> {
						val1.addAll(
							_getPortletIdsFromHtml(fragmentEntryLink, html));

						return val1;
					});
			}
		}

		List<long[]> orphanPortletPreferences = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select PortletPreferences.portletPreferencesId, ",
					"PortletPreferences.portletId, PortletPreferences.plid, ",
					"PortletPreferences.ctCollectionId from ",
					"PortletPreferences where PortletPreferences.plid in (",
					StringUtil.merge(plidPortletIdsMap.keySet(), ","), ")"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			List<String> basePortletIds = new ArrayList<>(
				Arrays.asList(
					"com_liferay_portal_search_web_search_bar_portlet_" +
						"SearchBarPortlet_INSTANCE_templateSearch",
					"com_liferay_product_navigation_user_personal_bar_web_" +
						"portlet_ProductNavigationUserPersonalBarPortlet",
					"com_liferay_site_navigation_menu_web_portlet_" +
						"SiteNavigationMenuPortlet",
					"com_liferay_product_navigation_product_menu_web_portlet_" +
						"ProductMenuPortlet",
					"com_liferay_layout_content_page_editor_web_internal_" +
						"portlet_ContentPageEditorPortlet",
					"com_liferay_layout_content_page_editor_web_internal_" +
						"portlet_ContentPageToolbarPortlet"));

			while (resultSet.next()) {
				String portletId = resultSet.getString("portletId");

				HashSet<String> layoutPortletIds = plidPortletIdsMap.get(
					resultSet.getLong("plid"));

				if (!basePortletIds.contains(portletId) &&
					!layoutPortletIds.contains(portletId)) {

					orphanPortletPreferences.add(
						new long[] {
							resultSet.getLong("ctCollectionId"),
							resultSet.getLong("portletPreferencesId")
						});
				}
			}
		}

		if (!orphanPortletPreferences.isEmpty()) {
			StringBundler sb = new StringBundler(
				orphanPortletPreferences.size());

			for (int i = 0; i < orphanPortletPreferences.size(); i++) {
				long[] orphan = orphanPortletPreferences.get(i);

				long ctCollectionId = orphan[0];
				long portletPreferencesId = orphan[1];

				if (i > 0) {
					sb.append(StringPool.COMMA);
				}

				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(ctCollectionId);
				sb.append(StringPool.COMMA);
				sb.append(portletPreferencesId);
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			runSQL(
				StringBundler.concat(
					"delete from PortletPreferences where (ctCollectionId, ",
					"portletPreferencesId) in (", sb, ")"));
		}
	}

	private List<String> _getPortletIdsFromHtml(
		FragmentEntryLink fragmentEntryLink, String html) {

		List<String> portletIds = new ArrayList<>();

		Document document = Jsoup.parseBodyFragment(html);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);

		document.outputSettings(outputSettings);

		for (Element element : document.select("*")) {
			String tagName = element.tagName();

			if (!StringUtil.startsWith(tagName, "lfr-widget-")) {
				continue;
			}

			String alias = StringUtil.removeSubstring(tagName, "lfr-widget-");

			String portletName = _portletNamesMap.get(alias);

			if (Validator.isNull(portletName)) {
				continue;
			}

			String portletId = PortletIdCodec.encode(
				PortletIdCodec.decodePortletName(portletName),
				PortletIdCodec.decodeUserId(portletName),
				fragmentEntryLink.getNamespace() + element.attr("id"));

			portletIds.add(portletId);
		}

		return portletIds;
	}

	private final FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;
	private final HashMap<String, String> _portletNamesMap = HashMapBuilder.put(
		"asset-list",
		"com_liferay_asset_publisher_web_portlet_AssetPublisherPortlet"
	).put(
		"breadcrumb",
		"com_liferay_site_navigation_breadcrumb_web_portlet_" +
			"SiteNavigationBreadcrumbPortlet"
	).put(
		"categories-nav",
		"com_liferay_asset_categories_navigation_web_portlet_" +
			"AssetCategoriesNavigationPortlet"
	).put(
		"dynamic-data-list",
		"com_liferay_dynamic_data_lists_web_portlet_DDLDisplayPortlet"
	).put(
		"form",
		"com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormPortlet"
	).put(
		"iframe", "com_liferay_iframe_web_portlet_IFramePortlet"
	).put(
		"media-gallery",
		"com_liferay_document_library_web_portlet_IGDisplayPortlet"
	).put(
		"nav",
		"com_liferay_site_navigation_menu_web_portlet_SiteNavigationMenuPortlet"
	).put(
		"related-assets",
		"com_liferay_asset_publisher_web_portlet_RelatedAssetsPortlet"
	).put(
		"rss", "com_liferay_rss_web_portlet_RSSPortlet"
	).put(
		"search-bar",
		"com_liferay_portal_search_web_search_bar_portlet_SearchBarPortlet"
	).put(
		"site-map",
		"com_liferay_site_navigation_site_map_web_portlet_" +
			"SiteNavigationSiteMapPortlet"
	).put(
		"tag-cloud",
		"com_liferay_asset_tags_navigation_web_portlet_AssetTagsCloudPortlet"
	).put(
		"tags-nav",
		"com_liferay_asset_tags_navigation_web_portlet_" +
			"AssetTagsNavigationPortlet"
	).put(
		"web-content",
		"com_liferay_journal_content_web_portlet_JournalContentPortlet"
	).build();

}