package boundary;

import adt.DoublyLinkedList;
import adt.DoublyListInterface;
import dao.InitialiserJava;
import entity.Company;
import entity.Job;
import entity.JobRequirement;
import entity.Skill;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author yijia
 */
public class JobManagementUI {
    private final Scanner scanner = new Scanner(System.in);
        
    public int getMainMenuChoice() {
        int choice;
        while (true) {
            try {
                System.out.println("\n== INTERNSHIP APPLICATION PROGRAM =======");
                System.out.println("1. Company Management");
                System.out.println("2. Applicant Management");
                System.out.println("3. Schedule Management");
                System.out.println("4. Job Matching Engine");
                System.out.println("\n0. Exit");
                System.out.print("\n  -> Enter choice: ");

                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (choice >= 0 && choice <= 4) {
                    break;
                }
                System.out.println(" ---[ Invalid choice. Please enter 0, 1, 2, 3, or 4. ]--- ");
            } catch (InputMismatchException e) {
                System.out.println(" ---[ Invalid input. Please enter a number. ]--- ");
                scanner.nextLine(); // Clear buffer
            }
        }
        return choice;
    }
    
    public int getCompanyChoice(String companyListStr) {
        int choice;
        while (true) {
            try {
               System.out.println("\n" + "=".repeat(25));
                System.out.println("    AVAILABLE COMPANY    ");
                System.out.println("=".repeat(25));
                System.out.println(companyListStr);
                System.out.println("-".repeat(25));
                System.out.println("7. List All Jobs (All Companies)"); // New
                System.out.println("8. List Active Jobs (All Companies)");
                System.out.println("0. Exit");
                System.out.print("\n  -> Enter choice: ");

                choice = scanner.nextInt();
                scanner.nextLine();

                if (choice >= 0) {
                    break;
                }
                System.out.println(" ---[ Invalid choice. ]--- ");
            } catch (InputMismatchException e) {
                System.out.println(" ---[ Invalid input. Please enter a number. ]--- ");
                scanner.nextLine();
            }
        }
        return choice;
    }

    public int getCompanyMenuChoice(Company selectedCompany) {
        int choice;
        while (true) {
            try {
                System.out.println("\n\n== COMPANY MANAGEMENT MENU =============");
                System.out.println("            " + selectedCompany.getName());
                System.out.println("-".repeat(35));
                System.out.println("   1. List all job postings");
                System.out.println("   2. Create a new job posting");
                System.out.println("   3. Update job details");
                System.out.println("   4. Remove a job posting");
                System.out.println("-".repeat(35));
                System.out.println("   5. Search Jobs");
                System.out.println("   6. Filter jobs based on criteria");
                System.out.println("   7. Generate Job Report");
                System.out.println("\n   0. Quit");
                System.out.print("\n  -> Enter choice: ");

                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (choice >= 0 && choice <= 7) {
                    break;
                }
                System.out.println(" ---[ Invalid choice. Please enter a number between 0 and 7. ]--- ");
            } catch (InputMismatchException e) {
                System.out.println(" ---[ Invalid input. Please enter a valid number. ]--- ");
                scanner.nextLine(); // Clear invalid input
            }
        }
        return choice;
    }
    
    public int getMenuChoice(int min, int max) {
        int choice;
        while(true) {
            try {
                System.out.print("\n  -> Enter choice ( " + min + " - " + max + " ): ");
                choice = scanner.nextInt();
                scanner.nextLine();

                if(choice >= min && choice <= max) {
                    return choice;
                }
                System.out.println(" ---[ Invalid choice. Please enter between " + min + "-" + max + " ]--- ");
            } catch(InputMismatchException e) {
                System.out.println(" ---[ Invalid input. Please enter a number. ]--- ");
                scanner.nextLine();
            }
        }
    }
    
    
    //-- Operation 1 : List Job -----------------------------------------------------------------------------------------------------------
    public void listAllJobs(String outputStr) {
        System.out.println(outputStr); // Print the pre-formatted job list with header
    }
    
    
    
    
    //-- Operation 2 : Add New Job --------------------------------------------------------------------------------------------------
    public String selectLocation(DoublyListInterface<String> locations) {
        if(locations.getNumberOfEntries() == 1) {
            return locations.getEntry(1); // Auto-select if only one location
        }
        System.out.println("\n=== LOCATION ==========");
        for(int i=1; i<=locations.getNumberOfEntries(); i++) {
            System.out.println("  " + i + ". " + locations.getEntry(i));
        }
        int choice = getMenuChoice(1, locations.getNumberOfEntries());
        return locations.getEntry(choice);
    }

    public Job inputJobDetails(String jobId, DoublyListInterface<Skill> skillList, String location) {
        System.out.println("\n=== NEW JOB CREATION ==================");
        System.out.println("  Current Job ID: " + jobId); // Add this line
        System.out.println("  Location: " + location);

        String title = inputJobTitle();
        double allowance = inputAllowance();
        int durationMonths = inputDuration();
        String status = inputStatus();
        
        JobRequirement jobRequirement = inputJobRequirement(jobId, skillList);

        return new Job(jobId, title, allowance, durationMonths, status, location, jobRequirement);
    }

    public String inputJobTitle() {
        System.out.println("\n-- Select Job Position:");
        System.out.println("  1. Software Engineer");
        System.out.println("  2. Data Analyst");
        System.out.println("  3. AI Developer");
        System.out.println("  4. Graphic Designer");
        System.out.println("  5. Video Editor");
        System.out.println("  6. Animator");
        int choice;
        while (true) {
            try {
                System.out.print("\n  -> Enter choice ( 1 - 8 ): ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (choice >= 1 && choice <= 6) break;
                System.out.println("\n ---[ Invalid choice. ]--- ");
            } catch (InputMismatchException e) {
                System.out.println("\n ---[ Please enter a number. ]--- ");
                scanner.nextLine();
            }
        }
        switch (choice) {
            case 1: return "Software Engineer";
            case 2: return "Data Analyst";
            case 3: return "AI Developer";
            case 4: return "Graphic Designer";
            case 5: return "Video Editor";
            case 6: return "Animator";
            default: return "Software Engineer";
        }
    }
    
    public double inputAllowance() {
        System.out.println("\n-- Select Allowance Range:");
        System.out.println("  1. 1000");
        System.out.println("  2. 1400");
        System.out.println("  3. 1800");
        System.out.println("  4. 2200");
        System.out.println("  5. 3000");
        int choice;
        while (true) {
            try {
                System.out.print("\n  -> Enter choice ( 1 - 5 ): ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (choice >= 1 && choice <= 5) break;
                System.out.println("\n ---[ Invalid choice. ]--- ");
            } catch (InputMismatchException e) {
                System.out.println("\n ---[ Please enter a number. ]--- ");
                scanner.nextLine();
            }
        }
        // Return the lower bound of the range, or use a random value in the range
        switch (choice) {
            case 1: return 1000; // 1000-1400
            case 2: return 1400; // 1400-1800
            case 3: return 1800; // 1800-2200
            case 4: return 2200; // 2200-2600
            case 5: return 3000; // 3000+
            default: return 1000;
        }
    }
    
    public int inputDuration() {
        System.out.println("\n-- Select Duration (in months):");
        System.out.println("  1. 6 months");
        System.out.println("  2. 8 months");
        System.out.println("  3. 10 months");
        System.out.println("  4. 12 months");
        int choice;
        while (true) {
            try {
                System.out.print("\n  -> Enter choice ( 1 - 4 ): ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (choice >= 1 && choice <= 4) break;
                System.out.println("\n ---[ Invalid choice. ]--- ");
            } catch (InputMismatchException e) {
                System.out.println("\n ---[ Please enter a number. ]--- ");
                scanner.nextLine();
            }
        }
        switch (choice) {
            case 1: return 6;
            case 2: return 8;
            case 3: return 10;
            case 4: return 12;
            default: return 6;
        }
    }
    
    public String inputStatus() {
        System.out.println("\n-- Select Job Status:");
        System.out.println("  1. Active");
        System.out.println("  2. Deactive");
        int choice;
        while (true) {
            try {
                System.out.print("\n  -> Enter choice ( 1 - 2 ): ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (choice >= 1 && choice <= 2) break;
                System.out.println("\n ---[ Invalid choice. ]--- ");
            } catch (InputMismatchException e) {
                System.out.println("\n ---[ Please enter a number. ]--- ");
                scanner.nextLine();
            }
        }
        switch (choice) {
            case 1: return "Active";
            case 2: return "Deactive";
            default: return "Active";
        }
    }
    
    public String inputEducation() {
        System.out.println("\n-- Select Education Requirement:");
        System.out.println("  1. Computer Science");
        System.out.println("  2. Multimedia");
        int choice;
        while (true) {
            try {
                System.out.print("\n  -> Enter choice ( 1 - 2 ): ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (choice >= 1 && choice <= 2) break;
                System.out.println("\n ---[ Invalid choice. ]--- ");
            } catch (InputMismatchException e) {
                System.out.println("\n ---[ Please enter a number. ]--- ");
                scanner.nextLine();
            }
        }
        switch (choice) {
            case 1: return "Computer Science";
            case 3: return "Multimedia";
            default: return "Computer Science";
        }
    }
    
    public double inputCGPARange() {
        System.out.println("\n-- Select Minimum Required CGPA:");
        System.out.println("  1. 2.0");
        System.out.println("  2. 2.2");
        System.out.println("  3. 2.4");
        System.out.println("  4. 2.6");
        System.out.println("  5. 2.8");
        System.out.println("  6. 3.0");
        System.out.println("  7. 3.2");
        System.out.println("  8. 3.4");
        System.out.println("  9. 3.6");
        System.out.println("  10. 3.8");
        System.out.println("  11. 4.0");
        int choice;
        while (true) {
            try {
                System.out.print("\n  -> Enter choice ( 1 - 11 ): ");
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= 1 && choice <= 11) break;
                System.out.println("\n ---[ Invalid choice. ]--- ");
            } catch (InputMismatchException e) {
                System.out.println("\n ---[ Please enter a number. ]--- ");
                scanner.nextLine();
            }
        }
        switch (choice) {
            case 1: return 2.0;
            case 2: return 2.2;
            case 3: return 2.4;
            case 4: return 2.6;
            case 5: return 2.8;
            case 6: return 3.0;
            case 7: return 3.2;
            case 8: return 3.4;
            case 9: return 3.6;
            case 10: return 3.8;
            case 11: return 4.0;
            default: return 2.0;
        }
    }
    
    public JobRequirement inputJobRequirement(String jobId, DoublyListInterface<Skill> skillList) {
        String education = inputEducation(); // Added this line to fix the error
        double cgpa = inputCGPARange();

        // Display skill options
        System.out.println("\n=== Skill Selection ===============");
        System.out.println(getFormattedSkillList(skillList)); // Display available skills

        // Select skills by ID
        System.out.print("  -> How many skills do you want to include? ");
        int skillCount = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        DoublyListInterface<Skill> selectedSkills = new DoublyLinkedList<>();
        for (int i = 1; i <= skillCount; i++) {
            System.out.println("\n\n=== Skill " + i + " ===");
            String category = selectCategory(skillList);
            String skillName = selectSkillName(skillList, category);
            String proficiency = selectProficiency();

            Skill skill = findSkill(skillList, category, skillName, proficiency);
            if (skill != null) {
                selectedSkills.add(skill);
                System.out.printf("\n  -> Added: %s - %s (%s)%n", category, skillName, proficiency);
            } else {
                System.out.println(" ---[ Skill not found. ]--- ");
                i--; // Retry
            }
        }
        System.out.println("\n=== Selected Skills Preview ==========");
        System.out.println(convertSkillsToString(selectedSkills)); // Add preview

        // Confirm before saving
        System.out.print("\n  -> Confirm to add? (Yes/No): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        if (!confirmation.equals("yes")) {
            System.out.println(" ---[ Skill selection canceled. ]--- ");
            return null; // Cancel adding job requirement
        }

        // Convert skills list to a comma-separated string
        String selectedSkillsStr = convertSkillsToString(selectedSkills);
        return new JobRequirement(education, cgpa, selectedSkillsStr);
    }
  
    private String getFormattedSkillList(DoublyListInterface<Skill> skillList) {
        StringBuilder outputStr = new StringBuilder();
        String currentCategory = "";
        int skillNumber = 1;

        Iterator<Skill> iterator = skillList.getIterator();
        while(iterator.hasNext()) {
            Skill skill = iterator.next();

            if(!skill.getCategory().equals(currentCategory)) {
                currentCategory = skill.getCategory();
                outputStr.append("\n").append(currentCategory).append(":\n");
                skillNumber = 1;
            }

                outputStr.append("\t")
                    .append(skillNumber++)
                    .append(". ")
                    .append(skill.getSkillName())
                    .append(" (")
                    .append(skill.getProficiency())
                    .append(")\n");
        }
        return outputStr.toString();
    }

    private String selectCategory(DoublyListInterface<Skill> skillList) {
        DoublyListInterface<String> categories = new DoublyLinkedList<>();
        Iterator<Skill> iter = skillList.getIterator();

        // Collect unique categories
        while (iter.hasNext()) {
            String category = iter.next().getCategory();
            if (!categories.contains(category)) {
                categories.add(category);
            }
        }

        // Sort categories alphabetically
        categories = sortAlphabetically(categories);

        // Display sorted categories 
        System.out.println("[ Categories ]");
        for (int i = 1; i <= categories.getNumberOfEntries(); i++) {
            System.out.printf(" %d. %s%n", i, categories.getEntry(i));
        }

        int choice = inputPositiveInt("\n  -> Select category: ");
        return categories.getEntry(choice);
    }
    
    private String selectSkillName(DoublyListInterface<Skill> skillList, String category) {
        DoublyListInterface<String> skillNames = new DoublyLinkedList<>();
        Iterator<Skill> iter = skillList.getIterator();

        // Filter skills by category
        while (iter.hasNext()) {
            Skill skill = iter.next();
            if (skill.getCategory().equals(category)) {
                String name = skill.getSkillName();
                if (!skillNames.contains(name)) {
                    skillNames.add(name);
                }
            }
        }

        // Sort skills alphabetically
        skillNames = sortAlphabetically(skillNames);

        System.out.println("\n [ Skills in " + category + " ]");
        for (int i = 1; i <= skillNames.getNumberOfEntries(); i++) {
            System.out.printf(" %d. %s%n", i, skillNames.getEntry(i));
        }

        int choice = inputPositiveInt("\n  -> Select skill: ");
        return skillNames.getEntry(choice);
    }

    private String selectProficiency() {
        System.out.println("\n [ Proficiency levels ]");
        System.out.println(" 1. Beginner");
        System.out.println(" 2. Intermediate");
        System.out.println(" 3. Advanced");
        int choice = inputPositiveInt("\n  -> Select proficiency: ");
        
        switch (choice) {
            case 1: return "Beginner";
            case 2: return "Intermediate";
            case 3: return "Advanced";
            default: return "Beginner";
        }
    }

    private String convertSkillsToString(DoublyListInterface<Skill> selectedSkills) {
        StringBuilder output = new StringBuilder();

        // First pass to collect all categories
        DoublyListInterface<String> allCategories = new DoublyLinkedList<>();
        Iterator<Skill> iter = selectedSkills.getIterator();
        while(iter.hasNext()) {
            String category = iter.next().getCategory();
            if(!allCategories.contains(category)) {
                allCategories.add(category);
            }
        }

        // Sort categories alphabetically
        allCategories = sortAlphabetically(allCategories);

        // Second pass to build output with spacing
        int categoryCount = allCategories.getNumberOfEntries();
        int currentIndex = 1;

        Iterator<String> catIter = allCategories.getIterator();
        while(catIter.hasNext()) {
            String category = catIter.next();
            output.append(category).append(":\n");

            // Add skills
            Iterator<Skill> skillIter = selectedSkills.getIterator();
            while(skillIter.hasNext()) {
                Skill skill = skillIter.next();
                if(skill.getCategory().equals(category)) {
                    output.append("\t")
                          .append(skill.getSkillName())
                          .append(" (")
                          .append(skill.getProficiency())
                          .append(")\n");
                }
            }

            // Add space between categories except after last
            if(currentIndex++ < categoryCount) {
                output.append("\n");
            }
        }
        return output.toString().trim();
    }
    
    private Skill findSkill(DoublyListInterface<Skill> skillList, String category, String skillName, String proficiency) {
        Iterator<Skill> iter = skillList.getIterator();
        while (iter.hasNext()) {
            Skill skill = iter.next();
            if (skill.getCategory().equals(category) 
                && skill.getSkillName().equals(skillName) 
                && skill.getProficiency().equals(proficiency)) {
                return skill;
            }
        }
        return null;
    }
    
    
    
    
    //-- Operation 3 : Update Job --------------------------------------------------------------------------------------------------
    public int selectJobId(DoublyListInterface<Job> jobs) {
        System.out.println("\n=== UPDATE JOB ===============");
        int index = 1;
        Iterator<Job> iter = jobs.getIterator();
        while(iter.hasNext()) {
            System.out.printf("%d. %s\n", index++, iter.next().getId());
        }
        System.out.println("\n0. Cancel");
        return getMenuChoice(0, jobs.getNumberOfEntries());
    }
    
    public String selectLocationWithCurrent(DoublyListInterface<String> locations, String currentLocation) {
        System.out.println("\n=== UPDATE LOCATION ===============");

        if(locations.getNumberOfEntries() == 1) {
            return locations.getEntry(1);
        }

        
        for(int i=1; i<=locations.getNumberOfEntries(); i++) {
            System.out.printf(" %d. %s\n", i, locations.getEntry(i));
        }
        System.out.println("\n 0. Keep Current Location ( " + currentLocation + " )");
        int choice = getMenuChoice(0, locations.getNumberOfEntries());
        return (choice == 0) ? currentLocation : locations.getEntry(choice);
    }
       
    public String inputJobTitleWithCurrent(String currentTitle) {
        System.out.println("\n=== UPDATE TITLE ===============");
        System.out.println(" 1. Software Engineer");
        System.out.println(" 2. Data Analyst");
        System.out.println(" 3. AI Developer");
        System.out.println(" 4. Graphic Designer");
        System.out.println(" 5. Video Editor");
        System.out.println(" 6. Animator");
        
        System.out.println("\n 0. Keep Current (" + currentTitle + ")");

        int choice = getMenuChoice(0, 6);
        if (choice == 0) {
            return currentTitle;
        } else {
            switch (choice) {
                case 1: return "Software Engineer";
                case 2: return "Data Analyst";
                case 3: return "AI Developer";
                case 4: return "Graphic Designer";
                case 5: return "Video Editor";
                case 6: return "Animator";
                default: return currentTitle;
            }
        }
    }
    
    public Double inputAllowanceWithCurrent(double currentValue) {
        System.out.println("\n=== UPDATE ALLOWANCE ===============");
        System.out.println(" 1. 1000");
        System.out.println(" 2. 1400"); 
        System.out.println(" 3. 1800");
        System.out.println(" 4. 2200");
        System.out.println(" 5. 3000");
        System.out.println("\n 0. Keep Current ( " + currentValue + " )");

        int choice = getMenuChoice(0, 5);
        switch (choice) {
            case 0: return currentValue;
            case 1: return 1000.0;
            case 2: return 1400.0;
            case 3: return 1800.0;
            case 4: return 2200.0;
            case 5: return 3000.0;
            default: return currentValue;
        }
    }
    
    public Integer inputDurationWithCurrent(int currentValue) {
        System.out.println("\n=== UPDATE DURATION ===============");
        System.out.println(" 1. 6 months");
        System.out.println(" 2. 8 months");
        System.out.println(" 3. 10 months");
        System.out.println(" 4. 12 months");
        System.out.println("\n 0. Keep Current ( " + currentValue + " months )");

        int choice = getMenuChoice(0, 4);
        if (choice == 0) {
            return currentValue;
        } else {
            switch (choice) {
                case 1: return 6;
                case 2: return 8;
                case 3: return 10;
                case 4: return 12;
                default: return null;
            }
        }
    }

    public String selectStatus(String currentStatus) {
        System.out.println("\n=== UPDATE JOB STATUS ===============");
        System.out.println(" 1. Active");
        System.out.println(" 2. Inactive");
        System.out.println("\n 0.Keep Current Education ( " + currentStatus + " )");
        
        int choice = getMenuChoice(0, 2);
        if (choice == 0) {
            return currentStatus;
        } else {
            switch (choice) {
                case 1: return "Active";
                case 2: return "Inactive";
                default: return null;
            }
        }
    }

    public String inputEducationWithCurrent(String currentEducation) {
        System.out.println("\n=== UPDATE EDUCATION REQUIREMENT ===");
        System.out.println(" 1. Computer Science");
        System.out.println(" 2. Multimedia");
        System.out.println("\n 0. Keep Current Education ( " + currentEducation + " )");

        int choice = getMenuChoice(0, 2);
        if (choice == 0) {
            return currentEducation;
        } else {
            switch (choice) {
                case 1: return "Computer Science";
                case 2: return "Multimedia";
                default: return "";
            }
        }
    }

    public double inputCGPARangeWithCurrent(double currentCGPA) {
        System.out.println("\n=== UPDATE CGPA ===");
        System.out.println(" 1. 2.0");
        System.out.println(" 2. 2.2");
        System.out.println(" 3. 2.4");
        System.out.println(" 4. 2.6");
        System.out.println(" 5. 2.8");
        System.out.println(" 6. 3.0");
        System.out.println(" 7. 3.2");
        System.out.println(" 8. 3.4");
        System.out.println(" 9. 3.6");
        System.out.println(" 10. 3.8");
        System.out.println(" 11. 4.0");
        System.out.println("\n 0. Keep Current CGPA ( " + currentCGPA + " )" );

        int choice = getMenuChoice(0, 11);
        if (choice == 0) {
            return currentCGPA;
        } else {
            switch(choice) {
                case 1: return 2.0;
                case 2: return 2.2;
                case 3: return 2.4;
                case 4: return 2.6;
                case 5: return 2.8;
                case 6: return 3.0;
                case 7: return 3.2;
                case 8: return 3.4;
                case 9: return 3.6;
                case 10: return 3.8;
                case 11: return 4.0;
                default: return 2.0;
            }
        }
    }

    public JobRequirement updateJobRequirements(
        String jobId,
        DoublyListInterface<Skill> skillList,
        String existingSkills,
        JobRequirement currentRequirement
    ) {
        while (true) {
            System.out.println("\n=== UPDATE SKILL ===");
            System.out.println("--- Current Skills:");
            System.out.println(convertSkillsToString(parseExistingSkills(skillList, existingSkills)));

            // Ask whether to fully replace or add to current skills
            System.out.print("  -> Do you want to replace the existing skills? (yes/no): ");
            String replaceChoice = scanner.nextLine().trim().toLowerCase();

            DoublyListInterface<Skill> selectedSkills;
            if (replaceChoice.equals("yes")) {
                selectedSkills = new DoublyLinkedList<>(); // Start fresh
            } else {
                selectedSkills = parseExistingSkills(skillList, existingSkills); // Keep current + add
            }

            // Ask how many new skills to add
            System.out.print("  -> How many new skills to add? (0 to keep current): ");
            int skillCount;
            try {
                skillCount = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Invalid input. Cancelling skill update.");
                return currentRequirement;
            }

            for (int i = 0; i < skillCount; i++) {
                System.out.println("\n=== ADDING SKILL " + (i + 1) + " ===");
                String category = selectCategory(skillList);
                String skillName = selectSkillName(skillList, category);
                String proficiency = selectProficiency();

                Skill skill = findSkill(skillList, category, skillName, proficiency);
                if (skill != null) {
                    selectedSkills.add(skill);
                    System.out.printf(" -> Added: %s - %s (%s)%n", category, skillName, proficiency);
                } else {
                    System.out.println(" ---[ Skill not found. Please try again. ]--- ");
                    i--; // Retry this skill
                }
            }

            // Display new preview
            String finalSkills = convertSkillsToString(selectedSkills);
            System.out.println("\n=== NEW SKILL PREVIEW ===");
            System.out.println(finalSkills);

            // Ask for confirmation
            System.out.print("  -> Confirm to update skills (yes/no): ");
            String confirm = scanner.nextLine().trim().toLowerCase();

            if (confirm.equals("yes")) {
                // Return new JobRequirement with updated skills
                return new JobRequirement(
                    currentRequirement.getEducation(),
                    currentRequirement.getCgpa(),
                    finalSkills
                );
            } else {
                System.out.print("  -> Reselect new skills or keep current? (reselect/keep): ");
                String choice = scanner.nextLine().trim().toLowerCase();

                if (choice.equals("keep")) {
                    System.out.println("\n[ Current Skills ]");
                    System.out.println(convertSkillsToString(parseExistingSkills(skillList, existingSkills)));
                    return currentRequirement; // Keep old skills
                }

                // Else: loop back and restart the process
                System.out.println("\n~~~ Restarting skill selection... ~~~");
            }
        }
    }
    
    private DoublyListInterface<Skill> parseExistingSkills(DoublyListInterface<Skill> allSkills, String skillString) {
        DoublyListInterface<Skill> existingSkills = new DoublyLinkedList<>();
        String[] lines = skillString.split("\n");

        for(String line : lines) {
            line = line.trim();
            if(line.contains("(")) {
                String[] parts = line.split("\\(");
                String skillName = parts[0].trim();
                String proficiency = parts[1].replace(")", "").trim();

                Iterator<Skill> iter = allSkills.getIterator();
                while(iter.hasNext()) {
                    Skill s = iter.next();
                    if(s.getSkillName().equals(skillName) && 
                       s.getProficiency().equals(proficiency)) {
                        existingSkills.add(s);
                        break;
                    }
                }
            }
        }
        return existingSkills;
    }

    
    
    
    //-- Selection in update / remove job--------------------------------------------------------------------------------------------------
    public int selectJobIndex(int maxJobs) {
        int choice;
        while (true) {
            try {
                System.out.print("Enter job number to remove (1-" + maxJobs + ", 0 to cancel): ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (choice >= 0 && choice <= maxJobs) {
                    return choice;
                }
                System.out.println("Invalid choice. Enter 0 to cancel or 1-" + maxJobs);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }


    
    
    //-- Operation 4 : Remove Job --------------------------------------------------------------------------------------------------
    public String inputRemoveJobId() {
        return inputNonEmptyString("  -> Enter Job ID to remove: ");
    }

    
        
    
    //-- Operation 5 : Search Job --------------------------------------------------------------------------------------------------
    public String inputSearchKeyword() {
        return inputNonEmptyString("  -> Enter search keyword: ").toLowerCase();
    }
    
    private DoublyListInterface<String> sortAlphabetically(DoublyListInterface<String> list) {
        // Bubble sort implementation
        int n = list.getNumberOfEntries();
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j < n; j++) {
                String current = list.getEntry(j);
                String next = list.getEntry(j + 1);
                if (current.compareToIgnoreCase(next) > 0) {
                    list.replace(j, next);
                    list.replace(j + 1, current);
                }
            }
        }
        return list;
    }
    
    
    
    
    //-- Operation 6 : Filter Job --------------------------------------------------------------------------------------------------
    // For filtering (returns range string)
    public int getFilterScope() {
        System.out.println("\n=== FILTER SCOPE =====================");
        System.out.println("  1. All Jobs (Include Other Companies)");
        System.out.println("  2. Current Company Jobs");
        System.out.println("\n 0. Exit Filter");
        return getMenuChoice(0, 2); // Use new helper
    }

    public DoublyListInterface<String> getFilterCriteria(DoublyListInterface<Skill> skillList) {
        DoublyListInterface<String> filters = new DoublyLinkedList<>();
        int choice;

        do {
            System.out.println("\n=== FILTER JOB BY ===========");
            System.out.println("  1. Job Title");
            System.out.println("  2. Allowance Range");
            System.out.println("  3. Duration");
            System.out.println("  4. Education");
            System.out.println("  5. CGPA");
            System.out.println("  6. Skills");
            System.out.println(" --------------------------");
            System.out.println("  7. Apply Filters");
            System.out.println("  8. Clear Filters");
            System.out.println("\n  0. Exit");
            System.out.println("\nCurrent Filters:");
            printFilters(filters);

            choice = getMenuChoice(0, 8);

            switch(choice) {
                case 1 -> filters.add("Job Title:" + inputJobTitle());
                case 2 -> filters.add("Allowance:" + inputAllowanceRangeFilter());
                case 3 -> filters.add("Duration:" + inputDuration());
                case 4 -> filters.add("Education:" + inputEducation());
                case 5 -> filters.add("CGPA:" + inputCGPARangeFilter());
                 case 6 -> filters.add("Skills:" + inputSkillsFilter(skillList));
                case 7 -> { return filters; }
                case 8 -> { filters.clear(); System.out.println(" ~~~ Filters cleared ~~~ "); }
            }
        } while(choice != 0);

        return new DoublyLinkedList<>();
    }

    private void printFilters(DoublyListInterface<String> filters) {
        if(filters.isEmpty()) {
            System.out.println(" - No filters applied - ");
            return;
        }

        Iterator<String> iter = filters.getIterator();
        while(iter.hasNext()) {
            String filter = iter.next();
            String[] parts = filter.split(":", 2);
            String formattedValue = parts[0].equals("cgpa") ? 
                parts[1].replace("-", " ~ ") : 
                parts[1];

            System.out.printf(" - %-15s: %s%n", parts[0], formattedValue);
        }
    }
    
    public String inputAllowanceRangeFilter() {
        System.out.println("\n-- Select Allowance Range:");
        System.out.println("  1. 1000-1400");
        System.out.println("  2. 1400-1800");
        System.out.println("  3. 1800-2200");
        System.out.println("  4. 2200-2600");
        System.out.println("  5. 2600-3000");
        int choice;
        while (true) {
            try {
                System.out.print("\n  -> Enter choice ( 1 - 5 ): ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (choice >= 1 && choice <= 5) break;
                System.out.println("\n ---[ Invalid choice. ]--- ");
            } catch (InputMismatchException e) {
                System.out.println("\n ---[ Please enter a number. ]--- ");
                scanner.nextLine();
            }
        }
        // Return the lower bound of the range, or use a random value in the range
        switch (choice) {
            case 1: return "1000-1400"; 
            case 2: return "1400-1800"; 
            case 3: return "1800-2200"; 
            case 4: return "2200-2600"; 
            case 5: return "2600-3000";
            default: return "1000-1400";
        }
    }
    
    public String inputCGPARangeFilter() {
        System.out.println("\nSelect CGPA range:");
        System.out.println("1. 2.0-2.5");
        System.out.println("2. 2.5-3.0");
        System.out.println("3. 3.0-3.5");
        System.out.println("4. 3.5-3.8");
        System.out.println("5. 3.8 above");
        int choice = getMenuChoice(1, 5);

        switch(choice) {
            case 1: return "2.0-2.5";
            case 2: return "2.5-3.0";
            case 3: return "3.0-3.5";
            case 4: return "3.5-3.8";
            case 5: return "3.8-4.0";
            default: return "2.0-2.5";
        }
    }

    public String inputSkillsFilter(DoublyListInterface<Skill> skillList) {
        System.out.println("\n=== SELECT REQUIRED SKILLS ===");
        System.out.println(getFormattedSkillList(skillList));

        DoublyListInterface<Skill> selectedSkills = new DoublyLinkedList<>();
        System.out.print("  -> How many skills to filter? ");
        int count = scanner.nextInt();
        scanner.nextLine();

        for(int i=0; i<count; i++) {
            String category = selectCategory(skillList);
            String skillName = selectSkillName(skillList, category);
            selectedSkills.add(new Skill(category, skillName, "Any"));
        }

        return convertSkillsToString(selectedSkills);
    }
    
    
    
    //-- Operation 7 : Report --------------------------------------------------------------------------------------------------
    public void displayJobReport(String report) {
        System.out.println(report);
        askToSaveReport(report);
    }

    public void askToSaveReport(String report) {
        String choice;
        while (true) {
            System.out.print("\n  -> Would you like to save this report to a file? (yes/no): ");
            choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("yes") || choice.equals("no")) {
                break;
            }
            System.out.println("\n ---[ Invalid input. Please enter 'yes' or 'no'. ]--- ");
        }

        if (choice.equals("yes")) {
            saveReportToFile(report);
            System.out.println("\n ~~~ Report saved successfully! ~~~ ");
        } else {
            System.out.println("\n ---[ Report was not saved. ]--- ");
        }
    }

    public void saveReportToFile(String report) {
        try (PrintWriter writer = new PrintWriter(new File("JobReport.txt"))) {
            writer.println(report);
            System.out.println("\n ~~~ Report has been saved as 'JobReport.txt'. ~~~ ");
        } catch (IOException e) {
            System.out.println("\n ---[ Error saving report: " + e.getMessage()+ " ]--- ");
        }
    }
    
    
    
    
    //-- Others --------------------------------------------------------------------------------------------------------------------------
    private String inputNonEmptyString(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println(" ---[ This field cannot be empty. ]--- ");
            }
        } while (input.isEmpty());
        return input;
    }

    private String inputEmptyString(String prompt) {
        String input;
        System.out.print(prompt);
        input = scanner.nextLine().trim();
        return input;
    }

    public String inputJobId() {
        return inputNonEmptyString("\n  -> Enter Job ID: ");
    }

    public String inputCompanyName() {
        return inputNonEmptyString("  -> Enter Company Name: ");
    }

    public String inputCompanyName(Boolean allowEmpty) {
        return inputEmptyString("  -> Enter Company Name: ");
    }
    
    public String inputJobLocation(Boolean allowEmpty) {
        return inputEmptyString("  -> Enter Job Location: ");
    }

    private int inputPositiveInt(String prompt) {
        int num;
        while (true) {
            try {
                System.out.print(prompt);
                num = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (num > 0) {
                    return num;
                }
                System.out.println(" ---[ Please enter a positive number. ]--- ");
            } catch (InputMismatchException e) {
                System.out.println(" ---[ Invalid input. Please enter a valid number. ]--- ");
                scanner.nextLine(); // Clear buffer
            }
        }
    }

    public Double inputOptionalDouble(String prompt) {
        System.out.print("  -> " + prompt + " (Press Enter to skip): ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return null; // Return null if skipped
        }
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println(" ---[ Invalid input. Please enter a valid number. ]--- ");
            return inputOptionalDouble(prompt); // Recursively ask again
        }
    }

    public Integer inputOptionalInt(String prompt) {
        System.out.print("  -> " + prompt + " (Press Enter to skip): ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return null; // Return null if skipped
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println(" ---[ Invalid input. Please enter a valid number. ]---  ");
            return inputOptionalInt(prompt); // Recursively ask again
        }
    }

    public boolean confirmAction(String message) {
        while (true) {
            System.out.print("  -> " + message + " (yes/no): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("yes")) {
                return true;
            } else if (choice.equals("no")) {
                return false;
            } else {
                System.out.println(" !-[ Invalid input. Please enter 'yes' or 'no'. ]--- ");
            }
        }
    }  
}

