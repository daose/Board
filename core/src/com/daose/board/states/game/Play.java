package com.daose.board.states.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.daose.board.Board;
import com.daose.board.states.EndGameState;
import com.daose.board.states.GSM;
import com.daose.board.states.State;
import com.daose.board.states.TransitionState;
import com.daose.board.ui.Grid;
import com.daose.board.ui.Score;

/**
 * Created by student on 01/01/16.
 */
public class Play extends State {

    private Grid grid;
    private int[] info;

    private int boardCompleted;
    private float scoreYPos;

    private Score score;

    private Color tint;
    private int colour;

    private float solutionTimer;
    private boolean showSolution;

    public Play(GSM gsm) {
        super(gsm);

        tint = new Color(1, 1, 1, 1);
        info = new int[3];

        colour = 1;
        solutionTimer = 1;
        showSolution = false;

        playInfo();

        grid = new Grid();
        grid.create(info[0], info[1], info[2], Grid.TileAnimation.CIRCLE_IN);
        boardCompleted = 0;

        score = new Score();
        scoreYPos = Board.gameHeight - ((Board.gameHeight - grid.getHeight()) / 2) + score.getLayout().height / 2;
        score.setYPos(scoreYPos);
    }

    private void playInfo() {

        if (boardCompleted % 5 == 1 && boardCompleted > 1) {
            info[0] -= 2;
            info[1] -= 4;
            info[2] -= 3;
            return;
        }

        if (boardCompleted % 5 == 0 && boardCompleted > 0) {
            info[0] += 2;
            info[1] += 4;
            info[2] += 3;
            colourWheel();
            return;
        }

        info[0] = 5;
        info[1] = 6;
        info[2] = 2;
    }

    private void colourWheel() {
        switch (colour % 5) {
            case 0:
                tint.set(1, 1, 1, 1);
                break;
            case 1:
                tint.set(197f / 255, 225f / 255, 165f / 255, 1);
                break;
            case 2:
                tint.set(192f / 255, 164f / 255, 224f / 255, 1);
                break;
            case 3:
                tint.set(255f / 255, 224f / 255, 130f / 255, 1);
                break;
            case 4:
                tint.set(178f / 255, 223f / 255, 219f / 255, 1);
                break;
        }
        colour++;
    }

    public void handleInput() {
        if (Gdx.input.justTouched() && !grid.isShowing() && !showSolution) {
            tap.x = Gdx.input.getX();
            tap.y = Gdx.input.getY();
            cam.unproject(tap);
            if (grid.contains(tap.x, tap.y)) {
                grid.tileTapped(tap.x, tap.y);
                if (grid.getSelectedSize() > grid.getCorrectSize()) {
                    showSolution = true;
                }
                if (grid.getCorrectSize() == grid.getSolutionSize()) {
                    boardCompleted++;
                    score.increment(1);
                    grid.reset();
                    playInfo();
                    grid.create(info[0], info[1], info[2], Grid.TileAnimation.CIRCLE_IN);
                }
            }
        }
    }

    public void update(float dt) {
        handleInput();
        grid.update(dt);
        if (showSolution) {
            grid.showSolution(true);
            if (solutionTimer > 0) {
                solutionTimer -= dt;
            } else {
                showSolution = false;
                gsm.set(new TransitionState(gsm, this, new EndGameState(gsm, this, score), TransitionState.TransitionStyle.FADE));
            }
        }
    }

    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.setColor(tint);
        grid.render(sb);
        sb.setColor(1, 1, 1, 1);
        score.render(sb);
        sb.end();

    }

    public void dispose() {

    }
}
