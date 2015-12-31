package com.daose.board.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.daose.board.Board;
import com.daose.board.ui.Button;

/**
 * Created by student on 27/12/15.
 */
public class MenuState extends State {

    public enum GameMode {
        CLASSIC
    }

    private Button classicButton, highScoreButton;
    private Button[] buttons;
    private String title;

    public MenuState(GSM gsm){
        super(gsm);

        //Buttons span the bottom half of the screen
        buttons = createButtons(2);
        highScoreButton = buttons[0];
        classicButton = buttons[1];

        title = "Tappy Tiles";

    }

    private Button[] createButtons(int num) {
        Button[] buttons = new Button[num];
        for (int i = 0; i < buttons.length; i++) {
            float yPos = Board.gameHeight / 2 / buttons.length / 2 + (i * Board.gameHeight / 2 / buttons.length);
            buttons[i] = new Button(Board.gameWidth / 2, yPos, (3 * Board.gameWidth / 4), Board.gameHeight / 8);
        }

        return buttons;
    }

    public void handleInput() {
        if (Gdx.input.isTouched()) {
            tap.x = Gdx.input.getX();
            tap.y = Gdx.input.getY();
            cam.unproject(tap);
            if(classicButton.contains(tap.x, tap.y)){
                classicButton.setSelected(true);
            } else if (highScoreButton.contains(tap.x, tap.y)) {
                highScoreButton.setSelected(true);
            }
        } else if (classicButton.isSelected()) {
            gsm.set(new TransitionState(gsm, this, new DifficultyState(gsm, GameMode.CLASSIC), TransitionState.TransitionStyle.FADE));
        } else if (highScoreButton.isSelected()) {
            gsm.set(new TransitionState(gsm, this, new ScoreState(gsm), TransitionState.TransitionStyle.FADE));
        }
    }

    public void dispose() {

    }

    public void update(float dt) {
        handleInput();
    }

    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        classicButton.render(sb);
        Board.font128.draw(sb, title, 50, 7 * Board.gameHeight / 8, Board.gameWidth - 50, Align.center, true);
        classicButton.drawText(sb, "CLASSIC", 64);
        sb.setColor(0.34f, 0.25f, 0.22f, 0.5f);
        highScoreButton.render(sb);
        sb.setColor(1, 1, 1, 1);
        highScoreButton.drawText(sb, "HIGHSCORE", 32);
        sb.end();
    }
}
