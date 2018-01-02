package collect;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FetchActivityData {
	private static final String SECRET_KEY = "c8hsbwj8xyx4xtg5zu9au6sj";
	
	public static WebTarget config() {
    	ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);
        WebTarget service = client.target(getBaseURI());
        System.out.println("Calling " + getBaseURI() ); 
        return service;
    }
	
	 public static String format(String jsonString) throws IOException {
		 ObjectMapper mapper = new ObjectMapper();
		 Object json = mapper.readValue(jsonString, Object.class);
		 String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);

	      return prettyJson;
	  }
	 private static URI getBaseURI() {
		 return UriBuilder.fromUri(
				 "http://api.amp.active.com/v2").build();
	 }
	 public static void main(String[] args) throws Exception {
	     WebTarget service = config();
	     //current_page
		 Response resp = service.path("search").queryParam("category", "event").queryParam("country", "Italy").queryParam("current_page", 1).queryParam("per_page", 2000).queryParam("start_date", "2018-01-01..").queryParam("api_key", SECRET_KEY).request().accept(MediaType.APPLICATION_JSON).header("Content-type","application/json").get();
		 Response resp2 = service.path("search").queryParam("category", "event").queryParam("country", "Italy").queryParam("current_page", 2).queryParam("per_page", 2000).queryParam("start_date", "2018-01-01..").queryParam("api_key", SECRET_KEY).request().accept(MediaType.APPLICATION_JSON).header("Content-type","application/json").get();

		 String response = resp.readEntity(String.class);
	     String response2 = resp2.readEntity(String.class);

		 JSONObject jsonObj = new JSONObject(response);
		 JSONObject jsonObj2 = new JSONObject(response2);
		 
		 int numberOfResults = (int) jsonObj.get("total_results");
	     
		 JSONArray results = jsonObj.getJSONArray("results");
		 JSONArray results2 = jsonObj2.getJSONArray("results");

		 for (int i = 0; i < results2.length(); i++) {
			 results.put(results2.getJSONObject(i));
		 }
	     System.out.println(results.length());
	     //"assetName", "place", "activityStartDate", "activityEndDate", "assetTopics", "assetTags"
	     FileWriter writer = new FileWriter("activity2.csv");
	     
	     for (int i=0;i<numberOfResults;i++) {
	    	 writer.append(String.format("item-%s", i+1));
	    	 writer.append(',');
	    	 writer.append(results.getJSONObject(i).getString("assetName"));
	    	 //System.out.println(results.getJSONObject(i).getString("assetName"));
             writer.append(',');
             JSONObject place = (JSONObject) results.getJSONObject(i).get("place");
             writer.append(place.getString("cityName"));
             writer.append(',');
             JSONObject topics = (JSONObject) ((JSONObject) results.getJSONObject(i).getJSONArray("assetTopics").get(0)).get("topic");
             writer.append(topics.getString("topicName"));
             writer.append(',');
             writer.append(results.getJSONObject(i).getString("activityStartDate"));
             writer.append(',');
             writer.append(results.getJSONObject(i).getString("activityEndDate"));
             writer.append('\n');
	     }
	     
	     writer.flush();
         writer.close();
	     
	 }
}
