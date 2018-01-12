package upload;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.recombee.api_client.RecombeeClient;
import com.recombee.api_client.api_requests.AddItemProperty;
import com.recombee.api_client.api_requests.Request;
import com.recombee.api_client.api_requests.ResetDatabase;
import com.recombee.api_client.api_requests.SetItemValues;
import com.recombee.api_client.exceptions.ApiException;

import connection.Connection;

public class UploadActivityData {
	static RecombeeClient client = Connection.createRecombeeClient();
	private static Map<String, Object> itemValues = new HashMap<>();
	static ArrayList<Request> itemRequests = new ArrayList<>();

	
	public static void upload() throws ApiException {
		String line = "";
		//client.send(new ResetDatabase());
		client.send(new AddItemProperty("type", "string"));
		client.send(new AddItemProperty("name", "string"));
		client.send(new AddItemProperty("city", "string"));
		client.send(new AddItemProperty("topic", "string"));
		client.send(new AddItemProperty("from", "string"));
		client.send(new AddItemProperty("to", "string"));
		client.send(new AddItemProperty("address","string"));
		client.send(new AddItemProperty("rating","string"));
		
		try (BufferedReader br = new BufferedReader(new FileReader("activity.csv"))) {

		    while ((line = br.readLine()) != null) {
		    	//System.out.println(line);
		        String[] row = line.split(",");
		        String id = row[0];
		        String type = "activity";
		        String name = row[1];
		        String city = row[2];
		        String topic = row[3];
		        Date from =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(row[4]);
		        Date to =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(row[5]);
		        
		        itemValues.put("type",type);
		        itemValues.put("name",name);
		        itemValues.put("city", city);
		        itemValues.put("topic", topic);
		        itemValues.put("from", from);
		        itemValues.put("to", to);
		        Request r = new SetItemValues(id, itemValues).setCascadeCreate(true);
		        //itemRequests.add(r);
		        client.send(r);
		    }
		try (BufferedReader br = new BufferedReader(new FileReader("restaurants.csv"))) {

		    while ((line = br.readLine()) != null) {
		    	//System.out.println(line);
		        String[] row = line.split(";");
		        String id = row[0];
		        String type = row[1];
		        String name = row[2];
		        String city = row[4];
		        String topic = row[5];
		        String address = row[3];
		        String rating = row[6];
		        
		        itemValues.put("type",type);
		        itemValues.put("name",name);
		        itemValues.put("city", city);
		        itemValues.put("topic", topic);
		        itemValues.put("address", address);
		        itemValues.put("rating", rating);
		        Request r = new SetItemValues(id, itemValues).setCascadeCreate(true);
		        //itemRequests.add(r);
		        client.send(r);
		    }

		} catch (IOException e) {
		    e.printStackTrace();
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		//client.send(new Batch(itemRequests));
		return;
	}
	public static void main(String[] args) throws ApiException {
		UploadActivityData.upload();
	}
	
}
