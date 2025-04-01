package control;

import adt.ListInterface;
import adt.LinkedList;
import entity.Applicant;
import dao.InitialiserJava;

import java.util.Scanner;

public class ApplicantManager {
    private ListInterface<Applicant> applicantList = new LinkedList<>();

    public void initializeApplicants() {
        InitialiserJava initialiser = new InitialiserJava();
        applicantList = initialiser.initializeApplicants();
    }

    private String generateNextApplicantID() {
    if (applicantList.isEmpty()) {
        return "A001";
    } else {
        Applicant lastApplicant = (Applicant) applicantList.getLast();
        String lastID = lastApplicant.getApplicantID();
        int lastNumber = Integer.parseInt(lastID.substring(1));
        lastNumber++;
        return "A" + String.format("%03d", lastNumber);
    }
}


    public void addApplicant(String name, String phone, String email, String skills) {
        String newID = generateNextApplicantID();
        Applicant newApplicant = new Applicant(newID, name, phone, email, skills);
        applicantList.add(newApplicant);
    }

    public boolean removeApplicant(String applicantID) {
        for (int i = 0; i < applicantList.size(); i++) {
            if (applicantList.get(i).getApplicantID().equals(applicantID)) {
                applicantList.remove(applicantList.get(i));
                return true;
            }
        }
        return false;
    }

     public Applicant searchApplicant(String searchBy, String searchTerm) {
        for (int i = 0; i < applicantList.size(); i++) {
            Applicant applicant = applicantList.get(i);
            switch (searchBy) {
                case "ID":
                    if (applicant.getApplicantID().equals(searchTerm)) {
                        return applicant;
                    }
                    break;
                case "Name":
                    if (applicant.getApplicantName().equals(searchTerm)) {
                        return applicant;
                    }
                    break;
                case "Phone":
                    if (applicant.getApplicantContact().equals(searchTerm)) {
                        return applicant;
                    }
                    break;
                case "Email":
                    if (applicant.getApplicantEmail().equals(searchTerm)) {
                        return applicant;
                    }
                    break;
            }
        }
        return null;
    }

    public void displayApplicants() {
        System.out.println("\n--- All Applicants ---");
        if (applicantList.size() == 0) {
            System.out.println("No applicants available.");
        } else {
            System.out.println("_".repeat(92));
            System.out.println("_".repeat(92));
            System.out.printf(" %-10s | %-18s | %-13s | %-28s | %-8s %n", 
                "ID", "Name", "Phone", "Email", "Skills");
            System.out.println("-".repeat(92));

            for (int i = 0; i < applicantList.size(); i++) {
                Applicant applicant = applicantList.get(i);
                System.out.printf(" %-10s | %-18s | %-13s | %-28s | %-8s %n", 
                    applicant.getApplicantID(), 
                    applicant.getApplicantName(), 
                    applicant.getApplicantContact(), 
                    applicant.getApplicantEmail(), 
                    applicant.getApplicantSkill());
            }
            System.out.println("_".repeat(92));
            System.out.println("_".repeat(92));
        }
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ApplicantManager manager = new ApplicantManager();

        manager.initializeApplicants();

        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- Applicant Management ---");
            System.out.println("1. Display All Applicants");
            System.out.println("2. Add Applicant");
            System.out.println("3. Remove Applicant");
            System.out.println("4. Search Applicant");
            System.out.println("5. Exit");
            System.out.println("-".repeat(30));
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) {
                case 1:
                    manager.displayApplicants();
                    break;

                case 2:
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Phone Number: ");
                    String phone = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter Skills: ");
                    String skills = scanner.nextLine();

                    manager.addApplicant(name, phone, email, skills);
                    System.out.println("Applicant added successfully.");
                    break;

                case 3:
                    System.out.println("-".repeat(30));
                    System.out.print("Enter Applicant ID to remove: ");
                    String removeID = scanner.nextLine();
                    if (manager.removeApplicant(removeID)) {
                        System.out.println("Applicant removed successfully.");
                    } else {
                        System.out.println("Applicant not found.");
                    }
                    break;

                case 4:
                    boolean backToSearch = false;
                    while (!backToSearch) {
                        System.out.println("\n--- Search Options ---");
                        System.out.println("1. Search by ID");
                        System.out.println("2. Search by Name");
                        System.out.println("3. Search by Phone");
                        System.out.println("4. Search by Email");
                        System.out.println("5. Back to Main Menu");
                        System.out.println("-".repeat(30));
                        System.out.print("Choose a search option: ");
                        int searchChoice = scanner.nextInt();
                        scanner.nextLine();  

                        String searchBy = "";
                        switch (searchChoice) {
                            case 1:
                                searchBy = "ID";
                                break;
                            case 2:
                                searchBy = "Name";
                                break;
                            case 3:
                                searchBy = "Phone";
                                break;
                            case 4:
                                searchBy = "Email";
                                break;
                            case 5:
                                backToSearch = true;
                                continue;
                            default:
                                System.out.println("Invalid option. Please try again.");
                                continue;
                        }

                        if (!searchBy.equals("")) {
                            System.out.print("Enter " + searchBy + " to search: ");
                            String searchTerm = scanner.nextLine();
                            Applicant foundApplicant = manager.searchApplicant(searchBy, searchTerm);
                            if (foundApplicant != null) {
                                System.out.println("\n");
                                System.out.println("*".repeat(30));
                                System.out.println("Found: ");
                                System.out.println("-".repeat(30));
                                System.out.println(foundApplicant);
                                System.out.println("*".repeat(30));
                            } else {
                                System.out.println("*".repeat(30));
                                System.out.println("Applicant not found.");
                                System.out.println("*".repeat(30));
                            }
                        }
                    }
                    break;


                case 5:
                    exit = true;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

    }
}
