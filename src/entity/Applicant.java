/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author evago
 */
public class Applicant {
    private String applicantID;
    private String applicantName;
    private String applicantContact;
    private String applicantEmail;
    private String applicantSkill;

    public Applicant(String applicantID, String applicantName, String applicantContact, String applicantEmail, String applicantSkill) {
        this.applicantID = applicantID;
        this.applicantName = applicantName;
        this.applicantContact = applicantContact;
        this.applicantEmail = applicantEmail;
        this.applicantSkill = applicantSkill;
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

    public String getApplicantSkill() {
        return applicantSkill;
    }

    public void setApplicantSkill(String applicantSkill) {
        this.applicantSkill = applicantSkill;
    }

    @Override
    public String toString() {
        return "Applicant ID: " + applicantID + "\n" +
               "Name: " + applicantName + "\n" +
               "Contact: " + applicantContact + "\n" +
               "Email: " + applicantEmail + "\n" +
               "Skills: " + applicantSkill;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Applicant other = (Applicant) obj;
        return applicantID != null && applicantID.equals(other.applicantID);
    }
}
