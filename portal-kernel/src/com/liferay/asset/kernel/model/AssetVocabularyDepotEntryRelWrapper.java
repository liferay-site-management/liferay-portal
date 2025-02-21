/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link AssetVocabularyDepotEntryRel}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyDepotEntryRel
 * @generated
 */
public class AssetVocabularyDepotEntryRelWrapper
	extends BaseModelWrapper<AssetVocabularyDepotEntryRel>
	implements AssetVocabularyDepotEntryRel,
			   ModelWrapper<AssetVocabularyDepotEntryRel> {

	public AssetVocabularyDepotEntryRelWrapper(
		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel) {

		super(assetVocabularyDepotEntryRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put(
			"assetVocabularyDepotEntryRelId",
			getAssetVocabularyDepotEntryRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("assetVocabularyId", getAssetVocabularyId());
		attributes.put("depotEntryId", getDepotEntryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long assetVocabularyDepotEntryRelId = (Long)attributes.get(
			"assetVocabularyDepotEntryRelId");

		if (assetVocabularyDepotEntryRelId != null) {
			setAssetVocabularyDepotEntryRelId(assetVocabularyDepotEntryRelId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long assetVocabularyId = (Long)attributes.get("assetVocabularyId");

		if (assetVocabularyId != null) {
			setAssetVocabularyId(assetVocabularyId);
		}

		Long depotEntryId = (Long)attributes.get("depotEntryId");

		if (depotEntryId != null) {
			setDepotEntryId(depotEntryId);
		}
	}

	@Override
	public AssetVocabularyDepotEntryRel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the asset vocabulary depot entry rel ID of this asset vocabulary depot entry rel.
	 *
	 * @return the asset vocabulary depot entry rel ID of this asset vocabulary depot entry rel
	 */
	@Override
	public long getAssetVocabularyDepotEntryRelId() {
		return model.getAssetVocabularyDepotEntryRelId();
	}

	/**
	 * Returns the asset vocabulary ID of this asset vocabulary depot entry rel.
	 *
	 * @return the asset vocabulary ID of this asset vocabulary depot entry rel
	 */
	@Override
	public long getAssetVocabularyId() {
		return model.getAssetVocabularyId();
	}

	/**
	 * Returns the company ID of this asset vocabulary depot entry rel.
	 *
	 * @return the company ID of this asset vocabulary depot entry rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ct collection ID of this asset vocabulary depot entry rel.
	 *
	 * @return the ct collection ID of this asset vocabulary depot entry rel
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the depot entry ID of this asset vocabulary depot entry rel.
	 *
	 * @return the depot entry ID of this asset vocabulary depot entry rel
	 */
	@Override
	public long getDepotEntryId() {
		return model.getDepotEntryId();
	}

	/**
	 * Returns the mvcc version of this asset vocabulary depot entry rel.
	 *
	 * @return the mvcc version of this asset vocabulary depot entry rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this asset vocabulary depot entry rel.
	 *
	 * @return the primary key of this asset vocabulary depot entry rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the uuid of this asset vocabulary depot entry rel.
	 *
	 * @return the uuid of this asset vocabulary depot entry rel
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the asset vocabulary depot entry rel ID of this asset vocabulary depot entry rel.
	 *
	 * @param assetVocabularyDepotEntryRelId the asset vocabulary depot entry rel ID of this asset vocabulary depot entry rel
	 */
	@Override
	public void setAssetVocabularyDepotEntryRelId(
		long assetVocabularyDepotEntryRelId) {

		model.setAssetVocabularyDepotEntryRelId(assetVocabularyDepotEntryRelId);
	}

	/**
	 * Sets the asset vocabulary ID of this asset vocabulary depot entry rel.
	 *
	 * @param assetVocabularyId the asset vocabulary ID of this asset vocabulary depot entry rel
	 */
	@Override
	public void setAssetVocabularyId(long assetVocabularyId) {
		model.setAssetVocabularyId(assetVocabularyId);
	}

	/**
	 * Sets the company ID of this asset vocabulary depot entry rel.
	 *
	 * @param companyId the company ID of this asset vocabulary depot entry rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ct collection ID of this asset vocabulary depot entry rel.
	 *
	 * @param ctCollectionId the ct collection ID of this asset vocabulary depot entry rel
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the depot entry ID of this asset vocabulary depot entry rel.
	 *
	 * @param depotEntryId the depot entry ID of this asset vocabulary depot entry rel
	 */
	@Override
	public void setDepotEntryId(long depotEntryId) {
		model.setDepotEntryId(depotEntryId);
	}

	/**
	 * Sets the mvcc version of this asset vocabulary depot entry rel.
	 *
	 * @param mvccVersion the mvcc version of this asset vocabulary depot entry rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this asset vocabulary depot entry rel.
	 *
	 * @param primaryKey the primary key of this asset vocabulary depot entry rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the uuid of this asset vocabulary depot entry rel.
	 *
	 * @param uuid the uuid of this asset vocabulary depot entry rel
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<AssetVocabularyDepotEntryRel, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<AssetVocabularyDepotEntryRel, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected AssetVocabularyDepotEntryRelWrapper wrap(
		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel) {

		return new AssetVocabularyDepotEntryRelWrapper(
			assetVocabularyDepotEntryRel);
	}

}