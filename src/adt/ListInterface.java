package adt;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author evago
 */
public interface ListInterface<T> {
    void add(T newEntry); //e
    boolean remove(T anEntry); //e
    T get(int index); //e
    int size(); //e
    boolean isEmpty(); //e
    void clear();
    T getLast(); //e
    boolean replace(int givenPosition, T newEntry);
    public boolean contains(T anEntry);

    
    // add more methods(T)
}