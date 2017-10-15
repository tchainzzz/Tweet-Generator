package main;

/*
 * ONLY RUN THIS AFTER UPDATING trump.txt!
 * 
 * Uses regex to take out unwanted characters.
 */

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FormatFile {
	
	private static final String TRAINING_SET = "res/trump.txt";

	public static void main(String[] args) {
		Path path = Paths.get(TRAINING_SET);
		Charset charset = StandardCharsets.UTF_8;
		
		try {
			String content = new String(Files.readAllBytes(path), charset);
			System.out.println(content);
			
			System.out.println("replacing links");
			content = content.replaceAll("https?://[^\\s]+", "");
			System.out.println("fixing ampersands");
			content = content.replaceAll("&amp", "and");
			System.out.println("removing unexpected markers");
			content = content.replaceAll("cont ", "");
			System.out.println("smoothing out quirks");
			content = content.replaceAll("[â€œ™˜\"(){}¦ºðŸ‡”“¸]", "");
			Files.write(path,  content.getBytes(charset));
			System.out.println("all done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
