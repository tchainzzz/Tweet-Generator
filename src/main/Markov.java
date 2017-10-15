package main;

import java.io.*;
import java.util.*;

/*
 * Given a training dataset (text), this class generates a Markov chain of text.
 */

public class Markov {

	//fields
	private HashMap<String, ArrayList<String>> markov; //the key String is the predictor, picking a resultant string from value ArrayList<String>
	private ArrayList<String> tokens; //tokenized full text of training
	private ArrayList<String> keys; //tokenized full text of training
	private Random rand;
	private String phrase;
	private static int chunkSize;

	private static final String TRAINING_SET = "res/trump.txt";
	private static final int INITIAL_CHUNK_SIZE = 2;


	public Markov() {
		markov = new HashMap<String, ArrayList<String>>();
		tokens = new ArrayList<String>();
		rand = new Random();
		chunkSize = INITIAL_CHUNK_SIZE;
		createMarkov();
	}

	private void createMarkov() {
		markov.clear();
		scanFile();
		createHashMap();
		keys = new ArrayList<String>(markov.keySet());
	}

	private void createHashMap() {
		for (int i = 0; i <= tokens.size() - chunkSize; i++) {
			String key = getKey(i);

			if (!markov.containsKey(key)) { //create new key if not in hashmap
				ArrayList<String> temp = new ArrayList<String>();
				updateMap(key, temp, i);
			} else { //append new word to hashmap
				ArrayList<String> temp = markov.get(key);
				updateMap(key, temp, i);

			}		
		}

		//System.out.println(markov.toString()); //comment this back in if you want to die
	}

	private String getKey(int step) {
		String s = "";
		for (int i = 0; i < chunkSize; i++) {
			s += tokens.get(step + i) + " ";
		}
		return s;
	}

	private void updateMap(String key, ArrayList<String> value, int step) {
		try {
			
				value.add(tokens.get(step + chunkSize));
			

		} catch (IndexOutOfBoundsException e) {
			value.add("covfefe");

		}
		markov.put(key, value);		
	}

	private void scanFile() {
		tokens = new ArrayList<String>();
		try {
			Scanner training = new Scanner(new File(TRAINING_SET));
			while (training.hasNext()) {

				tokens.add(training.next());
			}
			training.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}

	public String tweet() {
		phrase = ""; //this is what you see on screen
		int index = rand.nextInt(Math.max(1, keys.size() - chunkSize)); //picks a random starting point from keys, the keyset of the markov hashmap
		//System.out.println(keys.size());
		//System.out.println(index);
		phrase += keys.get(index).substring(0, 1).toUpperCase() + keys.get(index).substring(1).replaceAll("\\s+$", ""); //removes all whitespace at end of thing
		String result = keys.get(index);

		while (!".!?".contains(result.substring(result.length() - 2, result.length() - 1)) && phrase.length() < 132) {
			ArrayList<String> value = markov.get(result);
			System.out.println(result);
			String nextWord = value.get(rand.nextInt(value.size()));
			phrase += " " + nextWord.trim();
			result = createResult(result, nextWord);
			System.out.println(phrase);
		}

		if (!".!?".contains(phrase.substring(phrase.length() - 1))) phrase += "!";

		return phrase;

	}

	private String createResult(String result, String nextWord) {
		Scanner resultant = new Scanner(result);
		String s = "";
		resultant.next();
		while (resultant.hasNext()) {
			s += resultant.next() + " ";
		}
		resultant.close();
		s += nextWord + " ";
		return s;
	}
	
	public String toString() {
		return "I'm here!";
	}

	public void setChunkSize(int n) {
		chunkSize = n;
		createMarkov();

	}

}
