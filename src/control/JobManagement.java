//Student1: Eugenia Tang Yi Jia
package control;

import adt.DoublyLinkedList;
import adt.DoublyListInterface;
import boundary.JobManagementUI;
import dao.InitialiserJava;
import entity.Applicant;
import entity.Company;
import entity.Job;
import entity.JobRequirement;
import entity.Skill;
import utility.MessageUI;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author yijia
 */
public class JobManagement {
    private DoublyListInterface<Job> jobList = new DoublyLinkedList<>();
    private DoublyListInterface<Skill> skillList = new DoublyLinkedList<>();
    private DoublyListInterface<Company> companyList = new DoublyLinkedList<>();
    private DoublyListInterface<Applicant> applicantList = new DoublyLinkedList<>();
    private final JobManagementUI jobUI = new JobManagementUI();
    private Company selectedCompany;

    public JobManagement() {
        InitialiserJava initializer = new InitialiserJava();
        initializer.initializeData();
        
        // Get the hardcoded data directly from DataInitializer
        jobList = initializer.getJobList();
        companyList = initializer.getCompanyList();
        skillList = initializer.getSkillList();
        applicantList = initializer.getApplicantList(); 
    }

    //-- COMPANY MENU-----------------------------------------------------------------------------------------------------------------------------------------
    public void selectCompanyMenu() {
        int choice;
        do {
            System.out.println("");
            String companyListStr = getAllCompanies();
            int companyCount = companyList.getNumberOfEntries();
            // Use the new UI method with extended options
            choice = jobUI.getCompanyChoice(companyListStr);
        
        if (choice > 0 && choice <= companyCount) { // Company selected
            selectedCompany = companyList.getEntry(choice);
                System.out.println("\n You selected: " + selectedCompany.getName());
                companyMenu(selectedCompany);
            }else if (choice == 7 || choice == 8) {
                handleAllCompanyJobOptions(choice); // New method for options 7/8
            }
        } while (choice != 0);
    }

    public void companyMenu(Company selectedCompany) {
        int choice;
        do {
            choice = jobUI.getCompanyMenuChoice(selectedCompany);
            switch (choice) {
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                    //System.out.println(getAllSkills());
                    jobUI.listAllJobs(getAllJobsByCompany());
                    break;
                case 2:
                    addNewJob();
                    jobUI.listAllJobs(getAllJobsByCompany());
                    break;
                case 3:
                    updateJob();
                    break;
                case 4:
                    jobUI.listAllJobs(getAllJobsByCompany());
                    removeJob();
                    break;
                case 5:
                    searchJobs();
                    break;
                case 6:
                    filterJobs();
                    break;
                case 7:
                    generateReport();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    private void handleAllCompanyJobOptions(int choice) {
        DoublyListInterface<Job> jobs;
        String header;

        if (choice == 7) {
            jobs = getAllJobsList();
            header = "ALL JOBS (ALL COMPANIES)";
        } else { 
            jobs = getActiveJobsAllCompanies();
            header = "ACTIVE JOBS (ALL COMPANIES)";
        }

        DoublyListInterface<Job> sortedJobs = sortJobsByTitle(jobs);
        
        if (sortedJobs.isEmpty()) {
            System.out.println(" ---[ No jobs found. ]--- ");
        } else {
            System.out.println("\n=== " + header + " ===\n");
            System.out.println(formatJobList(sortedJobs));
        }
    }
    
    private DoublyListInterface<Job> getActiveJobsAllCompanies() {
        DoublyListInterface<Job> activeJobs = new DoublyLinkedList<>();
        Iterator<Job> iter = getAllJobsList().getIterator();
        while (iter.hasNext()) {
            Job job = iter.next();
            if (job.getStatus().equalsIgnoreCase("Active")) {
                activeJobs.add(job);
            }
        }
        return activeJobs;
    }
    
    private DoublyListInterface<Job> sortJobsByTitle(DoublyListInterface<Job> jobs) {
        DoublyListInterface<Job> sortedJobs = new DoublyLinkedList<>();
        Iterator<Job> iter = jobs.getIterator();

        while (iter.hasNext()) {
            Job job = iter.next();
            insertJobSortedByTitle(sortedJobs, job);
        }

        return sortedJobs;
    }
    
    
    //-- OPERATION 1 : LIST JOB ---------------------------------------------------------------------------------------------------
    private String formatJobList(DoublyListInterface<Job> jobs) {
        StringBuilder sb = new StringBuilder();
        String header = String.format("%-8s | %-25s | %10s | %6s | %-10s | %-15s | %-15s | %-20s | %6s | %-40s",
            "Job ID", "Title", "Allowance", "Months", "Status", "Location", "Company", "Education", "CGPA", "Skills");
        String separator = "-".repeat(180); 

        sb.append(separator).append("\n")
          .append(header).append("\n")
          .append(separator).append("\n");

        Iterator<Job> iter = jobs.getIterator();
        while(iter.hasNext()) {
            Job job = iter.next();
            JobRequirement req = job.getRequirement();
            String skills = formatSkillsByCategory(req != null ? req.getSkills() : "");
            String[] skillLines = skills.split("\n");

            // Main job row
            sb.append(String.format("%-8s | %-25s | %10.2f | %6d | %-10s | %-15s | %-15s | %-20s | %6.2f | %-40s",
                job.getId(),
                job.getTitle(),
                job.getAllowance(),
                job.getDurationMonths(),
                job.getStatus(),
                job.getLocation(),
                getCompanyNameForJob(job), // Changed from selectedCompany.getName()
                (req != null) ? req.getEducation() : "N/A",
                (req != null) ? req.getCgpa() : 0.0,
                skillLines.length > 0 ? skillLines[0] : ""))
              .append("\n");

            // Additional skill lines with proper indentation
            for(int i = 1; i < skillLines.length; i++) {
                sb.append(String.format("%-8s | %-25s | %10s | %6s | %-10s | %-15s | %-15s | %-20s | %6s | %-40s",
                    "", "", "", "", "", "", "", "", "", skillLines[i]))
                  .append("\n");
            }
            sb.append(separator).append("\n");
        }
        return sb.toString();
    }
    
    private String formatSkillsByCategory(String skills) {
        if(skills == null || skills.isEmpty()) return "N/A";

        StringBuilder formatted = new StringBuilder();
        String currentCategory = "";
        boolean firstCategory = true;

        String[] lines = skills.split("\n");
        for(String line : lines) {
            line = line.trim();
            if(line.contains(":")) {
                if(!firstCategory) formatted.append("\n");
                currentCategory = line.replace(":", "").trim();
                formatted.append(currentCategory).append(":");
                firstCategory = false;
            } else {
                formatted.append("\n\t").append(line);
            }
        }
        return formatted.toString();
    }
    
   
    //-- OPERATION 2 : ADD JOB ---------------------------------------------------------------------------------------------------
    private String generateJobId() {
        int maxId = 0;
        Iterator<Job> iterator = jobList.getIterator();
        while (iterator.hasNext()) {
            String id = iterator.next().getId();
            if (id.startsWith("J1")) {
                try {
                    // Extract numeric part after "J1"
                    int numericId = Integer.parseInt(id.substring(2));
                    if (numericId > maxId) maxId = numericId;
                } catch (NumberFormatException ignored) {}
            }
        }
        return "J1" + String.format("%03d", maxId + 1);
    }

    public void addNewJob() {
        String jobId = generateJobId();
        DoublyListInterface<String> companyLocations = selectedCompany.getLocations();

        // Get location from UI
        String location = jobUI.selectLocation(companyLocations);

        // Create job with selected location
        Job newJob = jobUI.inputJobDetails(jobId, skillList, location);
        selectedCompany.getJobList().add(newJob);
    }

    
    //-- OPERATION 3 : UPDATE ---------------------------------------------------------------------------------------------------
    public void updateJob() {
        if (selectedCompany == null) {
            System.out.println(" ---[ No company selected! Please select a company first. ]--- ");
            return;
        }

        DoublyListInterface<Job> companyJobs = selectedCompany.getJobList();
        jobUI.listAllJobs(getAllJobsByCompany());

        int jobChoice = jobUI.selectJobId(companyJobs);
        if (jobChoice == 0) return;

        Job jobToUpdate = companyJobs.getEntry(jobChoice);
        JobRequirement requirement = jobToUpdate.getRequirement();

        // === Store old values as reference
        String oldTitle = jobToUpdate.getTitle();
        double oldAllowance = jobToUpdate.getAllowance();
        int oldDuration = jobToUpdate.getDurationMonths();
        String oldStatus = jobToUpdate.getStatus();
        String oldLocation = jobToUpdate.getLocation();
        String oldEducation = requirement.getEducation();
        double oldCgpa = requirement.getCgpa();
        String oldSkills = requirement.getSkills();

        // === Store new editable values in variables
        String newTitle = jobUI.inputJobTitleWithCurrent(oldTitle);
        double newAllowance = jobUI.inputAllowanceWithCurrent(oldAllowance);
        int newDuration = jobUI.inputDurationWithCurrent(oldDuration);
        String newStatus = jobUI.selectStatus(oldStatus);
        String newLocation = jobUI.selectLocationWithCurrent(selectedCompany.getLocations(), oldLocation);
        String newEducation = jobUI.inputEducationWithCurrent(oldEducation);
        double newCgpa = jobUI.inputCGPARangeWithCurrent(oldCgpa);

        JobRequirement updatedRequirement = jobUI.updateJobRequirements(
            jobToUpdate.getId(), skillList, oldSkills, 
            new JobRequirement(newEducation, newCgpa, oldSkills)
        );
        String newSkills = updatedRequirement.getSkills();

        // === Display preview of all new values
        while (true) {
            System.out.println("\n=== JOB UPDATE PREVIEW ===");
            System.out.println("1. Title      : " + newTitle);
            System.out.println("2. Allowance  : " + newAllowance);
            System.out.println("3. Months     : " + newDuration);
            System.out.println("4. Status     : " + newStatus);
            System.out.println("5. Location   : " + newLocation);
            System.out.println("6. Company    : " + selectedCompany.getName());
            System.out.println("7. Education  : " + newEducation);
            System.out.println("8. CGPA       : " + newCgpa);
            System.out.println("9. Skill      :\n" + newSkills);

            System.out.print("\n -> Confirm to update all the details (yes / no): ");
            String confirm = new Scanner(System.in).nextLine().trim().toLowerCase();

            if (confirm.equals("yes")) {
                // Apply updates to the actual job object
                jobToUpdate.setTitle(newTitle);
                jobToUpdate.setAllowance(newAllowance);
                jobToUpdate.setDurationMonths(newDuration);
                jobToUpdate.setStatus(newStatus);
                jobToUpdate.setLocation(newLocation);
                jobToUpdate.setRequirement(new JobRequirement(newEducation, newCgpa, newSkills));

                System.out.println("\n~~~ Job updated successfully! ~~~");
                jobUI.listAllJobs(getAllJobsByCompany());
                return;
            } else {
                System.out.println("\n=== MODIFY WHICH DETAIL ===");
                System.out.println("1. Title - (" + newTitle + ")");
                System.out.println("2. Allowance - (" + newAllowance + ")");
                System.out.println("3. Months - (" + newDuration + ")");
                System.out.println("4. Status - (" + newStatus + ")");
                System.out.println("5. Location - (" + newLocation + ")");
                System.out.println("6. Company - (" + selectedCompany.getName() + ")");
                System.out.println("7. Education - (" + newEducation + ")");
                System.out.println("8. CGPA - (" + newCgpa + ")");
                System.out.println("9. Skill -");
                System.out.println(newSkills);
                System.out.println("0. Clear all updates and go back");

                System.out.print("\n -> Enter which to modify (0 - 9): ");
                int modifyChoice;
                try {
                    modifyChoice = new Scanner(System.in).nextInt();
                } catch (Exception e) {
                    System.out.println("Invalid input.");
                    continue;
                }

                switch (modifyChoice) {
                    case 0:
                        System.out.println(" ~~~ All changes discarded. Returning to menu. ~~~");
                        return;
                    case 1:
                        newTitle = jobUI.inputJobTitleWithCurrent(newTitle);
                        break;
                    case 2:
                        newAllowance = jobUI.inputAllowanceWithCurrent(newAllowance);
                        break;
                    case 3:
                        newDuration = jobUI.inputDurationWithCurrent(newDuration);
                        break;
                    case 4:
                        newStatus = jobUI.selectStatus(newStatus);
                        break;
                    case 5:
                        newLocation = jobUI.selectLocationWithCurrent(selectedCompany.getLocations(), newLocation);
                        break;
                    case 6:
                        System.out.println("Company cannot be changed here."); // optional: prevent or implement
                        break;
                    case 7:
                        newEducation = jobUI.inputEducationWithCurrent(newEducation);
                        break;
                    case 8:
                        newCgpa = jobUI.inputCGPARangeWithCurrent(newCgpa);
                        break;
                    case 9:
                        updatedRequirement = jobUI.updateJobRequirements(
                            jobToUpdate.getId(), skillList, newSkills,
                            new JobRequirement(newEducation, newCgpa, newSkills)
                        );
                        newSkills = updatedRequirement.getSkills();
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        }
    }

    
    //-- OPERATION 4 : REMOVE ---------------------------------------------------------------------------------------------------
    public boolean removeJob() {
        if (selectedCompany == null) {
            System.out.println(" ---[ No company selected! ]--- ");
            return false;
        }

        DoublyListInterface<Job> jobs = selectedCompany.getJobList();
        if (jobs.isEmpty()) {
            System.out.println(" ---[ No jobs to remove for this company. ]--- ");
            return false;
        }

        // Display job list (compact format)
        System.out.println("\n=== REMOVE JOB ===");
        for (int i = 1; i <= jobs.getNumberOfEntries(); i++) {
            Job job = jobs.getEntry(i);
            System.out.printf("%2d. [%s] %s (%s)\n", i, job.getId(), job.getTitle(), job.getStatus());
        }
        System.out.println("\n 0. Cancel");

        int choice = jobUI.getMenuChoice(0, jobs.getNumberOfEntries());

        if (choice == 0) {
            System.out.println(" ~~~ Job removal canceled. ~~~");
            return false;
        }

        Job jobToRemove = jobs.getEntry(choice);
        System.out.println("\nYou selected to remove:");
        System.out.printf(" [%s] %s\n", jobToRemove.getId(), jobToRemove.getTitle());

        if (jobUI.confirmAction("\n -> Are you sure you want to remove this job?")) {
            jobs.remove(choice);
            System.out.println("\n ~~~ Job removed successfully! ~~~");
            jobUI.listAllJobs(getAllJobsByCompany());
            return true;
        } else {
            System.out.println("\n ---[ Job removal aborted. ]--- ");
            return false;
        }
    }
    
    //-- OPERATION 5 : SEARCH ---------------------------------------------------------------------------------------------------
    public void searchJobs() {
        String keyword = jobUI.inputSearchKeyword().toLowerCase();
        DoublyListInterface<Job> results = new DoublyLinkedList<>();

        Iterator<Job> iterator = jobList.getIterator();
        while (iterator.hasNext()) {
            Job job = iterator.next();
            int rank = calculateRank(job, keyword);

            if (rank > 0) {  // Only add relevant jobs
                insertSorted(results, job, rank);
            }
        }

        // Display results
        if (results.getNumberOfEntries() == 0) {
            System.out.println(" ---[ No jobs found matching: " + keyword + " ]--- ");
        } else {
            System.out.println(formatSearchResults(results, keyword));
        }
    }
   
    //Formatted Job By Search - Title
    private String formatSearchResults(DoublyListInterface<Job> jobs, String keyword) {
        return "\n=== SEARCH RESULTS FOR '" + keyword + "' ===\n" + 
               formatJobList(jobs) + "\n";
    }
    
    //Sorting For Search
    private void insertSorted(DoublyListInterface<Job> list, Job job, int rank) {
        int size = list.getNumberOfEntries();
        int position = 1; // Start at 1 (1-based index)

        for (int i = 1; i <= size; i++) {
            Job existingJob = list.getEntry(i);
            if (calculateRank(existingJob, job.getTitle()) < rank) {
                break; // Insert at this position
            }
            position++;
        }

        list.add(position, job); // Insert in sorted order
    }

    private int calculateRank(Job job, String keyword) {
        int score = 0;

        // Check direct matches (Highest Priority)
        if (job.getTitle().equalsIgnoreCase(keyword) || job.getStatus().equalsIgnoreCase(keyword)) {
            return 100; // Return max score for exact matches
        }

        // Check for partial matches
        if (containsIgnoreCase(job.getTitle(), keyword) || containsIgnoreCase(job.getStatus(), keyword)) {
            score += 50; // Medium priority for partial matches
        }

        // Include job requirements in ranking
        JobRequirement req = job.getRequirement();
        if (req != null) {
            if (containsIgnoreCase(req.getEducation(), keyword)
                    || containsIgnoreCase(req.getSkills(), keyword)) {
                score += 30;
            }
            score += fuzzyMatchScore(req.getEducation(), keyword);
            score += fuzzyMatchScore(req.getSkills(), keyword);
        }
        // Fuzzy match for title and status
        score += fuzzyMatchScore(job.getTitle(), keyword);
        score += fuzzyMatchScore(job.getStatus(), keyword);

        return score;
    }

    private int fuzzyMatchScore(String field, String keyword) {
        if (field == null) {
            return 0;
        }
        int distance = levenshteinDistance(field.toLowerCase(), keyword);
        if (distance == 0) {
            return 30;
        }
        if (distance <= 2) {
            return 15;
        }
        return 0;
    }

    private int levenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }
    
    //-- OPERATION 6 : FILTER ---------------------------------------------------------------------------------------------------
    public void filterJobs() {
        int scope = jobUI.getFilterScope();
        if(scope == 0) return;

        DoublyListInterface<String> filters = jobUI.getFilterCriteria(this.skillList);
        if(filters.isEmpty()) {
            System.out.println(" ---[ No filters applied ]--- ");
            return;
        }

        DoublyListInterface<Job> sourceJobs = (scope == 1)
            ? getAllJobsList()
            : selectedCompany.getJobList();
        DoublyListInterface<Job> filteredJobs = applyFilters(sourceJobs, filters);

        if(filteredJobs.isEmpty()) {
            System.out.println(" ---[ No matching jobs found ]--- ");
        } else {
            System.out.println(formatJobList(filteredJobs));
        }
    }
    
    private DoublyListInterface<Job> applyFilters(DoublyListInterface<Job> jobs, DoublyListInterface<String> filters) {
        DoublyListInterface<Job> result = new DoublyLinkedList<>();
        Iterator<Job> jobIter = jobs.getIterator();

        while(jobIter.hasNext()) {
            Job job = jobIter.next();
            boolean matchesAll = true;

            Iterator<String> filterIter = filters.getIterator();
            while(filterIter.hasNext() && matchesAll) {
                String[] parts = filterIter.next().split(":", 2);
                matchesAll = matchesFilter(job, parts[0], parts[1]);
            }

            if(matchesAll) result.add(job);
        }

        return result;
    }

    private boolean matchesFilter(Job job, String filterType, String filterValue) {
        try {
            JobRequirement req = job.getRequirement();

            switch (filterType) {
                case "Job Title":
                    return job.getTitle().equalsIgnoreCase(filterValue);

                case "Allowance": {
                    String[] range = filterValue.split("-"); // Split the range
                    if (range.length != 2) return false;
                    try {
                        double min = Double.parseDouble(range[0]);
                        double max = Double.parseDouble(range[1]);
                        double jobAllowance = job.getAllowance();
                        return jobAllowance >= min && jobAllowance <= max;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }

                case "Duration":
                    try {
                        int selectedDuration = Integer.parseInt(filterValue);
                        return job.getDurationMonths() == selectedDuration;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                    
                case "Education":
                    return req != null && req.getEducation().equalsIgnoreCase(filterValue);

                case "CGPA": {
                    String[] range = filterValue.split("-");
                    double min = Double.parseDouble(range[0]);
                    double max = (range.length > 1) ? Double.parseDouble(range[1]) : 4.0;
                    return req != null && req.getCgpa() >= min && req.getCgpa() <= max;
                }

                case "Skills":
                    return req != null && req.getSkills().toLowerCase().contains(filterValue.toLowerCase());

                default:
                    System.out.println("Unknown filter type: " + filterType);
                    return false;
            }
        } catch (Exception e) {
            System.out.println("Error applying filter: " + e.getMessage());
            return false;
        }
    }

    //-- OPERATION 7 : REPORT ---------------------------------------------------------------------------------------------------
    public void generateReport() {
        // Select filter scope
        int scope = jobUI.getFilterScope();
        if(scope == 0) return;

        // Get filter criteria
        DoublyListInterface<String> filters = jobUI.getFilterCriteria(this.skillList);
        if(filters.isEmpty()) {
            System.out.println(" ---[ No filters applied ]--- ");
            return;
        }

        // Get appropriate job list based on scope
        DoublyListInterface<Job> sourceJobs = (scope == 1) ? 
            getAllJobsList() : 
            selectedCompany.getJobList();

        // Apply filters
        DoublyListInterface<Job> filteredJobs = applyFilters(sourceJobs, filters);

        if(filteredJobs.isEmpty()) {
            System.out.println(" ---[ No jobs match the filter criteria. ]--- ");
        } else {
            String report = formatReport(filteredJobs, scope);
            jobUI.displayJobReport(report);
        }
    }
    
    private String formatReport(DoublyListInterface<Job> jobList, int scope) {
        StringBuilder report = new StringBuilder();
        String separator = "=".repeat(126); 
        String divider = "-".repeat(126);
        String companyName = (scope == 2 && selectedCompany != null) ? selectedCompany.getName() : "ALL COMPANIES";
        report.append(separator);
        report.append("\n                                             JOB ANALYSIS REPORT FOR ").append(companyName).append("\n");
        report.append(separator);
        
        // === Summary Preparation ===
        int totalJobs = jobList.getNumberOfEntries();
        int totalApplicants = 0;
        String mostAppliedJobId = "N/A";
        int highestApplicationCount = 0;

        // Pre-pass to calculate summary data
        for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
            Job job = jobList.getEntry(i);
            int appCount = countApplicantsForJob(job.getTitle());
            totalApplicants += appCount;
            if (appCount > highestApplicationCount) {
                highestApplicationCount = appCount;
                mostAppliedJobId = job.getId();
            }
        }

        // === Add Summary Immediately After Title ===
        report.append("\n\nSUMMARY :\n");
        report.append(String.format("Total Jobs               : %d\n", totalJobs));
        report.append(String.format("Total Applicants         : %d\n", totalApplicants));
        report.append(String.format("Most Applied Job ID      : %s (%d applicants)\n", 
            mostAppliedJobId, highestApplicationCount));
       
        
        // === Distribution Counters ===
        DoublyLinkedList<String> statusDistribution = new DoublyLinkedList<>();
        DoublyLinkedList<String> skillDistribution = new DoublyLinkedList<>();

        // === Track processed companies ===
        DoublyLinkedList<String> processedCompanies = new DoublyLinkedList<>();

        // === Summary + Distribution Pass ===
        for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
            Job job = jobList.getEntry(i);

            // Status distribution
            String status = job.getStatus();
            boolean foundStatus = false;
            for (int j = 1; j <= statusDistribution.getNumberOfEntries(); j++) {
                String entry = statusDistribution.getEntry(j);
                if (entry.startsWith(status)) {
                    int count = Integer.parseInt(entry.split(":")[1].trim());
                    statusDistribution.replace(j, status + ": " + (count + 1));
                    foundStatus = true;
                    break;
                }
            }
            if (!foundStatus) statusDistribution.add(status + ": 1");

            // Skill distribution
            if (job.getRequirement() != null && job.getRequirement().getSkills() != null) {
                String[] lines = job.getRequirement().getSkills().split("\n");
                for (String line : lines) {
                    line = line.trim();
                    if (line.isEmpty() || line.endsWith(":")) continue;
                    String skillName = line.contains("(") ? line.substring(0, line.indexOf("(")).trim() : line;

                    boolean found = false;
                    for (int j = 1; j <= skillDistribution.getNumberOfEntries(); j++) {
                        String entry = skillDistribution.getEntry(j);
                        if (entry.startsWith(skillName)) {
                            int count = Integer.parseInt(entry.split(":")[1].trim());
                            skillDistribution.replace(j, skillName + ": " + (count + 1));
                            found = true;
                            break;
                        }
                    }
                    if (!found) skillDistribution.add(skillName + ": 1");
                }
            }
        }

        // === Begin Detailed Job Breakdown ===
        report.append("");

        for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
            Job job = jobList.getEntry(i);
            String company = (scope == 2 && selectedCompany != null)
                ? selectedCompany.getName()
                : getCompanyNameForJob(job);

            // Skip company if already processed
            if (processedCompanies.contains(company)) continue;
            processedCompanies.add(company);

            // === Gather jobs for this company and sort by title ===
            DoublyLinkedList<Job> companyJobs = new DoublyLinkedList<>();
            for (int j = 1; j <= jobList.getNumberOfEntries(); j++) {
                Job temp = jobList.getEntry(j);
                String tempCompany = (scope == 2 && selectedCompany != null)
                    ? selectedCompany.getName()
                    : getCompanyNameForJob(temp);
                if (tempCompany.equals(company)) {
                    insertJobSortedByTitle(companyJobs, temp);
                }
            }

            // === Print header for company ===
            report.append("\n\n[ COMPANY: ").append(company).append(" ]\n");
            report.append(divider).append("\n");
            report.append(String.format("| %-8s | %-25s | %-10s | %-8s | %-10s | %-10s | %-20s | %s |\n",
                    "Job ID", "Title", "Allowance", "Months", "Status", "CGPA", "Education", "Applicants"));
            report.append(divider).append("\n");

            for (int k = 1; k <= companyJobs.getNumberOfEntries(); k++) {
                Job entry = companyJobs.getEntry(k);
                JobRequirement req = entry.getRequirement();

                int appCount = countApplicantsForJob(entry.getTitle());
                totalApplicants += appCount;

                if (appCount > highestApplicationCount) {
                    highestApplicationCount = appCount;
                    mostAppliedJobId = entry.getId();
                }

                report.append(String.format("| %-8s | %-25s | RM%-8.2f | %-8d | %-10s | %-10.2f | %-20s | %-10d |\n",
                        entry.getId(),
                        entry.getTitle(),
                        entry.getAllowance(),
                        entry.getDurationMonths(),
                        entry.getStatus(),
                        (req != null) ? req.getCgpa() : 0.0,
                        (req != null) ? req.getEducation() : "N/A",
                        appCount
                ));
                
            }
            report.append(divider);
        }

        

        // === Status Distribution ===
        report.append("\n\nSTATUS DISTRIBUTION : \n");
        for (int i = 1; i <= statusDistribution.getNumberOfEntries(); i++) {
            String[] parts = statusDistribution.getEntry(i).split(":");
            String status = parts[0].trim();
            int count = Integer.parseInt(parts[1].trim());
            report.append(String.format("- %-10s %s\n", status, generateBarChart(count, totalJobs)));
        }

        // === Skill Distribution ===
        if (!skillDistribution.isEmpty()) {
            report.append("\n\nSKILL DEMAND : \n");
            for (int i = 1; i <= skillDistribution.getNumberOfEntries(); i++) {
                String[] parts = skillDistribution.getEntry(i).split(":");
                String skill = parts[0].trim();
                int count = Integer.parseInt(parts[1].trim());
                report.append(String.format("- %-25s %s\n", skill, generateBarChart(count, totalJobs)));
            }
        }
        report.append("\n-------------------------------------------------------- END OF REPORT --------------------------------------------------------");
        return report.toString();
    }
    
    private int countApplicantsForJob(String jobTitle) {
        int count = 0;
        for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) { // Remove parentheses
            Applicant applicant = applicantList.getEntry(i); // Remove parentheses
            for (int j = 1; j <= applicant.getJobDesiredList().getNumberOfEntries(); j++) {
                String desired = applicant.getJobDesiredList().getEntry(j).getPosition();
                if (desired.equalsIgnoreCase(jobTitle)) {
                    count++;
                    break; // One applicant counted once per job
                }
            }
        }
        
        return count;
    }
    
    private void insertJobSortedByTitle(DoublyListInterface<Job> list, Job job) {
        int position = 1;
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            if (job.getTitle().compareToIgnoreCase(list.getEntry(i).getTitle()) < 0) {
                break;
            }
            position++;
        }
        list.add(position, job);
    }

    private String getCompanyNameForJob(Job job) {
        for (int i = 1; i <= companyList.getNumberOfEntries(); i++) {
            Company company = companyList.getEntry(i);
            if (company.getJobList().contains(job)) {
                return company.getName();
            }
        }
        return "Unknown";
    }
    
    private String generateBarChart(int value, int max) {
        if (max == 0) return "";
        int width = 50;
        int barLength = (int) ((double) value / max * width);
        return "[" + repeatChar('+', barLength) + "] " + value;
    }

    private String repeatChar(char c, int length) {
        return new String(new char[length]).replace('\0', c);
    }

    //Used in Filter n Generate Report
    private DoublyListInterface<Job> getAllJobsList() {
        DoublyListInterface<Job> allJobs = new DoublyLinkedList<>();
        Iterator<Company> companyIter = companyList.getIterator();

        while(companyIter.hasNext()) {
            Company company = companyIter.next();
            Iterator<Job> jobIter = company.getJobList().getIterator();
            while(jobIter.hasNext()) {
                allJobs.add(jobIter.next());
            }
        }
        return allJobs;
    }
    
     //To Show Job By Company
    public String getAllJobsByCompany() {
        return formatJobList(selectedCompany.getJobList());
    }

    //Get Companies
    public String getAllCompanies() {
        StringBuilder outputStr = new StringBuilder();
        Iterator<Company> iterator = companyList.getIterator();
        int index = 1;

        while (iterator.hasNext()) {
            Company company = iterator.next();
            outputStr.append(index).append(". ").append(company.getName()).append("\n");
            index++;
        }
        return outputStr.toString();
    }

    private boolean containsIgnoreCase(String field, String keyword) {
        return field != null && field.toLowerCase().contains(keyword);
    }
}
