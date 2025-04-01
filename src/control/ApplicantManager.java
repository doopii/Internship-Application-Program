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

    public Applicant searchApplicant(String applicantID) {
        for (int i = 0; i < applicantList.size(); i++) {
            if (applicantList.get(i).getApplicantID().equals(applicantID)) {
                return applicantList.get(i);
            }
        }
        return null;
    }


    public void displayApplicants() {
       System.out.println("\n--- All Applicants ---");
       if (applicantList.size() == 0) {
           System.out.println("No applicants available.");
       } else {
           System.out.printf("%-12s %-20s %-15s %-30s %-10s%n", "ID", "Name", "Phone", "Email", "Skills");
           System.out.println("-".repeat(87));

           for (int i = 0; i < applicantList.size(); i++) {
               Applicant applicant = applicantList.get(i);
               System.out.printf("%-12s %-20s %-15s %-30s %-10s%n", 
                   applicant.getApplicantID(), 
                   applicant.getApplicantName(), 
                   applicant.getApplicantContact(), 
                   applicant.getApplicantEmail(), 
                   applicant.getApplicantSkill());
           }
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
                    System.out.print("Enter Applicant ID to remove: ");
                    String removeID = scanner.nextLine();
                    if (manager.removeApplicant(removeID)) {
                        System.out.println("Applicant removed successfully.");
                    } else {
                        System.out.println("Applicant not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter Applicant ID to search: ");
                    String searchID = scanner.nextLine();
                    Applicant foundApplicant = manager.searchApplicant(searchID);
                    if (foundApplicant != null) {
                        System.out.println("Found: " + foundApplicant);
                    } else {
                        System.out.println("Applicant not found.");
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
