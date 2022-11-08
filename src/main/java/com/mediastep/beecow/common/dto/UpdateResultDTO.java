/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 26/1/2017
 * Author: Huyen Lam <huyen.lam@mediastep.com>
 *******************************************************************************/
package com.mediastep.beecow.common.dto;

import java.io.Serializable;

/**
 * The DTO return updated record count.
 */
public class UpdateResultDTO implements Serializable {

    private static final long serialVersionUID = -3846716954854515272L;

    private int updatedCount;

    public UpdateResultDTO() {
        this.updatedCount = -1; // unknown
    }

    public UpdateResultDTO(int updatedCount) {
        this.updatedCount = updatedCount;
    }

    public int getUpdatedCount() {
        return updatedCount;
    }

    public void setUpdatedCount(int updatedSize) {
        this.updatedCount = updatedSize;
    }

    @Override
    public String toString() {
        return "UpdateResultDTO{" +
            "updatedCount=" + updatedCount +
            '}';
    }
}
