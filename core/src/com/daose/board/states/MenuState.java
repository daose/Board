package com.daose.board.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.daose.board.Board;
import com.daose.board.ui.Button;
import com.daose.board.ui.TextField;

/**
 * Created by student on 27/12/15.
 */
public class MenuState extends State {

    public enum GameMode {
        CLASSIC
    }

    private Array<TextField> text;
    private Button classicButton;

    public MenuState(GSM gsm){
        super(gsm);
        classicButton = new Button(Board.WIDTH / 2, Board.HEIGHT / 2 - 100, 300, 100);
    }

    public void handleInput() {
        if (Gdx.input.isTouched()) {
            tap.x = Gdx.input.getX();
            tap.y = Gdx.input.getY();
            cam.unproject(tap);
            if(classicButton.contains(tap.x, tap.y)){
                classicButton.setSelected(true);
            }
        } else if (classicButton.isSelected()) {
            gsm.set(new TransitionState(gsm, this, new DifficultyState(gsm, GameMode.CLASSIC), TransitionState.TransitionStyle.FADE));
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
        Board.font128.draw(sb, "Tappy Tiles", 40, Board.HEIGHT / 2 + 250, Board.WIDTH - 50, Align.center, true);
        Board.font64.draw(
                sb, "Classic",
                classicButton.getX() - classicButton.getWidth() / 2,
                classicButton.getY() + 25,
                classicButton.getWidth(), Align.center, true);
        sb.end();
    }
}
