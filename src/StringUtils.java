import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class StringUtils {

	public void tokenizePage(String page){
		loadStopWords();
		page.replaceAll("[\\p{Punct}&&[^.,\\s]]", "");
		page.replaceAll("[.,\\s]", " ");
		for(String current_word: page.split(" ")){
			current_word = current_word.trim();
			current_word = current_word.toLowerCase();
			if(!Stats.stopWords.contains(current_word) && !current_word.matches("\\s+")){
				addToFrequencyList(current_word);				
			}
		}		
	}
	public String[] splitToWords(String page){
		//stop words considered as words
		return page.split("[.,;\\s\\n]");		
		
	}
	
	public void loadStopWords(){		
		try{
			Scanner in = new Scanner(new File(Constants.FILE_NAME));
			while(in.hasNext()){
				String current_word = in.next();
				Stats.stopWords.add(current_word);
			}

			in.close();

		} catch (FileNotFoundException e){
			e.printStackTrace();

		}
	}

	public void addToFrequencyList(String current_word){
		if(!Stats.frequencyList.containsKey(current_word))
			Stats.frequencyList.put(current_word, 1);
		else{
			Stats.frequencyList.put(current_word, Stats.frequencyList.get(current_word)+1);
		}

	}

	public void addToUrlWordList(String current_word, String url){
		if(Stats.urlWordList.containsKey(current_word)){
			Stats.urlWordList.get(current_word).add(url);

		} else{
			HashSet<String> urlList = new HashSet<String>();
			urlList.add(url);
			Stats.urlWordList.put(current_word, urlList);
		}	
	}

	public void mapWordsToURL(String page, String url){
		tokenizePage(page);
		for(String current_word: Stats.frequencyList.keySet()){
			addToUrlWordList(current_word, url);
		}		
	}
	
	public void mapPageTo3Grams(String page){
		// convert String into InputStream
		InputStream is = new ByteArrayInputStream(page.getBytes());
		
		// read it with BufferedReader
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		try{
			while ((line = br.readLine()) != null) {
				line.replaceAll("[\\p{Punct}&&[^.,\\s]]", "");
				line.replaceAll("[.,\\s]"," "); // this is so that a,c,d => a b d and acd.def => abc def
				String [] words = line.toLowerCase().split(" ");
				for(int i=0;i<line.length();i++){
					words[i]= Stats.stopWords.contains(words[i])|| words[i].matches("\\s+")?"":words[i];
				}
				String three_gram_string="";
				for(int i=0;i<words.length-2;i+=3){
					three_gram_string+=words[i].trim()+" "+words[i+1].trim()+" "+words[i+2].trim();
					if(Stats.threeGramSet.get(three_gram_string)==null){
						Stats.threeGramSet.put(three_gram_string, 1);
					} else {
						Stats.threeGramSet.put(three_gram_string, Stats.threeGramSet.get(three_gram_string)+1);
					}
				}
			}
			br.close();
		} catch (Exception e){
			e.printStackTrace();
		}		
	}
	
	public ArrayList<Pair> sortMap(Map<String, Integer> freq){
		ArrayList<String> list = new ArrayList<String>(freq.keySet());
		Comparator<String> cmp = new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				Integer one = freq.get(s1);
				Integer two = freq.get(s2);
				return one.compareTo(two);
			}
		};

		Collections.sort(list, Collections.reverseOrder(cmp));
		ArrayList<Pair> listPair = new ArrayList<Pair>();
		for(String w: list){
			Pair p = new Pair();
			p.word = new String(w);
			p.frequency = freq.get(w);
			listPair.add(p);
		}
		return listPair;
	}

	public void print(ArrayList<Pair> pairs){
		int i=0;
		for(Pair p: pairs){
			i++;
			System.out.println(p.word.toString() + " : " + p.frequency);
			if(i>=500){
				System.out.println("DONE DON");
				break;
			}

		}

	}
	
	class Pair{
		public String word;
		public int frequency;
	}
}
