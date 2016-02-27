package searchengine;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class queryProcessing extends HttpServlet{

	public queryProcessing() throws IOException, ServletException{
		
		/* Place mongodb jar in the WEB-INF/lib folder */
		
		StringUtils stringUtils = new StringUtils();
		System.out.println("query " +Stats.query);
		Stats.query_words = stringUtils.tokenizePage(Stats.query);
		System.out.println("words are: " +Stats.query_words);
		/* should be inverted_index for the complete index. This is only for 200 documents. */
		MongoCollection<Document> collection = Constants.db.getCollection("new_inverted_index"); 
		HashMap<Integer, Double> tf_idf = new HashMap<Integer, Double>();
		for(int i=0; i<Stats.query_words.size(); i++){ //for every word in the query that is not a stop word
			String word = Stats.query_words.get(i);
			System.out.println("word inside loop is: " + word);
			FindIterable<Document> iterable = collection.find(Filters.eq("term", word));
			iterable.limit(1);
			iterable.forEach(new Block<Document>(){
				@Override
				public void apply(final Document document){
					Double df = (Double) document.get("df");
					ArrayList<Document> doc_array = new ArrayList<Document>();
					doc_array = (ArrayList<Document>) document.get("docs");
					System.out.println(doc_array.get(0));
					for(int j=0; j<doc_array.size(); j++){
						Document doc = doc_array.get(j);
						Integer doc_id = (Integer) doc.get("doc_id");
						Integer tf = (Integer) doc.get("tf");
						Double value = (1 + logOfBase(10, tf.doubleValue()))*(logOfBase(10, 200.0) - logOfBase(10, df));
						if(!tf_idf.containsKey(doc_id)){
							tf_idf.put(doc_id, value);
						}
						else{
							Double exist_value = tf_idf.get(doc_id);
							value+=exist_value;
							tf_idf.put(doc_id, value);
						}
					}
					
				}
			});
		}
		System.out.println("######tf_idf######");
		System.out.println();
		Pair p = new Pair();
		p.print(sortMap(tf_idf), 5);

		return;
	}

	public static double logOfBase(int base, Double num){
		return (double) Math.log(num) / Math.log(base);
	}
	
	public ArrayList<Pair> sortMap(HashMap<Integer, Double> results){
		ArrayList<Integer> list = new ArrayList<Integer>(results.keySet());
		Comparator<Integer> cmp = new Comparator<Integer>() {
			@Override
			public int compare(Integer i1, Integer i2) {
				Double one = results.get(i1);
				Double two = results.get(i2);
				return one.compareTo(two);
			}
		};
	
		Collections.sort(list, Collections.reverseOrder(cmp));
		ArrayList<Pair> listPair = new ArrayList<Pair>();
		for(Integer w: list){
			Pair p = new Pair();
			p.doc_id = new Integer(w);
			p.tf_idf_score = results.get(w);
			listPair.add(p);
		}
		return listPair;
	}

	class Pair{
		public Integer doc_id;
		public Double tf_idf_score;
	
		public void print(ArrayList<Pair> ar, int count){
			MongoCollection<Document> collection = Constants.db.getCollection("webcrawler_data");
			int min = Math.min(ar.size(), count);
			for(int i=0; i<min; i++){
				Pair p = ar.get(i);
				System.out.println(p.doc_id + " : " + p.tf_idf_score);
				
				FindIterable<Document> iterable = collection.find(Filters.eq("_uid", p.doc_id));
				iterable.limit(1);
				iterable.forEach(new Block<Document>(){
					@Override
					public void apply(final Document document){
						String url = document.get("URL").toString();
						Stats.urls.add(url);
						try {
							String title = TitleExtractor.getPageTitle(url);
							System.out.println("Title is: " + title);
							Stats.titles.add(title);
						} catch (IOException e) {
							e.printStackTrace();
						}
						System.out.println("URL IS: " + url);
					}
				});
			}
		}
	}
}
