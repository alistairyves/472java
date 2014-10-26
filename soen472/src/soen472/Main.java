package soen472;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.String;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
	
	static String hamPath = System.getProperty("user.dir")+"\\emails\\20030228_easy_ham_2\\easy_ham_2";
	static String spamPath = System.getProperty("user.dir")+"\\emails\\20030228_spam_2\\spam_2";
	
	static TreeMap<String, Integer> HamMap = new TreeMap<String, Integer>();
	static TreeMap<String, Integer> SpamMap = new TreeMap<String, Integer>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(System.getProperty("user.dir"));
		System.out.println(hamPath);
		System.out.println(spamPath);

		File hamDir = new File(hamPath);
		File spamDir = new File(spamPath);

	    File[] hamFiles = hamDir.listFiles();
	    File[] spamFiles = spamDir.listFiles();
	    
	    int count = 0;
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
	    for (File file : spamFiles) {
	        if (file.isDirectory()) {
	            System.out.print("directory:");
	        } else {
	            //System.out.print("file: \t");
	        }
	        processSpamFile(file);
	        //System.out.println(file.getAbsolutePath());
	        count++;
	        if (count >= 1000){
	        	break;
	        }
	        //tempf = file;
	    }
	    System.out.println(count);

	    System.out.println(HamMap);
	    System.out.println(SpamMap);
	    
	    
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
	
	private static void addEntry(TreeMap<String, Integer> map, String strEntry)
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
		if(str.length()<3 || numCheck(str))
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
