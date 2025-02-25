/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.model.impl;

import com.liferay.asset.tags.model.AssetTagDepotEntryRel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AssetTagDepotEntryRel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetTagDepotEntryRelCacheModel
	implements CacheModel<AssetTagDepotEntryRel>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AssetTagDepotEntryRelCacheModel)) {
			return false;
		}

		AssetTagDepotEntryRelCacheModel assetTagDepotEntryRelCacheModel =
			(AssetTagDepotEntryRelCacheModel)object;

		if ((assetTagDepotEntryRelId ==
				assetTagDepotEntryRelCacheModel.assetTagDepotEntryRelId) &&
			(mvccVersion == assetTagDepotEntryRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, assetTagDepotEntryRelId);

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
		sb.append(", assetTagDepotEntryRelId=");
		sb.append(assetTagDepotEntryRelId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", assetTagId=");
		sb.append(assetTagId);
		sb.append(", depotEntryId=");
		sb.append(depotEntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetTagDepotEntryRel toEntityModel() {
		AssetTagDepotEntryRelImpl assetTagDepotEntryRelImpl =
			new AssetTagDepotEntryRelImpl();

		assetTagDepotEntryRelImpl.setMvccVersion(mvccVersion);
		assetTagDepotEntryRelImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			assetTagDepotEntryRelImpl.setUuid("");
		}
		else {
			assetTagDepotEntryRelImpl.setUuid(uuid);
		}

		assetTagDepotEntryRelImpl.setAssetTagDepotEntryRelId(
			assetTagDepotEntryRelId);
		assetTagDepotEntryRelImpl.setCompanyId(companyId);
		assetTagDepotEntryRelImpl.setAssetTagId(assetTagId);
		assetTagDepotEntryRelImpl.setDepotEntryId(depotEntryId);

		assetTagDepotEntryRelImpl.resetOriginalValues();

		return assetTagDepotEntryRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		assetTagDepotEntryRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		assetTagId = objectInput.readLong();

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

		objectOutput.writeLong(assetTagDepotEntryRelId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(assetTagId);

		objectOutput.writeLong(depotEntryId);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long assetTagDepotEntryRelId;
	public long companyId;
	public long assetTagId;
	public long depotEntryId;

}