/**
 * Copyright 2017 (C) Mediastep Software Inc.
 * <p>
 * Created on : 4/14/17
 * Author: Loi Tran <email:loi.tran@mediastep.com>
 */
package com.mediastep.beecow.common.dto;

import com.mediastep.beecow.common.domain.enumeration.AuthorTypeEnum;

import java.io.Serializable;

public class SwitchProfileDTO implements Serializable {
    private static final long serialVersionUID = -9099733068472389795L;
    private AuthorTypeEnum authorTypeEnum;
    private Long pageId;

    public AuthorTypeEnum getAuthorTypeEnum() {
        return authorTypeEnum;
    }

    public void setAuthorTypeEnum(AuthorTypeEnum authorTypeEnum) {
        this.authorTypeEnum = authorTypeEnum;
    }

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}
}
