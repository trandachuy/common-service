/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 01/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *  
 *******************************************************************************/
package com.mediastep.beecow.common.service;

import com.mediastep.beecow.common.config.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidParameterException;
import java.util.*;

public class LocationBasedServiceFactory<S> {

    // TODO consider service that can apply on all locations or most of the locations

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Map of location-code and a collection of service-name and it instance.
     * <pre>
     * Map<String, S> services = locationToServiceMap.get("VN");
     * S service = services.get("myService");
     * </pre>
     */
    private Map<String, Map<String, S>> locationToServiceMap = new HashMap<>();

    private Map<String, S> serviceMap = new HashMap<>();

    public void addServices(String locationCode, String serviceName, S service) {
        log.debug("Request to add location based service '{}' for location '{}'", serviceName, locationCode);
        if (StringUtils.isBlank(locationCode)) {
            throw new InvalidParameterException("Failed to add location based service: locationCode must not be blank");
        }
        if (StringUtils.isBlank(serviceName)) {
            throw new InvalidParameterException("Failed to add location based service: serviceName must not be blank");
        }
        if (service == null) {
            throw new InvalidParameterException("Failed to add location based service: service must not be null");
        }
        Map<String, S> services = doGetServicesByLocationCode(locationCode);
        services.put(serviceName, service);
        serviceMap.put(serviceName, service);
    }

    private Map<String, S> doGetServicesByLocationCode(String locationCode) {
        synchronized (locationToServiceMap) {
            Map<String, S> allCountryServices = locationToServiceMap.computeIfAbsent(Constants.ALL_COUNTRY, k -> new HashMap<>());
            Map<String, S> services = locationToServiceMap.computeIfAbsent(locationCode, k -> new HashMap<>());
            
            services.putAll(allCountryServices);
            locationToServiceMap.put(locationCode, services);
            return services;
        }
    }

    public void removeServices(String locationCode, String serviceName) {
        log.debug("Request to remove location based service '{}' for location '{}'", serviceName, locationCode);
        if (StringUtils.isBlank(locationCode)) {
            throw new InvalidParameterException("Failed to remove location based service: locationCode must not be blank");
        }
        if (StringUtils.isBlank(serviceName)) {
            throw new InvalidParameterException("Failed to remove location based service: serviceName must not be blank");
        }
        Map<String, S> services = doGetServicesByLocationCode(locationCode);
        services.remove(serviceName);
    }

    /**
     * Get services for given location-code (could be country-code or district-code)
     * @param locationCode
     * @return
     */
    public List<S> getServicesByLocationCode(String locationCode) {
        log.debug("Request to get all location based for location '{}'", locationCode);
        if (StringUtils.isBlank(locationCode)) {
            return Collections.emptyList();
        }
        return doGetServices(locationCode, null);
    }

    /**
     * Get services for given location-code (could be country-code or district-code)
     */
    public List<S> getServicesByLocationCode(String locationCode, Collection<String> serviceNames) {
        log.debug("Request to get all location based for location '{}' with names: {}", locationCode, serviceNames);
        if (StringUtils.isBlank(locationCode)) {
            return Collections.emptyList();
        }
        return doGetServices(locationCode, serviceNames);
    }

    /**
     * Get service by location and service-names
     * @param locationCode
     * @param serviceNames service-names to get services, null get all, empty no service will be return
     * @return
     */
    private List<S> doGetServices(String locationCode, Collection<String> serviceNames) {
        // If service-names is empty, no service is returned
        if (serviceNames != null && serviceNames.isEmpty()) {
            return Collections.emptyList();
        }
        // Get services
        Map<String, S> services = doGetServicesByLocationCode(locationCode);
        // If service-names is null, return all services
        List<S> serviceList;
        if (serviceNames == null) {
            serviceList = new ArrayList<>(services.values());
        }
        else {
            // Filter out services that is not in service-names
            serviceList = new ArrayList<>();
            for (Map.Entry<String, S> serviceEntry : services.entrySet()) {
                String serviceName = serviceEntry.getKey();
                S service = serviceEntry.getValue();
                if (isSelected(serviceNames, serviceName)) {
                    serviceList.add(service);
                }
            }
        }
        return Collections.unmodifiableList(serviceList);
    }

    private boolean isSelected(Collection<String> serviceNames, String serviceName) {
        if (serviceNames == null || serviceNames.isEmpty()) {
            return true;
        }
        return serviceNames.contains(serviceName);
    }

    public S getServicesByName(String serviceName) {
        log.debug("Request to get service by name '{}'", serviceName);
        return serviceMap.get(serviceName);
    }
}
