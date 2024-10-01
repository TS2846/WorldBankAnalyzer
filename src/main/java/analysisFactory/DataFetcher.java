package analysisFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;


public class DataFetcher {
	public ArrayList<Double> getData(String country, String analysisType, int fromYear, int toYear) {
		String years = String.valueOf(fromYear) + ":" +String.valueOf(toYear); //generate URL
		String urlString = String.format("http://api.worldbank.org/v2/country/%s/indicator/%s?date=%s&format=json", country, analysisType, years);
		ArrayList<Double> data = new ArrayList<Double>();
		//System.out.println(urlString);
		double search = 0; //this is the variable that will hold values taken from the json file
		double cumulative = 0; //this is the variable that holds the cumulative value
		try {
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.connect();
		int responseCode = conn.getResponseCode();
		if (responseCode == 200) {
			String inline = "";
			Scanner sc = new Scanner(url.openStream());
			while (sc.hasNext()) {
				inline += sc.nextLine();
			}
			JsonArray jsonArray = new JsonParser().parse(inline).getAsJsonArray();
			int sizeOfResults = jsonArray.get(1).getAsJsonArray().size();
			//int year = 0;
			for (int i = 0; i < sizeOfResults; i++) {
				//year = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("date").getAsInt();
				// CHECK IF THERE IS A VALUE FOR THE POPULATION FOR A GIVEN YEAR
				if(jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull()) {
					search = 0;
					data.add(search);
				}
				else {
					search = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsDouble();
					cumulative = cumulative + search;
					data.add(search); //appends search to arraylist
				}
				//System.out.println("Population for : " + year + " is " + search);
			}
//			System.out.println(data.toString());
				//	System.out.println("The average population over the selected years is " + cummulativePopulation / sizeOfResults);
			}
		}
		catch(IOException e){}
		Collections.reverse(data);
		return data; //returns arraylist for iteration
	}
//	public static void main(String[] args) {
//		DataFetcher df = new DataFetcher();
//		df.getData("USA", "EG.USE.PCAP.KG.OE", 2001, 2018);
//	}
}

