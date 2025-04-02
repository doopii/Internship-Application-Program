/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.DoublyLinkedList;
import adt.DoublyListInterface;
import entity.Interview;
import dao.InitialiserJava;
import java.util.Scanner;

/**
 *
 * @author Acer
 */
public class ScheduleManager {
    private DoublyListInterface<Interview> interviewList = new DoublyLinkedList<>();
    

    
    public void initialiserJava() {
        InitialiserJava initializer = new InitialiserJava();
        DoublyListInterface<Interview> interviewList = initializer.initializeInterviews();

        for (int i = 0; i < interviewList.getNumberOfEntries(); i++) {
            Interview interview = interviewList.getEntry(i);
            addInterview(
                interview.getInterviewID(),
                interview.getJobID(),
                interview.getInterviewDate(),
                interview.getInterviewPosition()
            );
        }
    }
    
    public boolean addInterview(String interviewID, String jobID, String date, String time) {
        for (int i = 0; i < interviewList.getNumberOfEntries(); i++) {
            if (interviewList.getEntry(i).getInterviewID().equals(interviewID)) {
                return false; //duplicat interview id
            }
        }
        
        Interview interview = new Interview(jobID, null, interviewID, time, date);
        interviewList.add(interview);
        return true;
    }
    
    public boolean removeInterview(String interviewID) {
        for(int i = 0; i < interviewList.getNumberOfEntries(); i++) {
            if(interviewList.getEntry(i).getInterviewID().equals(interviewID)) {
                interviewList.remove(interviewList.getEntry(i));
                return true;
            }
        }
        return false;
    }
    
    public Interview searchInterview(String interviewID) {
        for (int i = 0; i < interviewList.getNumberOfEntries(); i++) {
            if (interviewList.getEntry(i).getInterviewID().equals(interviewID)) {
                return interviewList.getEntry(i);
            }
        }
        return null;
    }

    public void displayInterviews() {
        for (int i = 0; i < interviewList.getNumberOfEntries(); i++) {
            System.out.println(interviewList.getEntry(i));
        }
    }
    
    public boolean updateInterview(String interviewID, String newDate, String newTime) {
        for (int i = 0; i < interviewList.getNumberOfEntries(); i++) {
            Interview interview = interviewList.getEntry(i);
            if (interview.getInterviewID().equals(interviewID)) {
                interview.setInterviewDate(newDate);
                interview.setInterviewDate(newTime);
                interviewList.replace(i + 1, interview); 
                return true;
            }
        }
        return false;
    }

    public void displaySortedByDate() {
        bubbleSortDate();
        displayInterviews();
    } 
    
    public void filterByJob(String jobID) {
        for (int i = 0; i < interviewList.getNumberOfEntries(); i++) {
            Interview interview = interviewList.getEntry(i);
            if(interview.getJobID().equals(jobID)) {
                System.out.println(interview);
            }
        }
    }
    
    public void bubbleSortDate() {
        int n = interviewList.getNumberOfEntries();
        boolean sorted = false;

        for (int pass = 1; pass < n && !sorted; pass++) {
            sorted = true;
            for (int i = 0; i < n - pass; i++) {
                Interview first = interviewList.getEntry(i);
                Interview second = interviewList.getEntry(i + 1);

                // Compare based on interviewID (you can customize to date/time)
                if (first.getInterviewDate().compareTo(second.getInterviewDate()) > 0) {
                    // Swap
                    interviewList.replace(i, second);
                    interviewList.replace(i + 1, first);
                    sorted = false;
                }
            }
        }
    }
    
    public void run() {
        Scanner scanner = new Scanner(System.in);
        initialiserJava();

        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- Schedule Management ---");
            System.out.println("1. Display All Interviews");
            System.out.println("2. Add Interview");
            System.out.println("3. Remove Interview");
            System.out.println("4. Search Interview");
            System.out.println("5. Sort by Date");
            System.out.println("6. Filter by Job ID");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    displayInterviews();
                    break;
                case 2:
                    System.out.print("Enter Interview ID: ");
                    String interviewID = scanner.nextLine();
                    System.out.print("Enter Job ID: ");
                    String jobID = scanner.nextLine();
                    System.out.print("Enter Interview Date (dd-mm-yyyy): ");
                    String date = scanner.nextLine();
                    System.out.print("Enter Interview Position/Time: ");
                    String time = scanner.nextLine();

                    boolean added = addInterview(interviewID, jobID, date, time);
                    if (added)
                        System.out.println("Interview added successfully.");
                    else
                        System.out.println("Error: Duplicate Interview ID.");
                    break;
                case 3:
                    System.out.print("Enter Interview ID to remove: ");
                    String removeID = scanner.nextLine();
                    if (removeInterview(removeID))
                        System.out.println("Interview removed.");
                    else
                        System.out.println("Interview not found.");
                    break;
                case 4:
                    System.out.print("Enter Interview ID to search: ");
                    String searchID = scanner.nextLine();
                    Interview found = searchInterview(searchID);
                    if (found != null)
                        System.out.println("Found: " + found);
                    else
                        System.out.println("Interview not found.");
                    break;
                case 5:
                    displaySortedByDate();
                    break;
                case 6:
                    System.out.print("Enter Job ID to filter by: ");
                    String filterJobID = scanner.nextLine();
                    filterByJob(filterJobID);
                    break;
                case 7:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void main(String[] args) {
        new ScheduleManager().run();
    } 
}
