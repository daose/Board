package com.daose.board.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.daose.board.Board;
import com.daose.board.states.game.Classic;
import com.daose.board.ui.Button;
import com.daose.board.ui.TextField;

/**
 * Created by student on 28/12/15.
 */
public class DifficultyState extends State {

    private MenuState.GameMode mode;

    private Button[] difficulty;
    private TextField easyText, casualText, hardText;
    private Array<TextField> text;

    public DifficultyState(GSM gsm, MenuState.GameMode mode) {
        super(gsm);
        this.mode = mode;

        difficulty = new Button[3];
        difficulty[0] = new Button(Board.WIDTH / 2, Board.HEIGHT / 2 + 150, 300, 100);
        difficulty[1] = new Button(Board.WIDTH / 2, Board.HEIGHT / 2, 300, 100);
        difficulty[2] = new Button(Board.WIDTH / 2, Board.HEIGHT / 2 - 150, 300, 100);

        easyText = new TextField("easy", 64, Board.WIDTH / 2 + 10, Board.HEIGHT / 2 + 140, true);
        casualText = new TextField("casual", 64, Board.WIDTH / 2 + 10, Board.HEIGHT / 2 - 10, true);
        hardText = new TextField("hard", 64, Board.WIDTH / 2 + 10, Board.HEIGHT / 2 - 160, true);
        text = new Array<TextField>();
        text.add(easyText);
        text.add(casualText);
        text.add(hardText);

    }

    public void dispose() {
        for (int i = 0; i < text.size; i++) {
            text.get(i).dispose();
        }
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
                                gsm.set(new TransitionState(gsm, this, new Classic(gsm, Classic.Difficulty.EASY), TransitionState.TransitionStyle.FADE));
                            }
                            break;
                        case 1:
                            if (mode.toString().equals("CLASSIC")) {
                                gsm.set(new TransitionState(gsm, this, new Classic(gsm, Classic.Difficulty.NORMAL), TransitionState.TransitionStyle.FADE));
                            }
                            break;
                        case 2:
                            if (mode.toString().equals("CLASSIC")) {
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
        difficulty[0].render(sb);
        sb.setColor(1, 1, 1, 1);

        difficulty[1].render(sb);

        sb.setColor(183f / 255, 28f / 255, 28f / 255, 0.75f);
        difficulty[2].render(sb);
        sb.setColor(1, 1, 1, 1);

        easyText.render(sb);
        casualText.render(sb);
        hardText.render(sb);
        sb.end();
    }
}
