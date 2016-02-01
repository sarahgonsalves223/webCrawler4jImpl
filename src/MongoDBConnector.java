import java.util.Map;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnector {
	MongoClient mongoClient;
	MongoDatabase db;
	
	MongoDBConnector openConnection(){
		mongoClient = new MongoClient(Constants.HOST,Constants.PORT);
		db = mongoClient.getDatabase("test");
		return this;
	}
	
	void insertDocument(String collectionName, Map<String, Object> responseBody){
		try{
			db.getCollection(collectionName).insertOne(new Document(responseBody));
			System.out.println("here");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	void closeConnection(){
		mongoClient.close();
	}
}
