/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResourceTable;
import com.liferay.journal.model.JournalArticleTable;
import com.liferay.journal.model.JournalFolderTable;
import com.liferay.journal.service.persistence.JournalArticleResourcePersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class JournalArticleResourceTableReferenceDefinition
	implements TableReferenceDefinition<JournalArticleResourceTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<JournalArticleResourceTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.resourcePermissionReference(
			JournalArticleResourceTable.INSTANCE.resourcePrimKey,
			JournalArticle.class
		).singleColumnReference(
			JournalArticleResourceTable.INSTANCE.resourcePrimKey,
			JournalArticleTable.INSTANCE.resourcePrimKey
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<JournalArticleResourceTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			JournalArticleResourceTable.INSTANCE
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				JournalFolderTable.INSTANCE
			).innerJoinON(
				JournalArticleResourceTable.INSTANCE,
				JournalArticleResourceTable.INSTANCE.groupId.eq(
					JournalFolderTable.INSTANCE.groupId)
			).innerJoinON(
				JournalArticleTable.INSTANCE,
				JournalArticleTable.INSTANCE.folderId.eq(
					JournalFolderTable.INSTANCE.folderId
				).and(
					JournalArticleResourceTable.INSTANCE.resourcePrimKey.eq(
						JournalArticleTable.INSTANCE.resourcePrimKey)
				)
			)
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _journalArticleResourcePersistence;
	}

	@Override
	public JournalArticleResourceTable getTable() {
		return JournalArticleResourceTable.INSTANCE;
	}

	@Reference
	private JournalArticleResourcePersistence
		_journalArticleResourcePersistence;

}