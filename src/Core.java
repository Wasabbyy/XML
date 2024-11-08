import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class Core implements Runnable {


    Thread runThread;
    RecipeBook recipeBook;

    public Core(RecipeBook recipeBook) {
        this.recipeBook = recipeBook;
    }

    public void startrunThread() {
        runThread = new Thread(this);
        runThread.start();
    }

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (runThread != null) {
            System.out.println("Welcome to the RecipeBook and MealPlanner\nType 'recipes' to see all recipes, 'mealplanning' to see today's meal plan, or 'end'/'quit' to exit.");

            try {
                String userInput = reader.readLine();

                // Check for termination command
                if (userInput.equalsIgnoreCase("end") || userInput.equalsIgnoreCase("quit")) {
                    System.out.println("Exiting the program. Goodbye!");
                    runThread = null; // Stops the thread loop
                    break;
                }

                if (userInput.equalsIgnoreCase("recipes")) {
                    System.out.println("Available Recipes:");
                    recipeBook.printRecipeTitles();

                    System.out.println("Enter the number of the recipe you'd like to view, or '0' to go back:");
                    int recipeIndex = Integer.parseInt(reader.readLine()) - 1;

                    if (recipeIndex >= 0 && recipeIndex < recipeBook.getRecipes().size()) {
                        recipeBook.printRecipeDetails(recipeIndex);
                    } else if (recipeIndex == -1) {
                        System.out.println("Returning to main menu.");
                    } else {
                        System.out.println("Invalid input. Please enter a valid recipe number.");
                    }
                } else if (userInput.equalsIgnoreCase("mealplanning")) {
                    System.out.println("\nToday's Meal Plan:");
                    recipeBook.printTodayMealPlan();

                    System.out.println("Enter the number of the meal you'd like to view the recipe for, or '0' to go back:");
                    int mealIndex = Integer.parseInt(reader.readLine()) - 1;

                    // Get today's meal plan to find the selected meal's recipe
                    DayOfWeek today = LocalDate.now().getDayOfWeek();
                    String dayInEnglish = today.name().substring(0, 1) + today.name().substring(1).toLowerCase();
                    List<Meal> mealsForToday = recipeBook.getMealPlan().getDailyMeals().get(dayInEnglish);

                    if (mealIndex >= 0 && mealIndex < mealsForToday.size()) {
                        String recipeTitle = mealsForToday.get(mealIndex).getRecipeReference();
                        Recipe recipe = recipeBook.getRecipeByTitle(recipeTitle);

                        if (recipe != null) {
                            recipeBook.printRecipeDetails(recipeBook.getRecipes().indexOf(recipe));
                        } else {
                            System.out.println("Recipe not found for this meal.");
                        }
                    } else if (mealIndex == -1) {
                        System.out.println("Returning to main menu.");
                    } else {
                        System.out.println("Invalid input. Please enter a valid meal number.");
                    }
                } else {
                    System.out.println("Invalid option. Please type 'recipes', 'mealplanning', or 'end'/'quit' to exit.");
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Error reading input. Please try again.");
            }
        }
    }




}