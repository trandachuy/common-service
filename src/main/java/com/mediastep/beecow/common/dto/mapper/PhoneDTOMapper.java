/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 01/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *  
 *******************************************************************************/
package com.mediastep.beecow.common.dto.mapper;

import org.springframework.stereotype.Component;

import com.mediastep.beecow.common.dto.PhoneDTO;
import com.mediastep.beecow.common.util.PhoneDtoUtil;

/**
 * Mapper for string and PhoneDTO.
 */
@Component
public class PhoneDTOMapper {

    public PhoneDTO stringToPhoneDTO(String phone) {
        return PhoneDtoUtil.stringToPhoneDTO(phone);
    }

    public String phoneDTOToString(PhoneDTO phoneDTO) {
        return PhoneDtoUtil.phoneDTOToString(phoneDTO);
    }
}
