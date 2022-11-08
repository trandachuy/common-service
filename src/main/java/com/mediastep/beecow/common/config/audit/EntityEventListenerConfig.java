/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 12/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *******************************************************************************/
package com.mediastep.beecow.common.config.audit;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntityEventListenerConfig implements BeanFactoryAware {

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        AbstractEntityEventListener.setBeanFactory(beanFactory);
    }
}
