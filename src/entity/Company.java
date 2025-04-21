package entity;

import adt.DoublyLinkedList;
import adt.DoublyListInterface;
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
    private DoublyListInterface<String> locations = new DoublyLinkedList<>();
    private final DoublyListInterface<Job> jobList;

    public Company(String companyId, String name, String locationsInput) {
        this.companyId = companyId;
        this.name = name;
        this.jobList = new DoublyLinkedList<>();
        
        // Split the input locations string
        String[] locArray = locationsInput.split(", ");
        for(String loc : locArray) {
            this.locations.add(loc.trim());
        }
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
        return getPrimaryLocation();
    }

    public String getPrimaryLocation() {
        return locations.isEmpty() ? "Unknown" : locations.getEntry(1);
    }

    public DoublyListInterface<String> getLocations() {
        return locations;
    }

    public DoublyListInterface<Job> getJobList() {
        return jobList;
    }

    // ----------- CRUD METHODS FOR JOB LIST -----------
    public void addJob(Job job) {
        jobList.add(job);
        System.out.println(" *** Job added: " + job.getTitle() + " ***");
    }

    public Job findJobById(String jobId) {
        for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
            Job job = jobList.getEntry(i);
            if (job.getId().equals(jobId)) {
                return job;
            }
        }
        return null;
    }

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
        sb.append(String.format("Company ID: %s\nName: %s\nLocation: %s\nJobs:\n", companyId, name));

        for (int i = 1; i <= jobList.getNumberOfEntries(); i++) {
            sb.append("  - ").append(jobList.getEntry(i)).append("\n");
        }

        return sb.toString();
    }
}
