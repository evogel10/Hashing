/**
* Class HashTable is used to crate a hash table object if the collision
* handeling scheme is chaining
*
* Class ChainingNode is used to in the chainingHash class to store data
*
* @author Eric Vogel
*/
public class ChainingNode {
    Integer key = null;
    ChainingNode next = null;

    /**
    * Empty ChainingNode contructor
    */
    public void ChainingNode () {
    }

    /**
    * Sets the values of the ChainingNode
    *
    * @param key - key to the value from the input file
    */
    public ChainingNode (int key) {
        this.key = key;
        this.next = next;
    }

    /**
    * Prints the value of the chaining node
    *
    * @return value of chaining node
    */
    public String toString () {
        return key + "";
    }

} 