package byog.Core;


public class Room {
    private Position bottomLeft;
    private int width;
    private int height;
    public Room(Position p, int w, int h) {
        bottomLeft = p;
        width = w;
        height = h;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Position getBottomLeft() {
        return this.bottomLeft;
    }

    public boolean isOverlap(Room r) {
        boolean flat = bottomLeft.getX() > r.getBottomLeft().getX() + r.getWidth()
                || bottomLeft.getY() > r.getBottomLeft().getY() + r.getHeight()
                || bottomLeft.getX() + width < r.getBottomLeft().getX()
                || bottomLeft.getY() + height < r.getBottomLeft().getY();
        return !flat;
    }

    public Position centerPosition() {
        return new Position(bottomLeft.getX() + ((int) (width / 2)), bottomLeft.getY() + ((int) (height / 2)));
    }

}
