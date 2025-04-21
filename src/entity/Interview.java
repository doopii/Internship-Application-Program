/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Acer
 */
public class Interview {
    private String jobID;
    private String scheduleID;
    private String interviewID;
    private String interviewPosition;
    private String interviewStatus;   
    private String interviewDate;
    private Applicant applicant;
    
    public Interview(String jobID, String scheduleID, String interviewID, String interviewPosition, String interviewStatus, String interviewDate) {
        this.jobID = jobID;
        this.scheduleID = scheduleID;
        this.interviewID = interviewID;
        this.interviewPosition = interviewPosition;
        this.interviewStatus = interviewStatus;
        this.interviewDate = interviewDate;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getInterviewID() {
        return interviewID;
    }

    public void setInterviewID(String interviewID) {
        this.interviewID = interviewID;
    }

    public String getInterviewPosition() {
        return interviewPosition;
    }

    public void setInterviewPosition(String interviewPosition) {
        this.interviewPosition = interviewPosition;
    }

    public String getInterviewStatus() {
        return interviewStatus;
    }

    public void setInterviewStatus(String interviewStatus) {
        this.interviewStatus = interviewStatus;
    }

    public String getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(String interviewDate) {
        this.interviewDate = interviewDate;
    }
    
    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    @Override
    public String toString() {
        return "Interview{" + "jobID=" + jobID + ", scheduleID=" + scheduleID + ", interviewID=" + interviewID + ", interviewPosition=" + interviewPosition + ", interviewStatus=" + interviewStatus + ", interviewDate=" + interviewDate + '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Interview other = (Interview) obj;
        return interviewID != null && interviewID.equals(other.interviewID);
    }
}
