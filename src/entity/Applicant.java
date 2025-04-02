/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import adt.DoublyLinkedList;


/**
 *
 * @author evago
 */
public class Applicant {
    private String applicantID;
    private String applicantName;
    private String applicantContact;
    private String applicantEmail;
    private DoublyLinkedList<Skill> skill;
    private DoublyLinkedList<JobDesired> jobDesired;

    public Applicant(String applicantID, String applicantName, String applicantContact, String applicantEmail, DoublyLinkedList<Skill> skill, DoublyLinkedList<JobDesired> jobDesired) {
        this.applicantID = applicantID;
        this.applicantName = applicantName;
        this.applicantContact = applicantContact;
        this.applicantEmail = applicantEmail;
        this.skill = skill;
        this.jobDesired = jobDesired;
    }

    @Override
    public String toString() {
        return "Applicant{" + "applicantID=" + applicantID +
                ", applicantName: " + applicantName +
                ", applicantContact: " + applicantContact +
                ", applicantEmail: " + applicantEmail +
                ", skill: " + skill +
                ", jobDesired: " + jobDesired;
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

    public DoublyLinkedList<Skill> getSkill() {
        return skill;
    }

    public void setSkill(DoublyLinkedList<Skill> skill) {
        this.skill = skill;
    }

    public DoublyLinkedList<JobDesired> getJobDesired() {
        return jobDesired;
    }

    public void setJobDesired(DoublyLinkedList<JobDesired> jobDesired) {
        this.jobDesired = jobDesired;
    }
    
    
    
    
}
