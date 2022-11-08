/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 12/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *******************************************************************************/
package com.mediastep.beecow.common.dto;

import com.mediastep.beecow.common.domain.enumeration.AuthorTypeEnum;

@Deprecated
public class AuthorDTO {
	private Long userId;

	private String displayName;

	private ImageDTO avatarImage;
	
	private AuthorTypeEnum type;
	
	private String city;
	
	private Float rate;
	
	private Integer policyTermId;
	
	public AuthorDTO(){
		
	}
	
	public AuthorDTO(Long userId){
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public AuthorTypeEnum getType() {
		return type;
	}

	public void setType(AuthorTypeEnum type) {
		this.type = type;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public Integer getPolicyTermId() {
		return policyTermId;
	}

	public void setPolicyTermId(Integer policyTermId) {
		this.policyTermId = policyTermId;
	}

	public ImageDTO getAvatarImage() {
		return avatarImage;
	}

	public void setAvatarImage(ImageDTO avatarImage) {
		this.avatarImage = avatarImage;
	}

	@Override
	public String toString() {
		return "AuthorDTO [userId=" + userId + ", displayName=" + displayName + ", type="
				+ type + ", city=" + city + ", rate=" + rate + ", policyTermId=" + policyTermId + "]";
	}
	
}
