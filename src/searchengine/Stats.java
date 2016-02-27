package searchengine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Stats {
	public static Set<String> uniquePages = new HashSet<String>();
	public static Map<String, HashSet<String>> subDomains = new HashMap<String, HashSet<String>>();
	public static Map<String, Integer> subDomainsPageCount = new HashMap<String, Integer>();
	public static Set<String> stopWords = new HashSet<String>();
	public static Map<String, Integer> tokenfrequencyList = new HashMap<String, Integer>();
	public static Map<String, HashSet<String>> urlWordList = new HashMap<String, HashSet<String>>();
	public static Map<String, Integer> threeGramSet = new HashMap<String, Integer>();
	//public static Set<String> tokens = new HashSet<String>();
	
	// everything after this is for document retrieval
	
	public static String query = "";
	public static ArrayList<String> query_words = new ArrayList<String>();
	public static ArrayList<String> urls = new ArrayList<String>();
	public static ArrayList<String> titles = new ArrayList<String>();
	public static String getUrl(int index){
		return urls.get(index);
	}
	public static String getTitles(int index){
		return titles.get(index);
	}
}
