package entity;


public class JobDesired {
    private String category;
    private String position;

    public JobDesired(String category, String position) {
        this.category = category;
        this.position = position;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "JobDesired{" + "category=" + category + ", position=" + position + '}';
    }
    
    
}
