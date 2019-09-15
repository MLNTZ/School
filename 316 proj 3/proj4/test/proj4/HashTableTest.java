package proj4;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class HashTableTest {

	@Test
	void testCode() {
		HashTable hash = new HashTable( 39097  );
	//	assertFalse(hash.hashCode("acrylate") == hash.hashCode("acyclic"));
		Scanner dict = null;
		Scanner test = null;
		try {
			dict = new Scanner(new File("files/dictionary.txt"));
			test = new Scanner(new File("files/dictionary.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("cant open");
			fail();
		}
		
		ArrayList<Integer> num = new ArrayList<>();
		while(dict.hasNext()) {
			String s = dict.next();
			hash.insert(s);
//			if(num.contains(hash.hashCode(s))) {
//				System.out.println(s + " " + hash.hashCode(s));
//			} else {
//				num.add(hash.hashCode(s));
//			}
			
		}
		double total =  0;
		dict.reset();
		while(test.hasNext()) {
			String s = test.next();
			int probes = hash.contains(s);
			total += probes;
			if (probes < 0) {
				fail();
			}
			if (probes > 3) {
				System.out.println(s + " took " + probes);
			}
		}
		System.out.println("Avg: " + (total / 25144));
		dict.close();
		
		assertTrue(hash.contains("KJEgf") < 0 );
		
		System.out.println(hash.contains("zygote"));
		System.out.println("fill: " +  ( double)25144 / (double) hash.capacity );
		
		assertTrue(hash.contains("oudfh") < 0);
		
		
		
	}

}
