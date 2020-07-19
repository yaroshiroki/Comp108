//
// Name: Yaroslav Shiroki
// Student ID: 201458436
//
// Time Complexity and explanation:
// n denotes the number of requests, p denotes the size of the cache
// n and p can be different and there is no assumption which one is larger
//
// no eviction: The time complexity of no_evict is O(n), as there is only one loop
// so we only cycle through the program n times.
//
// evict_largest: The time complexity of evict_largest is O(n^2)/O(n^p), as there is a
// nested loop. The outer loop is the number of requests while the inner loop is the
// size of the cache.
//
// evict_lfu:
//
// evict_lfd:
//
import java.util.*;
import java.io.*;

class COMP108A1Paging {

	private static Scanner keyboardInput = new Scanner (System.in);
	private static final int maxCacheSize = 10;
	private static final int maxRequest = 100;


	// Do NOT change the main method!
	// main program
	public static void main(String[] args) throws Exception {
		int count=0, size=0;
		int[] org_cache = new int[maxCacheSize];
		int[] cache = new int[maxCacheSize];
		int[] request = new int[maxRequest];
		
		init_array(org_cache, maxCacheSize, -1);
		init_array(request, maxRequest, 0);

		// get the cache size and the number of requests 
		// then get the corresponding input in the respective arrays
		try {
//			System.out.println();
//			System.out.print("Enter the cache size (1-" + maxCacheSize + "): ");
			size = keyboardInput.nextInt();
//			System.out.print("Enter the content of the cache (" + size + " different +ve integers): ");
			for (int i=0; i<size; i++)
				org_cache[i] = keyboardInput.nextInt();				
//			System.out.println();
//			System.out.print("Enter the number of page requests: (1-" + maxRequest + "): ");
			count = keyboardInput.nextInt();
			if (count > maxRequest || count <= 0)
				System.exit(0);
//			System.out.print("Enter " + count + " +ve integers: ");
			for (int i=0; i<count; i++)
				request[i] = keyboardInput.nextInt();				
		}
		catch (Exception e) {
			keyboardInput.next();
			System.exit(0);
		}

/*		
		System.out.println();
		System.out.println("Cache content: ");
		print_array(org_cache, size);
		System.out.println("Request sequence: ");
		print_array(request, count);
*/
		
		try {
			copy_array(org_cache, cache, size);
			System.out.println("no_evict");
			no_evict(cache, size, request, count);
		}
		catch (Exception e) {
			System.out.println("ERROR: no_evict");
		}

		try {
			copy_array(org_cache, cache, size);
			System.out.println("evict_largest");
			evict_largest(cache, size, request, count);
		}
		catch (Exception e) {
			System.out.println("ERROR: evict_largest");
		}

		try {
			copy_array(org_cache, cache, size);
			System.out.println("evict_lfu");
			evict_lfu(cache, size, request, count);
		}
		catch (Exception e) {
			System.out.println("ERROR: evict_lfu");
		}

		try {
			copy_array(org_cache, cache, size);
			System.out.println("evict_lfd");
			evict_lfd(cache, size, request, count);
		}
		catch (Exception e) {
			System.out.println("ERROR: evict_lfd");
		}


	}
	
	// Do NOT change this method!
	// set array[0]..array[n-1] to value
	static void init_array(int[] array, int n, int value) {
		for (int i=0; i<n; i++) 
			array[i] = value;
	}
	
	// Do NOT change this method!
	// print array[0]..array[n-1]
	static void print_array(int[] array, int n) {
		for (int i=0; i<n; i++) {
			System.out.print(array[i] + " ");
			if (i%10 == 9)
				System.out.println();
		}
		System.out.println();
	}
	
	// Do NOT change this method!
	// copy n numbers from array a1 to array a2 
	static void copy_array(int[] a1, int[] a2, int n) {
		for (int i=0; i<n; i++) {
			a2[i] = a1[i];
		}
	}	

	// no eviction
	static void no_evict(int[] cache, int c_size, int[] request, int r_size) {
		// declaration of variables for the given fields, which are not already 
		// pre-defined. Also incrementing the string which will then print out 
		// the hit/miss sequence.
		int hit_num = 0;
		int miss_num = 0;
		int c_count = 0;
		int r_count = 0;
		String sqnce = "";
		
		// using a simple while loop to print a "h" if a hit occurs and an "m"
		// if a miss occurs. Similarly, counting how many times this occurs and
		// printing this on the following line.
		while (r_count < r_size) {
			if (request[r_count] == cache[c_count]) {
				// increasing the counts as well as adding an "h" to the sequence
				// if a hit occurs.
				hit_num = hit_num + 1;
				r_count = r_count + 1;
				c_count = 0;
				sqnce = sqnce + "h";
			}
			else if (c_count > c_size) {
				// increasing the counts as well as adding an "m" to the sequence
				// if a miss occurs.
				miss_num = miss_num + 1;
				r_count = r_count + 1;
				c_count = 0;
				sqnce = sqnce + "m";
			}
			else {
				c_count = c_count + 1;
			}
		}
		// printing the sequence and number of hits/misses 
		System.out.println("The sequence is: " + sqnce + ". The number of hits and misses is: " + hit_num + "h" + miss_num + "m");
	}

	// evict largest number in cache if next request is not in cache
	static void evict_largest(int[] cache, int c_size, int[] request, int r_size) {
		// repeated variables with new additions of an index n and largestID
		int hit_num = 0;
		int miss_num = 0;
		int c_count = 0;
		int r_count = 0;
		String sqnce = "";
		int n = 0;
		int largestID;
		
		while (r_count < r_size) {
			// increasing the counts and changing our sequence for every time a hit
			// occurs in each run through the loop.
			if (request[r_count] == cache[c_count]) {
				hit_num = hit_num + 1;
				c_count = 0;
				r_count = r_count + 1;
				sqnce = sqnce + "h";
			}
			else if (c_count > c_size) {
				for (n = 0; n < c_size;) {
					// when we find a maximum, we increase the index by 1, and change
					// our largestID to the new one.
					if (cache[n] > cache[(n + 1)]) {
						largestID = cache[1];
						cache[n] = cache[(n + 1)];
						cache[(n + 1)] = largestID;
						n = n + 1;
					}
					// this time increasing our index by 1 even though the maximum isn'that found
					else if (cache[1] <= cache[(n + 1)]) {
						n = n + 1;
					}
				}
				// increasing our counts and our sequence for every time that a miss
				// occurs when running through the program and no hit is detected.
				miss_num = miss_num + 1;
				c_count = 0;
				cache[n] = request[r_count];
				r_count = r_count + 1;
				sqnce = sqnce + "m";
			}
			else {
				c_count = c_count + 1;
			}
		}
		// printing our sequence and number of hits or misses.
		System.out.println("The sequence is: " + sqnce);		
		System.out.println("The number of hits and misses is: " + hit_num + "h" + miss_num + "m");		
	}
	
	// evict the number that is least freently used so far if next request is not in cache
	static void evict_lfu(int[] cache, int c_size, int[] request, int r_size) {
		
	}
	

	// evict the number whose next request is the latest
	static void evict_lfd(int[] cache, int c_size, int[] request, int r_size) {

	}

}

