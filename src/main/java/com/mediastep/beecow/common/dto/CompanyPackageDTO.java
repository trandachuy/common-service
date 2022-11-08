package com.mediastep.beecow.common.dto;


import java.io.Serializable;
import java.util.Objects;

import com.mediastep.beecow.common.domain.enumeration.PayStatusEnum;
import com.mediastep.beecow.common.dto.AbstractAuditingDTO;

/**
 * A DTO for the CompanyPackage entity.
 */
public class CompanyPackageDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private Long packageId;

    private PayStatusEnum payStatus;

    private Long companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }
    public PayStatusEnum getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(PayStatusEnum payStatus) {
        this.payStatus = payStatus;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyPackageDTO companyPackageDTO = (CompanyPackageDTO) o;

        if ( ! Objects.equals(id, companyPackageDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CompanyPackageDTO{" +
            "id=" + id +
            ", packageId='" + packageId + "'" +
            ", payStatus='" + payStatus + "'" +
            '}';
    }
}
