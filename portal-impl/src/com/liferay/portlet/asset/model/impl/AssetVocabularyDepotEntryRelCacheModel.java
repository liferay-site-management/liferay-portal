/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.model.impl;

import com.liferay.asset.kernel.model.AssetVocabularyDepotEntryRel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AssetVocabularyDepotEntryRel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetVocabularyDepotEntryRelCacheModel
	implements CacheModel<AssetVocabularyDepotEntryRel>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AssetVocabularyDepotEntryRelCacheModel)) {
			return false;
		}

		AssetVocabularyDepotEntryRelCacheModel
			assetVocabularyDepotEntryRelCacheModel =
				(AssetVocabularyDepotEntryRelCacheModel)object;

		if ((assetVocabularyDepotEntryRelId ==
				assetVocabularyDepotEntryRelCacheModel.
					assetVocabularyDepotEntryRelId) &&
			(mvccVersion ==
				assetVocabularyDepotEntryRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, assetVocabularyDepotEntryRelId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", assetVocabularyDepotEntryRelId=");
		sb.append(assetVocabularyDepotEntryRelId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", assetVocabularyId=");
		sb.append(assetVocabularyId);
		sb.append(", depotEntryId=");
		sb.append(depotEntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetVocabularyDepotEntryRel toEntityModel() {
		AssetVocabularyDepotEntryRelImpl assetVocabularyDepotEntryRelImpl =
			new AssetVocabularyDepotEntryRelImpl();

		assetVocabularyDepotEntryRelImpl.setMvccVersion(mvccVersion);
		assetVocabularyDepotEntryRelImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			assetVocabularyDepotEntryRelImpl.setUuid("");
		}
		else {
			assetVocabularyDepotEntryRelImpl.setUuid(uuid);
		}

		assetVocabularyDepotEntryRelImpl.setAssetVocabularyDepotEntryRelId(
			assetVocabularyDepotEntryRelId);
		assetVocabularyDepotEntryRelImpl.setCompanyId(companyId);
		assetVocabularyDepotEntryRelImpl.setAssetVocabularyId(
			assetVocabularyId);
		assetVocabularyDepotEntryRelImpl.setDepotEntryId(depotEntryId);

		assetVocabularyDepotEntryRelImpl.resetOriginalValues();

		return assetVocabularyDepotEntryRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		assetVocabularyDepotEntryRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		assetVocabularyId = objectInput.readLong();

		depotEntryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(assetVocabularyDepotEntryRelId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(assetVocabularyId);

		objectOutput.writeLong(depotEntryId);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long assetVocabularyDepotEntryRelId;
	public long companyId;
	public long assetVocabularyId;
	public long depotEntryId;

}