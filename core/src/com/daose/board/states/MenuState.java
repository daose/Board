package com.daose.board.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.daose.board.Board;
import com.daose.board.states.game.Classic;
import com.daose.board.ui.Button;
import com.daose.board.ui.TextField;

/**
 * Created by student on 27/12/15.
 */
public class MenuState extends State {

    private TextField title, classic;
    private Button classicButton;

    public MenuState(GSM gsm){
        super(gsm);
        title = new TextField("Board", 80, Board.WIDTH / 2 - 10, Board.HEIGHT / 2 + 200, true);
        classic = new TextField("classic", 64, Board.WIDTH / 2 + 15, Board.HEIGHT / 2 - 10, true);
        classicButton = new Button(Board.WIDTH / 2, Board.HEIGHT / 2, 300, 100);
    }

    public void handleInput() {
        if(Gdx.input.justTouched()){
            tap.x = Gdx.input.getX();
            tap.y = Gdx.input.getY();
            cam.unproject(tap);
            if(classicButton.contains(tap.x, tap.y)){
                gsm.set(new Classic(gsm, Classic.Difficulty.NORMAL));
            }
        }
    }

    public void update(float dt) {
        handleInput();
    }

    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        title.render(sb);
        classicButton.render(sb);
        classic.render(sb);
        sb.end();
    }
}
