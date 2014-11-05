package soen472;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
	
	static String hamPath = System.getProperty("user.dir")+"\\emails\\20030228_easy_ham_2\\easy_ham_2";
	static String spamPath = System.getProperty("user.dir")+"\\emails\\20030228_spam_2\\spam_2";
	
	static Map<String, Integer> HamMap = new TreeMap<String, Integer>();
	static Map<String, Integer> SpamMap = new TreeMap<String, Integer>();
	
	static Map<String, int[]> CombinedMap = new TreeMap<String, int[]>();
	
	static int hamTotal = 0;
	static int spamTotal = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Begining program");

		File hamDir = new File(hamPath);
		File spamDir = new File(spamPath);

	    File[] hamFiles = hamDir.listFiles();
	    File[] spamFiles = spamDir.listFiles();
	    
	    int count = 0;
	    
	    System.out.println("Processing Ham Files");
	    for (File file : hamFiles) {
	        if (file.isDirectory()) {
	            System.out.print("directory:");
	        } else {
	            //System.out.print("file: \t");
	        }
	        processHamFile(file);
	        //System.out.println(file.getAbsolutePath());
	        count++;
	        if (count >= 1000){
	        	break;
	        }
	        //tempf = file;
	    }	 
	    count = 0;
	    System.out.println("Processing Spam Files");
	    for (File file : spamFiles) {
	        processSpamFile(file);
	        count++;
	        if (count >= 1000){
	        	break;
	        }
	        //tempf = file;
	    }
	    
	    System.out.println("Combining Dictionaries");
	    for(Entry<String, Integer> entry : HamMap.entrySet()){
	    	int[] tmparr = new int[2];
	    	tmparr[0] = entry.getValue();
	    	if(SpamMap.containsKey(entry.getKey()))
	    		tmparr[1] = SpamMap.get(entry.getKey());
	    	else
	    		tmparr[1] = 0;
	    	CombinedMap.put(entry.getKey(), tmparr);
	    	hamTotal += tmparr[0];
	    	spamTotal += tmparr[1];
	    	
	    }
	    
	    for(Entry<String, Integer> entry : SpamMap.entrySet()){
	    	if(!CombinedMap.containsKey(entry.getKey()))
	    	{
	    		int[] tmparr = new int[2];
	    		tmparr[0] = 0;
	    		tmparr[1] = entry.getValue();
	    		CombinedMap.put(entry.getKey(), tmparr);
	    		spamTotal += tmparr[1];
	    	}
	    	
	    }
	    
	    System.out.println("Printing Model");
	    int tmp1 = 0;
	    count = 0;
	    try {
			PrintWriter writer = new PrintWriter("model.txt", "UTF-8");
			for(Entry<String, int[]> entry : CombinedMap.entrySet()){
		    	writer.println((count++)+"   " +entry.getKey() + "  " + (tmp1 = entry.getValue()[0]) + "  " 
		    			//+ (tmp1==0 ? 0.5/hamTotal : ((float)tmp1/hamTotal))//old bad smoothing
		    			+ tmp1+0.5/(hamTotal+(CombinedMap.size()*0.5))
		    			+"  "+(tmp1 = entry.getValue()[1]) + "  " 
		    			//+ (tmp1==0 ? 0.5/spamTotal : (float)tmp1/spamTotal));//old bad smoothing
		    			+ tmp1+0.5/(spamTotal+(CombinedMap.size()*0.5)));
		    }
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    System.out.println("File processing complete, \nprinted to \"model.txt\"");
	    
	    
	    
	}
	private static void processHamFile(File file){
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				String words[] = line.split("\\W*(\\W|_)\\W*");//splits on non alpha-numeric characters and ___
				for(int i =0; i<words.length; i++){
					if(wordCheck(words[i]))
						continue;
					addEntry(HamMap, words[i].toLowerCase());
//					System.out.println(words[i]);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
		
	}
	private static void processSpamFile(File file){
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				String words[] = line.split("\\W*(\\W|_)\\W*");//splits on non alpha-numeric characters and ___
				for(int i =0; i<words.length; i++){
					if(wordCheck(words[i]))
						continue;
					addEntry(SpamMap, words[i].toLowerCase());
//					System.out.println(words[i]);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
		
	}
	
	private static void addEntry(Map<String, Integer> map, String strEntry)
	{
		if(map.containsKey(strEntry))
		{
			map.put(strEntry, (Integer)map.get(strEntry) + 1);
		}
		else
			map.put(strEntry, 1);
	}
	static boolean wordCheck(String str){
		//There are very few constraints at the moment, but more can be added
		if(str.length()<4 || numCheck(str) || str.length()>15)
			return true;
		return false;
	}
	static boolean numCheck(String str){
		for(int i = 0; i<str.length(); i++){
			char c = str.charAt(i);
			if(Character.isDigit(c))
				return true; //returns true if there is a digit in the word
		}
		
		return false;
	}

}
