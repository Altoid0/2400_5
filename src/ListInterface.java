public interface ListInterface<T> {
    /** Adds a new entry to the end of this list.
     * Entries currently in the list are unaffected.
     * The list's size is increased by 1.
     * @param newEntry The object to be added as a new entry.
     */
    public void add(T newEntry);

    /** Adds a new entry at a specified position within this list.
     * Entries originally at and above the specified position are at the next higher position within the list.
     * The list's size is increased by 1.
     * @param newPosition An integer that specifies the desired position of the new entry.
     * @param newEntry The object to be added as a new entry.
     * @throws IndexOutOfBoundsException if either
     * newPosition < 1 or newPosition > getLength() + 1.
     */
    public void add(int newPosition, T newEntry);
}
