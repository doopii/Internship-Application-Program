package entity;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author yijia
 */
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private double allowance;
    private int durationMonths;
    private String status;
    private JobRequirement requirement;

    public Job() {
    }

    public Job(String id, String title, double allowance, int durationMonths, String status, JobRequirement requirement) {
        this.id = id;
        this.title = title;
        this.allowance = allowance;
        this.durationMonths = durationMonths;
        this.status = status;
        this.requirement = requirement;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAllowance() {
        return allowance;
    }

    public void setAllowance(double allowance) {
        this.allowance = allowance;
    }

    public int getDurationMonths() {
        return durationMonths;
    }

    public void setDurationMonths(int durationMonths) {
        this.durationMonths = durationMonths;
    }

    public JobRequirement getRequirement() {
        return requirement;
    }

    public void setRequirement(JobRequirement requirement) {
        this.requirement = requirement;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Job other = (Job) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return String.format("%-10s %-30s %10.2f %-8s %-15s",
                id, title, allowance, durationMonths, status);
    }
}
