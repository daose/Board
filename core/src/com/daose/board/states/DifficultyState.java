package com.daose.board.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.daose.board.Board;
import com.daose.board.states.game.Classic;
import com.daose.board.ui.Button;

/**
 * Created by student on 28/12/15.
 */
public class DifficultyState extends State {

    private MenuState.GameMode mode;

    private Button[] difficulty;
    private Button easy, casual, hard;

    public DifficultyState(GSM gsm, MenuState.GameMode mode) {
        super(gsm);
        this.mode = mode;

        difficulty = new Button[3];
        easy = difficulty[0] = new Button(Board.WIDTH / 2, Board.HEIGHT / 2 + 150, 300, 100);
        casual = difficulty[1] = new Button(Board.WIDTH / 2, Board.HEIGHT / 2, 300, 100);
        hard = difficulty[2] = new Button(Board.WIDTH / 2, Board.HEIGHT / 2 - 150, 300, 100);

    }

    public void dispose() {
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            tap.x = Gdx.input.getX();
            tap.y = Gdx.input.getY();
            cam.unproject(tap);
            for (int i = 0; i < difficulty.length; i++) {
                if (difficulty[i].contains(tap.x, tap.y)) {
                    switch (i) {
                        case 0:
                            if (mode.toString().equals("CLASSIC")) {
                                easy.setSelected(true);
                                gsm.set(new TransitionState(gsm, this, new Classic(gsm, Classic.Difficulty.EASY), TransitionState.TransitionStyle.FADE));
                            }
                            break;
                        case 1:
                            if (mode.toString().equals("CLASSIC")) {
                                casual.setSelected(true);
                                gsm.set(new TransitionState(gsm, this, new Classic(gsm, Classic.Difficulty.NORMAL), TransitionState.TransitionStyle.FADE));
                            }
                            break;
                        case 2:
                            if (mode.toString().equals("CLASSIC")) {
                                hard.setSelected(true);
                                gsm.set(new TransitionState(gsm, this, new Classic(gsm, Classic.Difficulty.HARD), TransitionState.TransitionStyle.FADE));
                            }
                            break;
                    }
                }
            }
        }
    }

    public void update(float dt) {
        handleInput();
    }

    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.setColor(67f / 255, 160f / 255, 71f / 255, 0.75f);
        easy.render(sb);
        sb.setColor(1, 1, 1, 1);

        casual.render(sb);

        sb.setColor(183f / 255, 28f / 255, 28f / 255, 0.75f);
        hard.render(sb);
        sb.setColor(1, 1, 1, 1);

        easy.drawText(sb, "easy", 64);
        casual.drawText(sb, "casual", 64);
        hard.drawText(sb, "hard", 64);

        sb.end();
    }

}
