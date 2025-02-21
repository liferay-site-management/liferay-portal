/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.service.persistence.impl;

import com.liferay.asset.kernel.exception.NoSuchVocabularyDepotEntryRelException;
import com.liferay.asset.kernel.model.AssetVocabularyDepotEntryRel;
import com.liferay.asset.kernel.model.AssetVocabularyDepotEntryRelTable;
import com.liferay.asset.kernel.service.persistence.AssetVocabularyDepotEntryRelPersistence;
import com.liferay.asset.kernel.service.persistence.AssetVocabularyDepotEntryRelUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portlet.asset.model.impl.AssetVocabularyDepotEntryRelImpl;
import com.liferay.portlet.asset.model.impl.AssetVocabularyDepotEntryRelModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the asset vocabulary depot entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetVocabularyDepotEntryRelPersistenceImpl
	extends BasePersistenceImpl<AssetVocabularyDepotEntryRel>
	implements AssetVocabularyDepotEntryRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AssetVocabularyDepotEntryRelUtil</code> to access the asset vocabulary depot entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AssetVocabularyDepotEntryRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the asset vocabulary depot entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset vocabulary depot entry rels
	 */
	@Override
	public List<AssetVocabularyDepotEntryRel> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<AssetVocabularyDepotEntryRel> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
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
	@Override
	public List<AssetVocabularyDepotEntryRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
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
	@Override
	public List<AssetVocabularyDepotEntryRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetVocabularyDepotEntryRel.class)) {

			uuid = Objects.toString(uuid, "");

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByUuid;
					finderArgs = new Object[] {uuid};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByUuid;
				finderArgs = new Object[] {uuid, start, end, orderByComparator};
			}

			List<AssetVocabularyDepotEntryRel> list = null;

			if (useFinderCache) {
				list =
					(List<AssetVocabularyDepotEntryRel>)
						FinderCacheUtil.getResult(finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (AssetVocabularyDepotEntryRel
							assetVocabularyDepotEntryRel : list) {

						if (!uuid.equals(
								assetVocabularyDepotEntryRel.getUuid())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						3 + (orderByComparator.getOrderByFields().length * 2));
				}
				else {
					sb = new StringBundler(3);
				}

				sb.append(_SQL_SELECT_ASSETVOCABULARYDEPOTENTRYREL_WHERE);

				boolean bindUuid = false;

				if (uuid.isEmpty()) {
					sb.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					bindUuid = true;

					sb.append(_FINDER_COLUMN_UUID_UUID_2);
				}

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(
						AssetVocabularyDepotEntryRelModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					if (bindUuid) {
						queryPos.add(uuid);
					}

					list = (List<AssetVocabularyDepotEntryRel>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						FinderCacheUtil.putResult(finderPath, finderArgs, list);
					}
				}
				catch (Exception exception) {
					throw processException(exception);
				}
				finally {
					closeSession(session);
				}
			}

			return list;
		}
	}

	/**
	 * Returns the first asset vocabulary depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel findByUuid_First(
			String uuid,
			OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException {

		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
			fetchByUuid_First(uuid, orderByComparator);

		if (assetVocabularyDepotEntryRel != null) {
			return assetVocabularyDepotEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchVocabularyDepotEntryRelException(sb.toString());
	}

	/**
	 * Returns the first asset vocabulary depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel fetchByUuid_First(
		String uuid,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		List<AssetVocabularyDepotEntryRel> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset vocabulary depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel findByUuid_Last(
			String uuid,
			OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException {

		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
			fetchByUuid_Last(uuid, orderByComparator);

		if (assetVocabularyDepotEntryRel != null) {
			return assetVocabularyDepotEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchVocabularyDepotEntryRelException(sb.toString());
	}

	/**
	 * Returns the last asset vocabulary depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<AssetVocabularyDepotEntryRel> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public AssetVocabularyDepotEntryRel[] findByUuid_PrevAndNext(
			long assetVocabularyDepotEntryRelId, String uuid,
			OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException {

		uuid = Objects.toString(uuid, "");

		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
			findByPrimaryKey(assetVocabularyDepotEntryRelId);

		Session session = null;

		try {
			session = openSession();

			AssetVocabularyDepotEntryRel[] array =
				new AssetVocabularyDepotEntryRelImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, assetVocabularyDepotEntryRel, uuid, orderByComparator,
				true);

			array[1] = assetVocabularyDepotEntryRel;

			array[2] = getByUuid_PrevAndNext(
				session, assetVocabularyDepotEntryRel, uuid, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetVocabularyDepotEntryRel getByUuid_PrevAndNext(
		Session session,
		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel, String uuid,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ASSETVOCABULARYDEPOTENTRYREL_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(AssetVocabularyDepotEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetVocabularyDepotEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetVocabularyDepotEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset vocabulary depot entry rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetVocabularyDepotEntryRel);
		}
	}

	/**
	 * Returns the number of asset vocabulary depot entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset vocabulary depot entry rels
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetVocabularyDepotEntryRel.class)) {

			uuid = Objects.toString(uuid, "");

			FinderPath finderPath = _finderPathCountByUuid;

			Object[] finderArgs = new Object[] {uuid};

			Long count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_ASSETVOCABULARYDEPOTENTRYREL_WHERE);

				boolean bindUuid = false;

				if (uuid.isEmpty()) {
					sb.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					bindUuid = true;

					sb.append(_FINDER_COLUMN_UUID_UUID_2);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					if (bindUuid) {
						queryPos.add(uuid);
					}

					count = (Long)query.uniqueResult();

					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
				catch (Exception exception) {
					throw processException(exception);
				}
				finally {
					closeSession(session);
				}
			}

			return count.intValue();
		}
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"assetVocabularyDepotEntryRel.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(assetVocabularyDepotEntryRel.uuid IS NULL OR assetVocabularyDepotEntryRel.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the asset vocabulary depot entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset vocabulary depot entry rels
	 */
	@Override
	public List<AssetVocabularyDepotEntryRel> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<AssetVocabularyDepotEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
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
	@Override
	public List<AssetVocabularyDepotEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
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
	@Override
	public List<AssetVocabularyDepotEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetVocabularyDepotEntryRel.class)) {

			uuid = Objects.toString(uuid, "");

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByUuid_C;
					finderArgs = new Object[] {uuid, companyId};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByUuid_C;
				finderArgs = new Object[] {
					uuid, companyId, start, end, orderByComparator
				};
			}

			List<AssetVocabularyDepotEntryRel> list = null;

			if (useFinderCache) {
				list =
					(List<AssetVocabularyDepotEntryRel>)
						FinderCacheUtil.getResult(finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (AssetVocabularyDepotEntryRel
							assetVocabularyDepotEntryRel : list) {

						if (!uuid.equals(
								assetVocabularyDepotEntryRel.getUuid()) ||
							(companyId !=
								assetVocabularyDepotEntryRel.getCompanyId())) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						4 + (orderByComparator.getOrderByFields().length * 2));
				}
				else {
					sb = new StringBundler(4);
				}

				sb.append(_SQL_SELECT_ASSETVOCABULARYDEPOTENTRYREL_WHERE);

				boolean bindUuid = false;

				if (uuid.isEmpty()) {
					sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
				}
				else {
					bindUuid = true;

					sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
				}

				sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(
						AssetVocabularyDepotEntryRelModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					if (bindUuid) {
						queryPos.add(uuid);
					}

					queryPos.add(companyId);

					list = (List<AssetVocabularyDepotEntryRel>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						FinderCacheUtil.putResult(finderPath, finderArgs, list);
					}
				}
				catch (Exception exception) {
					throw processException(exception);
				}
				finally {
					closeSession(session);
				}
			}

			return list;
		}
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
	@Override
	public AssetVocabularyDepotEntryRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException {

		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (assetVocabularyDepotEntryRel != null) {
			return assetVocabularyDepotEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchVocabularyDepotEntryRelException(sb.toString());
	}

	/**
	 * Returns the first asset vocabulary depot entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		List<AssetVocabularyDepotEntryRel> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public AssetVocabularyDepotEntryRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException {

		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (assetVocabularyDepotEntryRel != null) {
			return assetVocabularyDepotEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchVocabularyDepotEntryRelException(sb.toString());
	}

	/**
	 * Returns the last asset vocabulary depot entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<AssetVocabularyDepotEntryRel> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public AssetVocabularyDepotEntryRel[] findByUuid_C_PrevAndNext(
			long assetVocabularyDepotEntryRelId, String uuid, long companyId,
			OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException {

		uuid = Objects.toString(uuid, "");

		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
			findByPrimaryKey(assetVocabularyDepotEntryRelId);

		Session session = null;

		try {
			session = openSession();

			AssetVocabularyDepotEntryRel[] array =
				new AssetVocabularyDepotEntryRelImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, assetVocabularyDepotEntryRel, uuid, companyId,
				orderByComparator, true);

			array[1] = assetVocabularyDepotEntryRel;

			array[2] = getByUuid_C_PrevAndNext(
				session, assetVocabularyDepotEntryRel, uuid, companyId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetVocabularyDepotEntryRel getByUuid_C_PrevAndNext(
		Session session,
		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel, String uuid,
		long companyId,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_ASSETVOCABULARYDEPOTENTRYREL_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(AssetVocabularyDepotEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetVocabularyDepotEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetVocabularyDepotEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset vocabulary depot entry rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetVocabularyDepotEntryRel);
		}
	}

	/**
	 * Returns the number of asset vocabulary depot entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset vocabulary depot entry rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetVocabularyDepotEntryRel.class)) {

			uuid = Objects.toString(uuid, "");

			FinderPath finderPath = _finderPathCountByUuid_C;

			Object[] finderArgs = new Object[] {uuid, companyId};

			Long count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_COUNT_ASSETVOCABULARYDEPOTENTRYREL_WHERE);

				boolean bindUuid = false;

				if (uuid.isEmpty()) {
					sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
				}
				else {
					bindUuid = true;

					sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
				}

				sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					if (bindUuid) {
						queryPos.add(uuid);
					}

					queryPos.add(companyId);

					count = (Long)query.uniqueResult();

					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
				catch (Exception exception) {
					throw processException(exception);
				}
				finally {
					closeSession(session);
				}
			}

			return count.intValue();
		}
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"assetVocabularyDepotEntryRel.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(assetVocabularyDepotEntryRel.uuid IS NULL OR assetVocabularyDepotEntryRel.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"assetVocabularyDepotEntryRel.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByAssetVocabularyId;
	private FinderPath _finderPathWithoutPaginationFindByAssetVocabularyId;
	private FinderPath _finderPathCountByAssetVocabularyId;

	/**
	 * Returns all the asset vocabulary depot entry rels where assetVocabularyId = &#63;.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @return the matching asset vocabulary depot entry rels
	 */
	@Override
	public List<AssetVocabularyDepotEntryRel> findByAssetVocabularyId(
		long assetVocabularyId) {

		return findByAssetVocabularyId(
			assetVocabularyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<AssetVocabularyDepotEntryRel> findByAssetVocabularyId(
		long assetVocabularyId, int start, int end) {

		return findByAssetVocabularyId(assetVocabularyId, start, end, null);
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
	@Override
	public List<AssetVocabularyDepotEntryRel> findByAssetVocabularyId(
		long assetVocabularyId, int start, int end,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		return findByAssetVocabularyId(
			assetVocabularyId, start, end, orderByComparator, true);
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
	@Override
	public List<AssetVocabularyDepotEntryRel> findByAssetVocabularyId(
		long assetVocabularyId, int start, int end,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetVocabularyDepotEntryRel.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath =
						_finderPathWithoutPaginationFindByAssetVocabularyId;
					finderArgs = new Object[] {assetVocabularyId};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByAssetVocabularyId;
				finderArgs = new Object[] {
					assetVocabularyId, start, end, orderByComparator
				};
			}

			List<AssetVocabularyDepotEntryRel> list = null;

			if (useFinderCache) {
				list =
					(List<AssetVocabularyDepotEntryRel>)
						FinderCacheUtil.getResult(finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (AssetVocabularyDepotEntryRel
							assetVocabularyDepotEntryRel : list) {

						if (assetVocabularyId !=
								assetVocabularyDepotEntryRel.
									getAssetVocabularyId()) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						3 + (orderByComparator.getOrderByFields().length * 2));
				}
				else {
					sb = new StringBundler(3);
				}

				sb.append(_SQL_SELECT_ASSETVOCABULARYDEPOTENTRYREL_WHERE);

				sb.append(_FINDER_COLUMN_ASSETVOCABULARYID_ASSETVOCABULARYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(
						AssetVocabularyDepotEntryRelModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(assetVocabularyId);

					list = (List<AssetVocabularyDepotEntryRel>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						FinderCacheUtil.putResult(finderPath, finderArgs, list);
					}
				}
				catch (Exception exception) {
					throw processException(exception);
				}
				finally {
					closeSession(session);
				}
			}

			return list;
		}
	}

	/**
	 * Returns the first asset vocabulary depot entry rel in the ordered set where assetVocabularyId = &#63;.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel findByAssetVocabularyId_First(
			long assetVocabularyId,
			OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException {

		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
			fetchByAssetVocabularyId_First(
				assetVocabularyId, orderByComparator);

		if (assetVocabularyDepotEntryRel != null) {
			return assetVocabularyDepotEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assetVocabularyId=");
		sb.append(assetVocabularyId);

		sb.append("}");

		throw new NoSuchVocabularyDepotEntryRelException(sb.toString());
	}

	/**
	 * Returns the first asset vocabulary depot entry rel in the ordered set where assetVocabularyId = &#63;.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel fetchByAssetVocabularyId_First(
		long assetVocabularyId,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		List<AssetVocabularyDepotEntryRel> list = findByAssetVocabularyId(
			assetVocabularyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset vocabulary depot entry rel in the ordered set where assetVocabularyId = &#63;.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel findByAssetVocabularyId_Last(
			long assetVocabularyId,
			OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException {

		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
			fetchByAssetVocabularyId_Last(assetVocabularyId, orderByComparator);

		if (assetVocabularyDepotEntryRel != null) {
			return assetVocabularyDepotEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assetVocabularyId=");
		sb.append(assetVocabularyId);

		sb.append("}");

		throw new NoSuchVocabularyDepotEntryRelException(sb.toString());
	}

	/**
	 * Returns the last asset vocabulary depot entry rel in the ordered set where assetVocabularyId = &#63;.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel fetchByAssetVocabularyId_Last(
		long assetVocabularyId,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		int count = countByAssetVocabularyId(assetVocabularyId);

		if (count == 0) {
			return null;
		}

		List<AssetVocabularyDepotEntryRel> list = findByAssetVocabularyId(
			assetVocabularyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public AssetVocabularyDepotEntryRel[] findByAssetVocabularyId_PrevAndNext(
			long assetVocabularyDepotEntryRelId, long assetVocabularyId,
			OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException {

		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
			findByPrimaryKey(assetVocabularyDepotEntryRelId);

		Session session = null;

		try {
			session = openSession();

			AssetVocabularyDepotEntryRel[] array =
				new AssetVocabularyDepotEntryRelImpl[3];

			array[0] = getByAssetVocabularyId_PrevAndNext(
				session, assetVocabularyDepotEntryRel, assetVocabularyId,
				orderByComparator, true);

			array[1] = assetVocabularyDepotEntryRel;

			array[2] = getByAssetVocabularyId_PrevAndNext(
				session, assetVocabularyDepotEntryRel, assetVocabularyId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetVocabularyDepotEntryRel getByAssetVocabularyId_PrevAndNext(
		Session session,
		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel,
		long assetVocabularyId,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ASSETVOCABULARYDEPOTENTRYREL_WHERE);

		sb.append(_FINDER_COLUMN_ASSETVOCABULARYID_ASSETVOCABULARYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(AssetVocabularyDepotEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(assetVocabularyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetVocabularyDepotEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetVocabularyDepotEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset vocabulary depot entry rels where assetVocabularyId = &#63; from the database.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 */
	@Override
	public void removeByAssetVocabularyId(long assetVocabularyId) {
		for (AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel :
				findByAssetVocabularyId(
					assetVocabularyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetVocabularyDepotEntryRel);
		}
	}

	/**
	 * Returns the number of asset vocabulary depot entry rels where assetVocabularyId = &#63;.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @return the number of matching asset vocabulary depot entry rels
	 */
	@Override
	public int countByAssetVocabularyId(long assetVocabularyId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetVocabularyDepotEntryRel.class)) {

			FinderPath finderPath = _finderPathCountByAssetVocabularyId;

			Object[] finderArgs = new Object[] {assetVocabularyId};

			Long count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_ASSETVOCABULARYDEPOTENTRYREL_WHERE);

				sb.append(_FINDER_COLUMN_ASSETVOCABULARYID_ASSETVOCABULARYID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(assetVocabularyId);

					count = (Long)query.uniqueResult();

					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
				catch (Exception exception) {
					throw processException(exception);
				}
				finally {
					closeSession(session);
				}
			}

			return count.intValue();
		}
	}

	private static final String
		_FINDER_COLUMN_ASSETVOCABULARYID_ASSETVOCABULARYID_2 =
			"assetVocabularyDepotEntryRel.assetVocabularyId = ?";

	private FinderPath _finderPathWithPaginationFindByDepotEntryId;
	private FinderPath _finderPathWithoutPaginationFindByDepotEntryId;
	private FinderPath _finderPathCountByDepotEntryId;

	/**
	 * Returns all the asset vocabulary depot entry rels where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the matching asset vocabulary depot entry rels
	 */
	@Override
	public List<AssetVocabularyDepotEntryRel> findByDepotEntryId(
		long depotEntryId) {

		return findByDepotEntryId(
			depotEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<AssetVocabularyDepotEntryRel> findByDepotEntryId(
		long depotEntryId, int start, int end) {

		return findByDepotEntryId(depotEntryId, start, end, null);
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
	@Override
	public List<AssetVocabularyDepotEntryRel> findByDepotEntryId(
		long depotEntryId, int start, int end,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		return findByDepotEntryId(
			depotEntryId, start, end, orderByComparator, true);
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
	@Override
	public List<AssetVocabularyDepotEntryRel> findByDepotEntryId(
		long depotEntryId, int start, int end,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetVocabularyDepotEntryRel.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByDepotEntryId;
					finderArgs = new Object[] {depotEntryId};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByDepotEntryId;
				finderArgs = new Object[] {
					depotEntryId, start, end, orderByComparator
				};
			}

			List<AssetVocabularyDepotEntryRel> list = null;

			if (useFinderCache) {
				list =
					(List<AssetVocabularyDepotEntryRel>)
						FinderCacheUtil.getResult(finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (AssetVocabularyDepotEntryRel
							assetVocabularyDepotEntryRel : list) {

						if (depotEntryId !=
								assetVocabularyDepotEntryRel.
									getDepotEntryId()) {

							list = null;

							break;
						}
					}
				}
			}

			if (list == null) {
				StringBundler sb = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						3 + (orderByComparator.getOrderByFields().length * 2));
				}
				else {
					sb = new StringBundler(3);
				}

				sb.append(_SQL_SELECT_ASSETVOCABULARYDEPOTENTRYREL_WHERE);

				sb.append(_FINDER_COLUMN_DEPOTENTRYID_DEPOTENTRYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(
						AssetVocabularyDepotEntryRelModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(depotEntryId);

					list = (List<AssetVocabularyDepotEntryRel>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						FinderCacheUtil.putResult(finderPath, finderArgs, list);
					}
				}
				catch (Exception exception) {
					throw processException(exception);
				}
				finally {
					closeSession(session);
				}
			}

			return list;
		}
	}

	/**
	 * Returns the first asset vocabulary depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel findByDepotEntryId_First(
			long depotEntryId,
			OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException {

		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
			fetchByDepotEntryId_First(depotEntryId, orderByComparator);

		if (assetVocabularyDepotEntryRel != null) {
			return assetVocabularyDepotEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("depotEntryId=");
		sb.append(depotEntryId);

		sb.append("}");

		throw new NoSuchVocabularyDepotEntryRelException(sb.toString());
	}

	/**
	 * Returns the first asset vocabulary depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel fetchByDepotEntryId_First(
		long depotEntryId,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		List<AssetVocabularyDepotEntryRel> list = findByDepotEntryId(
			depotEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset vocabulary depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel findByDepotEntryId_Last(
			long depotEntryId,
			OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException {

		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
			fetchByDepotEntryId_Last(depotEntryId, orderByComparator);

		if (assetVocabularyDepotEntryRel != null) {
			return assetVocabularyDepotEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("depotEntryId=");
		sb.append(depotEntryId);

		sb.append("}");

		throw new NoSuchVocabularyDepotEntryRelException(sb.toString());
	}

	/**
	 * Returns the last asset vocabulary depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel fetchByDepotEntryId_Last(
		long depotEntryId,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		int count = countByDepotEntryId(depotEntryId);

		if (count == 0) {
			return null;
		}

		List<AssetVocabularyDepotEntryRel> list = findByDepotEntryId(
			depotEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public AssetVocabularyDepotEntryRel[] findByDepotEntryId_PrevAndNext(
			long assetVocabularyDepotEntryRelId, long depotEntryId,
			OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator)
		throws NoSuchVocabularyDepotEntryRelException {

		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
			findByPrimaryKey(assetVocabularyDepotEntryRelId);

		Session session = null;

		try {
			session = openSession();

			AssetVocabularyDepotEntryRel[] array =
				new AssetVocabularyDepotEntryRelImpl[3];

			array[0] = getByDepotEntryId_PrevAndNext(
				session, assetVocabularyDepotEntryRel, depotEntryId,
				orderByComparator, true);

			array[1] = assetVocabularyDepotEntryRel;

			array[2] = getByDepotEntryId_PrevAndNext(
				session, assetVocabularyDepotEntryRel, depotEntryId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetVocabularyDepotEntryRel getByDepotEntryId_PrevAndNext(
		Session session,
		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel,
		long depotEntryId,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ASSETVOCABULARYDEPOTENTRYREL_WHERE);

		sb.append(_FINDER_COLUMN_DEPOTENTRYID_DEPOTENTRYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(AssetVocabularyDepotEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(depotEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetVocabularyDepotEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetVocabularyDepotEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset vocabulary depot entry rels where depotEntryId = &#63; from the database.
	 *
	 * @param depotEntryId the depot entry ID
	 */
	@Override
	public void removeByDepotEntryId(long depotEntryId) {
		for (AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel :
				findByDepotEntryId(
					depotEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetVocabularyDepotEntryRel);
		}
	}

	/**
	 * Returns the number of asset vocabulary depot entry rels where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the number of matching asset vocabulary depot entry rels
	 */
	@Override
	public int countByDepotEntryId(long depotEntryId) {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetVocabularyDepotEntryRel.class)) {

			FinderPath finderPath = _finderPathCountByDepotEntryId;

			Object[] finderArgs = new Object[] {depotEntryId};

			Long count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_ASSETVOCABULARYDEPOTENTRYREL_WHERE);

				sb.append(_FINDER_COLUMN_DEPOTENTRYID_DEPOTENTRYID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(depotEntryId);

					count = (Long)query.uniqueResult();

					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
				catch (Exception exception) {
					throw processException(exception);
				}
				finally {
					closeSession(session);
				}
			}

			return count.intValue();
		}
	}

	private static final String _FINDER_COLUMN_DEPOTENTRYID_DEPOTENTRYID_2 =
		"assetVocabularyDepotEntryRel.depotEntryId = ?";

	private FinderPath _finderPathFetchByAVI_DEI;

	/**
	 * Returns the asset vocabulary depot entry rel where assetVocabularyId = &#63; and depotEntryId = &#63; or throws a <code>NoSuchVocabularyDepotEntryRelException</code> if it could not be found.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param depotEntryId the depot entry ID
	 * @return the matching asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a matching asset vocabulary depot entry rel could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel findByAVI_DEI(
			long assetVocabularyId, long depotEntryId)
		throws NoSuchVocabularyDepotEntryRelException {

		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
			fetchByAVI_DEI(assetVocabularyId, depotEntryId);

		if (assetVocabularyDepotEntryRel == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("assetVocabularyId=");
			sb.append(assetVocabularyId);

			sb.append(", depotEntryId=");
			sb.append(depotEntryId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchVocabularyDepotEntryRelException(sb.toString());
		}

		return assetVocabularyDepotEntryRel;
	}

	/**
	 * Returns the asset vocabulary depot entry rel where assetVocabularyId = &#63; and depotEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param depotEntryId the depot entry ID
	 * @return the matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel fetchByAVI_DEI(
		long assetVocabularyId, long depotEntryId) {

		return fetchByAVI_DEI(assetVocabularyId, depotEntryId, true);
	}

	/**
	 * Returns the asset vocabulary depot entry rel where assetVocabularyId = &#63; and depotEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param depotEntryId the depot entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset vocabulary depot entry rel, or <code>null</code> if a matching asset vocabulary depot entry rel could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel fetchByAVI_DEI(
		long assetVocabularyId, long depotEntryId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetVocabularyDepotEntryRel.class)) {

			Object[] finderArgs = null;

			if (useFinderCache) {
				finderArgs = new Object[] {assetVocabularyId, depotEntryId};
			}

			Object result = null;

			if (useFinderCache) {
				result = FinderCacheUtil.getResult(
					_finderPathFetchByAVI_DEI, finderArgs, this);
			}

			if (result instanceof AssetVocabularyDepotEntryRel) {
				AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
					(AssetVocabularyDepotEntryRel)result;

				if ((assetVocabularyId !=
						assetVocabularyDepotEntryRel.getAssetVocabularyId()) ||
					(depotEntryId !=
						assetVocabularyDepotEntryRel.getDepotEntryId())) {

					result = null;
				}
			}

			if (result == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_SELECT_ASSETVOCABULARYDEPOTENTRYREL_WHERE);

				sb.append(_FINDER_COLUMN_AVI_DEI_ASSETVOCABULARYID_2);

				sb.append(_FINDER_COLUMN_AVI_DEI_DEPOTENTRYID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(assetVocabularyId);

					queryPos.add(depotEntryId);

					List<AssetVocabularyDepotEntryRel> list = query.list();

					if (list.isEmpty()) {
						if (useFinderCache) {
							FinderCacheUtil.putResult(
								_finderPathFetchByAVI_DEI, finderArgs, list);
						}
					}
					else {
						if (list.size() > 1) {
							Collections.sort(list, Collections.reverseOrder());

							if (_log.isWarnEnabled()) {
								if (!useFinderCache) {
									finderArgs = new Object[] {
										assetVocabularyId, depotEntryId
									};
								}

								_log.warn(
									"AssetVocabularyDepotEntryRelPersistenceImpl.fetchByAVI_DEI(long, long, boolean) with parameters (" +
										StringUtil.merge(finderArgs) +
											") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
							}
						}

						AssetVocabularyDepotEntryRel
							assetVocabularyDepotEntryRel = list.get(0);

						result = assetVocabularyDepotEntryRel;

						cacheResult(assetVocabularyDepotEntryRel);
					}
				}
				catch (Exception exception) {
					throw processException(exception);
				}
				finally {
					closeSession(session);
				}
			}

			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (AssetVocabularyDepotEntryRel)result;
			}
		}
	}

	/**
	 * Removes the asset vocabulary depot entry rel where assetVocabularyId = &#63; and depotEntryId = &#63; from the database.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param depotEntryId the depot entry ID
	 * @return the asset vocabulary depot entry rel that was removed
	 */
	@Override
	public AssetVocabularyDepotEntryRel removeByAVI_DEI(
			long assetVocabularyId, long depotEntryId)
		throws NoSuchVocabularyDepotEntryRelException {

		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
			findByAVI_DEI(assetVocabularyId, depotEntryId);

		return remove(assetVocabularyDepotEntryRel);
	}

	/**
	 * Returns the number of asset vocabulary depot entry rels where assetVocabularyId = &#63; and depotEntryId = &#63;.
	 *
	 * @param assetVocabularyId the asset vocabulary ID
	 * @param depotEntryId the depot entry ID
	 * @return the number of matching asset vocabulary depot entry rels
	 */
	@Override
	public int countByAVI_DEI(long assetVocabularyId, long depotEntryId) {
		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
			fetchByAVI_DEI(assetVocabularyId, depotEntryId);

		if (assetVocabularyDepotEntryRel == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_AVI_DEI_ASSETVOCABULARYID_2 =
		"assetVocabularyDepotEntryRel.assetVocabularyId = ? AND ";

	private static final String _FINDER_COLUMN_AVI_DEI_DEPOTENTRYID_2 =
		"assetVocabularyDepotEntryRel.depotEntryId = ?";

	public AssetVocabularyDepotEntryRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(AssetVocabularyDepotEntryRel.class);

		setModelImplClass(AssetVocabularyDepotEntryRelImpl.class);
		setModelPKClass(long.class);

		setTable(AssetVocabularyDepotEntryRelTable.INSTANCE);
	}

	/**
	 * Caches the asset vocabulary depot entry rel in the entity cache if it is enabled.
	 *
	 * @param assetVocabularyDepotEntryRel the asset vocabulary depot entry rel
	 */
	@Override
	public void cacheResult(
		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					assetVocabularyDepotEntryRel.getCtCollectionId())) {

			EntityCacheUtil.putResult(
				AssetVocabularyDepotEntryRelImpl.class,
				assetVocabularyDepotEntryRel.getPrimaryKey(),
				assetVocabularyDepotEntryRel);

			FinderCacheUtil.putResult(
				_finderPathFetchByAVI_DEI,
				new Object[] {
					assetVocabularyDepotEntryRel.getAssetVocabularyId(),
					assetVocabularyDepotEntryRel.getDepotEntryId()
				},
				assetVocabularyDepotEntryRel);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the asset vocabulary depot entry rels in the entity cache if it is enabled.
	 *
	 * @param assetVocabularyDepotEntryRels the asset vocabulary depot entry rels
	 */
	@Override
	public void cacheResult(
		List<AssetVocabularyDepotEntryRel> assetVocabularyDepotEntryRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (assetVocabularyDepotEntryRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel :
				assetVocabularyDepotEntryRels) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						assetVocabularyDepotEntryRel.getCtCollectionId())) {

				if (EntityCacheUtil.getResult(
						AssetVocabularyDepotEntryRelImpl.class,
						assetVocabularyDepotEntryRel.getPrimaryKey()) == null) {

					cacheResult(assetVocabularyDepotEntryRel);
				}
			}
		}
	}

	/**
	 * Clears the cache for all asset vocabulary depot entry rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(AssetVocabularyDepotEntryRelImpl.class);

		FinderCacheUtil.clearCache(AssetVocabularyDepotEntryRelImpl.class);
	}

	/**
	 * Clears the cache for the asset vocabulary depot entry rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel) {

		EntityCacheUtil.removeResult(
			AssetVocabularyDepotEntryRelImpl.class,
			assetVocabularyDepotEntryRel);
	}

	@Override
	public void clearCache(
		List<AssetVocabularyDepotEntryRel> assetVocabularyDepotEntryRels) {

		for (AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel :
				assetVocabularyDepotEntryRels) {

			EntityCacheUtil.removeResult(
				AssetVocabularyDepotEntryRelImpl.class,
				assetVocabularyDepotEntryRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(AssetVocabularyDepotEntryRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				AssetVocabularyDepotEntryRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetVocabularyDepotEntryRelModelImpl
			assetVocabularyDepotEntryRelModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					assetVocabularyDepotEntryRelModelImpl.
						getCtCollectionId())) {

			Object[] args = new Object[] {
				assetVocabularyDepotEntryRelModelImpl.getAssetVocabularyId(),
				assetVocabularyDepotEntryRelModelImpl.getDepotEntryId()
			};

			FinderCacheUtil.putResult(
				_finderPathFetchByAVI_DEI, args,
				assetVocabularyDepotEntryRelModelImpl);
		}
	}

	/**
	 * Creates a new asset vocabulary depot entry rel with the primary key. Does not add the asset vocabulary depot entry rel to the database.
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key for the new asset vocabulary depot entry rel
	 * @return the new asset vocabulary depot entry rel
	 */
	@Override
	public AssetVocabularyDepotEntryRel create(
		long assetVocabularyDepotEntryRelId) {

		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
			new AssetVocabularyDepotEntryRelImpl();

		assetVocabularyDepotEntryRel.setNew(true);
		assetVocabularyDepotEntryRel.setPrimaryKey(
			assetVocabularyDepotEntryRelId);

		String uuid = PortalUUIDUtil.generate();

		assetVocabularyDepotEntryRel.setUuid(uuid);

		assetVocabularyDepotEntryRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return assetVocabularyDepotEntryRel;
	}

	/**
	 * Removes the asset vocabulary depot entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key of the asset vocabulary depot entry rel
	 * @return the asset vocabulary depot entry rel that was removed
	 * @throws NoSuchVocabularyDepotEntryRelException if a asset vocabulary depot entry rel with the primary key could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel remove(
			long assetVocabularyDepotEntryRelId)
		throws NoSuchVocabularyDepotEntryRelException {

		return remove((Serializable)assetVocabularyDepotEntryRelId);
	}

	/**
	 * Removes the asset vocabulary depot entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset vocabulary depot entry rel
	 * @return the asset vocabulary depot entry rel that was removed
	 * @throws NoSuchVocabularyDepotEntryRelException if a asset vocabulary depot entry rel with the primary key could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel remove(Serializable primaryKey)
		throws NoSuchVocabularyDepotEntryRelException {

		Session session = null;

		try {
			session = openSession();

			AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
				(AssetVocabularyDepotEntryRel)session.get(
					AssetVocabularyDepotEntryRelImpl.class, primaryKey);

			if (assetVocabularyDepotEntryRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchVocabularyDepotEntryRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(assetVocabularyDepotEntryRel);
		}
		catch (NoSuchVocabularyDepotEntryRelException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected AssetVocabularyDepotEntryRel removeImpl(
		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetVocabularyDepotEntryRel)) {
				assetVocabularyDepotEntryRel =
					(AssetVocabularyDepotEntryRel)session.get(
						AssetVocabularyDepotEntryRelImpl.class,
						assetVocabularyDepotEntryRel.getPrimaryKeyObj());
			}

			if ((assetVocabularyDepotEntryRel != null) &&
				CTPersistenceHelperUtil.isRemove(
					assetVocabularyDepotEntryRel)) {

				session.delete(assetVocabularyDepotEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetVocabularyDepotEntryRel != null) {
			clearCache(assetVocabularyDepotEntryRel);
		}

		return assetVocabularyDepotEntryRel;
	}

	@Override
	public AssetVocabularyDepotEntryRel updateImpl(
		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel) {

		boolean isNew = assetVocabularyDepotEntryRel.isNew();

		if (!(assetVocabularyDepotEntryRel instanceof
				AssetVocabularyDepotEntryRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					assetVocabularyDepotEntryRel.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					assetVocabularyDepotEntryRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetVocabularyDepotEntryRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetVocabularyDepotEntryRel implementation " +
					assetVocabularyDepotEntryRel.getClass());
		}

		AssetVocabularyDepotEntryRelModelImpl
			assetVocabularyDepotEntryRelModelImpl =
				(AssetVocabularyDepotEntryRelModelImpl)
					assetVocabularyDepotEntryRel;

		if (Validator.isNull(assetVocabularyDepotEntryRel.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			assetVocabularyDepotEntryRel.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(
					assetVocabularyDepotEntryRel)) {

				if (!isNew) {
					session.evict(
						AssetVocabularyDepotEntryRelImpl.class,
						assetVocabularyDepotEntryRel.getPrimaryKeyObj());
				}

				session.save(assetVocabularyDepotEntryRel);
			}
			else {
				assetVocabularyDepotEntryRel =
					(AssetVocabularyDepotEntryRel)session.merge(
						assetVocabularyDepotEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			AssetVocabularyDepotEntryRelImpl.class,
			assetVocabularyDepotEntryRelModelImpl, false, true);

		cacheUniqueFindersCache(assetVocabularyDepotEntryRelModelImpl);

		if (isNew) {
			assetVocabularyDepotEntryRel.setNew(false);
		}

		assetVocabularyDepotEntryRel.resetOriginalValues();

		return assetVocabularyDepotEntryRel;
	}

	/**
	 * Returns the asset vocabulary depot entry rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset vocabulary depot entry rel
	 * @return the asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a asset vocabulary depot entry rel with the primary key could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchVocabularyDepotEntryRelException {

		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
			fetchByPrimaryKey(primaryKey);

		if (assetVocabularyDepotEntryRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchVocabularyDepotEntryRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return assetVocabularyDepotEntryRel;
	}

	/**
	 * Returns the asset vocabulary depot entry rel with the primary key or throws a <code>NoSuchVocabularyDepotEntryRelException</code> if it could not be found.
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key of the asset vocabulary depot entry rel
	 * @return the asset vocabulary depot entry rel
	 * @throws NoSuchVocabularyDepotEntryRelException if a asset vocabulary depot entry rel with the primary key could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel findByPrimaryKey(
			long assetVocabularyDepotEntryRelId)
		throws NoSuchVocabularyDepotEntryRelException {

		return findByPrimaryKey((Serializable)assetVocabularyDepotEntryRelId);
	}

	/**
	 * Returns the asset vocabulary depot entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset vocabulary depot entry rel
	 * @return the asset vocabulary depot entry rel, or <code>null</code> if a asset vocabulary depot entry rel with the primary key could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel fetchByPrimaryKey(
		Serializable primaryKey) {

		if (CTPersistenceHelperUtil.isProductionMode(
				AssetVocabularyDepotEntryRel.class, primaryKey)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
			(AssetVocabularyDepotEntryRel)EntityCacheUtil.getResult(
				AssetVocabularyDepotEntryRelImpl.class, primaryKey);

		if (assetVocabularyDepotEntryRel != null) {
			return assetVocabularyDepotEntryRel;
		}

		Session session = null;

		try {
			session = openSession();

			assetVocabularyDepotEntryRel =
				(AssetVocabularyDepotEntryRel)session.get(
					AssetVocabularyDepotEntryRelImpl.class, primaryKey);

			if (assetVocabularyDepotEntryRel != null) {
				cacheResult(assetVocabularyDepotEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return assetVocabularyDepotEntryRel;
	}

	/**
	 * Returns the asset vocabulary depot entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetVocabularyDepotEntryRelId the primary key of the asset vocabulary depot entry rel
	 * @return the asset vocabulary depot entry rel, or <code>null</code> if a asset vocabulary depot entry rel with the primary key could not be found
	 */
	@Override
	public AssetVocabularyDepotEntryRel fetchByPrimaryKey(
		long assetVocabularyDepotEntryRelId) {

		return fetchByPrimaryKey((Serializable)assetVocabularyDepotEntryRelId);
	}

	@Override
	public Map<Serializable, AssetVocabularyDepotEntryRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(
				AssetVocabularyDepotEntryRel.class)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetVocabularyDepotEntryRel> map =
			new HashMap<Serializable, AssetVocabularyDepotEntryRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
				fetchByPrimaryKey(primaryKey);

			if (assetVocabularyDepotEntryRel != null) {
				map.put(primaryKey, assetVocabularyDepotEntryRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
						AssetVocabularyDepotEntryRel.class, primaryKey)) {

				AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel =
					(AssetVocabularyDepotEntryRel)EntityCacheUtil.getResult(
						AssetVocabularyDepotEntryRelImpl.class, primaryKey);

				if (assetVocabularyDepotEntryRel == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, assetVocabularyDepotEntryRel);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		if ((databaseInMaxParameters > 0) &&
			(primaryKeys.size() > databaseInMaxParameters)) {

			Iterator<Serializable> iterator = primaryKeys.iterator();

			while (iterator.hasNext()) {
				Set<Serializable> page = new HashSet<>();

				for (int i = 0;
					 (i < databaseInMaxParameters) && iterator.hasNext(); i++) {

					page.add(iterator.next());
				}

				map.putAll(fetchByPrimaryKeys(page));
			}

			return map;
		}

		StringBundler sb = new StringBundler((primaryKeys.size() * 2) + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel :
					(List<AssetVocabularyDepotEntryRel>)query.list()) {

				map.put(
					assetVocabularyDepotEntryRel.getPrimaryKeyObj(),
					assetVocabularyDepotEntryRel);

				cacheResult(assetVocabularyDepotEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the asset vocabulary depot entry rels.
	 *
	 * @return the asset vocabulary depot entry rels
	 */
	@Override
	public List<AssetVocabularyDepotEntryRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<AssetVocabularyDepotEntryRel> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<AssetVocabularyDepotEntryRel> findAll(
		int start, int end,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<AssetVocabularyDepotEntryRel> findAll(
		int start, int end,
		OrderByComparator<AssetVocabularyDepotEntryRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetVocabularyDepotEntryRel.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindAll;
					finderArgs = FINDER_ARGS_EMPTY;
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindAll;
				finderArgs = new Object[] {start, end, orderByComparator};
			}

			List<AssetVocabularyDepotEntryRel> list = null;

			if (useFinderCache) {
				list =
					(List<AssetVocabularyDepotEntryRel>)
						FinderCacheUtil.getResult(finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_ASSETVOCABULARYDEPOTENTRYREL);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_ASSETVOCABULARYDEPOTENTRYREL;

					sql = sql.concat(
						AssetVocabularyDepotEntryRelModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<AssetVocabularyDepotEntryRel>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						FinderCacheUtil.putResult(finderPath, finderArgs, list);
					}
				}
				catch (Exception exception) {
					throw processException(exception);
				}
				finally {
					closeSession(session);
				}
			}

			return list;
		}
	}

	/**
	 * Removes all the asset vocabulary depot entry rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel :
				findAll()) {

			remove(assetVocabularyDepotEntryRel);
		}
	}

	/**
	 * Returns the number of asset vocabulary depot entry rels.
	 *
	 * @return the number of asset vocabulary depot entry rels
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				CTPersistenceHelperUtil.setCTCollectionIdWithSafeCloseable(
					AssetVocabularyDepotEntryRel.class)) {

			Long count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_ASSETVOCABULARYDEPOTENTRYREL);

					count = (Long)query.uniqueResult();

					FinderCacheUtil.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
				catch (Exception exception) {
					throw processException(exception);
				}
				finally {
					closeSession(session);
				}
			}

			return count.intValue();
		}
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "assetVocabularyDepotEntryRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ASSETVOCABULARYDEPOTENTRYREL;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.getOrDefault(
			ctColumnResolutionType, Collections.emptySet());
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return AssetVocabularyDepotEntryRelModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "AssetVocabularyDepotEntryRel";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("companyId");
		ctMergeColumnNames.add("assetVocabularyId");
		ctMergeColumnNames.add("depotEntryId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("assetVocabularyDepotEntryRelId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);
	}

	/**
	 * Initializes the asset vocabulary depot entry rel persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathWithPaginationFindByAssetVocabularyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAssetVocabularyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"assetVocabularyId"}, true);

		_finderPathWithoutPaginationFindByAssetVocabularyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByAssetVocabularyId", new String[] {Long.class.getName()},
			new String[] {"assetVocabularyId"}, true);

		_finderPathCountByAssetVocabularyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAssetVocabularyId", new String[] {Long.class.getName()},
			new String[] {"assetVocabularyId"}, false);

		_finderPathWithPaginationFindByDepotEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByDepotEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"depotEntryId"}, true);

		_finderPathWithoutPaginationFindByDepotEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByDepotEntryId",
			new String[] {Long.class.getName()}, new String[] {"depotEntryId"},
			true);

		_finderPathCountByDepotEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByDepotEntryId",
			new String[] {Long.class.getName()}, new String[] {"depotEntryId"},
			false);

		_finderPathFetchByAVI_DEI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByAVI_DEI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"assetVocabularyId", "depotEntryId"}, true);

		AssetVocabularyDepotEntryRelUtil.setPersistence(this);
	}

	public void destroy() {
		AssetVocabularyDepotEntryRelUtil.setPersistence(null);

		EntityCacheUtil.removeCache(
			AssetVocabularyDepotEntryRelImpl.class.getName());
	}

	private static final String _SQL_SELECT_ASSETVOCABULARYDEPOTENTRYREL =
		"SELECT assetVocabularyDepotEntryRel FROM AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel";

	private static final String _SQL_SELECT_ASSETVOCABULARYDEPOTENTRYREL_WHERE =
		"SELECT assetVocabularyDepotEntryRel FROM AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel WHERE ";

	private static final String _SQL_COUNT_ASSETVOCABULARYDEPOTENTRYREL =
		"SELECT COUNT(assetVocabularyDepotEntryRel) FROM AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel";

	private static final String _SQL_COUNT_ASSETVOCABULARYDEPOTENTRYREL_WHERE =
		"SELECT COUNT(assetVocabularyDepotEntryRel) FROM AssetVocabularyDepotEntryRel assetVocabularyDepotEntryRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"assetVocabularyDepotEntryRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AssetVocabularyDepotEntryRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AssetVocabularyDepotEntryRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetVocabularyDepotEntryRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}