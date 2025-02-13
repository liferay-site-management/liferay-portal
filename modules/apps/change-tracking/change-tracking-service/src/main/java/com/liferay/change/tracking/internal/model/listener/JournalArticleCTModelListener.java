/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.model.listener;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gislayne Vitorino
 */
@Component(service = ModelListener.class)
public class JournalArticleCTModelListener
	extends BaseModelListener<JournalArticle> {

	@Override
	public void onAfterUpdate(
			JournalArticle originalJournalArticle,
			JournalArticle journalArticle)
		throws ModelListenerException {

		long ctCollectionId = journalArticle.getCtCollectionId();

		if (ctCollectionId != CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			try {
				JournalArticleResource journalArticleResource =
					journalArticle.getArticleResource();

				try (SafeCloseable safeCloseable =
						CTCollectionThreadLocal.
							setCTCollectionIdWithSafeCloseable(
								ctCollectionId)) {

					_journalArticleResourceLocalService.
						updateJournalArticleResource(journalArticleResource);
				}
			}
			catch (PortalException portalException) {
				throw new ModelListenerException(portalException);
			}
		}
	}

	@Reference
	private JournalArticleResourceLocalService
		_journalArticleResourceLocalService;

}