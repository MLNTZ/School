package proj4;

import java.util.LinkedList;

public class HashTable {
	Bucket[] table;
	int capacity;
	int size;
	
	HashTable(int size){
		table = new Bucket[size];
		capacity = size;
		
		for (int i = 0; i < size; i++) {
			table[i] = new Bucket();
		}
	}
	
	int hashCode(String key) {
		int code = 0;
		int l = key.length();
		for (int i = 0; i < l; i++) {
			code =  (((int) key.charAt(i)) + code) * 67 + (int) Math.pow(2, l * (l - i)) ;
		}
		return code;
	}
	
	int hashIndex(String key) {
		int code = hashCode(key);
		return (Math.abs(691 * code + 149) % 64231) % capacity;
	}
	
	void insert(String key) {
		String lower = key.toLowerCase();
		table[hashIndex(lower)].add(lower); 
		size++;
	}
	
	int contains(String key) {
		String lower = key.toLowerCase();
		return table[hashIndex(lower)].contains(lower);
	}
	
}

class Bucket {
	LinkedList<String> keys;
	Bucket(){
		keys = new LinkedList<>();
	}
	void add(String key){
		keys.add(key);
	}
	
	boolean remove(String key) {
		return keys.remove(key);
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
