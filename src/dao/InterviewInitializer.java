/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.*;
import entity.Interview;
/**
 *
 * @author Acer
 */
public class InterviewInitializer {
    public ListInterface<Interview> initializeInterviews() {
        ListInterface<Interview> interviewList = new LinkedList<>();
        interviewList.add(new Interview("J001", "S001", "I001", "Developer", "20-02-2025"));
        interviewList.add(new Interview("J002", "S002", "I002", "Tester", "20-02-2025"));
        interviewList.add(new Interview("J003", "S003", "I003", "Designer", "21-02-2025"));
        interviewList.add(new Interview("J004", "S004", "I004", "Developer", "22-02-2025"));
        interviewList.add(new Interview("J005", "S005", "I005", "Support specialist", "22-02-2025"));
        return interviewList;
    }
    
    public static void main(String[] args) {
        InterviewInitializer initializer = new InterviewInitializer();
        ListInterface<Interview> list = initializer.initializeInterviews();
        System.out.println("Interview List:");
        for (int i = 0; i < list.size(); i++){
            System.out.println(list.get(i));
        }
    }
}
