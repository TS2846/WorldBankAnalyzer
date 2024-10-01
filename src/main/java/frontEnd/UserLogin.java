package frontEnd;

import java.util.HashMap;


public class UserLogin {
	JSonReader jr = new JSonReader("resources/login.json");
	private static HashMap<String,String> info;
	
	public UserLogin() {
		info = jr.parseLogin();
	}
	public Boolean check(String username, String password) {
		if (info.containsKey(username)) {
			if (info.get(username).equals(password)) {
				return true;
			}
		}
		else { 
			return false;
		}
		return null;
	}
}
