package adt;


public class LinkedList<T> implements ListInterface<T> {
    private class Node {
        T data;
        Node next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public void add(T newEntry) {
        Node newNode = new Node(newEntry);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    @Override
    public boolean remove(T anEntry) {
        if (head == null) return false;

        if (head.data.equals(anEntry)) {
            head = head.next;
            size--;
            return true;
        }

        Node current = head;
        Node previous = null;
        while (current != null && !current.data.equals(anEntry)) {
            previous = current;
            current = current.next;
        }

        if (current == null) return false;

        previous.next = current.next;
        size--;
        return true;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) return null;

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }
}
