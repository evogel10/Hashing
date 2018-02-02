import java.io.*;
import java.io.IOException;

/**
* Class HashTable is used to crate a hash table object if the collision
* handeling scheme is linear or quadratic probing
*
* @author Eric Vogel
*/
public class HashTable {
    private final int TABLESIZE = 120;
    int[][] hashTable;
    private int entries;
    private int modulo;
    private int hashScheme;
    private int bucketSize;
    private int collisionScheme;
    private int printStyle;
    private int rows;
    private int cols;
    private int primaryCollision;
    private int secondaryCollisions;
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
    public HashTable(int e, int m, int hS, int bS, int cHS, int pS){
        entries = e;
        modulo = m;
        hashScheme = hS;
        bucketSize = bS;
        collisionScheme = cHS;
        printStyle = pS;

        rows = TABLESIZE/bucketSize;
        cols = bucketSize;

        hashTable = new int[rows][cols];
        //initiate all values to null for HashTable
        for(int i = 0; i<rows; i++) {
            for(int j = 0; j<cols; j++){
                hashTable[i][j] = -1;
            }
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
        int index = 0;
        //hash the key
        hash = hashFunction(key);

        // Used to keep track of primary and secondary collisions
        boolean colid = false;

        // Check hash table to see if the space is empty
        while( chechRow(hash, value) == -10 ) { // if the value is already there

            index++;
            hash = reHash(hash, index);

            if(!colid){
                // Primary collisions
                primaryCollision++;
                colid = true;
            } else {
                // Secondary collisions
                secondaryCollisions++;
            }

        }
        colid = false;
        // Checks for duplicates
        if(chechRow(hash, value) == -11){
        } else {         
            //now that we found a row with available spce, add to hashtable
            hashTable[hash][chechRow(hash, value)] = value;
        }
    }

    /**
    * Checks the row for a free space and looks for duplicates
    *
    * @param hash - value from hash function
    * @param value - value from input file to add to hash table
    * @return -10 if element is full, -11 if it is an duplication, and i if
    * space is free
    */
    public int chechRow(int hash, int value) {

        for ( int i = 0; i < cols; i++) {
            // Checks for duplicates
            if (hashTable[hash][i] == value){
                return -11;
            } else if (hashTable[hash][i] == -1){ // Checks for freespace
                return i;
            }
        }
        // returns -10 if element is full
        return -10;
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
    * Both the linear and quadratic probing if a collision occurs
    *
    * @param hash - value from hash function
    * @param index - increases the quadraction probing by 1    
    * @return hash value
    */
    public int reHash(int hash, int index){

        // Constant values for quadratic probing
        double c1 = 0.5;
        double c2 = 0.5;

        // Linear probing
        if (collisionScheme == 1){
            hash = (hash + 1) % modulo;
            if (hash >= rows) {
                hash = 0;
            }
            return hash;
        } else  { // Quadratic probing
            hash = (int)((hash + index * c1 + (index * index) * c2) % modulo);
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
    * Secondary collision handeling
    *    
    * @return returns number of Secondary collisions
    */
    public int secondaryCollisions() {
        return secondaryCollisions;
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
    public void writeTableToFile(BufferedWriter writer) throws IOException {
        int temp = 0;
        //printTable();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < bucketSize; j++) {
                if (hashTable[i][j] == -1) {
                    writer.write("-----" + " ");
                } else {
                    writer.write(String.format("%05d", hashTable[i][j]) + " ");
                }
                temp++;
                if(temp == printStyle){
                    writer.newLine();
                    temp = 0;
                }
            }
        }
    }
}