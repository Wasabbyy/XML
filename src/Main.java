public class Main {
    public static void main(String[] args) {
        RecipeBook recipeBook = new RecipeBook("recept.xml");
        Core core = new Core(recipeBook);
        core.startrunThread();
    }
}
