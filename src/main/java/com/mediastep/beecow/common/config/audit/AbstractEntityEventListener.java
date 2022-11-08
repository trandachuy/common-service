/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 01/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *  
 *******************************************************************************/
package com.mediastep.beecow.common.config.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;

import com.mediastep.beecow.common.events.EntityEventType;

public abstract class AbstractEntityEventListener<T> {

    private final Logger log = LoggerFactory.getLogger(AbstractEntityEventListener.class);

    protected static BeanFactory beanFactory;

    protected abstract void process(T target, EntityEventType entityEventType);

    @SuppressWarnings("unchecked")
    protected void onChanged(Object target, EntityEventType entityEventType) {
        log.debug("Entity changed '{}': {}", entityEventType, target);
        try {
            process((T) target, entityEventType);
        }
        catch (Exception exc) {
            log.warn("Exception while procesin {} entity event: {}", entityEventType, target);
            log.debug("Exception while procesin '" + entityEventType + "' entity event: '" + target + "'", exc);
        }
    }

    static void setBeanFactory(BeanFactory beanFactory) {
        AbstractEntityEventListener.beanFactory = beanFactory;
    }
}
