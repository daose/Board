package com.daose.board.states.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.daose.board.Board;
import com.daose.board.states.GSM;
import com.daose.board.states.MenuState;
import com.daose.board.states.State;
import com.daose.board.ui.Grid;

/**
 * Created by student on 01/01/16.
 */
public class Play extends State {

    private Grid grid;
    private int[] info;

    private int boardCompleted;
    private float scoreYPos;

    private GlyphLayout score;
    private Color scoreColor;

    private Color tint;
    private int colour;

    public Play(GSM gsm) {
        super(gsm);

        tint = new Color(52f / 255, 152f / 255, 219f / 255, 1);
        scoreColor = new Color(52f / 255, 152f / 255, 219f / 255, 1);
        playInfo();

        grid = new Grid();
        grid.create(3, 3, 2, Grid.TileAnimation.CIRCLE_IN);
        boardCompleted = 0;
        info = new int[3];


        score = new GlyphLayout(Board.font64, "0", scoreColor, Board.gameWidth, Align.center, true);
        scoreYPos = Board.gameHeight - ((Board.gameHeight - grid.getHeight()) / 2) + score.height / 2;
    }

    private void playInfo() {
        info = new int[3];

        colourWheel();
        Gdx.app.log("Board completed", Integer.toString(boardCompleted % 5));
    }

    private void colourWheel() {
        switch (colour % 5) {
            case 0:
                tint.set(scoreColor);
                break;
            case 1:
                tint.set(231f / 255, 76f / 255, 60f / 255, 1);
                break;
            case 2:
                tint.set(115f / 255, 89f / 255, 182f / 255, 1);
                break;
            case 3:
                tint.set(26f / 255, 188f / 255, 156f / 255, 1);
                break;
            case 4:
                tint.set(46f / 255, 204f / 255, 113f / 255, 1);
                break;
        }
        colour++;
    }

    public void handleInput() {
        if (Gdx.input.justTouched() && !grid.isShowing()) {
            tap.x = Gdx.input.getX();
            tap.y = Gdx.input.getY();
            cam.unproject(tap);
            if (grid.contains(tap.x, tap.y)) {
                grid.tileTapped(tap.x, tap.y);
                if (grid.getSelectedSize() > grid.getCorrectSize()) {
                    gsm.set(new MenuState(gsm));
                }
                if (grid.getCorrectSize() == grid.getSolutionSize()) {
                    boardCompleted++;
                    score.setText(Board.font64, Integer.toString(boardCompleted), scoreColor, Board.gameWidth, Align.center, true);
                    grid.reset();
                    playInfo();
                    grid.create(3, 3, 2, Grid.TileAnimation.CIRCLE_IN);

                }
            }
        }
    }

    public void update(float dt) {
        handleInput();
        grid.update(dt);
    }

    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.setColor(tint);
        grid.render(sb);
        sb.setColor(1, 1, 1, 1);
        Board.font64.draw(sb, score, 0, scoreYPos);
        sb.end();

    }

    public void dispose() {

    }
}
