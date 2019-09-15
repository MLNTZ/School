package spell;

import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;




import java.util.LinkedList;
/**
 * defines a hash table that can insert and find key values
 * @author hmintz Hunter Mintz
 *
 */
 class HashTable {
	Bucket[] table;
	int capacity;
	int size;
	/**
	 * creates hash table and initializes the buckets in the array
	 * @param size
	 */
	HashTable(int size){
		table = new Bucket[size];
		capacity = size;
		
		for (int i = 0; i < size; i++) {
			table[i] = new Bucket();
		}
	}
	/**
	 * will generate a hash code for the given string
	 * @param key
	 * @return the hash code
	 */
	int hashCode(String key) {
		int code = 0;
		int l = key.length();
		for (int i = 0; i < l; i++) {
			code =  (((int) key.charAt(i)) + code) * 67 + (int) Math.pow(2, l * (l - i)) ;
		}
		return code;
	}
	/**
	 * will compress the hash code into an index inside of the the table
	 * @param key
	 * @return
	 */
	int hashIndex(String key) {
		int code = hashCode(key);
		return (Math.abs(691 * code + 149) % 64231) % capacity;
	}
	/**
	 * will insert the key into the hash table
	 * @param key
	 */
	void insert(String key) {
		String lower = key.toLowerCase();
		table[hashIndex(lower)].add(lower); 
		size++;
	}
	/**
	 * will test if the key is in the hash table
	 * @param key
	 * @return the number of comparisons made to find the key
	 * 			the number of comparisons made negated if the key is not found
	 * 			0 if the key leads to an empty bucket
	 */
	int contains(String key) {
		String lower = key.toLowerCase();
		return table[hashIndex(lower)].contains(lower);
	}
	
}
/**
 * defines a bucket stored in the hash table, contains a linked list of keys that are inserted into the table
 * @author hmintz Hunter Mintz
 *
 */
class Bucket {
	LinkedList<String> keys;
	Bucket(){
		keys = new LinkedList<>();
	}
	/**
	 * will add the given key to this bucket
	 * @param key
	 */
	void add(String key){
		keys.add(key);
	}
	/**
	 * checks if this bucket contains the given key and counts the number of comparisons made
	 * @param key
	 * @return the number of comparisons made to find the key
	 * 			the number of comparisons made negated if not found
	 * 			0 if the bucket is empty
	 */
	int contains(String key) {
		for (int i = 0; i < keys.size(); i++) {
			if (keys.get(i).equals(key)) {
				return i + 1;
			}
		}
		return keys.size() == 0 ? 0 : -keys.size() ;
	}
	
}
/**
 * Project 4 for csc316, will prompt user for a dictionary file and a text file and then will spell check the file, and output any misspelled words
 * as well as data concerning the serach and hash table
 * @author hmintz Hunter Mintz
 *
 */
public class proj4 {
	
	static HashTable ht;
	static Scanner dict;
	static Scanner text;
	static PrintStream out;
	static final int TABLE_SIZE = 39097;
	
	
	static double wordsInDictionary;
	static double wordsChecked;
	static double totalProbes;
	static double misspelledWords;
	static double totalLookups;
	
	public static void main(String[] args) {
		System.out.println("Welcome to Spell Checker");
		Scanner stdin = new Scanner(System.in);
		System.out.print("Dictionary File: ");
		File dictionary = new File(stdin.next());
		System.out.print("\nText File: ");
		File textFile = new File(stdin.next());
		System.out.print("\nOutput File: ");
		File outFile =  new File(stdin.next());
		stdin.close();
		dict = null;
		text = null;
		out = null;
		
		try {
			dict = new Scanner (dictionary);
			text = new Scanner (textFile);
			out = new PrintStream(outFile);
		} catch (Exception e) {
			System.out.println("File cannot be opened");
			System.exit(1);
		}
		wordsInDictionary = readDictionary();
		
		spellCheck();
		out.println("Words in dictionary: " + (int) wordsInDictionary);
		out.println("Words Checked: " + (int) wordsChecked);
		out.println("Misspelled Words: " + (int) misspelledWords);
		out.println("Total Probes: " + (int) totalProbes);
		out.println("Avg Probes per word: " + (double) (totalProbes/wordsChecked));
		out.println("Avg Probes per Lookup: " + (double)(totalProbes/totalLookups));
		
		dict.close();
		text.close();
		out.close();
		
	}
	
	

	private static int readDictionary() {
		ht = new HashTable(TABLE_SIZE);
		int i = 0;
		while(dict.hasNext()) {
			ht.insert(dict.next());
			i ++;
		}
		return i;
	}
	
	
	
	private static void spellCheck() {
		wordsChecked = 0;
		misspelledWords = 0;	
		totalProbes = 0;
		totalLookups = 0;
		while (text.hasNext()) {
			String word = text.next();
			try {
				@SuppressWarnings("unused")
				double num = Double.valueOf(word);
				wordsChecked++;
				continue;
			} catch (Exception e) {
				//do nothing
			}
			word = word.replaceAll("[\\W&&[^']]", "");
			
			if (word.equals("") || word.equals(" ")){
				continue;
			}
			if (!checkWord(word.toLowerCase())) {
				out.println(word);
				misspelledWords++;
			} 
			wordsChecked ++;
		
		}
		
	}



	private static boolean checkWord(String word) {
		int lookups = 0;
		int probes = 0;
		int check = 0;
		boolean valid = false;
		if ((check = ht.contains(word)) > 0) {
			probes += check;
			lookups++;
			valid = true;
		} else if (!valid) {
			lookups++;
			probes += Math.abs(check);
			//check for 's
			if (word.endsWith("'s") && !valid) {
				check = ht.contains(word.substring(0, word.length() - 2));
				probes += Math.abs(check);
				lookups++;
				if (check > 0) {
					valid = true;
				}
			}
			//check for s
			if (word.endsWith("s") && !valid) {
				check = ht.contains(word.substring(0, word.length() - 1));
				probes += Math.abs(check);
				lookups++;
				if (check > 0) {
					valid = true;
				}
			}
			//check for es
			if (word.endsWith("es") && !valid) {
				check = ht.contains(word.substring(0, word.length() - 2));
				probes += Math.abs(check);
				lookups++;
				if (check > 0) {
					valid = true;
				}
			}
			//chekc for ed
			if(word.endsWith("ed") && !valid){
				check = ht.contains(word.substring(0, word.length() - 2));
				probes += Math.abs(check);
				lookups++;
				if (check > 0) {
					valid = true;
				}
				if (!valid) {
					check = ht.contains(word.substring(0, word.length() - 1));
					probes += Math.abs(check);
					lookups++;
					if (check > 0) {
						valid = true;
					}
				}
			}
			//check for er
			if(word.endsWith("er") && !valid){
				check = ht.contains(word.substring(0, word.length() - 2));
				probes += Math.abs(check);
				lookups++;
				if (check > 0) {
					valid = true;
				}
				if (!valid) {
					check = ht.contains(word.substring(0, word.length() - 1));
					probes += Math.abs(check);
					lookups++;
					if (check > 0) {
						valid = true;
					}
				}
			}
			//check for ing
			if(word.endsWith("ing") && !valid){
				check = ht.contains(word.substring(0, word.length() - 3));
				probes += Math.abs(check);
				lookups++;
				if (check > 0) {
					valid = true;
				}
				if (!valid) {
					check = ht.contains(word.substring(0, word.length() - 3) + "e");
					probes += Math.abs(check);
					lookups++;
					if (check > 0) {
						valid = true;
					}
				}
			}
			//check for ly
			if(word.endsWith("ly") && !valid){
				check = ht.contains(word.substring(0, word.length() - 2));
				probes += Math.abs(check);
				lookups++;
				if (check > 0) {
					valid = true;
				}
			}	
		}
		totalLookups += lookups;
		totalProbes += probes;
		return valid;
	
	}
	
	
	
	
	
}


