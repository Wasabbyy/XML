import java.util.List;

public class Recipe {
    private String title;
    private List<Ingredient> ingredients;
    private String instructions;
    private int preparationTime;
    private int cookingTime;
    private int servingSize;
    private String category;
    private String difficulty;
    private String image;

    public Recipe(String title, List<Ingredient> ingredients, String instructions, int preparationTime,
                  int cookingTime, int servingSize, String category, String difficulty, String image) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.preparationTime = preparationTime;
        this.cookingTime = cookingTime;
        this.servingSize = servingSize;
        this.category = category;
        this.difficulty = difficulty;
        this.image = image;
    }

    // Gettery
    public String getTitle() {
        return title;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public int getServingSize() {
        return servingSize;
    }

    public String getCategory() {
        return category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Recipe{title='").append(title).append("', ingredients=[");
        for (Ingredient ingredient : ingredients) {
            sb.append(ingredient).append(", ");
        }
        if (!ingredients.isEmpty()) sb.setLength(sb.length() - 2);  // Remove last comma
        sb.append("], instructions='").append(instructions).append("', preparationTime=").append(preparationTime)
                .append(", cookingTime=").append(cookingTime).append(", servingSize=").append(servingSize)
                .append(", category='").append(category).append("', difficulty='").append(difficulty)
                .append("', image='").append(image).append("'}");
        return sb.toString();
    }
}
