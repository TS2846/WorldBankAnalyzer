package frontEnd;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Collections;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JSonReader {
	JsonArray jo;
	public JSonReader(String filePath) {
		JsonParser jp = new JsonParser();
		try (FileReader reader = new FileReader(filePath)){
			Object obj = jp.parse(reader);
			jo = (JsonArray) obj;
		} catch (FileNotFoundException e) {
          e.printStackTrace();
		} catch (IOException e) {
          e.printStackTrace();
		}
	}
	
	public HashMap<String, String> parseLogin() 
    {
        HashMap<String, String> hs = new HashMap<String, String>();
	    for (JsonElement obj : jo) {
	    	JsonObject loginObject = (JsonObject) ((JsonObject) obj).get("login info");
	    	String username = loginObject.get("username").toString();
	    	String password = loginObject.get("password").toString();
	    	hs.put(username.substring(1, username.length()-1), password.substring(1,password.length()-1));
	    }
		return hs;
    }
	public ArrayList<Integer> parseYears(){
		ArrayList<Integer> out = new ArrayList<Integer>();
		for (JsonElement obj : jo) {
	    	JsonObject loginObject = (JsonObject) ((JsonObject) obj).get("Years");
	    	out.add(loginObject.get("Year").getAsInt());
	    }
		//System.out.println(out);
		Collections.reverse(out);
		return out;
	}
	public LinkedHashMap<String, String> parseCountry(){
		LinkedHashMap<String, String> hs = new LinkedHashMap<String, String>();
		for (JsonElement obj : jo) {
	    	JsonObject loginObject = (JsonObject) ((JsonObject) obj).get("Country Info");
	    	String username = loginObject.get("country").toString();
	    	String password = loginObject.get("code").toString();
	    	hs.put(username.substring(1, username.length()-1), password.substring(1,password.length()-1));	    }
		return hs;
	}
	public LinkedHashMap<String, ArrayList<Integer>> parseAnalysis(){
		LinkedHashMap<String, ArrayList<Integer>> hs = new LinkedHashMap<String, ArrayList<Integer>>();
		for (JsonElement obj : jo) {
	    	JsonObject loginObject = (JsonObject) ((JsonObject) obj).get("Analysis Info");
	    	String username = loginObject.get("analysis").toString();
	    	JsonArray possible = loginObject.getAsJsonArray("possible");
	    	ArrayList<Integer> al = new ArrayList<Integer>();
	    	for (int i = 0; i < possible.size(); i++) {
	    		JsonElement js = (possible.get(i));
	    		al.add(js.getAsInt());
	    	}
	    	hs.put(username.substring(1, username.length()-1), al);	    
	    	}
		return hs;
		
	}
	public static void main(String[] args) {
		JSonReader js = new JSonReader("resources/analyses.json");
		HashMap<String, ArrayList<Integer>> hs = js.parseAnalysis();
		System.out.println(hs.keySet());
	}
}
