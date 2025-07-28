public class Profile {
    private int userId;
    private String name;
    private int age;
    private String gender;
    private double weight;
    private double height;
    private String activityLevel;
    private String goal;

    public Profile(int userId, String name, int age, String gender, double weight, double height, String activityLevel, String goal) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.activityLevel = activityLevel;
        this.goal = goal;
    }

    // Getters
    public int getUserId() { return userId; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public double getWeight() { return weight; }
    public double getHeight() { return height; }
    public String getActivityLevel() { return activityLevel; }
    public String getGoal() { return goal; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setGender(String gender) { this.gender = gender; }
    public void setWeight(double weight) { this.weight = weight; }
    public void setHeight(double height) { this.height = height; }
    public void setActivityLevel(String activityLevel) { this.activityLevel = activityLevel; }
    public void setGoal(String goal) { this.goal = goal; }
}
