package org.apps.common;
/**
 * 
 * @author FAIZ
 *
 */
public class ActionMessage {
	private String messageType;
	private String message;
	
	public ActionMessage(String messageType,String message) {
		this.messageType=messageType;
		this.message=message;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Deprecated
	public ActionMessage() {
		// TODO Auto-generated constructor stub
	}

}
