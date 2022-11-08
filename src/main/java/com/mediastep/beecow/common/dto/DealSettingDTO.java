package com.mediastep.beecow.common.dto;

import com.mediastep.beecow.common.domain.enumeration.DealTypeEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A DTO for the DealSetting entity.
 */
public class DealSettingDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 5)
    private String title;

    private Integer hourLeft;

    private Long voucherLeft;

    private Integer sizeWeb;

    private Float numberOfDeal;

    private DealTypeEnum type;

    private Integer sizeMobile;

    private Integer orderCancellableTime;

    private Map<String, String> metadata = new HashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getHourLeft() {
        return hourLeft;
    }

    public void setHourLeft(Integer hourLeft) {
        this.hourLeft = hourLeft;
    }
    public Long getVoucherLeft() {
        return voucherLeft;
    }

    public void setVoucherLeft(Long voucherLeft) {
        this.voucherLeft = voucherLeft;
    }
    public Integer getSizeWeb() {
        return sizeWeb;
    }

    public void setSizeWeb(Integer sizeWeb) {
        this.sizeWeb = sizeWeb;
    }

    public Float getNumberOfDeal() {
        return numberOfDeal;
    }

    public void setNumberOfDeal(Float numberOfDeal) {
        this.numberOfDeal = numberOfDeal;
    }
    public DealTypeEnum getType() {
        return type;
    }

    public void setType(DealTypeEnum type) {
        this.type = type;
    }
    public Integer getSizeMobile() {
        return sizeMobile;
    }

    public void setSizeMobile(Integer sizeMobile) {
        this.sizeMobile = sizeMobile;
    }
    public Integer getOrderCancellableTime() {
        return orderCancellableTime;
    }

    public void setOrderCancellableTime(Integer orderCancellableTime) {
        this.orderCancellableTime = orderCancellableTime;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DealSettingDTO dealSettingDTO = (DealSettingDTO) o;

        if ( ! Objects.equals(id, dealSettingDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DealSettingDTO{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", hourLeft='" + hourLeft + "'" +
            ", voucherLeft='" + voucherLeft + "'" +
            ", sizeWeb='" + sizeWeb + "'" +
            ", numberOfDeal='" + numberOfDeal + "'" +
            ", type='" + type + "'" +
            ", sizeMobile='" + sizeMobile + "'" +
            ", orderCancellableTime='" + orderCancellableTime + "'" +
            '}';
    }
}
