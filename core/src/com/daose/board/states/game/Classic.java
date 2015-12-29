package com.daose.board.states.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.daose.board.Board;
import com.daose.board.helper.Stats;
import com.daose.board.states.GSM;
import com.daose.board.states.State;
import com.daose.board.states.StatsScreen;
import com.daose.board.states.TransitionState;
import com.daose.board.ui.Button;
import com.daose.board.ui.Score;
import com.daose.board.ui.Tile;

/**
 * Created by student on 27/12/15.
 */
public class Classic extends State {

    //Easy: 3 levels, 3x3 grid
    //Casual: 5 levels, 5x5 grid, last level 7x7
    //Bored?: 10 levels, 7x7 grid, last level 9x9
    public enum Difficulty {
        EASY,
        NORMAL,
        HARD
    }

    private Tile[][] tiles;
    private Array<Tile> solution;
    private Array<Tile> selected;
    private Array<Tile> correct;

    private int tileSize;
    private int boardOffset;
    private int boardHeight;

    private boolean isShowing;
    private float solutionTimer;

    private int level;
    private Difficulty difficulty;
    private int[] boardInfo;

    private Score score;
    private int completeBonus;

    private float scoreTimer;

    private Stats stats;

    private Button skip;

    public Classic(GSM gsm, Difficulty difficulty) {
        super(gsm);

        solution = new Array<Tile>();
        selected = new Array<Tile>();
        correct = new Array<Tile>();

        this.difficulty = difficulty;
        level = 1;
        boardInfo = new int[3];
        score = new Score(Board.WIDTH / 2, Board.HEIGHT / 2 + 300);
        stats = new Stats();
        stats.setDifficulty(difficulty);
        skip = new Button(Board.WIDTH / 2, Board.HEIGHT / 2 - 300, 100, 50);

        //0 = size, 1 = solution size, 2 = number of levels
        getBoardInfo();
        resetBoard();
        createBoard(boardInfo[0]);
        createSolution(boardInfo[1]);

    }

    public void dispose() {

    }


    private void createBoard(int size) {
        tiles = new Tile[size][size];
        tileSize = Board.WIDTH / size;
        boardHeight = tileSize * size;
        boardOffset = (Board.HEIGHT - boardHeight) / 2;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                tiles[row][col] = new Tile(
                        col * tileSize + tileSize / 2,
                        row * tileSize + boardOffset + tileSize / 2,
                        tileSize, tileSize);
            }
        }
        setTileTimer();
    }

    private void createSolution(int numTiles) {
        int row, col;

        for (int i = 0; i < numTiles; i++) {
            do {
                row = MathUtils.random(tiles.length - 1);
                col = MathUtils.random(tiles.length - 1);
            } while (tiles[row][col].isSelected());
            tiles[row][col].setSelected(true);
            solution.add(tiles[row][col]);
        }
    }

    private void resetBoard() {
        solutionTimer = 0;
        scoreTimer = 5;
        isShowing = true;
        solution.clear();
        selected.clear();
        correct.clear();
    }

    public void handleInput() {
        if (Gdx.input.justTouched() && !isShowing) {
            tap.x = Gdx.input.getX();
            tap.y = Gdx.input.getY();
            cam.unproject(tap);
            if ((tap.y > boardOffset) && (tap.y < (boardOffset + boardHeight)) && (tap.x > 0) && (tap.x < Board.WIDTH)) {
                tileTapped();
            } else if (skip.contains(tap.x, tap.y)) {
                skip.setSelected(true);
                nextLevel();
            }
        }
    }

    private void nextLevel() {
        level++;
        if (level > boardInfo[2]) {
            done();
        }
        resetBoard();
        getBoardInfo();
        createBoard(boardInfo[0]);
        createSolution(boardInfo[1]);
        if (skip.isSelected()) {
            skip.setSelected(false);
        }
    }

    public void tileTapped() {
        int row = (int) (tap.y - boardOffset) / tileSize;
        int col = (int) (tap.x / tileSize);
        if (!tiles[row][col].isSelected()) {
            selected.add(tiles[row][col]);
            tiles[row][col].setSelected(true);
            if (solution.contains(tiles[row][col], true)) {

                stats.incrementCorrect();
                correct.add(tiles[row][col]);
                int incScore = (int) (5 * scoreTimer);
                score.increment(incScore);

                if (correct.size == solution.size) {
                    stats.incrementCompleted();
                    nextLevel();
                }
            } else {
                stats.incrementIncorrect();
                score.increment(-10);
            }
        }
    }

    private void done() {
        stats.setGameScore(score.getScore());
        gsm.set(new TransitionState(gsm, this, new StatsScreen(gsm, stats, new Classic(gsm, difficulty)), TransitionState.TransitionStyle.FADE));
    }

    public void updateTiles(float dt) {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                tiles[row][col].update(dt);
            }
        }
    }

    public void update(float dt) {
        handleInput();
        updateTiles(dt);
        score.update(dt);
        stats.incTime(dt);
        if (isShowing) {
            solutionTimer += dt;
            if (solutionTimer > 2) {
                for (int i = 0; i < solution.size; i++) {
                    solution.get(i).setSelected(false);
                }
                isShowing = false;
            }
        } else if (scoreTimer > 0) {
            scoreTimer -= dt;
            if (scoreTimer < 0) scoreTimer = 0;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                tiles[row][col].render(sb);
            }
        }
        sb.setColor(247f / 255, 200f / 255, 212f / 255, 1);
        skip.render(sb);
        sb.setColor(1, 1, 1, 1);
        skip.drawText(sb, "skip", 32);
        score.render(sb);
        sb.end();
    }

    private void getBoardInfo() {
        if (difficulty == Difficulty.EASY) {
            boardInfo[0] = 3;
            boardInfo[1] = 3;
            boardInfo[2] = 3;
            completeBonus = 10;
        } else if (difficulty == Difficulty.NORMAL) {
            boardInfo[2] = 5;
            completeBonus = 50;
            if (level == boardInfo[2]) {
                boardInfo[0] = 7;
            } else boardInfo[0] = 5;
            switch (level) {
                case 1:
                    boardInfo[1] = 3;
                    break;
                case 2:
                    boardInfo[1] = 4;
                    break;
                case 3:
                    boardInfo[1] = 5;
                    break;
                case 4:
                    boardInfo[1] = 6;
                    break;
                case 5:
                    boardInfo[1] = 8;
                    break;
            }
        } else if (difficulty == Difficulty.HARD) {
            boardInfo[2] = 10;
            completeBonus = 100;
            if (level == boardInfo[2]) {
                boardInfo[0] = 9;
            } else boardInfo[0] = 7;
            switch (level) {
                case 1:
                case 2:
                    boardInfo[1] = 4;
                    break;
                case 3:
                case 4:
                    boardInfo[1] = 5;
                    break;
                case 5:
                case 6:
                    boardInfo[1] = 6;
                    break;
                case 7:
                case 8:
                    boardInfo[1] = 7;
                    break;
                case 9:
                    boardInfo[1] = 8;
                    break;
                case 10:
                    boardInfo[1] = 10;
            }
        }
    }

    private void setTileTimer() {

        //if size is odd
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
                tiles[row][col].setTimer(myFunc(i));
                if ((j + 1) < (2 * i) + 1) col++;
            }
            //Right column (bottom -> top)
            row++;
            for (j = 0; j < (2 * i); j++) {
                tiles[row][col].setTimer(myFunc(i));
                if (j < (2 * i) - 1) row++;
            }
            //Top Row (left <- right)
            col--;
            for (j = 0; j < (2 * i); j++) {
                tiles[row][col].setTimer(myFunc(i));
                if (j < (2 * i) - 1) col--;
            }
            //Left column (top -> bottom)
            row--;
            for (j = 0; j < (2 * i); j++) {
                tiles[row][col].setTimer(myFunc(i));
                if (j < (2 * i) - 1) row--;
            }
        }
    }

    private float myFunc(int i) {
        return (float) -0.15 * i;
    }
}
