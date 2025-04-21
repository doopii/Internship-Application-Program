//Student 4: Wong Yieng Zheng
package control;

import adt.DoublyLinkedList;
import adt.DoublyListInterface;
import dao.InitialiserJava;
import entity.Schedule;
import entity.Interview;
import entity.Applicant;
import entity.Job;

import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Acer
 */

public class ScheduleManager {
    
    private DoublyListInterface<Schedule> scheduleList = new DoublyLinkedList<>();
    private DoublyListInterface<Interview> interviewList = new DoublyLinkedList<>();
    private final MatchingEngine matchingEngine;


    public ScheduleManager() {
        InitialiserJava initializer = new InitialiserJava();
        initializer.initializeData();
        scheduleList = initializer.getScheduleList();
        interviewList = initializer.getInterviewList();
        matchingEngine = new MatchingEngine();
    }
    
    public void showAllSchedules() {
        System.out.println("\n=============================================================");
        System.out.println("                 ALL SCHEDULES WITH INTERVIEWS           ");
        System.out.println("=============================================================");

        for (int i = 1; i <= scheduleList.getNumberOfEntries(); i++) {
            Schedule sched = scheduleList.getEntry(i);
            System.out.println("Schedule ID   : " + sched.getScheduleID());
            System.out.println("Schedule Time : " + sched.getScheduleTime());
            System.out.println("Description   : " + sched.getScheduleDesc());
            System.out.println("-------------------------------------------------------------");
            System.out.printf("%-15s %-10s %-22s %-15s\n", 
                    "Interview ID", "Job ID", "Position", "Time");
            System.out.println("-------------------------------------------------------------");

            boolean hasInterview = false;

            for (int j = 1; j <= interviewList.getNumberOfEntries(); j++) {
                Interview inter = interviewList.getEntry(j);
                if (inter.getScheduleID().equals(sched.getScheduleID())) {
                    System.out.printf("%-15s %-10s %-25s%-15s\n",
                            inter.getInterviewID(),
                            inter.getJobID(),
                            inter.getInterviewPosition(),
                            inter.getInterviewDate());
                    hasInterview = true;
                }
            }

            if (!hasInterview) {
                System.out.println("No interviews scheduled for this session.");
            }

            System.out.println("=============================================================\n");
        }
    }

    public boolean addSchedule(String scheduleID, String scheduleTime, String scheduleDescription) {
        for (int i = 1; i <= scheduleList.getNumberOfEntries(); i++) {
            if (scheduleList.getEntry(i).getScheduleID().equalsIgnoreCase(scheduleID)) {
                return false;
            }
        }

        Schedule schedule = new Schedule(scheduleID, scheduleTime, scheduleDescription);
        scheduleList.add(schedule);
        return true;
    }

    public boolean addInterviewToSchedule(String jobID, String scheduleID, String interviewID, String interviewPosition, String interviewStatus, String interviewDate) {
        Interview interview = new Interview(jobID, scheduleID, interviewID, interviewPosition, interviewStatus, interviewDate);
        interviewList.add(interview);
        return true;
    }

    public boolean removeSchedule(String scheduleID) {
        for (int i = 1; i <= scheduleList.getNumberOfEntries(); i++) {
            if (scheduleList.getEntry(i).getScheduleID().equals(scheduleID)) {
                scheduleList.remove(i);
                return true;
            }
        }
        return false;
    }

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

    public boolean updateInterview(String interviewID, String newJobID, String newPosition, String newStatus) {
        for (int i = 1; i <= interviewList.getNumberOfEntries(); i++) {
            Interview interview = interviewList.getEntry(i);
            if (interview.getInterviewID().equals(interviewID)) {
                interview.setJobID(newJobID);
                interview.setInterviewPosition(newPosition);
                interview.setInterviewStatus(newStatus);
                interviewList.replace(i, interview);
                return true;
            }
        }
        return false;
    }

    public Interview searchInterview(String interviewID) {
        for (int i = 1; i <= interviewList.getNumberOfEntries(); i++) {
            if (interviewList.getEntry(i).getInterviewID().equals(interviewID)) {
                return interviewList.getEntry(i);
            }
        }
        return null;
    }
    
    public void matchAndScheduleApplicants(Scanner scanner) {
        for (int i = 1; i <= scheduleList.getNumberOfEntries(); i++) {
            Schedule schedule = scheduleList.getEntry(i);

            for (int j = 1; j <= interviewList.getNumberOfEntries(); j++) {
                Interview interview = interviewList.getEntry(j);

                if (interview.getScheduleID().equals(schedule.getScheduleID()) && interview.getApplicant() == null) {
                    String jobId = interview.getJobID();
                    Job job = matchingEngine.findJobById(jobId);

                    if (job != null) {
                        DoublyListInterface<MatchingEngine.MatchResult> matches = matchingEngine.findMatchesForJob(job);

                        boolean assigned = false;

                        for (int k = 1; k <= matches.getNumberOfEntries(); k++) {
                            MatchingEngine.MatchResult match = matches.getEntry(k);
                            Applicant applicant = match.getApplicant();
                            double score = match.getScore();

                            // Optional: enforce score threshold
                            if (score < 0.2) continue;

                            boolean alreadyAssigned = false;
                            for (int m = 1; m <= interviewList.getNumberOfEntries(); m++) {
                                Interview existing = interviewList.getEntry(m);
                                if (existing.getApplicant() != null &&
                                    existing.getApplicant().getApplicantID().equals(applicant.getApplicantID())) {
                                    alreadyAssigned = true;
                                    break;
                                }
                            }

                            if (!alreadyAssigned) {
                                interview.setApplicant(applicant);
                                interview.setInterviewStatus("Success");
                                interview.getInterviewDate();
                                interviewList.replace(j, interview);

                                System.out.printf("[MATCHED] Interview %s assigned to %s with score %.2f%%\n",
                                    interview.getInterviewID(),
                                    applicant.getApplicantName(),
                                    score * 100);

                                assigned = true;
                                break;
                            }
                        }

                        if (!assigned) {
                            interview.setInterviewStatus("Pending");
                            interview.getInterviewDate();
                            interviewList.replace(j, interview);

                            System.out.println("No suitable match found for: " + interview.getInterviewID() + " (" + interview.getInterviewPosition() + ")");
                        }
                    }
                }
            }
        }

        System.out.println("\nMatched applicants scheduled successfully.");
        System.out.print("View the matched schedule? (1 = Yes, 2 = No): ");
        int viewChoice = scanner.nextInt();
        scanner.nextLine();

        if (viewChoice == 1) {
            displayMatchedApplicantsSchedule(scanner);
        }
    }

    private void displayMatchedApplicantsSchedule(Scanner scanner) {
        System.out.println("\n================================================================================================================");
        System.out.println("                                 SCHEDULED INTERVIEWS WITH MATCHED APPLICANTS");
        System.out.println("================================================================================================================");

        System.out.println("\nAvailable Schedules:");
        for (int i = 1; i <= scheduleList.getNumberOfEntries(); i++) {
            Schedule s = scheduleList.getEntry(i);
            System.out.println((i) + ". " + s.getScheduleID() + " - " + s.getScheduleDesc());
        }

        System.out.print("\nSelect a schedule to view (enter number): ");
        int schedChoice = scanner.nextInt();
        scanner.nextLine();

        if (schedChoice < 1 || schedChoice > scheduleList.getNumberOfEntries()) {
            System.out.println("Invalid choice.");
            return;
        }

        Schedule sched = scheduleList.getEntry(schedChoice);
        System.out.println("\nSchedule ID   : " + sched.getScheduleID());
        System.out.println("Schedule Time : " + sched.getScheduleTime());
        System.out.println("Description   : " + sched.getScheduleDesc());
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-10s %-25s %-20s %-12s %-25s\n", 
            "Interview ID", "Job ID", "Position", "Time", "Status", "Applicant");
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        boolean hasMatched = false;
        for (int j = 1; j <= interviewList.getNumberOfEntries(); j++) {
            Interview inter = interviewList.getEntry(j);
            if (inter.getScheduleID().equals(sched.getScheduleID())) {
                String applicantInfo = (inter.getApplicant() != null)
                    ? inter.getApplicant().getApplicantName() + " (" + inter.getApplicant().getApplicantID() + ")"
                    : "-";

                System.out.printf("%-15s %-10s %-25s %-20s %-12s %-25s\n",
                    inter.getInterviewID(),
                    inter.getJobID(),
                    inter.getInterviewPosition(),
                    inter.getInterviewDate(),
                    inter.getInterviewStatus(),
                    applicantInfo);
                hasMatched = true;
            }
        }

        if (!hasMatched) {
            System.out.println("No interviews scheduled for this session.");
        }

        System.out.println("================================================================================================================");
    }

    public void rankSuccessfulApplicants() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter minimum CGPA to qualify: ");
        double minCgpa = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("\nSort by:");
        System.out.println("1. Skill Count");
        System.out.println("2. CGPA");
        System.out.print("Enter choice: ");
        int sortChoice = scanner.nextInt();
        scanner.nextLine();

        System.out.println("\n===============================================================");
        System.out.println("                  RANKED SUCCESSFUL APPLICANTS");
        System.out.println("===============================================================");

        boolean hasAny = false;
        for (int i = 1; i <= scheduleList.getNumberOfEntries(); i++) {
            Schedule sched = scheduleList.getEntry(i);
            DoublyListInterface<Applicant> rankedApplicants = new DoublyLinkedList<>();

            for (int j = 1; j <= interviewList.getNumberOfEntries(); j++) {
                Interview inter = interviewList.getEntry(j);
                Applicant a = inter.getApplicant();

                if (a != null &&
                    inter.getScheduleID().equals(sched.getScheduleID()) &&
                    inter.getInterviewStatus().equalsIgnoreCase("Success") &&
                    a.getCgpa() >= minCgpa &&
                    !containsApplicantID(rankedApplicants, a.getApplicantID())) {
                    rankedApplicants.add(a);
                }
            }

            if (!rankedApplicants.isEmpty()) {
                hasAny = true;

                if (sortChoice == 1) {
                    // Sort by skill count
                    for (int p = 1; p <= rankedApplicants.getNumberOfEntries() - 1; p++) {
                        for (int q = 1; q <= rankedApplicants.getNumberOfEntries() - p; q++) {
                            Applicant a1 = rankedApplicants.getEntry(q);
                            Applicant a2 = rankedApplicants.getEntry(q + 1);
                            if (a1.getSkillList().getNumberOfEntries() < a2.getSkillList().getNumberOfEntries()) {
                                rankedApplicants.replace(q, a2);
                                rankedApplicants.replace(q + 1, a1);
                            }
                        }
                    }
                } else if (sortChoice == 2) {
                    // Sort by CGPA
                    for (int p = 1; p <= rankedApplicants.getNumberOfEntries() - 1; p++) {
                        for (int q = 1; q <= rankedApplicants.getNumberOfEntries() - p; q++) {
                            Applicant a1 = rankedApplicants.getEntry(q);
                            Applicant a2 = rankedApplicants.getEntry(q + 1);
                            if (a1.getCgpa() < a2.getCgpa()) {
                                rankedApplicants.replace(q, a2);
                                rankedApplicants.replace(q + 1, a1);
                            }
                        }
                    }
                }

                System.out.println();
                System.out.println("Schedule ID   : " + sched.getScheduleID());
                System.out.println("Schedule Time : " + sched.getScheduleTime());
                System.out.println("Description   : " + sched.getScheduleDesc());
                System.out.println("---------------------------------------------------------------");
                System.out.printf("%-20s %-15s %-10s\n", "Applicant Name", "Applicant ID", sortChoice == 1 ? "Skills" : "CGPA");
                System.out.println("---------------------------------------------------------------");

                for (int x = 1; x <= rankedApplicants.getNumberOfEntries(); x++) {
                    Applicant a = rankedApplicants.getEntry(x);
                    System.out.printf("%-20s %-15s %-10s\n",
                        a.getApplicantName(),
                        a.getApplicantID(),
                        sortChoice == 1 ? a.getSkillList().getNumberOfEntries() : String.format("%.2f", a.getCgpa()));
                }

                System.out.println("---------------------------------------------------------------");
            }
        }

        if (!hasAny) {
            System.out.println("No applicants match the criteria.");
        }
    }

    private boolean containsApplicantID(DoublyListInterface<Applicant> list, String applicantID) {
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            if (list.getEntry(i).getApplicantID().equalsIgnoreCase(applicantID)) {
                return true;
            }
        }
        return false;
    }

    public void generateInterviewReport() {
        System.out.println("\n================================================================================================================");
        System.out.println("                                INTERVIEW SCHEDULE & SUCCESSFUL CANDIDATES REPORT");
        System.out.println("================================================================================================================");

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE, MMM dd yyyy, hh:mm a"));
        System.out.println("Generated at: " + timestamp);
        System.out.println("\n");

        String[] positions = {"Software Engineer", "Data Analyst", "AI Developer", "Graphic Designer", "Video Editor", "Animator"};
        int[] successCounts = new int[positions.length];
        int totalSuccess = 0;

        for (int i = 1; i <= scheduleList.getNumberOfEntries(); i++) {
            Schedule sched = scheduleList.getEntry(i);
            System.out.println("Schedule ID   : " + sched.getScheduleID());
            System.out.println("Schedule Time : " + sched.getScheduleTime());
            System.out.println("Description   : " + sched.getScheduleDesc());
            System.out.println("----------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-15s %-10s %-25s %-20s %-25s\n",
                "Interview ID", "Job ID", "Position", "Time", "Applicant");
            System.out.println("----------------------------------------------------------------------------------------------------------------");

            for (int j = 1; j <= interviewList.getNumberOfEntries(); j++) {
                Interview inter = interviewList.getEntry(j);
                if (inter.getScheduleID().equals(sched.getScheduleID()) &&
                    inter.getInterviewStatus() != null &&
                    inter.getInterviewStatus().equalsIgnoreCase("Success")) {

                    String applicantInfo = (inter.getApplicant() != null)
                        ? inter.getApplicant().getApplicantName() + " (" + inter.getApplicant().getApplicantID() + ")"
                        : "-";

                    System.out.printf("%-15s %-10s %-25s %-20s %-25s\n",
                        inter.getInterviewID(),
                        inter.getJobID(),
                        inter.getInterviewPosition(),
                        inter.getInterviewDate(),
                        applicantInfo);

                    totalSuccess++;

                    for (int p = 0; p < positions.length; p++) {
                        if (positions[p].equalsIgnoreCase(inter.getInterviewPosition())) {
                            successCounts[p]++;
                            break;
                        }
                    }
                }
            }
            System.out.println();
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.println("Total Successful Interviews: " + totalSuccess);
        System.out.println("\nSuccessful Interviews by Position:");
        for (int i = 0; i < positions.length; i++) {
            System.out.printf("%-25s : %2d\n", positions[i], successCounts[i]);
        }

        System.out.println("\nSummary Chart (by Position):");
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        for (int i = 6; i >= 1; i--) {
            System.out.print(i + " | ");
            for (int j = 0; j < positions.length; j++) {
                if (successCounts[j] >= i) {
                    System.out.print("  *  ");
                } else {
                    System.out.print("     ");
                }
            }
            System.out.println();
        }
        System.out.print("    ");
        for (int j = 0; j < positions.length; j++) {
            System.out.print("-----");
        }
        System.out.println("\n     ");
        System.out.print("     ");
        for (String pos : positions) {
            System.out.print(pos.charAt(0) + "    ");
        }
        System.out.println("  --> Positions (First Letter)\n");
        // Identify most and least successful positions
        int max = 0, min = Integer.MAX_VALUE;
        String most = "", least = "";
        for (int i = 0; i < positions.length; i++) {
            if (successCounts[i] > max) {
                max = successCounts[i];
                most = positions[i];
            }
            if (successCounts[i] < min && successCounts[i] > 0) {
                min = successCounts[i];
                least = positions[i];
            }
        }

        System.out.println("Most successful position: " + most + " with " + max + " successful applicants.");
        System.out.println("Least successful position: " + least + " with " + min + " successful applicants.");
        System.out.println("");
        System.out.println("                                               END OF THE REPORT");
        System.out.println("\n");
        
    }

    public void scheduleMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n============== Schedule Menu ==============");
            System.out.println("1. Display All Schedules with Interviews");
            System.out.println("2. Add Schedule");
            System.out.println("3. Add Interview to Schedule");
            System.out.println("4. Remove Schedule");
            System.out.println("5. Remove Interview");
            System.out.println("6. Update Interview");
            System.out.println("7. Search Interview");
            System.out.println("8. Match And Schedule Applicants");
            System.out.println("9. Rank Successful Applicants");
            System.out.println("10. Generate Interview Report");
            System.out.println("11. Exit\n");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

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

                    System.out.println("\nSelect Job ID:");
                    System.out.println("1 - J1001");
                    System.out.println("2 - J1002");
                    System.out.println("3 - J1003");
                    System.out.println("4 - J1004");
                    System.out.println("5 - J1005");
                    System.out.println("6 - J1006");
                    System.out.print("Enter choice: ");
                    int jobIDChoice = scanner.nextInt();
                    scanner.nextLine();
                    
                    String jobID = "";
                    if (jobIDChoice == 1) {
                        jobID = "J1001";
                    } else if (jobIDChoice == 2) {
                        jobID = "J1002";
                    } else if (jobIDChoice == 3) {
                        jobID = "J1003";
                    } else if (jobIDChoice == 4) {
                        jobID = "J1004";
                    } else if (jobIDChoice == 5) {
                        jobID = "J1005";
                    } else if (jobIDChoice == 6) {
                        jobID = "J1006";
                    } else {
                        jobID = "Unknown";
                    }

                    // Interview Position Options
                    System.out.println("\nSelect Interview Position:");
                    System.out.println("1 - Software Engineer");
                    System.out.println("2 - Data Analyst");
                    System.out.println("3 - AI Developer");
                    System.out.println("4 - Graphic Designer");
                    System.out.println("5 - Video Editor");
                    System.out.println("6 - Animator");
                    System.out.print("Enter choice: ");
                    int posChoice = scanner.nextInt();
                    scanner.nextLine();
                    
                    String position = "";
                    if (posChoice == 1) {
                        position = "Software Engineer";
                    } else if (posChoice == 2) {
                        position = "Data Analyst";
                    } else if (posChoice == 3) {
                        position = "AI Developer";
                    } else if (posChoice == 4) {
                        position = "Graphic Designer";
                    } else if (posChoice == 5) {
                        position = "Video Editor";
                    } else if (posChoice == 6) {
                        position = "Animator";
                    } else {
                        position = "Unknown";
                    }
                    
                    // Interview Status Options
                    System.out.println("\nSelect Interview Status:");
                    System.out.println("1 - Success");
                    System.out.println("2 - Fail");
                    System.out.print("Enter choice: ");
                    int statusChoice = scanner.nextInt();
                    scanner.nextLine();
                    
                    String status = "";
                    if (statusChoice == 1) status = "Success";
                    else if (statusChoice == 2) status = "Fail";
                    else status = "Unknown";
                    
                    System.out.print("Enter new Date (e.g., 09:00AM): ");
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
                    System.out.print("Enter Interview ID to update: ");
                    String updateInterviewID = scanner.nextLine();
                    
                    System.out.println("\nSelect Job ID:");
                    System.out.println("1 - J1001");
                    System.out.println("2 - J1002");
                    System.out.println("3 - J1003");
                    System.out.println("4 - J1004");
                    System.out.println("5 - J1005");
                    System.out.println("6 - J1006");
                    System.out.print("Enter choice: ");
                    int UpdatedJobIDChoice = scanner.nextInt();
                    scanner.nextLine();
                    
                    String UpdatedJobID = "";
                    if (UpdatedJobIDChoice == 1) {
                        UpdatedJobID = "J1001";
                    } else if (UpdatedJobIDChoice == 2) {
                        UpdatedJobID = "J1002";
                    } else if (UpdatedJobIDChoice == 3) {
                        UpdatedJobID = "J1003";
                    } else if (UpdatedJobIDChoice == 4) {
                        UpdatedJobID = "J1004";
                    } else if (UpdatedJobIDChoice == 5) {
                        UpdatedJobID = "J1005";
                    } else if (UpdatedJobIDChoice == 6) {
                        UpdatedJobID = "J1006";
                    } else {
                        UpdatedJobID = "Unknown";
                    }
                    
                    // Interview Position Options
                    System.out.println("\nSelect New Interview Position:");
                    System.out.println("1 - Software Engineer");
                    System.out.println("2 - Data Analyst");
                    System.out.println("3 - AI Developer");
                    System.out.println("4 - Graphic Designer");
                    System.out.println("5 - Video Editor");
                    System.out.println("6 - Animator");
                    System.out.print("Enter choice: ");
                    int UpdatePosChoice = scanner.nextInt();
                    scanner.nextLine();
                    
                    String newPosition = "";
                    if (UpdatePosChoice == 1) {
                        newPosition = "Software Engineer";
                    } else if (UpdatePosChoice == 2) {
                        newPosition = "Data Analyst";
                    } else if (UpdatePosChoice == 3) {
                        newPosition = "AI Developer";
                    } else if (UpdatePosChoice == 4) {
                        newPosition = "Graphic Designer";
                    } else if (UpdatePosChoice == 5) {
                        newPosition = "Video Editor";
                    } else if (UpdatePosChoice == 6) {
                        newPosition = "Animator";
                    } else {
                        newPosition = "Unknown";
                    }
                    
                    // Interview Status Options
                    System.out.println("\nSelect Interview Status:");
                    System.out.println("1 - Success");
                    System.out.println("2 - Fail");
                    System.out.print("Enter choice: ");
                    int UpdateStatusChoice = scanner.nextInt();
                    scanner.nextLine();
                    
                    String newStatus = "";
                    if (UpdateStatusChoice == 1) newStatus = "Success";
                    else if (UpdateStatusChoice == 2) newStatus = "Fail";
                    else newStatus = "Unknown";
                    
                    if(updateInterview(updateInterviewID, UpdatedJobID, newPosition, newStatus)) {
                        System.out.println("Interview successfully updated.");
                    }else {
                        System.out.println("Interview not found.");
                    }
                    break;
                case 7:
                    System.out.print("Enter Interview ID to search: ");
                    String searchInterviewID = scanner.nextLine();
                    Interview foundInterview = searchInterview(searchInterviewID);
                    if (foundInterview != null) {
                        System.out.println("\nInterview Found:");
                        System.out.printf("\n%-15s %-10s %-25s %-12s %-20s\n", 
                            "Interview ID", "Job ID", "Position", "Status", "Date");
                        System.out.println("-------------------------------------------------------------------------------------");
                        System.out.printf("%-15s %-10s %-25s %-12s %-20s\n",
                            foundInterview.getInterviewID(),
                            foundInterview.getJobID(),
                            foundInterview.getInterviewPosition(),
                            foundInterview.getInterviewStatus(),
                            foundInterview.getInterviewDate());
                    } else {
                        System.out.println("Interview not found.");
                    }
                    break;
                case 8:
                    matchAndScheduleApplicants(scanner);
                    break;
                case 9:
                    rankSuccessfulApplicants();
                    break;
                case 10: 
                    generateInterviewReport();
                    break;
                case 11:
                    System.out.println("Exiting Schedule Manager...");
                    break;                    
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 11);
    }
}