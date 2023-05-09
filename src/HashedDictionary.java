import java.util.Iterator;
import java.util.NoSuchElementException;

//
// Name: Shah, Tanay
// Project: 5
// Due: 5/12/23
// Course: cs-2400-03-sp23
//
// Description:
// Airport app to show and manage airports, distances, and shortest paths through the use of a graph
//

public class HashedDictionary <K,V> implements DictionaryInterface<K,V> {
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 5;
    private static final int MAX_CAPACITY = 10000;
    private Entry<K,V>[] hashTable;
    private int tableSize;
    private static final int MAX_SIZE = 2 * MAX_CAPACITY;
    private static final double MAX_LOAD_FACTOR = 0.5;
    protected final Entry<K,V> AVAILABLE = new Entry<K,V>(null, null);
    private int collisionCount;

    public HashedDictionary() {
        this(DEFAULT_CAPACITY);
    }
    public HashedDictionary(int initialCapacity) {
        checkCapacity(initialCapacity);
        numberOfEntries = 0;
        tableSize = getNextPrime(initialCapacity);
        @SuppressWarnings("unchecked")
        Entry<K,V>[] tempHashTable = (Entry<K,V>[])new Entry[tableSize];
        hashTable = tempHashTable;
    }
    private void checkCapacity(int capacity) {
        if (capacity > MAX_CAPACITY) {
            throw new IllegalStateException("Requested size exceeds the allowed maximum of " + MAX_CAPACITY);
        }
    }

    private int getNextPrime(int integer) {
        if (integer <= 0) {
            integer = 3;
        }
        if (integer % 2 == 0) {
            integer++;
        }
        while (!isPrime(integer)) {
            integer += 2;
        }
        return integer;
    }

    private boolean isPrime(int integer) {
        boolean foundDivisor = false;
        int divisor = 3;
        while (!foundDivisor && (divisor < integer)) {
            if (integer % divisor == 0) {
                foundDivisor = true;
            }
            else {
                divisor += 2;
            }
        }
        return !foundDivisor;
    }

    public V getValue(K key) {
        V result = null;
        int index = getHashIndex(key);
        index = linearProbe(index, key);
        if ((hashTable[index] != null) && (hashTable[index] != AVAILABLE)) {
            result = hashTable[index].getValue(); // key is populated, return the value
        }
        return result;
    }


    public V add (K key, V value) {
        V result = null;
        if ((key == null) || (value == null)) {
            throw new IllegalArgumentException("Cannot add null to a dictionary.");
        }
        int index = getHashIndex(key);
        index = linearProbe(index, key);

        if ((hashTable[index] == null) || (hashTable[index] == AVAILABLE)) {
            hashTable[index] = new Entry<K,V>(key, value);
            if (index != getHashIndex(key)) {
                collisionCount++;
            }
            numberOfEntries++;
        } else {
            result = hashTable[index].getValue();
            hashTable[index].setValue(value);
        }
        return result;
    }

    public Iterator<K> getKeyIterator() {
        return new KeyIterator();
    }

    public int getCollisionCount() {
        return collisionCount;
    }

    public Iterator<V> getValueIterator() {
        throw new UnsupportedOperationException("Not supported.");
    }
    public V remove(K key) {
        throw new UnsupportedOperationException("Not supported.");
    }
    public boolean contains(K key) {
        throw new UnsupportedOperationException("Not supported.");
    }
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }
    public int getSize() {
        return numberOfEntries;
    }
    public void clear() {
        throw new UnsupportedOperationException("Not supported.");
    }


    private int getHashIndex(K key) {
        int hashIndex = key.hashCode() % tableSize;
        if (hashIndex < 0) {
            hashIndex += tableSize;
        }
        return hashIndex;
    }

    private int linearProbe(int index, K key) {
        boolean found = false;
        int availableStateIndex = -1;
        while (!found && (hashTable[index] != null)) {
            if (hashTable[index] != AVAILABLE) {
                if (key.equals(hashTable[index].getKey())) {
                    found = true; // if the key we are probing for already exists tell the user that by returning its index
                }
                else {
                    index = (index + 1) % tableSize; // else if the key is not the desired key keep probing
                }
            }
            else { // if the given index is available...
                if (availableStateIndex == -1) {
                    availableStateIndex = index; // save the index
                    index = (index + 1) % tableSize; // what is the point of doing this if we already found an available index?
                }
                // if there were 2 available indexes one after another wouldn't the while loop never end?
            }
        }
        if (found || (availableStateIndex == -1)) {
            return index;
        }
        else {
            return availableStateIndex;
        }
    }

    private class KeyIterator implements Iterator<K> {
        private int currentIndex; // current index of cursor
        private int numberLeft; // number of entries left to iterate over

        private KeyIterator() {
            currentIndex = 0;
            numberLeft = numberOfEntries;
        }

        public boolean hasNext() {
            return numberLeft > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported by this iterator.");
        }

        public K next() {
            K result = null;
            if (hasNext()) {
                while ((hashTable[currentIndex] == null) || (hashTable[currentIndex] == AVAILABLE)) {
                    currentIndex++;
                }
                result = hashTable[currentIndex].getKey();
                currentIndex++;
                numberLeft--;
            }
            else {
                throw new NoSuchElementException("no more entries to iterate over.");
            }

            return result;
        }
    }

    private static class Entry<K,V> {
        private K key;
        private V value;
        private States state;
        private enum States {CURRENT, REMOVED};

        private Entry(K searchKey, V dataValue) {
            key = searchKey;
            value = dataValue;
            state = States.CURRENT;
        }
        private K getKey() {
            return key;
        }
        private V getValue() {
            return value;
        }
        private void setValue(V dataValue) {
            value = dataValue;
        }
    }
}
