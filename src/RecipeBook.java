import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class RecipeBook {
    private List<Recipe> recipes;
    private MealPlan mealPlan;

    public RecipeBook(String xmlFilePath) {
        recipes = new ArrayList<>();
        loadRecipesAndMealPlan(xmlFilePath);
    }

    private void loadRecipesAndMealPlan(String xmlFilePath) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(xmlFilePath));
            doc.getDocumentElement().normalize();

            // Load recipes
            NodeList recipeNodes = doc.getElementsByTagName("r:Recipe");
            for (int i = 0; i < recipeNodes.getLength(); i++) {
                Element recipeElement = (Element) recipeNodes.item(i);
                recipes.add(parseRecipe(recipeElement));
            }

            // Load meal plan
            NodeList mealPlanNodes = doc.getElementsByTagName("r:MealPlan");
            if (mealPlanNodes.getLength() > 0) {
                Element mealPlanElement = (Element) mealPlanNodes.item(0);
                mealPlan = parseMealPlan(mealPlanElement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Recipe parseRecipe(Element recipeElement) {
        String title = recipeElement.getElementsByTagName("r:Title").item(0).getTextContent();
        List<Ingredient> ingredients = new ArrayList<>();
        NodeList ingredientNodes = recipeElement.getElementsByTagName("r:Ingredient");
        for (int j = 0; j < ingredientNodes.getLength(); j++) {
            Element ingredientElement = (Element) ingredientNodes.item(j);
            String name = ingredientElement.getElementsByTagName("r:Name").item(0).getTextContent();
            int quantity = Integer.parseInt(ingredientElement.getElementsByTagName("r:Quantity").item(0).getTextContent());
            String unit = ingredientElement.getElementsByTagName("r:Unit").item(0).getTextContent();
            ingredients.add(new Ingredient(name, quantity, unit));
        }
        String instructions = recipeElement.getElementsByTagName("r:Instructions").item(0).getTextContent();
        int preparationTime = Integer.parseInt(recipeElement.getElementsByTagName("r:PreparationTime").item(0).getTextContent());
        int cookingTime = Integer.parseInt(recipeElement.getElementsByTagName("r:CookingTime").item(0).getTextContent());
        int servingSize = Integer.parseInt(recipeElement.getElementsByTagName("r:ServingSize").item(0).getTextContent());
        String category = recipeElement.getElementsByTagName("r:Category").item(0).getTextContent();
        String difficulty = recipeElement.getElementsByTagName("r:Difficulty").item(0).getTextContent();
        String image = recipeElement.getElementsByTagName("r:Image").item(0).getTextContent();

        return new Recipe(title, ingredients, instructions, preparationTime, cookingTime, servingSize, category, difficulty, image);
    }

    private MealPlan parseMealPlan(Element mealPlanElement) {
        Map<String, List<Meal>> dailyMeals = new HashMap<>();
        NodeList dayNodes = mealPlanElement.getElementsByTagName("r:Day");

        for (int i = 0; i < dayNodes.getLength(); i++) {
            Element dayElement = (Element) dayNodes.item(i);
            String dayName = dayElement.getAttribute("name");
            List<Meal> meals = new ArrayList<>();

            NodeList mealNodes = dayElement.getElementsByTagName("r:Meal");
            for (int j = 0; j < mealNodes.getLength(); j++) {
                Element mealElement = (Element) mealNodes.item(j);
                String type = mealElement.getAttribute("type");
                String recipeReference = mealElement.getElementsByTagName("r:RecipeReference").item(0).getTextContent();
                meals.add(new Meal(type, recipeReference));
            }
            dailyMeals.put(dayName, meals);
        }
        return new MealPlan(dailyMeals);
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public MealPlan getMealPlan() {
        return mealPlan;
    }


    public void printTodayMealPlan() {
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        String dayInEnglish = today.name().substring(0, 1) + today.name().substring(1).toLowerCase();
        List<Meal> mealsForToday = mealPlan.getDailyMeals().get(dayInEnglish);

        if (mealsForToday != null && !mealsForToday.isEmpty()) {
            System.out.println(dayInEnglish + " Meal Plan:");
            for (int i = 0; i < mealsForToday.size(); i++) {
                Meal meal = mealsForToday.get(i);
                String mealType;
                switch (meal.getType().toLowerCase()) {
                    case "breakfast":
                        mealType = "Breakfast";
                        break;
                    case "lunch":
                        mealType = "Lunch";
                        break;
                    case "dinner":
                        mealType = "Dinner";
                        break;
                    default:
                        mealType = "Food";
                }
                System.out.println((i + 1) + ". " + mealType + ": " + meal.getRecipeReference());
            }
        } else {
            System.out.println("No meals planned for " + dayInEnglish + ".");
        }
    }

    public Recipe getRecipeByTitle(String title) {
        for (Recipe recipe : recipes) {
            if (recipe.getTitle().equalsIgnoreCase(title)) {
                return recipe;
            }
        }
        return null; // Return null if no recipe with the given title is found
    }

    public void printRecipeTitles() {
        for (int i = 0; i < recipes.size(); i++) {
            System.out.println((i + 1) + ". " + recipes.get(i).getTitle());
        }
    }

    public void printRecipeDetails(int index) {
        if (index < 0 || index >= recipes.size()) {
            System.out.println("Invalid recipe number.");
            return;
        }
        Recipe recipe = recipes.get(index);
        System.out.println("Title: " + recipe.getTitle());
        System.out.println("Ingredients:");
        for (Ingredient ingredient : recipe.getIngredients()) {
            System.out.println("  - " + ingredient.getName() + " " + ingredient.getQuantity() + " " + ingredient.getUnit());
        }
        System.out.println("Instructions: " + recipe.getInstructions().trim());
        System.out.println("Preparation time: " + recipe.getPreparationTime() + " minutes");
        System.out.println("Cooking time: " + recipe.getCookingTime() + " minutes");
        System.out.println("Difficulty: " + recipe.getDifficulty());
        System.out.println("Serving size: " + recipe.getServingSize());
        System.out.println("Category: " + recipe.getCategory());
        System.out.println("Picture: " + recipe.getImage());
        System.out.println();
    }
}
