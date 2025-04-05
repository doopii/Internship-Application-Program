/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.DoublyLinkedList;
import adt.DoublyListInterface;
import dao.InitialiserJava;
import entity.Schedule;
import entity.Interview;
import entity.Applicant;
import entity.JobDesired;
import entity.Skill;
import utility.BubbleSort;
import java.util.Scanner;

/**
 *
 * @author Acer
 */
public class ScheduleManager {

    private final DoublyListInterface<Schedule> scheduleList;
    private final DoublyListInterface<Interview> interviewList;
    private DoublyListInterface<Applicant> applicantList;
    private final DoublyListInterface<Skill> skillList = new InitialiserJava().getSkillList();

    public ScheduleManager() {
        InitialiserJava initializer = new InitialiserJava();
        initializer.initializeData();
        scheduleList = initializer.getScheduleList();
        interviewList = initializer.getInterviewList();
    }

    // Display all schedules and interviews
    public void showAllSchedules() {
        System.out.println("\n========== All Schedules ==========");
        for (int i = 1; i <= scheduleList.getNumberOfEntries(); i++) {
            Schedule schedule = scheduleList.getEntry(i);
            System.out.println(schedule);
            System.out.println("  Interviews:");
            for (int j = 1; j <= interviewList.getNumberOfEntries(); j++) {
                Interview interview = interviewList.getEntry(j);
                if (interview.getScheduleID().equals(schedule.getScheduleID())) {
                    System.out.println("    - " + interview);
                }
            }
        }
    }

    // Add a new schedule with auto-generated ID
    public boolean addSchedule(String scheduleID, String scheduleTime, String scheduleDescription) {
        for (int i = 1; i <= scheduleList.getNumberOfEntries(); i++) {
            if (scheduleList.getEntry(i).getScheduleID().equalsIgnoreCase(scheduleID)) {
                return false; // Prevent duplicate
            }
        }

        Schedule schedule = new Schedule(scheduleID, scheduleTime, scheduleDescription);
        scheduleList.add(schedule);
        return true;
    }

    // Add an interview to a schedule
    public boolean addInterviewToSchedule(String jobID, String scheduleID, String interviewID, String interviewPosition, String interviewStatus, String interviewDate) {
        Interview interview = new Interview(jobID, scheduleID, interviewID, interviewPosition, interviewStatus, interviewDate);
        interviewList.add(interview);
        return true;
    }

    // Remove a schedule
    public boolean removeSchedule(String scheduleID) {
        for (int i = 1; i <= scheduleList.getNumberOfEntries(); i++) {
            if (scheduleList.getEntry(i).getScheduleID().equals(scheduleID)) {
                scheduleList.remove(i);
                return true;
            }
        }
        return false;
    }

    // Remove an interview from a schedule
    public boolean removeInterview(String scheduleID, String interviewID) {
        for (int i = 1; i <= interviewList.getNumberOfEntries(); i++) {
            Interview interview = interviewList.getEntry(i);
            if (interview.getInterviewID().equalsIgnoreCase(interviewID) &&
                interview.getScheduleID().equalsIgnoreCase(scheduleID)) {
                interviewList.remove(i);
                return true;
            }
        }
        return false;
    }

    // Update the details of a schedule
    public boolean updateSchedule(String scheduleID, String newTime, String newDescription) {
        for (int i = 1; i <= scheduleList.getNumberOfEntries(); i++) {
            Schedule schedule = scheduleList.getEntry(i);
            if (schedule.getScheduleID().equals(scheduleID)) {
                schedule.setScheduleTime(newTime);
                schedule.setScheduleDesc(newDescription);
                scheduleList.replace(i, schedule);
                return true;
            }
        }
        return false;
    }

    // Update an interview (replace interviewee)
    public boolean updateInterview(String interviewID, String newPosition, String newStatus, String newDate) {
        for (int i = 1; i <= interviewList.getNumberOfEntries(); i++) {
            Interview interview = interviewList.getEntry(i);
            if (interview.getInterviewID().equals(interviewID)) {
                interview.setInterviewPosition(newPosition);
                interview.setInterviewStatus(newStatus);
                interview.setInterviewDate(newDate);
                interviewList.replace(i, interview);
                return true;
            }
        }
        return false;
    }

    // Search an interview by its ID
    public Interview searchInterview(String interviewID) {
        for (int i = 1; i <= interviewList.getNumberOfEntries(); i++) {
            if (interviewList.getEntry(i).getInterviewID().equals(interviewID)) {
                return interviewList.getEntry(i);
            }
        }
        return null;
    }

    // Sort interviews by date
    public void sortInterviewsByDate() {
        BubbleSort.sortInterviewByDate(interviewList);
    }
    
    /*public void analyzeResults() {
        System.out.println("\n=== Successful & Ranked Applicants ===");

        DoublyListInterface<Applicant> matchedApplicants = new DoublyLinkedList<>();
        DoublyListInterface<Applicant> rankedApplicants = new DoublyLinkedList<>();

        Match applicants to successful interviews
        for (int i = 1; i <= interviewList.getNumberOfEntries(); i++) {
            Interview interview = interviewList.getEntry(i);

            if (interview.getInterviewStatus().equalsIgnoreCase("Success")) {
                String matchedPosition = interview.getInterviewPosition();

                for (int j = 1; j <= applicantList.getNumberOfEntries(); j++) {
                    Applicant applicant = applicantList.getEntry(j);

                    for (int k = 1; k <= applicant.getJobDesiredList().getNumberOfEntries(); k++) {
                        JobDesired job = applicant.getJobDesiredList().getEntry(k);

                        if (job.getPosition().equalsIgnoreCase(matchedPosition)) {
                            matchedApplicants.add(applicant);
                            break;
                        }
                    }
                }
            }
        }

        Filter by CGPA and education
        for (int i = 1; i <= matchedApplicants.getNumberOfEntries(); i++) {
            Applicant applicant = matchedApplicants.getEntry(i);
            if (applicant.getCgpa() >= 3.7 && applicant.getJobDesiredList().contains(new JobDesired("Computer Science", ""))) {
                rankedApplicants.add(applicant);
            }
        }

        Sort by number of skills (descending)
        int n = rankedApplicants.getNumberOfEntries();
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= n - i; j++) {
                Applicant a1 = rankedApplicants.getEntry(j);
                Applicant a2 = rankedApplicants.getEntry(j + 1);

                if (a1.getSkillList().getNumberOfEntries() < a2.getSkillList().getNumberOfEntries()) {
                    rankedApplicants.replace(j, a2);
                    rankedApplicants.replace(j + 1, a1);
                }
            }
        }

        // Display ranked list
        for (int i = 1; i <= rankedApplicants.getNumberOfEntries(); i++) {
            Applicant applicant = rankedApplicants.getEntry(i);
            System.out.println((i) + ". " + applicant.getApplicantName() + " | CGPA: " + applicant.getCgpa() + " | Skills: " + applicant.getSkillList().getNumberOfEntries());
        }
    }*/
                        
    // Menu for schedule and interview management
    public void scheduleMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n========== Schedule Menu ==========");
            System.out.println("1. Display All Schedules with Interviews");
            System.out.println("2. Add Schedule");
            System.out.println("3. Add Interview to Schedule");
            System.out.println("4. Remove Schedule");
            System.out.println("5. Remove Interview");
            System.out.println("6. Update Schedule");
            System.out.println("7. Update Interview");
            System.out.println("8. Search Interview");
            System.out.println("9. Sort Interviews by Date");
            System.out.println("10. Analyze Interview Results");
            System.out.println("11. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    showAllSchedules();
                    break;
                case 2:
                    int nextNum = scheduleList.getNumberOfEntries() + 1;
                    String id = String.format("S%03d", nextNum);  // Auto ID: S001, S002, etc.
                    System.out.println("Generated Schedule ID: " + id);

                    System.out.print("Enter Schedule Time (e.g., 2025-04-10 09:00AM): ");
                    String time = scanner.nextLine();

                    System.out.print("Enter Description: ");
                    String desc = scanner.nextLine();

                    if (addSchedule(id, time, desc))
                        System.out.println("Schedule added successfully.");
                    else
                        System.out.println("Duplicate schedule ID.");
                    break;
                case 3:
                    int nextInt = interviewList.getNumberOfEntries() + 1;
                    String interviewID = String.format("IV%03d", nextInt);
                    System.out.println("Generated Interview ID: " + interviewID);

                    System.out.print("Enter Schedule ID to add Interview: ");
                    String scheduleID = scanner.nextLine();

                    System.out.print("Enter Job ID: ");
                    String jobID = scanner.nextLine();

                    System.out.print("Enter Interview Position: ");
                    String position = scanner.nextLine();

                    System.out.print("Enter Interview Status (Success/Fail): ");
                    String status = scanner.nextLine();

                    System.out.print("Enter Interview Date (e.g., 2025-04-10 10:00AM): ");
                    String date = scanner.nextLine();

                    if (addInterviewToSchedule(jobID, scheduleID, interviewID, position, status, date))
                        System.out.println("Interview added to schedule.");
                    else
                        System.out.println("Interview already exists.");
                    break;
                case 4:
                    System.out.print("Enter Schedule ID to remove: ");
                    String removeScheduleID = scanner.nextLine();
                    removeSchedule(removeScheduleID);
                    break;
                case 5:
                    System.out.print("Enter Schedule ID: ");
                    String schedID = scanner.nextLine();
                    System.out.print("Enter Interview ID to remove: ");
                    String removeInterviewID = scanner.nextLine();

                    if (removeInterview(schedID, removeInterviewID))
                        System.out.println("Interview removed.");
                    else
                        System.out.println("Interview not found.");
                    break;
                case 6:
                    System.out.print("Enter Schedule ID to update: ");
                    String updateScheduleID = scanner.nextLine();
                    System.out.print("Enter new Schedule Time: ");
                    String newTime = scanner.nextLine();
                    System.out.print("Enter new Description: ");
                    String newDesc = scanner.nextLine();
                    updateSchedule(updateScheduleID, newTime, newDesc);
                    break;
                case 7:
                    System.out.print("Enter Interview ID to update: ");
                    String updateInterviewID = scanner.nextLine();
                    System.out.print("Enter new Position: ");
                    String newPosition = scanner.nextLine();
                    System.out.print("Enter new Status: ");
                    String newStatus = scanner.nextLine();
                    System.out.print("Enter new Date: ");
                    String newDate = scanner.nextLine();
                    updateInterview(updateInterviewID, newPosition, newStatus, newDate);
                    break;
                case 8:
                    System.out.print("Enter Interview ID to search: ");
                    String searchInterviewID = scanner.nextLine();
                    Interview foundInterview = searchInterview(searchInterviewID);
                    if (foundInterview != null)
                        System.out.println(foundInterview);
                    else
                        System.out.println("Interview not found.");
                    break;
                case 9:
                    sortInterviewsByDate();
                    System.out.println("Interviews sorted by date.");
                    showAllSchedules();
                    break;
                //case 10:
                    //analyzeResults();
                    //break;
                case 10:
                    System.out.println("Exiting Schedule Manager...");
                    break;                    
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 10);
    }

    public static void main(String[] args) {
        new ScheduleManager().scheduleMenu();
    }
}
