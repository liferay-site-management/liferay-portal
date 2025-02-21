/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.service.persistence;

import com.liferay.asset.kernel.model.AssetVocabularyDepotEntryRel;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the asset vocabulary depot entry rel service. This utility wraps <code>com.liferay.portlet.asset.service.persistence.impl.AssetVocabularyDepotEntryRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyDepotEntryRelPersistence
 * @generated
 */
public class AssetVocabularyDepotEntryRelUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(
		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel) {

		getPersistence().clearCache(assetVocabularyDepotEntryRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, AssetVocabularyDepotEntryRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AssetVocabularyDepotEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetVocabularyDepotEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetVocabularyDepotEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AssetVocabularyDepotEntryRel update(
		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel) {

		return getPersistence().update(assetVocabularyDepotEntryRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AssetVocabularyDepotEntryRel update(
		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel,
		ServiceContext serviceContext) {

		return getPersistence().update(
			assetVocabularyDepotEntryRel, serviceContext);
	}

	/**
	 * Returns all the asset vocabulary depot entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset vocabulary depot entry rels
	 */
	public static List<AssetVocabularyDepotEntryRel> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the asset vocabulary depot entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset vocabulary depot entry rels
	 * @param end the upper bound of the range of asset vocabulary depot entry rels (not inclusive)
	 * @return the range of matching asset vocabulary depot entry rels
	 */
	public static List<AssetVocabularyDepotEntryRel> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary depot entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset vocabulary depot entry rels
	 * @param end the upper bound of the range of asset vocabulary depot entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabulary depot entry rels
	 */
	public static List<AssetVocabularyDepotEntryRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary depot entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset vocabulary depot entry rels
	 * @param end the upper bound of the range of asset vocabulary depot entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset vocabulary depot entry rels
	 */
	public static List<AssetVocabularyDepotEntryRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset vocabulary depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	public static AssetVocabularyDepotEntryRel findByUuid_First(
			String uuid,
			OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyDepotEntryRelException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first asset vocabulary depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	public static AssetVocabularyDepotEntryRel fetchByUuid_First(
		String uuid,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last asset vocabulary depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	public static AssetVocabularyDepotEntryRel findByUuid_Last(
			String uuid,
			OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyDepotEntryRelException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last asset vocabulary depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	public static AssetVocabularyDepotEntryRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the asset vocabulary depot entry rels before and after the current asset vocabulary depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key of the current asset vocabulary depot entry rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a asset vocabulary depot entry rel with the primary key could not be found
	 */
	public static AssetVocabularyDepotEntryRel[] findByUuid_PrevAndNext(
			long assetVocabularyDepotEntryRelId, String uuid,
			OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyDepotEntryRelException {

		return getPersistence().findByUuid_PrevAndNext(
			assetVocabularyDepotEntryRelId, uuid, orderByComparator);
	}

	/**
	 * Removes all the asset vocabulary depot entry rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of asset vocabulary depot entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset vocabulary depot entry rels
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the asset vocabulary depot entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset vocabulary depot entry rels
	 */
	public static List<AssetVocabularyDepotEntryRel> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the asset vocabulary depot entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset vocabulary depot entry rels
	 * @param end the upper bound of the range of asset vocabulary depot entry rels (not inclusive)
	 * @return the range of matching asset vocabulary depot entry rels
	 */
	public static List<AssetVocabularyDepotEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary depot entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset vocabulary depot entry rels
	 * @param end the upper bound of the range of asset vocabulary depot entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabulary depot entry rels
	 */
	public static List<AssetVocabularyDepotEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary depot entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset vocabulary depot entry rels
	 * @param end the upper bound of the range of asset vocabulary depot entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset vocabulary depot entry rels
	 */
	public static List<AssetVocabularyDepotEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset vocabulary depot entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	public static AssetVocabularyDepotEntryRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyDepotEntryRelException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first asset vocabulary depot entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	public static AssetVocabularyDepotEntryRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last asset vocabulary depot entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	public static AssetVocabularyDepotEntryRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyDepotEntryRelException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last asset vocabulary depot entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	public static AssetVocabularyDepotEntryRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the asset vocabulary depot entry rels before and after the current asset vocabulary depot entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key of the current asset vocabulary depot entry rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a asset vocabulary depot entry rel with the primary key could not be found
	 */
	public static AssetVocabularyDepotEntryRel[] findByUuid_C_PrevAndNext(
			long assetVocabularyDepotEntryRelId, String uuid, long companyId,
			OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyDepotEntryRelException {

		return getPersistence().findByUuid_C_PrevAndNext(
			assetVocabularyDepotEntryRelId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the asset vocabulary depot entry rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of asset vocabulary depot entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset vocabulary depot entry rels
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the asset vocabulary depot entry rels where assetVocabularyId = &#63;.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @return the matching asset vocabulary depot entry rels
	 */
	public static List<AssetVocabularyDepotEntryRel> findByAssetVocabularyId(
		long assetVocabularyId) {

		return getPersistence().findByAssetVocabularyId(assetVocabularyId);
	}

	/**
	 * Returns a range of all the asset vocabulary depot entry rels where assetVocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param start the lower bound of the range of asset vocabulary depot entry rels
	 * @param end the upper bound of the range of asset vocabulary depot entry rels (not inclusive)
	 * @return the range of matching asset vocabulary depot entry rels
	 */
	public static List<AssetVocabularyDepotEntryRel> findByAssetVocabularyId(
		long assetVocabularyId, int start, int end) {

		return getPersistence().findByAssetVocabularyId(
			assetVocabularyId, start, end);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary depot entry rels where assetVocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param start the lower bound of the range of asset vocabulary depot entry rels
	 * @param end the upper bound of the range of asset vocabulary depot entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabulary depot entry rels
	 */
	public static List<AssetVocabularyDepotEntryRel> findByAssetVocabularyId(
		long assetVocabularyId, int start, int end,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		return getPersistence().findByAssetVocabularyId(
			assetVocabularyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary depot entry rels where assetVocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param start the lower bound of the range of asset vocabulary depot entry rels
	 * @param end the upper bound of the range of asset vocabulary depot entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset vocabulary depot entry rels
	 */
	public static List<AssetVocabularyDepotEntryRel> findByAssetVocabularyId(
		long assetVocabularyId, int start, int end,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByAssetVocabularyId(
			assetVocabularyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset vocabulary depot entry rel in the ordered set where assetVocabularyId = &#63;.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	public static AssetVocabularyDepotEntryRel findByAssetVocabularyId_First(
			long assetVocabularyId,
			OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyDepotEntryRelException {

		return getPersistence().findByAssetVocabularyId_First(
			assetVocabularyId, orderByComparator);
	}

	/**
	 * Returns the first asset vocabulary depot entry rel in the ordered set where assetVocabularyId = &#63;.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	public static AssetVocabularyDepotEntryRel fetchByAssetVocabularyId_First(
		long assetVocabularyId,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		return getPersistence().fetchByAssetVocabularyId_First(
			assetVocabularyId, orderByComparator);
	}

	/**
	 * Returns the last asset vocabulary depot entry rel in the ordered set where assetVocabularyId = &#63;.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	public static AssetVocabularyDepotEntryRel findByAssetVocabularyId_Last(
			long assetVocabularyId,
			OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyDepotEntryRelException {

		return getPersistence().findByAssetVocabularyId_Last(
			assetVocabularyId, orderByComparator);
	}

	/**
	 * Returns the last asset vocabulary depot entry rel in the ordered set where assetVocabularyId = &#63;.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	public static AssetVocabularyDepotEntryRel fetchByAssetVocabularyId_Last(
		long assetVocabularyId,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		return getPersistence().fetchByAssetVocabularyId_Last(
			assetVocabularyId, orderByComparator);
	}

	/**
	 * Returns the asset vocabulary depot entry rels before and after the current asset vocabulary depot entry rel in the ordered set where assetVocabularyId = &#63;.
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key of the current asset vocabulary depot entry rel
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a asset vocabulary depot entry rel with the primary key could not be found
	 */
	public static AssetVocabularyDepotEntryRel[]
			findByAssetVocabularyId_PrevAndNext(
				long assetVocabularyDepotEntryRelId, long assetVocabularyId,
				OrderByComparator<AssetVocabularyDepotEntryRel>
					orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyDepotEntryRelException {

		return getPersistence().findByAssetVocabularyId_PrevAndNext(
			assetVocabularyDepotEntryRelId, assetVocabularyId,
			orderByComparator);
	}

	/**
	 * Removes all the asset vocabulary depot entry rels where assetVocabularyId = &#63; from the database.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 */
	public static void removeByAssetVocabularyId(long assetVocabularyId) {
		getPersistence().removeByAssetVocabularyId(assetVocabularyId);
	}

	/**
	 * Returns the number of asset vocabulary depot entry rels where assetVocabularyId = &#63;.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @return the number of matching asset vocabulary depot entry rels
	 */
	public static int countByAssetVocabularyId(long assetVocabularyId) {
		return getPersistence().countByAssetVocabularyId(assetVocabularyId);
	}

	/**
	 * Returns all the asset vocabulary depot entry rels where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the matching asset vocabulary depot entry rels
	 */
	public static List<AssetVocabularyDepotEntryRel> findByDepotEntryId(
		long depotEntryId) {

		return getPersistence().findByDepotEntryId(depotEntryId);
	}

	/**
	 * Returns a range of all the asset vocabulary depot entry rels where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of asset vocabulary depot entry rels
	 * @param end the upper bound of the range of asset vocabulary depot entry rels (not inclusive)
	 * @return the range of matching asset vocabulary depot entry rels
	 */
	public static List<AssetVocabularyDepotEntryRel> findByDepotEntryId(
		long depotEntryId, int start, int end) {

		return getPersistence().findByDepotEntryId(depotEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary depot entry rels where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of asset vocabulary depot entry rels
	 * @param end the upper bound of the range of asset vocabulary depot entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabulary depot entry rels
	 */
	public static List<AssetVocabularyDepotEntryRel> findByDepotEntryId(
		long depotEntryId, int start, int end,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		return getPersistence().findByDepotEntryId(
			depotEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary depot entry rels where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of asset vocabulary depot entry rels
	 * @param end the upper bound of the range of asset vocabulary depot entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset vocabulary depot entry rels
	 */
	public static List<AssetVocabularyDepotEntryRel> findByDepotEntryId(
		long depotEntryId, int start, int end,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByDepotEntryId(
			depotEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset vocabulary depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	public static AssetVocabularyDepotEntryRel findByDepotEntryId_First(
			long depotEntryId,
			OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyDepotEntryRelException {

		return getPersistence().findByDepotEntryId_First(
			depotEntryId, orderByComparator);
	}

	/**
	 * Returns the first asset vocabulary depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	public static AssetVocabularyDepotEntryRel fetchByDepotEntryId_First(
		long depotEntryId,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		return getPersistence().fetchByDepotEntryId_First(
			depotEntryId, orderByComparator);
	}

	/**
	 * Returns the last asset vocabulary depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	public static AssetVocabularyDepotEntryRel findByDepotEntryId_Last(
			long depotEntryId,
			OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyDepotEntryRelException {

		return getPersistence().findByDepotEntryId_Last(
			depotEntryId, orderByComparator);
	}

	/**
	 * Returns the last asset vocabulary depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	public static AssetVocabularyDepotEntryRel fetchByDepotEntryId_Last(
		long depotEntryId,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		return getPersistence().fetchByDepotEntryId_Last(
			depotEntryId, orderByComparator);
	}

	/**
	 * Returns the asset vocabulary depot entry rels before and after the current asset vocabulary depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key of the current asset vocabulary depot entry rel
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a asset vocabulary depot entry rel with the primary key could not be found
	 */
	public static AssetVocabularyDepotEntryRel[] findByDepotEntryId_PrevAndNext(
			long assetVocabularyDepotEntryRelId, long depotEntryId,
			OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyDepotEntryRelException {

		return getPersistence().findByDepotEntryId_PrevAndNext(
			assetVocabularyDepotEntryRelId, depotEntryId, orderByComparator);
	}

	/**
	 * Removes all the asset vocabulary depot entry rels where depotEntryId = &#63; from the database.
	 *
	 * @param depotEntryId the depot entry ID
	 */
	public static void removeByDepotEntryId(long depotEntryId) {
		getPersistence().removeByDepotEntryId(depotEntryId);
	}

	/**
	 * Returns the number of asset vocabulary depot entry rels where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the number of matching asset vocabulary depot entry rels
	 */
	public static int countByDepotEntryId(long depotEntryId) {
		return getPersistence().countByDepotEntryId(depotEntryId);
	}

	/**
	 * Returns the asset vocabulary depot entry rel where assetVocabularyId = &#63; and depotEntryId = &#63; or throws a <code>NoSuchVocabularyDepotEntryRelException</code> if it could not be found.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param depotEntryId the depot entry ID
	 * @return the matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	public static AssetVocabularyDepotEntryRel findByAVI_DEI(
			long assetVocabularyId, long depotEntryId)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyDepotEntryRelException {

		return getPersistence().findByAVI_DEI(assetVocabularyId, depotEntryId);
	}

	/**
	 * Returns the asset vocabulary depot entry rel where assetVocabularyId = &#63; and depotEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param depotEntryId the depot entry ID
	 * @return the matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	public static AssetVocabularyDepotEntryRel fetchByAVI_DEI(
		long assetVocabularyId, long depotEntryId) {

		return getPersistence().fetchByAVI_DEI(assetVocabularyId, depotEntryId);
	}

	/**
	 * Returns the asset vocabulary depot entry rel where assetVocabularyId = &#63; and depotEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param depotEntryId the depot entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	public static AssetVocabularyDepotEntryRel fetchByAVI_DEI(
		long assetVocabularyId, long depotEntryId, boolean useFinderCache) {

		return getPersistence().fetchByAVI_DEI(
			assetVocabularyId, depotEntryId, useFinderCache);
	}

	/**
	 * Removes the asset vocabulary depot entry rel where assetVocabularyId = &#63; and depotEntryId = &#63; from the database.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param depotEntryId the depot entry ID
	 * @return the asset vocabulary depot entry rel that was removed
	 */
	public static AssetVocabularyDepotEntryRel removeByAVI_DEI(
			long assetVocabularyId, long depotEntryId)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyDepotEntryRelException {

		return getPersistence().removeByAVI_DEI(
			assetVocabularyId, depotEntryId);
	}

	/**
	 * Returns the number of asset vocabulary depot entry rels where assetVocabularyId = &#63; and depotEntryId = &#63;.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param depotEntryId the depot entry ID
	 * @return the number of matching asset vocabulary depot entry rels
	 */
	public static int countByAVI_DEI(
		long assetVocabularyId, long depotEntryId) {

		return getPersistence().countByAVI_DEI(assetVocabularyId, depotEntryId);
	}

	/**
	 * Caches the asset vocabulary depot entry rel in the entity cache if it is enabled.
	 *
	 * @param assetVocabularyDepotEntryRel the asset vocabulary depot entry rel
	 */
	public static void cacheResult(
		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel) {

		getPersistence().cacheResult(assetVocabularyDepotEntryRel);
	}

	/**
	 * Caches the asset vocabulary depot entry rels in the entity cache if it is enabled.
	 *
	 * @param assetVocabularyDepotEntryRels the asset vocabulary depot entry rels
	 */
	public static void cacheResult(
		List<AssetVocabularyDepotEntryRel> assetVocabularyDepotEntryRels) {

		getPersistence().cacheResult(assetVocabularyDepotEntryRels);
	}

	/**
	 * Creates a new asset vocabulary depot entry rel with the primary key. Does not add the asset vocabulary depot entry rel to the database.
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key for the new asset vocabulary depot entry rel
	 * @return the new asset vocabulary depot entry rel
	 */
	public static AssetVocabularyDepotEntryRel create(
		long assetVocabularyDepotEntryRelId) {

		return getPersistence().create(assetVocabularyDepotEntryRelId);
	}

	/**
	 * Removes the asset vocabulary depot entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key of the asset vocabulary depot entry rel
	 * @return the asset vocabulary depot entry rel that was removed
	 * @throws NoSuchVocabularyDepotEntryRelException if a asset vocabulary depot entry rel with the primary key could not be found
	 */
	public static AssetVocabularyDepotEntryRel remove(
			long assetVocabularyDepotEntryRelId)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyDepotEntryRelException {

		return getPersistence().remove(assetVocabularyDepotEntryRelId);
	}

	public static AssetVocabularyDepotEntryRel updateImpl(
		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel) {

		return getPersistence().updateImpl(assetVocabularyDepotEntryRel);
	}

	/**
	 * Returns the asset vocabulary depot entry rel with the primary key or throws a <code>NoSuchVocabularyDepotEntryRelException</code> if it could not be found.
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key of the asset vocabulary depot entry rel
	 * @return the asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a asset vocabulary depot entry rel with the primary key could not be found
	 */
	public static AssetVocabularyDepotEntryRel findByPrimaryKey(
			long assetVocabularyDepotEntryRelId)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyDepotEntryRelException {

		return getPersistence().findByPrimaryKey(
			assetVocabularyDepotEntryRelId);
	}

	/**
	 * Returns the asset vocabulary depot entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key of the asset vocabulary depot entry rel
	 * @return the asset vocabulary depot entry rel, or <code>null</code> if a asset vocabulary depot entry rel with the primary key could not be found
	 */
	public static AssetVocabularyDepotEntryRel fetchByPrimaryKey(
		long assetVocabularyDepotEntryRelId) {

		return getPersistence().fetchByPrimaryKey(
			assetVocabularyDepotEntryRelId);
	}

	/**
	 * Returns all the asset vocabulary depot entry rels.
	 *
	 * @return the asset vocabulary depot entry rels
	 */
	public static List<AssetVocabularyDepotEntryRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the asset vocabulary depot entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset vocabulary depot entry rels
	 * @param end the upper bound of the range of asset vocabulary depot entry rels (not inclusive)
	 * @return the range of asset vocabulary depot entry rels
	 */
	public static List<AssetVocabularyDepotEntryRel> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary depot entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset vocabulary depot entry rels
	 * @param end the upper bound of the range of asset vocabulary depot entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset vocabulary depot entry rels
	 */
	public static List<AssetVocabularyDepotEntryRel> findAll(
		int start, int end,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary depot entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset vocabulary depot entry rels
	 * @param end the upper bound of the range of asset vocabulary depot entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset vocabulary depot entry rels
	 */
	public static List<AssetVocabularyDepotEntryRel> findAll(
		int start, int end,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the asset vocabulary depot entry rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of asset vocabulary depot entry rels.
	 *
	 * @return the number of asset vocabulary depot entry rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AssetVocabularyDepotEntryRelPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		AssetVocabularyDepotEntryRelPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile AssetVocabularyDepotEntryRelPersistence
		_persistence;

}