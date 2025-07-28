import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * Use this to access goals from anywhere:
 * ArrayList<UserGoal> currentGoals = SessionData.getInstance().getGoalsForUser(userId);
 * System.out.println("User goals: " + currentGoals);
 */

public class SessionData {
    private static SessionData instance = null;

    // Maps each userId to a list of UserGoal objects
    private final Map<Integer, ArrayList<UserGoal>> userGoalsMap;

    private SessionData() {
        userGoalsMap = new HashMap<>();
    }

    public static SessionData getInstance() {
        if (instance == null) {
            instance = new SessionData();
        }
        return instance;
    }

    public ArrayList<UserGoal> getGoalsForUser(int userId) {
        return userGoalsMap.getOrDefault(userId, new ArrayList<>());
    }

    public void setGoalsForUser(int userId, ArrayList<UserGoal> goals) {
        userGoalsMap.put(userId, new ArrayList<>(goals));
    }

    public void clearGoalsForUser(int userId) {
        userGoalsMap.remove(userId);
    }

    public void addGoalForUser(int userId, UserGoal goal) {
        userGoalsMap.computeIfAbsent(userId, k -> new ArrayList<>()).add(goal);
    }

    public void removeGoalForUser(int userId, int index) {
        ArrayList<UserGoal> goals = userGoalsMap.get(userId);
        if (goals != null && index >= 0 && index < goals.size()) {
            goals.remove(index);
        }
    }

    public boolean containsNutrientForUser(int userId, String nutrient) {
        ArrayList<UserGoal> goals = userGoalsMap.get(userId);
        if (goals == null) return false;
        for (UserGoal goal : goals) {
            if (goal.getNutrient().equalsIgnoreCase(nutrient)) return true;
        }
        return false;
    }

    public int getGoalCountForUser(int userId) {
        ArrayList<UserGoal> goals = userGoalsMap.get(userId);
        return goals != null ? goals.size() : 0;
    }
}

