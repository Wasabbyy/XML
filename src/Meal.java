public class Meal {
    private String type; // Breakfast, Lunch, Dinner
    private String recipeReference; // Reference to recipe title

    public Meal(String type, String recipeReference) {
        this.type = type;
        this.recipeReference = recipeReference;
    }

    // Gettery
    public String getType() {
        return type;
    }

    public String getRecipeReference() {
        return recipeReference;
    }

    @Override
    public String toString() {
        return "Meal{type='" + type + "', recipeReference='" + recipeReference + "'}";
    }
}
