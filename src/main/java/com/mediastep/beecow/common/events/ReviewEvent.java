/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 01/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *  
 *******************************************************************************/
package com.mediastep.beecow.common.events;

import com.mediastep.beecow.common.dto.ReviewDTO;
import com.mediastep.beecow.common.events.EntityEvent;

public class ReviewEvent extends EntityEvent<ReviewDTO> {

    private static final long serialVersionUID = 1L;

    private ReviewDTO oldState;

	public ReviewDTO getOldState() {
		return oldState;
	}

	public void setOldState(ReviewDTO oldState) {
		this.oldState = oldState;
	}

	@Override
	public String toString() {
		return "ReviewEvent [oldState=" + oldState + ", getEntity()=" + getEntity() + ", getType()=" + getType() + "]";
	}
	
}
