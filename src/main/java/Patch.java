import java.util.LinkedList;

/**
 * A patch in the field.
 */
 class Patch {
    private double grain_here = 0;
    private int max_grain_here = 0;
    // A patch is aware of the turtles currently at here
    // by holding a list of reference of Turtle instances
    LinkedList<Turtle> turtles_here;

    Patch() {
        this.turtles_here = new LinkedList<>();
    }

    /**
     * As the patch knows which turtles are currently on the spot,
     * it's more natural to implement the harvest method as a patch behavior.
     */
    void harvest() {
        int num_turtle = turtles_here.size();
        if (num_turtle > 0) {
            // only harvest if at least 1 turtle on spot
            int share = (int) Math.floor(grain_here / num_turtle);
            for (Turtle t: turtles_here) {
                t.addHarvest(share);
            }
            grain_here = 0;
        }
        // clear the reference list for preparation of next round of turtle move.
        turtles_here.clear();
    }

    /**
     * grow grain
     */
    void growGrain() {
        grain_here += Params.NUM_GRAIN_GROWN;
        if (grain_here > max_grain_here)
            grain_here = max_grain_here;
    }

    /**
     * add grain by value
     * used in patch init phase thus no max_grain limit.
     * @param grain
     */
    void addGrain_here(double grain) {
        grain_here += grain;
    }

    double getGrain_here() {
        return grain_here;
    }

    void setGrain_here(double grain_here) {
        this.grain_here = grain_here;
    }

    int getMax_grain_here() {
        return max_grain_here;
    }

    void setMax_grain_here(int max_grain_here) {
        this.max_grain_here = max_grain_here;
    }
}
