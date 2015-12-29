package com.daose.board;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.daose.board.states.GSM;
import com.daose.board.states.MenuState;

public class Board extends ApplicationAdapter {

    //Desktop config setup
    public static final String TITLE = "Board";
	public static final int WIDTH = 480;
    public static final int HEIGHT = 800;

    private SpriteBatch sb;
    private GSM gsm;

    private Texture texture;
    private Texture score;

    public static TextureRegion[] regions;
    public static TextureRegion[] numbers;

    public static BitmapFont font32, font64, font128;

	public void create () {
        Gdx.gl.glClearColor(241f / 255, 242f / 255, 240f / 255, 1);

        sb = new SpriteBatch();


        texture = new Texture(Gdx.files.internal("android/assets/blocks.png"));
        score = new Texture(Gdx.files.internal("android/assets/score.png"));

        font32 = new BitmapFont(Gdx.files.internal("android/assets/ubuntu32.fnt"));
        font64 = new BitmapFont(Gdx.files.internal("android/assets/ubuntu64.fnt"));
        font128 = new BitmapFont(Gdx.files.internal("android/assets/ubuntu128.fnt"));

        //ANDROID SETTINGS
        /**
        texture = new Texture(Gdx.files.internal("blocks.png"));
        score = new Texture(Gdx.files.internal("score.png"));

         font32 = new BitmapFont(Gdx.files.internal("ubuntu32.fnt"));
         font64 = new BitmapFont(Gdx.files.internal("ubuntu64.fnt"));
         font128 = new BitmapFont(Gdx.files.internal("ubuntu128.fnt"));
         **/

        font32.setColor(Color.DARK_GRAY);
        font64.setColor(Color.DARK_GRAY);
        font128.setColor(Color.DARK_GRAY);
        regions = new TextureRegion[2];
        regions[0] = new TextureRegion(texture, 0, 0, 2, 2);
        regions[1] = new TextureRegion(texture, 2, 0, 2, 2);

        numbers = new TextureRegion[10];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = new TextureRegion(score, 40 * i, 0, 40, 53);
        }


        gsm = new GSM();
        //gsm.push(new Classic(gsm, Classic.Difficulty.NORMAL));
        gsm.push(new MenuState(gsm));
    }


	public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(sb);
	}
}
