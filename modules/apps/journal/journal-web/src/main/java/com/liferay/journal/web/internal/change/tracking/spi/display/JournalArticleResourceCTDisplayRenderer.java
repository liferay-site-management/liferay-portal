/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.change.tracking.spi.display.context.DisplayContext;
import com.liferay.diff.exception.CompareVersionsException;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gislayne Vitorino
 */
@Component(service = CTDisplayRenderer.class)
public class JournalArticleResourceCTDisplayRenderer
	extends BaseCTDisplayRenderer<JournalArticleResource> {

	@Override
	public JournalArticleResource fetchLatestVersionedModel(
		JournalArticleResource journalArticleResource) {

		return _journalArticleResourceLocalService.fetchJournalArticleResource(
			journalArticleResource.getResourcePrimKey());
	}

	@Override
	public String[] getAvailableLanguageIds(
		JournalArticleResource journalArticleResource) {

		try {
			return _getLatestJournalArticle(
				journalArticleResource
			).getAvailableLanguageIds();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return null;
		}
	}

	@Override
	public String getDefaultLanguageId(
		JournalArticleResource journalArticleResource) {

		try {
			return _getLatestJournalArticle(
				journalArticleResource
			).getDefaultLanguageId();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return null;
		}
	}

	@Override
	public String getEditURL(
			HttpServletRequest httpServletRequest,
			JournalArticleResource journalArticleResource)
		throws PortalException {

		JournalArticle journalArticle = _getLatestJournalArticle(
			journalArticleResource);

		Group group = _groupLocalService.getGroup(journalArticle.getGroupId());

		if (group.isCompany()) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, group, JournalPortletKeys.JOURNAL, 0, 0,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/journal/edit_article"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setParameter(
			"articleId", journalArticle.getArticleId()
		).setParameter(
			"groupId", journalArticle.getGroupId()
		).setParameter(
			"version", journalArticle.getVersion()
		).buildString();
	}

	@Override
	public Class<JournalArticleResource> getModelClass() {
		return JournalArticleResource.class;
	}

	@Override
	public String getTitle(
			Locale locale, JournalArticleResource journalArticleResource)
		throws PortalException {

		return _getLatestJournalArticle(
			journalArticleResource
		).getTitle();
	}

	@Override
	public String getVersionName(
		JournalArticleResource journalArticleResource) {

		try {
			long ctCollectionId = journalArticleResource.getCtCollectionId();

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						ctCollectionId)) {

				JournalArticle journalArticle = _getLatestJournalArticle(
					journalArticleResource);

				return String.valueOf(journalArticle.getVersion());
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return null;
		}
	}

	@Override
	public String renderPreview(
			DisplayContext<JournalArticleResource> displayContext)
		throws Exception {

		HttpServletRequest httpServletRequest =
			displayContext.getHttpServletRequest();

		PortletRequest portletRequest =
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);
		PortletResponse portletResponse =
			(PortletResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		PortletRequestModel portletRequestModel = new PortletRequestModel(
			portletRequest, portletResponse);

		JournalArticleResource journalArticleResource =
			displayContext.getModel();

		JournalArticle journalArticle = _getLatestJournalArticle(
			journalArticleResource);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!_journalArticleLocalService.isRenderable(
				journalArticle, portletRequestModel, themeDisplay)) {

			throw new CompareVersionsException(journalArticle.getVersion());
		}

		JournalArticleDisplay journalArticleDisplay =
			_journalArticleLocalService.getArticleDisplay(
				journalArticle, null, Constants.VIEW,
				_language.getLanguageId(displayContext.getLocale()), 1,
				portletRequestModel, themeDisplay);

		return journalArticleDisplay.getContent();
	}

	@Override
	public boolean showPreviewDiff() {
		return true;
	}

	@Override
	protected void buildDisplay(
		DisplayBuilder<JournalArticleResource> displayBuilder) {

		JournalArticleResource journalArticleResource =
			displayBuilder.getModel();

		JournalArticle journalArticle = null;

		try {
			journalArticle = _getLatestJournalArticle(journalArticleResource);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		JournalArticle finalJournalArticle = journalArticle;

		displayBuilder.display(
			"name", journalArticle.getTitle(displayBuilder.getLocale())
		).display(
			"description",
			journalArticle.getDescription(displayBuilder.getLocale())
		).display(
			"created-by",
			() -> {
				String userName = finalJournalArticle.getUserName();

				if (Validator.isNotNull(userName)) {
					return userName;
				}

				return null;
			}
		).display(
			"create-date", journalArticle.getCreateDate()
		).display(
			"last-modified", journalArticle.getModifiedDate()
		).display(
			"version", journalArticle.getVersion()
		).display(
			"structure",
			() -> {
				DDMStructure ddmStructure =
					finalJournalArticle.getDDMStructure();

				return ddmStructure.getName(displayBuilder.getLocale());
			}
		).display(
			"template",
			() -> {
				DDMTemplate ddmTemplate = finalJournalArticle.getDDMTemplate();

				return ddmTemplate.getName(displayBuilder.getLocale());
			}
		);
	}

	private JournalArticle _getLatestJournalArticle(
			JournalArticleResource journalArticleResource)
		throws PortalException {

		return _journalArticleLocalService.getArticle(
			journalArticleResource.getLatestArticlePK());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleResourceCTDisplayRenderer.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalArticleResourceLocalService
		_journalArticleResourceLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}