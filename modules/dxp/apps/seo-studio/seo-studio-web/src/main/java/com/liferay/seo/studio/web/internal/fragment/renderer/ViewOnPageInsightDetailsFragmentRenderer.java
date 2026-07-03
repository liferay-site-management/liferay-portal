/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.fragment.renderer;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.seo.studio.web.internal.constants.SEOStudioFDSNames;
import com.liferay.seo.studio.web.internal.constants.SEOStudioWebConstants;
import com.liferay.seo.studio.web.internal.display.context.ViewOnPageInsightDetailsDisplayContext;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noor Najjar
 */
@Component(service = FragmentRenderer.class)
public class ViewOnPageInsightDetailsFragmentRenderer
	extends BaseFragmentRenderer<ViewOnPageInsightDetailsDisplayContext> {

	@Override
	public String getCollectionKey() {
		return "sections";
	}

	@Override
	public String getLabel(Locale locale) {
		return language.get(locale, "insight-detail-view");
	}

	@Override
	protected ViewOnPageInsightDetailsDisplayContext getDisplayContext(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_ensureAIHubAccountMembership(
			themeDisplay.getCompanyId(), themeDisplay.getUserId());

		_ensureAIHubCellClientAccountMembership(themeDisplay.getCompanyId());

		JSONArray viewsJSONArray = fdsSerializer.serializeViews(
			SEOStudioFDSNames.AFFECTED_PAGES_SECTION, httpServletRequest);

		return new ViewOnPageInsightDetailsDisplayContext(
			httpServletRequest, language, themeDisplay, viewsJSONArray);
	}

	@Override
	protected String getJSPPath() {
		return "/on_page_insight_details_view.jsp";
	}

	private void _ensureAccountMembership(
		long companyId, long userId, String accountEntryExternalReferenceCode) {

		AccountEntry accountEntry =
			_accountEntryLocalService.fetchAccountEntryByExternalReferenceCode(
				accountEntryExternalReferenceCode, companyId);

		if ((accountEntry == null) ||
			_accountEntryUserRelLocalService.hasAccountEntryUserRel(
				accountEntry.getAccountEntryId(), userId)) {

			return;
		}

		try {
			_accountEntryUserRelLocalService.addAccountEntryUserRel(
				accountEntry.getAccountEntryId(), userId);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	private void _ensureAIHubAccountMembership(long companyId, long userId) {
		_ensureAccountMembership(companyId, userId, "L_AI_HUB");
		_ensureAccountMembership(companyId, userId, "L_SEO_STUDIO");
	}

	private void _ensureAIHubCellClientAccountMembership(long companyId) {
		User user = _userLocalService.fetchUserByScreenName(
			companyId,
			SEOStudioWebConstants.SCREEN_NAME_AI_HUB_CELL_SERVICE_ACCOUNT);

		if (user == null) {
			return;
		}

		_ensureAIHubAccountMembership(companyId, user.getUserId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewOnPageInsightDetailsFragmentRenderer.class);

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private UserLocalService _userLocalService;

}