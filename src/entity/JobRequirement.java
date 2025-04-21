package entity;

/**
 *
 * @author yijia
 */
public class JobRequirement {
    private String education;
    private double cgpa;
    private String skills; 

    public JobRequirement(String education, double cgpa, String skills) {
        this.education = education;
        this.cgpa = cgpa;
        this.skills = skills;
    }
    
    public JobRequirement(String education, String cgpaRange, String skills) {
        this.education = education;
        if (cgpaRange.contains("-")) {
            this.cgpa = Double.parseDouble(cgpaRange.split("-")[0]);
        } else if (cgpaRange.contains(" above")) {
            this.cgpa = Double.parseDouble(cgpaRange.split(" ")[0]);
        } else {
            this.cgpa = 2.0; 
        }
        this.skills = skills;
    }
   
    public JobRequirement(String education, String cgpaRange, 
                          String progSkills, String designSkills) {
        this.education = education;
        // Extract the lower bound of the CGPA range
        if (cgpaRange.contains("-")) {
            this.cgpa = Double.parseDouble(cgpaRange.split("-")[0]);
        } else if (cgpaRange.contains(" above")) {
            this.cgpa = Double.parseDouble(cgpaRange.split(" ")[0]);
        } else {
            this.cgpa = 2.0; 
        }
        
        // Combine programming and design skills
        StringBuilder skillsBuilder = new StringBuilder();
        
        if (!progSkills.isEmpty()) {
            skillsBuilder.append(progSkills);
        }
        
        if (!designSkills.isEmpty()) {
            if (!progSkills.isEmpty()) {
                skillsBuilder.append(", ");
            }
            skillsBuilder.append(designSkills);
        }
        
        this.skills = skillsBuilder.toString();
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

    public void setCgpa(double cgpa) {
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
        return "JobRequirement{" +
                ", education='" + education + '\'' +
                ", cgpa=" + cgpa +
                ", skills='" + skills + '\'' +
                '}';
    }
}