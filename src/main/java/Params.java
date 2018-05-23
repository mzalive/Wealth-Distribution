import java.util.Random;

/**
 * Parameters that influence the behaviour of the system.
 *
 * @author Wenzhuo Mi 818944
 *
 */
class Params {

    public enum WealthClass {
        RICH, MIDDLE, POOR
    }

    // The height of the field.
    static int HEIGHT = 51;

    // The width of the field.
    static int WIDTH = 51;

    // The maximum amount of grain a patch can hold.
    static int MAX_GRAIN = 50;

    // The percentage of patches that filled with full amount of grain initially.
    static double PERCENT_BEST_LAND = 0.1;

    // The number of people initially on on the field.
    static int NUM_PEOPLE = 250;

    // The minimum life expectancy of a individual.
    static int LIFE_EXPECTANCY_MIN = 1;

    // The maximum life expectancy of a individual.
    static int LIFE_EXPECTANCY_MAX = 83;

    // The maximum amount of grain a individual can consume per day.
    static int METABOLISM_MAX = 15;

    // The amount of patches that a individual can see.
    static int MAX_VISION = 5;

    // The interval for a patch to growth grain.
    static int GRAIN_GROWTH_INTERVAL = 1;

    // The amount of grain each time a patch can grow.
    static int NUM_GRAIN_GROWN = 4;

    // TAX MODE Parameters
    static boolean TAX_MODE = false;
    static double TAX_RATE_RICH = 0.2;
    static double TAX_RATE_MIDDLE = 0.1;
    static double TAX_RATE_POOR = 0.0;

    static int getRandomLifeExpectancy() {
        Random random = new Random();
        return random.nextInt(LIFE_EXPECTANCY_MAX - LIFE_EXPECTANCY_MIN + 1) + LIFE_EXPECTANCY_MIN;
    }

    static int getRandomMetabolism() {
        Random random = new Random();
        return random.nextInt(METABOLISM_MAX) + 1;
    }

    static int getRandomVision() {
        Random random = new Random();
        return random.nextInt(MAX_VISION) + 1;
    }


}
