import java.util.Random;

/**
 * A turtle that can represent a person.
 */
public class Turtle implements Comparable<Turtle> {
    private int age;
    private int wealth;
    private int life_expectancy;
    private int metabolism;
    private int vision;
    private Params.WealthClass turtle_class;
    Coordinate location;

    Turtle() {
        setInitTurtleVars();
        location = new Coordinate();
        Random random = new Random();
        age = random.nextInt(life_expectancy);
    }

    /**
     * init the turtle.
     */
    private void setInitTurtleVars() {
        Random random = new Random();
        age = 0;
        life_expectancy = Params.getRandomLifeExpectancy();
        metabolism = Params.getRandomMetabolism();
        vision = Params.getRandomVision();
        wealth = metabolism + random.nextInt(50);
    }

    /**
     * add wealth by value
     * @param share
     */
    void addHarvest(int share) {
        double tax_rate = 0;
        // extension: Tax mode applies here
        if (Params.TAX_MODE) {
            switch (this.turtle_class) {
                case RICH:
                    tax_rate = Params.TAX_RATE_RICH;
                    break;
                case MIDDLE:
                    tax_rate = Params.TAX_RATE_MIDDLE;
                    break;
                case POOR:
                    tax_rate = Params.TAX_RATE_POOR;
                    break;
            }
        }
        this.wealth += (1-tax_rate) * share;
    }

    /**
     * update the class of the turtle given the max_wealth among the society
     * @param max_wealth
     * @return Updated WealthClass
     */
    Params.WealthClass updateTurtleClass(int max_wealth) {
        if (wealth <= (double)max_wealth / 3)
            turtle_class = Params.WealthClass.POOR;
        else if (wealth <= (double)max_wealth * 2 / 3)
            turtle_class = Params.WealthClass.MIDDLE;
        else
            turtle_class = Params.WealthClass.RICH;
        return  turtle_class;
    }

    /**
     * Imitation of the move-eate-age-die method in NetLogo
     * @return
     */
    int moveEatAgeDie() {
        location.step();
        wealth -= metabolism;
        age ++;
        if (wealth < 0 || age >= life_expectancy)
            setInitTurtleVars();
        return wealth;
    }

    int getVision() {
        return vision;
    }

    int getWealth() {
        return wealth;
    }

    public Params.WealthClass getTurtle_class() {
        return turtle_class;
    }

    /**
     * Implement the compareTo(Class<T>) interface to make the turtle comparable in the list
     * @param turtle
     * @return
     */
    public int compareTo(Turtle turtle) {
        return this.wealth - turtle.getWealth();
    }
}