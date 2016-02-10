package com.daose.board.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.daose.board.Board;
import com.daose.board.states.game.Play;
import com.daose.board.ui.Button;

/**
 * Created by student on 27/12/15.
 */
public class MenuState extends State {

    public enum GameMode {
        CLASSIC
    }

    private Button playButton, classicButton, highScoreButton;
    private Button[] buttons;

    private GlyphLayout title;

    public MenuState(GSM gsm){
        super(gsm);

        //Buttons span the bottom half of the screen
        buttons = createButtons(3);
        highScoreButton = buttons[0];
        classicButton = buttons[1];
        playButton = buttons[2];

        playButton.setText("PLAY", Color.DARK_GRAY, 64);
        classicButton.setText("classic", Color.DARK_GRAY, 64);
        highScoreButton.setText("HIGHSCORES", Color.DARK_GRAY, 32);

        title = new GlyphLayout(Board.font128, "Tappy Tiles", Color.DARK_GRAY, Board.gameWidth, Align.center, true);

    }

    private Button[] createButtons(int num) {
        Button[] buttons = new Button[num];
        float initPos = (Board.gameHeight / 2 / (buttons.length + 1) / 2);
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(Board.gameWidth / 2, initPos + (i * 2 * initPos), (2 * Board.gameWidth / 3), Board.gameHeight / 8);
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
            } else if (playButton.contains(tap.x, tap.y)) {
                playButton.setSelected(true);
            }
        } else if (classicButton.isSelected()) {
            gsm.set(new TransitionState(gsm, this, new DifficultyState(gsm, GameMode.CLASSIC), TransitionState.TransitionStyle.FADE));
        } else if (highScoreButton.isSelected()) {
            gsm.set(new TransitionState(gsm, this, new ScoreState(gsm), TransitionState.TransitionStyle.FADE));
        } else if (playButton.isSelected()) {
            gsm.set(new TransitionState(gsm, this, new Play(gsm), TransitionState.TransitionStyle.FADE));
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
        Board.font128.draw(sb, title, 0, 7 * Board.gameHeight / 8);
        classicButton.drawText(sb);
        sb.setColor(0.34f, 0.25f, 0.22f, 0.5f);
        highScoreButton.render(sb);
        sb.setColor(1, 1, 1, 1);
        highScoreButton.drawText(sb);

        playButton.render(sb);
        playButton.drawText(sb);

        sb.end();
    }
}
