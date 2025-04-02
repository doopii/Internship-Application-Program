package adt;

import java.io.Serializable;
import java.util.Iterator;

/**
 *
 * @author yijia
 * @param <E> The class of objects which the collection ADT is storing
 */
public class DoublyLinkedList<E> implements DoublyListInterface<E>, Cloneable, Serializable {

    private static final long serialVersionUID = 1L;
    private Node firstNode;
    private Node lastNode;
    private int numberOfEntries;

    public DoublyLinkedList() {
        firstNode = null;
        lastNode = null;
        numberOfEntries = 0;
    }

    @Override
    public boolean add(E newEntry) {
        Node newNode = new Node(newEntry);
        if (isEmpty()) {
            firstNode = newNode;
        } else {
            lastNode.nextNode = newNode;
            newNode.previousNode = lastNode;
        }
        lastNode = newNode;
        numberOfEntries++;
        return true;
    }

    @Override
    public boolean add(int newPosition, E newEntry) {
        if (newPosition < 1 || newPosition > numberOfEntries + 1) {
            return false;
        }

        Node newNode = new Node(newEntry);

        Node currentNode = firstNode;
        for (int i = 1; i < newPosition - 1; i++) {
            currentNode = currentNode.nextNode;
        }

        if (currentNode == null) {
            firstNode = newNode;
            lastNode = newNode;
        } else if (currentNode.previousNode == null && newPosition == 1) {
            firstNode = newNode;
            newNode.nextNode = currentNode;
            currentNode.previousNode = newNode;
        } else {
            newNode.nextNode = currentNode.nextNode;
            if (currentNode.nextNode == null) {
                lastNode = newNode;
            } else {
                newNode.nextNode.previousNode = newNode;
            }
            currentNode.nextNode = newNode;
            newNode.previousNode = currentNode;
        }
        numberOfEntries++;
        return true;
    }

    @Override
    public E remove(int givenPosition) {
        if (isEmpty() || givenPosition < 1 || givenPosition > numberOfEntries) {
            return null;
        }

        Node<E> removedNode;

        if (givenPosition == 1) {
            removedNode = firstNode;
            firstNode = firstNode.nextNode;
            if (firstNode == null) {
                lastNode = null;
            } else {
                firstNode.previousNode = null;
            }
        } else if (givenPosition == numberOfEntries) {
            removedNode = lastNode;
            lastNode = lastNode.previousNode;
            lastNode.nextNode = null;
        } else {
            Node currentNode = firstNode;
            for (int i = 1; i < givenPosition - 1; i++) {
                currentNode = currentNode.nextNode;
            }
            removedNode = currentNode.nextNode;
            currentNode.nextNode = removedNode.nextNode;
            removedNode.nextNode.previousNode = currentNode;
        }

        numberOfEntries--;
        return removedNode.data;
    }

    @Override
    public E remove(E anEntry) {
        if (isEmpty() || !contains(anEntry)) {
            return null;
        }

        int position = -1;
        for (int i = 1; i <= numberOfEntries; i++) {
            if (getEntry(i).equals(anEntry)) {
                position = i;
                break;
            }
        }

        if (position < 1) {
            return null;
        }
        return remove(position);
    }

    @Override
    public void clear() {
        firstNode = null;
        lastNode = null;
        numberOfEntries = 0;
    }

    @Override
    public boolean replace(int givenPosition, E newEntry) {
        if (isEmpty() || givenPosition < 1 || givenPosition > numberOfEntries) {
            return false;
        }

        Node currentNode = firstNode;
        for (int i = 1; i < givenPosition; i++) {
            currentNode = currentNode.nextNode;
        }

        currentNode.data = newEntry;
        return true;
    }

    @Override
    public E getEntry(int givenPosition) {
        if (isEmpty() || givenPosition < 1 || givenPosition > numberOfEntries) {
            return null;
        }

        Node<E> currentNode = firstNode;
        for (int i = 1; i < givenPosition; i++) {
            currentNode = currentNode.nextNode;
        }

        return currentNode.data;
    }

    @Override
    public boolean contains(E anEntry) {
        Node currentNode = firstNode;
        for (int i = 1; i <= numberOfEntries; i++) {
            if (currentNode.data.equals(anEntry)) {
                return true;
            }
            currentNode = currentNode.nextNode;
        }

        return false;
    }

    @Override
    public int getNumberOfEntries() {
        return numberOfEntries;
    }

    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public Iterator<E> getIterator() {
        return new DoublyLinkedListIterator();
    }

    /**
     * Create a newly instantiated shallow copy of the list
     *
     * @return a newly instantiated shallow copy of the list
     */
    @Override
    public DoublyListInterface<E> clone() {
        DoublyLinkedList<E> clonedList = new DoublyLinkedList();

        Node<E> currentNode = firstNode;
        clonedList.firstNode = currentNode;

        while (currentNode != null) {
            clonedList.add(currentNode.data);
            currentNode = currentNode.nextNode;
        }

        return clonedList;
    }
    
    @Override
    public E getLast() {
        if (isEmpty()) {
            return null;
        }
        return (E) lastNode.data;
    }


    /**
     * Get if the two objects are equal by their entries
     *
     * @param o an object to be evaluated if they are equal
     * @return true IF reference are equal, OTHERWISE false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DoublyLinkedList)) {
            return false;
        }
        DoublyLinkedList<?> other = (DoublyLinkedList<?>) o;
        if (numberOfEntries != other.numberOfEntries) {
            return false;
        }
        Node<E> currentThis = firstNode;
        Node<?> currentOther = other.firstNode;
        while (currentThis != null) {
            if (!currentThis.data.equals(currentOther.data)) {
                return false;
            }
            currentThis = currentThis.nextNode;
            currentOther = currentOther.nextNode;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Iterator<E> iterator = getIterator();
        while (iterator.hasNext()) {
            builder.append(iterator.next());
            builder.append("\n");
        }
        return builder.toString();
    }

    private class Node<E> implements Serializable {

        private static final long serialVersionUID = 1L;
        private E data;
        private Node previousNode;
        private Node nextNode;

        private Node(E data) {
            this.data = data;
            previousNode = null;
            nextNode = null;
        }
    }

    private class DoublyLinkedListIterator implements Iterator<E> {

        private Node<E> currentNode;

        private DoublyLinkedListIterator() {
            this.currentNode = firstNode;
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public E next() {
            if (hasNext()) {
                E data = currentNode.data;
                currentNode = currentNode.nextNode;
                return data;
            } else {
                return null;
            }
        }
        

    }
}
