/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 26/1/2017
 * Author: Huyen Lam <huyen.lam@mediastep.com>
 *******************************************************************************/
package com.mediastep.beecow.common.domain.enumeration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The DataType enumeration.
 */
public enum DataType {

    /**
     * String.
     */
    STRING(String.class, true),

    /**
     * Number.
     */
    NUMBER(Number.class, true),

    /**
     * Collection&lt;String&gt;.
     */
    LIST(Collection.class, false),

    /**
     * Collection&lt;String&gt;.<br/>
     * Refer to {@link #LIST}.
     */
    STRING_LIST(LIST, Collection.class, false),

    /**
     * Collection&lt;Number&gt;.
     */
    NUMBER_LIST(Collection.class, false);

    private static List<DataType> listTypes = new ArrayList<>();

    private static List<DataType> simpleTypes = new ArrayList<>();

    private Class<?> mappedClazz;

    private DataType baseType;

    private boolean simpleType;

    /**
     * Get list of all simple types.
     * @return
     */
    public static List<DataType> allSimpleTypes() {
        return allTypes(simpleTypes, true);
    }

    /**
     * Get list of all simple types.
     * @return
     */
    public static List<DataType> allListTypes() {
        return allTypes(listTypes, false);
    }

    /**
     * Collect expected types.
     * @param outputTypes
     * @param listSimpleTypes
     * @return
     */
    private static List<DataType> allTypes(List<DataType> outputTypes, boolean listSimpleTypes) {
        synchronized (outputTypes) {
            if (outputTypes.isEmpty()) {
                for (DataType type : values()) {
                    if (type.isSimpleType() == listSimpleTypes) {
                        outputTypes.add(type);
                    }
                }
            }
            return outputTypes;
        }
    }

    DataType(Class<?> mappedClazz, boolean simpleType) {
        this(null, mappedClazz, simpleType);
        assert (mappedClazz != null); // mappedClazz is required
    }

    DataType(DataType baseType, Class<?> mappedClazz, boolean simpleType) {
        this.mappedClazz = mappedClazz;
        this.baseType = baseType;
        this.simpleType = simpleType;
        assert (mappedClazz != null); // mappedClazz is required
    }

    public Class<?> getMappedClazz() {
        return mappedClazz;
    }

    public void setMappedClazz(Class<?> mappedClazz) {
        this.mappedClazz = mappedClazz;
    }

    public DataType getBaseType() {
        return baseType;
    }

    public void setBaseType(DataType baseType) {
        this.baseType = baseType;
    }

    public boolean isSimpleType() {
        return simpleType;
    }

    public void setSimpleType(boolean simpleType) {
        this.simpleType = simpleType;
    }

}
