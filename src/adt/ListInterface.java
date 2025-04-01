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
    void add(T newEntry);
    boolean remove(T anEntry);
    T get(int index);
    int size();
    boolean isEmpty();
    void clear();
    int getLength();
    T getEntry(int index);
    boolean replace(int givenPosition, T newEntry);
    
    // add more methods(T)
}