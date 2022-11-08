/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 26/1/2017
 * Author: Huyen Lam <huyen.lam@mediastep.com>
 *******************************************************************************/
package com.mediastep.beecow.common.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mediastep.beecow.common.security.AuthoritiesConstants;
import com.mediastep.beecow.common.security.TokenStoreDetails;
import com.mediastep.beecow.common.security.TokenUserAuthority;
import com.mediastep.beecow.common.security.TokenUserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Utility class for testing REST controllers.
 */
public class CommonTestUtil {

    private static final String AUTHORITIES_KEY = "auth";

    private static final String USER_ID_KEY = "userId";

    private static final String STORE_ID_KEY = "storeId";

    private static final String SECRET_KEY = "unit-test";

    private static final long TOKEN_VALIDITY = 60 * 60 * 1000; // 1 hour

    public static final long ADMIN_USER_ID = 3; // according to gateway initial data

    public static final String ADMIN_USER_LOGIN = "admin"; // according to gateway initial data

    public static final long SYSTEM_USER_ID = 1; // according to gateway initial data

    public static final long SYSTEM_BEECOW_ID = 5; // according to gateway initial data

    public static final String SYSTEM_USER_LOGIN = "system"; // according to gateway initial data

    public static final String SYSTEM_BEECOW_LOGIN = "beecowuser"; // according to gateway initial data

    public static final String SYSTEM_GUEST_LOGIN = "guest"; // according to gateway initial data

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Convert an object to JSON byte array.
     *
     * @param object
     *            the object to convert
     * @return the JSON byte array
     * @throws IOException
     */
    public static byte[] convertObjectToJsonBytes(Object object)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);

        return mapper.writeValueAsBytes(object);
    }

    public static Map<String, Object> convertJsonToMap(String json) throws JsonParseException, JsonMappingException, IOException {
        Map<String, Object> map = MAPPER.readValue(json, new TypeReference<Map<String, Object>>(){});
        return map;
    }

    public static void cleanUp(SecurityContext securityContext) {
        securityContext.setAuthentication(null);
    }

    public static String createToken(SecurityContext securityContext, Long userId, String login, List<TokenUserAuthority> authorities) {
        TokenUserDetails user = createTokenUserDetails(userId, login, authorities);
        long now = System.nanoTime();
        Date validity = new Date(now + TOKEN_VALIDITY);
        String token = Jwts.builder()
            .setSubject(login)
            .claim(AUTHORITIES_KEY, authorities)
            .claim(USER_ID_KEY, userId)
            .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
            .setExpiration(validity)
            .compact();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, token);
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
        return token;
    }

    private static TokenUserDetails createTokenUserDetails(Long userId, String login, List<TokenUserAuthority> authorities) {
        TokenUserDetails user = new TokenUserDetails();
        user.setId(userId);
        user.setUsername(login);
        user.setAuthorities(new HashSet<>(authorities));
        return user;
    }

    public static String createStoreToken(SecurityContext securityContext, Long storeId, Long userId) {
        List<TokenUserAuthority> authorities = Arrays.asList(new TokenUserAuthority(AuthoritiesConstants.STORE));
        TokenUserDetails user = createTokenUserDetails(userId, String.valueOf(userId), authorities);
        TokenStoreDetails store = new TokenStoreDetails(user);
        store.setStoreId(storeId);
        long now = (new Date()).getTime();
        Date validity = new Date(now + TOKEN_VALIDITY);
        String token = Jwts.builder()
            .setSubject(user.getUsername())
            .claim(AUTHORITIES_KEY, authorities)
            .claim(USER_ID_KEY, userId)
            .claim(STORE_ID_KEY, storeId)
            .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
            .setExpiration(validity)
            .compact();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(store, token);
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
        return token;
    }

    public static String createUserToken(SecurityContext securityContext, Long userId, String login) {
        return createToken(securityContext, userId, login,
            Arrays.asList(new TokenUserAuthority[] {TokenUserAuthority.getAuthority(AuthoritiesConstants.USER)}));
    }

    public static String createGuestToken(SecurityContext securityContext, Long userId) {
        return createToken(securityContext, userId, SYSTEM_GUEST_LOGIN,
            Arrays.asList(new TokenUserAuthority[] {TokenUserAuthority.getAuthority(AuthoritiesConstants.GUEST)}));
    }

    public static String createBeecowToken(SecurityContext securityContext) {
        return createToken(securityContext, SYSTEM_BEECOW_ID, SYSTEM_BEECOW_LOGIN,
            Arrays.asList(new TokenUserAuthority[] {
        		TokenUserAuthority.getAuthority(AuthoritiesConstants.GUEST),
        		TokenUserAuthority.getAuthority(AuthoritiesConstants.BEECOW)}));
    }

    public static String createEditorToken(SecurityContext securityContext, Long userId, String login) {
        return createToken(securityContext, userId, login,
            Arrays.asList(new TokenUserAuthority[] {TokenUserAuthority.getAuthority(AuthoritiesConstants.EDITOR)}));
    }

    public static String createAdminToken(SecurityContext securityContext) {
        return createToken(securityContext, ADMIN_USER_ID, ADMIN_USER_LOGIN,
            Arrays.asList(new TokenUserAuthority[] {TokenUserAuthority.getAuthority(AuthoritiesConstants.ADMIN)}));
    }

    public static String createSystemToken(SecurityContext securityContext) {
        return createToken(securityContext, SYSTEM_USER_ID, SYSTEM_USER_LOGIN,
            Arrays.asList(new TokenUserAuthority[] {TokenUserAuthority.getAuthority(AuthoritiesConstants.ADMIN)}));
    }
}
