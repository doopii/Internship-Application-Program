package adt;

import java.util.Iterator;

/**
 *
 * @author yijia
*/

public interface DoublyListInterface<E> extends Cloneable {
    boolean add(E newEntry);
    boolean add(int newPosition, E newEntry);
    E remove(int givenPosition);
    E remove(E anEntry);
    void clear();
    boolean replace(int givenPosition, E newEntry);
    E getEntry(int givenPosition);
    boolean contains(E anEntry);
    int getNumberOfEntries();
    boolean isEmpty();
    boolean isFull();
    Iterator<E> getIterator();
    DoublyListInterface<E> clone();
    E getLast();
    void swap(int index1, int index2);
}
