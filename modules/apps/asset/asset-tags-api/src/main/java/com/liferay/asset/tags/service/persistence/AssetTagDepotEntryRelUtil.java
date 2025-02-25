/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.service.persistence;

import com.liferay.asset.tags.model.AssetTagDepotEntryRel;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the asset tag depot entry rel service. This utility wraps <code>com.liferay.asset.tags.service.persistence.impl.AssetTagDepotEntryRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagDepotEntryRelPersistence
 * @generated
 */
public class AssetTagDepotEntryRelUtil {

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
	public static void clearCache(AssetTagDepotEntryRel assetTagDepotEntryRel) {
		getPersistence().clearCache(assetTagDepotEntryRel);
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
	public static Map<Serializable, AssetTagDepotEntryRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AssetTagDepotEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetTagDepotEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetTagDepotEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AssetTagDepotEntryRel update(
		AssetTagDepotEntryRel assetTagDepotEntryRel) {

		return getPersistence().update(assetTagDepotEntryRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AssetTagDepotEntryRel update(
		AssetTagDepotEntryRel assetTagDepotEntryRel,
		ServiceContext serviceContext) {

		return getPersistence().update(assetTagDepotEntryRel, serviceContext);
	}

	/**
	 * Returns all the asset tag depot entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset tag depot entry rels
	 */
	public static List<AssetTagDepotEntryRel> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the asset tag depot entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset tag depot entry rels
	 * @param end the upper bound of the range of asset tag depot entry rels (not inclusive)
	 * @return the range of matching asset tag depot entry rels
	 */
	public static List<AssetTagDepotEntryRel> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the asset tag depot entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset tag depot entry rels
	 * @param end the upper bound of the range of asset tag depot entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tag depot entry rels
	 */
	public static List<AssetTagDepotEntryRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset tag depot entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset tag depot entry rels
	 * @param end the upper bound of the range of asset tag depot entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tag depot entry rels
	 */
	public static List<AssetTagDepotEntryRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset tag depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	public static AssetTagDepotEntryRel findByUuid_First(
			String uuid,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws com.liferay.asset.tags.exception.NoSuchDepotEntryRelException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first asset tag depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	public static AssetTagDepotEntryRel fetchByUuid_First(
		String uuid,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last asset tag depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	public static AssetTagDepotEntryRel findByUuid_Last(
			String uuid,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws com.liferay.asset.tags.exception.NoSuchDepotEntryRelException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last asset tag depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	public static AssetTagDepotEntryRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the asset tag depot entry rels before and after the current asset tag depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param assetTagDepotEntryRelId the primary key of the current asset tag depot entry rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a asset tag depot entry rel with the primary key could not be found
	 */
	public static AssetTagDepotEntryRel[] findByUuid_PrevAndNext(
			long assetTagDepotEntryRelId, String uuid,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws com.liferay.asset.tags.exception.NoSuchDepotEntryRelException {

		return getPersistence().findByUuid_PrevAndNext(
			assetTagDepotEntryRelId, uuid, orderByComparator);
	}

	/**
	 * Removes all the asset tag depot entry rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of asset tag depot entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset tag depot entry rels
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the asset tag depot entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset tag depot entry rels
	 */
	public static List<AssetTagDepotEntryRel> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the asset tag depot entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset tag depot entry rels
	 * @param end the upper bound of the range of asset tag depot entry rels (not inclusive)
	 * @return the range of matching asset tag depot entry rels
	 */
	public static List<AssetTagDepotEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the asset tag depot entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset tag depot entry rels
	 * @param end the upper bound of the range of asset tag depot entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tag depot entry rels
	 */
	public static List<AssetTagDepotEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset tag depot entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset tag depot entry rels
	 * @param end the upper bound of the range of asset tag depot entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tag depot entry rels
	 */
	public static List<AssetTagDepotEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset tag depot entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	public static AssetTagDepotEntryRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws com.liferay.asset.tags.exception.NoSuchDepotEntryRelException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first asset tag depot entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	public static AssetTagDepotEntryRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last asset tag depot entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	public static AssetTagDepotEntryRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws com.liferay.asset.tags.exception.NoSuchDepotEntryRelException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last asset tag depot entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	public static AssetTagDepotEntryRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the asset tag depot entry rels before and after the current asset tag depot entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param assetTagDepotEntryRelId the primary key of the current asset tag depot entry rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a asset tag depot entry rel with the primary key could not be found
	 */
	public static AssetTagDepotEntryRel[] findByUuid_C_PrevAndNext(
			long assetTagDepotEntryRelId, String uuid, long companyId,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws com.liferay.asset.tags.exception.NoSuchDepotEntryRelException {

		return getPersistence().findByUuid_C_PrevAndNext(
			assetTagDepotEntryRelId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the asset tag depot entry rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of asset tag depot entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset tag depot entry rels
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the asset tag depot entry rels where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @return the matching asset tag depot entry rels
	 */
	public static List<AssetTagDepotEntryRel> findByAssetTagId(
		long assetTagId) {

		return getPersistence().findByAssetTagId(assetTagId);
	}

	/**
	 * Returns a range of all the asset tag depot entry rels where assetTagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetTagId the asset tag ID
	 * @param start the lower bound of the range of asset tag depot entry rels
	 * @param end the upper bound of the range of asset tag depot entry rels (not inclusive)
	 * @return the range of matching asset tag depot entry rels
	 */
	public static List<AssetTagDepotEntryRel> findByAssetTagId(
		long assetTagId, int start, int end) {

		return getPersistence().findByAssetTagId(assetTagId, start, end);
	}

	/**
	 * Returns an ordered range of all the asset tag depot entry rels where assetTagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetTagId the asset tag ID
	 * @param start the lower bound of the range of asset tag depot entry rels
	 * @param end the upper bound of the range of asset tag depot entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tag depot entry rels
	 */
	public static List<AssetTagDepotEntryRel> findByAssetTagId(
		long assetTagId, int start, int end,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		return getPersistence().findByAssetTagId(
			assetTagId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset tag depot entry rels where assetTagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param assetTagId the asset tag ID
	 * @param start the lower bound of the range of asset tag depot entry rels
	 * @param end the upper bound of the range of asset tag depot entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tag depot entry rels
	 */
	public static List<AssetTagDepotEntryRel> findByAssetTagId(
		long assetTagId, int start, int end,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByAssetTagId(
			assetTagId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset tag depot entry rel in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	public static AssetTagDepotEntryRel findByAssetTagId_First(
			long assetTagId,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws com.liferay.asset.tags.exception.NoSuchDepotEntryRelException {

		return getPersistence().findByAssetTagId_First(
			assetTagId, orderByComparator);
	}

	/**
	 * Returns the first asset tag depot entry rel in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	public static AssetTagDepotEntryRel fetchByAssetTagId_First(
		long assetTagId,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		return getPersistence().fetchByAssetTagId_First(
			assetTagId, orderByComparator);
	}

	/**
	 * Returns the last asset tag depot entry rel in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	public static AssetTagDepotEntryRel findByAssetTagId_Last(
			long assetTagId,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws com.liferay.asset.tags.exception.NoSuchDepotEntryRelException {

		return getPersistence().findByAssetTagId_Last(
			assetTagId, orderByComparator);
	}

	/**
	 * Returns the last asset tag depot entry rel in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	public static AssetTagDepotEntryRel fetchByAssetTagId_Last(
		long assetTagId,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		return getPersistence().fetchByAssetTagId_Last(
			assetTagId, orderByComparator);
	}

	/**
	 * Returns the asset tag depot entry rels before and after the current asset tag depot entry rel in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagDepotEntryRelId the primary key of the current asset tag depot entry rel
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a asset tag depot entry rel with the primary key could not be found
	 */
	public static AssetTagDepotEntryRel[] findByAssetTagId_PrevAndNext(
			long assetTagDepotEntryRelId, long assetTagId,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws com.liferay.asset.tags.exception.NoSuchDepotEntryRelException {

		return getPersistence().findByAssetTagId_PrevAndNext(
			assetTagDepotEntryRelId, assetTagId, orderByComparator);
	}

	/**
	 * Removes all the asset tag depot entry rels where assetTagId = &#63; from the database.
	 *
	 * @param assetTagId the asset tag ID
	 */
	public static void removeByAssetTagId(long assetTagId) {
		getPersistence().removeByAssetTagId(assetTagId);
	}

	/**
	 * Returns the number of asset tag depot entry rels where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @return the number of matching asset tag depot entry rels
	 */
	public static int countByAssetTagId(long assetTagId) {
		return getPersistence().countByAssetTagId(assetTagId);
	}

	/**
	 * Returns all the asset tag depot entry rels where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the matching asset tag depot entry rels
	 */
	public static List<AssetTagDepotEntryRel> findByDepotEntryId(
		long depotEntryId) {

		return getPersistence().findByDepotEntryId(depotEntryId);
	}

	/**
	 * Returns a range of all the asset tag depot entry rels where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of asset tag depot entry rels
	 * @param end the upper bound of the range of asset tag depot entry rels (not inclusive)
	 * @return the range of matching asset tag depot entry rels
	 */
	public static List<AssetTagDepotEntryRel> findByDepotEntryId(
		long depotEntryId, int start, int end) {

		return getPersistence().findByDepotEntryId(depotEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the asset tag depot entry rels where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of asset tag depot entry rels
	 * @param end the upper bound of the range of asset tag depot entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tag depot entry rels
	 */
	public static List<AssetTagDepotEntryRel> findByDepotEntryId(
		long depotEntryId, int start, int end,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		return getPersistence().findByDepotEntryId(
			depotEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset tag depot entry rels where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of asset tag depot entry rels
	 * @param end the upper bound of the range of asset tag depot entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tag depot entry rels
	 */
	public static List<AssetTagDepotEntryRel> findByDepotEntryId(
		long depotEntryId, int start, int end,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByDepotEntryId(
			depotEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset tag depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	public static AssetTagDepotEntryRel findByDepotEntryId_First(
			long depotEntryId,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws com.liferay.asset.tags.exception.NoSuchDepotEntryRelException {

		return getPersistence().findByDepotEntryId_First(
			depotEntryId, orderByComparator);
	}

	/**
	 * Returns the first asset tag depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	public static AssetTagDepotEntryRel fetchByDepotEntryId_First(
		long depotEntryId,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		return getPersistence().fetchByDepotEntryId_First(
			depotEntryId, orderByComparator);
	}

	/**
	 * Returns the last asset tag depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	public static AssetTagDepotEntryRel findByDepotEntryId_Last(
			long depotEntryId,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws com.liferay.asset.tags.exception.NoSuchDepotEntryRelException {

		return getPersistence().findByDepotEntryId_Last(
			depotEntryId, orderByComparator);
	}

	/**
	 * Returns the last asset tag depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	public static AssetTagDepotEntryRel fetchByDepotEntryId_Last(
		long depotEntryId,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		return getPersistence().fetchByDepotEntryId_Last(
			depotEntryId, orderByComparator);
	}

	/**
	 * Returns the asset tag depot entry rels before and after the current asset tag depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param assetTagDepotEntryRelId the primary key of the current asset tag depot entry rel
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a asset tag depot entry rel with the primary key could not be found
	 */
	public static AssetTagDepotEntryRel[] findByDepotEntryId_PrevAndNext(
			long assetTagDepotEntryRelId, long depotEntryId,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws com.liferay.asset.tags.exception.NoSuchDepotEntryRelException {

		return getPersistence().findByDepotEntryId_PrevAndNext(
			assetTagDepotEntryRelId, depotEntryId, orderByComparator);
	}

	/**
	 * Removes all the asset tag depot entry rels where depotEntryId = &#63; from the database.
	 *
	 * @param depotEntryId the depot entry ID
	 */
	public static void removeByDepotEntryId(long depotEntryId) {
		getPersistence().removeByDepotEntryId(depotEntryId);
	}

	/**
	 * Returns the number of asset tag depot entry rels where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the number of matching asset tag depot entry rels
	 */
	public static int countByDepotEntryId(long depotEntryId) {
		return getPersistence().countByDepotEntryId(depotEntryId);
	}

	/**
	 * Returns the asset tag depot entry rel where assetTagId = &#63; and depotEntryId = &#63; or throws a <code>NoSuchDepotEntryRelException</code> if it could not be found.
	 *
	 * @param assetTagId the asset tag ID
	 * @param depotEntryId the depot entry ID
	 * @return the matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	public static AssetTagDepotEntryRel findByAVI_DEI(
			long assetTagId, long depotEntryId)
		throws com.liferay.asset.tags.exception.NoSuchDepotEntryRelException {

		return getPersistence().findByAVI_DEI(assetTagId, depotEntryId);
	}

	/**
	 * Returns the asset tag depot entry rel where assetTagId = &#63; and depotEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetTagId the asset tag ID
	 * @param depotEntryId the depot entry ID
	 * @return the matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	public static AssetTagDepotEntryRel fetchByAVI_DEI(
		long assetTagId, long depotEntryId) {

		return getPersistence().fetchByAVI_DEI(assetTagId, depotEntryId);
	}

	/**
	 * Returns the asset tag depot entry rel where assetTagId = &#63; and depotEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetTagId the asset tag ID
	 * @param depotEntryId the depot entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	public static AssetTagDepotEntryRel fetchByAVI_DEI(
		long assetTagId, long depotEntryId, boolean useFinderCache) {

		return getPersistence().fetchByAVI_DEI(
			assetTagId, depotEntryId, useFinderCache);
	}

	/**
	 * Removes the asset tag depot entry rel where assetTagId = &#63; and depotEntryId = &#63; from the database.
	 *
	 * @param assetTagId the asset tag ID
	 * @param depotEntryId the depot entry ID
	 * @return the asset tag depot entry rel that was removed
	 */
	public static AssetTagDepotEntryRel removeByAVI_DEI(
			long assetTagId, long depotEntryId)
		throws com.liferay.asset.tags.exception.NoSuchDepotEntryRelException {

		return getPersistence().removeByAVI_DEI(assetTagId, depotEntryId);
	}

	/**
	 * Returns the number of asset tag depot entry rels where assetTagId = &#63; and depotEntryId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param depotEntryId the depot entry ID
	 * @return the number of matching asset tag depot entry rels
	 */
	public static int countByAVI_DEI(long assetTagId, long depotEntryId) {
		return getPersistence().countByAVI_DEI(assetTagId, depotEntryId);
	}

	/**
	 * Caches the asset tag depot entry rel in the entity cache if it is enabled.
	 *
	 * @param assetTagDepotEntryRel the asset tag depot entry rel
	 */
	public static void cacheResult(
		AssetTagDepotEntryRel assetTagDepotEntryRel) {

		getPersistence().cacheResult(assetTagDepotEntryRel);
	}

	/**
	 * Caches the asset tag depot entry rels in the entity cache if it is enabled.
	 *
	 * @param assetTagDepotEntryRels the asset tag depot entry rels
	 */
	public static void cacheResult(
		List<AssetTagDepotEntryRel> assetTagDepotEntryRels) {

		getPersistence().cacheResult(assetTagDepotEntryRels);
	}

	/**
	 * Creates a new asset tag depot entry rel with the primary key. Does not add the asset tag depot entry rel to the database.
	 *
	 * @param assetTagDepotEntryRelId the primary key for the new asset tag depot entry rel
	 * @return the new asset tag depot entry rel
	 */
	public static AssetTagDepotEntryRel create(long assetTagDepotEntryRelId) {
		return getPersistence().create(assetTagDepotEntryRelId);
	}

	/**
	 * Removes the asset tag depot entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetTagDepotEntryRelId the primary key of the asset tag depot entry rel
	 * @return the asset tag depot entry rel that was removed
	 * @throws NoSuchDepotEntryRelException if a asset tag depot entry rel with the primary key could not be found
	 */
	public static AssetTagDepotEntryRel remove(long assetTagDepotEntryRelId)
		throws com.liferay.asset.tags.exception.NoSuchDepotEntryRelException {

		return getPersistence().remove(assetTagDepotEntryRelId);
	}

	public static AssetTagDepotEntryRel updateImpl(
		AssetTagDepotEntryRel assetTagDepotEntryRel) {

		return getPersistence().updateImpl(assetTagDepotEntryRel);
	}

	/**
	 * Returns the asset tag depot entry rel with the primary key or throws a <code>NoSuchDepotEntryRelException</code> if it could not be found.
	 *
	 * @param assetTagDepotEntryRelId the primary key of the asset tag depot entry rel
	 * @return the asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a asset tag depot entry rel with the primary key could not be found
	 */
	public static AssetTagDepotEntryRel findByPrimaryKey(
			long assetTagDepotEntryRelId)
		throws com.liferay.asset.tags.exception.NoSuchDepotEntryRelException {

		return getPersistence().findByPrimaryKey(assetTagDepotEntryRelId);
	}

	/**
	 * Returns the asset tag depot entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetTagDepotEntryRelId the primary key of the asset tag depot entry rel
	 * @return the asset tag depot entry rel, or <code>null</code> if a asset tag depot entry rel with the primary key could not be found
	 */
	public static AssetTagDepotEntryRel fetchByPrimaryKey(
		long assetTagDepotEntryRelId) {

		return getPersistence().fetchByPrimaryKey(assetTagDepotEntryRelId);
	}

	/**
	 * Returns all the asset tag depot entry rels.
	 *
	 * @return the asset tag depot entry rels
	 */
	public static List<AssetTagDepotEntryRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the asset tag depot entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset tag depot entry rels
	 * @param end the upper bound of the range of asset tag depot entry rels (not inclusive)
	 * @return the range of asset tag depot entry rels
	 */
	public static List<AssetTagDepotEntryRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the asset tag depot entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset tag depot entry rels
	 * @param end the upper bound of the range of asset tag depot entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset tag depot entry rels
	 */
	public static List<AssetTagDepotEntryRel> findAll(
		int start, int end,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset tag depot entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagDepotEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset tag depot entry rels
	 * @param end the upper bound of the range of asset tag depot entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset tag depot entry rels
	 */
	public static List<AssetTagDepotEntryRel> findAll(
		int start, int end,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the asset tag depot entry rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of asset tag depot entry rels.
	 *
	 * @return the number of asset tag depot entry rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AssetTagDepotEntryRelPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		AssetTagDepotEntryRelPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile AssetTagDepotEntryRelPersistence _persistence;

}