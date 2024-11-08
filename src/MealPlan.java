import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public class MealPlan {
    private Map<String, List<Meal>> dailyMeals;

    public MealPlan(Map<String, List<Meal>> dailyMeals) {
        this.dailyMeals = dailyMeals;
    }

    // Getter
    public Map<String, List<Meal>> getDailyMeals() {
        return dailyMeals;
    }

    @Override
    public String toString() {
        // Seřazené dny v požadovaném pořadí
        List<String> dayOrder = Arrays.asList("Pondělí", "Úterý", "Středa", "Čtvrtek", "Pátek", "Sobota", "Neděle");

        // Použití LinkedHashMap pro udržení pořadí
        Map<String, List<Meal>> sortedMeals = new LinkedHashMap<>();

        // Přiřadíme hodnoty podle správného pořadí
        for (String day : dayOrder) {
            if (dailyMeals.containsKey(day)) {
                sortedMeals.put(day, dailyMeals.get(day));
            }
        }

        // Generování výstupu
        StringBuilder sb = new StringBuilder("MealPlan:\n");
        for (Map.Entry<String, List<Meal>> entry : sortedMeals.entrySet()) {
            sb.append(entry.getKey()).append(":\n");
            for (Meal meal : entry.getValue()) {
                sb.append("  ").append(meal.getType()).append(" = ").append(meal.getRecipeReference()).append("\n");
            }
        }
        return sb.toString();
    }
}
