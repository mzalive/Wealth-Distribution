import java.util.Random;

/**
 * A turtle that can represent a person.
 *
 * @author Wenzhuo Mi 818944
 *
 */
public class Turtle implements Comparable<Turtle> {
    private int age;
    private int wealth;
    private int life_expectancy;
    private int metabolism;
    private int vision;
    private Params.WealthClass turtle_class;
    public Coordinate location;

    public Turtle() {
        setInitTurtleVars();
        location = new Coordinate();
        Random random = new Random();
        age = random.nextInt(life_expectancy);
    }

    public void setInitTurtleVars() {
        Random random = new Random();
        age = 0;
        life_expectancy = Params.getRandomLifeExpectancy();
        metabolism = Params.getRandomMetabolism();
        vision = Params.getRandomVision();
        wealth = metabolism + random.nextInt(50);
    }

    public void addHarvest(int share) {
        this.wealth += share;
    }

    public Params.WealthClass updateTurtleClass(int max_wealth) {
        if (wealth <= max_wealth / 3)
            turtle_class = Params.WealthClass.POOR;
        else if (wealth <= max_wealth * 2 / 3)
            turtle_class = Params.WealthClass.MIDDLE;
        else
            turtle_class = Params.WealthClass.RICH;
        return  turtle_class;
    }

    public int moveEatAgeDie() {
        location.step();
        wealth -= metabolism;
        age ++;
        if (wealth < 0 || age >= life_expectancy)
            setInitTurtleVars();
        return wealth;
    }

    public int getVision() {
        return vision;
    }

    public int getWealth() {
        return wealth;
    }

    public Params.WealthClass getTurtle_class() {
        return turtle_class;
    }

    public int compareTo(Turtle turtle) {
        return this.wealth - turtle.getWealth();
    }
}