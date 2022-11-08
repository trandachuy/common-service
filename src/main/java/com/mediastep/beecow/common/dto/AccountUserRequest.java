package com.mediastep.beecow.common.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Account user request.
 */
@Data
@NoArgsConstructor
public class AccountUserRequest {
    private String name;
    private String phone;
    private String email;
    private String password;
    private String locationCode;
    private String langKey;
    private String countryCode;

    @Builder
    public AccountUserRequest(String name, String phone, String email, String password, String locationCode, String langKey, String countryCode) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.locationCode = locationCode;
        this.langKey = langKey;
        this.countryCode = countryCode;
    }
}
