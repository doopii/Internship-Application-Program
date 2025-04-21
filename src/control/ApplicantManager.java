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

// covered adt
//    add(E newEntry)- addApplicant, updateApplicant, filterApplicants, generateApplicantReport
//    remove(int givenPosition)	- removeApplicant
//    clear() - filterApplicants, updateApplicant (reset skills/jobs)
//    replace(int givenPosition, E)- updateApplicant
//    getEntry(int givenPosition)- Everywhere (used in almost all methods)
//    contains(E anEntry)- addApplicant, updateApplicant, filterApplicants, generateApplicantReport
//    getNumberOfEntries()- Everywhere (used in loops throughout all methods)
//    isEmpty()- addApplicant, removeApplicant, updateApplicant, searchApplicant, sortApplicantsMenu, showAllApplicants, filterApplicants, generateApplicantReport, showAllSkills, showAllDesiredJobs
//    clone()- filterApplicants (used to clone full applicant list into filteredList)
//    getLast()- generateNextApplicantID
//    swap(int index1, int index2)- Used indirectly via BubbleSort.bubbleSort() in sortApplicantsMenu

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
        DoublyListInterface<Applicant> originalList = applicantList.clone();

        Scanner scanner = new Scanner(System.in);
        boolean done = false;

        while (!done) {
            System.out.println("\n--- Sort Applicants ---");
            System.out.println("1. Sort by ID");
            System.out.println("2. Sort by Name");
            System.out.println("3. Sort by CGPA");
            System.out.println("4. Reset");
            System.out.println("5. Exit");
            System.out.print("Choose sorting option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    System.out.println("1. Ascending ID");
                    System.out.println("2. Descending ID");
                    System.out.print("Choose ID sort order: ");
                    String idOrder = scanner.nextLine();
                    BubbleSort.bubbleSort(applicantList, "id", idOrder.equals("1"));
                    System.out.println("Sorted by ID (" + (idOrder.equals("1") ? "Ascending" : "Descending") + ").");
                    break;

                case "2":
                    System.out.println("1. A - Z");
                    System.out.println("2. Z - A");
                    System.out.print("Choose Name sort order: ");
                    String nameOrder = scanner.nextLine();
                    BubbleSort.bubbleSort(applicantList, "name", nameOrder.equals("1"));
                    System.out.println("Sorted by Name (" + (nameOrder.equals("1") ? "A - Z" : "Z - A") + ").");
                    break;

                case "3":
                    System.out.println("1. Ascending CGPA");
                    System.out.println("2. Descending CGPA");
                    System.out.print("Choose CGPA sort order: ");
                    String order = scanner.nextLine();
                    boolean ascending = order.equals("1");
                    BubbleSort.bubbleSort(applicantList, "cgpa", ascending);
                    System.out.println("Sorted by CGPA (" + (ascending ? "Ascending" : "Descending") + ").");
                    break;

                case "4":
                    applicantList = originalList.clone(); 
                    System.out.println("Applicant list reset to original order.");
                    break;

                case "5":
                    done = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
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
    } // generateNextApplicantID - done
    
    public void showAllApplicants() {
        if (applicantList.isEmpty()) {
            System.out.println("No applicants found.");
            return;
        }
        System.out.println("=".repeat(150));
        System.out.println(centerText("ALL INTERNSHIP APPLICANT", 150));
        System.out.println("=".repeat(150));
        System.out.printf("%-6s | %-20s | %-12s | %-30s | %-12s | %-4s | %-30s | %-25s\n", 
            "ID", "Name", "Contact", "Email", "Address", "CGPA", "Skills", "Desired Jobs");
        System.out.println("-".repeat(150));

        for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
            Applicant a = applicantList.getEntry(i);

            int maxLines = Math.max(
                a.getSkillList().getNumberOfEntries(),
                a.getJobDesiredList().getNumberOfEntries()
            );

            for (int line = 0; line < maxLines; line++) {
                String id = "", name = "", contact = "", email = "", addr = "", cgpa = "";
                if (line == 0) {
                    id = a.getApplicantID();
                    name = a.getApplicantName();
                    contact = a.getApplicantContact();
                    email = a.getApplicantEmail();
                    addr = a.getApplicantAddress();
                    cgpa = String.format("%.2f", a.getCgpa());
                }

                String skillStr = "";
                if (line < a.getSkillList().getNumberOfEntries()) {
                    Skill s = a.getSkillList().getEntry(line + 1);
                    skillStr = s.getSkillName() + " (" + s.getProficiency() + ")";
                }

                String jobStr = "";
                if (line < a.getJobDesiredList().getNumberOfEntries()) {
                    JobDesired j = a.getJobDesiredList().getEntry(line + 1);
                    jobStr = j.getPosition();
                }

                System.out.printf("%-6s | %-20s | %-12s | %-30s | %-12s | %-4s | %-30s | %-25s\n",
                    id, name, contact, email, addr, cgpa, skillStr, jobStr);

                // Clear after first line to avoid repeating details
                id = name = contact = email = addr = cgpa = "";
            }

            System.out.println("-".repeat(150));
        }
    } //showAllApplicants - done

    public void addApplicant() {
        Scanner scanner = new Scanner(System.in);

        String id = generateNextApplicantID();
        System.out.println("Generated Applicant ID: " + id);

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        String contact;
        while (true) {
            System.out.print("Enter Contact (10 digits): ");
            contact = scanner.nextLine().trim();
            if (contact.matches("\\d{10}")) break;
            System.out.println("Invalid contact. Please enter exactly 10 digits.");
        }

        String email;
        while (true) {
            System.out.print("Enter Email: ");
            email = scanner.nextLine().trim();
            if (email.contains("@") && email.contains(".")) break;
            System.out.println("Invalid email format.");
        }

        String[] states = {"Selangor", "Johor", "Kuala Lumpur", "Penang", "Sarawak"};
        System.out.println("\nAvailable Addresses:");
        for (int i = 0; i < states.length; i++) {
            System.out.printf("%d. %s\n", i + 1, states[i]);
        }

        int addrChoice = -1;
        while (addrChoice < 1 || addrChoice > states.length) {
            System.out.print("Select address number (1-" + states.length + "): ");
            try {
                addrChoice = Integer.parseInt(scanner.nextLine().trim());
                if (addrChoice < 1 || addrChoice > states.length) {
                    System.out.println("Invalid choice. Please enter a number between 1 and " + states.length + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        String address = states[addrChoice - 1];

        double cgpa = -1;
        while (cgpa <= 0) {
            System.out.print("Enter CGPA: ");
            try {
                cgpa = Double.parseDouble(scanner.nextLine());
                if (cgpa <= 0) {
                    System.out.println("CGPA must be greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }

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

            int catIndex = -1;
            while (catIndex < 1 || catIndex > categories.getNumberOfEntries()) {
                System.out.print("Choose skill category number: ");
                try {
                    catIndex = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    continue;
                }
            }
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

            int skillIndex = -1;
            while (skillIndex < 1 || skillIndex > skillNames.getNumberOfEntries()) {
                System.out.print("Choose skill name number: ");
                try {
                    skillIndex = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    continue;
                }
            }

            String selectedSkill = skillNames.getEntry(skillIndex);

            // Prevent duplicate skill names
            boolean alreadyAdded = false;
            for (int i = 1; i <= selectedSkills.getNumberOfEntries(); i++) {
                if (selectedSkills.getEntry(i).getSkillName().equalsIgnoreCase(selectedSkill)) {
                    alreadyAdded = true;
                    break;
                }
            }

            if (alreadyAdded) {
                System.out.println("You already added this skill. Skipping.");
            } else {
                System.out.println("\nAvailable Proficiency:");
                System.out.println("1. Beginner");
                System.out.println("2. Intermediate");
                System.out.println("3. Advanced");

                int profChoice = -1;
                while (profChoice < 1 || profChoice > 3) {
                    System.out.print("Choose proficiency number: ");
                    try {
                        profChoice = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }

                String proficiency = (profChoice == 1) ? "Beginner"
                                    : (profChoice == 2) ? "Intermediate"
                                    : "Advanced";

                selectedSkills.add(new Skill(selectedSkill, selectedCat, proficiency));
            }

            int moreSkillChoice = -1;
            while (moreSkillChoice != 1 && moreSkillChoice != 2) {
                System.out.print("Add more skills? (1 = Yes, 2 = No): ");
                try {
                    moreSkillChoice = Integer.parseInt(scanner.nextLine().trim());
                    if (moreSkillChoice != 1 && moreSkillChoice != 2) {
                        System.out.println("Invalid choice. Please enter 1 or 2.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }
            if (moreSkillChoice == 1) {
                addMoreSkill = true;
            } else {
                addMoreSkill = false;
            }
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

            int catIndex = -1;
            while (catIndex < 1 || catIndex > jobCats.getNumberOfEntries()) {
                System.out.print("Choose job category number: ");
                try {
                    catIndex = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    continue;
                }
            }

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

            int posIndex = -1;
            while (posIndex < 1 || posIndex > jobsInCat.getNumberOfEntries()) {
                System.out.print("Choose job position number: ");
                try {
                    posIndex = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    continue;
                }
            }

            selectedJobs.add(jobsInCat.getEntry(posIndex));

            int moreJobChoice = -1;
            while (moreJobChoice != 1 && moreJobChoice != 2) {
                System.out.print("Add more desired jobs? (1 = Yes, 2 = No): ");
                try {
                    moreJobChoice = Integer.parseInt(scanner.nextLine().trim());
                    if (moreJobChoice != 1 && moreJobChoice != 2) {
                        System.out.println("Invalid choice. Please enter 1 or 2.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }
            if (moreJobChoice == 1) {
                addMoreJob = true;
            } else {
                addMoreJob = false;
            }
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
        printMatchingHeader();
        for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
            printApplicantDetails(applicantList.getEntry(i));
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
    } // removeApplicant - done


    public void updateApplicant() {
        if (applicantList.isEmpty()) {
            System.out.println("No applicants to update.");
            return;
        }

        System.out.println("\n===== Current Applicants =====");
        showAllApplicants();

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

        System.out.println("\n--- Selected Applicant ---");
        printMatchingHeader();
        printApplicantDetails(target);

        boolean done = false;
        while (!done) {
            System.out.println("\n--- Update Menu for " + target.getApplicantName() + " ---");
            System.out.println("1. Name");
            System.out.println("2. Contact");
            System.out.println("3. Email");
            System.out.println("4. Address");
            System.out.println("5. CGPA");
            System.out.println("6. Skills");
            System.out.println("7. Desired Jobs");
            System.out.println("8. Done");
            System.out.print("Select a field to update: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                System.out.print("Enter new name: ");
                target.setApplicantName(scanner.nextLine().trim());
            } else if (choice.equals("2")) {
                System.out.print("Enter new contact: ");
                target.setApplicantContact(scanner.nextLine().trim());
            } else if (choice.equals("3")) {
                System.out.print("Enter new email: ");
                target.setApplicantEmail(scanner.nextLine().trim());
            } else if (choice.equals("4")) {
                String[] states = {"Selangor", "Johor", "Kuala Lumpur", "Penang", "Sarawak"};
                System.out.println("\nAvailable Addresses:");
                for (int i = 0; i < states.length; i++) {
                    System.out.printf("%d. %s\n", i + 1, states[i]);
                }
                System.out.print("Select new address number: ");
                int addrChoice = Integer.parseInt(scanner.nextLine());
                if (addrChoice >= 1 && addrChoice <= states.length) {
                    target.setApplicantAddress(states[addrChoice - 1]);
                } else {
                    System.out.println("Invalid choice.");
                }
            } else if (choice.equals("5")) {
                System.out.print("Enter new CGPA: ");
                try {
                    double newCgpa = Double.parseDouble(scanner.nextLine().trim());
                    target.setCgpa(newCgpa);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid CGPA input.");
                }
            } else if (choice.equals("6")) {
                System.out.println("1. Reset skills");
                System.out.println("2. Add new skills");
                System.out.print("Choose option: ");
                String skillOpt = scanner.nextLine().trim();
                if (skillOpt.equals("1")) {
                    target.setSkillList(new DoublyLinkedList<>());
                }

                boolean addMoreSkill = true;
                while (addMoreSkill) {
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

                    DoublyLinkedList<String> skillNames = new DoublyLinkedList<>();
                    for (int i = 1; i <= skillList.getNumberOfEntries(); i++) {
                        Skill s = skillList.getEntry(i);
                        if (s.getCategory().equalsIgnoreCase(selectedCat) && !skillNames.contains(s.getSkillName())) {
                            skillNames.add(s.getSkillName());
                        }
                    }

                    System.out.println("\nSkill Names in " + selectedCat + ":");
                    for (int i = 1; i <= skillNames.getNumberOfEntries(); i++) {
                        System.out.printf("%d. %s\n", i, skillNames.getEntry(i));
                    }

                    System.out.print("Choose skill name number: ");
                    int skillIndex = Integer.parseInt(scanner.nextLine());
                    String selectedSkill = skillNames.getEntry(skillIndex);

                    String proficiency = "";
                    System.out.println("\nAvailable Proficiency:");
                    System.out.println("1. Beginner");
                    System.out.println("2. Intermediate");
                    System.out.println("3. Advanced");
                    System.out.print("Choose proficiency number: ");
                    String profChoice = scanner.nextLine().trim();

                    if (profChoice.equals("1")) {
                        proficiency = "Beginner";
                    } else if (profChoice.equals("2")) {
                        proficiency = "Intermediate";
                    } else if (profChoice.equals("3")) {
                        proficiency = "Advanced";
                    } else {
                        System.out.println("Invalid proficiency. Skipping this skill.");
                        continue;
                    }

                    target.getSkillList().add(new Skill(selectedCat, selectedSkill, proficiency));
                    System.out.print("Add more skills? (1 = Yes, 2 = No): ");
                    addMoreSkill = scanner.nextLine().trim().equals("1");
                }

                System.out.println("Skills updated.");
            } else if (choice.equals("7")) {
                System.out.println("1. Reset jobs");
                System.out.println("2. Add new desired jobs");
                System.out.print("Choose option: ");
                String jobOpt = scanner.nextLine().trim();
                if (jobOpt.equals("1")) {
                    target.setJobDesiredList(new DoublyLinkedList<>());
                }

                boolean addMoreJob = true;
                while (addMoreJob) {
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
                    JobDesired selectedJob = jobsInCat.getEntry(posIndex);

                    target.getJobDesiredList().add(selectedJob);

                    System.out.print("Add more desired jobs? (1 = Yes, 2 = No): ");
                    addMoreJob = scanner.nextLine().trim().equals("1");
                }

                System.out.println("Desired jobs updated.");
            } else if (choice.equals("8")) {
                done = true;
            } else {
                System.out.println("Invalid choice.");
            }

            if (!choice.equals("8")) {
                System.out.println("\n--- Updated Applicant Info ---");
                printMatchingHeader();
                printApplicantDetails(target);
            }
        }

        applicantList.replace(targetIndex, target);
        System.out.println("\nApplicant updated successfully.");
    } // updateApplicant - done
    
    public void searchApplicant() {
        if (applicantList.isEmpty()) {
            System.out.println("No applicants to search.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Search Applicant ---");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Name");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine().trim();

        boolean found = false;

        if (choice.equals("1")) {
            System.out.print("Enter Applicant ID: ");
            String id = scanner.nextLine().trim();

            for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
                Applicant a = applicantList.getEntry(i);
                if (a.getApplicantID().equalsIgnoreCase(id)) {
                    printMatchingHeader();
                    printApplicantDetails(a);
                    found = true;
                    break;
                }
            }

        } else if (choice.equals("2")) {
            System.out.print("Enter name keyword: ");
            String keyword = scanner.nextLine().trim().toLowerCase();

            DoublyLinkedList<Applicant> matches = new DoublyLinkedList<>();
            for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
                Applicant a = applicantList.getEntry(i);
                if (a.getApplicantName().toLowerCase().contains(keyword)) {
                    matches.add(a);
                }
            }

            if (matches.isEmpty()) {
                System.out.println("No matching names.");
                return;
            }

            // Show matching suggestions
            System.out.println("\nSuggestions found:");
            for (int i = 1; i <= matches.getNumberOfEntries(); i++) {
                System.out.printf("%d. %s\n", i, matches.getEntry(i).getApplicantName());
            }

            System.out.print("Choose number or type full name: ");
            String input = scanner.nextLine().trim();
            Applicant selected = null;

            try {
                int index = Integer.parseInt(input);
                if (index >= 1 && index <= matches.getNumberOfEntries()) {
                    selected = matches.getEntry(index);
                }
            } catch (NumberFormatException e) {
                for (int i = 1; i <= matches.getNumberOfEntries(); i++) {
                    if (matches.getEntry(i).getApplicantName().equalsIgnoreCase(input)) {
                        selected = matches.getEntry(i);
                        break;
                    }
                }
            }

            if (selected != null) {
                printMatchingHeader();
                printApplicantDetails(selected);
                found = true;
            } else {
                System.out.println("No exact match found.");
            }
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        if (!found) {
            System.out.println("No matching applicant found.");
        }
    } // searchApplicant - done

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
        System.out.println("=".repeat(150));
        System.out.println(centerText("APPLICANT", 150));
        System.out.println("=".repeat(150));
        System.out.printf("%-6s | %-20s | %-12s | %-30s | %-12s | %-4s | %-30s | %-25s\n", 
            "ID", "Name", "Contact", "Email", "Address", "CGPA", "Skills", "Desired Jobs");
        System.out.println("-".repeat(150));
    } // printMatchingHeader

    private void printApplicantDetails(Applicant a) {
        int maxLines = Math.max(
            a.getSkillList().getNumberOfEntries(),
            a.getJobDesiredList().getNumberOfEntries()
        );

        for (int line = 0; line < maxLines; line++) {
            String id = "", name = "", contact = "", email = "", addr = "", cgpa = "";
            if (line == 0) {
                id = a.getApplicantID();
                name = a.getApplicantName();
                contact = a.getApplicantContact();
                email = a.getApplicantEmail();
                addr = a.getApplicantAddress();
                cgpa = String.format("%.2f", a.getCgpa());
            }

            String skillStr = "";
            if (line < a.getSkillList().getNumberOfEntries()) {
                Skill s = a.getSkillList().getEntry(line + 1);
                skillStr = s.getSkillName() + " (" + s.getProficiency() + ")";
            }

            String jobStr = "";
            if (line < a.getJobDesiredList().getNumberOfEntries()) {
                JobDesired j = a.getJobDesiredList().getEntry(line + 1);
                jobStr = j.getPosition();
            }

            System.out.printf("%-6s | %-20s | %-12s | %-30s | %-12s | %-4s | %-30s | %-25s\n",
                id, name, contact, email, addr, cgpa, skillStr, jobStr);
        }

        System.out.println("-".repeat(150));
    }

    private boolean containsJob(Applicant a, String job) {
        for (int i = 1; i <= a.getJobDesiredList().getNumberOfEntries(); i++) {
            if (a.getJobDesiredList().getEntry(i).getPosition().equalsIgnoreCase(job)) return true;
        }
        return false;
    } // containsJob

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

        int reportWidth = 130;
        System.out.println("\n" + "=".repeat(reportWidth));
        System.out.println(centerText("INTERNSHIP APPLICATION SUMMARY REPORT", reportWidth));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd yyyy, hh:mm a");
        String timestamp = "Generated at: " + formatter.format(LocalDateTime.now());
        System.out.println(centerText(timestamp, reportWidth));
        System.out.println("=".repeat(reportWidth) + "\n");


        // Applicant Table
        System.out.printf("%-6s | %-20s | %-12s | %-25s | %-15s | %-2s | %-8s | %-12s\n",
            "ID", "Name", "Contact", "Email", "Address", "CGPA", "NoOfSkills", "NoOfDesiredJobs");
        System.out.println("-".repeat(reportWidth));

        int totalSkills = 0;
        int totalJobs = 0;

        for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
            Applicant a = applicantList.getEntry(i);
            int skillCount = a.getSkillList().getNumberOfEntries();
            int jobCount = a.getJobDesiredList().getNumberOfEntries();
            totalSkills += skillCount;
            totalJobs += jobCount;

            System.out.printf("%-6s | %-20s | %-12s | %-25s | %-15s | %.2f | %-8d | %-12d\n",
                a.getApplicantID(), a.getApplicantName(), a.getApplicantContact(),
                a.getApplicantEmail(), a.getApplicantAddress(), a.getCgpa(),
                skillCount, jobCount);
        }

        System.out.println("\nTotal Applicants : " + applicantList.getNumberOfEntries());
        System.out.println("Total Number of Skills : " + totalSkills);
        System.out.println("Total Number of Desired Jobs : " + totalJobs);
        System.out.print("-".repeat(reportWidth));


        // === Skill Category Summary (Count Unique Skill Names) ===
        System.out.println("\nSkill Categories Summary:");
        DoublyLinkedList<String> printedCats = new DoublyLinkedList<>();

        for (int i = 1; i <= skillList.getNumberOfEntries(); i++) {
            Skill s = skillList.getEntry(i);
            String cat = s.getCategory();

            if (!printedCats.contains(cat)) {
                printedCats.add(cat);

                // Collect unique skill names under this category
                DoublyLinkedList<String> uniqueSkills = new DoublyLinkedList<>();
                for (int j = 1; j <= skillList.getNumberOfEntries(); j++) {
                    Skill s2 = skillList.getEntry(j);
                    if (s2.getCategory().equalsIgnoreCase(cat)) {
                        String skillName = s2.getSkillName();
                        if (!uniqueSkills.contains(skillName)) {
                            uniqueSkills.add(skillName);
                        }
                    }
                }
                System.out.printf(" - %-15s : %d skill(s)\n", cat, uniqueSkills.getNumberOfEntries());
            }
        }
        System.out.print("-".repeat(reportWidth));

        // === Job Position Summary ===
        System.out.println("\nDesired Job Positions Summary:");
        DoublyLinkedList<String> printedJobs = new DoublyLinkedList<>();

        for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
            Applicant a = applicantList.getEntry(i);
            for (int j = 1; j <= a.getJobDesiredList().getNumberOfEntries(); j++) {
                String pos = a.getJobDesiredList().getEntry(j).getPosition();

                if (!printedJobs.contains(pos)) {
                    printedJobs.add(pos);

                    int count = 0;
                    for (int k = 1; k <= applicantList.getNumberOfEntries(); k++) {
                        Applicant other = applicantList.getEntry(k);
                        for (int m = 1; m <= other.getJobDesiredList().getNumberOfEntries(); m++) {
                            if (other.getJobDesiredList().getEntry(m).getPosition().equalsIgnoreCase(pos)) {
                                count++;
                                break;
                            }
                        }
                    }

                    System.out.printf(" - %-20s : %d applicant(s)\n", pos, count);
                }
            }
        }
        System.out.print("-".repeat(reportWidth));

        // === Location Summary ===
        System.out.println("\nApplicant Location Summary:");
        DoublyLinkedList<String> printedLocations = new DoublyLinkedList<>();

        for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
            String loc = applicantList.getEntry(i).getApplicantAddress();

            if (!printedLocations.contains(loc)) {
                printedLocations.add(loc);

                int count = 0;
                for (int j = 1; j <= applicantList.getNumberOfEntries(); j++) {
                    if (applicantList.getEntry(j).getApplicantAddress().equalsIgnoreCase(loc)) {
                        count++;
                    }
                }

                System.out.printf(" - %-15s : %d applicant(s)\n", loc, count);
            }
        }

        // === CGPA Distribution ===
        System.out.println("\nOverall CGPA Distribution:");
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
        

        // === Address + CGPA Tier Distribution ===
        System.out.println("\nCGPA Tier Distribution by Address:");
        DoublyLinkedList<String> addresses = new DoublyLinkedList<>();
        for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
            String addr = applicantList.getEntry(i).getApplicantAddress();
            if (!addresses.contains(addr)) addresses.add(addr);
        }

        for (int a = 1; a <= addresses.getNumberOfEntries(); a++) {
            String addr = addresses.getEntry(a);
            int[] addrTiers = new int[5]; // for 5 CGPA tiers
            for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
                Applicant app = applicantList.getEntry(i);
                if (!app.getApplicantAddress().equalsIgnoreCase(addr)) continue;
                double cgpa = app.getCgpa();
                if (cgpa < 2.0) addrTiers[0]++;
                else if (cgpa < 2.5) addrTiers[1]++;
                else if (cgpa < 3.0) addrTiers[2]++;
                else if (cgpa < 3.5) addrTiers[3]++;
                else addrTiers[4]++;
            }

            System.out.println("\n" + addr + ":");
            String[] tierLabels = { "< 2.0     ", "2.0 - 2.49", "2.5 - 2.99", "3.0 - 3.49", "3.5 - 4.00" };
            for (int i = 0; i < 5; i++) {
                System.out.printf("  %-11s : %-2d | %s\n", tierLabels[i], addrTiers[i], "*".repeat(addrTiers[i]));
            }
        }
        System.out.println("-".repeat(reportWidth));
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

    public void applicantManagementMenu() {
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
            System.out.println("5. Search Applicant");
            System.out.println("6. Filter Applicants");
            System.out.println("7. Generate Summary Report");
            System.out.println("8. Sort Applicants");

            System.out.println("-".repeat(40));
            System.out.println("9. Exit");

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
                    manager.searchApplicant();
                    break;
                case 6:
                    manager.filterApplicants();
                    break;
                case 7:
                    manager.generateApplicantReport();
                    break;
                case 8: 
                    manager.sortApplicantsMenu();
                    break;
                case 9:
                    exit = true;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    } // applicantManagementMenu     

} // applicant manager
