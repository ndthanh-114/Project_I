package mygame;

import java.util.Random;

public class Shape {

    public enum MyShape { NoShape, ZShape, SShape, LineShape,
        TShape, SquareShape, LShape, MirroredLShape }

    public MyShape pieceShape;
    private int coords[][];
    private int[][][] coordsTable;


    public Shape() {

        initShape();
    }

    private void initShape() {

        coords = new int[4][2];

        coordsTable = new int[][][] {
                { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
                { { 0, -1 },  { 0, 0 },   { -1, 0 },  { -1, 1 } },//Z
                { { 0, -1 },  { 0, 0 },   { 1, 0 },   { 1, 1 } },//S
                { { 0, -1 },  { 0, 0 },   { 0, 1 },   { 0, 2 } },//Line
                { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, 1 } },//T
                { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } },//Square
                { { -1, -1 }, { 0, -1 },  { 0, 0 },   { 0, 1 } },
                { { 1, -1 },  { 0, -1 },  { 0, 0 },   { 0, 1 } }//Mirror
        };

        setShape(MyShape.NoShape);
    }

//    protected enum MyShape { NoShape, ZShape, SShape, LineShape,
//        TShape, SquareShape, LShape, MirroredLShape }
    
    protected void setShape(MyShape shape) {

        for (int i = 0; i < 4 ; i++) {

            for (int j = 0; j < 2; ++j) {

                coords[i][j] = coordsTable[shape.ordinal()][i][j];
            }
        }

        pieceShape = shape;
    }

    private void setX(int index, int x) { coords[index][0] = x; }
    private void setY(int index, int y) { coords[index][1] = y; }
    public int x(int index) { return coords[index][0]; }
    public int y(int index) { return coords[index][1]; }
    public MyShape getShape()  { return pieceShape; }

    public void setRandomShape() {

        var r = new Random();
        int x = Math.abs(r.nextInt()) % 7 + 1;

        MyShape[] values = MyShape.values();
        setShape(values[x]);
    }

    public int minX() {

        int m = coords[0][0];

        for (int i=0; i < 4; i++) {

            m = Math.min(m, coords[i][0]);
        }

        return m;
    }


    public int minY() {

        int m = coords[0][1];

        for (int i=0; i < 4; i++) {

            m = Math.min(m, coords[i][1]);
        }

        return m;
    }

    public Shape rotateLeft() {

        if (pieceShape == MyShape.SquareShape) {

            return this;
        }

        var result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; ++i) {

            result.setX(i, y(i));
            result.setY(i, -x(i));
        }

        return result;
    }

    public Shape rotateRight() {

        if (pieceShape == MyShape.SquareShape) {

            return this;
        }

        var result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; ++i) {

            result.setX(i, -y(i));
            result.setY(i, x(i));
        }

        return result;
    }
}

