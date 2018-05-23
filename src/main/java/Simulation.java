import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * The Main class of the simulation.
 * Models the top-level behavior of the NetLogo.
 */
public class Simulation {
    private Patch[][] field;
    private Turtle[] turtles;
    private int ticks = 0;

    //data to output
    private int count_rich = 0;
    private int count_middle = 0;
    private int count_poor = 0;
    private double gini_index = 0;

    public Simulation() {
        setupPatches();
        setupTurtles();
        update_gini_index();
    }

    /**
     * Init the Patches
     */
    private void setupPatches() {
        Random random = new Random();

        // create the world
        field = new Patch[Params.WIDTH][Params.HEIGHT];
        for (int x = 0; x < Params.WIDTH; x++)
            for (int y = 0; y < Params.HEIGHT; y++)
                field[x][y] = new Patch();

        // init seed patches
        for (Patch[] a: field) {
            for (Patch p : a) {
                if (random.nextDouble() <= Params.PERCENT_BEST_LAND) {
                    p.setMax_grain_here(Params.MAX_GRAIN);
                    p.setGrain_here(Params.MAX_GRAIN);
                }
            }
        }

        // spread
        for (int i = 0; i < 5; i++) {
            for (int x = 0; x < Params.WIDTH; x++) {
                for (int y = 0; y < Params.HEIGHT; y++) {
                    if (field[x][y].getMax_grain_here() > 0) {
                        field[x][y].setGrain_here(field[x][y].getMax_grain_here());
                    }
                }
            }
            diffuse(0.25);
        }

        // spread more
        for (int i = 0; i < 10; i++) {
            diffuse(0.25);
        }

        // fix max_grain_here and fraction
        for (Patch[] a: field) {
            for (Patch p : a) {
                p.setGrain_here(Math.floor(p.getGrain_here()));
                p.setMax_grain_here((int)p.getGrain_here());
            }
        }

    }

    /**
     * Util method for identify a patch by a coordinate.
     * @param coordinate the location.
     * @return the patch in the location.
     */
    private Patch getPatchByCoordinate(Coordinate coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        return field[x][y];
    }

    /**
     * Imitation of the NetLogo's diffuse method.
     * Fractions in the diffuse process are reserved.
     * No heat-loss due to the nature of wrapped world.
     * Diffusion among multiple patches happens simultaneously (i.e. order-independent and no overlapping)
     * @param rate
     */
    private void diffuse(double rate) {

        // store proposed amount in a temp 2-D array corresponding to the world before actual diffusion
        // to prevent diffusion overlap.
        double pies[][] = new double[Params.WIDTH][Params.HEIGHT];
        for (int x = 0; x < Params.WIDTH; x++) {
            for (int y = 0; y < Params.HEIGHT; y++) {
                double grain_here = field[x][y].getGrain_here();
                pies[x][y] = grain_here * rate;
                field[x][y].setGrain_here(grain_here * (1 - rate));
            }
        }

        // execute the diffusion
        for (int x = 0; x < Params.WIDTH; x++) {
            for (int y = 0; y < Params.HEIGHT; y++) {
                if (pies[x][y] > 0) {
                    double share = pies[x][y] / 8;
                    Coordinate base = new Coordinate(x, y);
                    // N, S, W, E
                    for (Coordinate.Direction face: Coordinate.Direction.values())
                        getPatchByCoordinate(base.next(1, face)).addGrain_here(share);
                    // NE
                    getPatchByCoordinate(base
                            .next(1, Coordinate.Direction.NORTH)
                            .next(1, Coordinate.Direction.EAST))
                            .addGrain_here(share);
                    // NW
                    getPatchByCoordinate(base
                            .next(1, Coordinate.Direction.NORTH)
                            .next(1, Coordinate.Direction.WEST))
                            .addGrain_here(share);
                    // SE
                    getPatchByCoordinate(base
                            .next(1, Coordinate.Direction.SOUTH)
                            .next(1, Coordinate.Direction.EAST))
                            .addGrain_here(share);
                    // SW
                    getPatchByCoordinate(base
                            .next(1, Coordinate.Direction.SOUTH)
                            .next(1, Coordinate.Direction.WEST))
                            .addGrain_here(share);

                }
            }
        }
    }

    /**
     * Init the turtles (people)
     */
    private void setupTurtles() {
        int max_wealth = 0;
        turtles = new Turtle[Params.NUM_PEOPLE];
        for (int i = 0; i < Params.NUM_PEOPLE; i++)
            turtles[i] = new Turtle();
        for (Turtle t: turtles) {
            int x = t.location.getX();
            int y = t.location.getY();
            field[x][y].turtles_here.add(t);
            if (t.getWealth() > max_wealth )
                max_wealth = t.getWealth();
        }
        updateTurtlesClass(max_wealth);

    }

    /**
     * Let the turtle to decide which direction to do in next tick.
     * @param loc current location
     * @param vision the vision applies.
     */
    private void turnTowardGrain(Coordinate loc, int vision) {
        double best_grain = 0;
        Coordinate.Direction best_direction = loc.getDirection();
        for (Coordinate.Direction face :Coordinate.Direction.values()) {
            double grain_ahead = 0;
            for (int i = 1; i <= vision; i++) {
                Coordinate loc_next = loc.next(i, face);
                if (loc_next != null) {
                    int x = loc_next.getX();
                    int y = loc_next.getY();
                    grain_ahead += field[x][y].getGrain_here();
                }
            }
            if (grain_ahead > best_grain) {
                best_grain = grain_ahead;
                best_direction = face;
            }
        }
        loc.setDirection(best_direction);

    }

    /**
     * update the class statistics
     * @param max_wealth the max_wealth in current tick.
     */
    private void updateTurtlesClass(int max_wealth) {
        for (Turtle t: turtles) {
            Params.WealthClass currentClass = t.updateTurtleClass(max_wealth);
            switch (currentClass) {
                case RICH:
                    count_rich ++;
                    break;
                case MIDDLE:
                    count_middle ++;
                    break;
                case POOR:
                    count_poor ++;
                    break;
            }

        }
    }


    /**
     * go for one tick
     */
    void go() {
        // reset statistics
        count_rich = 0;
        count_middle = 0;
        count_poor = 0;
        gini_index = 0;
        int max_wealth = 0;

        // ask turtles to turn
        for (Turtle t: turtles) {
            turnTowardGrain(t.location, t.getVision());
        }

        // harvest
        for (Patch[] a: field) {
            for (Patch p : a) {
                p.harvest();
                if (ticks % Params.GRAIN_GROWTH_INTERVAL == 0)
                    p.growGrain();
            }
        }

        // ask turtle to move_eat_age_die
        for (Turtle t: turtles) {
            int wealth = t.moveEatAgeDie();
            if (wealth > max_wealth)
                max_wealth = wealth;
            Coordinate loc = t.location;
            field[loc.getX()][loc.getY()].turtles_here.add(t);
        }

        // update statistics
        updateTurtlesClass(max_wealth);
        update_gini_index();

        ticks ++;

    }

    /**
     * Calculate Gini Index
     */
    private void update_gini_index() {
        int total_wealth = 0;
        int wealth_sum_so_far = 0;
        double gini_index_reserve = 0.0;
        for (Turtle t: turtles) {
            total_wealth += t.getWealth();
        }
        Arrays.sort(turtles);
        // update Gini-index
        for (int i = 0; i < Params.NUM_PEOPLE; ++i) {
            wealth_sum_so_far += turtles[i].getWealth();
            gini_index_reserve += ((double)i + 1) / Params.NUM_PEOPLE - ((double)wealth_sum_so_far / total_wealth);
        }
        gini_index = gini_index_reserve / Params.NUM_PEOPLE / 0.5;
    }


    int getCount_rich() {
        return count_rich;
    }

    int getCount_middle() {
        return count_middle;
    }

    int getCount_poor() {
        return count_poor;
    }

    double getGini_index() {
        return gini_index;
    }
}
