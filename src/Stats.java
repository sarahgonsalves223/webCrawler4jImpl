import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.uci.ics.crawler4j.url.WebURL;

public class Stats {
	public static Set<WebURL> uniquePages = new HashSet<WebURL>();
	public static Map<String, HashSet<String>> subDomains = new HashMap<String, HashSet<String>>();
	public static Map<String, Integer> subDomainsPageCount = new HashMap<String, Integer>();
	public static Set<String> stopWords = new HashSet<String>();
	public static Map<String, Integer> frequencyList = new HashMap<String, Integer>();
	public static Map<String, HashSet<String>> urlWordList = new HashMap<String, HashSet<String>>();
	public static Map<String, Integer> threeGramSet = new HashMap<String, Integer>();
}
