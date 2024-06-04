package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class World {
    private TETile[][] world;
    private int width;
    private int height;
    private final int BOUND = 10;
    private final int MINNUMBER = 12;

    public World(int w, int h) {
        width = w;
        height = h;
        world = new TETile[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                world[i][j] = Tileset.WALL;
            }
        }
    }

    public void addRoom(Room r) {
        int x = r.getBottomLeft().getX();
        int y = r.getBottomLeft().getY();
        int w = r.getWidth();
        int h = r.getHeight();
        for (int i = 0; i < h; i++) {
            world[x][y + i] = Tileset.WALL;
            world[x + w - 1][y + i] = Tileset.WALL;
        }

        for (int i = 0; i < w; i++) {
            world[x + i][y] = Tileset.WALL;
            world[x + i][y + h - 1] = Tileset.WALL;
        }

        for (int i = 0; i < w - 2; i++) {
            for (int j = 0; j < h - 2; j++) {
                world[x + 1 + i][y + 1 + j] = Tileset.FLOOR;
            }
        }
    }

    public void addConnection(Position p1, Position p2, Random r) {
        boolean isVertcal = r.nextBoolean();
        int xMin = Math.min(p1.getX(), p2.getX());
        int xMax = Math.max(p1.getY(), p2.getX());
        int yMin = Math.min(p1.getY(), p2.getY());
        int yMax = Math.max(p1.getY(), p2.getY());
        if (isVertcal) {
            for (int y = yMin; y <= yMax; y++) {
                world[xMin][y] = Tileset.FLOOR;
            }
            for (int x = xMin; x <= xMax; x++) {
                world[x][yMax] = Tileset.FLOOR;
            }
        } else {
            for (int x = xMin; x <= xMax; x++) {
                world[x][yMax] = Tileset.FLOOR;
            }
            for (int y = yMin; y <= yMax; y++) {
                world[xMax][y] = Tileset.FLOOR;
            }
        }
    }

    public TETile[][] generate(long seed) {
        ArrayDeque<Room> arrayRoom = new ArrayDeque<>();
        Random r = new Random(seed);
        int numRoom = MINNUMBER + r.nextInt(8);
        for (int i = 0; i < numRoom; i++) {
            while (true) {
                int w = r.nextInt(BOUND) + 5;
                int h = r.nextInt(BOUND) + 4;
                int x = r.nextInt(width - w - 1) + 1;
                int y = r.nextInt(height - h - 1) + 1;
                Room newRoom = new Room(new Position(x, y), w, h);
                if (arrayRoom.isEmpty()) {
                    arrayRoom.addLast(newRoom);
                    break;
                } else {
                    boolean flat = false;
                    for (int j = 0; j < arrayRoom.size(); j++) {
                        flat = flat || newRoom.isOverlap(arrayRoom.get(j));
                    }
                    if (!flat) {
                        arrayRoom.addLast(newRoom);
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < arrayRoom.size(); i++) {
            addRoom(arrayRoom.get(i));
        }

        for (int i = 0; i < arrayRoom.size() - 1; i++) {
            Position p1 = arrayRoom.get(i).centerPosition();
            Position p2 = arrayRoom.get(i + 1).centerPosition();
            addConnection(p1, p2, r);
        }
        delete();
        addPlayer(r);
        return this.world;
    }

    private boolean isDelete(int x, int y) {
        boolean flat = false;
        int xp = x - 1;
        int yp = y - 1;
        for (int i = xp; i < xp + 3; i++) {
            for (int j = yp; j < yp + 3; j++) {
                if (i < 0 || i >= width || j < 0 || j >= height) {
                    continue;
                }
                flat = flat || world[i][j] == Tileset.FLOOR;
            }
        }
        return !flat;
    }

    public void delete() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (isDelete(i, j)) {
                    world[i][j] = Tileset.NOTHING;
                }
            }
        }
    }

    public void addPlayer(Random r) {
        while (true) {
            int x = r.nextInt(width);
            int y = r.nextInt(height);
            if (world[x][y] == Tileset.FLOOR) {
                world[x][y] = Tileset.PLAYER;
                break;
            }
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        World w = new World(Game.WIDTH, Game.HEIGHT);
        TETile[][] world = w.generate(Game.HEIGHT);
        ter.initialize(Game.WIDTH, Game.HEIGHT);
        ter.renderFrame(world);
    }
}
