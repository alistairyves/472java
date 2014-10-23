package soen472;
import java.io.File;
import java.io.IOException;
import java.lang.String;

public class Main {
	
	static String hamPath = System.getProperty("user.dir")+"\\emails\\20030228_easy_ham_2\\easy_ham_2";
	static String spamPath = System.getProperty("user.dir")+"\\emails\\20030228_spam_2\\spam_2";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(System.getProperty("user.dir"));
		System.out.println(hamPath);
		System.out.println(spamPath);

		File f = new File(hamPath); // current directory

	    File[] files = f.listFiles();
	    int count = 0;
	    for (File file : files) {
	        if (file.isDirectory()) {
	            System.out.print("directory:");
	        } else {
	            System.out.print("     file:");
	        }
	        try {
				System.out.println(file.getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        count++;
	        if (count >= 1000){
	        	break;
	        }
	    }
	    System.out.println(count);
	}

}
