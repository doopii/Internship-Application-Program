package entity;


public class Skill {
    private String category;
    private String skillName;
    private String proficiency;

    public Skill(String category, String skillName, String proficiency) {
        this.category = category;
        this.skillName = skillName;
        this.proficiency = proficiency;
    }

    @Override
    public String toString() {
        return "Skill{" + "category=" + category + ", skillName=" + skillName + ", proficiency=" + proficiency + '}';
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }
}