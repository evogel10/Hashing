import java.util.*;
import java.io.*;
import java.io.IOException;

/**
* Class HashTable is used to crate a hash table object if the collision
* handeling scheme is chaining
*
* @author Eric Vogel
*/
public class ChainHashTable {
	private final int TABLESIZE = 120;
	ChainingNode[] hashTable;
    private int entries;
    private int modulo;
    private int hashScheme;
    private int bucketSize;
    private int collisionScheme;
    private int printStyle;
    private int rows;
    private Stack<Integer> space;
    private int primaryCollision;
    private double loadFactor;

    /**
    * HashTable contstructor to instatiate a hash table
    *
    * @param e - number of entries from the input file
    * @param m - modulo number used in the hashing fuction
    * @param hS - hashing scheme used
    * @param bS - bucket size of the hash table
    * @param cHS - collision handeling used
    * @param pS - print setup for writing the hash table to the output file
    */
	public ChainHashTable(int e, int m, int hS, int bS, int cHS, int pS) {
        entries = e;
        modulo = m;
        hashScheme = hS;
        bucketSize = bS;
        collisionScheme = cHS;
        printStyle = pS;

        rows = TABLESIZE/bucketSize;

        space = new Stack<Integer>();


		hashTable = new ChainingNode[TABLESIZE];

		for (int i = 0; i < TABLESIZE; i++ ) {
			hashTable[i] = null;
			space.push(i);
		}

	}

    /**
    * Add a value to the hash table
    *
    * @param key - key to the value from the input file
    * @param value - value form the input file
    * @param modulo - modulo value used in the hash function
    */
	public void add(int key, int value, int modulo) {
	    int hash = 0;

        // Hash the key
        hash = hashFunction(key);

        // Check hashtable to see if the space is empty
        if(hashTable[hash] == null){
        	hashTable[hash] = new ChainingNode(value);

        	// Remove index hash from stack of free space
        	removeSpace(hash);
        }
        // Check hash table to see if the space is empty
        else if(hashTable[hash] != null ) { // if the value is already there
        	// Checks for duplicates
        	if (hashTable[hash].key == value) {
        	} else {
	        	primaryCollision++;
	        	// Free space fround to insert
	        	int freeSpace = (Integer)space.pop();
	        	hashTable[freeSpace] = new ChainingNode(value);

	        	// Remove index hash from space
	        	hashTable[hash].next = hashTable[freeSpace];
        	}
        }
	}

    /**
    * Removes the free space from the free space stack after a value
    * is inserted into the hash table.
    *
    * @param hash - value hash function
    */
	public void removeSpace(int hash) {
		// Temporay stack to hold free space
		Stack<Integer> temp = new Stack<Integer>();
		int possibleMatch = (Integer)space.pop();

		// searches for free space
		while (!space.empty() && possibleMatch != hash){
			temp.push(possibleMatch);
			possibleMatch = (Integer)space.pop();
		}
		// Pushes free space back onto space stack
		while(!temp.empty()){
			space.push(temp.pop());
		}
	}

    /**
    * Both division and multiplicaiton hash function
    *
    * @param key - key of value to use in hash function
    * @return hash value
    */
	public int hashFunction(int key){
	    int hash = 0;
	    if (hashScheme == 1){
	        //division
	        hash = key % modulo;
	        if (hash >= rows) {
	            hash = 0;
	        }
	        return Math.abs(hash);
	    } else { // Multiplicaiton (hashScheme == 2)
	        double a = 0.1;
	        hash = (int)(modulo * ((key * a) % 1));
	        if (hash >= rows) {
	            hash = 0;
	        }
	        return Math.abs(hash);
	    }
    }

    /**
    * Primary collision handeling
    *    
    * @return returns number of primary collisions
    */
    public int primaryCollision() {
        return primaryCollision;
    }

    /**
    * Load factor handeling
    *    
    * @return load factor
    */
    public double loadFactor() {
        return entries/ (double)TABLESIZE;
    }

    /**
    * Writes matrix to output file.
    *
    * @param writer - output file.
    */
	public void writeTableToFile(BufferedWriter writer) throws IOException{
		int temp = 0;
		for (int i = 0; i < TABLESIZE; i++) {
			if (hashTable[i] == null) {
				writer.write("-----" + " ");
			} else {
				writer.write(hashTable[i] + " ");
			}
            temp++;
            if(temp == printStyle){
                writer.newLine();
                temp = 0;
            }
		}
	}
}