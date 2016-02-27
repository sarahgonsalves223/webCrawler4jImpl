import java.util.HashMap;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class queryProcessing {

	public queryProcessing(){
		
		/* Place mongodb jar in the WEB-INF/lib folder */
		
		StringUtils stringUtils = new StringUtils();
		System.out.println("query " +Stats.query);
		Stats.query_words = stringUtils.tokenizePage(Stats.query);
		System.out.println("words are: " +Stats.query_words);
		/* should be inverted_index for the complete index. This is only for 200 documents. */
		MongoCollection<Document> collection = Constants.db.getCollection("new_inverted_index"); 
		HashMap<Integer, Integer> tf_idf = new HashMap<Integer, Integer>();
		for(int i=0; i<Stats.query_words.size(); i++){ //for every word in the query that is not a stop word
			String word = Stats.query_words.get(i);
			System.out.println("word inside loop is: " + word);
			FindIterable<Document> iterable = collection.find(Filters.eq("term", word));
			iterable.limit(1);
			iterable.forEach(new Block<Document>(){
				@Override
				public void apply(final Document document){
					System.out.println(document);
				}
			});
		}
		return;
	}
}
