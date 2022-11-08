/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 09 Sep, 2017
 * Author: Anh Le <email:anh.le@mediastep.com>
 *  
 *******************************************************************************/
package com.mediastep.beecow.common.dto;

import java.io.Serializable;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediastep.beecow.common.domain.enumeration.BeecowCommandTypeEnum;

/**
 * A DTO for the BeecowCommandNotifyDTO entity.
 */
public class BeecowCommandNotifyDTO extends NotifyDTO implements Serializable {
	
	private static final long serialVersionUID = -4070441222413720362L;
	private final static ObjectMapper objectMapper = new ObjectMapper();
	public  final static String FEATURE_BEECOW_COMMAND = "BEECOW_COMMAND";
	public  final static String PAYLOAD_DATA_FIELD_COMMAND_TYPE = "commandType";
	public  final static String PAYLOAD_DATA_FIELD_COMMAND_DATA = "commandData";
	
	private BeecowCommandTypeEnum commandType;
	
	private String commandData;

	private String deviceToken;
	
	public BeecowCommandTypeEnum getCommandType() {
		return commandType;
	}

	public void setCommandType(BeecowCommandTypeEnum commandType) {
		this.commandType = commandType;
	}
	
	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	
	public String getCommandData() {
		return commandData;
	}
	
	
	/**
	 * Convert object data into JSON string and give info for commandData
	 * to provide more info to execute the command.
	 * 
	 * @param objectData The data object need be set.
	 */
	public void setCommandData(Object objectData) {
		try {
			setCommandData(objectMapper.writeValueAsString(objectData));
		} catch (Exception e) {
			e.printStackTrace();
			throw (RuntimeException) e;
		}
	}
	
	/**
	 * Set a JSON string give info for commandData
	 * to provide more info to execute the command.
	 * 
	 * @param jsonString Must be a JSON String has been converted form an Object.
	 */
	public void setCommandData(String jsonString) {
		this.commandData = jsonString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((commandData == null) ? 0 : commandData.hashCode());
		result = prime * result + ((commandType == null) ? 0 : commandType.hashCode());
		result = prime * result + ((deviceToken == null) ? 0 : deviceToken.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BeecowCommandNotifyDTO other = (BeecowCommandNotifyDTO) obj;
		if (commandData == null) {
			if (other.commandData != null)
				return false;
		} else if (!commandData.equals(other.commandData))
			return false;
		if (commandType != other.commandType)
			return false;
		if (deviceToken == null) {
			if (other.deviceToken != null)
				return false;
		} else if (!deviceToken.equals(other.deviceToken))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BeecowCommandNotifyDTO {commandType=" + commandType + ", commandData=" + commandData + ", deviceToken="
				+ deviceToken + ", getUserId()=" + getUserId() + ", getMessage()=" + getMessage()
				+ ", getMessageByte()=" + Arrays.toString(getMessageByte()) + "}";
	}

}
