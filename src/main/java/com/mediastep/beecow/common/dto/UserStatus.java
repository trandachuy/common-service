/*
 *
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 12/1/2017
 * Author: Loi Tran <email:loi.tran@mediastep.com>
 *
 */

package com.mediastep.beecow.common.dto;

/**
 * A DTO representing a user, with his authorities.
 */
public enum UserStatus {
	/**
	 * Role is GUEST
	 */
	PRE_ACTIVATED,

	/**
	 * Registered but not activated
	 */
	REGISTERED,

	/**
	 * Registered and activated
	 */
	ACTIVATED,

	/**
	 * Locked to access the system
	 */
	LOCKED
}
