package byog.Core;

import java.util.Random;

public class Room {
    public Position BottomLeft;
    public int width;
    public int height;
    public Room(Position p, int w, int h) {
        BottomLeft = p;
        width = w;
        height = h;
    }

    public boolean isOverlap(Room r) {
        boolean flat = BottomLeft.x > r.BottomLeft.x + r.width || BottomLeft.y > r.BottomLeft.y + r.height
                || BottomLeft.x + width < r.BottomLeft.x || BottomLeft.y + height < r.BottomLeft.y;
        return !flat;
    }

    public Position CenterPosition() {
        return new Position(BottomLeft.x + ((int) (width / 2)), BottomLeft.y + ((int)(height / 2)));
    }

    public Position sample(Random r) {
        int mode = r.nextInt(4);
        double t = r.nextDouble();
        int x = (int)((1 - t) + t * (width - 2));
        int y = (int)((1 - t) + t * (height - 2));
        if (mode == 0) {
            return new Position(BottomLeft.x, y);
        } else if (mode == 1) {
            return new Position(BottomLeft.x + width - 1, y);
        } else if (mode == 2) {
            return new Position(x, BottomLeft.y);
        } else{
            return new Position(x, BottomLeft.y + height - 1);
        }
    }
}
