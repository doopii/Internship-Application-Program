package entity;

import adt.*;
import java.io.Serializable;
import java.util.Iterator;

/**
 *
 * @author yijia
 */
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    private String companyId;
    private String name;
    private String location;
    private final DoublyListInterface<Job> jobList;

    public Company(String companyId, String name, String location) {
        this.companyId = companyId;
        this.name = name;
        this.location = location;
        this.jobList = new DoublyLinkedList<>();
    }

    // ----------- GETTERS & SETTERS -----------
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public DoublyListInterface<Job> getJobList() {
        return jobList;
    }

    // ----------- CRUD METHODS FOR JOB LIST -----------
    /**
     * Adds a new job to the company's job list.
     *
     * @param job Job to be added
     */
    public void addJob(Job job) {
        jobList.add(job);
        System.out.println(" *** Job added: " + job.getTitle() + " ***");
    }

    /**
     * Finds a job by ID.
     *
     * @param jobId Job ID
     * @return Job object if found, otherwise null
     */
    public Job findJobById(String jobId) {
        for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
            Job job = jobList.getEntry(i);
            if (job.getId().equals(jobId)) {
                return job;
            }
        }
        return null;
    }

    /**
     * Updates an existing job's details.
     *
     * @param updatedJob Job object with new details
     * @return true if update is successful, false if job not found
     */
    public boolean updateJob(Job updatedJob) {
        for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
            Job job = jobList.getEntry(i);
            if (job.getId().equals(updatedJob.getId())) {
                jobList.replace(i, updatedJob);
                System.out.println(" *** Job updated: " + updatedJob.getTitle()+ " ***");
                return true;
            }
        }
        System.out.println("  ---[ Job ID not found: " + updatedJob.getId() + " ]--- ");
        return false;
    }

    /**
     * Removes a job by ID.
     *
     * @param jobId Job ID
     * @return true if job is removed, false if job not found
     */
    public boolean removeJob(String jobId) {
        for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
            Job job = jobList.getEntry(i);
            if (job.getId().equals(jobId)) {
                jobList.remove(i);
                System.out.println(" *** Job removed: " + job.getTitle() + " ***");
                return true;
            }
        }
        System.out.println(" ---[  Job ID not found: " + jobId + " ]--- ");
        return false;
    }

    public String getAllJobs() {
        StringBuilder outputStr = new StringBuilder();
        Iterator<Job> jobIterator = getJobList().getIterator();

        while (jobIterator.hasNext()) {
            Job job = jobIterator.next();
            JobRequirement requirement = job.getRequirement(); // Get job requirement

            // Handling null JobRequirement to prevent NullPointerException
            String education = (requirement != null) ? requirement.getEducation() : "N/A";
            String cgpa = (requirement != null) ? String.format("%.2f", requirement.getCgpa()) : "N/A";
            String skills = (requirement != null) ? requirement.getSkills() : "N/A";

            outputStr.append(String.format("%-10s %-30s %-10.2f %-8d %-15s %-20s %-30s %-10s %-30s\n",
                    job.getId(), job.getTitle(), job.getAllowance(),
                    job.getDurationMonths(), job.getStatus(), getName(),
                    education, cgpa, skills));
        }
        return outputStr.toString();
    }

    // ----------- TO STRING -----------
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Company ID: %s\nName: %s\nLocation: %s\nJobs:\n", companyId, name, location));

        for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
            sb.append("  - ").append(jobList.getEntry(i)).append("\n");
        }

        return sb.toString();
    }
}
