/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.LinkedList;
import adt.ListInterface;
import entity.Applicant;
import entity.Interview;

/**
 *
 * @author Acer
 */
public class Schedule {
    private ListInterface<Interview> interviewList = new LinkedList<>();
    
    public Schedule() {
        interviewList = new LinkedList<>();
    }
    
    public boolean addInterview(String interviewID, String jobID, String date, String time) {
        for (int i = 0; i < interviewList.size(); i++) {
            if (interviewList.get(i).getInterviewID().equals(interviewID)) {
                return false; //duplicat interview id
            }
        }
        
        Interview interview = new Interview(jobID, null, interviewID, time, date);
        interviewList.add(interview);
        return true;
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
    
    public boolean updateInterview(String interviewID, String newDate, String newTime) {
        for (int i = 0; i < interviewList.size(); i++) {
            Interview interview = interviewList.get(i);
            if (interview.getInterviewID().equals(interviewID)) {
                interview.setInterviewDate(newDate);
                interview.setInterviewDate(newTime);
                interviewList.replace(i + 1, interview); 
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
    
        // Bubble sort by Interview ID
    public void bubbleSortInterviews() {
        int n = interviewList.size();
        boolean sorted = false;

        for (int pass = 1; pass < n && !sorted; pass++) {
            sorted = true;
            for (int i = 0; i < n - pass; i++) {
                Interview first = interviewList.get(i);
                Interview second = interviewList.get(i + 1);

                // Compare based on interviewID (you can customize to date/time)
                if (first.getInterviewID().compareTo(second.getInterviewID()) > 0) {
                    // Swap
                    interviewList.replace(i, second);
                    interviewList.replace(i + 1, first);
                    sorted = false;
                }
            }
        }

        System.out.println("Interviews sorted by ID.");
    }
    
    public void bubbleSortDate() {
        int n = interviewList.size();
        boolean sorted = false;

        for (int pass = 1; pass < n && !sorted; pass++) {
            sorted = true;
            for (int i = 0; i < n - pass; i++) {
                Interview first = interviewList.get(i);
                Interview second = interviewList.get(i + 1);

                // Compare based on interviewID (you can customize to date/time)
                if (first.getInterviewDate().compareTo(second.getInterviewDate()) > 0) {
                    // Swap
                    interviewList.replace(i, second);
                    interviewList.replace(i + 1, first);
                    sorted = false;
                }
            }
        }

        System.out.println("Interviews sorted by date.");
    }
    
    public void displaySortedByDate() {
        bubbleSortDate();
        displayInterviews();
    }
    
    public void filterByJob(String jobID) {
        for (int i = 0; i < interviewList.size(); i++) {
            Interview interview = interviewList.get(i);
            if(interview.getJobID().equals(jobID)) {
                System.out.println(interview);
            }
        }
    }
    
}
