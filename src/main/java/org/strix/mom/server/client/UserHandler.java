package org.strix.mom.server.client;

import java.util.List;

public class UserHandler {
	private List<User> users  = null;
	
	public boolean authenticate(String userName,String password){
		//TODO THARINDUJ
		if(userName.equals("admin")||userName.equals("root")){
			return true;
		}
		return false;
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
