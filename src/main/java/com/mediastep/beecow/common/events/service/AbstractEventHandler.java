/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 01/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *  
 *******************************************************************************/
package com.mediastep.beecow.common.events.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mediastep.beecow.common.errors.InvalidInputException;
import com.mediastep.beecow.common.events.EntityEvent;
import com.mediastep.beecow.common.events.EntityEventType;
import com.mediastep.beecow.common.events.validation.AbstractEventValidator;

/**
 * Abstract event handler which will listen to event.
 */
public abstract class AbstractEventHandler<V, T extends EntityEvent<V>> {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected final AbstractEventValidator<T> eventValidator;

    protected AbstractEventHandler(AbstractEventValidator<T> eventValidator) {
        this.eventValidator = eventValidator;
    }

    protected abstract void doProcess(V entityDTO, EntityEventType entityEventType);

    protected boolean isInterested(V entityDTO) {
        return true;
    }

    public void processEvent(T event) {
        log.debug("Received event: {}", event);
        try {
            // Validate event
            if (eventValidator != null) {
                eventValidator.validate(event);
            }
            V entityDTO = event.getEntity();
            if (isInterested(entityDTO)) {
                switch (event.getType()) {
                    case CREATED:
                        doProcess(entityDTO, EntityEventType.CREATED);
                        break;
                    case UPDATED:
                        doProcess(entityDTO, EntityEventType.UPDATED);
                        break;
                    case DELETED:
                        doProcess(entityDTO, EntityEventType.DELETED);
                        break;
                    default:
                        log.debug("Event type is not supported: {}", event);
                        break;
                }
            }
            else {
                log.debug("The event is ignored: {}", event);
            }
        }
        catch (InvalidInputException exc) {
            log.warn("Failed to handle event {} : {}", event, exc.getMessage());
        }
        catch (Exception exc) {
            log.warn("Failed to handle event: " + event, exc);
        }
    }
}
