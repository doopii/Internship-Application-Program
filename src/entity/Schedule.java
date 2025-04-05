/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Acer
 */
public class Schedule {
    private String scheduleID;
    private String scheduleTime;
    private String scheduleDesc;

    public Schedule(String scheduleID, String scheduleTime, String scheduleDesc) {
        this.scheduleID = scheduleID;
        this.scheduleTime = scheduleTime;
        this.scheduleDesc = scheduleDesc;
    }

    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getScheduleDesc() {
        return scheduleDesc;
    }

    public void setScheduleDesc(String scheduleDesc) {
        this.scheduleDesc = scheduleDesc;
    }

    @Override
    public String toString() {
        return "Schedule{" + "scheduleID=" + scheduleID + ", scheduleTime=" + scheduleTime + ", scheduleDesc=" + scheduleDesc + " }";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Schedule other = (Schedule) obj;
        return scheduleID != null && scheduleID.equals(other.scheduleID);
    }

    
}
