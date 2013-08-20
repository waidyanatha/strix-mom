package org.strix.mom.server.client;

import org.strix.mom.server.communication.impl.UdpServer.Type;

public class User {

	public static enum UserType {
		CLIENT, ADMIN
	};

	private UserType type = UserType.CLIENT;
	private String userName;
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

}
