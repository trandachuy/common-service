package com.mediastep.beecow.common.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Ward entity.
 * 
 * @deprecated since sprint 35. Move to catalog-shared
 */
@Deprecated
public class WardDTO implements Serializable {

    private Long id;

    private String code;

    private String inCountry;

    private String outCountry;

    private String zone;


    private Long cityId;
    
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
    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WardDTO wardDTO = (WardDTO) o;

        if ( ! Objects.equals(id, wardDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WardDTO{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", inCountry='" + inCountry + "'" +
            ", outCountry='" + outCountry + "'" +
            ", zone='" + zone + "'" +
            '}';
    }
}
