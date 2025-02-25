/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.service.persistence;

import com.liferay.asset.tags.exception.NoSuchDepotEntryRelException;
import com.liferay.asset.tags.model.AssetTagDepotEntryRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the asset tag depot entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagDepotEntryRelUtil
 * @generated
 */
@ProviderType
public interface AssetTagDepotEntryRelPersistence
	extends BasePersistence<AssetTagDepotEntryRel>,
			CTPersistence<AssetTagDepotEntryRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetTagDepotEntryRelUtil} to access the asset tag depot entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the asset tag depot entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset tag depot entry rels
	 */
	public java.util.List<AssetTagDepotEntryRel> findByUuid(String uuid);

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
	public java.util.List<AssetTagDepotEntryRel> findByUuid(
		String uuid, int start, int end);

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
	public java.util.List<AssetTagDepotEntryRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagDepotEntryRel>
			orderByComparator);

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
	public java.util.List<AssetTagDepotEntryRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagDepotEntryRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset tag depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	public AssetTagDepotEntryRel findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException;

	/**
	 * Returns the first asset tag depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	public AssetTagDepotEntryRel fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagDepotEntryRel>
			orderByComparator);

	/**
	 * Returns the last asset tag depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	public AssetTagDepotEntryRel findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException;

	/**
	 * Returns the last asset tag depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	public AssetTagDepotEntryRel fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagDepotEntryRel>
			orderByComparator);

	/**
	 * Returns the asset tag depot entry rels before and after the current asset tag depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param assetTagDepotEntryRelId the primary key of the current asset tag depot entry rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a asset tag depot entry rel with the primary key could not be found
	 */
	public AssetTagDepotEntryRel[] findByUuid_PrevAndNext(
			long assetTagDepotEntryRelId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException;

	/**
	 * Removes all the asset tag depot entry rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of asset tag depot entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset tag depot entry rels
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the asset tag depot entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset tag depot entry rels
	 */
	public java.util.List<AssetTagDepotEntryRel> findByUuid_C(
		String uuid, long companyId);

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
	public java.util.List<AssetTagDepotEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end);

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
	public java.util.List<AssetTagDepotEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagDepotEntryRel>
			orderByComparator);

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
	public java.util.List<AssetTagDepotEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagDepotEntryRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset tag depot entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	public AssetTagDepotEntryRel findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException;

	/**
	 * Returns the first asset tag depot entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	public AssetTagDepotEntryRel fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagDepotEntryRel>
			orderByComparator);

	/**
	 * Returns the last asset tag depot entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	public AssetTagDepotEntryRel findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException;

	/**
	 * Returns the last asset tag depot entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	public AssetTagDepotEntryRel fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagDepotEntryRel>
			orderByComparator);

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
	public AssetTagDepotEntryRel[] findByUuid_C_PrevAndNext(
			long assetTagDepotEntryRelId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException;

	/**
	 * Removes all the asset tag depot entry rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of asset tag depot entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset tag depot entry rels
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the asset tag depot entry rels where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @return the matching asset tag depot entry rels
	 */
	public java.util.List<AssetTagDepotEntryRel> findByAssetTagId(
		long assetTagId);

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
	public java.util.List<AssetTagDepotEntryRel> findByAssetTagId(
		long assetTagId, int start, int end);

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
	public java.util.List<AssetTagDepotEntryRel> findByAssetTagId(
		long assetTagId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagDepotEntryRel>
			orderByComparator);

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
	public java.util.List<AssetTagDepotEntryRel> findByAssetTagId(
		long assetTagId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagDepotEntryRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset tag depot entry rel in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	public AssetTagDepotEntryRel findByAssetTagId_First(
			long assetTagId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException;

	/**
	 * Returns the first asset tag depot entry rel in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	public AssetTagDepotEntryRel fetchByAssetTagId_First(
		long assetTagId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagDepotEntryRel>
			orderByComparator);

	/**
	 * Returns the last asset tag depot entry rel in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	public AssetTagDepotEntryRel findByAssetTagId_Last(
			long assetTagId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException;

	/**
	 * Returns the last asset tag depot entry rel in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	public AssetTagDepotEntryRel fetchByAssetTagId_Last(
		long assetTagId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagDepotEntryRel>
			orderByComparator);

	/**
	 * Returns the asset tag depot entry rels before and after the current asset tag depot entry rel in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagDepotEntryRelId the primary key of the current asset tag depot entry rel
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a asset tag depot entry rel with the primary key could not be found
	 */
	public AssetTagDepotEntryRel[] findByAssetTagId_PrevAndNext(
			long assetTagDepotEntryRelId, long assetTagId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException;

	/**
	 * Removes all the asset tag depot entry rels where assetTagId = &#63; from the database.
	 *
	 * @param assetTagId the asset tag ID
	 */
	public void removeByAssetTagId(long assetTagId);

	/**
	 * Returns the number of asset tag depot entry rels where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @return the number of matching asset tag depot entry rels
	 */
	public int countByAssetTagId(long assetTagId);

	/**
	 * Returns all the asset tag depot entry rels where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the matching asset tag depot entry rels
	 */
	public java.util.List<AssetTagDepotEntryRel> findByDepotEntryId(
		long depotEntryId);

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
	public java.util.List<AssetTagDepotEntryRel> findByDepotEntryId(
		long depotEntryId, int start, int end);

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
	public java.util.List<AssetTagDepotEntryRel> findByDepotEntryId(
		long depotEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagDepotEntryRel>
			orderByComparator);

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
	public java.util.List<AssetTagDepotEntryRel> findByDepotEntryId(
		long depotEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagDepotEntryRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset tag depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	public AssetTagDepotEntryRel findByDepotEntryId_First(
			long depotEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException;

	/**
	 * Returns the first asset tag depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	public AssetTagDepotEntryRel fetchByDepotEntryId_First(
		long depotEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagDepotEntryRel>
			orderByComparator);

	/**
	 * Returns the last asset tag depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	public AssetTagDepotEntryRel findByDepotEntryId_Last(
			long depotEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException;

	/**
	 * Returns the last asset tag depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	public AssetTagDepotEntryRel fetchByDepotEntryId_Last(
		long depotEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagDepotEntryRel>
			orderByComparator);

	/**
	 * Returns the asset tag depot entry rels before and after the current asset tag depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param assetTagDepotEntryRelId the primary key of the current asset tag depot entry rel
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a asset tag depot entry rel with the primary key could not be found
	 */
	public AssetTagDepotEntryRel[] findByDepotEntryId_PrevAndNext(
			long assetTagDepotEntryRelId, long depotEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException;

	/**
	 * Removes all the asset tag depot entry rels where depotEntryId = &#63; from the database.
	 *
	 * @param depotEntryId the depot entry ID
	 */
	public void removeByDepotEntryId(long depotEntryId);

	/**
	 * Returns the number of asset tag depot entry rels where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the number of matching asset tag depot entry rels
	 */
	public int countByDepotEntryId(long depotEntryId);

	/**
	 * Returns the asset tag depot entry rel where assetTagId = &#63; and depotEntryId = &#63; or throws a <code>NoSuchDepotEntryRelException</code> if it could not be found.
	 *
	 * @param assetTagId the asset tag ID
	 * @param depotEntryId the depot entry ID
	 * @return the matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	public AssetTagDepotEntryRel findByAVI_DEI(
			long assetTagId, long depotEntryId)
		throws NoSuchDepotEntryRelException;

	/**
	 * Returns the asset tag depot entry rel where assetTagId = &#63; and depotEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetTagId the asset tag ID
	 * @param depotEntryId the depot entry ID
	 * @return the matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	public AssetTagDepotEntryRel fetchByAVI_DEI(
		long assetTagId, long depotEntryId);

	/**
	 * Returns the asset tag depot entry rel where assetTagId = &#63; and depotEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetTagId the asset tag ID
	 * @param depotEntryId the depot entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	public AssetTagDepotEntryRel fetchByAVI_DEI(
		long assetTagId, long depotEntryId, boolean useFinderCache);

	/**
	 * Removes the asset tag depot entry rel where assetTagId = &#63; and depotEntryId = &#63; from the database.
	 *
	 * @param assetTagId the asset tag ID
	 * @param depotEntryId the depot entry ID
	 * @return the asset tag depot entry rel that was removed
	 */
	public AssetTagDepotEntryRel removeByAVI_DEI(
			long assetTagId, long depotEntryId)
		throws NoSuchDepotEntryRelException;

	/**
	 * Returns the number of asset tag depot entry rels where assetTagId = &#63; and depotEntryId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param depotEntryId the depot entry ID
	 * @return the number of matching asset tag depot entry rels
	 */
	public int countByAVI_DEI(long assetTagId, long depotEntryId);

	/**
	 * Caches the asset tag depot entry rel in the entity cache if it is enabled.
	 *
	 * @param assetTagDepotEntryRel the asset tag depot entry rel
	 */
	public void cacheResult(AssetTagDepotEntryRel assetTagDepotEntryRel);

	/**
	 * Caches the asset tag depot entry rels in the entity cache if it is enabled.
	 *
	 * @param assetTagDepotEntryRels the asset tag depot entry rels
	 */
	public void cacheResult(
		java.util.List<AssetTagDepotEntryRel> assetTagDepotEntryRels);

	/**
	 * Creates a new asset tag depot entry rel with the primary key. Does not add the asset tag depot entry rel to the database.
	 *
	 * @param assetTagDepotEntryRelId the primary key for the new asset tag depot entry rel
	 * @return the new asset tag depot entry rel
	 */
	public AssetTagDepotEntryRel create(long assetTagDepotEntryRelId);

	/**
	 * Removes the asset tag depot entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetTagDepotEntryRelId the primary key of the asset tag depot entry rel
	 * @return the asset tag depot entry rel that was removed
	 * @throws NoSuchDepotEntryRelException if a asset tag depot entry rel with the primary key could not be found
	 */
	public AssetTagDepotEntryRel remove(long assetTagDepotEntryRelId)
		throws NoSuchDepotEntryRelException;

	public AssetTagDepotEntryRel updateImpl(
		AssetTagDepotEntryRel assetTagDepotEntryRel);

	/**
	 * Returns the asset tag depot entry rel with the primary key or throws a <code>NoSuchDepotEntryRelException</code> if it could not be found.
	 *
	 * @param assetTagDepotEntryRelId the primary key of the asset tag depot entry rel
	 * @return the asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a asset tag depot entry rel with the primary key could not be found
	 */
	public AssetTagDepotEntryRel findByPrimaryKey(long assetTagDepotEntryRelId)
		throws NoSuchDepotEntryRelException;

	/**
	 * Returns the asset tag depot entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetTagDepotEntryRelId the primary key of the asset tag depot entry rel
	 * @return the asset tag depot entry rel, or <code>null</code> if a asset tag depot entry rel with the primary key could not be found
	 */
	public AssetTagDepotEntryRel fetchByPrimaryKey(
		long assetTagDepotEntryRelId);

	/**
	 * Returns all the asset tag depot entry rels.
	 *
	 * @return the asset tag depot entry rels
	 */
	public java.util.List<AssetTagDepotEntryRel> findAll();

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
	public java.util.List<AssetTagDepotEntryRel> findAll(int start, int end);

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
	public java.util.List<AssetTagDepotEntryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagDepotEntryRel>
			orderByComparator);

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
	public java.util.List<AssetTagDepotEntryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetTagDepotEntryRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the asset tag depot entry rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of asset tag depot entry rels.
	 *
	 * @return the number of asset tag depot entry rels
	 */
	public int countAll();

}