/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.service;

import com.liferay.asset.kernel.model.AssetVocabularyDepotEntryRel;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

/**
 * Provides a wrapper for {@link AssetVocabularyDepotEntryRelLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyDepotEntryRelLocalService
 * @generated
 */
public class AssetVocabularyDepotEntryRelLocalServiceWrapper
	implements AssetVocabularyDepotEntryRelLocalService,
			   ServiceWrapper<AssetVocabularyDepotEntryRelLocalService> {

	public AssetVocabularyDepotEntryRelLocalServiceWrapper() {
		this(null);
	}

	public AssetVocabularyDepotEntryRelLocalServiceWrapper(
		AssetVocabularyDepotEntryRelLocalService
			assetVocabularyDepotEntryRelLocalService) {

		_assetVocabularyDepotEntryRelLocalService =
			assetVocabularyDepotEntryRelLocalService;
	}

	/**
	 * Adds the asset vocabulary depot entry rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AssetVocabularyDepotEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param assetVocabularyDepotEntryRel the asset vocabulary depot entry rel
	 * @return the asset vocabulary depot entry rel that was added
	 */
	@Override
	public AssetVocabularyDepotEntryRel addAssetVocabularyDepotEntryRel(
		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel) {

		return _assetVocabularyDepotEntryRelLocalService.
			addAssetVocabularyDepotEntryRel(assetVocabularyDepotEntryRel);
	}

	/**
	 * Creates a new asset vocabulary depot entry rel with the primary key. Does not add the asset vocabulary depot entry rel to the database.
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key for the new asset vocabulary depot entry rel
	 * @return the new asset vocabulary depot entry rel
	 */
	@Override
	public AssetVocabularyDepotEntryRel createAssetVocabularyDepotEntryRel(
		long assetVocabularyDepotEntryRelId) {

		return _assetVocabularyDepotEntryRelLocalService.
			createAssetVocabularyDepotEntryRel(assetVocabularyDepotEntryRelId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetVocabularyDepotEntryRelLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the asset vocabulary depot entry rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AssetVocabularyDepotEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param assetVocabularyDepotEntryRel the asset vocabulary depot entry rel
	 * @return the asset vocabulary depot entry rel that was removed
	 */
	@Override
	public AssetVocabularyDepotEntryRel deleteAssetVocabularyDepotEntryRel(
		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel) {

		return _assetVocabularyDepotEntryRelLocalService.
			deleteAssetVocabularyDepotEntryRel(assetVocabularyDepotEntryRel);
	}

	/**
	 * Deletes the asset vocabulary depot entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AssetVocabularyDepotEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key of the asset vocabulary depot entry rel
	 * @return the asset vocabulary depot entry rel that was removed
	 * @throws PortalException if a asset vocabulary depot entry rel with the primary key could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel deleteAssetVocabularyDepotEntryRel(
			long assetVocabularyDepotEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetVocabularyDepotEntryRelLocalService.
			deleteAssetVocabularyDepotEntryRel(assetVocabularyDepotEntryRelId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetVocabularyDepotEntryRelLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _assetVocabularyDepotEntryRelLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _assetVocabularyDepotEntryRelLocalService.dslQueryCount(
			dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _assetVocabularyDepotEntryRelLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _assetVocabularyDepotEntryRelLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.asset.model.impl.AssetVocabularyDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _assetVocabularyDepotEntryRelLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.asset.model.impl.AssetVocabularyDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _assetVocabularyDepotEntryRelLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _assetVocabularyDepotEntryRelLocalService.dynamicQueryCount(
			dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _assetVocabularyDepotEntryRelLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public AssetVocabularyDepotEntryRel fetchAssetVocabularyDepotEntryRel(
		long assetVocabularyDepotEntryRelId) {

		return _assetVocabularyDepotEntryRelLocalService.
			fetchAssetVocabularyDepotEntryRel(assetVocabularyDepotEntryRelId);
	}

	/**
	 * Returns the asset vocabulary depot entry rel with the matching UUID and company.
	 *
	 * @param uuid the asset vocabulary depot entry rel's UUID
	 * @param companyId the primary key of the company
	 * @return the matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel
		fetchAssetVocabularyDepotEntryRelByUuidAndCompanyId(
			String uuid, long companyId) {

		return _assetVocabularyDepotEntryRelLocalService.
			fetchAssetVocabularyDepotEntryRelByUuidAndCompanyId(
				uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _assetVocabularyDepotEntryRelLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the asset vocabulary depot entry rel with the primary key.
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key of the asset vocabulary depot entry rel
	 * @return the asset vocabulary depot entry rel
	 * @throws PortalException if a asset vocabulary depot entry rel with the primary key could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel getAssetVocabularyDepotEntryRel(
			long assetVocabularyDepotEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetVocabularyDepotEntryRelLocalService.
			getAssetVocabularyDepotEntryRel(assetVocabularyDepotEntryRelId);
	}

	/**
	 * Returns the asset vocabulary depot entry rel with the matching UUID and company.
	 *
	 * @param uuid the asset vocabulary depot entry rel's UUID
	 * @param companyId the primary key of the company
	 * @return the matching asset vocabulary depot entry rel
	 * @throws PortalException if a matching asset vocabulary depot entry rel could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel
			getAssetVocabularyDepotEntryRelByUuidAndCompanyId(
				String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetVocabularyDepotEntryRelLocalService.
			getAssetVocabularyDepotEntryRelByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the asset vocabulary depot entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.asset.model.impl.AssetVocabularyDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset vocabulary depot entry rels
	 * @param end the upper bound of the range of asset vocabulary depot entry rels (not inclusive)
	 * @return the range of asset vocabulary depot entry rels
	 */
	@Override
	public java.util.List<AssetVocabularyDepotEntryRel>
		getAssetVocabularyDepotEntryRels(int start, int end) {

		return _assetVocabularyDepotEntryRelLocalService.
			getAssetVocabularyDepotEntryRels(start, end);
	}

	/**
	 * Returns the number of asset vocabulary depot entry rels.
	 *
	 * @return the number of asset vocabulary depot entry rels
	 */
	@Override
	public int getAssetVocabularyDepotEntryRelsCount() {
		return _assetVocabularyDepotEntryRelLocalService.
			getAssetVocabularyDepotEntryRelsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _assetVocabularyDepotEntryRelLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetVocabularyDepotEntryRelLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetVocabularyDepotEntryRelLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the asset vocabulary depot entry rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AssetVocabularyDepotEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param assetVocabularyDepotEntryRel the asset vocabulary depot entry rel
	 * @return the asset vocabulary depot entry rel that was updated
	 */
	@Override
	public AssetVocabularyDepotEntryRel updateAssetVocabularyDepotEntryRel(
		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel) {

		return _assetVocabularyDepotEntryRelLocalService.
			updateAssetVocabularyDepotEntryRel(assetVocabularyDepotEntryRel);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _assetVocabularyDepotEntryRelLocalService.getBasePersistence();
	}

	@Override
	public CTPersistence<AssetVocabularyDepotEntryRel> getCTPersistence() {
		return _assetVocabularyDepotEntryRelLocalService.getCTPersistence();
	}

	@Override
	public Class<AssetVocabularyDepotEntryRel> getModelClass() {
		return _assetVocabularyDepotEntryRelLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<AssetVocabularyDepotEntryRel>, R, E>
				updateUnsafeFunction)
		throws E {

		return _assetVocabularyDepotEntryRelLocalService.
			updateWithUnsafeFunction(updateUnsafeFunction);
	}

	@Override
	public AssetVocabularyDepotEntryRelLocalService getWrappedService() {
		return _assetVocabularyDepotEntryRelLocalService;
	}

	@Override
	public void setWrappedService(
		AssetVocabularyDepotEntryRelLocalService
			assetVocabularyDepotEntryRelLocalService) {

		_assetVocabularyDepotEntryRelLocalService =
			assetVocabularyDepotEntryRelLocalService;
	}

	private AssetVocabularyDepotEntryRelLocalService
		_assetVocabularyDepotEntryRelLocalService;

}