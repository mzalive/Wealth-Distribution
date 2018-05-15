import java.util.LinkedList;

/**
 * A patch in the field.
 *
 * @author Wenzhuo Mi 818944
 *
 */
public class Patch {
    private double grain_here = 0;
    private double max_grain_here = 0;
    public LinkedList<Turtle> turtles_here;

    public Patch() {
        this.turtles_here = new LinkedList<Turtle>();
    }

    public void harvest() {
        int num_turtle = turtles_here.size();
        if (num_turtle > 0) {
            int share = (int) (grain_here / num_turtle);
            for (Turtle t: turtles_here) {
                t.addHarvest(share);
            }
        }
        turtles_here.clear();
    }

    public void growGrain() {
        grain_here += Params.NUM_GRAIN_GROWN;
        if (grain_here > max_grain_here)
            grain_here = max_grain_here;
    }

    public void addGrain_here(double grain) {
        grain_here += grain;
    }

    public double getGrain_here() {
        return grain_here;
    }

    public void setGrain_here(double grain_here) {
        this.grain_here = grain_here;
    }

    public double getMax_grain_here() {
        return max_grain_here;
    }

    public void setMax_grain_here(double max_grain_here) {
        this.max_grain_here = max_grain_here;
    }
}
