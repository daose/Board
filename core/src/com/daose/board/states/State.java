package com.daose.board.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.daose.board.Board;

/**
 * Created by student on 27/12/15.
 */
public abstract class State {

    protected GSM gsm;
    protected OrthographicCamera cam;
    protected Vector3 tap;

    protected State(GSM gsm){
        this.gsm = gsm;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Board.WIDTH, Board.HEIGHT);
        tap = new Vector3();
    }

    public abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);

}
