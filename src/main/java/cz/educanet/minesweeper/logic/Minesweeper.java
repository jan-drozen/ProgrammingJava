package cz.educanet.minesweeper.logic;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;
import java.lang.Math;

public class Minesweeper {

    private byte[][] mineMap;
    private int bombCount;
    private byte[][] pitchState;

    private int rowsCount;
    private int columnsCount;

    public Minesweeper(int rows, int columns) {
        this.rowsCount = rows;
        this.columnsCount = columns;
        this.pitchState = new byte[rows][columns];
        this.mineMap = new byte[rows][columns];
        Init();
    }

    private void Init()
    {
        var random = new Random();
        for (int i = 0; i < this.rowsCount; i++)
            for (int j = 0; j < this.columnsCount; j++)
            {
                if (random.nextInt(10) == 1) {
                    this.mineMap[i][j] = 1;
                    this.bombCount++;
                }
            }
    }

    /**
     * 0 - Hidden
     * 1 - Visible
     * 2 - Flag
     * 3 - Question mark
     *
     * @param x X
     * @param y Y
     * @return field type
     */
    public int getField(int x, int y) {
        return pitchState[x][y];
    }

    /**
     * Toggles the field state, ie.
     * 0 -> 1,
     * 1 -> 2,
     * 2 -> 3 and
     * 3 -> 0
     *
     * @param x X
     * @param y Y
     */
    public void toggleFieldState(int x, int y) {
        this.pitchState[x][y] = (byte)((this.pitchState[x][y] + 3)%4);
    }

    /**
     * Reveals the field and all fields adjacent (with 0 adjacent bombs) and all fields adjacent to the adjacent fields... ect.
     *
     * @param x X
     * @param y Y
     */
    public void reveal(int x, int y) {
        Queue<Point> queue = new ArrayDeque<Point>();
        queue.add(new Point(x, y));
        while (!queue.isEmpty()) {
            var currentPoint = queue.remove();
            if (pitchState[currentPoint.x][currentPoint.y] == 0)
                pitchState[currentPoint.x][currentPoint.y] = 1;
            if (getAdjacentBombCount(currentPoint.x, currentPoint.y) == 0)
                for (int i = Math.max(currentPoint.x - 1, 0); i <= Math.min(rowsCount-1, currentPoint.x + 1); i++) {
                    for (int j = Math.max(currentPoint.y - 1, 0); j <= Math.min(columnsCount-1, currentPoint.y + 1); j++) {
                        if (pitchState[i][j] == 0) {
                            var nextPoint = new Point(i, j);
                            if (!queue.contains(nextPoint))
                                queue.add(nextPoint);
                        }
                    }
                }
        }
    }

    /**
     * Returns the amount of adjacent bombs
     *
     * @param x X
     * @param y Y
     * @return number of adjacent bombs
     */
    public int getAdjacentBombCount(int x, int y) {
        if (mineMap[x][y] == 1)
            return 0;
        var bombCount = 0;

        for (int i = Math.max(x-1,0); i <= Math.min(rowsCount-1, x+1); i++) {
            for (int j = Math.max(y-1,0); j <= Math.min(columnsCount-1, y+1);j++) {
                bombCount += mineMap[i][j];
            }
        }

        return bombCount;
    }

    /**
     * Checks if there is a bomb on the current position
     *
     * @param x X
     * @param y Y
     * @return true if bomb on position
     */
    public boolean isBombOnPosition(int x, int y) {
        return this.mineMap[x][y] == 1;
    }

    /**
     * Returns the amount of bombs on the field
     *
     * @return bomb count
     */
    public int getBombCount() {
        return this.bombCount;
    }

    /**
     * total bombs - number of flags
     *
     * @return remaining bomb count
     */
    public int getRemainingBombCount() {
        var remainingBombs = this.bombCount;
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                if (pitchState[i][j] == 2 && mineMap[i][j] == 1)
                    remainingBombs--;
            }
        }
        return remainingBombs;
    }

    /**
     * returns true if every flag is on a bomb, else false
     *
     * @return if player won
     */
    public boolean didWin() {
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                if (pitchState[i][j] != 2 && mineMap[i][j] == 1)
                    return false;
            }
        }
        return true;
    }

    /**
     * returns true if player revealed a bomb, else false
     *
     * @return if player lost
     */
    public boolean didLoose() {
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                if (pitchState[i][j] == 1 && mineMap[i][j] == 1)
                    return true;
            }
        }
        return false;
    }

    public int getRows() {
        return rowsCount;
    }

    public int getColumns() {
        return columnsCount;
    }

}
