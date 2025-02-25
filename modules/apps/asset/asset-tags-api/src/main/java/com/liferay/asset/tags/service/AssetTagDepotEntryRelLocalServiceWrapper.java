/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.service;

import com.liferay.asset.tags.model.AssetTagDepotEntryRel;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

/**
 * Provides a wrapper for {@link AssetTagDepotEntryRelLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagDepotEntryRelLocalService
 * @generated
 */
public class AssetTagDepotEntryRelLocalServiceWrapper
	implements AssetTagDepotEntryRelLocalService,
			   ServiceWrapper<AssetTagDepotEntryRelLocalService> {

	public AssetTagDepotEntryRelLocalServiceWrapper() {
		this(null);
	}

	public AssetTagDepotEntryRelLocalServiceWrapper(
		AssetTagDepotEntryRelLocalService assetTagDepotEntryRelLocalService) {

		_assetTagDepotEntryRelLocalService = assetTagDepotEntryRelLocalService;
	}

	/**
	 * Adds the asset tag depot entry rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AssetTagDepotEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param assetTagDepotEntryRel the asset tag depot entry rel
	 * @return the asset tag depot entry rel that was added
	 */
	@Override
	public AssetTagDepotEntryRel addAssetTagDepotEntryRel(
		AssetTagDepotEntryRel assetTagDepotEntryRel) {

		return _assetTagDepotEntryRelLocalService.addAssetTagDepotEntryRel(
			assetTagDepotEntryRel);
	}

	/**
	 * Creates a new asset tag depot entry rel with the primary key. Does not add the asset tag depot entry rel to the database.
	 *
	 * @param assetTagDepotEntryRelId the primary key for the new asset tag depot entry rel
	 * @return the new asset tag depot entry rel
	 */
	@Override
	public AssetTagDepotEntryRel createAssetTagDepotEntryRel(
		long assetTagDepotEntryRelId) {

		return _assetTagDepotEntryRelLocalService.createAssetTagDepotEntryRel(
			assetTagDepotEntryRelId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetTagDepotEntryRelLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the asset tag depot entry rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AssetTagDepotEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param assetTagDepotEntryRel the asset tag depot entry rel
	 * @return the asset tag depot entry rel that was removed
	 */
	@Override
	public AssetTagDepotEntryRel deleteAssetTagDepotEntryRel(
		AssetTagDepotEntryRel assetTagDepotEntryRel) {

		return _assetTagDepotEntryRelLocalService.deleteAssetTagDepotEntryRel(
			assetTagDepotEntryRel);
	}

	/**
	 * Deletes the asset tag depot entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AssetTagDepotEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param assetTagDepotEntryRelId the primary key of the asset tag depot entry rel
	 * @return the asset tag depot entry rel that was removed
	 * @throws PortalException if a asset tag depot entry rel with the primary key could not be found
	 */
	@Override
	public AssetTagDepotEntryRel deleteAssetTagDepotEntryRel(
			long assetTagDepotEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetTagDepotEntryRelLocalService.deleteAssetTagDepotEntryRel(
			assetTagDepotEntryRelId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetTagDepotEntryRelLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _assetTagDepotEntryRelLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _assetTagDepotEntryRelLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _assetTagDepotEntryRelLocalService.dynamicQuery();
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

		return _assetTagDepotEntryRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.tags.model.impl.AssetTagDepotEntryRelModelImpl</code>.
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

		return _assetTagDepotEntryRelLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.tags.model.impl.AssetTagDepotEntryRelModelImpl</code>.
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

		return _assetTagDepotEntryRelLocalService.dynamicQuery(
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

		return _assetTagDepotEntryRelLocalService.dynamicQueryCount(
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

		return _assetTagDepotEntryRelLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public AssetTagDepotEntryRel fetchAssetTagDepotEntryRel(
		long assetTagDepotEntryRelId) {

		return _assetTagDepotEntryRelLocalService.fetchAssetTagDepotEntryRel(
			assetTagDepotEntryRelId);
	}

	/**
	 * Returns the asset tag depot entry rel with the matching UUID and company.
	 *
	 * @param uuid the asset tag depot entry rel's UUID
	 * @param companyId the primary key of the company
	 * @return the matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	@Override
	public AssetTagDepotEntryRel fetchAssetTagDepotEntryRelByUuidAndCompanyId(
		String uuid, long companyId) {

		return _assetTagDepotEntryRelLocalService.
			fetchAssetTagDepotEntryRelByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _assetTagDepotEntryRelLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the asset tag depot entry rel with the primary key.
	 *
	 * @param assetTagDepotEntryRelId the primary key of the asset tag depot entry rel
	 * @return the asset tag depot entry rel
	 * @throws PortalException if a asset tag depot entry rel with the primary key could not be found
	 */
	@Override
	public AssetTagDepotEntryRel getAssetTagDepotEntryRel(
			long assetTagDepotEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetTagDepotEntryRelLocalService.getAssetTagDepotEntryRel(
			assetTagDepotEntryRelId);
	}

	/**
	 * Returns the asset tag depot entry rel with the matching UUID and company.
	 *
	 * @param uuid the asset tag depot entry rel's UUID
	 * @param companyId the primary key of the company
	 * @return the matching asset tag depot entry rel
	 * @throws PortalException if a matching asset tag depot entry rel could not be found
	 */
	@Override
	public AssetTagDepotEntryRel getAssetTagDepotEntryRelByUuidAndCompanyId(
			String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetTagDepotEntryRelLocalService.
			getAssetTagDepotEntryRelByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the asset tag depot entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.asset.tags.model.impl.AssetTagDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset tag depot entry rels
	 * @param end the upper bound of the range of asset tag depot entry rels (not inclusive)
	 * @return the range of asset tag depot entry rels
	 */
	@Override
	public java.util.List<AssetTagDepotEntryRel> getAssetTagDepotEntryRels(
		int start, int end) {

		return _assetTagDepotEntryRelLocalService.getAssetTagDepotEntryRels(
			start, end);
	}

	/**
	 * Returns the number of asset tag depot entry rels.
	 *
	 * @return the number of asset tag depot entry rels
	 */
	@Override
	public int getAssetTagDepotEntryRelsCount() {
		return _assetTagDepotEntryRelLocalService.
			getAssetTagDepotEntryRelsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _assetTagDepotEntryRelLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetTagDepotEntryRelLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetTagDepotEntryRelLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the asset tag depot entry rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AssetTagDepotEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param assetTagDepotEntryRel the asset tag depot entry rel
	 * @return the asset tag depot entry rel that was updated
	 */
	@Override
	public AssetTagDepotEntryRel updateAssetTagDepotEntryRel(
		AssetTagDepotEntryRel assetTagDepotEntryRel) {

		return _assetTagDepotEntryRelLocalService.updateAssetTagDepotEntryRel(
			assetTagDepotEntryRel);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _assetTagDepotEntryRelLocalService.getBasePersistence();
	}

	@Override
	public CTPersistence<AssetTagDepotEntryRel> getCTPersistence() {
		return _assetTagDepotEntryRelLocalService.getCTPersistence();
	}

	@Override
	public Class<AssetTagDepotEntryRel> getModelClass() {
		return _assetTagDepotEntryRelLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<AssetTagDepotEntryRel>, R, E>
				updateUnsafeFunction)
		throws E {

		return _assetTagDepotEntryRelLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public AssetTagDepotEntryRelLocalService getWrappedService() {
		return _assetTagDepotEntryRelLocalService;
	}

	@Override
	public void setWrappedService(
		AssetTagDepotEntryRelLocalService assetTagDepotEntryRelLocalService) {

		_assetTagDepotEntryRelLocalService = assetTagDepotEntryRelLocalService;
	}

	private AssetTagDepotEntryRelLocalService
		_assetTagDepotEntryRelLocalService;

}