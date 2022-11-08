/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 01/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *  
 *******************************************************************************/
package com.mediastep.beecow.common.events.validation;

import com.mediastep.beecow.common.errors.InvalidInputException;
import com.mediastep.beecow.common.events.Event;

/**
 * Class validate event.
 */
public abstract class AbstractEventValidator<T extends Event<?, ?>> {

    /**
     * Check input entity event valid, throw InvalidInputException
     * @param event
     * @throws InvalidInputException if valid
     */
    public void validate(T event) {
        if (event == null) {
            throw new InvalidInputException("Incoming event is null");
        }
        if (event.getEntity() == null) {
            throw new InvalidInputException("Incoming event does not contain an entity");
        }
        if (event.getType() == null) {
            throw new InvalidInputException("Incoming event type is not specified");
        }
    }
}
