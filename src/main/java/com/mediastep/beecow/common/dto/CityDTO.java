/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 01/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *  
 *******************************************************************************/
package com.mediastep.beecow.common.dto;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * A DTO for the City entity.
 */
public class CityDTO implements Serializable {

    private Long id;

    private String code;

    private String inCountry;

    private String outCountry;

    private Long countryId;

    List<DistrictDTO> districts;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean isShow;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer orderNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getInCountry() {
        return inCountry;
    }

    public void setInCountry(String inCountry) {
        this.inCountry = inCountry;
    }
    public String getOutCountry() {
        return outCountry;
    }

    public void setOutCountry(String outCountry) {
        this.outCountry = outCountry;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }
    
    public boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(boolean isShow) {
		this.isShow = isShow;
	}
	
	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public List<DistrictDTO> getDistricts() {
		return districts;
	}

	public void setDistricts(List<DistrictDTO> districts) {
		this.districts = districts;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CityDTO cityDTO = (CityDTO) o;

        if ( ! Objects.equals(id, cityDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CityDTO{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", inCountry='" + inCountry + "'" +
            ", outCountry='" + outCountry + "'" +
            '}';
    }
    
    public static Comparator<CityDTO> CityOrderComparator = new Comparator<CityDTO>() {
    	public int compare(CityDTO city1, CityDTO city2) {
			try {
				return city1.getOrderNumber() - city2.getOrderNumber();
			} catch (Exception e){
				return 0;
			}
		}
    };
    
}
