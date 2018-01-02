package connection;

import com.recombee.api_client.RecombeeClient;

public class Connection {
	private final static String DB_ID = "sde-final-assignment";
	private final static String SECRET_TOKEN = "LeDMWIM05dYcFydwyKTxJnJbsfwOE7kieL6Hk0oB9VGqzJ3rYCBjhAcV3AMQhpQo";

	public static RecombeeClient createRecombeeClient() {
		RecombeeClient client = new RecombeeClient(DB_ID, SECRET_TOKEN);
		return client;
	}
	
}

