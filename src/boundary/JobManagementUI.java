package boundary;

import adt.DoublyListInterface;
import adt.LinkedList;
import adt.ListInterface;
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

    public int getCompanyChoice(String companyListStr, DoublyListInterface<Company> companyList) {
        int choice;
        while (true) {
            try {
                System.out.println(companyListStr);
                System.out.println("0. Exit");
                System.out.print("\n  -> Enter choice: ");

                choice = scanner.nextInt();
                scanner.nextLine();

                if (choice >= 0 && choice < companyList.getNumberOfEntries()) {
                    break;
                }
                System.out.println(" ---[ Invalid choice. ]--- ");
            } catch (InputMismatchException e) {
                System.out.println(" ---[ Invalid input. Please enter a number. ]--- ");
                scanner.nextLine(); // Clear buffer
            }
        }
        return choice;
    }

    public int getMainMenuChoice() {
        int choice;
        while (true) {
            try {
                System.out.println("\n=== INTERNSHIP APPLICATION PROGRAM ===");
                System.out.println("1. Company Management");
                System.out.println("2. Applicant");
                System.out.println("0. Exit");
                System.out.print("\n  -> Enter choice: ");

                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (choice >= 0 && choice <= 2) {
                    break;
                }
                System.out.println(" ---[ Invalid choice. Please enter 0, 1, or 2. ]--- ");
            } catch (InputMismatchException e) {
                System.out.println(" ---[ Invalid input. Please enter a number. ]--- ");
                scanner.nextLine(); // Clear buffer
            }
        }
        return choice;
    }

    public int getCompanyMenuChoice() {
        int choice;
        while (true) {
            try {
                System.out.println("\n=== COMPANY MANAGEMENT MENU ===");
                System.out.println("1. Create a new job posting");
                System.out.println("2. Update job details");
                System.out.println("3. Remove a job posting");
                System.out.println("4. List all job postings");
                System.out.println("5. Filter jobs based on criteria");
                System.out.println("6. Generate Job Report");
                System.out.println("0. Quit");
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

    public int getApplicantMenuChoice() {
        int choice;
        while (true) {
            try {
                System.out.println("\n=== APPLICANT PORTAL ===");
                System.out.println("1. Search Jobs");
                System.out.println("2. View All Jobs");
                System.out.println("0. Back to Main Menu");
                System.out.print("\n  -> Enter choice: ");

                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (choice >= 0 && choice <= 2) {
                    break;
                }
                System.out.println(" ---[ Invalid choice. Please enter a number between 0 and 4. ]--- ");
            } catch (InputMismatchException e) {
                System.out.println(" ---[ Invalid input. Please enter a valid number. ]--- ");
                scanner.nextLine(); // Clear invalid input
            }
        }
        return choice;
    }

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

    public String inputJobTitle() {
        return inputNonEmptyString("  -> Enter Job Title: ");
    }

    public String inputCompanyName() {
        return inputNonEmptyString("  -> Enter Company Name: ");
    }

    public String inputJobLocation() {
        return inputNonEmptyString("  -> Enter Job Location: ");
    }

    public String inputJobId(Boolean allowEmpty) {
        return inputEmptyString("  -> Enter Job ID: ");
    }

    public String inputJobTitle(Boolean allowEmpty) {
        return inputEmptyString("  -> Enter Job Title: ");
    }

    public String inputCompanyName(Boolean allowEmpty) {
        return inputEmptyString("  -> Enter Company Name: ");
    }

    public String inputJobLocation(Boolean allowEmpty) {
        return inputEmptyString("  -> Enter Job Location: ");
    }

    public String inputJobType() {
        String jobType;
        while (true) {
            jobType = inputNonEmptyString("  -> Enter Job Type (Full-Time, Part-Time, Contract): ");
            if (jobType.equalsIgnoreCase("Full-Time") || jobType.equalsIgnoreCase("Part-Time") || jobType.equalsIgnoreCase("Contract")) {
                return jobType;
            }
            System.out.println(" ---[ Invalid job type. Please enter 'Full-Time', 'Part-Time', or 'Contract'. ]--- ");
        }
    }

    public String inputJobType(Boolean allowEmpty) {
        String jobType;
        while (true) {
            jobType = inputEmptyString("  -> Enter Job Type (Full-Time, Part-Time, Contract): ");
            if (jobType.equalsIgnoreCase("Full-Time") || jobType.equalsIgnoreCase("Part-Time") || jobType.equalsIgnoreCase("Contract") || jobType.isEmpty()) {
                return jobType;
            }
            System.out.println(" ---[ Invalid job type. Please enter 'Full-Time', 'Part-Time', or 'Contract'. ]--- ");
        }
    }

    private double inputPositiveDouble(String prompt) {
        double value;
        while (true) {
            try {
                System.out.print(prompt);
                value = scanner.nextDouble();
                if (value > 0) {
                    break;
                }
                System.out.println(" ---[ Salary must be a positive number. ]--- ");
            } catch (InputMismatchException e) {
                System.out.println(" ---[ Invalid input. Please enter a valid number. ]--- ");
                scanner.nextLine(); // Clear buffer
            }
        }
        scanner.nextLine(); // Consume newline
        return value;
    }

    public double inputMinSalary() {
        return inputPositiveDouble("  -> Enter Minimum Salary: ");
    }

    public double inputMaxSalary(double minSalary) { 
        double maxSalary;
        while (true) {
            maxSalary = inputPositiveDouble("  -> Enter Maximum Salary: ");
            if (maxSalary >= minSalary) {
                break;
            }
            System.out.println(" ---[ Maximum salary must be greater than or equal to minimum salary. ]--- ");
        }
        return maxSalary;
    }

    public String inputUpdateJobId() {
        return inputNonEmptyString("  -> Enter Job ID to update: ");
    }

    public String inputRemoveJobId() {
        return inputNonEmptyString("  -> Enter Job ID to remove: ");
    }

    public String inputFilterCriteria() {
        int choice;
        while (true) {
            try {
                System.out.println("\n=== FILTER JOB BY === ");
                System.out.println("1. Job Title");
                System.out.println("2. Allowance (Salary Range)");
                System.out.println("3. Duration (Months)");
                System.out.println("4. Education Requirement");
                System.out.println("5. Minimum CGPA Requirement");
                System.out.println("6. Skills Requirement");
                System.out.print("\n  -> Enter choice: ");

                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (choice >= 1 && choice <= 6) {
                    break;
                }
                System.out.println(" ---[ Invalid choice. Please enter a number between 1 and 7. ]--- ");
            } catch (InputMismatchException e) {
                System.out.println(" ---[ Invalid input. Please enter a valid number. ]--- ");
                scanner.nextLine(); // Clear buffer
            }
        }

        switch (choice) {
            case 1:
                return "title:" + inputNonEmptyString("  -> Enter job title: ");
            case 2:
                return "allowance:" + inputAllowance();
            case 3:
                return "duration:" + inputPositiveInt("  -> Enter job duration (in months): ");
            case 4:
                return "education:" + inputNonEmptyString("  -> Enter required education level: ");
            case 5:
                return "cgpa:" + inputMinCGPA();
            case 6:
                return "skills:" + inputNonEmptyString("  -> Enter required skills (comma-separated): ");
            default:
                return "invalid";
        }
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

    private double inputMinCGPA() {
        double cgpa;
        while (true) {
            try {
                System.out.print("  -> Enter minimum CGPA (0.0 - 4.0): ");
                cgpa = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                if (cgpa >= 0.0 && cgpa <= 4.0) {
                    return cgpa;
                }
                System.out.println(" ---[ CGPA must be between 0.0 and 4.0. ]--- ");
            } catch (InputMismatchException e) {
                System.out.println(" ---[ Invalid input. Please enter a valid CGPA. ]--- ");
                scanner.nextLine(); // Clear buffer
            }
        }
    }

    public void listAllJobs(String outputStr) {
        int line = 183;
        for (int i = 1; i <= line; i++) {
            System.out.print("-");
        }
        System.out.println();// %-10s %-30s %-20s %-15s %-10s %10.2f - %10.2f
        System.out.print(String.format("%-10s %-30s %-10s %-8s %-15s %-20s %-30s %-10s %-30s \n",
                "Job ID", "Title", "Allowance", "Months", "Status", "Company Name",
                "Education", "CGPA", "Skills"));

        for (int i = 1; i <= line; i++) {
            System.out.print("-");
        }

        System.out.println();

        System.out.print(outputStr);

        for (int i = 1; i <= line; i++) {
            System.out.print("-");
        }
        System.out.println("\n");
    }

    public void displayJobReport(String report) {
        System.out.println("\n=== JOB REPORT ===");
        System.out.println(report);
        askToSaveReport(report); // Ask if the user wants to save it
    }

    public void askToSaveReport(String report) {
        String choice;
        while (true) {
            System.out.print("  -> Would you like to save this report to a file? (yes/no): ");
            choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("yes") || choice.equals("no")) {
                break;
            }
            System.out.println(" ---[ Invalid input. Please enter 'yes' or 'no'. ]--- ");
        }

        if (choice.equals("yes")) {
            saveReportToFile(report);
            System.out.println(" ~~~ Report saved successfully! ~~~ ");
        } else {
            System.out.println(" ---[ Report was not saved. ]--- ");
        }
    }

    public void saveReportToFile(String report) {
        try (PrintWriter writer = new PrintWriter(new File("JobReport.txt"))) {
            writer.println(report);
            System.out.println(" ~~~ Report has been saved as 'JobReport.txt'. ~~~ ");
        } catch (IOException e) {
            System.out.println(" ---[ Error saving report: " + e.getMessage()+ " ]--- ");
        }
    }

    public String inputSearchKeyword() {
        return inputNonEmptyString("  -> Enter search keyword: ").toLowerCase();
    }

    public double inputAllowance() {
        return inputPositiveDouble("  -> Enter Allowance: ");
    }

    public int inputDuration() {
        int duration;
        while (true) {
            try {
                System.out.print("  -> Enter Duration (in months): ");
                duration = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (duration > 0) {
                    return duration;
                }
                System.out.println(" ---[ Duration must be a positive integer. ]--- ");
            } catch (InputMismatchException e) {
                System.out.println(" ---[ Invalid input. Please enter a valid number. ]--- ");
                scanner.nextLine(); // Clear buffer
            }
        }
    }

    public String inputStatus() {
        String status;
        while (true) {
            status = inputNonEmptyString("  -> Enter Job Status (Active/Inactive): ");
            if (status.equalsIgnoreCase("Active") || status.equalsIgnoreCase("Inactive")) {
                return status;
            }
            System.out.println(" ---[ Invalid status. Please enter 'Active' or 'Inactive'. ]--- ");
        }
    }

    public JobRequirement inputJobRequirement(String jobId, ListInterface<Skill> skillList) {
        String education = inputEmptyString("  -> Enter Education Requirement (or leave blank): ");

        double cgpa = 2.0;
        System.out.print("  -> Enter Minimum CGPA Requirement (or leave blank for 2.0): ");
        String cgpaInput = scanner.nextLine().trim();
        if (!cgpaInput.isEmpty()) {
            try {
                cgpa = Double.parseDouble(cgpaInput);
            } catch (NumberFormatException e) {
                System.out.println(" ---[ Invalid CGPA input. Skipping CGPA requirement. ]--- ");
            }
        }

        // Display skill options
        System.out.println("\n=== Required Skill Selection: ===");
        System.out.println(getFormattedSkillList(skillList)); // Display available skills

        // Select skills by ID
        ListInterface<Skill> selectedSkills = new LinkedList<>();
        System.out.print("  -> How many job required skills you wish to add (or leave blank): ");
        String skillCountInput = scanner.nextLine().trim();
        int skillCount = skillCountInput.isEmpty() ? 0 : Integer.parseInt(skillCountInput);

        for (int i = 1; i <= skillCount; i++) {
            System.out.print("  -> Enter your job required skill " + i + " : ");
            String skillId = scanner.nextLine().trim();

            Skill selectedSkill = getSkillById(skillList, skillId);
            if (selectedSkill != null) {
                selectedSkills.add(selectedSkill);
            } else {
                System.out.println(" ---[ Invalid Skill ID. Please try again. ]--- ");
                i--; // Ask again
            }
        }

        // Display selected skills for confirmation
        System.out.println("\n=== Selected skills: ===");
        Iterator<Skill> iterator = selectedSkills.getIterator();
        while (iterator.hasNext()) {
            Skill skill = iterator.next();
            System.out.println(skill.getId() + ". " + skill.getDescription());
        }

        // Confirm before saving
        System.out.print("\n  -> Confirm to add? (Yes/No): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        if (!confirmation.equals("yes")) {
            System.out.println(" ---[ Skill selection canceled. ]--- ");
            return null; // Cancel adding job requirement
        }

        // Convert skills list to a comma-separated string
        String selectedSkillsStr = convertSkillsToString(selectedSkills);
        return new JobRequirement(jobId, education, cgpa, selectedSkillsStr);
    }

    private Skill getSkillById(ListInterface<Skill> skillList, String skillId) {
        Iterator<Skill> iterator = skillList.getIterator();
        while (iterator.hasNext()) {
            Skill skill = iterator.next();
            if (skill.getId().equals(skillId)) {
                return skill;
            }
        }
        return null;
    }

    private String convertSkillsToString(ListInterface<Skill> selectedSkills) {
        StringBuilder skillsStr = new StringBuilder();
        Iterator<Skill> iterator = selectedSkills.getIterator();

        while (iterator.hasNext()) {
            Skill skill = iterator.next();
            skillsStr.append(skill.getId());
            if (iterator.hasNext()) {
                skillsStr.append(", ");
            }
        }

        return skillsStr.toString();
    }

    private String getFormattedSkillList(ListInterface<Skill> skillList) {
        StringBuilder outputStr = new StringBuilder();
        String currentCategory = "";

        Iterator<Skill> iterator = skillList.getIterator();
        while (iterator.hasNext()) {
            Skill skill = iterator.next();
            if (!skill.getCategory().equals(currentCategory)) {
                currentCategory = skill.getCategory();
                outputStr.append("\n").append(currentCategory).append("\n");
            }
            outputStr.append("\t").append(skill.getId()).append(". ").append(skill.getDescription()).append("\n");
        }
        return outputStr.toString();
    }

    public Job inputJobDetails(String jobId, ListInterface<Skill> skillList) {
        System.out.println("\n  -> Enter Job Details:");

        String title = inputJobTitle();
        double allowance = inputAllowance();
        int durationMonths = inputDuration();
        String status = inputStatus();
        JobRequirement jobRequirement = inputJobRequirement(jobId, skillList);

        return new Job(jobId, title, allowance,
                durationMonths, status, jobRequirement);
    }

    public boolean confirmUpdateJob() {
        while (true) {
            System.out.print("  -> Are you sure you want to update this job? (yes/no): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("yes")) {
                return true;
            } else if (choice.equals("no")) {
                return false;
            }
            System.out.println(" ---[ Invalid input. Please enter 'yes' or 'no'. ]--- ");
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
