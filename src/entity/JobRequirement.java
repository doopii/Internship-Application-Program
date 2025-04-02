package entity;

import java.io.Serializable;

/**
 *
 * @author yijia
 */
public class JobRequirement implements Serializable {

    private static final long serialVersionUID = 1L;

    private String jobId; 
    private String education; // category
    private double cgpa;
    private String skills;

    public JobRequirement(String jobId, String education, double cgpa, String skills) {
        this.jobId = jobId;
        this.education = education;
        this.cgpa = cgpa;
        this.skills = skills;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(int cgpa) {
        this.cgpa = cgpa;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return String.format("Education: %s\nExperience: %.2f years\nSkills: %s",
                education, cgpa, skills);
    }
}
