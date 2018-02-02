import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
* Lab 2 read in an input file containing a list of values. The program reads in this list
* of values and hashes them into a hash table useing division and multiplication methods
* and using linear probing, quadriatic probing, and chaining to handle collisions. The 
* final hash table is written to an output file with associated statistics.
*
* @author Eric Vogel
* @version 9/25/16
*/
public class EVogelLab2 {

	/**
	* Main method takes in an input file and uses the method readInData to
	* manupulate the data
	*
	* @param Args - is the file name in the command line
	* @exception FileNotFoundException
	*/
	public static void main(String[] args) throws IOException {

		try {

			readInData(new Scanner(new FileReader(args[0])), new File(args[1]));
		} catch (FileNotFoundException ex) {
			System.out.print("Error: " + ex);
		}
	}

	/**
	* Takes input file from main method and reads in line by line to build 
	* an array of values
	*
	* @param input - file with containing matrices and their orders
	* @param output - file
	*/
	private static void readInData(Scanner input, File file) throws IOException {
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
		String line;
		int[] table = new int[120];
		int e = 0; // entries from file
		Stopwatch watch = new Stopwatch();
		StringBuilder stringBuilder = new StringBuilder();

		// Sets header for results table.
		stringBuilder.append("\n------------------------------------Algorithm Running Time-------------------------------------\n");
		stringBuilder.append("      Hashing Scheme            Bucket Size     Collision Handling Scheme     Time In Nanoseconds\n");

		try {
			// Parses through the input file line by line
			while ((line = (input.nextLine())) != null) {
				if (!isInteger(line)) {
				// Skips blank lines in input file
				} else if(line.trim().equals("") || line.equals("\n")) {
    				continue;		
    			// Checks if the value is an integer
    			} else if (isInteger(line.trim())) {
    				int num = Integer.parseInt(line.trim());

	    			table[e] = num;
	    			e++;
    			}
    		}


		} catch (NoSuchElementException ex) {
			// End of file
		} catch (NumberFormatException ex) {
			writer.write("Invalid Input.\n");
		}

		// Calls the hashhandlig method to write the hash tables to teh files and records the running time
		writer.write("\n1. Mod = 120, Hashing = Division, Bucket = 1, Collison = Linear, Print = 5\n");
		watch.start();
		hashHandling(table, e, 120, 1, 1, 1, 5, writer);
		watch.stop();
		stringBuilder.append("1   Divison modulo 120               1               Linear Probing                " + watch.timeInNanoseconds() +"\n");
		writer.write("\n2. Mod = 120, Hashing = Division, Bucket = 1, Collison = Quadratic, Print = 5\n");
		watch.start();
		hashHandling(table, e, 120, 1, 1, 2, 5, writer);
		watch.stop();
		stringBuilder.append("2   Divison modulo 120               1             Quadratic Probing               " + watch.timeInNanoseconds() +"\n");
		writer.write("\n3. Mod = 120, Hashing = Division, Bucket = 1, Collison = Chaining, Print = 5\n");
		watch.start();
		hashHandling(table, e, 120, 1, 1, 3, 5, writer);
		watch.stop();
		stringBuilder.append("3   Divison modulo 120               1                  Chaining                   " + watch.timeInNanoseconds() +"\n");
		writer.write("\n4. Mod = 113, Hashing = Division, Bucket = 1, Collison = Linear, Print = 5\n");
		watch.start();
		hashHandling(table, e, 113, 1, 1, 1, 5, writer);
		watch.stop();
		stringBuilder.append("4   Divison modulo 113               1               Linear Probing                " + watch.timeInNanoseconds() +"\n");
		writer.write("\n5. Mod = 113, Hashing = Division, Bucket = 1, Collison = Quadratic, Print = 5\n");
		watch.start();
		hashHandling(table, e, 113, 1, 1, 2, 5, writer);
		watch.stop();
		stringBuilder.append("5   Divison modulo 113               1             Quadratic Probing               " + watch.timeInNanoseconds() +"\n");
		writer.write("\n6. Mod = 113, Hashing = Division, Bucket = 1, Collison = Chaining, Print = 5\n");
		watch.start();
		hashHandling(table, e, 113, 1, 1, 3, 5, writer);
		watch.stop();
		stringBuilder.append("6   Divison modulo 113               1                  Chaining                   " + watch.timeInNanoseconds() +"\n");
		writer.write("\n7. Mod = 41, Hashing = Division, Bucket = 1, Collison = Linear, Print = 3\n");
		watch.start();
		hashHandling(table, e, 41, 1, 3, 1, 3, writer);
		watch.stop();
		stringBuilder.append("7   Divison modulo 41                3               Linear Probing                " + watch.timeInNanoseconds() +"\n");
		writer.write("\n8. Mod = 41, Hashing = Division, Bucket = 1, Collison = Quadratic, Print = 3\n");
		watch.start();
		hashHandling(table, e, 41, 1, 3, 2, 3, writer);
		watch.stop();
		stringBuilder.append("8   Divison modulo 41                3             Quadratic Probing               " + watch.timeInNanoseconds() +"\n");
		writer.write("\n9. Mod = 120, Hashing = Multiplication, Bucket = 1, Collison = Linear, Print = 5\n");
		watch.start();
		hashHandling(table, e, 120, 2, 1, 1, 5, writer);
		watch.stop();
		stringBuilder.append("9   Multiplication modulo 120        1               Linear Probing                " + watch.timeInNanoseconds() +"\n");
		writer.write("\n10. Mod = 120, Hashing = Multiplication, Bucket = 1, Collison = Quadratic, Print = 5\n");
		watch.start();
		hashHandling(table, e, 120, 2, 1, 2, 5, writer);
		watch.stop(); 
		stringBuilder.append("10  Multiplication modulo 120        1             Quadratic Probing               " + watch.timeInNanoseconds() +"\n");
		writer.write("\n11. Mod = 120, Hashing = Division, Bucket = 1, Collison = Chaining, Print = 5\n");
		watch.start();
		hashHandling(table, e, 120, 2, 1, 3, 5, writer);
		watch.stop();
		stringBuilder.append("11  Multiplication modulo 120        1                  Chaining                   " + watch.timeInNanoseconds() +"\n");
		
		String results = stringBuilder.toString();
		writer.write(results);
		writer.close();
	}

	/**
	* Takes in the values from the input array table creates the hash tables 
	*
	* @param table - table of values from the input file
	* @param e - number of entries from the input file
	* @param m - modulo number used in the hashing fuction
	* @param hS - hashing scheme used
	* @param bS - bucket size of the hash table
	* @param cHS - collision handeling used
	* @param pS - print setup for writing the hash table to the output file
	* @param writer - writes to the output fie
	*/
	public static void hashHandling(int[] table, int e, int m, int hS, int bS, int cHS, int pS, BufferedWriter writer) throws IOException{

		// Creates both hash table objects
		HashTable hashTable = new HashTable(e, m, hS, bS, cHS, pS);
		ChainHashTable chainHashTable = new ChainHashTable(e, m, hS, bS, cHS, pS);

		// Pass the values from the input file into the hash tables depending on the collions handling scheme
		for (int i = 0; i<e; i++){
			if (cHS == 1 || cHS == 2){
				hashTable.add(table[i], table[i], m);
			} else {
				chainHashTable.add(table[i], table[i], m);

			}
		}

		// If the collision handeling scheme is linear or quadracti probing, it will write its data to output file
		if (cHS == 1 || cHS == 2) {
			hashTable.writeTableToFile(writer);

			writer.newLine();
			writer.write("Table Statistics");
			writer.write("\nNumber of primary collisions: ");
			writer.write(Integer.toString(hashTable.primaryCollision()));
			writer.write("\nNumber of secondary collisions: ");
			writer.write(Integer.toString(hashTable.secondaryCollisions()));
			writer.write("\nLoad Factor: ");
			writer.write(Double.toString((hashTable.loadFactor())));
			writer.newLine();
		} else { // If the collision handeling scheme is chaining, it will write its data to output file
			chainHashTable.writeTableToFile(writer);

			writer.newLine();
			writer.write("Table Statistics");
			writer.write("\nNumber of primary collisions: ");
			writer.write(Integer.toString(chainHashTable.primaryCollision()));
			writer.write("\nLoad Factor: ");
			writer.write(Double.toString((chainHashTable.loadFactor())));
			writer.newLine();
		}
	}

	/**
	* Checks to see if the input file is an integer 
	*
	* @param s - line from input file
	* @return boolean - true if it is an integer and false if it isn't
	*/
	public static boolean isInteger(String s) {
		for (char c : s.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
			return true;
	}
}