import java.util.Scanner;

// Main Pizza App
public class MyPizzaApp {
    // Abstract PizzaStore
    public abstract static class PizzaStore {
        protected abstract Pizza createPizza(String item);

        public Pizza orderPizza(String type) {
            Pizza pizza = createPizza(type);
            if (pizza != null) {
                System.out.println("-- Making a " + pizza.getName() + " --");
                pizza.prepare();
                pizza.bake();
                pizza.cut();
                pizza.box();
            } else {
                System.out.println("Sorry, we don't make that kind of pizza.");
            }
            return pizza;
        }
    }

    // Abstract Pizza
    public abstract static class Pizza {
        String name;
        Dough dough;
        Sauce sauce;
        Cheese cheese;
        Veggies[] veggies;
        Pepperoni pepperoni;
        Clams clam;

        abstract void prepare();

        void bake() {
            System.out.println("Bake for 25 minutes at 350");
        }

        void cut() {
            System.out.println("Cutting the pizza into diagonal slices");
        }

        void box() {
            System.out.println("Place pizza in official PizzaStore box");
        }

        void setName(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }

    // Ingredient Interfaces and Implementations
    public interface Dough {}
    public static class ThinCrustDough implements Dough {}
    public static class ThickCrustDough implements Dough {}

    public interface Sauce {}
    public static class MarinaraSauce implements Sauce {}
    public static class PlumTomatoSauce implements Sauce {}

    public interface Cheese {}
    public static class ReggianoCheese implements Cheese {}
    public static class MozzarellaCheese implements Cheese {}

    public interface Veggies {}
    public static class Garlic implements Veggies {}
    public static class Onion implements Veggies {}
    public static class Mushroom implements Veggies {}
    public static class RedPepper implements Veggies {}
    public static class Spinach implements Veggies {}
    public static class Eggplant implements Veggies {}
    public static class BlackOlives implements Veggies {}

    public interface Pepperoni {}
    public static class SlicedPepperoni implements Pepperoni {}

    public interface Clams {}
    public static class FreshClams implements Clams {}
    public static class FrozenClams implements Clams {}

    // PizzaIngredientFactory Interface
    public interface PizzaIngredientFactory {
        Dough createDough();
        Sauce createSauce();
        Cheese createCheese();
        Veggies[] createVeggies();
        Pepperoni createPepperoni();
        Clams createClam();
    }

    // NYPizzaIngredientFactory
    public static class NYPizzaIngredientFactory implements PizzaIngredientFactory {
        public Dough createDough() { return new ThinCrustDough(); }
        public Sauce createSauce() { return new MarinaraSauce(); }
        public Cheese createCheese() { return new ReggianoCheese(); }
        public Veggies[] createVeggies() {
            return new Veggies[]{new Garlic(), new Onion(), new Mushroom(), new RedPepper()};
        }
        public Pepperoni createPepperoni() { return new SlicedPepperoni(); }
        public Clams createClam() { return new FreshClams(); }
    }

    // ChicagoPizzaIngredientFactory
    public static class ChicagoPizzaIngredientFactory implements PizzaIngredientFactory {
        public Dough createDough() { return new ThickCrustDough(); }
        public Sauce createSauce() { return new PlumTomatoSauce(); }
        public Cheese createCheese() { return new MozzarellaCheese(); }
        public Veggies[] createVeggies() {
            return new Veggies[]{new Spinach(), new Eggplant(), new BlackOlives()};
        }
        public Pepperoni createPepperoni() { return new SlicedPepperoni(); }
        public Clams createClam() { return new FrozenClams(); }
    }

    // NYPizzaStore
    public static class NYPizzaStore extends PizzaStore {
        protected Pizza createPizza(String item) {
            Pizza pizza = null;
            PizzaIngredientFactory ingredientFactory = new NYPizzaIngredientFactory();

            if (item.equals("cheese")) {
                pizza = new CheesePizza(ingredientFactory);
                pizza.setName("New York Style Cheese Pizza");
            } else if (item.equals("veggie")) {
                pizza = new VeggiePizza(ingredientFactory);
                pizza.setName("New York Style Veggie Pizza");
            }
            return pizza;
        }
    }

    // ChicagoPizzaStore
    public static class ChicagoPizzaStore extends PizzaStore {
        protected Pizza createPizza(String item) {
            Pizza pizza = null;
            PizzaIngredientFactory ingredientFactory = new ChicagoPizzaIngredientFactory();

            if (item.equals("cheese")) {
                pizza = new CheesePizza(ingredientFactory);
                pizza.setName("Chicago Style Cheese Pizza");
            } else if (item.equals("veggie")) {
                pizza = new VeggiePizza(ingredientFactory);
                pizza.setName("Chicago Style Veggie Pizza");
            }
            return pizza;
        }
    }

    // CheesePizza
    public static class CheesePizza extends Pizza {
        PizzaIngredientFactory ingredientFactory;

        public CheesePizza(PizzaIngredientFactory ingredientFactory) {
            this.ingredientFactory = ingredientFactory;
        }

        void prepare() {
            System.out.println("Preparing " + name);
            dough = ingredientFactory.createDough();
            sauce = ingredientFactory.createSauce();
            cheese = ingredientFactory.createCheese();
        }
    }

    // VeggiePizza
    public static class VeggiePizza extends Pizza {
        PizzaIngredientFactory ingredientFactory;

        public VeggiePizza(PizzaIngredientFactory ingredientFactory) {
            this.ingredientFactory = ingredientFactory;
        }

        void prepare() {
            System.out.println("Preparing " + name);
            dough = ingredientFactory.createDough();
            sauce = ingredientFactory.createSauce();
            veggies = ingredientFactory.createVeggies();
        }
    }

    // Main Method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to MyPizzaApp!");
        System.out.print("Enter the franchise (NY/Chicago): ");
        String franchise = scanner.nextLine().trim();

        System.out.print("Enter the type of pizza (cheese/veggie): ");
        String pizzaType = scanner.nextLine().trim();

        PizzaStore store;
        if (franchise.equalsIgnoreCase("NY")) {
            store = new NYPizzaStore();
        } else if (franchise.equalsIgnoreCase("Chicago")) {
            store = new ChicagoPizzaStore();
        } else {
            System.out.println("Sorry, we don't have that franchise.");
            return;
        }

        Pizza pizza = store.orderPizza(pizzaType);
        if (pizza != null) {
            System.out.println("You ordered a " + pizza.getName() + ". Enjoy!");
        }

        scanner.close();
    }
}