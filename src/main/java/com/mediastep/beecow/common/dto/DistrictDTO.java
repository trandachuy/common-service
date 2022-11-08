package com.mediastep.beecow.common.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class DistrictDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String code;

    private String inCountry;

    private String outCountry;
    
    private String zone;
    
    private Long cityId;
}
