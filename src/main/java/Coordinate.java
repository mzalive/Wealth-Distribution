import java.util.Random;

/**
 * A util class for representing coordinate and direction of a turtle.
 *
 * @author Wenzhuo Mi 818944
 *
 */
public class Coordinate {

    public enum Direction {
        NORTH, SOUTH, WEST, EAST;
    }

    private int x_max = Params.WIDTH - 1;
    private int y_max = Params.HEIGHT - 1;
    private int x, y;
    private Direction face;

    public Coordinate() {
        getRandomCoordinate();
    }

    private Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void getRandomCoordinate() {
        Random random = new Random();
        x = random.nextInt(Params.WIDTH);
        y = random.nextInt(Params.HEIGHT);
        face = getRandomDirection();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinate step() {
        switch (face) {
            case NORTH:
                y --;
                break;
            case SOUTH:
                y ++;
                break;
            case WEST:
                x --;
                break;
            case EAST:
                x ++;
                break;
        }
        if (x < 0) x = 0;
        else if (x > x_max) x = x_max;
        if (y < 0) y = 0;
        else if (y > y_max) y = y_max;

        return this;
    }

    public Direction getRandomDirection() {
        Random random = new Random();
        switch (random.nextInt(4)) {
            case 0:
                return Direction.NORTH;
            case 1:
                return Direction.SOUTH;
            case 2:
                return Direction.WEST;
            case 3:
            default:
                return Direction.EAST;
        }
    }

    public void setDirection(Direction direction) {
        face = direction;
    }

    public Direction getDirection() {
        return face;
    }

    public Coordinate next(int step, Direction face) {
        int x = this.x;
        int y = this.y;
        switch (face) {
            case NORTH:
                y -= step;
                break;
            case SOUTH:
                y += step;
                break;
            case WEST:
                x -= step;
                break;
            case EAST:
                x += step;
                break;
        }
        if (x < 0 || y < 0 || x > x_max || y > y_max)
            return null;
        return new Coordinate(x, y);
    }
}
