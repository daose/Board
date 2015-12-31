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

    private Button[] buttons;
    private Button easy, casual, hard;

    public DifficultyState(GSM gsm, MenuState.GameMode mode) {
        super(gsm);
        this.mode = mode;

        buttons = createButtons(3);
        hard = buttons[0];
        casual = buttons[1];
        easy = buttons[2];

    }

    private Button[] createButtons(int num) {
        Button[] buttons = new Button[num];
        for (int i = 0; i < buttons.length; i++) {
            float yPos = Board.gameHeight / buttons.length / 2 + (i * Board.gameHeight / buttons.length);
            buttons[i] = new Button(Board.gameWidth / 2, yPos, (3 * Board.gameWidth / 4), Board.gameHeight / 8);
        }
        return buttons;
    }

    public void dispose() {
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            tap.x = Gdx.input.getX();
            tap.y = Gdx.input.getY();
            cam.unproject(tap);
            for (int i = 0; i < buttons.length; i++) {
                if (buttons[i].contains(tap.x, tap.y)) {
                    switch (i) {
                        case 2:
                            if (mode == MenuState.GameMode.CLASSIC) {
                                easy.setSelected(true);
                                gsm.set(new TransitionState(gsm, this, new Classic(gsm, Classic.Difficulty.EASY), TransitionState.TransitionStyle.FADE));
                            }
                            break;
                        case 1:
                            if (mode == MenuState.GameMode.CLASSIC) {
                                casual.setSelected(true);
                                gsm.set(new TransitionState(gsm, this, new Classic(gsm, Classic.Difficulty.NORMAL), TransitionState.TransitionStyle.FADE));
                            }
                            break;
                        case 0:
                            if (mode == MenuState.GameMode.CLASSIC) {
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

        sb.setColor(183f / 255, 50f / 255, 50f / 255, 0.75f);
        hard.render(sb);
        sb.setColor(1, 1, 1, 1);

        easy.drawText(sb, "easy", 64);
        casual.drawText(sb, "casual", 64);
        hard.drawText(sb, "hard", 64);

        sb.end();
    }

}
