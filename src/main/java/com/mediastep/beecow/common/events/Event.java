/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 01/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *  
 *******************************************************************************/
package com.mediastep.beecow.common.events;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class Event<T, V> implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Valid
    private V entity;

    private T type;

    private final ZonedDateTime timestamp = ZonedDateTime.now();

    /**
     * Default constructor.
     * @param entity
     */
    public Event() {
    }

    /**
     * @return the entity
     */
    public V getEntity() {
        return entity;
    }

    /**
     * @param type the type to set
     */
    public Event<T, V> entity(V entity) {
        this.entity = entity;
        return this;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(V entity) {
        this.entity = entity;
    }

    /**
     * @param type the type to set
     */
    public Event<T, V> type(T type) {
        this.type = type;
        return this;
    }

    /**
     * @return the type
     */
    public T getType() {
        return type;
    }

    /**
     * @return the timestamp
     */
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * @param type the type to set
     */
    public void setType(T type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
            "type='" + type + '\'' +
            ", entity='" + entity + '\'' +
            ", timestamp='" + timestamp + '\'' +
            "}";
    }
}
