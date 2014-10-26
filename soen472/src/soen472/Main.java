package soen472;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.String;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
	
	static String hamPath = System.getProperty("user.dir")+"\\emails\\20030228_easy_ham_2\\easy_ham_2";
	static String spamPath = System.getProperty("user.dir")+"\\emails\\20030228_spam_2\\spam_2";
	
	static HashMap<String, Integer> HamMap = new HashMap<String, Integer>();
	HashMap<String, Integer> SpamMap = new HashMap<String, Integer>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(System.getProperty("user.dir"));
		System.out.println(hamPath);
		System.out.println(spamPath);

		File f = new File(hamPath); // current directory

	    File[] files = f.listFiles();
	    File tempf = null;
	    int count = 0;
	    for (File file : files) {
	        if (file.isDirectory()) {
	            System.out.print("directory:");
	        } else {
	            //System.out.print("file: \t");
	        }
	        //System.out.println(file.getAbsolutePath());
	        count++;
	        if (count >= 1000){
	        	break;
	        }
	        tempf = file;
	    }	    
	    System.out.println(count);
	    processHamFile(tempf);
	    
	    System.out.println(HamMap);
	    
	    
	}
	private static void processHamFile(File file){
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				System.out.println(line);
				String words[] = line.split("\\W+");
				for(int i =0; i<words.length; i++){
					if(words[i].length()<3)
						continue;
					addEntry(HamMap, words[i].toLowerCase());
					System.out.println(words[i]);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
		
	}
	
	private static void addEntry(HashMap map, String strEntry)
	{
		if(map.containsKey(strEntry))
		{
			map.put(strEntry, (Integer)map.get(strEntry) + 1);
		}
		else
			map.put(strEntry, 1);
	}

}
