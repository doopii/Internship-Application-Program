package control;

import adt.DoublyLinkedList;
import adt.DoublyListInterface;
import entity.Applicant;
import entity.Skill;
import entity.JobDesired;
import dao.InitialiserJava;

import java.util.Scanner;

public class ApplicantManager {
    private DoublyListInterface<Applicant> applicantList = new DoublyLinkedList<>();

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


    public void addApplicant() {
        Scanner scanner = new Scanner(System.in);
        String name, contact, email, categoryInput, skillNameInput, proficiencyInput, jobCategoryInput, jobPositionInput;
        double cgpaInput;

        // Collecting applicant details
        System.out.print("Enter Name: ");
        name = scanner.nextLine();

        // Ensure contact is unique
        while (true) {
            System.out.print("Enter Contact Number: ");
            contact = scanner.nextLine();

            boolean duplicateFound = false;
            for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
                if (applicantList.getEntry(i).getApplicantContact().equals(contact)) {
                    System.out.println("Error: Contact number is already registered. Try again.");
                    duplicateFound = true;
                    break;
                }
            }
            if (!duplicateFound) {
                break;
            }
        }

        System.out.print("Enter Email: ");
        email = scanner.nextLine();

        // Handling skills as individual inputs
        DoublyLinkedList<Skill> skills = new DoublyLinkedList<>();
        System.out.println("Enter Skills (enter 'ok' when finished): ");
        while (true) {
            System.out.print("Enter Skill Category: ");
            categoryInput = scanner.nextLine();

            if (categoryInput.equalsIgnoreCase("ok")) {
                break;
            }

            System.out.print("Enter Skill Name: ");
            skillNameInput = scanner.nextLine();

            System.out.print("Enter Proficiency Level: ");
            proficiencyInput = scanner.nextLine();

            System.out.print("Enter CGPA: ");
            cgpaInput = scanner.nextDouble();
            scanner.nextLine();  // Consume the remaining newline

            Skill skill = new Skill(categoryInput, skillNameInput, proficiencyInput, cgpaInput);
            skills.add(skill);
        }

        // Handling job desires as individual inputs
        DoublyLinkedList<JobDesired> jobDesired = new DoublyLinkedList<>();
        System.out.println("Enter Job Desired (enter 'done' when finished): ");
        while (true) {
            System.out.print("Enter Job Category: ");
            jobCategoryInput = scanner.nextLine();

            if (jobCategoryInput.equalsIgnoreCase("done")) {
                break;
            }

            System.out.print("Enter Job Position: ");
            jobPositionInput = scanner.nextLine();

            JobDesired job = new JobDesired(jobCategoryInput, jobPositionInput);
            jobDesired.add(job);
        }

        String newID = generateNextApplicantID();
        Applicant newApplicant = new Applicant(newID, name, contact, email, skills, jobDesired);

        applicantList.add(newApplicant);
        System.out.println("Applicant added successfully.");
    }



    public void removeApplicants() {
    
    } // removeApplicants 
    
    public void displayApplicants() {
    
    } // displayApplicants 




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
            System.out.println("4. Filter Applicant");
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
                    manager.addApplicant();
                    break;

                case 3:
                    System.out.println("-".repeat(30));
                    System.out.print("Enter Applicant ID to remove: ");
                    String removeID = scanner.nextLine();
                    

                case 4:
                    boolean backToSearch = false;
                    while (!backToSearch) {
                        System.out.println("\n--- Filter Options ---");
                        System.out.println("1. Search by ID");
                        System.out.println("2. Search by Name");
                        System.out.println("3. Search by Contact");
                        System.out.println("4. Search by Email");
                        System.out.println("5. Back to Main Menu");
                        System.out.println("-".repeat(30));
                        System.out.print("Choose a search option: ");
                        int searchChoice = scanner.nextInt();
                        scanner.nextLine();  

                       
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
