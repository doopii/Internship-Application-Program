/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

/**
 *
 * @author User
 */
import adt.DoublyLinkedList;
import adt.DoublyListInterface;
import dao.DataInitializer;
import dao.InitialiserJava;
import entity.Applicant;
import entity.Company;
import entity.Job;
import entity.JobRequirement;
import entity.Skill;
import utility.MessageUI;
import java.util.Scanner;

public class MatchingEngine {
    private DoublyListInterface<Applicant> applicantList;
    private DoublyListInterface<Job> jobList;
    private Scanner scanner = new Scanner(System.in);

    public MatchingEngine() {
        InitialiserJava initialiser = new InitialiserJava();
        DataInitializer data = new DataInitializer();

        this.applicantList = initialiser.getApplicantList();
        this.jobList = data.getJobList();
    }

    public void startMatching() {
        System.out.println("\n=== JOB MATCHING SYSTEM ===");
        
        // Step 1: Input Job ID
        System.out.print("Enter Job ID to find matches (or 'exit' to quit): ");
        String jobId = scanner.nextLine().trim();
        
        if (jobId.equalsIgnoreCase("exit")) {
            return;
        }
        
        Job job = findJobById(jobId);
        if (job == null) {
            MessageUI.displayInvalidChoiceMessage();
            return;
        }
        
        // Step 2: Find and display all matches for this job
        DoublyListInterface<MatchResult> allMatches = findMatchesForJob(job);
        displayAllMatches(allMatches, job);
        
        // Step 3: Optionally view specific applicant match
        System.out.print("\nEnter Applicant ID to view detailed match (or press Enter to skip): ");
        String applicantId = scanner.nextLine().trim();
        
        if (!applicantId.isEmpty()) {
            Applicant applicant = findApplicantById(applicantId);
            if (applicant != null) {
                displayDetailedMatch(findMatch(allMatches, applicant, job));
            } else {
                System.out.println("Applicant not found.");
            }
        }
    }

    private Job findJobById(String jobId) {
        for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
            Job job = jobList.getEntry(i);
            if (job.getId().equalsIgnoreCase(jobId)) {
                return job;
            }
        }
        return null;
    }
    
    private Company getCompanyForJob(Job job) {
        DataInitializer data = new DataInitializer();
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

    private Applicant findApplicantById(String applicantId) {
        for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
            Applicant applicant = applicantList.getEntry(i);
            if (applicant.getApplicantID().equalsIgnoreCase(applicantId)) {
                return applicant;
            }
        }
        return null;
    }

    private DoublyListInterface<MatchResult> findMatchesForJob(Job job) {
        DoublyListInterface<MatchResult> matches = new DoublyLinkedList<>();
        JobRequirement requirement = job.getRequirement();
        
        for (int i = 1; i <= applicantList.getNumberOfEntries(); i++) {
            Applicant applicant = applicantList.getEntry(i);
            double score = calculateMatchScore(applicant, job, requirement);
            
            if (score > 0.5) { // Threshold
                matches.add(new MatchResult(applicant, job, score));
            }
        }
        
        sortResultsByScore(matches);
        return matches;
    }

    private MatchResult findMatch(DoublyListInterface<MatchResult> matches, Applicant applicant, Job job) {
        for (int i = 1; i <= matches.getNumberOfEntries(); i++) {
            MatchResult result = matches.getEntry(i);
            if (result.getApplicant().equals(applicant) && result.getJob().equals(job)) {
                return result;
            }
        }
        return null;
    }

    private void displayAllMatches(DoublyListInterface<MatchResult> matches, Job job) {
        System.out.println("\n=== MATCHES FOR JOB: " + job.getId() + " - " + job.getTitle() + " ===");
        System.out.println("=".repeat(120));
        System.out.printf("%-6s | %-10s | %-20s | %-15s | %-30s | %-20s\n", 
                "Rank", "Score", "Applicant ID", "CGPA", "Skills", "Desired Jobs");
        System.out.println("-".repeat(120));
        
        if (matches.getNumberOfEntries() == 0) {
            System.out.println("No matches found for this job.");
        } else {
            for (int i = 1; i <= matches.getNumberOfEntries(); i++) {
                MatchResult result = matches.getEntry(i);
                Applicant a = result.getApplicant();
                
                // Get skills as string
                String skills = getSkillsString(a);
                
                // Get desired jobs as string
                String desiredJobs = getDesiredJobsString(a);
                
                System.out.printf("%-6d | %-10.2f | %-20s | %-15.2f | %-30s | %-20s\n",
                        i,
                        result.getScore() * 100,
                        a.getApplicantID(),
                        a.getCgpa(),
                        skills,
                        desiredJobs);
            }
        }
        
        System.out.println("=".repeat(120));
    }

    private void displayDetailedMatch(MatchResult result) {
        if (result == null) {
            System.out.println("No detailed match found.");
            return;
        }
        
        Applicant a = result.getApplicant();
        Job j = result.getJob();
        JobRequirement req = j.getRequirement();
        
        System.out.println("\n=== DETAILED MATCH ANALYSIS ===");
        System.out.println("=".repeat(80));
        System.out.printf("%-20s: %s\n", "Job Title", j.getTitle());
        System.out.printf("%-20s: %s\n", "Applicant Name", a.getApplicantName());
        System.out.printf("%-20s: %.2f%%\n", "Overall Match Score", result.getScore() * 100);
        System.out.println("-".repeat(80));
        
        // Skill matching breakdown
        System.out.println("\nSKILL MATCHING:");
        System.out.printf("%-30s | %-15s | %-15s\n", "Skill", "Proficiency", "Match");
        System.out.println("-".repeat(65));
        
        if (req != null && req.getSkills() != null) {
            String[] requiredSkills = req.getSkills().split(",\\s*");
            
            for (String skillId : requiredSkills) {
                boolean found = false;
                String proficiency = "";
                
                for (int i = 1; i <= a.getSkillList().getNumberOfEntries(); i++) {
                    Skill s = a.getSkillList().getEntry(i);
                    if (s.getSkillName().equalsIgnoreCase(skillId.trim())) {
                        found = true;
                        proficiency = s.getProficiency();
                        break;
                    }
                }
                
                System.out.printf("%-30s | %-15s | %-15s\n", 
                        skillId.trim(), 
                        found ? proficiency : "Missing", 
                        found ? "✓" : "✗");
            }
        }
        
        // CGPA comparison
        System.out.println("\nCGPA REQUIREMENT:");
        if (req != null) {
            System.out.printf("Required: %.2f | Applicant: %.2f | %s\n",
                    req.getCgpa(),
                    a.getCgpa(),
                    a.getCgpa() >= req.getCgpa() ? "Meets Requirement ✓" : "Below Requirement ✗");
        }
        
        System.out.println("=".repeat(80));
    }

    // Helper methods
    private String getSkillsString(Applicant a) {
        StringBuilder skills = new StringBuilder();
        for (int i = 1; i <= a.getSkillList().getNumberOfEntries(); i++) {
            Skill s = a.getSkillList().getEntry(i);
            skills.append(s.getSkillName()).append("(").append(s.getProficiency()).append(")");
            if (i < a.getSkillList().getNumberOfEntries()) skills.append(", ");
        }
        return skills.toString();
    }

    private String getDesiredJobsString(Applicant a) {
        StringBuilder jobs = new StringBuilder();
        for (int i = 1; i <= a.getJobDesiredList().getNumberOfEntries(); i++) {
            jobs.append(a.getJobDesiredList().getEntry(i).getPosition());
            if (i < a.getJobDesiredList().getNumberOfEntries()) jobs.append(", ");
        }
        return jobs.toString();
    }

    // Existing matching calculation methods from previous implementation
    private double calculateMatchScore(Applicant applicant, Job job, JobRequirement requirement) {
        double totalScore = 0;
        
        // Skill Matching (40%)
        double skillScore = calculateSkillMatchScore(applicant, requirement);
        totalScore += skillScore * 0.4;
        
        // CGPA Matching (20%)
        double cgpaScore = calculateCGPAMatchScore(applicant, requirement);
        totalScore += cgpaScore * 0.2;
        
        // Location Preference (20%)
        Company company = getCompanyForJob(job);
        double locationScore = calculateLocationMatchScore(applicant, company);
        totalScore += locationScore * 0.2;
        
        return totalScore;
    }

    private double calculateSkillMatchScore(Applicant applicant, JobRequirement requirement) {
        int matchCount = 0;
        int totalRequired = 0;
        if (requirement.getSkills() == null) return 0;

        String[] requiredSkills = requirement.getSkills().split(",\\s*");
        totalRequired = requiredSkills.length;

        for (String reqSkill : requiredSkills) {
            for (int i = 1; i <= applicant.getSkillList().getNumberOfEntries(); i++) {
                Skill s = applicant.getSkillList().getEntry(i);
                if (s.getSkillName().equalsIgnoreCase(reqSkill.trim())) {
                    matchCount++;
                    break;
                }
            }
        }
        return totalRequired == 0 ? 0 : (double) matchCount / totalRequired;
    }

    private double calculateCGPAMatchScore(Applicant applicant, JobRequirement requirement) {
        if (requirement == null) {
            return 0;
        }
        return applicant.getCgpa() >= requirement.getCgpa() ? 1.0 : 0.0;
    }
    
    private double calculateLocationMatchScore(Applicant applicant, Company company) {
        if (company == null) {
            return 0;
        }
        String companyLocation = company.getLocation();
        return applicant.getApplicantAddress().equalsIgnoreCase(companyLocation) ? 1.0 : 0.0;
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

    // Inner MatchResult class
    private class MatchResult {
        private Applicant applicant;
        private Job job;
        private double score;

        public MatchResult(Applicant applicant, Job job, double score) {
            this.applicant = applicant;
            this.job = job;
            this.score = score;
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
    }
}
