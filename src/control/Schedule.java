/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.LinkedList;
import entity.Interview;

/**
 *
 * @author Acer
 */
public class Schedule {
    private LinkedList<Interview> interviewList;
    
    public Schedule() {
        interviewList = new LinkedList<>();
    }
    
    public void addInterview(Interview interview) {
        interviewList.add(interview);
    }
    
    public boolean removeInterview(String interviewID) {
        for(int i = 0; i < interviewList.size(); i++) {
            if(interviewList.get(i).getInterviewID().equals(interviewID)) {
                interviewList.remove(interviewList.get(i));
                return true;
            }
        }
        return false;
    }
    
    public Interview searchInterview(String interviewID) {
        for (int i = 0; i < interviewList.size(); i++) {
            if (interviewList.get(i).getInterviewID().equals(interviewID)) {
                return interviewList.get(i);
            }
        }
        return null;
    }
    
    public void displayInterviews() {
        for (int i = 0; i < interviewList.size(); i++) {
            System.out.println(interviewList.get(i));
        }
    }
    
    public void sortInterviewByDate() {
        int n = interviewList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                Interview i1 = interviewList.get(j);
                Interview i2 = interviewList.get(j + 1);
                if (i1.getInterviewDate().compareTo(i2.getInterviewDate()) > 0) {
                    interviewList.replace(j, i2);
                    interviewList.replace(j + 1, i1);
                }
            }
        }
    }
}
