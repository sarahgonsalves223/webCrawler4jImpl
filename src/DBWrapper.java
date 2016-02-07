import java.util.HashMap;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class DBWrapper {
	private static MongoCollection<Document> collection = Constants.db.getCollection("webcrawler_data");
	
	public HashMap<String,HashMap<String,String>> fetch(){
		/** source https://docs.mongodb.org/getting-started/java/query/
		 * #query-for-all-documents-in-a-collection		*
		*/
		HashMap<String,HashMap<String,String>> record = new HashMap<String,HashMap<String,String>>();
		FindIterable<Document> iterable = collection.find();
		iterable.batchSize(2);
		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		    	HashMap<String,String> recordValues = new HashMap<String, String>();
		        recordValues.put("URL",document.getString("URL"));
		        recordValues.put("HTML_RES",document.getString("HTML_RES"));
		        recordValues.put("TEXT_RES",document.getString("TEXT_RES"));
		        recordValues.put("DOMAIN",document.getString("DOMAIN"));
		        recordValues.put("SUBDOMAIN",document.getString("SUBDOMAIN"));
		        recordValues.put("NUM_WORDS",document.getInteger("NUM_WORDS").toString());
		    	record.put(document.getString("URL"), recordValues);
		    }
		});
		
		return record;
	}
}
