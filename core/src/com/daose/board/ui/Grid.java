package com.daose.board.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.daose.board.Board;

/**
 * Created by student on 01/01/16.
 */
public class Grid {

    public enum TileAnimation {
        CIRCLE_IN,
        CIRCLE_OUT
    }

    private int length;
    private float tileSize, boardHeight, boardOffset;
    private float solutionTimer, timeShown;
    private boolean isShowing;
    private Tile[][] tiles;

    private float pitch;

    private Array<Tile> solutionList;
    private Array<Tile> selectedList;
    private Array<Tile> correctList;

    public Grid() {
        solutionList = new Array<Tile>();
        selectedList = new Array<Tile>();
        correctList = new Array<Tile>();
        isShowing = true;
    }

    public void create(int length, int numTiles, int timeShown, TileAnimation ani) {
        this.timeShown = timeShown;
        this.length = length;
        tiles = new Tile[length][length];
        tileSize = Board.gameWidth / length;
        boardHeight = tileSize * length;
        boardOffset = (Board.gameHeight - boardHeight) / 2;

        pitch = 0.5f;

        for (int row = 0; row < length; row++) {
            for (int col = 0; col < length; col++) {
                tiles[row][col] = new Tile(col * tileSize + tileSize / 2, row * tileSize + boardOffset + tileSize / 2, tileSize, tileSize);
            }
        }
        setSolution(numTiles);
        setAnimation(ani);
    }

    public float getHeight() {
        return boardHeight + boardOffset;
    }

    public int getSolutionSize() {
        return solutionList.size;
    }

    public int getSelectedSize() {
        return selectedList.size;
    }

    public int getCorrectSize() {
        return correctList.size;
    }

    public void setAnimation(TileAnimation ani) {
        switch (ani) {
            case CIRCLE_IN:
                if (length % 2 == 1) {
                    int middleRow = tiles.length / 2;
                    int middleCol = tiles.length / 2;
                    int waves = tiles.length / 2 + 1;
                    for (int i = 0; i < waves; i++) {
                        int row, col, j;

                        if (i == 0) continue;
                        //Bottom row (left -> right)
                        row = middleRow - i;
                        col = middleCol - i;
                        for (j = 0; j < (2 * i) + 1; j++) {
                            tiles[row][col].setTimer((float) (-0.1 * (i + j)));
                            if ((j + 1) < (2 * i) + 1) {
                                col++;
                            }
                        }
                        //Right column (bottom -> top)
                        row++;
                        for (j = 0; j < (2 * i); j++) {
                            tiles[row][col].setTimer((float) (-0.1 * (i + j)));
                            if (j < (2 * i) - 1) row++;
                        }
                        //Top Row (left <- right)
                        col--;
                        for (j = 0; j < (2 * i); j++) {
                            tiles[row][col].setTimer((float) (-0.1 * (i + j)));
                            if (j < (2 * i) - 1) col--;
                        }
                        //Left column (top -> bottom)
                        row--;
                        for (j = 0; j < (2 * i); j++) {
                            tiles[row][col].setTimer((float) (-0.1 * (i + j)));
                            if (j < (2 * i) - 1) row--;
                        }
                    }
                }
                break;
        }
    }

    public void setSolution(int numTiles) {
        int row, col;
        for (int i = 0; i < numTiles; i++) {
            do {
                row = MathUtils.random(tiles.length - 1);
                col = MathUtils.random(tiles.length - 1);
            } while (tiles[row][col].isSelected());
            tiles[row][col].setSelected(true);
            solutionList.add(tiles[row][col]);
        }
    }

    public void showSolution(boolean b) {
        if (b) {
            for (int i = 0; i < solutionList.size; i++) {
                solutionList.get(i).setShowAnswer(true);
            }
        } else {
            for (int i = 0; i < solutionList.size; i++) {
                solutionList.get(i).setShowAnswer(false);
            }
        }
    }

    public void reset() {
        solutionList.clear();
        selectedList.clear();
        correctList.clear();
        solutionTimer = 0;
        isShowing = true;
    }

    public void update(float dt) {
        if (isShowing) {
            solutionTimer += dt;
            if (solutionTimer > timeShown) {
                for (int i = 0; i < solutionList.size; i++) {
                    solutionList.get(i).setSelected(false);
                }
                isShowing = false;
            }
        }
        updateTiles(dt);
    }

    public boolean contains(float x, float y) {
        if ((y > boardOffset) && (y < (boardOffset + boardHeight)) && (x > 0) && (x < Board.gameWidth)) {
            return true;
        } else return false;
    }

    public void tileTapped(float x, float y) {
        int row = (int) ((y - boardOffset) / tileSize);
        int col = (int) (x / tileSize);
        if (!tiles[row][col].isSelected()) {
            tiles[row][col].setSelected(true);
            selectedList.add(tiles[row][col]);
            if (solutionList.contains(tiles[row][col], true)) {
                correctList.add(tiles[row][col]);
                Board.tapped.play(1.0f, pitch, 1);
                pitch += 0.05;
            } else {
                Board.tapped.play(1, 0.8f, 1);
            }
        }
    }

    public void render(SpriteBatch sb) {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                tiles[row][col].render(sb);
            }
        }
    }

    private void updateTiles(float dt) {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                tiles[row][col].update(dt);
            }
        }
    }

    public void setTimeShown(int time) {
        timeShown = time;
    }

    public boolean isShowing() {
        return isShowing;
    }
}
