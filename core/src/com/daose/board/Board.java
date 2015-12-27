package com.daose.board;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.daose.board.states.GSM;
import com.daose.board.states.game.Classic;

public class Board extends ApplicationAdapter {

    //Desktop config setup
    public static final String TITLE = "Board";
	public static final int WIDTH = 480;
    public static final int HEIGHT = 800;

    private SpriteBatch sb;
    private GSM gsm;

    private Texture texture;
    public static TextureRegion[] regions;

	public void create () {
        Gdx.gl.glClearColor(241f / 255, 242f / 255, 240f / 255, 1);

        sb = new SpriteBatch();

        texture = new Texture(Gdx.files.internal("android/assets/blocks.png"));
        regions = new TextureRegion[2];
        regions[0] = new TextureRegion(texture, 0, 0, 2, 2);
        regions[1] = new TextureRegion(texture, 2, 0, 2, 2);

        gsm = new GSM();
        gsm.push(new Classic(gsm, Classic.Difficulty.HARD));
	}


	public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(sb);
	}
}
