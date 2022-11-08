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
 * A DTO for the Country entity.
 */
public class CountryDTO implements Serializable {
	
	private static final long serialVersionUID = -3123151992437461467L;

	private Long id;
    
    private String code;
    
    private String name;
    
    private String inCountry;
    
    private String outCountry;

    private Long countryId;
    
    private ImageDTO flag;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer orderNumber;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean isShow;
    
    List<CityDTO> cities;

    private int phoneCode;

    private String currencyCode;

    private String currencySymbol;

    private String currencyName;

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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

	public String getInCountry() {
		return inCountry;
	}

	public void setInCountry(String inCountry) {
		this.inCountry = inCountry;
	}

	public ImageDTO getFlag() {
		return flag;
	}

	public void setFlag(ImageDTO flag) {
		this.flag = flag;
	}

	public List<CityDTO> getCities() {
		return cities;
	}

	public void setCities(List<CityDTO> cities) {
		this.cities = cities;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

    public int getPhoneCode() {
        return phoneCode;
    }

	public String getActualPhoneCode() {
		return "+" + phoneCode;
	}
    public void setPhoneCode(int phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CountryDTO countryDTO = (CountryDTO) o;

        if ( ! Objects.equals(id, countryDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CountryDTO{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            '}';
    }
    
    public static Comparator<CountryDTO> CountryOrderComparator = new Comparator<CountryDTO>() {
    	public int compare(CountryDTO country1, CountryDTO country2) {
			try {
				return country1.getOrderNumber() - country2.getOrderNumber();
			} catch (Exception e){
				return 0;
			}
		}
    };
}
