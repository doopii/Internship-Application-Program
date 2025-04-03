package entity;

import adt.DoublyLinkedList;

public class Applicant {
    private String applicantID;
    private String applicantName;
    private String applicantContact;
    private String applicantEmail;
    private String applicantAddress; 
    private double cgpa;

    private DoublyLinkedList<Skill> skillList;
    private DoublyLinkedList<JobDesired> jobDesiredList;

    public Applicant(String applicantID, String applicantName, String applicantContact, String applicantEmail,
                     String applicantAddress, double cgpa, DoublyLinkedList<Skill> skillList, DoublyLinkedList<JobDesired> jobDesiredList) {
        this.applicantID = applicantID;
        this.applicantName = applicantName;
        this.applicantContact = applicantContact;
        this.applicantEmail = applicantEmail;
        this.applicantAddress = applicantAddress;
        this.cgpa = cgpa;
        this.skillList = skillList;
        this.jobDesiredList = jobDesiredList;
    }

    @Override
    public String toString() {
        return "Applicant{" +
                "applicantID='" + applicantID + '\'' +
                ", applicantName='" + applicantName + '\'' +
                ", applicantContact='" + applicantContact + '\'' +
                ", applicantEmail='" + applicantEmail + '\'' +
                ", applicantAddress='" + applicantAddress + '\'' +
                ", cgpa=" + cgpa +
                ", skillList=" + skillList +
                ", jobDesiredList=" + jobDesiredList +
                '}';
    }

    public String getApplicantID() {
        return applicantID;
    }

    public void setApplicantID(String applicantID) {
        this.applicantID = applicantID;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantContact() {
        return applicantContact;
    }

    public void setApplicantContact(String applicantContact) {
        this.applicantContact = applicantContact;
    }

    public String getApplicantEmail() {
        return applicantEmail;
    }

    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    public String getApplicantAddress() {
        return applicantAddress;
    }

    public void setApplicantAddress(String applicantAddress) {
        this.applicantAddress = applicantAddress;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public DoublyLinkedList<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(DoublyLinkedList<Skill> skillList) {
        this.skillList = skillList;
    }

    public DoublyLinkedList<JobDesired> getJobDesiredList() {
        return jobDesiredList;
    }

    public void setJobDesiredList(DoublyLinkedList<JobDesired> jobDesiredList) {
        this.jobDesiredList = jobDesiredList;
    }
    
    
}

