/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.service;

import com.liferay.asset.kernel.model.AssetVocabularyDepotEntryRel;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for AssetVocabularyDepotEntryRel. This utility wraps
 * <code>com.liferay.portlet.asset.service.impl.AssetVocabularyDepotEntryRelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyDepotEntryRelLocalService
 * @generated
 */
public class AssetVocabularyDepotEntryRelLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.asset.service.impl.AssetVocabularyDepotEntryRelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static AssetVocabularyDepotEntryRel addAssetVocabularyDepotEntryRel(
		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel) {

		return getService().addAssetVocabularyDepotEntryRel(
			assetVocabularyDepotEntryRel);
	}

	public static AssetVocabularyDepotEntryRel addAssetVocabularyDepotEntryRel(
			long assetVocabularyId, long depotEntryId)
		throws PortalException {

		return getService().addAssetVocabularyDepotEntryRel(
			assetVocabularyId, depotEntryId);
	}

	/**
	 * Creates a new asset vocabulary depot entry rel with the primary key. Does not add the asset vocabulary depot entry rel to the database.
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key for the new asset vocabulary depot entry rel
	 * @return the new asset vocabulary depot entry rel
	 */
	public static AssetVocabularyDepotEntryRel
		createAssetVocabularyDepotEntryRel(
			long assetVocabularyDepotEntryRelId) {

		return getService().createAssetVocabularyDepotEntryRel(
			assetVocabularyDepotEntryRelId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
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
	public static AssetVocabularyDepotEntryRel
		deleteAssetVocabularyDepotEntryRel(
			AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel) {

		return getService().deleteAssetVocabularyDepotEntryRel(
			assetVocabularyDepotEntryRel);
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
	public static AssetVocabularyDepotEntryRel
			deleteAssetVocabularyDepotEntryRel(
				long assetVocabularyDepotEntryRelId)
		throws PortalException {

		return getService().deleteAssetVocabularyDepotEntryRel(
			assetVocabularyDepotEntryRelId);
	}

	public static void deleteAssetVocabularyDepotEntryRelsByAssetVocabularyId(
		long assetVocabularyId) {

		getService().deleteAssetVocabularyDepotEntryRelsByAssetVocabularyId(
			assetVocabularyId);
	}

	public static void deleteAssetVocabularyDepotEntryRelsByDepotEntryId(
		long depotEntryId) {

		getService().deleteAssetVocabularyDepotEntryRelsByDepotEntryId(
			depotEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static AssetVocabularyDepotEntryRel
		fetchAssetVocabularyDepotEntryRel(long assetVocabularyDepotEntryRelId) {

		return getService().fetchAssetVocabularyDepotEntryRel(
			assetVocabularyDepotEntryRelId);
	}

	/**
	 * Returns the asset vocabulary depot entry rel with the matching UUID and company.
	 *
	 * @param uuid the asset vocabulary depot entry rel's UUID
	 * @param companyId the primary key of the company
	 * @return the matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	public static AssetVocabularyDepotEntryRel
		fetchAssetVocabularyDepotEntryRelByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().fetchAssetVocabularyDepotEntryRelByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the asset vocabulary depot entry rel with the primary key.
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key of the asset vocabulary depot entry rel
	 * @return the asset vocabulary depot entry rel
	 * @throws PortalException if a asset vocabulary depot entry rel with the primary key could not be found
	 */
	public static AssetVocabularyDepotEntryRel getAssetVocabularyDepotEntryRel(
			long assetVocabularyDepotEntryRelId)
		throws PortalException {

		return getService().getAssetVocabularyDepotEntryRel(
			assetVocabularyDepotEntryRelId);
	}

	/**
	 * Returns the asset vocabulary depot entry rel with the matching UUID and company.
	 *
	 * @param uuid the asset vocabulary depot entry rel's UUID
	 * @param companyId the primary key of the company
	 * @return the matching asset vocabulary depot entry rel
	 * @throws PortalException if a matching asset vocabulary depot entry rel could not be found
	 */
	public static AssetVocabularyDepotEntryRel
			getAssetVocabularyDepotEntryRelByUuidAndCompanyId(
				String uuid, long companyId)
		throws PortalException {

		return getService().getAssetVocabularyDepotEntryRelByUuidAndCompanyId(
			uuid, companyId);
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
	public static List<AssetVocabularyDepotEntryRel>
		getAssetVocabularyDepotEntryRels(int start, int end) {

		return getService().getAssetVocabularyDepotEntryRels(start, end);
	}

	public static List<AssetVocabularyDepotEntryRel>
		getAssetVocabularyDepotEntryRelsByAssetVocabularyId(
			long assetVocabularyId) {

		return getService().getAssetVocabularyDepotEntryRelsByAssetVocabularyId(
			assetVocabularyId);
	}

	public static List<AssetVocabularyDepotEntryRel>
		getAssetVocabularyDepotEntryRelsByDepotEntryId(long depotEntryId) {

		return getService().getAssetVocabularyDepotEntryRelsByDepotEntryId(
			depotEntryId);
	}

	/**
	 * Returns the number of asset vocabulary depot entry rels.
	 *
	 * @return the number of asset vocabulary depot entry rels
	 */
	public static int getAssetVocabularyDepotEntryRelsCount() {
		return getService().getAssetVocabularyDepotEntryRelsCount();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static void setAssetVocabularyDepotEntryRels(
			long assetVocabularyId, long[] depotEntryIds)
		throws PortalException {

		getService().setAssetVocabularyDepotEntryRels(
			assetVocabularyId, depotEntryIds);
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
	public static AssetVocabularyDepotEntryRel
		updateAssetVocabularyDepotEntryRel(
			AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel) {

		return getService().updateAssetVocabularyDepotEntryRel(
			assetVocabularyDepotEntryRel);
	}

	public static AssetVocabularyDepotEntryRelLocalService getService() {
		return _service;
	}

	public static void setService(
		AssetVocabularyDepotEntryRelLocalService service) {

		_service = service;
	}

	private static volatile AssetVocabularyDepotEntryRelLocalService _service;

}