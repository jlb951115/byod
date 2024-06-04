package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class World {
    private TETile[][] world;
    private int width;
    private int height;

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
        int x = r.BottomLeft.x;
        int y = r.BottomLeft.y;
        int w = r.width;
        int h = r.height;
        for (int i = 0; i < h; i++) {
            world[x][y + i] = Tileset.WALL;
            world[x + w - 1] [y + i] = Tileset.WALL;
        }

        for (int i = 0; i < w; i++) {
            world[x + i][y] = Tileset.WALL;
            world[x + i] [y + h - 1] = Tileset.WALL;
        }

        for (int i = 0; i < w - 2; i++) {
            for (int j = 0; j < h - 2; j++) {
                world[x + 1 + i] [y + 1 + j] = Tileset.FLOOR;
            }
        }
    }

    public void addConnection(Position p1, Position p2, Random r) {
        boolean isVertcal = r.nextBoolean();
        int xMin = Math.min(p1.x, p2.x);
        int xMax = Math.max(p1.x, p2.x);
        int yMin = Math.min(p1.y, p2.y);
        int yMax = Math.max(p1.y, p2.y);
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

    public TETile[][] Generate(long seed) {
        ArrayDeque<Room> ArrayRoom = new ArrayDeque<>();
        Random r = new Random(seed);
        int numRoom = 12 + r.nextInt(8);
        for (int i = 0; i < numRoom; i++) {
            while (true) {
                int w = r.nextInt(10) + 5;
                int h = r.nextInt(10) + 4;
                int x = r.nextInt(width - w - 1) + 1;
                int y = r.nextInt(height - h - 1) + 1;
                Room newRoom = new Room(new Position(x, y), w, h);
                if (ArrayRoom.isEmpty()) {
                    ArrayRoom.addLast(newRoom);
                    break;
                } else {
                    boolean flat = false;
                    for (int j = 0; j < ArrayRoom.size(); j++) {
                        flat = flat || newRoom.isOverlap(ArrayRoom.get(j));
                    }
                    if (!flat) {
                        ArrayRoom.addLast(newRoom);
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < ArrayRoom.size(); i++) {
            addRoom(ArrayRoom.get(i));
        }

        for (int i = 0; i < ArrayRoom.size() - 1; i++) {
            Position p1 = ArrayRoom.get(i).CenterPosition();
            Position p2 = ArrayRoom.get(i + 1).CenterPosition();
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
                    world[i][j]= Tileset.NOTHING;
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

    public static void main( String[] args) {
        TERenderer ter = new TERenderer();
        World w = new World(80, 30);
        TETile[][] world = w.Generate(18);
        ter.initialize(80, 30);
        ter.renderFrame(world);
    }
}
