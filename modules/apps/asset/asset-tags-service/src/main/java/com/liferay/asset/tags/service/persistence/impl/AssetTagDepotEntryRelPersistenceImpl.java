/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.service.persistence.impl;

import com.liferay.asset.tags.exception.NoSuchDepotEntryRelException;
import com.liferay.asset.tags.model.AssetTagDepotEntryRel;
import com.liferay.asset.tags.model.AssetTagDepotEntryRelTable;
import com.liferay.asset.tags.model.impl.AssetTagDepotEntryRelImpl;
import com.liferay.asset.tags.model.impl.AssetTagDepotEntryRelModelImpl;
import com.liferay.asset.tags.service.persistence.AssetTagDepotEntryRelPersistence;
import com.liferay.asset.tags.service.persistence.AssetTagDepotEntryRelUtil;
import com.liferay.asset.tags.service.persistence.impl.constants.AssetTagPersistenceConstants;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
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

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the asset tag depot entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AssetTagDepotEntryRelPersistence.class)
public class AssetTagDepotEntryRelPersistenceImpl
	extends BasePersistenceImpl<AssetTagDepotEntryRel>
	implements AssetTagDepotEntryRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AssetTagDepotEntryRelUtil</code> to access the asset tag depot entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AssetTagDepotEntryRelImpl.class.getName();

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
	 * Returns all the asset tag depot entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset tag depot entry rels
	 */
	@Override
	public List<AssetTagDepotEntryRel> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<AssetTagDepotEntryRel> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
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
	@Override
	public List<AssetTagDepotEntryRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
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
	@Override
	public List<AssetTagDepotEntryRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetTagDepotEntryRel.class)) {

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

			List<AssetTagDepotEntryRel> list = null;

			if (useFinderCache) {
				list = (List<AssetTagDepotEntryRel>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (AssetTagDepotEntryRel assetTagDepotEntryRel : list) {
						if (!uuid.equals(assetTagDepotEntryRel.getUuid())) {
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

				sb.append(_SQL_SELECT_ASSETTAGDEPOTENTRYREL_WHERE);

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
					sb.append(AssetTagDepotEntryRelModelImpl.ORDER_BY_JPQL);
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

					list = (List<AssetTagDepotEntryRel>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first asset tag depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	@Override
	public AssetTagDepotEntryRel findByUuid_First(
			String uuid,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException {

		AssetTagDepotEntryRel assetTagDepotEntryRel = fetchByUuid_First(
			uuid, orderByComparator);

		if (assetTagDepotEntryRel != null) {
			return assetTagDepotEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchDepotEntryRelException(sb.toString());
	}

	/**
	 * Returns the first asset tag depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	@Override
	public AssetTagDepotEntryRel fetchByUuid_First(
		String uuid,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		List<AssetTagDepotEntryRel> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset tag depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	@Override
	public AssetTagDepotEntryRel findByUuid_Last(
			String uuid,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException {

		AssetTagDepotEntryRel assetTagDepotEntryRel = fetchByUuid_Last(
			uuid, orderByComparator);

		if (assetTagDepotEntryRel != null) {
			return assetTagDepotEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchDepotEntryRelException(sb.toString());
	}

	/**
	 * Returns the last asset tag depot entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	@Override
	public AssetTagDepotEntryRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<AssetTagDepotEntryRel> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public AssetTagDepotEntryRel[] findByUuid_PrevAndNext(
			long assetTagDepotEntryRelId, String uuid,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException {

		uuid = Objects.toString(uuid, "");

		AssetTagDepotEntryRel assetTagDepotEntryRel = findByPrimaryKey(
			assetTagDepotEntryRelId);

		Session session = null;

		try {
			session = openSession();

			AssetTagDepotEntryRel[] array = new AssetTagDepotEntryRelImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, assetTagDepotEntryRel, uuid, orderByComparator, true);

			array[1] = assetTagDepotEntryRel;

			array[2] = getByUuid_PrevAndNext(
				session, assetTagDepotEntryRel, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetTagDepotEntryRel getByUuid_PrevAndNext(
		Session session, AssetTagDepotEntryRel assetTagDepotEntryRel,
		String uuid, OrderByComparator<AssetTagDepotEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETTAGDEPOTENTRYREL_WHERE);

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
			sb.append(AssetTagDepotEntryRelModelImpl.ORDER_BY_JPQL);
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
						assetTagDepotEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetTagDepotEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset tag depot entry rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (AssetTagDepotEntryRel assetTagDepotEntryRel :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetTagDepotEntryRel);
		}
	}

	/**
	 * Returns the number of asset tag depot entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset tag depot entry rels
	 */
	@Override
	public int countByUuid(String uuid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetTagDepotEntryRel.class)) {

			uuid = Objects.toString(uuid, "");

			FinderPath finderPath = _finderPathCountByUuid;

			Object[] finderArgs = new Object[] {uuid};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_ASSETTAGDEPOTENTRYREL_WHERE);

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

					finderCache.putResult(finderPath, finderArgs, count);
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
		"assetTagDepotEntryRel.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(assetTagDepotEntryRel.uuid IS NULL OR assetTagDepotEntryRel.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the asset tag depot entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset tag depot entry rels
	 */
	@Override
	public List<AssetTagDepotEntryRel> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<AssetTagDepotEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
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
	@Override
	public List<AssetTagDepotEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
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
	@Override
	public List<AssetTagDepotEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetTagDepotEntryRel.class)) {

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

			List<AssetTagDepotEntryRel> list = null;

			if (useFinderCache) {
				list = (List<AssetTagDepotEntryRel>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (AssetTagDepotEntryRel assetTagDepotEntryRel : list) {
						if (!uuid.equals(assetTagDepotEntryRel.getUuid()) ||
							(companyId !=
								assetTagDepotEntryRel.getCompanyId())) {

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

				sb.append(_SQL_SELECT_ASSETTAGDEPOTENTRYREL_WHERE);

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
					sb.append(AssetTagDepotEntryRelModelImpl.ORDER_BY_JPQL);
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

					list = (List<AssetTagDepotEntryRel>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first asset tag depot entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	@Override
	public AssetTagDepotEntryRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException {

		AssetTagDepotEntryRel assetTagDepotEntryRel = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (assetTagDepotEntryRel != null) {
			return assetTagDepotEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchDepotEntryRelException(sb.toString());
	}

	/**
	 * Returns the first asset tag depot entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	@Override
	public AssetTagDepotEntryRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		List<AssetTagDepotEntryRel> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public AssetTagDepotEntryRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException {

		AssetTagDepotEntryRel assetTagDepotEntryRel = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (assetTagDepotEntryRel != null) {
			return assetTagDepotEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchDepotEntryRelException(sb.toString());
	}

	/**
	 * Returns the last asset tag depot entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	@Override
	public AssetTagDepotEntryRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<AssetTagDepotEntryRel> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public AssetTagDepotEntryRel[] findByUuid_C_PrevAndNext(
			long assetTagDepotEntryRelId, String uuid, long companyId,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException {

		uuid = Objects.toString(uuid, "");

		AssetTagDepotEntryRel assetTagDepotEntryRel = findByPrimaryKey(
			assetTagDepotEntryRelId);

		Session session = null;

		try {
			session = openSession();

			AssetTagDepotEntryRel[] array = new AssetTagDepotEntryRelImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, assetTagDepotEntryRel, uuid, companyId,
				orderByComparator, true);

			array[1] = assetTagDepotEntryRel;

			array[2] = getByUuid_C_PrevAndNext(
				session, assetTagDepotEntryRel, uuid, companyId,
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

	protected AssetTagDepotEntryRel getByUuid_C_PrevAndNext(
		Session session, AssetTagDepotEntryRel assetTagDepotEntryRel,
		String uuid, long companyId,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETTAGDEPOTENTRYREL_WHERE);

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
			sb.append(AssetTagDepotEntryRelModelImpl.ORDER_BY_JPQL);
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
						assetTagDepotEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetTagDepotEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset tag depot entry rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (AssetTagDepotEntryRel assetTagDepotEntryRel :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetTagDepotEntryRel);
		}
	}

	/**
	 * Returns the number of asset tag depot entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset tag depot entry rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetTagDepotEntryRel.class)) {

			uuid = Objects.toString(uuid, "");

			FinderPath finderPath = _finderPathCountByUuid_C;

			Object[] finderArgs = new Object[] {uuid, companyId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_COUNT_ASSETTAGDEPOTENTRYREL_WHERE);

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

					finderCache.putResult(finderPath, finderArgs, count);
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
		"assetTagDepotEntryRel.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(assetTagDepotEntryRel.uuid IS NULL OR assetTagDepotEntryRel.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"assetTagDepotEntryRel.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByAssetTagId;
	private FinderPath _finderPathWithoutPaginationFindByAssetTagId;
	private FinderPath _finderPathCountByAssetTagId;

	/**
	 * Returns all the asset tag depot entry rels where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @return the matching asset tag depot entry rels
	 */
	@Override
	public List<AssetTagDepotEntryRel> findByAssetTagId(long assetTagId) {
		return findByAssetTagId(
			assetTagId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<AssetTagDepotEntryRel> findByAssetTagId(
		long assetTagId, int start, int end) {

		return findByAssetTagId(assetTagId, start, end, null);
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
	@Override
	public List<AssetTagDepotEntryRel> findByAssetTagId(
		long assetTagId, int start, int end,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		return findByAssetTagId(
			assetTagId, start, end, orderByComparator, true);
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
	@Override
	public List<AssetTagDepotEntryRel> findByAssetTagId(
		long assetTagId, int start, int end,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetTagDepotEntryRel.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByAssetTagId;
					finderArgs = new Object[] {assetTagId};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByAssetTagId;
				finderArgs = new Object[] {
					assetTagId, start, end, orderByComparator
				};
			}

			List<AssetTagDepotEntryRel> list = null;

			if (useFinderCache) {
				list = (List<AssetTagDepotEntryRel>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (AssetTagDepotEntryRel assetTagDepotEntryRel : list) {
						if (assetTagId !=
								assetTagDepotEntryRel.getAssetTagId()) {

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

				sb.append(_SQL_SELECT_ASSETTAGDEPOTENTRYREL_WHERE);

				sb.append(_FINDER_COLUMN_ASSETTAGID_ASSETTAGID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(AssetTagDepotEntryRelModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(assetTagId);

					list = (List<AssetTagDepotEntryRel>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first asset tag depot entry rel in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	@Override
	public AssetTagDepotEntryRel findByAssetTagId_First(
			long assetTagId,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException {

		AssetTagDepotEntryRel assetTagDepotEntryRel = fetchByAssetTagId_First(
			assetTagId, orderByComparator);

		if (assetTagDepotEntryRel != null) {
			return assetTagDepotEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assetTagId=");
		sb.append(assetTagId);

		sb.append("}");

		throw new NoSuchDepotEntryRelException(sb.toString());
	}

	/**
	 * Returns the first asset tag depot entry rel in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	@Override
	public AssetTagDepotEntryRel fetchByAssetTagId_First(
		long assetTagId,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		List<AssetTagDepotEntryRel> list = findByAssetTagId(
			assetTagId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset tag depot entry rel in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	@Override
	public AssetTagDepotEntryRel findByAssetTagId_Last(
			long assetTagId,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException {

		AssetTagDepotEntryRel assetTagDepotEntryRel = fetchByAssetTagId_Last(
			assetTagId, orderByComparator);

		if (assetTagDepotEntryRel != null) {
			return assetTagDepotEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assetTagId=");
		sb.append(assetTagId);

		sb.append("}");

		throw new NoSuchDepotEntryRelException(sb.toString());
	}

	/**
	 * Returns the last asset tag depot entry rel in the ordered set where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	@Override
	public AssetTagDepotEntryRel fetchByAssetTagId_Last(
		long assetTagId,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		int count = countByAssetTagId(assetTagId);

		if (count == 0) {
			return null;
		}

		List<AssetTagDepotEntryRel> list = findByAssetTagId(
			assetTagId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public AssetTagDepotEntryRel[] findByAssetTagId_PrevAndNext(
			long assetTagDepotEntryRelId, long assetTagId,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException {

		AssetTagDepotEntryRel assetTagDepotEntryRel = findByPrimaryKey(
			assetTagDepotEntryRelId);

		Session session = null;

		try {
			session = openSession();

			AssetTagDepotEntryRel[] array = new AssetTagDepotEntryRelImpl[3];

			array[0] = getByAssetTagId_PrevAndNext(
				session, assetTagDepotEntryRel, assetTagId, orderByComparator,
				true);

			array[1] = assetTagDepotEntryRel;

			array[2] = getByAssetTagId_PrevAndNext(
				session, assetTagDepotEntryRel, assetTagId, orderByComparator,
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

	protected AssetTagDepotEntryRel getByAssetTagId_PrevAndNext(
		Session session, AssetTagDepotEntryRel assetTagDepotEntryRel,
		long assetTagId,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETTAGDEPOTENTRYREL_WHERE);

		sb.append(_FINDER_COLUMN_ASSETTAGID_ASSETTAGID_2);

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
			sb.append(AssetTagDepotEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(assetTagId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetTagDepotEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetTagDepotEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset tag depot entry rels where assetTagId = &#63; from the database.
	 *
	 * @param assetTagId the asset tag ID
	 */
	@Override
	public void removeByAssetTagId(long assetTagId) {
		for (AssetTagDepotEntryRel assetTagDepotEntryRel :
				findByAssetTagId(
					assetTagId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetTagDepotEntryRel);
		}
	}

	/**
	 * Returns the number of asset tag depot entry rels where assetTagId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @return the number of matching asset tag depot entry rels
	 */
	@Override
	public int countByAssetTagId(long assetTagId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetTagDepotEntryRel.class)) {

			FinderPath finderPath = _finderPathCountByAssetTagId;

			Object[] finderArgs = new Object[] {assetTagId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_ASSETTAGDEPOTENTRYREL_WHERE);

				sb.append(_FINDER_COLUMN_ASSETTAGID_ASSETTAGID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(assetTagId);

					count = (Long)query.uniqueResult();

					finderCache.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_ASSETTAGID_ASSETTAGID_2 =
		"assetTagDepotEntryRel.assetTagId = ?";

	private FinderPath _finderPathWithPaginationFindByDepotEntryId;
	private FinderPath _finderPathWithoutPaginationFindByDepotEntryId;
	private FinderPath _finderPathCountByDepotEntryId;

	/**
	 * Returns all the asset tag depot entry rels where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the matching asset tag depot entry rels
	 */
	@Override
	public List<AssetTagDepotEntryRel> findByDepotEntryId(long depotEntryId) {
		return findByDepotEntryId(
			depotEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<AssetTagDepotEntryRel> findByDepotEntryId(
		long depotEntryId, int start, int end) {

		return findByDepotEntryId(depotEntryId, start, end, null);
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
	@Override
	public List<AssetTagDepotEntryRel> findByDepotEntryId(
		long depotEntryId, int start, int end,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		return findByDepotEntryId(
			depotEntryId, start, end, orderByComparator, true);
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
	@Override
	public List<AssetTagDepotEntryRel> findByDepotEntryId(
		long depotEntryId, int start, int end,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetTagDepotEntryRel.class)) {

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

			List<AssetTagDepotEntryRel> list = null;

			if (useFinderCache) {
				list = (List<AssetTagDepotEntryRel>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (AssetTagDepotEntryRel assetTagDepotEntryRel : list) {
						if (depotEntryId !=
								assetTagDepotEntryRel.getDepotEntryId()) {

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

				sb.append(_SQL_SELECT_ASSETTAGDEPOTENTRYREL_WHERE);

				sb.append(_FINDER_COLUMN_DEPOTENTRYID_DEPOTENTRYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(AssetTagDepotEntryRelModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(depotEntryId);

					list = (List<AssetTagDepotEntryRel>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first asset tag depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	@Override
	public AssetTagDepotEntryRel findByDepotEntryId_First(
			long depotEntryId,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException {

		AssetTagDepotEntryRel assetTagDepotEntryRel = fetchByDepotEntryId_First(
			depotEntryId, orderByComparator);

		if (assetTagDepotEntryRel != null) {
			return assetTagDepotEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("depotEntryId=");
		sb.append(depotEntryId);

		sb.append("}");

		throw new NoSuchDepotEntryRelException(sb.toString());
	}

	/**
	 * Returns the first asset tag depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	@Override
	public AssetTagDepotEntryRel fetchByDepotEntryId_First(
		long depotEntryId,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		List<AssetTagDepotEntryRel> list = findByDepotEntryId(
			depotEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset tag depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	@Override
	public AssetTagDepotEntryRel findByDepotEntryId_Last(
			long depotEntryId,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException {

		AssetTagDepotEntryRel assetTagDepotEntryRel = fetchByDepotEntryId_Last(
			depotEntryId, orderByComparator);

		if (assetTagDepotEntryRel != null) {
			return assetTagDepotEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("depotEntryId=");
		sb.append(depotEntryId);

		sb.append("}");

		throw new NoSuchDepotEntryRelException(sb.toString());
	}

	/**
	 * Returns the last asset tag depot entry rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	@Override
	public AssetTagDepotEntryRel fetchByDepotEntryId_Last(
		long depotEntryId,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		int count = countByDepotEntryId(depotEntryId);

		if (count == 0) {
			return null;
		}

		List<AssetTagDepotEntryRel> list = findByDepotEntryId(
			depotEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public AssetTagDepotEntryRel[] findByDepotEntryId_PrevAndNext(
			long assetTagDepotEntryRelId, long depotEntryId,
			OrderByComparator<AssetTagDepotEntryRel> orderByComparator)
		throws NoSuchDepotEntryRelException {

		AssetTagDepotEntryRel assetTagDepotEntryRel = findByPrimaryKey(
			assetTagDepotEntryRelId);

		Session session = null;

		try {
			session = openSession();

			AssetTagDepotEntryRel[] array = new AssetTagDepotEntryRelImpl[3];

			array[0] = getByDepotEntryId_PrevAndNext(
				session, assetTagDepotEntryRel, depotEntryId, orderByComparator,
				true);

			array[1] = assetTagDepotEntryRel;

			array[2] = getByDepotEntryId_PrevAndNext(
				session, assetTagDepotEntryRel, depotEntryId, orderByComparator,
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

	protected AssetTagDepotEntryRel getByDepotEntryId_PrevAndNext(
		Session session, AssetTagDepotEntryRel assetTagDepotEntryRel,
		long depotEntryId,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_ASSETTAGDEPOTENTRYREL_WHERE);

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
			sb.append(AssetTagDepotEntryRelModelImpl.ORDER_BY_JPQL);
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
						assetTagDepotEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetTagDepotEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset tag depot entry rels where depotEntryId = &#63; from the database.
	 *
	 * @param depotEntryId the depot entry ID
	 */
	@Override
	public void removeByDepotEntryId(long depotEntryId) {
		for (AssetTagDepotEntryRel assetTagDepotEntryRel :
				findByDepotEntryId(
					depotEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetTagDepotEntryRel);
		}
	}

	/**
	 * Returns the number of asset tag depot entry rels where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the number of matching asset tag depot entry rels
	 */
	@Override
	public int countByDepotEntryId(long depotEntryId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetTagDepotEntryRel.class)) {

			FinderPath finderPath = _finderPathCountByDepotEntryId;

			Object[] finderArgs = new Object[] {depotEntryId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_ASSETTAGDEPOTENTRYREL_WHERE);

				sb.append(_FINDER_COLUMN_DEPOTENTRYID_DEPOTENTRYID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(depotEntryId);

					count = (Long)query.uniqueResult();

					finderCache.putResult(finderPath, finderArgs, count);
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
		"assetTagDepotEntryRel.depotEntryId = ?";

	private FinderPath _finderPathFetchByAVI_DEI;

	/**
	 * Returns the asset tag depot entry rel where assetTagId = &#63; and depotEntryId = &#63; or throws a <code>NoSuchDepotEntryRelException</code> if it could not be found.
	 *
	 * @param assetTagId the asset tag ID
	 * @param depotEntryId the depot entry ID
	 * @return the matching asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a matching asset tag depot entry rel could not be found
	 */
	@Override
	public AssetTagDepotEntryRel findByAVI_DEI(
			long assetTagId, long depotEntryId)
		throws NoSuchDepotEntryRelException {

		AssetTagDepotEntryRel assetTagDepotEntryRel = fetchByAVI_DEI(
			assetTagId, depotEntryId);

		if (assetTagDepotEntryRel == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("assetTagId=");
			sb.append(assetTagId);

			sb.append(", depotEntryId=");
			sb.append(depotEntryId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchDepotEntryRelException(sb.toString());
		}

		return assetTagDepotEntryRel;
	}

	/**
	 * Returns the asset tag depot entry rel where assetTagId = &#63; and depotEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param assetTagId the asset tag ID
	 * @param depotEntryId the depot entry ID
	 * @return the matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	@Override
	public AssetTagDepotEntryRel fetchByAVI_DEI(
		long assetTagId, long depotEntryId) {

		return fetchByAVI_DEI(assetTagId, depotEntryId, true);
	}

	/**
	 * Returns the asset tag depot entry rel where assetTagId = &#63; and depotEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param assetTagId the asset tag ID
	 * @param depotEntryId the depot entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset tag depot entry rel, or <code>null</code> if a matching asset tag depot entry rel could not be found
	 */
	@Override
	public AssetTagDepotEntryRel fetchByAVI_DEI(
		long assetTagId, long depotEntryId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetTagDepotEntryRel.class)) {

			Object[] finderArgs = null;

			if (useFinderCache) {
				finderArgs = new Object[] {assetTagId, depotEntryId};
			}

			Object result = null;

			if (useFinderCache) {
				result = finderCache.getResult(
					_finderPathFetchByAVI_DEI, finderArgs, this);
			}

			if (result instanceof AssetTagDepotEntryRel) {
				AssetTagDepotEntryRel assetTagDepotEntryRel =
					(AssetTagDepotEntryRel)result;

				if ((assetTagId != assetTagDepotEntryRel.getAssetTagId()) ||
					(depotEntryId != assetTagDepotEntryRel.getDepotEntryId())) {

					result = null;
				}
			}

			if (result == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_SELECT_ASSETTAGDEPOTENTRYREL_WHERE);

				sb.append(_FINDER_COLUMN_AVI_DEI_ASSETTAGID_2);

				sb.append(_FINDER_COLUMN_AVI_DEI_DEPOTENTRYID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(assetTagId);

					queryPos.add(depotEntryId);

					List<AssetTagDepotEntryRel> list = query.list();

					if (list.isEmpty()) {
						if (useFinderCache) {
							finderCache.putResult(
								_finderPathFetchByAVI_DEI, finderArgs, list);
						}
					}
					else {
						if (list.size() > 1) {
							Collections.sort(list, Collections.reverseOrder());

							if (_log.isWarnEnabled()) {
								if (!useFinderCache) {
									finderArgs = new Object[] {
										assetTagId, depotEntryId
									};
								}

								_log.warn(
									"AssetTagDepotEntryRelPersistenceImpl.fetchByAVI_DEI(long, long, boolean) with parameters (" +
										StringUtil.merge(finderArgs) +
											") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
							}
						}

						AssetTagDepotEntryRel assetTagDepotEntryRel = list.get(
							0);

						result = assetTagDepotEntryRel;

						cacheResult(assetTagDepotEntryRel);
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
				return (AssetTagDepotEntryRel)result;
			}
		}
	}

	/**
	 * Removes the asset tag depot entry rel where assetTagId = &#63; and depotEntryId = &#63; from the database.
	 *
	 * @param assetTagId the asset tag ID
	 * @param depotEntryId the depot entry ID
	 * @return the asset tag depot entry rel that was removed
	 */
	@Override
	public AssetTagDepotEntryRel removeByAVI_DEI(
			long assetTagId, long depotEntryId)
		throws NoSuchDepotEntryRelException {

		AssetTagDepotEntryRel assetTagDepotEntryRel = findByAVI_DEI(
			assetTagId, depotEntryId);

		return remove(assetTagDepotEntryRel);
	}

	/**
	 * Returns the number of asset tag depot entry rels where assetTagId = &#63; and depotEntryId = &#63;.
	 *
	 * @param assetTagId the asset tag ID
	 * @param depotEntryId the depot entry ID
	 * @return the number of matching asset tag depot entry rels
	 */
	@Override
	public int countByAVI_DEI(long assetTagId, long depotEntryId) {
		AssetTagDepotEntryRel assetTagDepotEntryRel = fetchByAVI_DEI(
			assetTagId, depotEntryId);

		if (assetTagDepotEntryRel == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_AVI_DEI_ASSETTAGID_2 =
		"assetTagDepotEntryRel.assetTagId = ? AND ";

	private static final String _FINDER_COLUMN_AVI_DEI_DEPOTENTRYID_2 =
		"assetTagDepotEntryRel.depotEntryId = ?";

	public AssetTagDepotEntryRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(AssetTagDepotEntryRel.class);

		setModelImplClass(AssetTagDepotEntryRelImpl.class);
		setModelPKClass(long.class);

		setTable(AssetTagDepotEntryRelTable.INSTANCE);
	}

	/**
	 * Caches the asset tag depot entry rel in the entity cache if it is enabled.
	 *
	 * @param assetTagDepotEntryRel the asset tag depot entry rel
	 */
	@Override
	public void cacheResult(AssetTagDepotEntryRel assetTagDepotEntryRel) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					assetTagDepotEntryRel.getCtCollectionId())) {

			entityCache.putResult(
				AssetTagDepotEntryRelImpl.class,
				assetTagDepotEntryRel.getPrimaryKey(), assetTagDepotEntryRel);

			finderCache.putResult(
				_finderPathFetchByAVI_DEI,
				new Object[] {
					assetTagDepotEntryRel.getAssetTagId(),
					assetTagDepotEntryRel.getDepotEntryId()
				},
				assetTagDepotEntryRel);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the asset tag depot entry rels in the entity cache if it is enabled.
	 *
	 * @param assetTagDepotEntryRels the asset tag depot entry rels
	 */
	@Override
	public void cacheResult(
		List<AssetTagDepotEntryRel> assetTagDepotEntryRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (assetTagDepotEntryRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (AssetTagDepotEntryRel assetTagDepotEntryRel :
				assetTagDepotEntryRels) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						assetTagDepotEntryRel.getCtCollectionId())) {

				if (entityCache.getResult(
						AssetTagDepotEntryRelImpl.class,
						assetTagDepotEntryRel.getPrimaryKey()) == null) {

					cacheResult(assetTagDepotEntryRel);
				}
			}
		}
	}

	/**
	 * Clears the cache for all asset tag depot entry rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetTagDepotEntryRelImpl.class);

		finderCache.clearCache(AssetTagDepotEntryRelImpl.class);
	}

	/**
	 * Clears the cache for the asset tag depot entry rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetTagDepotEntryRel assetTagDepotEntryRel) {
		entityCache.removeResult(
			AssetTagDepotEntryRelImpl.class, assetTagDepotEntryRel);
	}

	@Override
	public void clearCache(List<AssetTagDepotEntryRel> assetTagDepotEntryRels) {
		for (AssetTagDepotEntryRel assetTagDepotEntryRel :
				assetTagDepotEntryRels) {

			entityCache.removeResult(
				AssetTagDepotEntryRelImpl.class, assetTagDepotEntryRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(AssetTagDepotEntryRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				AssetTagDepotEntryRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetTagDepotEntryRelModelImpl assetTagDepotEntryRelModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					assetTagDepotEntryRelModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				assetTagDepotEntryRelModelImpl.getAssetTagId(),
				assetTagDepotEntryRelModelImpl.getDepotEntryId()
			};

			finderCache.putResult(
				_finderPathFetchByAVI_DEI, args,
				assetTagDepotEntryRelModelImpl);
		}
	}

	/**
	 * Creates a new asset tag depot entry rel with the primary key. Does not add the asset tag depot entry rel to the database.
	 *
	 * @param assetTagDepotEntryRelId the primary key for the new asset tag depot entry rel
	 * @return the new asset tag depot entry rel
	 */
	@Override
	public AssetTagDepotEntryRel create(long assetTagDepotEntryRelId) {
		AssetTagDepotEntryRel assetTagDepotEntryRel =
			new AssetTagDepotEntryRelImpl();

		assetTagDepotEntryRel.setNew(true);
		assetTagDepotEntryRel.setPrimaryKey(assetTagDepotEntryRelId);

		String uuid = PortalUUIDUtil.generate();

		assetTagDepotEntryRel.setUuid(uuid);

		assetTagDepotEntryRel.setCompanyId(CompanyThreadLocal.getCompanyId());

		return assetTagDepotEntryRel;
	}

	/**
	 * Removes the asset tag depot entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetTagDepotEntryRelId the primary key of the asset tag depot entry rel
	 * @return the asset tag depot entry rel that was removed
	 * @throws NoSuchDepotEntryRelException if a asset tag depot entry rel with the primary key could not be found
	 */
	@Override
	public AssetTagDepotEntryRel remove(long assetTagDepotEntryRelId)
		throws NoSuchDepotEntryRelException {

		return remove((Serializable)assetTagDepotEntryRelId);
	}

	/**
	 * Removes the asset tag depot entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset tag depot entry rel
	 * @return the asset tag depot entry rel that was removed
	 * @throws NoSuchDepotEntryRelException if a asset tag depot entry rel with the primary key could not be found
	 */
	@Override
	public AssetTagDepotEntryRel remove(Serializable primaryKey)
		throws NoSuchDepotEntryRelException {

		Session session = null;

		try {
			session = openSession();

			AssetTagDepotEntryRel assetTagDepotEntryRel =
				(AssetTagDepotEntryRel)session.get(
					AssetTagDepotEntryRelImpl.class, primaryKey);

			if (assetTagDepotEntryRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDepotEntryRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(assetTagDepotEntryRel);
		}
		catch (NoSuchDepotEntryRelException noSuchEntityException) {
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
	protected AssetTagDepotEntryRel removeImpl(
		AssetTagDepotEntryRel assetTagDepotEntryRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetTagDepotEntryRel)) {
				assetTagDepotEntryRel = (AssetTagDepotEntryRel)session.get(
					AssetTagDepotEntryRelImpl.class,
					assetTagDepotEntryRel.getPrimaryKeyObj());
			}

			if ((assetTagDepotEntryRel != null) &&
				ctPersistenceHelper.isRemove(assetTagDepotEntryRel)) {

				session.delete(assetTagDepotEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetTagDepotEntryRel != null) {
			clearCache(assetTagDepotEntryRel);
		}

		return assetTagDepotEntryRel;
	}

	@Override
	public AssetTagDepotEntryRel updateImpl(
		AssetTagDepotEntryRel assetTagDepotEntryRel) {

		boolean isNew = assetTagDepotEntryRel.isNew();

		if (!(assetTagDepotEntryRel instanceof
				AssetTagDepotEntryRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(assetTagDepotEntryRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					assetTagDepotEntryRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetTagDepotEntryRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetTagDepotEntryRel implementation " +
					assetTagDepotEntryRel.getClass());
		}

		AssetTagDepotEntryRelModelImpl assetTagDepotEntryRelModelImpl =
			(AssetTagDepotEntryRelModelImpl)assetTagDepotEntryRel;

		if (Validator.isNull(assetTagDepotEntryRel.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			assetTagDepotEntryRel.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(assetTagDepotEntryRel)) {
				if (!isNew) {
					session.evict(
						AssetTagDepotEntryRelImpl.class,
						assetTagDepotEntryRel.getPrimaryKeyObj());
				}

				session.save(assetTagDepotEntryRel);
			}
			else {
				assetTagDepotEntryRel = (AssetTagDepotEntryRel)session.merge(
					assetTagDepotEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			AssetTagDepotEntryRelImpl.class, assetTagDepotEntryRelModelImpl,
			false, true);

		cacheUniqueFindersCache(assetTagDepotEntryRelModelImpl);

		if (isNew) {
			assetTagDepotEntryRel.setNew(false);
		}

		assetTagDepotEntryRel.resetOriginalValues();

		return assetTagDepotEntryRel;
	}

	/**
	 * Returns the asset tag depot entry rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset tag depot entry rel
	 * @return the asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a asset tag depot entry rel with the primary key could not be found
	 */
	@Override
	public AssetTagDepotEntryRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDepotEntryRelException {

		AssetTagDepotEntryRel assetTagDepotEntryRel = fetchByPrimaryKey(
			primaryKey);

		if (assetTagDepotEntryRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDepotEntryRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return assetTagDepotEntryRel;
	}

	/**
	 * Returns the asset tag depot entry rel with the primary key or throws a <code>NoSuchDepotEntryRelException</code> if it could not be found.
	 *
	 * @param assetTagDepotEntryRelId the primary key of the asset tag depot entry rel
	 * @return the asset tag depot entry rel
	 * @throws NoSuchDepotEntryRelException if a asset tag depot entry rel with the primary key could not be found
	 */
	@Override
	public AssetTagDepotEntryRel findByPrimaryKey(long assetTagDepotEntryRelId)
		throws NoSuchDepotEntryRelException {

		return findByPrimaryKey((Serializable)assetTagDepotEntryRelId);
	}

	/**
	 * Returns the asset tag depot entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset tag depot entry rel
	 * @return the asset tag depot entry rel, or <code>null</code> if a asset tag depot entry rel with the primary key could not be found
	 */
	@Override
	public AssetTagDepotEntryRel fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				AssetTagDepotEntryRel.class, primaryKey)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		AssetTagDepotEntryRel assetTagDepotEntryRel =
			(AssetTagDepotEntryRel)entityCache.getResult(
				AssetTagDepotEntryRelImpl.class, primaryKey);

		if (assetTagDepotEntryRel != null) {
			return assetTagDepotEntryRel;
		}

		Session session = null;

		try {
			session = openSession();

			assetTagDepotEntryRel = (AssetTagDepotEntryRel)session.get(
				AssetTagDepotEntryRelImpl.class, primaryKey);

			if (assetTagDepotEntryRel != null) {
				cacheResult(assetTagDepotEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return assetTagDepotEntryRel;
	}

	/**
	 * Returns the asset tag depot entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetTagDepotEntryRelId the primary key of the asset tag depot entry rel
	 * @return the asset tag depot entry rel, or <code>null</code> if a asset tag depot entry rel with the primary key could not be found
	 */
	@Override
	public AssetTagDepotEntryRel fetchByPrimaryKey(
		long assetTagDepotEntryRelId) {

		return fetchByPrimaryKey((Serializable)assetTagDepotEntryRelId);
	}

	@Override
	public Map<Serializable, AssetTagDepotEntryRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(AssetTagDepotEntryRel.class)) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetTagDepotEntryRel> map =
			new HashMap<Serializable, AssetTagDepotEntryRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetTagDepotEntryRel assetTagDepotEntryRel = fetchByPrimaryKey(
				primaryKey);

			if (assetTagDepotEntryRel != null) {
				map.put(primaryKey, assetTagDepotEntryRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
						AssetTagDepotEntryRel.class, primaryKey)) {

				AssetTagDepotEntryRel assetTagDepotEntryRel =
					(AssetTagDepotEntryRel)entityCache.getResult(
						AssetTagDepotEntryRelImpl.class, primaryKey);

				if (assetTagDepotEntryRel == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, assetTagDepotEntryRel);
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

			for (AssetTagDepotEntryRel assetTagDepotEntryRel :
					(List<AssetTagDepotEntryRel>)query.list()) {

				map.put(
					assetTagDepotEntryRel.getPrimaryKeyObj(),
					assetTagDepotEntryRel);

				cacheResult(assetTagDepotEntryRel);
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
	 * Returns all the asset tag depot entry rels.
	 *
	 * @return the asset tag depot entry rels
	 */
	@Override
	public List<AssetTagDepotEntryRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<AssetTagDepotEntryRel> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<AssetTagDepotEntryRel> findAll(
		int start, int end,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<AssetTagDepotEntryRel> findAll(
		int start, int end,
		OrderByComparator<AssetTagDepotEntryRel> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetTagDepotEntryRel.class)) {

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

			List<AssetTagDepotEntryRel> list = null;

			if (useFinderCache) {
				list = (List<AssetTagDepotEntryRel>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_ASSETTAGDEPOTENTRYREL);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_ASSETTAGDEPOTENTRYREL;

					sql = sql.concat(
						AssetTagDepotEntryRelModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<AssetTagDepotEntryRel>)QueryUtil.list(
						query, getDialect(), start, end);

					cacheResult(list);

					if (useFinderCache) {
						finderCache.putResult(finderPath, finderArgs, list);
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
	 * Removes all the asset tag depot entry rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetTagDepotEntryRel assetTagDepotEntryRel : findAll()) {
			remove(assetTagDepotEntryRel);
		}
	}

	/**
	 * Returns the number of asset tag depot entry rels.
	 *
	 * @return the number of asset tag depot entry rels
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetTagDepotEntryRel.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_ASSETTAGDEPOTENTRYREL);

					count = (Long)query.uniqueResult();

					finderCache.putResult(
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
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "assetTagDepotEntryRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ASSETTAGDEPOTENTRYREL;
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
		return AssetTagDepotEntryRelModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "AssetTagDepotEntryRel";
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
		ctMergeColumnNames.add("assetTagId");
		ctMergeColumnNames.add("depotEntryId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("assetTagDepotEntryRelId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);
	}

	/**
	 * Initializes the asset tag depot entry rel persistence.
	 */
	@Activate
	public void activate() {
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

		_finderPathWithPaginationFindByAssetTagId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAssetTagId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"assetTagId"}, true);

		_finderPathWithoutPaginationFindByAssetTagId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAssetTagId",
			new String[] {Long.class.getName()}, new String[] {"assetTagId"},
			true);

		_finderPathCountByAssetTagId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAssetTagId",
			new String[] {Long.class.getName()}, new String[] {"assetTagId"},
			false);

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
			new String[] {"assetTagId", "depotEntryId"}, true);

		AssetTagDepotEntryRelUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		AssetTagDepotEntryRelUtil.setPersistence(null);

		entityCache.removeCache(AssetTagDepotEntryRelImpl.class.getName());
	}

	@Override
	@Reference(
		target = AssetTagPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = AssetTagPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = AssetTagPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected CTPersistenceHelper ctPersistenceHelper;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ASSETTAGDEPOTENTRYREL =
		"SELECT assetTagDepotEntryRel FROM AssetTagDepotEntryRel assetTagDepotEntryRel";

	private static final String _SQL_SELECT_ASSETTAGDEPOTENTRYREL_WHERE =
		"SELECT assetTagDepotEntryRel FROM AssetTagDepotEntryRel assetTagDepotEntryRel WHERE ";

	private static final String _SQL_COUNT_ASSETTAGDEPOTENTRYREL =
		"SELECT COUNT(assetTagDepotEntryRel) FROM AssetTagDepotEntryRel assetTagDepotEntryRel";

	private static final String _SQL_COUNT_ASSETTAGDEPOTENTRYREL_WHERE =
		"SELECT COUNT(assetTagDepotEntryRel) FROM AssetTagDepotEntryRel assetTagDepotEntryRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"assetTagDepotEntryRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AssetTagDepotEntryRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AssetTagDepotEntryRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetTagDepotEntryRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}