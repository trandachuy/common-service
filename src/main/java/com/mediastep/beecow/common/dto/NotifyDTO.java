package com.mediastep.beecow.common.dto;


import java.io.Serializable;

/**
 * A DTO for the UserToken entity.
 */
public class NotifyDTO implements Serializable {

	private static final long serialVersionUID = -479451892898109541L;

	private Long userId;
	
	private String message;
	
	private byte[] messageByte;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public byte[] getMessageByte() {
		return messageByte;
	}

	public void setMessageByte(byte[] messageByte) {
		this.messageByte = messageByte;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotifyDTO other = (NotifyDTO) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
}
