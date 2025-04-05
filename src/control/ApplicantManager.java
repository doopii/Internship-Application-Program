package control;

import adt.DoublyLinkedList;
import adt.DoublyListInterface;
import entity.Applicant;
import entity.Skill;
import entity.JobDesired;
import dao.InitialiserJava;
import utility.BubbleSort;

import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;

// Covered DLL 
    //add() – To add applicants, skills, and job entries.
    //getEntry(int index) – To access data for display, filtering, sorting, updating.
    //remove(int index) – To delete applicants.
    //replace(int index, T newEntry) – To update applicant details.
    //getNumberOfEntries() – To loop through the list.
    //isEmpty() – To check if lists are empty before performing actions.
    //getLast() – To get the last applicant for ID generation.
    //clone() – To make a copy of the list for filtering without changing the original.
    //contains(value) – To avoid duplicates in skill and job category selections.
    //swap(index1, index2) – Used in sorting (BubbleSort).

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
    
    public void sortApplicantsMenu() {
        if (applicantList.isEmpty()) {
            System.out.println("No applicants to sort.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Sort Applicants ---");
        System.out.println("1. Sort by ID");
        System.out.println("2. Sort by Name");
        System.out.println("3. Sort by CGPA");
        System.out.print("Choose sorting option: ");
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                BubbleSort.bubbleSort(applicantList, "id");
                System.out.println("Sorted by ID.");
                break;
            case "2":
                BubbleSort.bubbleSort(applicantList, "name");
                System.out.println("Sorted by Name.");
                break;
            case "3":
                BubbleSort.bubbleSort(applicantList, "cgpa");
                System.out.println("Sorted by CGPA.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
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
        int addrChoice = Integer.parseInt(scanner.nextLine());
        String address = states[addrChoice - 1];

        System.out.print("Enter CGPA: ");
        double cgpa = Double.parseDouble(scanner.nextLine());

        // ========== Add Skills ==========
        DoublyLinkedList<Skill> selectedSkills = new DoublyLinkedList<>();
        boolean addMoreSkill = true;

        while (addMoreSkill) {
            // Step 1: Choose skill category
            DoublyLinkedList<String> categories = new DoublyLinkedList<>();
            for (int i = 1; i <= skillList.getNumberOfEntries(); i++) {
                String cat = skillList.getEntry(i).getCategory();
                if (!categories.contains(cat)) categories.add(cat);
            }

            System.out.println("\nAvailable Skill Categories:");
            for (int i = 1; i <= categories.getNumberOfEntries(); i++) {
                System.out.printf("%d. %s\n", i, categories.getEntry(i));
            }
            System.out.print("Choose skill category number: ");
            int catIndex = Integer.parseInt(scanner.nextLine());
            String selectedCat = categories.getEntry(catIndex);

            // Step 2: Choose skill name
            DoublyLinkedList<String> skillNames = new DoublyLinkedList<>();
            for (int i = 1; i <= skillList.getNumberOfEntries(); i++) {
                Skill s = skillList.getEntry(i);
                if (s.getCategory().equalsIgnoreCase(selectedCat)) {
                    if (!skillNames.contains(s.getSkillName())) {
                        skillNames.add(s.getSkillName());
                    }
                }
            }

            System.out.println("\nAvailable Skill Names in " + selectedCat + ":");
            for (int i = 1; i <= skillNames.getNumberOfEntries(); i++) {
                System.out.printf("%d. %s\n", i, skillNames.getEntry(i));
            }
            System.out.print("Choose skill name number: ");
            int skillIndex = Integer.parseInt(scanner.nextLine());
            String selectedSkill = skillNames.getEntry(skillIndex);

            // Step 3: Choose proficiency
            System.out.println("\nAvailable Proficiency:");
            System.out.println("1. Beginner");
            System.out.println("2. Intermediate");
            System.out.println("3. Advanced");
            System.out.print("Choose proficiency number: ");
            int profChoice = Integer.parseInt(scanner.nextLine());
            
            String proficiency = "";
            switch (profChoice) {
                case 1:
                    proficiency = "Beginner";
                    break;
                case 2:
                    proficiency = "Intermediate";
                    break;
                case 3:
                    proficiency = "Advanced";
                    break;
                default:
                    System.out.println("Invalid proficiency. Skipping this skill.");
                    continue;
            }


            selectedSkills.add(new Skill(selectedSkill, selectedCat, proficiency));

            System.out.print("Add more skills? (1 = Yes, 2 = No): ");
            String moreSkill = scanner.nextLine();
            addMoreSkill = moreSkill.equals("1");
        }

        // ========== Add Desired Jobs ==========
        DoublyLinkedList<JobDesired> selectedJobs = new DoublyLinkedList<>();
        boolean addMoreJob = true;

        while (addMoreJob) {
            // Step 1: Choose job category
            DoublyLinkedList<String> jobCats = new DoublyLinkedList<>();
            for (int i = 1; i <= jobDesiredList.getNumberOfEntries(); i++) {
                String cat = jobDesiredList.getEntry(i).getCategory();
                if (!jobCats.contains(cat)) jobCats.add(cat);
            }

            System.out.println("\nAvailable Job Categories:");
            for (int i = 1; i <= jobCats.getNumberOfEntries(); i++) {
                System.out.printf("%d. %s\n", i, jobCats.getEntry(i));
            }
            System.out.print("Choose job category number: ");
            int catIndex = Integer.parseInt(scanner.nextLine());
            String selectedCat = jobCats.getEntry(catIndex);

            // Step 2: Show jobs under that category
            DoublyLinkedList<JobDesired> jobsInCat = new DoublyLinkedList<>();
            for (int i = 1; i <= jobDesiredList.getNumberOfEntries(); i++) {
                JobDesired jd = jobDesiredList.getEntry(i);
                if (jd.getCategory().equalsIgnoreCase(selectedCat)) {
                    jobsInCat.add(jd);
                }
            }

            System.out.println("\nAvailable Positions in " + selectedCat + ":");
            for (int i = 1; i <= jobsInCat.getNumberOfEntries(); i++) {
                System.out.printf("%d. %s\n", i, jobsInCat.getEntry(i).getPosition());
            }
            System.out.print("Choose job position number: ");
            int posIndex = Integer.parseInt(scanner.nextLine());
            selectedJobs.add(jobsInCat.getEntry(posIndex));

            System.out.print("Add more desired jobs? (1 = Yes, 2 = No): ");
            String moreJob = scanner.nextLine();
            addMoreJob = moreJob.equals("1");
        }

        // Finalize Applicant
        Applicant applicant = new Applicant(id, name, contact, email, address, cgpa, selectedSkills, selectedJobs);
        applicantList.add(applicant);
        System.out.println("\nApplicant added successfully.");
    } // addApplicant - done

    public void removeApplicant() {
        if (applicantList.isEmpty()) {
            System.out.println("No applicants to remove.");
            return;
        }

        System.out.println("\n========== Applicant List ==========");
        System.out.println("=".repeat(110));
        System.out.printf("%-6s | %-20s | %-12s | %-30s | %-15s | %-4s\n", 
                  "ID", "Name", "Contact", "Email", "Address", "CGPA");
        System.out.println("-".repeat(110));

        for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
            Applicant a = applicantList.getEntry(i);
            System.out.printf("%-6s | %-20s | %-12s | %-30s | %-15s | %-4.2f\n",
                a.getApplicantID(), a.getApplicantName(), a.getApplicantContact(), 
                a.getApplicantEmail(), a.getApplicantAddress(), a.getCgpa());

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
    }  // removeApplicant - donw

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
        DoublyListInterface<Applicant> filteredList = applicantList.clone();
        DoublyLinkedList<String> activeFilters = new DoublyLinkedList<>();
        boolean doneFiltering = false;

        while (!doneFiltering) {
            System.out.println("\n==== Filter Applicants ====");
            System.out.print("Current Filters: ");
            if (activeFilters.isEmpty()) {
                System.out.println("[None]");
            } else {
                System.out.print("[");
                for (int i = 1; i <= activeFilters.getNumberOfEntries(); i++) {
                    System.out.print(activeFilters.getEntry(i));
                    if (i < activeFilters.getNumberOfEntries()) System.out.print(", ");
                }
                System.out.println("]");
            }

            System.out.println("\n1. Filter by Job Category and Position");
            System.out.println("2. Filter by Skill Category, Name, and Proficiency");
            System.out.println("3. Filter by Minimum CGPA");
            System.out.println("4. Filter by Address");
            System.out.println("5. Filter by Skill Count in a Specific Category");
            System.out.println("\n6. View Matching Applicants");
            System.out.println("7. Reset All Filters");
            System.out.println("8. Back");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    filteredList = filterByJobCategoryAndPosition(filteredList, activeFilters, scanner);
                    break;
                case "2":
                    filteredList = filterBySkillHierarchy(filteredList, activeFilters, scanner);
                    break;
                case "3":
                    System.out.print("Enter minimum CGPA: ");
                    double minCgpa = Double.parseDouble(scanner.nextLine());
                    filteredList = filterByCGPA(filteredList, activeFilters, minCgpa);
                    break;
                case "4":
                    filteredList = filterByAddress(filteredList, activeFilters, scanner);
                    break;
                case "5":
                    filteredList = filterBySkillCountOnlyInCategory(filteredList, scanner, activeFilters);
                    break;
                case "6":
                    if (filteredList.isEmpty()) {
                        System.out.println("No matching applicants.");
                    } else {
                        printMatchingHeader();
                        for (int i = 1; i <= filteredList.getNumberOfEntries(); i++) {
                            printApplicantDetails(filteredList.getEntry(i));
                        }
                    }
                    break;
                case "7":
                    filteredList = applicantList.clone();
                    activeFilters.clear();
                    System.out.println("All filters reset.");
                    break;
                case "8":
                    doneFiltering = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    } // filterApplicants - done

    private DoublyListInterface<Applicant> filterByCGPA(DoublyListInterface<Applicant> list, DoublyLinkedList<String> activeFilters, double minCgpa) {
        DoublyLinkedList<Applicant> filtered = new DoublyLinkedList<>();
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            Applicant a = list.getEntry(i);
            if (a.getCgpa() >= minCgpa) {
                filtered.add(a);
            }
        }
        activeFilters.add("CGPA >= " + minCgpa);
        return filtered;
    } // filterByCGPA

    private DoublyListInterface<Applicant> filterByAddress(DoublyListInterface<Applicant> list, DoublyLinkedList<String> activeFilters, Scanner scanner) {
        DoublyLinkedList<String> addresses = new DoublyLinkedList<>();
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            String addr = list.getEntry(i).getApplicantAddress();
            if (!addresses.contains(addr)) addresses.add(addr);
        }

        System.out.println("\nAvailable Addresses:");
        for (int i = 1; i <= addresses.getNumberOfEntries(); i++) {
            System.out.printf("%d. %s\n", i, addresses.getEntry(i));
        }

        System.out.print("Select address number: ");
        int choice = Integer.parseInt(scanner.nextLine());
        String selectedAddr = addresses.getEntry(choice);

        DoublyLinkedList<Applicant> filtered = new DoublyLinkedList<>();
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            Applicant a = list.getEntry(i);
            if (a.getApplicantAddress().equalsIgnoreCase(selectedAddr)) {
                filtered.add(a);
            }
        }
        activeFilters.add("Address = " + selectedAddr);
        return filtered;
    } // filterByAddressfilterByAddress

    private DoublyListInterface<Applicant> filterByJobCategoryAndPosition(DoublyListInterface<Applicant> list, DoublyLinkedList<String> activeFilters, Scanner scanner) {
        DoublyLinkedList<String> categories = new DoublyLinkedList<>();
        for (int i = 1; i <= jobDesiredList.getNumberOfEntries(); i++) {
            String cat = jobDesiredList.getEntry(i).getCategory();
            if (!categories.contains(cat)) categories.add(cat);
        }

        System.out.println("\nAvailable Job Categories:");
        for (int i = 1; i <= categories.getNumberOfEntries(); i++) {
            System.out.printf("%d. %s\n", i, categories.getEntry(i));
        }

        System.out.print("Choose category number: ");
        int catIndex = Integer.parseInt(scanner.nextLine());
        String selectedCat = categories.getEntry(catIndex);
        activeFilters.add("Job Category = " + selectedCat);

        System.out.print("Filter by specific position? (1 = Yes, 2 = No): ");
        String answer = scanner.nextLine();
        if (answer.equals("1")) {
            DoublyLinkedList<JobDesired> filteredJobs = new DoublyLinkedList<>();
            for (int i = 1; i <= jobDesiredList.getNumberOfEntries(); i++) {
                JobDesired jd = jobDesiredList.getEntry(i);
                if (jd.getCategory().equalsIgnoreCase(selectedCat)) {
                    filteredJobs.add(jd);
                }
            }

            System.out.println("Available Positions:");
            for (int i = 1; i <= filteredJobs.getNumberOfEntries(); i++) {
                System.out.printf("%d. %s\n", i, filteredJobs.getEntry(i).getPosition());
            }

            System.out.print("Choose position number: ");
            int posIndex = Integer.parseInt(scanner.nextLine());
            String selectedPos = filteredJobs.getEntry(posIndex).getPosition();

            activeFilters.add("Position = " + selectedPos);

            DoublyLinkedList<Applicant> filtered = new DoublyLinkedList<>();
            for (int i = 1; i <= list.getNumberOfEntries(); i++) {
                Applicant a = list.getEntry(i);
                if (containsJob(a, selectedPos)) filtered.add(a);
            }
            return filtered;
        } else {
            DoublyLinkedList<Applicant> filtered = new DoublyLinkedList<>();
            for (int i = 1; i <= list.getNumberOfEntries(); i++) {
                Applicant a = list.getEntry(i);
                if (containsCategory(a, selectedCat)) filtered.add(a);
            }
            return filtered;
        }
    } // filterByJobCategoryAndPosition

    private DoublyListInterface<Applicant> filterBySkillHierarchy(DoublyListInterface<Applicant> list, DoublyLinkedList<String> activeFilters, Scanner scanner) {
        DoublyLinkedList<String> categories = new DoublyLinkedList<>();
        for (int i = 1; i <= skillList.getNumberOfEntries(); i++) {
            String cat = skillList.getEntry(i).getCategory();
            if (!categories.contains(cat)) categories.add(cat);
        }

        System.out.println("\nAvailable Skill Categories:");
        for (int i = 1; i <= categories.getNumberOfEntries(); i++) {
            System.out.printf("%d. %s\n", i, categories.getEntry(i));
        }

        System.out.print("Choose category number: ");
        int catIndex = Integer.parseInt(scanner.nextLine());
        if (catIndex < 1 || catIndex > categories.getNumberOfEntries()) {
            System.out.println("Invalid category.");
            return list;
        }

        String selectedCat = categories.getEntry(catIndex);
        activeFilters.add("Skill Category = " + selectedCat);

        // Ask user if they want to filter by skill name
        System.out.print("Filter by specific skill name? (1 = Yes, 2 = No): ");
        String skillNameChoice = scanner.nextLine().trim();

        String selectedSkill = null;
        String selectedProf = null;

        if (skillNameChoice.equals("1")) {
            // Collect skill names under the selected category
            DoublyLinkedList<String> uniqueSkillNames = new DoublyLinkedList<>();
            for (int i = 1; i <= skillList.getNumberOfEntries(); i++) {
                Skill s = skillList.getEntry(i);
                if (s.getCategory().equalsIgnoreCase(selectedCat)) {
                    if (!uniqueSkillNames.contains(s.getSkillName())) {
                        uniqueSkillNames.add(s.getSkillName());
                    }
                }
            }

            System.out.println("\nSkill Names under " + selectedCat + ":");
            for (int i = 1; i <= uniqueSkillNames.getNumberOfEntries(); i++) {
                System.out.printf("%d. %s\n", i, uniqueSkillNames.getEntry(i));
            }

            System.out.print("Choose skill name number: ");
            int skillIndex = Integer.parseInt(scanner.nextLine());
            if (skillIndex >= 1 && skillIndex <= uniqueSkillNames.getNumberOfEntries()) {
                selectedSkill = uniqueSkillNames.getEntry(skillIndex);
                activeFilters.add("Skill Name = " + selectedSkill);

                // Ask if want to go further into proficiency
                System.out.print("Filter by proficiency level? (1 = Yes, 2 = No): ");
                String profChoice = scanner.nextLine();
                if (profChoice.equals("1")) {
                    // Gather all proficiencies under the selected skill
                    DoublyLinkedList<String> profs = new DoublyLinkedList<>();
                    for (int i = 1; i <= skillList.getNumberOfEntries(); i++) {
                        Skill s = skillList.getEntry(i);
                        if (s.getSkillName().equalsIgnoreCase(selectedSkill)) {
                            if (!profs.contains(s.getProficiency())) {
                                profs.add(s.getProficiency());
                            }
                        }
                    }

                    System.out.println("\nAvailable Proficiencies for " + selectedSkill + ":");
                    for (int i = 1; i <= profs.getNumberOfEntries(); i++) {
                        System.out.printf("%d. %s\n", i, profs.getEntry(i));
                    }

                    System.out.print("Choose proficiency number: ");
                    int profIndex = Integer.parseInt(scanner.nextLine());
                    if (profIndex >= 1 && profIndex <= profs.getNumberOfEntries()) {
                        selectedProf = profs.getEntry(profIndex);
                        activeFilters.add("Proficiency = " + selectedProf);
                    }
                }
            }
        }

        // Final filtering logic
        DoublyLinkedList<Applicant> filtered = new DoublyLinkedList<>();
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            Applicant a = list.getEntry(i);
            for (int j = 1; j <= a.getSkillList().getNumberOfEntries(); j++) {
                Skill s = a.getSkillList().getEntry(j);
                if (s.getCategory().equalsIgnoreCase(selectedCat)) {
                    boolean matchSkill = (selectedSkill == null || s.getSkillName().equalsIgnoreCase(selectedSkill));
                    boolean matchProf = (selectedProf == null || s.getProficiency().equalsIgnoreCase(selectedProf));
                    if (matchSkill && matchProf) {
                        filtered.add(a);
                        break;
                    }
                }
            }
        }

        return filtered;
    } // filterBySkillHierarchy

    private DoublyListInterface<Applicant> filterBySkillCountOnlyInCategory(DoublyListInterface<Applicant> list, Scanner scanner, DoublyListInterface<String> currentFilters) {
        DoublyLinkedList<String> categories = new DoublyLinkedList<>();
        for (int i = 1; i <= skillList.getNumberOfEntries(); i++) {
            String cat = skillList.getEntry(i).getCategory();
            if (!categories.contains(cat)) categories.add(cat);
        }

        System.out.println("\nAvailable Skill Categories:");
        for (int i = 1; i <= categories.getNumberOfEntries(); i++) {
            System.out.printf("%d. %s\n", i, categories.getEntry(i));
        }

        System.out.print("Select category number: ");
        int catChoice = Integer.parseInt(scanner.nextLine());
        if (catChoice < 1 || catChoice > categories.getNumberOfEntries()) {
            System.out.println("Invalid category.");
            return list;
        }

        String selectedCat = categories.getEntry(catChoice);

        System.out.print("Enter minimum number of skills in category \"" + selectedCat + "\": ");
        int minCount = Integer.parseInt(scanner.nextLine());

        DoublyLinkedList<Applicant> filtered = new DoublyLinkedList<>();
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            Applicant a = list.getEntry(i);
            int count = 0;
            for (int j = 1; j <= a.getSkillList().getNumberOfEntries(); j++) {
                Skill s = a.getSkillList().getEntry(j);
                if (s.getCategory().equalsIgnoreCase(selectedCat)) {
                    count++;
                }
            }
            if (count >= minCount) {
                filtered.add(a);
            }
        }

        currentFilters.add("Skill Category = " + selectedCat + " (Min Count = " + minCount + ")");
        return filtered;
    } // filterBySkillCountOnlyInCategory

    private void printMatchingHeader() {
        System.out.println("=".repeat(130));
        System.out.printf("%-6s | %-20s | %-12s | %-30s | %-15s | %-4s\n", 
                  "ID", "Name", "Contact", "Email", "Address", "CGPA");
        System.out.println("-".repeat(130));
    } // printMatchingHeader

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
    } // printApplicantDetails

    private boolean containsJob(Applicant a, String job) {
        for (int i = 1; i <= a.getJobDesiredList().getNumberOfEntries(); i++) {
            if (a.getJobDesiredList().getEntry(i).getPosition().equalsIgnoreCase(job)) return true;
        }
        return false;
    } // containsJob

    private boolean containsSkill(Applicant a, String name, String proficiency) {
        for (int i = 1; i <= a.getSkillList().getNumberOfEntries(); i++) {
            Skill s = a.getSkillList().getEntry(i);
            if (s.getSkillName().equalsIgnoreCase(name)) {
                if (proficiency == null || s.getProficiency().equalsIgnoreCase(proficiency)) return true;
            }
        }
        return false;
    } // containsSkill

    private boolean containsCategory(Applicant a, String category) {
        for (int i = 1; i <= a.getJobDesiredList().getNumberOfEntries(); i++) {
            if (a.getJobDesiredList().getEntry(i).getCategory().equalsIgnoreCase(category)) return true;
        }
        return false;
    } // containsCategory

    public void generateApplicantReport() {
        if (applicantList.isEmpty()) {
            System.out.println("No applicants found.");
            return;
        }

        System.out.println("\n" + "=".repeat(100));
        System.out.println("INTERNSHIP APPLICATION SUMMARY REPORT");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd yyyy, hh:mm a");
        System.out.println("Generated at: " + formatter.format(LocalDateTime.now()));
        System.out.println("=".repeat(100) + "\n");

        // Applicant Table
        System.out.printf("%-6s | %-20s | %-12s | %-25s | %-15s | %-4s\n", "ID", "Name", "Contact", "Email", "Address", "CGPA");
        System.out.println("-".repeat(100));
        for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
            Applicant a = applicantList.getEntry(i);
            System.out.printf("%-6s | %-20s | %-12s | %-25s | %-15s | %.2f\n",
                a.getApplicantID(), a.getApplicantName(), a.getApplicantContact(),
                a.getApplicantEmail(), a.getApplicantAddress(), a.getCgpa());
        }

        System.out.println("\nTotal Applicants: " + applicantList.getNumberOfEntries());
        System.out.print("-".repeat(100));

        // === Skill Category Summary (Count Unique Skill Names) ===
        System.out.println("\nSkill Categories Summary:");
        HashMap<String, HashSet<String>> categoryToUniqueSkills = new HashMap<>();

        for (int i = 1; i <= skillList.getNumberOfEntries(); i++) {
            Skill s = skillList.getEntry(i);
            String cat = s.getCategory();
            String skillName = s.getSkillName();

            categoryToUniqueSkills.putIfAbsent(cat, new HashSet<>());
            categoryToUniqueSkills.get(cat).add(skillName);
        }

        for (String cat : categoryToUniqueSkills.keySet()) {
            int uniqueCount = categoryToUniqueSkills.get(cat).size();
            System.out.printf(" - %-15s : %d skill(s)\n", cat, uniqueCount);
        }

        System.out.print("-".repeat(100));

        // === Job Position Summary ===
        System.out.println("\nDesired Job Positions Summary:");
        HashMap<String, Integer> jobPosCount = new HashMap<>();
        for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
            Applicant a = applicantList.getEntry(i);
            for (int j = 1; j <= a.getJobDesiredList().getNumberOfEntries(); j++) {
                String pos = a.getJobDesiredList().getEntry(j).getPosition();
                jobPosCount.put(pos, jobPosCount.getOrDefault(pos, 0) + 1);
            }
        }
        for (String pos : jobPosCount.keySet()) {
            System.out.printf(" - %-20s : %d applicant(s)\n", pos, jobPosCount.get(pos));
        }
        System.out.print("-".repeat(100));

        // === Location Summary ===
        System.out.println("\nApplicant Location Summary:");
        HashMap<String, Integer> locationCount = new HashMap<>();
        for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
            String loc = applicantList.getEntry(i).getApplicantAddress();
            locationCount.put(loc, locationCount.getOrDefault(loc, 0) + 1);
        }
        for (String loc : locationCount.keySet()) {
            System.out.printf(" - %-15s : %d applicant(s)\n", loc, locationCount.get(loc));
        }
        System.out.print("-".repeat(100));

        // === CGPA Distribution ===
        System.out.println("\nCGPA Distribution:");
        int[] ranges = new int[5]; 
        for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
            double cgpa = applicantList.getEntry(i).getCgpa();
            if (cgpa < 2.0) ranges[0]++;
            else if (cgpa < 2.5) ranges[1]++;
            else if (cgpa < 3.0) ranges[2]++;
            else if (cgpa < 3.5) ranges[3]++;
            else ranges[4]++;
        }
        String[] labels = {"< 2.0     ", "2.0 - 2.49", "2.5 - 2.99", "3.0 - 3.49", "3.5 - 4.00"};
        for (int i = 0; i < labels.length; i++) {
            System.out.printf(" %-10s : %-2d | %s\n", labels[i], ranges[i], "*".repeat(ranges[i]));
        }
        System.out.println("-".repeat(100));
        System.out.println("END OF REPORT");
    } //generateApplicantReport

    private String centerText(String text, int width) {
        int padSize = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padSize)) + text;
    } //centerText

    public void showBasicApplicantTable() {
        if (applicantList.isEmpty()) {
            System.out.println("No applicants available.");
            return;
        }

        System.out.println("-".repeat(100));
        System.out.printf("%-6s | %-20s | %-12s | %-30s | %-15s | %-4s\n", 
            "ID", "Name", "Contact", "Email", "Address", "CGPA");
        System.out.println("-".repeat(100));

        for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
            Applicant a = applicantList.getEntry(i);
            System.out.printf("%-6s | %-20s | %-12s | %-30s | %-15s | %-4.2f\n",
                a.getApplicantID(), a.getApplicantName(), a.getApplicantContact(), 
                a.getApplicantEmail(), a.getApplicantAddress(), a.getCgpa());
        }

        System.out.println("-".repeat(100));
    } //showBasicApplicantTable

    public void showAllSkills() {
        if (skillList == null || skillList.isEmpty()) {
            System.out.println("No skills available.");
            return;
        }
        System.out.printf("%-20s | %-15s | %-12s\n", "Skill Name", "Category", "Proficiency");
        System.out.println("-".repeat(50));
        for (int i = 1; i <= skillList.getNumberOfEntries(); i++) {
            Skill s = skillList.getEntry(i);
            System.out.printf("%-20s | %-15s | %-12s\n", s.getSkillName(), s.getCategory(), s.getProficiency());
        }
    }  // showAllSkills

    public void showAllDesiredJobs() {
        if (jobDesiredList == null || jobDesiredList.isEmpty()) {
            System.out.println("No job positions available.");
            return;
        }
        System.out.printf("%-25s | %-15s\n", "Position", "Category");
        System.out.println("-".repeat(45));
        for (int i = 1; i <= jobDesiredList.getNumberOfEntries(); i++) {
            JobDesired j = jobDesiredList.getEntry(i);
            System.out.printf("%-25s | %-15s\n", j.getPosition(), j.getCategory());
        }
    } // showAllDesiredJobs

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ApplicantManager manager = new ApplicantManager();
        manager.initializeData(); 

        System.out.println("\n==== Initialized Applicant Data ====");
        manager.showBasicApplicantTable();

        System.out.println("\n==== Initialized Skill List ====");
        manager.showAllSkills();

        System.out.println("\n==== Initialized Job List ====");
        manager.showAllDesiredJobs();
        
        boolean exit = false;
        while (!exit) {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("        APPLICANT MANAGEMENT MENU");
            System.out.println("=".repeat(40));

            System.out.println("1. Display All Applicants");
            System.out.println("2. Add Applicant");
            System.out.println("3. Remove Applicant");
            System.out.println("4. Update Applicant");

            System.out.println("-".repeat(40));
            System.out.println("5. Filter Applicants");
            System.out.println("6. Generate Summary Report");
            System.out.println("7. Sort Applicants");

            System.out.println("-".repeat(40));
            System.out.println("8. Exit");

            System.out.println("=".repeat(40));
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
                    manager.generateApplicantReport();
                    break;
                case 7: 
                    manager.sortApplicantsMenu();
                    break;
                case 8:
                    exit = true;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    } // main

    
} // applicant manager
