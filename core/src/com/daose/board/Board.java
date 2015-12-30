package com.daose.board;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.daose.board.states.GSM;
import com.daose.board.states.MenuState;

public class Board extends ApplicationAdapter {

    public static final String TITLE = "Tappy Tiles";
    public static final int WIDTH = 480;
    public static final int HEIGHT = 800;

    private SpriteBatch sb;
    private GSM gsm;

    private Texture texture;
    private Texture score;
    private Texture stamp;

    public static TextureRegion[] regions;
    public static TextureRegion[] numbers;
    private static TextureRegion[] grades;
    public static TextureRegion stampA, stampB, stampC, stampF;

    public static BitmapFont font32, font64, font128;

    public static Sound tapped;

	public void create () {
        Gdx.gl.glClearColor(241f / 255, 242f / 255, 240f / 255, 1);

        sb = new SpriteBatch();
        initText();
        initImages();

        tapped = Gdx.audio.newSound(Gdx.files.internal("tapped.wav"));

        gsm = new GSM();
        gsm.push(new MenuState(gsm));
    }

    private void initImages() {
        texture = new Texture(Gdx.files.internal("blocks.png"));
        score = new Texture(Gdx.files.internal("score.png"));
        stamp = new Texture(Gdx.files.internal("stamps.png"));
        grades = new TextureRegion[4];
        for (int i = 0; i < grades.length; i++) {
            grades[i] = new TextureRegion(stamp, 0 + 300 * i, 0, 300, 300);
        }
        stampA = grades[0];
        stampB = grades[1];
        stampC = grades[2];
        stampF = grades[3];

        regions = new TextureRegion[2];
        regions[0] = new TextureRegion(texture, 0, 0, 2, 2);
        regions[1] = new TextureRegion(texture, 2, 0, 2, 2);

        numbers = new TextureRegion[10];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = new TextureRegion(score, 40 * i, 0, 40, 53);
        }


    }

    private void initText() {
        font128 = new BitmapFont(Gdx.files.internal("ubuntu128.fnt"));
        font128.setColor(Color.DARK_GRAY);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Ubuntu-B.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 64;
        parameter.color = Color.DARK_GRAY;
        parameter.shadowColor = new Color(0, 0, 0, 0.25f);
        parameter.shadowOffsetX = 1;
        parameter.shadowOffsetY = 1;

        font64 = generator.generateFont(parameter);

        parameter.size = 32;

        font32 = generator.generateFont(parameter);
        generator.dispose();
    }


	public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(sb);
	}
}
