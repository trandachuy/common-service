/*
 *
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 12/1/2017
 * Author: Loi Tran <email:loi.tran@mediastep.com>
 *
 */

package com.mediastep.beecow.common.security;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.mediastep.beecow.common.errors.ErrorConstants;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

    public static final String SYSTEM_ACCOUNT = "system";

    private SecurityUtils() {
    }

    /**
     * Get user details (authentication principal)
     * @return null if not set
     */
    public static Object getUserDetails() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            return authentication.getPrincipal();
        }
        return null;
    }

    /**
     * Get user details (authentication principal)
     * @return null if not set
     */
    @SuppressWarnings("unchecked")
    public static <T> T getUserDetails(Class<T> clazz) {
        assert(clazz != null);
        Object userDetails = getUserDetails();
        if (userDetails != null && clazz.isAssignableFrom(userDetails.getClass())) {
            return (T) userDetails;
        }
        else {
            return null;
        }
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user, null if not set
     */
    public static String getCurrentUserLogin() {
        Object userDetails = getUserDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails) userDetails).getUsername();
        }
        else if (userDetails instanceof String) {
            return ((String) userDetails);
        }
        else {
            return null;
        }
    }

    public static Optional<String> findCurrentUserLogin() {
        return Optional.ofNullable(getCurrentUserLogin());
    }

    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user
     */
    public static String getCurrentUserJWT() {
        return doGetCurrentUserJWT();
    }

    public static Optional<String> findCurrentUserJWT() {
        return Optional.ofNullable(getCurrentUserJWT());
    }

    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user
     */
    private static String doGetCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof String) {
            return (String) authentication.getCredentials();
        }
        return null;
    }

    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user
     */
    public static String getCurrentUserBearerJWT() {
    	String token = doGetCurrentUserJWT();
        String bearerToken = "Bearer " + token;
        return bearerToken;
    }

    /**
     * Get the ID of the current user.
     *
     * @return the ID of the current user, null if not set
     */
    public static Long getCurrentUserId() {
        Object userDetials = getUserDetails();
        if (userDetials instanceof ITokenUserDetails) {
            return ((ITokenUserDetails) userDetials).getId();
        }
        else {
            return null;
        }
    }

    /**
     * Get location code of the current user
     *
     * @return location code of the current user, null if not set
     */
    public static String getCurrentUserLocationCode() {
        Object userDetails = getUserDetails();
        if (userDetails instanceof ITokenUserDetails)
            return ((ITokenUserDetails) userDetails).getLocationCode();
        else
            return null;
    }

    /**
     * Get the ID of the current user.
     *
     * @return the ID of the current user, null if not set
     */
    public static Long getCurrentStoreId() {
        Object userDetials = getUserDetails();
        if (userDetials instanceof ITokenStoreDetails) {
            return ((ITokenStoreDetails) userDetials).getStoreId();
        }
        else {
            return null;
        }
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().stream()
                .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(AuthoritiesConstants.ANONYMOUS));
        }
        return false;
    }

    /**
     * If the current user has a specific authority (security role).
     *
     * <p>The name of this method comes from the isUserInRole() method in the Servlet API</p>
     *
     * @return true if the current user has the authority, false otherwise
     */
    public static boolean isCurrentUserActivated() {
        return isCurrentUserInRole(Arrays.asList(AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN));
    }

    /**
     * If the current user has a specific authority (security role).
     *
     * <p>The name of this method comes from the isUserInRole() method in the Servlet API</p>
     *
     * @param authority the authority to check
     * @return true if the current user has the authority, false otherwise
     */
    public static boolean isCurrentUserInRole(String authority) {
        Collection<? extends GrantedAuthority> authorities = getCurrentAuthorities();
        return authorities.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority));
    }

    public static boolean isAdmin() {
    	return isCurrentUserInRole(AuthoritiesConstants.ADMIN);
    }

    public static boolean isEditor() {
    	return isCurrentUserInRole(AuthoritiesConstants.EDITOR);
    }

    public static boolean isSystem() {
    	String login = getCurrentUserLogin();
    	return StringUtils.isBlank(login) || SYSTEM_ACCOUNT.equals(login) || isAdmin();
    }

    public static boolean isBeecow() {
    	return isCurrentUserInRole(AuthoritiesConstants.BEECOW);
    }

    public static boolean isGuest() {
    	return isCurrentUserInRole(AuthoritiesConstants.GUEST);
    }

    private static Collection<? extends GrantedAuthority> getCurrentAuthorities() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null) {
        	return Collections.emptyList();
        }
        Object principal = authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities;
        if (principal instanceof UserDetails) {
        	authorities = ((UserDetails) principal).getAuthorities();
        }
        else {
        	authorities = authentication.getAuthorities();
        }
        return authorities;
    }

    /**
     * If the current user has one of specific authorities (security role).
     *
     * <p>The name of this method comes from the isUserInRole() method in the Servlet API</p>
     *
     * @param authorities the authority to check
     * @return true if the current user has the authority, false otherwise
     */
    public static boolean isCurrentUserInRole(List<String> authorities) {
        Collection<? extends GrantedAuthority> curAuthorities = getCurrentAuthorities();
        return curAuthorities.stream().anyMatch(grantedAuthority -> authorities.contains(grantedAuthority.getAuthority()));
    }

    /**
     * If input user ID equals to the current user ID .
     * @param userId input user ID to be checked
     * @return true if input user ID equals to the current user ID .
     */
    public static boolean isCurrentUser(Long userId) {
        Long currentUserId = getCurrentUserId();
        return (currentUserId != null && currentUserId.equals(userId));
    }

    /**
     * Check if given user ID is current user or is ADMIN.
     *
     * @param userId user ID to check
     * @throw AccessDeniedException if given user ID is not current user and is not ADMIN
     */
    public static void assertIsCurrentUser(long userId) {
        if (!isCurrentUser(userId)) {
            throw new AccessDeniedException(ErrorConstants.ERR_ACCESS_DENIED);
        }
    }

    /**
     * If input store ID equals to the current store ID .
     */
    public static boolean isCurrentStore(Long storeId) {
        Long currentStoreId = getCurrentStoreId();
        return (currentStoreId != null && currentStoreId.equals(storeId));
    }

    /**
     * Check f input store ID equals to the current store ID .
     * @throw AccessDeniedException if given user ID is not current user and is not ADMIN
     */
    public static void assertIsCurrentStore(long storeId) {
        if (!isCurrentStore(storeId)) {
            throw new AccessDeniedException(ErrorConstants.ERR_ACCESS_DENIED);
        }
    }

    /**
     * Check if given user ID is current user or is ADMIN.
     *
     * @throw AccessDeniedException if given user ID is not current user and is not ADMIN
     */
    public static void assertIsUser() {
        if (!isCurrentUserInRole(AuthoritiesConstants.USER)) {
            throw new AccessDeniedException(ErrorConstants.ERR_ACCESS_DENIED);
        }
    }

    /**
     * Check if given user ID is current user or is ADMIN.
     *
     * @throw AccessDeniedException if given user ID is not current user and is not ADMIN
     */
    public static void assertIsAdmin() {
        if (!isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            throw new AccessDeniedException(ErrorConstants.ERR_ACCESS_DENIED);
        }
    }

    /**
     * Check if given user ID is current user or is ADMIN.
     *
     * @param userId user ID to check
     * @throw AccessDeniedException if given user ID is not current user and is not ADMIN
     */
    public static void assertIsCurrentUserOrAdmin(long userId) {
        if (!isCurrentUser(userId) && !isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            throw new AccessDeniedException(ErrorConstants.ERR_ACCESS_DENIED);
        }
    }

    /**
     * Check if given user ID is current user or is ADMIN.
     *
     * @param userId user ID to check
     * @return true if given user ID is current user or is ADMIN, otherwise return false
     */
    public static boolean isAdminOrCurrentUser(Long userId){
		if (isCurrentUser(userId) || isCurrentUserInRole(AuthoritiesConstants.ADMIN)){
			return true;
		}
		return false;
	}

    /**
     * Set current user as login user
     * @param tokenUser
     * @return previous Authentication object
     */
    public static Authentication setCurrentUser(ITokenUserDetails tokenUser) {
        // Get current context Authentication object
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication prevAuth = context.getAuthentication();
        // Set user
        Authentication authentication = new UsernamePasswordAuthenticationToken(tokenUser, null, tokenUser.getAuthorities());
        context.setAuthentication(authentication);
        return prevAuth;
    }

    /**
     * Set current user with given user ID, login name and authorities
     * @param userId
     * @param login
     * @param authorities
     * @return previous Authentication object
     */
    public static Authentication setCurrentUser(Long userId, String login, String... authorities) {
        ITokenUserDetails tokenUser = createTokenUserDetails(userId, login, authorities);
        return setCurrentUser(tokenUser);
    }

    private static ITokenUserDetails createTokenUserDetails(Long userId, String login, String... authorityNames) {
        Set<TokenUserAuthority> authorities = toTokenUserAuthorities(authorityNames);
        TokenDetails tokenUser = TokenDetails.builder()
                .id(userId)
                .username(login)
                .activated(true)
                .authorities(authorities).build();
//        tokenUser.setId(userId);
//        tokenUser.setUsername(login);
//        tokenUser.setActivated(true);
//        tokenUser.setAuthorities(authorities);
        return tokenUser;
    }

    private static Set<TokenUserAuthority> toTokenUserAuthorities(String... authorityNames) {
        Set<TokenUserAuthority> authorities = new HashSet<>();
        for (String authorityName : authorityNames) {
            TokenUserAuthority authority = new TokenUserAuthority(authorityName);
            authorities.add(authority);
        }
        return authorities;
    }
}
