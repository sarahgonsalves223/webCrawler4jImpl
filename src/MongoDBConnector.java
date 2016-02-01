import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnector {
	MongoClient mongoClient = new MongoClient();
	MongoDatabase db = mongoClient.getDatabase("test");
}
