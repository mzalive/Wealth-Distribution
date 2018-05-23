import java.util.Random;

/**
 * A util class for representing coordinate and direction of a turtle.
 *
 * @author Wenzhuo Mi 818944
 *
 */
 class Coordinate {

    public enum Direction {
        NORTH, SOUTH, WEST, EAST
    }

    private int x_max = Params.WIDTH - 1;
    private int y_max = Params.HEIGHT - 1;
    private int x, y;
    private Direction face;

    Coordinate() {
        getRandomCoordinate();
    }

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private void getRandomCoordinate() {
        Random random = new Random();
        x = random.nextInt(Params.WIDTH);
        y = random.nextInt(Params.HEIGHT);
        face = getRandomDirection();
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void step() {
        Coordinate nextPos = next(1, this.face);
        this.x = nextPos.getX();
        this.y = nextPos.getY();
    }

    private Direction getRandomDirection() {
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

    void setDirection(Direction direction) {
        face = direction;
    }

    Direction getDirection() {
        return face;
    }

    Coordinate next(int step, Direction face) {
        int x = this.x;
        int y = this.y;
        switch (face) {
            case NORTH:
                y -= step;
                y = (y < 0) ? y + Params.HEIGHT : y;
                break;
            case SOUTH:
                y += step;
                y = (y >= Params.HEIGHT) ? y - Params.HEIGHT : y;
                break;
            case WEST:
                x -= step;
                x = (x < 0) ? x + Params.WIDTH : x;
                break;
            case EAST:
                x += step;
                x = (x >= Params.WIDTH) ? x - Params.WIDTH : x;
                break;
        }
        return new Coordinate(x, y);
    }
}
