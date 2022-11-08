/*
 *
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 12/1/2017
 * Author: Loi Tran <email:loi.tran@mediastep.com>
 *
 */

package com.mediastep.beecow.common.dto;

import com.mediastep.beecow.common.config.Constants;
import com.mediastep.beecow.common.domain.enumeration.AccountType;
import com.mediastep.beecow.common.domain.enumeration.Gender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 * A DTO representing a user, with his authorities.
 */
@ApiModel(value = "UserDTO", description = "A DTO of user entity")
public class UserDTO implements Serializable {

    public static final int FIRST_NAME_MAX_LENGTH = 50;
    public static final int LAST_NAME_MAX_LENGTH = 50;
    public static final int DISPLAY_NAME_MAX_LENGTH = 128;
    public static final int COMPANY_NAME_MAX_LENGTH = 255;
    public static final int TAX_CODE_MAX_LENGTH = 150;
    public static final int EMAIL_MIN_LENGTH = 5;
    public static final int EMAIL_MAX_LENGTH = 100;
    public static final int LOCATION_CODE_MIN_LENGTH = 2;
    public static final int LOCATION_CODE_MAX_LENGTH = 10;
    public static final int LANG_KEY_MIN_LENGTH = 2;
    public static final int LANG_KEY_MAX_LENGTH = 10;

    @ApiModelProperty(value = "Unique ID of an user", readOnly = true)
    private Long id;

    @ApiModelProperty(value = "Unique login name", required = true)
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 100)
    private String login;

    @ApiModelProperty(value = "Frist name")
    @Size(max = FIRST_NAME_MAX_LENGTH)
    private String firstName;

    @ApiModelProperty(value = "Last name")
    @Size(max = LAST_NAME_MAX_LENGTH)
    private String lastName;

    @ApiModelProperty(value = "Display name")
    @Size(max = DISPLAY_NAME_MAX_LENGTH)
    private String displayName;

    @ApiModelProperty(value = "Company name")
    @Size(max = COMPANY_NAME_MAX_LENGTH)
    private String companyName;

    @ApiModelProperty(value = "Tax code")
    @Size(max = TAX_CODE_MAX_LENGTH)
    private String taxCode;

    @ApiModelProperty(value = "Email")
    @Email
    @Size(min = EMAIL_MIN_LENGTH, max = EMAIL_MAX_LENGTH)
    private String email;

    @ApiModelProperty(value = "Mobile phone number")
    private PhoneDTO mobile;

    private AccountType accountType;

    private ZonedDateTime dateOfBirth;

    private Gender gender;

    @ApiModelProperty(value = "Avatar URL")
    private ImageDTO avatarUrl;

    @ApiModelProperty(value = "Cover Photo URL")
    private ImageDTO coverPhotoUrl;
    
    @ApiModelProperty(value = "Activated")
    private boolean activated = false;

    @ApiModelProperty(value = "User status")
    private UserStatus status;

    @ApiModelProperty(value = "Location code (ISO_3166-2)")
    @Size(min = LOCATION_CODE_MIN_LENGTH, max = LOCATION_CODE_MAX_LENGTH)
    private String locationCode;

    @ApiModelProperty(value = "Language code (ISO 639-1)")
    @Size(min = LANG_KEY_MIN_LENGTH, max = LANG_KEY_MAX_LENGTH)
    private String langKey;

    @ApiModelProperty(value = "Authorities, such as ADMIN, USER, GUEST or BEECOW. " +
        "User with Authority is GUEST is pre-activate user. " +
        "User with Authority is BEECOW is system user for frontend (this user is used for frontend to interact with backend after logout, and cannot be edited).")
    private Set<String> authorities;

    private String address;

    private String districtCode;

    private String wardCode;

    private Boolean skipInterested;

    private Boolean createShopCart;

    private boolean userAlreadyExist = false;

    public UserDTO() {
    }

    public UserDTO(String login, String firstName, String lastName,
        String email, boolean activated, String langKey, Set<String> authorities) {

        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.activated = activated;
        this.langKey = langKey;
        this.authorities = authorities;
    }

    public UserDTO(String login, String firstName, String lastName,
                   String email, boolean activated, String langKey, Set<String> authorities,
                   String locationCode, AccountType accountType, String displayName, String companyName, String taxCode) {
        this(login, firstName, lastName, email, activated, langKey, authorities);
        this.locationCode = locationCode;
        this.accountType = accountType;
        this.displayName = displayName;
        this.companyName = companyName;
        this.taxCode = taxCode;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**

     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**

     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**

     * @return the taxCode
     */
    public String getTaxCode() {
        return taxCode;
    }

    /**
     * @param taxCode the taxCode to set
     */
    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the mobile
     */
    public PhoneDTO getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(PhoneDTO mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the accountType
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * @param accountType the accountType to set
     */
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    /**
     * @return the dateOfBirth
     */
    public ZonedDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @param dateOfBirth the dateOfBirth to set
     */
    public void setDateOfBirth(ZonedDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * @return the gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * @return the avatarUrl
     */
    public ImageDTO getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * @param avatarUrl the avatarUrl to set
     */
    public void setAvatarUrl(ImageDTO avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * @return the CoverPhotoUrl
     */
    public ImageDTO getCoverPhotoUrl() {
        return coverPhotoUrl;
    }

    /**
     * @param coverPhotoUrl the CoverPhotoUrl to set
     */
    public void setCoverPhotoUrl(ImageDTO coverPhotoUrl) {
        this.coverPhotoUrl = coverPhotoUrl;
    }

    /**
     * @return the activated
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * @param activated the activated to set
     */
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    /**
	 * @return the status
	 */
	public UserStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(UserStatus status) {
		this.status = status;
	}

	/**
     * @return the locationCode
     */
    public String getLocationCode() {
        return locationCode;
    }

    /**
     * @param locationCode the locationCode to set
     */
    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    /**
     * @return the langKey
     */
    public String getLangKey() {
        return langKey;
    }

    /**
     * @param langKey the langKey to set
     */
    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    /**
     * @return the authorities
     */
    public Set<String> getAuthorities() {
        return authorities;
    }

    /**
     * @param authorities the authorities to set
     */
    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", taxCode='" + taxCode + '\'' +
                ", email='" + email + '\'' +
                ", mobile=" + mobile +
                ", accountType=" + accountType +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                ", avatarUrl=" + avatarUrl +
                ", coverPhotoUrl=" + coverPhotoUrl +
                ", activated=" + activated +
                ", status=" + status +
                ", locationCode='" + locationCode + '\'' +
                ", langKey='" + langKey + '\'' +
                ", authorities=" + authorities +
                ", address='" + address + '\'' +
                ", districtCode='" + districtCode + '\'' +
                ", wardCode='" + wardCode + '\'' +
                ", skipInterested=" + skipInterested +
                ", createShopCart=" + createShopCart +
                ", userAlreadyExist=" + userAlreadyExist +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getWardCode() {
        return wardCode;
    }

    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }

    public Boolean getSkipInterested() {
        return skipInterested;
    }

    public void setSkipInterested(Boolean skipInterested) {
        this.skipInterested = skipInterested;
    }

    public Boolean getCreateShopCart() {
        return createShopCart;
    }

    public void setCreateShopCart(Boolean createShopCart) {
        this.createShopCart = createShopCart;
    }

    public boolean isUserAlreadyExist() {
        return userAlreadyExist;
    }

    public void setUserAlreadyExist(boolean userAlreadyExist) {
        this.userAlreadyExist = userAlreadyExist;
    }

}
