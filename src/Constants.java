import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class Constants {
	public static final String DOMAIN = "ics.uci.edu";
	public static final String USER_AGENT = "IR W16 WebCrawler 89841518/81406625/20766885";
	public static final String HOST = "localhost";
	public static final int PORT = 27017;
	public static final String FILE_NAME = "resources/Stop_Words.txt";
	public static final String[] DIVIDERS = {" ",",","."};
	public static final int THREAD_POOL_SIZE =10;
	public static final MongoClient mongoClient = new MongoClient();
	public static final MongoDatabase db = mongoClient.getDatabase("crawler");
	public static final Boolean SHOULD_CRAWL = false;
	
}
