package control;

import adt.*;
import boundary.JobManagementUI;
import dao.DataInitializer;
//import dao.CompanyDAO;
//import dao.JobDAO;
//import dao.SkillDAO;
import entity.Company;
import entity.Job;
import entity.JobRequirement;
import entity.Skill;
import utility.MessageUI;
import java.util.Iterator;

/**
 * @author yijia
 */
public class JobManagement {
    private DoublyListInterface<Job> jobList = new DoublyLinkedList<>();
    private ListInterface<Skill> skillList = new LinkedList<>();
    private DoublyListInterface<Company> companyList = new DoublyLinkedList<>();
    private final DataInitializer dataInitializer = new DataInitializer();
    //private final JobDAO jobDAO = new JobDAO();
    //private final CompanyDAO companyDAO = new CompanyDAO();
    //private final SkillDAO skillDAO = new SkillDAO();
    private final JobManagementUI jobUI = new JobManagementUI();
    private Company selectedCompany;

    public JobManagement() {
        dataInitializer.initializeData();
        
        // Get the hardcoded data directly from DataInitializer
        jobList = dataInitializer.getJobList();
        companyList = dataInitializer.getCompanyList();
        skillList = dataInitializer.getSkillList();
        //jobList = jobDAO.retrieveFromFile();
        //companyList = companyDAO.retrieveFromFile();
        //skillList = skillDAO.retrieveFromFile();
    }

    //private void saveData() {
        //jobDAO.saveToFile(jobList);
        //companyDAO.saveToFile(companyList);
    //}

    public void start() {
        int choice;
        do {
            choice = jobUI.getMainMenuChoice();
            switch (choice) {
                case 1 ->
                    selectCompanyMenu();
                case 2 ->
                    applicantMenu();
                case 0 -> {
                    System.out.println("Exiting... Thank you!");
                    return;
                }
            }
        } while (choice != 0);
    }

    public void selectCompanyMenu() {
        int choice;
        do {
            System.out.println("");
            String companyListStr = getAllCompanies();
            choice = jobUI.getCompanyChoice(companyListStr, companyList);
            if (choice != 0) {
                selectedCompany = companyList.getEntry(choice);
                System.out.println("\n You selected: " + selectedCompany.getName());
                companyMenu();
            }

        } while (choice != 0);
    }

    public void companyMenu() {
        int choice;
        do {
            choice = jobUI.getCompanyMenuChoice();
            switch (choice) {
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                    addNewJob();
                    jobUI.listAllJobs(getAllJobsByCompany());
                    break;
                case 2:
                    updateJob();
                    break;
                case 3:
                    jobUI.listAllJobs(getAllJobsByCompany());
                    removeJob();
                    break;
                case 4:
//                    System.out.println(getAllSkills());
                    jobUI.listAllJobs(getAllJobsByCompany());
                    break;
                case 5:
                    filterJobs();
                    break;
                case 6:
                    generateReport();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    public void applicantMenu() {
        int choice;
        do {
            choice = jobUI.getApplicantMenuChoice();
            switch (choice) {
                case 1 ->
                    searchJobs();
                case 2 ->
                    jobUI.listAllJobs(getAllJobs());
                case 0 -> {
                    System.out.println(" *** Returning to Main Menu... *** ");
                    return;
                }
            }
        } while (choice != 0);
    }

    public void addNewJob() {
        String jobId;

        do {
            jobId = jobUI.inputJobId();
            if (findJobById(jobId) != null) {
                System.out.println(" ---[ Error: A job with ID " + jobId + " already exists! Please enter a unique ID. ]---");
            }
        } while (findJobById(jobId) != null);

        Job newJob = jobUI.inputJobDetails(jobId, skillList);
        selectedCompany.getJobList().add(newJob);
        //saveData();
        System.out.println(" ~~~ Job added successfully! ~~~ \n");
    }

    public void updateJob() {
        if (selectedCompany == null) {
            System.out.println(" ---[ No company selected! Please select a company first. ]--- ");
            return;
        }

        jobUI.listAllJobs(getAllJobsByCompany());

        String jobId = jobUI.inputJobId();
        Job jobToUpdate = findJobByIdInCompany(jobId);

        if (jobToUpdate == null) {
            System.out.println(" ---[ Job ID not found in the selected company! ]--- ");
            return;
        }

        if (!jobUI.confirmUpdateJob()) {
            System.out.println(" ---[ Job update canceled. ]--- ");
            return;
        }

        System.out.println("\n  -> Enter new details (leave blank to keep the existing value):");

        String newTitle = jobUI.inputJobTitle(true);
        if (!newTitle.isEmpty()) {
            jobToUpdate.setTitle(newTitle);
        }

        Double newAllowance = jobUI.inputOptionalDouble("  -> Enter new allowance: ");
        if (newAllowance != null) {
            jobToUpdate.setAllowance(newAllowance);
        }

        Integer newDuration = jobUI.inputOptionalInt("  -> Enter new duration (months): ");
        if (newDuration != null) {
            jobToUpdate.setDurationMonths(newDuration);
        }

        String newStatus = jobUI.inputStatus();
        if (!newStatus.isEmpty()) {
            jobToUpdate.setStatus(newStatus);
        }

        JobRequirement newRequirement = jobUI.inputJobRequirement(jobId, skillList);
        if (newRequirement != null) {
            jobToUpdate.setRequirement(newRequirement);
        }

        //saveData();

        System.out.println(" ~~~ Job updated successfully! ~~~ \n");
    }

    private Job findJobByIdInCompany(String jobId) {
        Iterator<Job> iterator = selectedCompany.getJobList().getIterator();
        while (iterator.hasNext()) {
            Job job = iterator.next();
            if (job.getId().equalsIgnoreCase(jobId)) {
                return job;
            }
        }
        return null;
    }

    public boolean removeJob() {
        String jobId = jobUI.inputJobId();
        Job jobToRemove = findJobByIdInCompany(jobId);

        if (jobToRemove == null) {
            System.out.println(" ---[ Job ID not found in the selected company! ]--- ");
            return false;
        }

        if (!jobUI.confirmAction("  -> Are you sure you want to remove this job?")) {
            System.out.println(" ---[ Job removal canceled. ]---");
            return false;
        }

        if (selectedCompany.removeJob(jobId)) {
            System.out.println(" ~~~ Job removed successfully! ~~~ ");

            //saveData();
            return true;
        } else {
            System.out.println(" ---[ Job removal failed. ]--- ");
        }
        return false;
    }

    public void filterJobs() {
        String filterCriteria = jobUI.inputFilterCriteria();
        DoublyListInterface<Job> filteredJobs = new DoublyLinkedList<>();

        DoublyListInterface<Job> companyJobs = selectedCompany.getJobList();

        if (companyJobs.getNumberOfEntries() == 0) {
            System.out.println(" ---[ No jobs available for " + selectedCompany.getName() + " ]--- ");
            return;
        }

        Iterator<Job> iterator = companyJobs.getIterator();
        while (iterator.hasNext()) {
            Job job = iterator.next();
            if (matchesFilter(job, filterCriteria)) {
                filteredJobs.add(job);
            }
        }

        if (filteredJobs.getNumberOfEntries() == 0) {
            System.out.println(" ---[ No jobs match the filter criteria. ]--- \n");
        } else {
            System.out.println(formatJobList(filteredJobs));
        }
    }

    private String formatJobList(DoublyListInterface<Job> jobList) {
        StringBuilder report = new StringBuilder();
        report.append("------------------------------------------------------------------------------------------------------------------------------\n");
        report.append(String.format("%-10s %-30s %-10s %-8s %-15s %-10s %-25s %-30s\n",
                "Job ID", "Title", "Allowance", "Months", "Status", "CGPA", "Education", "Skills"));
        report.append("------------------------------------------------------------------------------------------------------------------------------\n");
        Iterator<Job> iterator = jobList.getIterator();
        while (iterator.hasNext()) {
            Job job = iterator.next();
            JobRequirement req = job.getRequirement(); // Get job requirement

            String cgpa = (req != null) ? String.format("%.2f", req.getCgpa()) : "N/A";
            String education = (req != null) ? req.getEducation() : "N/A";
            String skills = (req != null) ? req.getSkills() : "N/A";

            report.append(String.format("%-10s %-30s %-10s %-8s %-15s %-10s %-25s %-30s\n",
                    job.getId(), job.getTitle(), job.getAllowance(), job.getDurationMonths(),
                    job.getStatus(), cgpa, education, skills));
        }

        return report.toString();
    }

    private boolean matchesFilter(Job job, String filterCriteria) {
        if (job == null || filterCriteria == null || filterCriteria.trim().isEmpty()) {
            return false;
        }

        filterCriteria = filterCriteria.trim().toLowerCase();
        JobRequirement req = job.getRequirement(); // Fetch job requirements

        if (filterCriteria.startsWith("title:")) {
            String title = filterCriteria.substring(6).trim();
            return job.getTitle().equalsIgnoreCase(title);

        } else if (filterCriteria.startsWith("status:")) {
            String status = filterCriteria.substring(7).trim();
            return job.getStatus().equalsIgnoreCase(status);

        } else if (filterCriteria.startsWith("allowance:")) {
            try {
                double allowance = Double.parseDouble(filterCriteria.substring(10).trim());
                return job.getAllowance() >= allowance;
            } catch (NumberFormatException e) {
                System.out.println(" ---[ Invalid allowance format! Use a decimal value. ]--- ");
                return false;
            }

        } else if (filterCriteria.startsWith("duration:")) {
            try {
                int minDuration = Integer.parseInt(filterCriteria.substring(9).trim());
                return job.getDurationMonths() >= minDuration;
            } catch (NumberFormatException e) {
                System.out.println(" ---[ Invalid duration format! Use an integer value. ]--- ");
                return false;
            }

        } else if (filterCriteria.startsWith("education:")) {
            if (req != null) {
                String education = filterCriteria.substring(10).trim();
                return req.getEducation().equalsIgnoreCase(education);
            }

        } else if (filterCriteria.startsWith("cgpa:")) {
            if (req != null) {
                try {
                    double minCgpa = Double.parseDouble(filterCriteria.substring(5).trim());
                    return req.getCgpa() >= minCgpa;
                } catch (NumberFormatException e) {
                    System.out.println(" ---[ Invalid CGPA format! Use a decimal value. ]--- ");
                    return false;
                }
            }

        } else if (filterCriteria.startsWith("skills:")) {
            if (req != null) {
                String skill = filterCriteria.substring(7).trim().toLowerCase();
                return req.getSkills().toLowerCase().contains(skill);
            }
        }

        return false; // No match
    }

    public String getAllJobs() {
        StringBuilder outputStr = new StringBuilder();
        Iterator<Company> iterator = companyList.getIterator();

        while (iterator.hasNext()) {
            Company company = iterator.next();
            Iterator<Job> jobIterator = company.getJobList().getIterator();

            while (jobIterator.hasNext()) {
                Job job = jobIterator.next();

                //  ~~~ Only display jobs with status "Active"
                if (job.getStatus().equalsIgnoreCase("Active")) {
                    JobRequirement requirement = job.getRequirement(); // Get job requirement

                    // Handling null JobRequirement to prevent NullPointerException
                    String education = (requirement != null) ? requirement.getEducation() : "N/A";
                    String cgpa = (requirement != null) ? String.format("%.2f", requirement.getCgpa()) : "N/A";
                    String skills = (requirement != null) ? requirement.getSkills() : "N/A";

                    outputStr.append(String.format("%-10s %-30s %-10.2f %-8d %-15s %-20s %-30s %-10s %-30s\n",
                            job.getId(), job.getTitle(), job.getAllowance(),
                            job.getDurationMonths(), job.getStatus(), company.getName(),
                            education, cgpa, skills));
                }
            }
        }

        return outputStr.toString();
    }

    public String getAllJobsByCompany() {
        StringBuilder outputStr = new StringBuilder();

        Iterator<Job> jobIterator = selectedCompany.getJobList().getIterator();

        while (jobIterator.hasNext()) {
            Job job = jobIterator.next();
            JobRequirement requirement = job.getRequirement();

            String education = (requirement != null) ? requirement.getEducation() : "N/A";
            String cgpa = (requirement != null) ? String.format("%.2f", requirement.getCgpa()) : "N/A";
            String skills = (requirement != null) ? requirement.getSkills() : "N/A";

            outputStr.append(String.format("%-10s %-30s %-10.2f %-8d %-15s %-20s %-30s %-10s %-30s\n",
                    job.getId(), job.getTitle(), job.getAllowance(),
                    job.getDurationMonths(), job.getStatus(), selectedCompany.getName(),
                    education, cgpa, skills));

        }
        return outputStr.toString();
    }

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

    public String getAllSkills() {
        StringBuilder outputStr = new StringBuilder();
        ListInterface<String> categories = new LinkedList<>();
        ListInterface<ListInterface<Skill>> groupedSkills = new LinkedList<>();

        // Group skills by category
        Iterator<Skill> iterator = skillList.getIterator();
        while (iterator.hasNext()) {
            Skill skill = iterator.next();
            String category = skill.getCategory();
            int index = -1;

            for (int i = 1; i < categories.size() - 1; i++) {
                if (categories.getEntry(i).equals(category)) {
                    index = i;
                    break;
                }
            }

            if (index == -1) { 
                categories.add(category);
                ListInterface<Skill> newCategoryList = new LinkedList<>();
                newCategoryList.add(skill);
                groupedSkills.add(newCategoryList);
            } else { 
                groupedSkills.getEntry(index).add(skill);
            }
        }

        for (int i = 1; i < categories.size() - 1; i++) { 
            String category = categories.getEntry(i);
            ListInterface<Skill> skillGroup = groupedSkills.getEntry(i);

            outputStr.append("\n").append(category).append("\n");
            Iterator<Skill> skillIterator = skillGroup.getIterator();

            int index = 1;
            while (skillIterator.hasNext()) {
                Skill skill = skillIterator.next();
                outputStr.append("\t").append(index++).append(". ").append(skill.getId())
                        .append(". ").append(skill.getDescription()).append("\n");
            }
        }

        return outputStr.toString();
    }

    public Job findJobById(String jobId) {
        Iterator<Job> iterator = jobList.getIterator();
        while (iterator.hasNext()) {
            Job job = iterator.next();
            if (job.getId().equals(jobId)) {
                return job;
            }
        }
        return null;
    }

    public int findJobIndexById(String jobId) {
        Iterator<Job> iterator = jobList.getIterator();
        int index = 0;
        while (iterator.hasNext()) {
            Job job = iterator.next();
            if (job.getId().equals(jobId)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    private String formatReport(DoublyListInterface<Job> jobList) {
        StringBuilder report = new StringBuilder();
        report.append(" === JOB REPORT FOR ").append(selectedCompany.getName()).append(" ===\n\n");

        // Header
        report.append(String.format("%-10s %-30s %-10s %-8s %-15s %-10s %-25s %-30s\n",
                "Job ID", "Title", "Allowance", "Months", "Status", "CGPA", "Education", "Skills"));
        report.append("------------------------------------------------------------------------------------------------------------------------------\n");

        // Data Rows
        Iterator<Job> iterator = jobList.getIterator();
        while (iterator.hasNext()) {
            Job job = iterator.next();
            JobRequirement req = job.getRequirement(); // Get job requirement

            String cgpa = (req != null) ? String.format("%.2f", req.getCgpa()) : "N/A";
            String education = (req != null) ? req.getEducation() : "N/A";
            String skills = (req != null) ? req.getSkills() : "N/A";

            report.append(String.format("%-10s %-30s %-10.2f %-8d %-15s %-10s %-25s %-30s\n",
                    job.getId(), job.getTitle(), job.getAllowance(), job.getDurationMonths(),
                    job.getStatus(), cgpa, education, skills));
        }

        // Summary
        report.append("\n *** Total Jobs: ").append(jobList.getNumberOfEntries()).append("*** \n");

        return report.toString();
    }

    public void generateReport() {
        if (selectedCompany == null) {
            System.out.println(" ---[ No company selected. Please select a company first. ]--- ");
            return;
        }

        DoublyListInterface<Job> companyJobs = selectedCompany.getJobList();

        if (companyJobs.getNumberOfEntries() == 0) {
            System.out.println(" ---[ No jobs available for " + selectedCompany.getName() + " ]--- ");
            return;
        }

        String filterCriteria = jobUI.inputFilterCriteria();
        DoublyListInterface<Job> filteredJobs = new DoublyLinkedList<>();

        Iterator<Job> iterator = companyJobs.getIterator();
        while (iterator.hasNext()) {
            Job job = iterator.next();
            if (matchesFilter(job, filterCriteria)) {
                filteredJobs.add(job);
            }
        }

        if (filteredJobs.getNumberOfEntries() == 0) {
            System.out.println(" ---[ No jobs match the filter criteria. ]--- \n");
        } else {
            String report = formatReport(filteredJobs);
            jobUI.displayJobReport(report);
        }
    }

    private boolean containsIgnoreCase(String field, String keyword) {
        return field != null && field.toLowerCase().contains(keyword);
    }

    private String formatSearchResults(DoublyListInterface<Job> jobs, String keyword) {
        StringBuilder output = new StringBuilder();
        output.append(" === SEARCH RESULT FOR ").append(keyword).append(" ===\n\n");

        // Header
        output.append(String.format("%-10s %-30s %-10s %-8s %-15s %-10s %-25s %-30s\n",
                "Job ID", "Title", "Allowance", "Months", "Status", "CGPA", "Education", "Skills"));
        output.append("------------------------------------------------------------------------------------------------------------------------------\n");

        // Data Rows
        Iterator<Job> iterator = jobs.getIterator();
        while (iterator.hasNext()) {
            Job job = iterator.next();
            JobRequirement req = job.getRequirement(); // Get job requirement

            String cgpa = (req != null) ? String.format("%.2f", req.getCgpa()) : "N/A";
            String education = (req != null) ? req.getEducation() : "N/A";
            String skills = (req != null) ? req.getSkills() : "N/A";

            output.append(String.format("%-10s %-30s %-10.2f %-8d %-15s %-10s %-25s %-30s\n",
                    job.getId(), job.getTitle(), job.getAllowance(), job.getDurationMonths(),
                    job.getStatus(), cgpa, education, skills));
        }

        // Summary
        output.append("\n *** Total Jobs Found: ").append(jobs.getNumberOfEntries()).append("*** \n");

        return output.toString();
    }

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

    public static void main(String[] args) {
        JobManagement jobManagement = new JobManagement();
        jobManagement.start();
    }
}
