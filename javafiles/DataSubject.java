
public interface DataSubject {
    void registerObserver(NutrientView observer);
    void removeObserver(NutrientView observer);
    void notifyObservers(String data);
}
