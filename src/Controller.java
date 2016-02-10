import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller {
    public static void main(String[] args) throws Exception {
    	
        if(Constants.SHOULD_CRAWL){
        	String crawlStorageFolder = "data/crawl/root";
        	int numberOfCrawlers = 1;

        	CrawlConfig config = new CrawlConfig();
        	config.setCrawlStorageFolder(crawlStorageFolder);
        	config.setUserAgentString(Constants.USER_AGENT);
        	config.setPolitenessDelay(1000);
        	config.setResumableCrawling(true);//change to true
        	config.setMaxDownloadSize(1000000);

        	/*
        	 * Instantiate the controller for this crawl.
        	 */
        	PageFetcher pageFetcher = new PageFetcher(config);
        	RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        	RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        	CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        	/*
        	 * For each crawl, you need to add some seed urls. These are the first
        	 * URLs that are fetched and then the crawler starts following links
        	 * which are found in these pages
        	 */
        	controller.addSeed("http://www.ics.uci.edu/~lopes/");
        	controller.addSeed("http://www.ics.uci.edu/");

        	/*
        	 * Start the crawl. This is a blocking operation, meaning that your code
        	 * will reach the line after this only when crawling is finished.
        	 */
        	controller.start(MyCrawler.class, numberOfCrawlers);
        	controller.shutdown();	// comment if needed
        	controller.waitUntilFinish();
        	Constants.mongoClient.close(); // comment if needed
        
        } else {
        	// process crawled data
        	StringUtils stringUtils = new StringUtils();
        	MyCrawler crawled = new MyCrawler();
        	DBWrapper db = new DBWrapper();
        	int maxWordCount=0;
        	String maxWordCountURL="";
        	
        	for(int i=0;i<Constants.DB_ROW_COUNT;i++){
        		HashMap<String,HashMap<String,String>> records =db.fetch(i); //don't make multiple db calls fetch records in batches of 500 and process
        		for(String url:records.keySet()){
        			crawled.addUniquePages(url);
        			crawled.findDomainsAndPages((records.get(url).get("SUBDOMAIN")), url);
        			stringUtils.tokenizePage(records.get(url).get("TEXT_RES"));
        			stringUtils.mapPageTo3Grams(records.get(url).get("TEXT_RES"));
        			
        			int wordcount = Integer.parseInt(records.get(url).get("NUM_WORDS"));
        			
        			if(wordcount>=maxWordCount){
        				maxWordCount=wordcount;
        				maxWordCountURL = url;
        			}
        		}
        	}
        	
        	System.out.println("*****Unique Pages******** "+Stats.uniquePages.size());
        	
        	File threeGramFile = new File("resources/Three_Gram.txt");
        	File subDomainsFile = new File("resources/Subdomains.txt");
        	File domainWordsFile = new File("resources/CommonDomainWords.txt");
        	File longestPage = new File("resources/longestPage.txt");
        	
        	try{
    			if (!longestPage.exists()){
    				longestPage.createNewFile();
    			}	
    			
    			FileWriter fw = new FileWriter(longestPage);
    			BufferedWriter bw = new BufferedWriter(fw);
    			String content = "Longest Page url "+maxWordCountURL +" word count "+maxWordCount;
    			
    			bw.write(content);
    			bw.flush();
    			bw.close();				
    		} catch (Exception e){
    			e.printStackTrace();
    		}
        	
        	ArrayList<StringUtils.Pair> threeGrams = stringUtils.sortMap(Stats.threeGramSet);
        	ArrayList<StringUtils.Pair> commonWords = stringUtils.sortMap(Stats.tokenfrequencyList);
        	ArrayList<StringUtils.Pair> domainPgCount = stringUtils.sortSet(Stats.subDomainsPageCount);
        	
        	stringUtils.print(threeGrams,threeGramFile,20);
        	stringUtils.print(commonWords, domainWordsFile, 500);
        	stringUtils.print(domainPgCount, subDomainsFile, -1); // -1 => no limit
        }  
    }
}