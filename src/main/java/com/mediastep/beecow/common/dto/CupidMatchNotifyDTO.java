package com.mediastep.beecow.common.dto;


import java.io.Serializable;

/**
 * A DTO for the UserToken entity.
 */
public class CupidMatchNotifyDTO extends NotifyDTO implements Serializable {
	
	private static final long serialVersionUID = -4070441222413720361L;

	private Long targetId;
	
	private String feature;
	
	private Long imgId;
	
	private String imgUrlPrefix;
	
	private String aliasName;

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public Long getImgId() {
		return imgId;
	}

	public void setImgId(Long imgId) {
		this.imgId = imgId;
	}

	public String getImgUrlPrefix() {
		return imgUrlPrefix;
	}

	public void setImgUrlPrefix(String imgUrlPrefix) {
		this.imgUrlPrefix = imgUrlPrefix;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((aliasName == null) ? 0 : aliasName.hashCode());
		result = prime * result + ((feature == null) ? 0 : feature.hashCode());
		result = prime * result + ((imgId == null) ? 0 : imgId.hashCode());
		result = prime * result + ((imgUrlPrefix == null) ? 0 : imgUrlPrefix.hashCode());
		result = prime * result + ((targetId == null) ? 0 : targetId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CupidMatchNotifyDTO other = (CupidMatchNotifyDTO) obj;
		if (aliasName == null) {
			if (other.aliasName != null)
				return false;
		} else if (!aliasName.equals(other.aliasName))
			return false;
		if (feature == null) {
			if (other.feature != null)
				return false;
		} else if (!feature.equals(other.feature))
			return false;
		if (imgId == null) {
			if (other.imgId != null)
				return false;
		} else if (!imgId.equals(other.imgId))
			return false;
		if (imgUrlPrefix == null) {
			if (other.imgUrlPrefix != null)
				return false;
		} else if (!imgUrlPrefix.equals(other.imgUrlPrefix))
			return false;
		if (targetId == null) {
			if (other.targetId != null)
				return false;
		} else if (!targetId.equals(other.targetId))
			return false;
		return true;
	}
	
}
