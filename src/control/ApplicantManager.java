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
    private DoublyListInterface<Skill> skillList;
    private DoublyListInterface<JobDesired> jobDesiredList;

    public void initializeData() {
        InitialiserJava initializer = new InitialiserJava();
        initializer.initializeData();

        applicantList = initializer.getApplicantList();
        skillList = initializer.getSkillList();
        jobDesiredList = initializer.getJobDesiredList();
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
    
    public void showAllApplicants() {
        if (applicantList.isEmpty()) {
            System.out.println("No applicants found.");
            return;
        }

        System.out.println("=".repeat(110));
        System.out.printf("%-6s | %-20s | %-12s | %-30s | %-15s | %-4s\n", 
                  "ID", "Name", "Contact", "Email", "Address", "CGPA");
        System.out.println("-".repeat(110));

        for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
            Applicant a = applicantList.getEntry(i);

            System.out.printf("%-6s | %-20s | %-12s | %-30s | %-15s | %-4.2f\n",
            a.getApplicantID(), a.getApplicantName(), a.getApplicantContact(), a.getApplicantEmail(), a.getApplicantAddress(), a.getCgpa());

            System.out.print("     Skills: ");
            for (int j = 1; j <= a.getSkillList().getNumberOfEntries(); j++) {
                Skill s = a.getSkillList().getEntry(j);
                System.out.print(s.getSkillName() + " (" + s.getProficiency() + ")");
                if (j < a.getSkillList().getNumberOfEntries()) System.out.print(", ");
            }
            System.out.println();

            System.out.print("     Desired Jobs: ");
            for (int j = 1; j <= a.getJobDesiredList().getNumberOfEntries(); j++) {
                JobDesired jd = a.getJobDesiredList().getEntry(j);
                System.out.print(jd.getPosition());
                if (j < a.getJobDesiredList().getNumberOfEntries()) System.out.print(", ");
            }
            System.out.println();

            System.out.println("-".repeat(110));
        }
        
    } // showAllApplicants - done

    public void addApplicant() {
        Scanner scanner = new Scanner(System.in);

        String id = generateNextApplicantID();
        System.out.println("Generated Applicant ID: " + id);

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Contact: ");
        String contact = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        
        String[] states = {"Selangor", "Johor", "Kuala Lumpur", "Penang", "Sarawak"};
        System.out.println("\nAvailable Addresses:");
        for (int i = 0; i < states.length; i++) {
            System.out.printf("%d. %s\n", i + 1, states[i]);
        }
        System.out.print("Select address number: ");
        int addrChoice = scanner.nextInt();
        scanner.nextLine(); 
        String address = states[addrChoice - 1];

        System.out.print("Enter CGPA: ");
        double cgpa = scanner.nextDouble();
        scanner.nextLine(); 
        
        

        // Show Available Skills
        System.out.println("\nAvailable Skills:");
        System.out.printf("%-5s | %-20s | %-12s\n", "No.", "Skill Name", "Proficiency");
        System.out.println("-----------------------------------------------");

        for (int i = 1; i <= skillList.getNumberOfEntries(); i++) {
            Skill s = (Skill) skillList.getEntry(i);
            System.out.printf("%-5d | %-20s | %-12s\n", i, s.getSkillName(), s.getProficiency());
        }

        System.out.println("-----------------------------------------------");


        System.out.print("Enter skill numbers: ");
        String[] skillIndexes = scanner.nextLine().trim().split("\\s+");
        DoublyLinkedList<Skill> selectedSkills = new DoublyLinkedList<>();
        for (String index : skillIndexes) {
            int idx = Integer.parseInt(index.trim());
            if (idx >= 1 && idx <= skillList.getNumberOfEntries()) {
                selectedSkills.add((Skill) skillList.getEntry(idx));
            } else {
                System.out.println("Invalid skill index: " + idx);
            }
        }

        System.out.println("\nAvailable Job Positions:");
        System.out.printf("%-5s | %-25s | %-15s\n", "No.", "Position", "Category");
        System.out.println("------------------------------------------------------------");

        for (int i = 1; i <= jobDesiredList.getNumberOfEntries(); i++) {
            JobDesired j = (JobDesired) jobDesiredList.getEntry(i);
            System.out.printf("%-5d | %-25s | %-15s\n", i, j.getPosition(), j.getCategory());
        }

        System.out.println("------------------------------------------------------------");


        System.out.print("Enter job numbers: ");
        String[] jobIndexes = scanner.nextLine().trim().split("\\s+");
        DoublyLinkedList<JobDesired> selectedJobs = new DoublyLinkedList<>();
        for (String index : jobIndexes) {
            int idx = Integer.parseInt(index.trim());
            if (idx >= 1 && idx <= jobDesiredList.getNumberOfEntries()) {
                selectedJobs.add((JobDesired) jobDesiredList.getEntry(idx));
            } else {
                System.out.println("Invalid job index: " + idx);
            }
        }

        // Create and add the new applicant
        Applicant applicant = new Applicant(id, name, contact, email, address, cgpa, selectedSkills, selectedJobs);
        applicantList.add(applicant);

        System.out.println("\nApplicant added successfully.");
        
    } // addApplicant - done

    public void removeApplicant() {
        if (applicantList.isEmpty()) {
            System.out.println("No applicants to remove.");
            return;
        }

        // Display all applicants
        System.out.println("\n========== Applicant List ==========");
        System.out.printf("%-6s | %-20s\n", "ID", "Name");
        System.out.println("-------------------------------");

        for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
            Applicant a = applicantList.getEntry(i);
            System.out.printf("%-6s | %-20s\n", a.getApplicantID(), a.getApplicantName());
        }

        System.out.println("-------------------------------");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Applicant ID to remove: ");
        String id = scanner.nextLine().trim();

        boolean found = false;
        for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
            Applicant a = applicantList.getEntry(i);
            if (a.getApplicantID().equalsIgnoreCase(id)) {
                applicantList.remove(i);
                System.out.println("Applicant " + id + " removed successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No applicant found with ID: " + id);
        }
        
    } // removeApplicant - done

    public void updateApplicant() {
        if (applicantList.isEmpty()) {
            System.out.println("No applicants to update.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Applicant ID to update: ");
        String id = scanner.nextLine().trim();

        Applicant target = null;
        int targetIndex = -1;

        for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
            Applicant a = applicantList.getEntry(i);
            if (a.getApplicantID().equalsIgnoreCase(id)) {
                target = a;
                targetIndex = i;
                break;
            }
        }

        if (target == null) {
            System.out.println("No applicant found with ID: " + id);
            return;
        }

        boolean done = false;
        while (!done) {
            System.out.println("\n--- Update Menu for " + target.getApplicantName() + " ---");
            System.out.println("1. Name");
            System.out.println("2. Contact");
            System.out.println("3. Email");
            System.out.println("4. Address");
            System.out.println("5. CGPA");
            System.out.println("6. Skills");
            System.out.println("7. Job Desired");
            System.out.println("8. Done");
            System.out.print("Select a field to update: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter new name: ");
                    target.setApplicantName(scanner.nextLine().trim());
                    break;

                case "2":
                    System.out.print("Enter new contact: ");
                    target.setApplicantContact(scanner.nextLine().trim());
                    break;

                case "3":
                    System.out.print("Enter new email: ");
                    target.setApplicantEmail(scanner.nextLine().trim());
                    break;
                case "4":
                    System.out.print("Enter new address: ");
                    target.setApplicantAddress(scanner.nextLine().trim());
                    break;

                case "5":
                    System.out.print("Enter new CGPA: ");
                    try {
                        double newCgpa = Double.parseDouble(scanner.nextLine().trim());
                        target.setCgpa(newCgpa);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid CGPA input.");
                    }
                    break;

                case "6":
                    DoublyLinkedList<Skill> updatedSkills = new DoublyLinkedList<>();
                    System.out.println("\nAvailable Skills:");
                    for (int i = 1; i <= skillList.getNumberOfEntries(); i++) {
                        Skill s = (Skill) skillList.getEntry(i);
                        System.out.printf("%2d. %-20s (%s)\n", i, s.getSkillName(), s.getProficiency());
                    }
                    System.out.print("Enter skill numbers (e.g. 1 3 5): ");
                    String[] skillIndexes = scanner.nextLine().trim().split("\\s+");
                    for (String index : skillIndexes) {
                        int idx = Integer.parseInt(index.trim());
                        if (idx >= 1 && idx <= skillList.getNumberOfEntries()) {
                            updatedSkills.add((Skill) skillList.getEntry(idx));
                        }
                    }
                    target.setSkillList(updatedSkills);
                    break;

                case "7":
                    DoublyLinkedList<JobDesired> updatedJobs = new DoublyLinkedList<>();
                    System.out.println("\nAvailable Job Positions:");
                    for (int i = 1; i <= jobDesiredList.getNumberOfEntries(); i++) {
                        JobDesired j = (JobDesired) jobDesiredList.getEntry(i);
                        System.out.printf("%2d. %-25s (%s)\n", i, j.getPosition(), j.getCategory());
                    }
                    System.out.print("Enter job numbers (e.g. 2 5): ");
                    String[] jobIndexes = scanner.nextLine().trim().split("\\s+");
                    for (String index : jobIndexes) {
                        int idx = Integer.parseInt(index.trim());
                        if (idx >= 1 && idx <= jobDesiredList.getNumberOfEntries()) {
                            updatedJobs.add((JobDesired) jobDesiredList.getEntry(idx));
                        }
                    }
                    target.setJobDesiredList(updatedJobs);
                    break;

                case "8":
                    done = true;
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        applicantList.replace(targetIndex, target);
        System.out.println("\nApplicant updated successfully.");
    } // updateApplicant - done

    public void filterApplicants() {
        if (applicantList.isEmpty()) {
            System.out.println("No applicants available.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Filter Applicants ---");
            System.out.println("1. By Desired Job Position");
            System.out.println("2. By Skill Name");
            System.out.println("3. By Skill + Proficiency");
            System.out.println("4. By CGPA (Min)");
            System.out.println("5. By Job Category");
            System.out.println("6. By Number of Skills ≥ X");
            System.out.println("7. By Address");
            System.out.println("8. Back");
            System.out.print("Choose filter type: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": {
                    System.out.println("\nAvailable Job Positions:");
                    for (int i = 1; i <= jobDesiredList.getNumberOfEntries(); i++) {
                        JobDesired j = jobDesiredList.getEntry(i);
                        System.out.printf("%2d. %s\n", i, j.getPosition());
                    }
                    System.out.print("Select job number: ");
                    int pos = Integer.parseInt(scanner.nextLine());
                    if (pos < 1 || pos > jobDesiredList.getNumberOfEntries()) {
                        System.out.println("Invalid selection.");
                        break;
                    }

                    String selectedJob = jobDesiredList.getEntry(pos).getPosition();
                    printMatchingHeader();
                    for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
                        Applicant a = applicantList.getEntry(i);
                        if (containsJob(a, selectedJob)) {
                            printApplicantDetails(a);
                        }
                    }
                    break;
                }

                case "2": {
                    System.out.println("\nAvailable Skills:");
                    for (int i = 1; i <= skillList.getNumberOfEntries(); i++) {
                        Skill s = skillList.getEntry(i);
                        System.out.printf("%2d. %-20s (%s)\n", i, s.getSkillName(), s.getProficiency());
                    }
                    System.out.print("Select skill number: ");
                    int index = Integer.parseInt(scanner.nextLine());
                    if (index < 1 || index > skillList.getNumberOfEntries()) {
                        System.out.println("Invalid selection.");
                        break;
                    }
                    String selectedSkill = skillList.getEntry(index).getSkillName();

                    printMatchingHeader();
                    for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
                        Applicant a = applicantList.getEntry(i);
                        if (containsSkill(a, selectedSkill, null)) {
                            printApplicantDetails(a);
                        }
                    }
                    break;
                }

                case "3": {
                    System.out.println("\nAvailable Skills:");
                    for (int i = 1; i <= skillList.getNumberOfEntries(); i++) {
                        Skill s = skillList.getEntry(i);
                        System.out.printf("%2d. %-20s (%s)\n", i, s.getSkillName(), s.getProficiency());
                    }
                    System.out.print("Select skill number: ");
                    int index = Integer.parseInt(scanner.nextLine());
                    if (index < 1 || index > skillList.getNumberOfEntries()) {
                        System.out.println("Invalid selection.");
                        break;
                    }
                    Skill selected = skillList.getEntry(index);

                    printMatchingHeader();
                    for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
                        Applicant a = applicantList.getEntry(i);
                        if (containsSkill(a, selected.getSkillName(), selected.getProficiency())) {
                            printApplicantDetails(a);
                        }
                    }
                    break;
                }

                case "4": {
                    System.out.print("Enter minimum CGPA: ");
                    double minCgpa = Double.parseDouble(scanner.nextLine());

                    printMatchingHeader();
                    for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
                        Applicant a = applicantList.getEntry(i);
                        if (a.getCgpa() >= minCgpa) {
                            printApplicantDetails(a);
                        }
                    }
                    break;
                }

                case "5": {
                    DoublyLinkedList<String> categories = new DoublyLinkedList<>();
                    for (int i = 1; i <= jobDesiredList.getNumberOfEntries(); i++) {
                        String cat = jobDesiredList.getEntry(i).getCategory();
                        boolean exists = false;
                        for (int j = 1; j <= categories.getNumberOfEntries(); j++) {
                            if (categories.getEntry(j).equalsIgnoreCase(cat)) {
                                exists = true;
                                break;
                            }
                        }
                        if (!exists) categories.add(cat);
                    }

                    System.out.println("\nAvailable Categories:");
                    for (int i = 1; i <= categories.getNumberOfEntries(); i++) {
                        System.out.printf("%2d. %s\n", i, categories.getEntry(i));
                    }
                    System.out.print("Select category number: ");
                    int catIndex = Integer.parseInt(scanner.nextLine());
                    if (catIndex < 1 || catIndex > categories.getNumberOfEntries()) {
                        System.out.println("Invalid selection.");
                        break;
                    }

                    String selectedCat = categories.getEntry(catIndex);
                    printMatchingHeader();
                    for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
                        Applicant a = applicantList.getEntry(i);
                        if (containsCategory(a, selectedCat)) {
                            printApplicantDetails(a);
                        }
                    }
                    break;
                }

                case "6": {
                    System.out.print("Enter minimum number of skills: ");
                    int min = Integer.parseInt(scanner.nextLine());

                    printMatchingHeader();
                    for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
                        Applicant a = applicantList.getEntry(i);
                        if (a.getSkillList().getNumberOfEntries() >= min) {
                            printApplicantDetails(a);
                        }
                    }
                    break;
                }
                case "7": {
                    DoublyLinkedList<String> addressList = new DoublyLinkedList<>();
                    for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
                        String addr = applicantList.getEntry(i).getApplicantAddress();
                        boolean exists = false;
                        for (int j = 1; j <= addressList.getNumberOfEntries(); j++) {
                            if (addressList.getEntry(j).equalsIgnoreCase(addr)) {
                                exists = true;
                                break;
                            }
                        }
                        if (!exists) {
                            addressList.add(addr);
                        }
                    }

                    System.out.println("\nAvailable Locations:");
                    for (int i = 1; i <= addressList.getNumberOfEntries(); i++) {
                        System.out.printf("%2d. %s\n", i, addressList.getEntry(i));
                    }

                    System.out.print("Select address number: ");
                    int addrChoice = Integer.parseInt(scanner.nextLine().trim());

                    if (addrChoice < 1 || addrChoice > addressList.getNumberOfEntries()) {
                        System.out.println("Invalid selection.");
                        break;
                    }

                    String selectedAddr = addressList.getEntry(addrChoice);

                    printMatchingHeader();
                    for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
                        Applicant a = applicantList.getEntry(i);
                        if (a.getApplicantAddress().equalsIgnoreCase(selectedAddr)) {
                            printApplicantDetails(a);
                        }
                    }
                    break;
                }

                case "8":
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void printMatchingHeader() {
        System.out.println("=".repeat(130));
        System.out.printf("%-6s | %-20s | %-12s | %-30s | %-15s | %-4s\n", 
                  "ID", "Name", "Contact", "Email", "Address", "CGPA");
        System.out.println("-".repeat(130));
    }

    private void printApplicantDetails(Applicant a) {
        System.out.printf("%-6s | %-20s | %-12s | %-30s | %-4.2f | %-12s\n",
            a.getApplicantID(), a.getApplicantName(), a.getApplicantContact(), 
            a.getApplicantEmail(), a.getCgpa(), a.getApplicantAddress());

        System.out.print("     Skills: ");
        for (int j = 1; j <= a.getSkillList().getNumberOfEntries(); j++) {
            Skill s = a.getSkillList().getEntry(j);
            System.out.print(s.getSkillName() + " (" + s.getProficiency() + ")");
            if (j < a.getSkillList().getNumberOfEntries()) System.out.print(", ");
        }
        System.out.println();

        System.out.print("     Desired Jobs: ");
        for (int j = 1; j <= a.getJobDesiredList().getNumberOfEntries(); j++) {
            JobDesired jd = a.getJobDesiredList().getEntry(j);
            System.out.print(jd.getPosition());
            if (j < a.getJobDesiredList().getNumberOfEntries()) System.out.print(", ");
        }
        System.out.println();
        System.out.println("-".repeat(130));
    }


    private boolean containsJob(Applicant a, String job) {
        for (int i = 1; i <= a.getJobDesiredList().getNumberOfEntries(); i++) {
            if (a.getJobDesiredList().getEntry(i).getPosition().equalsIgnoreCase(job)) return true;
        }
        return false;
    }

    private boolean containsSkill(Applicant a, String name, String proficiency) {
        for (int i = 1; i <= a.getSkillList().getNumberOfEntries(); i++) {
            Skill s = a.getSkillList().getEntry(i);
            if (s.getSkillName().equalsIgnoreCase(name)) {
                if (proficiency == null || s.getProficiency().equalsIgnoreCase(proficiency)) return true;
            }
        }
        return false;
    }

    private boolean containsCategory(Applicant a, String category) {
        for (int i = 1; i <= a.getJobDesiredList().getNumberOfEntries(); i++) {
            if (a.getJobDesiredList().getEntry(i).getCategory().equalsIgnoreCase(category)) return true;
        }
        return false;
    }






    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ApplicantManager manager = new ApplicantManager();
        manager.initializeData(); 

        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- Applicant Management ---");
            System.out.println("1. Display All Applicants");
            System.out.println("2. Add Applicant");
            System.out.println("3. Remove Applicant");
            System.out.println("4. Update Applicant"); 
            System.out.println("5. Filter Applicant");
            System.out.println("6. Exit");
            System.out.println("-".repeat(30));
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) {
                case 1:
                    manager.showAllApplicants();
                    break;

                case 2:
                    manager.addApplicant();
                    break;

                case 3:
                    manager.removeApplicant();
                    break;

                case 4:
                    manager.updateApplicant();  
                    break;
                case 5:
                    manager.filterApplicants();
                    break;
                case 6:
                    exit = true;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    
} // applicant manager
