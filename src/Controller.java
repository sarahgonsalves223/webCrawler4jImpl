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
        	DBWrapper db = new DBWrapper();
        	HashMap<String,HashMap<String,String>> records =db.fetch();
        	System.out.println(records.size());
        	//to get records
        	/*for(String url:records.keySet()){
        		System.out.println(records.get(url).toString());
        	}*/
        }  
    }
}