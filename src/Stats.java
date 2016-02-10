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
}
