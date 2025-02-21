/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.service.persistence;

import com.liferay.asset.kernel.exception.NoSuchVocabularyDepotEntryRelException;
import com.liferay.asset.kernel.model.AssetVocabularyDepotEntryRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the asset vocabulary depot entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyDepotEntryRelUtil
 * @generated
 */
@ProviderType
public interface AssetVocabularyDepotEntryRelPersistence
	extends BasePersistence<AssetVocabularyDepotEntryRel>,
			CTPersistence<AssetVocabularyDepotEntryRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetVocabularyDepotEntryRelUtil} to access the asset vocabulary depot entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the asset vocabulary depot entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset vocabulary depot entry rels
	 */
	public java.util.List<AssetVocabularyDepotEntryRel> findByUuid(String uuid);

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
	public java.util.List<AssetVocabularyDepotEntryRel> findByUuid(
		String uuid, int start, int end);

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
	public java.util.List<AssetVocabularyDepotEntryRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetVocabularyDepotEntryRel> orderByComparator);

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
	public java.util.List<AssetVocabularyDepotEntryRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetVocabularyDepotEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset vocabulary depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	public AssetVocabularyDepotEntryRel findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException;

	/**
	 * Returns the first asset vocabulary depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	public AssetVocabularyDepotEntryRel fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetVocabularyDepotEntryRel> orderByComparator);

	/**
	 * Returns the last asset vocabulary depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	public AssetVocabularyDepotEntryRel findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException;

	/**
	 * Returns the last asset vocabulary depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	public AssetVocabularyDepotEntryRel fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetVocabularyDepotEntryRel> orderByComparator);

	/**
	 * Returns the asset vocabulary depot entry rels before and after the current asset vocabulary depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key of the current asset vocabulary depot entry rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a asset vocabulary depot entry rel with the primary key could not be found
	 */
	public AssetVocabularyDepotEntryRel[] findByUuid_PrevAndNext(
			long assetVocabularyDepotEntryRelId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException;

	/**
	 * Removes all the asset vocabulary depot entry rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of asset vocabulary depot entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset vocabulary depot entry rels
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the asset vocabulary depot entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset vocabulary depot entry rels
	 */
	public java.util.List<AssetVocabularyDepotEntryRel> findByUuid_C(
		String uuid, long companyId);

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
	public java.util.List<AssetVocabularyDepotEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end);

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
	public java.util.List<AssetVocabularyDepotEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetVocabularyDepotEntryRel> orderByComparator);

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
	public java.util.List<AssetVocabularyDepotEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetVocabularyDepotEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset vocabulary depot entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	public AssetVocabularyDepotEntryRel findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException;

	/**
	 * Returns the first asset vocabulary depot entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	public AssetVocabularyDepotEntryRel fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetVocabularyDepotEntryRel> orderByComparator);

	/**
	 * Returns the last asset vocabulary depot entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	public AssetVocabularyDepotEntryRel findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException;

	/**
	 * Returns the last asset vocabulary depot entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	public AssetVocabularyDepotEntryRel fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetVocabularyDepotEntryRel> orderByComparator);

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
	public AssetVocabularyDepotEntryRel[] findByUuid_C_PrevAndNext(
			long assetVocabularyDepotEntryRelId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException;

	/**
	 * Removes all the asset vocabulary depot entry rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of asset vocabulary depot entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset vocabulary depot entry rels
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the asset vocabulary depot entry rels where assetVocabularyId = &#63;.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @return the matching asset vocabulary depot entry rels
	 */
	public java.util.List<AssetVocabularyDepotEntryRel> findByAssetVocabularyId(
		long assetVocabularyId);

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
	public java.util.List<AssetVocabularyDepotEntryRel> findByAssetVocabularyId(
		long assetVocabularyId, int start, int end);

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
	public java.util.List<AssetVocabularyDepotEntryRel> findByAssetVocabularyId(
		long assetVocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetVocabularyDepotEntryRel> orderByComparator);

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
	public java.util.List<AssetVocabularyDepotEntryRel> findByAssetVocabularyId(
		long assetVocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetVocabularyDepotEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset vocabulary depot entry rel in the ordered set where assetVocabularyId = &#63;.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	public AssetVocabularyDepotEntryRel findByAssetVocabularyId_First(
			long assetVocabularyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException;

	/**
	 * Returns the first asset vocabulary depot entry rel in the ordered set where assetVocabularyId = &#63;.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	public AssetVocabularyDepotEntryRel fetchByAssetVocabularyId_First(
		long assetVocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetVocabularyDepotEntryRel> orderByComparator);

	/**
	 * Returns the last asset vocabulary depot entry rel in the ordered set where assetVocabularyId = &#63;.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	public AssetVocabularyDepotEntryRel findByAssetVocabularyId_Last(
			long assetVocabularyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException;

	/**
	 * Returns the last asset vocabulary depot entry rel in the ordered set where assetVocabularyId = &#63;.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	public AssetVocabularyDepotEntryRel fetchByAssetVocabularyId_Last(
		long assetVocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetVocabularyDepotEntryRel> orderByComparator);

	/**
	 * Returns the asset vocabulary depot entry rels before and after the current asset vocabulary depot entry rel in the ordered set where assetVocabularyId = &#63;.
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key of the current asset vocabulary depot entry rel
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a asset vocabulary depot entry rel with the primary key could not be found
	 */
	public AssetVocabularyDepotEntryRel[] findByAssetVocabularyId_PrevAndNext(
			long assetVocabularyDepotEntryRelId, long assetVocabularyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException;

	/**
	 * Removes all the asset vocabulary depot entry rels where assetVocabularyId = &#63; from the database.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 */
	public void removeByAssetVocabularyId(long assetVocabularyId);

	/**
	 * Returns the number of asset vocabulary depot entry rels where assetVocabularyId = &#63;.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @return the number of matching asset vocabulary depot entry rels
	 */
	public int countByAssetVocabularyId(long assetVocabularyId);

	/**
	 * Returns all the asset vocabulary depot entry rels where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the matching asset vocabulary depot entry rels
	 */
	public java.util.List<AssetVocabularyDepotEntryRel> findByDepotEntryId(
		long depotEntryId);

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
	public java.util.List<AssetVocabularyDepotEntryRel> findByDepotEntryId(
		long depotEntryId, int start, int end);

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
	public java.util.List<AssetVocabularyDepotEntryRel> findByDepotEntryId(
		long depotEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetVocabularyDepotEntryRel> orderByComparator);

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
	public java.util.List<AssetVocabularyDepotEntryRel> findByDepotEntryId(
		long depotEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetVocabularyDepotEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first asset vocabulary depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	public AssetVocabularyDepotEntryRel findByDepotEntryId_First(
			long depotEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException;

	/**
	 * Returns the first asset vocabulary depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	public AssetVocabularyDepotEntryRel fetchByDepotEntryId_First(
		long depotEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetVocabularyDepotEntryRel> orderByComparator);

	/**
	 * Returns the last asset vocabulary depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	public AssetVocabularyDepotEntryRel findByDepotEntryId_Last(
			long depotEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException;

	/**
	 * Returns the last asset vocabulary depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	public AssetVocabularyDepotEntryRel fetchByDepotEntryId_Last(
		long depotEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetVocabularyDepotEntryRel> orderByComparator);

	/**
	 * Returns the asset vocabulary depot entry rels before and after the current asset vocabulary depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key of the current asset vocabulary depot entry rel
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a asset vocabulary depot entry rel with the primary key could not be found
	 */
	public AssetVocabularyDepotEntryRel[] findByDepotEntryId_PrevAndNext(
			long assetVocabularyDepotEntryRelId, long depotEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException;

	/**
	 * Removes all the asset vocabulary depot entry rels where depotEntryId = &#63; from the database.
	 *
	 * @param depotEntryId the depot entry ID
	 */
	public void removeByDepotEntryId(long depotEntryId);

	/**
	 * Returns the number of asset vocabulary depot entry rels where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the number of matching asset vocabulary depot entry rels
	 */
	public int countByDepotEntryId(long depotEntryId);

	/**
	 * Returns the asset vocabulary depot entry rel where assetVocabularyId = &#63; and depotEntryId = &#63; or throws a <code>NoSuchVocabularyDepotEntryRelException</code> if it could not be found.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param depotEntryId the depot entry ID
	 * @return the matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	public AssetVocabularyDepotEntryRel findByAVI_DEI(
			long assetVocabularyId, long depotEntryId)
		throws NoSuchVocabularyDepotEntryRelException;

	/**
	 * Returns the asset vocabulary depot entry rel where assetVocabularyId = &#63; and depotEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param depotEntryId the depot entry ID
	 * @return the matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	public AssetVocabularyDepotEntryRel fetchByAVI_DEI(
		long assetVocabularyId, long depotEntryId);

	/**
	 * Returns the asset vocabulary depot entry rel where assetVocabularyId = &#63; and depotEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param depotEntryId the depot entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	public AssetVocabularyDepotEntryRel fetchByAVI_DEI(
		long assetVocabularyId, long depotEntryId, boolean useFinderCache);

	/**
	 * Removes the asset vocabulary depot entry rel where assetVocabularyId = &#63; and depotEntryId = &#63; from the database.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param depotEntryId the depot entry ID
	 * @return the asset vocabulary depot entry rel that was removed
	 */
	public AssetVocabularyDepotEntryRel removeByAVI_DEI(
			long assetVocabularyId, long depotEntryId)
		throws NoSuchVocabularyDepotEntryRelException;

	/**
	 * Returns the number of asset vocabulary depot entry rels where assetVocabularyId = &#63; and depotEntryId = &#63;.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param depotEntryId the depot entry ID
	 * @return the number of matching asset vocabulary depot entry rels
	 */
	public int countByAVI_DEI(long assetVocabularyId, long depotEntryId);

	/**
	 * Caches the asset vocabulary depot entry rel in the entity cache if it is enabled.
	 *
	 * @param assetVocabularyDepotEntryRel the asset vocabulary depot entry rel
	 */
	public void cacheResult(
		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel);

	/**
	 * Caches the asset vocabulary depot entry rels in the entity cache if it is enabled.
	 *
	 * @param assetVocabularyDepotEntryRels the asset vocabulary depot entry rels
	 */
	public void cacheResult(
		java.util.List<AssetVocabularyDepotEntryRel>
			assetVocabularyDepotEntryRels);

	/**
	 * Creates a new asset vocabulary depot entry rel with the primary key. Does not add the asset vocabulary depot entry rel to the database.
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key for the new asset vocabulary depot entry rel
	 * @return the new asset vocabulary depot entry rel
	 */
	public AssetVocabularyDepotEntryRel create(
		long assetVocabularyDepotEntryRelId);

	/**
	 * Removes the asset vocabulary depot entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key of the asset vocabulary depot entry rel
	 * @return the asset vocabulary depot entry rel that was removed
	 * @throws NoSuchVocabularyDepotEntryRelException if a asset vocabulary depot entry rel with the primary key could not be found
	 */
	public AssetVocabularyDepotEntryRel remove(
			long assetVocabularyDepotEntryRelId)
		throws NoSuchVocabularyDepotEntryRelException;

	public AssetVocabularyDepotEntryRel updateImpl(
		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel);

	/**
	 * Returns the asset vocabulary depot entry rel with the primary key or throws a <code>NoSuchVocabularyDepotEntryRelException</code> if it could not be found.
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key of the asset vocabulary depot entry rel
	 * @return the asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a asset vocabulary depot entry rel with the primary key could not be found
	 */
	public AssetVocabularyDepotEntryRel findByPrimaryKey(
			long assetVocabularyDepotEntryRelId)
		throws NoSuchVocabularyDepotEntryRelException;

	/**
	 * Returns the asset vocabulary depot entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key of the asset vocabulary depot entry rel
	 * @return the asset vocabulary depot entry rel, or <code>null</code> if a asset vocabulary depot entry rel with the primary key could not be found
	 */
	public AssetVocabularyDepotEntryRel fetchByPrimaryKey(
		long assetVocabularyDepotEntryRelId);

	/**
	 * Returns all the asset vocabulary depot entry rels.
	 *
	 * @return the asset vocabulary depot entry rels
	 */
	public java.util.List<AssetVocabularyDepotEntryRel> findAll();

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
	public java.util.List<AssetVocabularyDepotEntryRel> findAll(
		int start, int end);

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
	public java.util.List<AssetVocabularyDepotEntryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetVocabularyDepotEntryRel> orderByComparator);

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
	public java.util.List<AssetVocabularyDepotEntryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<AssetVocabularyDepotEntryRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the asset vocabulary depot entry rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of asset vocabulary depot entry rels.
	 *
	 * @return the number of asset vocabulary depot entry rels
	 */
	public int countAll();

}