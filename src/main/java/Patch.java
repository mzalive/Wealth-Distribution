import java.util.LinkedList;

/**
 * A patch in the field.
 *
 * @author Wenzhuo Mi 818944
 *
 */
 class Patch {
    private double grain_here = 0;
    private int max_grain_here = 0;
    LinkedList<Turtle> turtles_here;

    Patch() {
        this.turtles_here = new LinkedList<>();
    }

    void harvest() {
        int num_turtle = turtles_here.size();
        if (num_turtle > 0) {
            int share = (int) Math.floor(grain_here / num_turtle);
            for (Turtle t: turtles_here) {
                t.addHarvest(share);
            }
            grain_here = 0;
        }
        turtles_here.clear();
    }

    void growGrain() {
        grain_here += Params.NUM_GRAIN_GROWN;
        if (grain_here > max_grain_here)
            grain_here = max_grain_here;
    }

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
