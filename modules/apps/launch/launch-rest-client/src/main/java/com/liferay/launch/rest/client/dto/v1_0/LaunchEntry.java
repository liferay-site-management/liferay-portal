/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.rest.client.dto.v1_0;

import com.liferay.launch.rest.client.function.UnsafeSupplier;
import com.liferay.launch.rest.client.serdes.v1_0.LaunchEntrySerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author David Truong
 * @generated
 */
@Generated("")
public class LaunchEntry implements Cloneable, Serializable {

	public static LaunchEntry toDTO(String json) {
		return LaunchEntrySerDes.toDTO(json);
	}

	public Map<String, Map<String, String>> getActions() {
		return actions;
	}

	public void setActions(Map<String, Map<String, String>> actions) {
		this.actions = actions;
	}

	public void setActions(
		UnsafeSupplier<Map<String, Map<String, String>>, Exception>
			actionsUnsafeSupplier) {

		try {
			actions = actionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Map<String, String>> actions;

	public Long getClassNameId() {
		return classNameId;
	}

	public void setClassNameId(Long classNameId) {
		this.classNameId = classNameId;
	}

	public void setClassNameId(
		UnsafeSupplier<Long, Exception> classNameIdUnsafeSupplier) {

		try {
			classNameId = classNameIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long classNameId;

	public Long getClassPK() {
		return classPK;
	}

	public void setClassPK(Long classPK) {
		this.classPK = classPK;
	}

	public void setClassPK(
		UnsafeSupplier<Long, Exception> classPKUnsafeSupplier) {

		try {
			classPK = classPKUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long classPK;

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDateCreated(
		UnsafeSupplier<Date, Exception> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateCreated;

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public void setDateModified(
		UnsafeSupplier<Date, Exception> dateModifiedUnsafeSupplier) {

		try {
			dateModified = dateModifiedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	public Long getLaunchSetId() {
		return launchSetId;
	}

	public void setLaunchSetId(Long launchSetId) {
		this.launchSetId = launchSetId;
	}

	public void setLaunchSetId(
		UnsafeSupplier<Long, Exception> launchSetIdUnsafeSupplier) {

		try {
			launchSetId = launchSetIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long launchSetId;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setStatus(
		UnsafeSupplier<Status, Exception> statusUnsafeSupplier) {

		try {
			status = statusUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Status status;

	@Override
	public LaunchEntry clone() throws CloneNotSupportedException {
		return (LaunchEntry)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LaunchEntry)) {
			return false;
		}

		LaunchEntry launchEntry = (LaunchEntry)object;

		return Objects.equals(toString(), launchEntry.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return LaunchEntrySerDes.toJSON(this);
	}

}