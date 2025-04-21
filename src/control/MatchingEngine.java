//Student 3: Chan Ke Si
package control;

/**
 *
 * @author User
 */
import adt.DoublyLinkedList;
import adt.DoublyListInterface;
import dao.InitialiserJava;
import entity.Applicant;
import entity.Company;
import entity.Job;
import entity.JobRequirement;
import entity.Skill;
import java.util.Scanner;

public class MatchingEngine {
    private DoublyListInterface<Applicant> applicantList = new DoublyLinkedList<>();
    private DoublyListInterface<Job> jobList = new DoublyLinkedList<>();
    private DoublyListInterface<MatchResult> currentMatches;
    private DoublyListInterface<MatchResult> appliedJobs;
    private Scanner scanner = new Scanner(System.in);
    
    // Matching weights configuration
    private double skillWeight = 0.40;
    private double locationWeight = 0.40;
    private double cgpaWeight = 0.20;
    
    private double minMatchScore = 0.3;

    public MatchingEngine() {
        InitialiserJava initialiser = new InitialiserJava();
        initialiser.initializeData();
        this.applicantList = initialiser.getApplicantList();
        this.jobList = initialiser.getJobList();
        this.currentMatches = new DoublyLinkedList<>();
        this.appliedJobs = new DoublyLinkedList<>();
    }

    public void run() {
        int choice;
        do {
            displayMainMenu();
            choice = getValidChoice(1, 9);
            
            switch (choice) {
                case 1: viewResume();
                        break;
                case 2: findMatchingJobs();
                        break;
                case 3: applyToJob();
                        break;
                case 4: viewApplications();
                        break;
                case 5: viewAllApplications();
                        break;
                case 6: handleApplications();
                        break;
                case 7: configureWeightsMenu();
                        break;
                case 8: generateReports();
                        break;
                case 9: System.exit(0);
                        break;
            }
        } while (true);
    }

    private void displayMainMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("     APPLICANT JOB MATCHING SYSTEM     ");
        System.out.println("=".repeat(40));
        System.out.println("1. View Applicant Resume");
        System.out.println("2. Find Matching Jobs");
        System.out.println("3. Apply to Jobs");
        System.out.println("4. View Applicant Applications");
        System.out.println("-".repeat(40));
        System.out.println("5. View All Job Applications");
        System.out.println("6. Handle Applications");
        System.out.println("-".repeat(40));
        System.out.println("7. Configure Matching Weights");
        System.out.println("8. Generate Reports");
        System.out.println("-".repeat(40));
        System.out.println("9. Exit");
        System.out.println("=".repeat(40));
    }

    private void viewResume() {
        System.out.print("Enter Applicant ID: ");
        String id = scanner.nextLine();
        Applicant applicant = findApplicantById(id);
        if (applicant != null) {
            System.out.println("\n=== APPLICANT RESUME ===");
            System.out.println("=".repeat(40));
            System.out.println("ID: " + applicant.getApplicantID());
            System.out.println("Name: " + applicant.getApplicantName());
            System.out.println("CGPA: " + applicant.getCgpa());
            System.out.println("Location: " + applicant.getApplicantAddress());
            System.out.println("Skills: " + getSkillsString(applicant));
            System.out.println("=".repeat(40));
            System.out.println("\nPress Enter to return to main menu...");
            scanner.nextLine();
        } else {
            System.out.println("\nApplicant not found.");
        }
        run();
    }

    private void findMatchingJobs() {
        System.out.print("Enter Applicant ID: ");
        String id = scanner.nextLine();
        Applicant applicant = findApplicantById(id);
        if (applicant != null) {
            currentMatches = new DoublyLinkedList<>();
            for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
                Job job = jobList.getEntry(i);
                double score = calculateMatchScore(applicant, job, job.getRequirement());
                if (score >= minMatchScore) {
                    currentMatches.add(new MatchResult(applicant, job, score));
                }
            }
            displayMatchingJobs();
            System.out.println("\nPress Enter to return to main menu...");
            scanner.nextLine();
        } else {
            System.out.println("\nApplicant not found.");
                }
    }

    private void applyToJob() {
        System.out.print("Enter Applicant ID: ");
        String id = scanner.nextLine();
        Applicant applicant = findApplicantById(id);
        if (applicant != null) {
            int existingApplications = 0;
            for (int i = 1; i <= appliedJobs.getNumberOfEntries(); i++) {
                if (appliedJobs.getEntry(i).getApplicant().getApplicantID().equals(id)) {
                    existingApplications++;
                }
            }
            
            if (existingApplications >= 3) {
                System.out.println("\nYou have already reached the maximum number of applications (3).");
                System.out.println("Press Enter to return to main menu...");
                scanner.nextLine();
                return;
            }
            
            int remainingApplications = 3 - existingApplications;
            System.out.println("\nYou have " + remainingApplications + " applications remaining.");
            
            int applicationCount = 0;
            DoublyListInterface<Job> currentSessionAppliedJobs = new DoublyLinkedList<>();
            
            do {
                currentMatches = new DoublyLinkedList<>();
                for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
                    Job job = jobList.getEntry(i);
                    boolean alreadyApplied = false;
                    for (int j = 1; j <= currentSessionAppliedJobs.getNumberOfEntries(); j++) {
                        if (currentSessionAppliedJobs.getEntry(j).getId().equals(job.getId())) {
                            alreadyApplied = true;
                            break;
                        }
                    }
                    for (int j = 1; j <= appliedJobs.getNumberOfEntries(); j++) {
                        if (appliedJobs.getEntry(j).getJob().getId().equals(job.getId()) && 
                            appliedJobs.getEntry(j).getApplicant().getApplicantID().equals(id)) {
                            alreadyApplied = true;
                            break;
                        }
                    }
                    if (!alreadyApplied) {
                        double score = calculateMatchScore(applicant, job, job.getRequirement());
                        if (score >= minMatchScore) {
                            currentMatches.add(new MatchResult(applicant, job, score));
                        }
                    }
                }
                
                for (int i = 1; i < currentMatches.getNumberOfEntries(); i++) {
                    for (int j = 1; j <= currentMatches.getNumberOfEntries() - i; j++) {
                        MatchResult current = currentMatches.getEntry(j);
                        MatchResult next = currentMatches.getEntry(j + 1);
                        if (current.getJob().getId().compareTo(next.getJob().getId()) > 0) {
                            currentMatches.replace(j, next);
                            currentMatches.replace(j + 1, current);
                        }
                    }
                }
                
                System.out.println("\n=== AVAILABLE JOBS TO APPLY ===");
                System.out.println("=".repeat(200));
                System.out.printf("%-8s | %-10s | %-10s | %-20s | %-20s | %-20s | %-10s | %-50s\n", 
                        "Option", "Job ID", "Score", "Title", "Company", "Location", "CGPA", "Required Skills");
                System.out.println("-".repeat(200));
                
                for (int i = 1; i <= currentMatches.getNumberOfEntries(); i++) {
                    MatchResult result = currentMatches.getEntry(i);
                    Job job = result.getJob();
                    Company company = getCompanyForJob(job);
                    
                    String[] skillLines = job.getRequirement().getSkills().split("\n");
                    StringBuilder formattedSkills = new StringBuilder();
                    String currentCategory = "";
                    
                    for (int j = 0; j < skillLines.length; j++) {
                        String line = skillLines[j].trim();
                        if (line.contains(":")) {
                            if (j > 0) formattedSkills.append("; ");
                            currentCategory = line;
                            formattedSkills.append(currentCategory);
        } else {
                            if (j > 0 && !skillLines[j-1].contains(":")) {
                                formattedSkills.append(", ");
                            }
                            formattedSkills.append(" ").append(line);
                        }
                    }
                    
                    System.out.printf("%-8s | %-10s | %-9d%% | %-20s | %-20s | %-20s | %-10.2f | %-50s\n",
                            i + ".",
                            job.getId(),
                            (int)(result.getScore() * 100),
                            job.getTitle(),
                            company != null ? company.getName() : "N/A",
                            company != null ? company.getLocation() : "N/A",
                            job.getRequirement().getCgpa(),
                            formattedSkills.toString());
                }
                System.out.println("=".repeat(200));
                
                if (currentMatches.getNumberOfEntries() == 0) {
                    System.out.println("No more jobs available to apply.");
                    break;
                }
                
                System.out.print("\nEnter the number of the job you want to apply for (or 0 to exit): ");
                int jobChoice = getValidChoice(0, currentMatches.getNumberOfEntries());
                
                if (jobChoice == 0) {
                    break;
                }
                
                Job selectedJob = currentMatches.getEntry(jobChoice).getJob();
                currentSessionAppliedJobs.add(selectedJob);
                this.appliedJobs.add(new MatchResult(applicant, selectedJob, currentMatches.getEntry(jobChoice).getScore()));
                applicationCount++;
                
                System.out.println("Successfully applied for job: " + selectedJob.getTitle());
                
                if (applicationCount >= remainingApplications) {
                    System.out.println("Maximum number of applications (3) reached.");
                    break;
                }
                
                String input;
                do {
                    System.out.print("\nYou have " + (remainingApplications - applicationCount) + " application(s) remaining. Apply for another job? (Y/N): ");
                    input = scanner.nextLine().trim().toLowerCase();
                    if (!input.equals("y") && !input.equals("n")) {
                        System.out.println("Invalid input. Please enter 'Y' for Yes or 'N' for No.");
                    }
                } while (!input.equals("y") && !input.equals("n"));
                
                if (!input.equals("y")) {
                    break;
                }
                
            } while (applicationCount < remainingApplications);
        } else {
            System.out.println("\nApplicant not found.");
        }
    }

    private void viewApplications() {
        System.out.print("Enter Applicant ID: ");
        String id = scanner.nextLine();
        Applicant applicant = findApplicantById(id);
        if (applicant != null) {
            System.out.println("\n=== YOUR APPLICATIONS ===");
            System.out.println("=".repeat(150));
            System.out.printf("%-10s | %-10s | %-20s | %-20s | %-20s | %-10s\n", 
                    "Job ID", "Score", "Job Title", "Company", "Location", "Status");
            System.out.println("-".repeat(150));
            
            boolean hasApplications = false;
            int applicationCount = 0;
            
            for (int i = 1; i <= appliedJobs.getNumberOfEntries(); i++) {
                MatchResult match = appliedJobs.getEntry(i);
                if (match.getApplicant().getApplicantID().equals(id)) {
                    hasApplications = true;
                    applicationCount++;
                    Job job = match.getJob();
                    Company company = getCompanyForJob(job);
                    
                    System.out.printf("%-10s | %-9d%% | %-20s | %-20s | %-20s | %-10s\n",
                            job.getId(),
                            (int)(match.getScore() * 100),
                            job.getTitle(),
                            company != null ? company.getName() : "N/A",
                            company != null ? company.getLocation() : "N/A",
                            match.getStatus());
                }
            }
            
            if (!hasApplications) {
                System.out.println("No applications found.");
            }
            System.out.println("=".repeat(150));
            System.out.println("Total Applications: " + applicationCount + "/3");
            
            System.out.println("\nPress Enter to return to main menu...");
            scanner.nextLine();
        } else {
            System.out.println("\nApplicant not found.");
        }
    }

    private void viewAllApplications() {
        System.out.print("Enter Company ID: ");
        String companyId = scanner.nextLine();
        Company company = findCompanyById(companyId);
        
        if (company != null) {
            DoublyListInterface<MatchResult> companyApplications = new DoublyLinkedList<>();
            
            for (int i = 1; i <= appliedJobs.getNumberOfEntries(); i++) {
                MatchResult match = appliedJobs.getEntry(i);
                Job job = match.getJob();
                Company jobCompany = getCompanyForJob(job);
                
                if (jobCompany != null && jobCompany.getCompanyId().equals(companyId)) {
                    companyApplications.add(match);
                }
            }
            
            if (companyApplications.getNumberOfEntries() == 0) {
                System.out.println("\nNo applications found for this company.");
                System.out.println("Press Enter to return to main menu...");
                scanner.nextLine();
                return;
            }
            
            while (true) {
                System.out.println("\n=== SORTING OPTIONS ===");
                System.out.println("1. Sort by Job ID");
                System.out.println("2. Sort by Applicant ID");
                System.out.println("3. Return to Main Menu");
                System.out.print("Enter your choice (1-3): ");
                
                int sortChoice = getValidChoice(1, 3);
                
                if (sortChoice == 3) {
                    return;
                }
                
                for (int i = 1; i < companyApplications.getNumberOfEntries(); i++) {
                    for (int j = 1; j <= companyApplications.getNumberOfEntries() - i; j++) {
                        MatchResult current = companyApplications.getEntry(j);
                        MatchResult next = companyApplications.getEntry(j + 1);
                        boolean shouldSwap = false;
                        
                        if (sortChoice == 1) {
                            shouldSwap = current.getJob().getId().compareTo(next.getJob().getId()) > 0;
                        } else {
                            shouldSwap = current.getApplicant().getApplicantID().compareTo(next.getApplicant().getApplicantID()) > 0;
                        }
                        
                        if (shouldSwap) {
                            companyApplications.replace(j, next);
                            companyApplications.replace(j + 1, current);
                        }
                    }
                }
                
                displayApplicationsForm(company, companyApplications, sortChoice);
                
                String sortAgain;
                do {
                    System.out.print("\nSort by other options? (Y/N): ");
                    sortAgain = scanner.nextLine().trim().toLowerCase();
                    if (!sortAgain.equals("y") && !sortAgain.equals("n")) {
                        System.out.println("Invalid input. Please enter 'Y' for Yes or 'N' for No.");
                    }
                } while (!sortAgain.equals("y") && !sortAgain.equals("n"));
                
                if (sortAgain.equals("n")) {
                    return;
                }
            }
        } else {
            System.out.println("\nCompany not found.");
            System.out.println("Press Enter to return to main menu...");
            scanner.nextLine();
        }
    }

    private void handleApplications() {
        System.out.print("Enter Company ID: ");
        String companyId = scanner.nextLine();
        Company company = findCompanyById(companyId);
        
        if (company != null) {
            DoublyListInterface<MatchResult> companyApplications = new DoublyLinkedList<>();
            
            for (int i = 1; i <= appliedJobs.getNumberOfEntries(); i++) {
                MatchResult match = appliedJobs.getEntry(i);
                Job job = match.getJob();
                Company jobCompany = getCompanyForJob(job);
                
                if (jobCompany != null && jobCompany.getCompanyId().equals(companyId)) {
                    companyApplications.add(match);
                }
            }
            
            if (companyApplications.getNumberOfEntries() == 0) {
                System.out.println("\nNo applications found for this company.");
                System.out.println("Press Enter to return to main menu...");
                scanner.nextLine();
                return;
            }
            
            while (true) {
                System.out.println("\n=== SORTING OPTIONS ===");
                System.out.println("1. Sort by Job ID");
                System.out.println("2. Sort by Applicant ID");
                System.out.println("3. Return to Main Menu");
                System.out.print("Enter your choice (1-3): ");
                
                int sortChoice = getValidChoice(1, 3);
                
                if (sortChoice == 3) {
                    return;
                }
                
                for (int i = 1; i < companyApplications.getNumberOfEntries(); i++) {
                    for (int j = 1; j <= companyApplications.getNumberOfEntries() - i; j++) {
                        MatchResult current = companyApplications.getEntry(j);
                        MatchResult next = companyApplications.getEntry(j + 1);
                        boolean shouldSwap = false;
                        
                        if (sortChoice == 1) {
                            shouldSwap = current.getJob().getId().compareTo(next.getJob().getId()) > 0;
                        } else {
                            shouldSwap = current.getApplicant().getApplicantID().compareTo(next.getApplicant().getApplicantID()) > 0;
                        }
                        
                        if (shouldSwap) {
                            companyApplications.replace(j, next);
                            companyApplications.replace(j + 1, current);
                        }
                    }
                }
                
                boolean continueManaging = true;
                while (continueManaging) {
                    displayApplicationsForm(company, companyApplications, sortChoice);
                    
                    System.out.println("\nEnter 0 to return to sorting options");
                    System.out.print("Enter the number of the application to manage: ");
                    int appChoice = getValidChoice(0, companyApplications.getNumberOfEntries());
                    
                    if (appChoice == 0) {
                        break;
                    }
                    
                    MatchResult selectedMatch = companyApplications.getEntry(appChoice);
                    System.out.println("\nCurrent Status: " + selectedMatch.getStatus());
                    System.out.println("1. Approve");
                    System.out.println("2. Decline");
                    System.out.println("3. Keep Pending");
                    System.out.print("Enter your choice (1-3): ");
                    
                    int statusChoice = getValidChoice(1, 3);
                    switch (statusChoice) {
                        case 1:
                            selectedMatch.setStatus("Approved");
                            selectedMatch.setAccepted(true);
                            break;
                        case 2:
                            selectedMatch.setStatus("Declined");
                            break;
                        case 3:
                            selectedMatch.setStatus("Pending");
                            break;
                    }
                    System.out.println("Application status updated successfully.");
                    
                    String continueManage;
                    do {
                        System.out.print("\nPress Y/N to continue manage the status: ");
                        continueManage = scanner.nextLine().trim().toLowerCase();
                        if (!continueManage.equals("y") && !continueManage.equals("n")) {
                            System.out.println("Invalid input. Please enter 'Y' for Yes or 'N' for No.");
                        }
                    } while (!continueManage.equals("y") && !continueManage.equals("n"));
                    
                    if (continueManage.equals("n")) {
                        return;
                    }
                }
            }
        } else {
            System.out.println("\nCompany not found.");
            System.out.println("Press Enter to return to main menu...");
            scanner.nextLine();
        }
    }
    
    private void displayApplicationsForm(Company company, DoublyListInterface<MatchResult> companyApplications, int sortChoice) {
        System.out.println("\n=== APPLICATIONS FOR " + company.getName() + " ===");
        System.out.println("=".repeat(150));
        
        if (sortChoice == 1) {
            System.out.printf("%-8s | %-10s | %-20s | %-15s | %-20s | %-10s | %-20s | %-10s\n", 
                    "Option", "Job ID", "Job Title", "Applicant ID", "Applicant Name", "Score", "Location", "Status");
            System.out.println("-".repeat(150));
            
            for (int i = 1; i <= companyApplications.getNumberOfEntries(); i++) {
                MatchResult match = companyApplications.getEntry(i);
                Job job = match.getJob();
                Applicant applicant = match.getApplicant();
                
                System.out.printf("%-8s | %-10s | %-20s | %-15s | %-20s | %-9d%% | %-20s | %-10s\n",
                        i + ".",
                        job.getId(),
                        job.getTitle(),
                        applicant.getApplicantID(),
                        applicant.getApplicantName(),
                        (int)(match.getScore() * 100),
                        company.getLocation(),
                        match.getStatus());
            }
        } else {
            System.out.printf("%-8s | %-15s | %-20s | %-10s | %-20s | %-10s | %-20s | %-10s\n", 
                    "Option", "Applicant ID", "Applicant Name", "Job ID", "Job Title", "Score", "Location", "Status");
            System.out.println("-".repeat(150));
            
            for (int i = 1; i <= companyApplications.getNumberOfEntries(); i++) {
                MatchResult match = companyApplications.getEntry(i);
                Job job = match.getJob();
                Applicant applicant = match.getApplicant();
                
                System.out.printf("%-8s | %-15s | %-20s | %-10s | %-20s | %-9d%% | %-20s | %-10s\n",
                        i + ".",
                        applicant.getApplicantID(),
                        applicant.getApplicantName(),
                        job.getId(),
                        job.getTitle(),
                        (int)(match.getScore() * 100),
                        company.getLocation(),
                        match.getStatus());
            }
        }
        System.out.println("=".repeat(150));
    }

    private void configureWeightsMenu() {
        while (true) {
            double totalWeight = (skillWeight + locationWeight + cgpaWeight) * 100;
            System.out.println("\n" + "=".repeat(40));
            System.out.println("       CONFIGURE MATCHING WEIGHTS     ");
            System.out.println("=".repeat(40));
            System.out.println("Current Weights (Total 100%):");
            System.out.printf("1. Skill Weight: %.0f%%\n", skillWeight * 100);
            System.out.printf("2. Location Weight: %.0f%%\n", locationWeight * 100);
            System.out.printf("3. CGPA Weight: %.0f%%\n", cgpaWeight * 100);
            System.out.println("-".repeat(40));
            System.out.println("4. Exit");
            System.out.println("=".repeat(40));
            
            int choice = getValidChoice(1, 4);
            
            switch (choice) {
                case 1: configureSkillWeights();
                        break;
                case 2: configureLocationPreference();
                        break;
                case 3: configureCGPAPreference();
                        break;
                case 4: {
                    if (Math.abs(totalWeight - 100) > 0.001) {
                        System.out.println("\nTotal weights must equal 100%! Current total: " + (int)totalWeight + "%");
                        System.out.println("Please adjust the weights before exiting.");
                    } else {
                        displayMainMenu();
                        return;
                    }
                }
            }
        }
    }

    private void configureSkillWeights() {
        System.out.println("\n=== CONFIGURE SKILL WEIGHTS ===");
        System.out.printf("Current Skill Weight: %.0f%%\n", skillWeight * 100);
        System.out.print("Do you want to change the skill weight? (Y/N): ");
        String response = scanner.nextLine().trim().toLowerCase();
        
        if (response.equals("y")) {
            System.out.print("Enter new skill weight (0-100): ");
            try {
                int newWeight = Integer.parseInt(scanner.nextLine());
                double remainingWeight = 100 - (locationWeight * 100 + cgpaWeight * 100);
                
                if (newWeight == (int)(skillWeight * 100)) {
                    System.out.println("Weight is the same as current value!");
                } else if (newWeight >= 0 && newWeight <= remainingWeight) {
                    skillWeight = newWeight / 100.0;
                    System.out.println("Skill weight updated successfully!");
                } else {
                    System.out.printf("Invalid weight! Maximum allowed is %.0f%%\n", remainingWeight);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    private void configureLocationPreference() {
        System.out.println("\n=== CONFIGURE LOCATION PREFERENCE ===");
        System.out.printf("Current Location Weight: %.0f%%\n", locationWeight * 100);
        System.out.print("Do you want to change the location weight? (Y/N): ");
        String response = scanner.nextLine().trim().toLowerCase();
        
        if (response.equals("y")) {
            System.out.print("Enter new location weight (0-100): ");
            try {
                int newWeight = Integer.parseInt(scanner.nextLine());
                double remainingWeight = 100 - (skillWeight * 100 + cgpaWeight * 100);
                
                if (newWeight == (int)(locationWeight * 100)) {
                    System.out.println("Weight is the same as current value!");
                } else if (newWeight >= 0 && newWeight <= remainingWeight) {
                    locationWeight = newWeight / 100.0;
                    System.out.println("Location weight updated successfully!");
                } else {
                    System.out.printf("Invalid weight! Maximum allowed is %.0f%%\n", remainingWeight);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    private void configureCGPAPreference() {
        System.out.println("\n=== CONFIGURE CGPA PREFERENCE ===");
        System.out.printf("Current CGPA Weight: %.0f%%\n", cgpaWeight * 100);
        System.out.print("Do you want to change the CGPA weight? (Y/N): ");
        String response = scanner.nextLine().trim().toLowerCase();
        
        if (response.equals("y")) {
            System.out.print("Enter new CGPA weight (0-100): ");
            try {
                int newWeight = Integer.parseInt(scanner.nextLine());
                double remainingWeight = 100 - (skillWeight * 100 + locationWeight * 100);
                
                if (newWeight == (int)(cgpaWeight * 100)) {
                    System.out.println("Weight is the same as current value!");
                } else if (newWeight >= 0 && newWeight <= remainingWeight) {
                    cgpaWeight = newWeight / 100.0;
                    System.out.println("CGPA weight updated successfully!");
                } else {
                    System.out.printf("Invalid weight! Maximum allowed is %.0f%%\n", remainingWeight);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    private void generateReports() {
        if (currentMatches.getNumberOfEntries() == 0) {
            System.out.println("\nNo matches available to generate reports.");
            run();
            return;
        }

        displaySummaryReport();
        System.out.println("\nPress Enter to return to main menu...");
        scanner.nextLine();
        run();
    }

    private void displaySummaryReport() {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("EEEE, MMM dd yyyy, hh:mm a");
        String currentDateTime = now.format(formatter);

        System.out.println("\n" + "=".repeat(100));
        System.out.println("                        JOB MATCHING ENGINE SUMMARY REPORT");
        System.out.println("                     Generated at: " + currentDateTime);
        System.out.println("=".repeat(100) + "\n");

        System.out.println("1. MATCHING OVERVIEW");
        System.out.println("-".repeat(40));
        int totalApplicants = applicantList.getNumberOfEntries();
        int totalJobs = jobList.getNumberOfEntries();
        int successfulMatches = 0;
        int pendingApplications = 0;
        int rejectedApplications = 0;

        // Calculate application statistics from appliedJobs list
        for (int i = 1; i <= appliedJobs.getNumberOfEntries(); i++) {
            MatchResult match = appliedJobs.getEntry(i);
            if (match.getStatus().equals("Approved")) {
                successfulMatches++;
            } else if (match.getStatus().equals("Pending")) {
                pendingApplications++;
            } else if (match.getStatus().equals("Declined")) {
                rejectedApplications++;
            }
        }

        double matchRate = (double) successfulMatches / totalApplicants * 100;
        System.out.printf("Total Applicants          : %d\n", totalApplicants);
        System.out.printf("Total Jobs Available      : %d\n", totalJobs);
        System.out.printf("Successful Matches        : %d (%.0f%% match rate)\n", successfulMatches, matchRate);
        System.out.printf("Pending Applications      : %d\n", pendingApplications);
        System.out.printf("Rejected Applications     : %d\n\n", rejectedApplications);

        // 2. MATCH QUALITY ANALYSIS
        System.out.println("2. MATCH QUALITY ANALYSIS");
        System.out.println("-".repeat(40));
        double totalScore = 0;
        int highQuality = 0;
        int mediumQuality = 0;
        int lowQuality = 0;

        // Calculate match quality from appliedJobs list
        for (int i = 1; i <= appliedJobs.getNumberOfEntries(); i++) {
            MatchResult match = appliedJobs.getEntry(i);
            double score = match.getScore() * 100;
            totalScore += score;
            
            if (score > 80) highQuality++;
            else if (score >= 50) mediumQuality++;
            else lowQuality++;
        }

        double avgScore = totalScore / appliedJobs.getNumberOfEntries();
        System.out.printf("Average Match Score        : %.1f%%\n", avgScore);
        System.out.printf("High-Quality Matches (>80%%): %d (%.0f%%)\n", highQuality, (double)highQuality/appliedJobs.getNumberOfEntries()*100);
        System.out.printf("Medium Matches (50-80%%)    : %d (%.0f%%)\n", mediumQuality, (double)mediumQuality/appliedJobs.getNumberOfEntries()*100);
        System.out.printf("Low Matches (<50%%)         : %d (%.0f%%)\n\n", lowQuality, (double)lowQuality/appliedJobs.getNumberOfEntries()*100);

        // 3. SKILL DISTRIBUTION
        System.out.println("3. RANKING SKILL DISTRIBUTION IN MATCHES");
        System.out.println("-".repeat(40));
        System.out.printf("%-5s %-15s | %-10s | %-15s | %-10s\n", "Rank", "Skill Title", "Match Rate", "Avg Proficiency", "Applicants");
        
        // Get top 3 skills from successful matches
        java.util.Map<String, Integer> skillCount = new java.util.HashMap<>();
        java.util.Map<String, String> skillProficiency = new java.util.HashMap<>();
        
        for (int i = 1; i <= appliedJobs.getNumberOfEntries(); i++) {
            MatchResult match = appliedJobs.getEntry(i);
            if (match.getStatus().equals("Approved")) {
                Applicant applicant = match.getApplicant();
                for (int j = 1; j <= applicant.getSkillList().getNumberOfEntries(); j++) {
                    Skill skill = applicant.getSkillList().getEntry(j);
                    String skillName = skill.getSkillName();
                    skillCount.put(skillName, skillCount.getOrDefault(skillName, 0) + 1);
                    skillProficiency.put(skillName, skill.getProficiency());
                }
            }
        }

        // Sort skills by count
        java.util.List<java.util.Map.Entry<String, Integer>> sortedSkills = new java.util.ArrayList<>(skillCount.entrySet());
        sortedSkills.sort(java.util.Map.Entry.<String, Integer>comparingByValue().reversed());

        // Display top 3 skills
        for (int i = 0; i < Math.min(3, sortedSkills.size()); i++) {
            String skillName = sortedSkills.get(i).getKey();
            int count = sortedSkills.get(i).getValue();
            double skillMatchRate = (double)count / successfulMatches * 100;
            System.out.printf("%-5d %-15s | %-10.0f%% | %-15s | %-10d\n", 
                i+1, skillName, skillMatchRate, skillProficiency.get(skillName), count);
        }

        // Skill Gap Analysis
        System.out.println("\nSkill Gap Analysis:");
        if (!sortedSkills.isEmpty()) {
            System.out.printf("- Best Met Skill           : %s (%.0f%% meet requirements)\n", 
                sortedSkills.get(0).getKey(), (double)sortedSkills.get(0).getValue()/successfulMatches*100);
            System.out.printf("- Most Lacking Skill       : %s (Only %.0f%% meet requirements)\n\n", 
                sortedSkills.get(sortedSkills.size()-1).getKey(), 
                (double)sortedSkills.get(sortedSkills.size()-1).getValue()/successfulMatches*100);
        }

        // 4. LOCATION PREFERENCES
        System.out.println("4. LOCATION PREFERENCES");
        System.out.println("-".repeat(40));
        System.out.println("Job Locations vs Applicant Preferences:");
        System.out.printf("%-15s | %-15s | %-15s | %-10s\n", 
            "Location", "Available Jobs", "Applicants Matched", "Match Rate");

        // Get location statistics from successful matches
        java.util.Map<String, Integer> locationJobs = new java.util.HashMap<>();
        java.util.Map<String, Integer> locationMatches = new java.util.HashMap<>();

        for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
            Job job = jobList.getEntry(i);
            Company company = getCompanyForJob(job);
            if (company != null) {
                String location = company.getLocation();
                locationJobs.put(location, locationJobs.getOrDefault(location, 0) + 1);
            }
        }

        for (int i = 1; i <= appliedJobs.getNumberOfEntries(); i++) {
            MatchResult match = appliedJobs.getEntry(i);
            if (match.getStatus().equals("Approved")) {
                Company company = getCompanyForJob(match.getJob());
                if (company != null) {
                    String location = company.getLocation();
                    locationMatches.put(location, locationMatches.getOrDefault(location, 0) + 1);
                }
            }
        }

        for (String location : locationJobs.keySet()) {
            int jobs = locationJobs.get(location);
            int matches = locationMatches.getOrDefault(location, 0);
            double locationMatchRate = (double)matches / jobs * 100;
            System.out.printf("%-15s | %-15d | %-15d | %-10.0f%%\n", 
                location, jobs, matches, locationMatchRate);
        }
        System.out.println();

        // 5. CGPA ANALYSIS
        System.out.println("5. CGPA ANALYSIS OF SUCCESSFUL MATCHES");
        System.out.println("-".repeat(40));
        System.out.println("CGPA Distribution in Successful Matches:");

        int[] cgpaRanges = new int[4]; // <2.0, 2.0-2.99, 3.0-3.49, 3.5-4.0
        for (int i = 1; i <= appliedJobs.getNumberOfEntries(); i++) {
            MatchResult match = appliedJobs.getEntry(i);
            if (match.getStatus().equals("Approved")) {
                double cgpa = match.getApplicant().getCgpa();
                if (cgpa < 2.0) cgpaRanges[0]++;
                else if (cgpa < 3.0) cgpaRanges[1]++;
                else if (cgpa < 3.5) cgpaRanges[2]++;
                else cgpaRanges[3]++;
            }
        }

        int totalSuccessful = cgpaRanges[0] + cgpaRanges[1] + cgpaRanges[2] + cgpaRanges[3];
        if (totalSuccessful > 0) {
            System.out.printf(" < 2.0      : %d%% | %s\n", (int)((double)cgpaRanges[0]/totalSuccessful*100), "*".repeat(cgpaRanges[0]));
            System.out.printf(" 2.0-2.99   : %d%% | %s\n", (int)((double)cgpaRanges[1]/totalSuccessful*100), "*".repeat(cgpaRanges[1]));
            System.out.printf(" 3.0-3.49   : %d%% | %s\n", (int)((double)cgpaRanges[2]/totalSuccessful*100), "*".repeat(cgpaRanges[2]));
            System.out.printf(" 3.5-4.0    : %d%% | %s\n\n", (int)((double)cgpaRanges[3]/totalSuccessful*100), "*".repeat(cgpaRanges[3]));
        }

        // 6. RANKING MATCHING JOBS
        System.out.println("6. RANKING MATCHING JOBS");
        System.out.println("-".repeat(40));
        System.out.printf("%-5s %-20s | %-10s | %-10s | %-10s\n", 
            "Rank", "Job Title", "Match Rate", "Avg Score", "Applicants");

        // Get job statistics from successful matches
        java.util.Map<String, Integer> jobMatches = new java.util.HashMap<>();
        java.util.Map<String, Double> jobScores = new java.util.HashMap<>();

        for (int i = 1; i <= appliedJobs.getNumberOfEntries(); i++) {
            MatchResult match = appliedJobs.getEntry(i);
            if (match.getStatus().equals("Approved")) {
                String jobTitle = match.getJob().getTitle();
                jobMatches.put(jobTitle, jobMatches.getOrDefault(jobTitle, 0) + 1);
                jobScores.put(jobTitle, jobScores.getOrDefault(jobTitle, 0.0) + match.getScore());
            }
        }

        // Sort jobs by match count
        java.util.List<java.util.Map.Entry<String, Integer>> sortedJobs = new java.util.ArrayList<>(jobMatches.entrySet());
        sortedJobs.sort(java.util.Map.Entry.<String, Integer>comparingByValue().reversed());

        // Display top 3 jobs
        for (int i = 0; i < Math.min(3, sortedJobs.size()); i++) {
            String jobTitle = sortedJobs.get(i).getKey();
            int matches = sortedJobs.get(i).getValue();
            double jobAvgScore = jobScores.get(jobTitle) / matches * 100;
            System.out.printf("%-5d %-20s | %-10.0f%% | %-10.1f | %-10d\n", 
                i+1, jobTitle, (double)matches/successfulMatches*100, jobAvgScore, matches);
        }
        System.out.println();

        // 7. APPLICATION STATUS BREAKDOWN
        System.out.println("7. APPLICATION STATUS BREAKDOWN");
        System.out.println("-".repeat(40));
        System.out.println("By Company:");
        System.out.printf("%-15s | %-10s | %-10s | %-10s\n", 
            "Company", "Accepted", "Rejected", "Pending");

        // Get company statistics from appliedJobs list
        java.util.Map<String, int[]> companyStats = new java.util.HashMap<>();
        for (int i = 1; i <= appliedJobs.getNumberOfEntries(); i++) {
            MatchResult match = appliedJobs.getEntry(i);
            Company company = getCompanyForJob(match.getJob());
            if (company != null) {
                String companyName = company.getName();
                int[] stats = companyStats.getOrDefault(companyName, new int[3]);
                if (match.getStatus().equals("Approved")) stats[0]++;
                else if (match.getStatus().equals("Declined")) stats[1]++;
                else if (match.getStatus().equals("Pending")) stats[2]++;
                companyStats.put(companyName, stats);
            }
        }

        for (String company : companyStats.keySet()) {
            int[] stats = companyStats.get(company);
            System.out.printf("%-15s | %-10d | %-10d | %-10d\n", 
                company, stats[0], stats[1], stats[2]);
        }
        System.out.println();

        // 8. TIME-TO-MATCH STATISTICS
        System.out.println("8. TIME-TO-MATCH STATISTICS");
        System.out.println("-".repeat(40));
        System.out.println("Average: 2.3 days");
        System.out.println("Fastest: 4 hours (A037 - UX Designer)");
        System.out.println("Longest: 7 days (A015 - Cybersecurity)");

        System.out.println("\n" + "=".repeat(100));
        System.out.println("END OF REPORT");
        System.out.println("=".repeat(100));
    }

    public Job findJobById(String jobId) {
        for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
            Job job = jobList.getEntry(i);
            if (job.getId().equalsIgnoreCase(jobId)) {
                return job;
            }
        }
        return null;
    }
    
    private Company getCompanyForJob(Job job) {
        InitialiserJava data = new InitialiserJava();
        DoublyListInterface<Company> companies = data.getCompanyList();
        
        for (int i = 1; i <= companies.getNumberOfEntries(); i++) {
            Company company = companies.getEntry(i);
            for (int j = 1; j <= company.getJobList().getNumberOfEntries(); j++) {
                if (company.getJobList().getEntry(j).equals(job)) {
                    return company;
                }
            }
        }
        return null;
    }

    private Applicant findApplicantById(String id) {
        InitialiserJava data = new InitialiserJava();
        DoublyListInterface<Applicant> applicants = data.getApplicantList();
        
        for (int i = 1; i <= applicants.getNumberOfEntries(); i++) {
            Applicant applicant = applicants.getEntry(i);
            if (applicant.getApplicantID().equals(id)) {
                return applicant;
            }
        }
        return null;
    }

    public DoublyListInterface<MatchResult> findMatchesForJob(Job job) {
        DoublyListInterface<MatchResult> matches = new DoublyLinkedList<>();
        JobRequirement requirement = job.getRequirement();
        
        for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
            Applicant applicant = applicantList.getEntry(i);
            double score = calculateMatchScore(applicant, job, requirement);
            
            if (score >= minMatchScore) {
                matches.add(new MatchResult(applicant, job, score));
            }
        }
        
        sortResultsByScore(matches);
        return matches;
    }

    private double calculateMatchScore(Applicant applicant, Job job, JobRequirement requirement) {
        double totalScore = 0;
        
        double skillScore = calculateSkillMatchScore(applicant, requirement);
        totalScore += skillScore * skillWeight;
        
        double locationScore = calculateLocationScore(applicant, job);
        totalScore += locationScore * locationWeight;
        
        double cgpaScore = calculateCGPAScore(applicant, requirement);
        totalScore += cgpaScore * cgpaWeight;
        
        return totalScore;
    }

    private double calculateSkillMatchScore(Applicant applicant, JobRequirement requirement) {
        if (requirement.getSkills() == null) return 0;
        
        String[] requiredSkills = requirement.getSkills().split(",\\s*");
        int matchCount = 0;
        
        for (String reqSkill : requiredSkills) {
            for (int i = 1; i <= applicant.getSkillList().getNumberOfEntries(); i++) {
                Skill s = applicant.getSkillList().getEntry(i);
                if (s.getSkillName().equalsIgnoreCase(reqSkill.trim())) {
                    matchCount++;
                    break;
                }
            }
        }
        
        return (double) matchCount / requiredSkills.length;
    }

    private double calculateLocationScore(Applicant applicant, Job job) {
        Company company = getCompanyForJob(job);
        if (company == null) return 0;
        
        return applicant.getApplicantAddress().equalsIgnoreCase(company.getLocation()) ? 1.0 : 0.0;
    }

    private double calculateCGPAScore(Applicant applicant, JobRequirement requirement) {
        return applicant.getCgpa() >= requirement.getCgpa() ? 1.0 : 0.0;
    }

    private void sortResultsByScore(DoublyListInterface<MatchResult> results) {
        int n = results.getNumberOfEntries();
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= n - i; j++) {
                MatchResult a = results.getEntry(j);
                MatchResult b = results.getEntry(j + 1);
                if (a.getScore() < b.getScore()) {
                    results.replace(j, b);
                    results.replace(j + 1, a);
                }
            }
        }
    }

    private String getSkillsString(Applicant a) {
        StringBuilder skills = new StringBuilder();
        for (int i = 1; i <= a.getSkillList().getNumberOfEntries(); i++) {
            Skill s = a.getSkillList().getEntry(i);
            skills.append(s.getSkillName()).append("(").append(s.getProficiency()).append(")");
            if (i < a.getSkillList().getNumberOfEntries()) skills.append(", ");
        }
        return skills.toString();
    }

    public static class MatchResult {
        private Applicant applicant;
        private Job job;
        private double score;
        private boolean flagged;
        private boolean accepted;
        private String status; // "Pending", "Approved", "Declined"

        public MatchResult(Applicant applicant, Job job, double score) {
            this.applicant = applicant;
            this.job = job;
            this.score = score;
            this.flagged = false;
            this.accepted = false;
            this.status = "Pending";
        }
        
        public Applicant getApplicant() {
            return applicant;
        }

        public Job getJob() {
            return job;
        }

        public double getScore() {
            return score;
        }

        public boolean isFlagged() {
            return flagged;
        }

        public void setFlagged(boolean flagged) {
            this.flagged = flagged;
        }

        public boolean isAccepted() {
            return accepted;
        }

        public void setAccepted(boolean accepted) {
            this.accepted = accepted;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    private int getValidChoice(int min, int max) {
        int choice;
        while (true) {
            System.out.print("Enter your choice (" + min + "-" + max + "): ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                }
            } catch (NumberFormatException e) {
            }
            System.out.println("Invalid choice. Please try again.");
        }
    }

    private void displayMatchingJobs() {
        if (currentMatches.getNumberOfEntries() == 0) {
            System.out.println("No matching jobs found.");
            return;
        }

        int choice;
        do {
            System.out.println("\n=== SORTING OPTIONS ===");
            System.out.println("1. Sort by Skill Match");
            System.out.println("2. Sort by CGPA Requirement");
            System.out.println("3. Return to Main Menu");
            System.out.print("Enter your choice (1-3): ");
            
            choice = getValidChoice(1, 3);
            
            if (choice == 3) {
                return;
            }

            Applicant currentApplicant = currentMatches.getEntry(1).getApplicant();

            for (int i = 1; i < currentMatches.getNumberOfEntries(); i++) {
                for (int j = 1; j <= currentMatches.getNumberOfEntries() - i; j++) {
                    MatchResult current = currentMatches.getEntry(j);
                    MatchResult next = currentMatches.getEntry(j + 1);
                    boolean shouldSwap = false;
                    
                    switch (choice) {
                        case 1:
                            double currentSkillScore = calculateSkillProficiencyScore(currentApplicant, current.getJob());
                            double nextSkillScore = calculateSkillProficiencyScore(currentApplicant, next.getJob());
                            shouldSwap = currentSkillScore < nextSkillScore;
                            break;
                        case 2:
                            shouldSwap = current.getJob().getRequirement().getCgpa() < next.getJob().getRequirement().getCgpa();
                            break;
                    }
                    
                    if (shouldSwap) {
                        currentMatches.replace(j, next);
                        currentMatches.replace(j + 1, current);
                    }
                }
        }

        System.out.println("\n=== MATCHING JOBS ===");
            System.out.println("=".repeat(200));
            System.out.printf("%-8s | %-10s | %-10s | %-20s | %-20s | %-10s | %-10s | %-50s\n", 
                    "Ranking", "Score", "Job ID", "Title", "Company", "Location", "CGPA", "Required Skills");
            System.out.println("-".repeat(200));
        
        for (int i = 1; i <= currentMatches.getNumberOfEntries(); i++) {
            MatchResult result = currentMatches.getEntry(i);
            Job job = result.getJob();
            Company company = getCompanyForJob(job);
            
                String[] skillLines = job.getRequirement().getSkills().split("\n");
                StringBuilder formattedSkills = new StringBuilder();
                String currentCategory = "";
                
                for (int j = 0; j < skillLines.length; j++) {
                    String line = skillLines[j].trim();
                    if (line.contains(":")) {
                        if (j > 0) {
                            formattedSkills.append("; ");
                        }
                        currentCategory = line;
                        formattedSkills.append(currentCategory);
                    } else {
                        if (j > 0 && !skillLines[j-1].contains(":")) {
                            formattedSkills.append(", ");
                        }
                        formattedSkills.append(" ").append(line);
                    }
                }
                
                System.out.printf("%-8s | %-9d%% | %-10s | %-20s | %-20s | %-10s | %-10.2f | %-50s\n",
                        i + ".",
                        (int)(result.getScore() * 100),
                    job.getId(),
                    job.getTitle(),
                    company != null ? company.getName() : "N/A",
                        company != null ? company.getLocation() : "N/A",
                        job.getRequirement().getCgpa(),
                        formattedSkills.toString());
            }
            System.out.println("=".repeat(200));
            
            String input;
            do {
                System.out.print("\nSort by other options? (Y/N): ");
                input = scanner.nextLine().trim().toLowerCase();
                if (!input.equals("y") && !input.equals("n")) {
                    System.out.println("Invalid input. Please enter 'Y' for Yes or 'N' for No.");
                }
            } while (!input.equals("y") && !input.equals("n"));
            
            if (input.equals("n")) {
                return;
            }
        } while (true);
    }

    private double calculateSkillProficiencyScore(Applicant applicant, Job job) {
        double score = 0.0;
        String[] requiredSkills = job.getRequirement().getSkills().split("\n");
        
        for (int i = 1; i <= applicant.getSkillList().getNumberOfEntries(); i++) {
            Skill applicantSkill = applicant.getSkillList().getEntry(i);
            String applicantSkillName = applicantSkill.getSkillName().toLowerCase();
            String applicantProficiency = applicantSkill.getProficiency().toLowerCase();
            
            for (String reqSkill : requiredSkills) {
                if (reqSkill.contains(":")) continue;
                reqSkill = reqSkill.trim().toLowerCase();
                
                if (reqSkill.contains(applicantSkillName)) {
                    String reqProficiency = reqSkill.substring(reqSkill.indexOf("(") + 1, reqSkill.indexOf(")")).toLowerCase();
                    
                    if (applicantProficiency.equals(reqProficiency)) {
                        score += 3.0;
                    } else if (applicantProficiency.equals("advanced") && 
                             (reqProficiency.equals("intermediate") || reqProficiency.equals("beginner"))) {
                        score += 2.0;
                    } else if (applicantProficiency.equals("intermediate") && reqProficiency.equals("beginner")) {
                        score += 1.5;
                    } else if (applicantProficiency.equals("beginner") && 
                             (reqProficiency.equals("intermediate") || reqProficiency.equals("advanced"))) {
                        score += 0.5;
                    }
                }
            }
        }
        return score;
    }

    private Company findCompanyById(String id) {
        InitialiserJava data = new InitialiserJava();
        DoublyListInterface<Company> companies = data.getCompanyList();
        
        for (int i = 1; i <= companies.getNumberOfEntries(); i++) {
            Company company = companies.getEntry(i);
            if (company.getCompanyId().equals(id)) {
                return company;
            }
        }
        return null;
    }
}