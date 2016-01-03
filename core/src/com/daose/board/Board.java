package com.daose.board;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.math.MathUtils;
import com.daose.board.states.GSM;
import com.daose.board.states.MenuState;

public class Board extends ApplicationAdapter {

    public static final String TITLE = "Tappy Tiles";
    public static int gameWidth = 480;
    public static int gameHeight = 800;

    //Things to load
    public static TextureRegion[] tileTexture;
    public static TextureRegion[] numbers;
    private static TextureRegion[] grades;
    public static TextureRegion stampA, stampB, stampC, stampF;
    public static BitmapFont font32, font64, font128;
    public static Sound tapped;
    public static Preferences highScore;

    private AssetManager am;
    private SpriteBatch sb;
    private GSM gsm;
    private OrthographicCamera cam;
    private boolean loaded = false;

	public void create () {
        gameWidth = Gdx.graphics.getWidth();
        gameHeight = Gdx.graphics.getHeight();
        Gdx.gl.glClearColor(241f / 255, 242f / 255, 240f / 255, 1);
        sb = new SpriteBatch();
        cam = new OrthographicCamera();
        cam.setToOrtho(false, gameWidth, gameHeight);

        load();
    }

    private void load() {
        initPrefs();

        am = new AssetManager();

        //Textures
        am.load("splashscreen.png", Texture.class);
        am.load("blocks.png", Texture.class);
        am.load("score.png", Texture.class);
        am.load("stamps.png", Texture.class);

        //Sound and Music
        am.load("tapped.wav", Sound.class);

        //Font
        float small, medium, large;
        small = (float) gameHeight / 25;
        medium = (float) gameHeight / 10;
        large = (float) gameHeight / 6;

        FileHandleResolver resolver = new InternalFileHandleResolver();
        am.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        am.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        //Large Font
        FreetypeFontLoader.FreeTypeFontLoaderParameter largeParam = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        largeParam.fontFileName = "Ubuntu-B.ttf";
        largeParam.fontParameters.color = Color.WHITE;
        largeParam.fontParameters.size = MathUtils.ceil(large);
        am.load("largeFont.ttf", BitmapFont.class, largeParam);

        FreetypeFontLoader.FreeTypeFontLoaderParameter medParam = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        medParam.fontFileName = "Ubuntu-B.ttf";
        medParam.fontParameters.color = Color.WHITE;
        medParam.fontParameters.size = MathUtils.ceil(medium);
        am.load("medFont.ttf", BitmapFont.class, medParam);

        FreetypeFontLoader.FreeTypeFontLoaderParameter smallParam = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        smallParam.fontFileName = "Ubuntu-B.ttf";
        smallParam.fontParameters.color = Color.WHITE;
        smallParam.fontParameters.size = MathUtils.ceil(small);
        am.load("smallFont.ttf", BitmapFont.class, smallParam);

    }

    private void finishLoading() {
        font128 = am.get("largeFont.ttf", BitmapFont.class);
        font64 = am.get("medFont.ttf", BitmapFont.class);
        font32 = am.get("smallFont.ttf", BitmapFont.class);

        tapped = am.get("tapped.wav", Sound.class);

        grades = new TextureRegion[4];
        for (int i = 0; i < grades.length; i++) {
            grades[i] = new TextureRegion(am.get("stamps.png", Texture.class), 300 * i, 0, 300, 300);
        }
        stampA = grades[0];
        stampB = grades[1];
        stampC = grades[2];
        stampF = grades[3];

        //Light and Dark Tiles
        tileTexture = new TextureRegion[2];
        tileTexture[0] = new TextureRegion(am.get("blocks.png", Texture.class), 0, 0, 2, 2);
        tileTexture[1] = new TextureRegion(am.get("blocks.png", Texture.class), 2, 0, 2, 2);

        //Score numbers
        numbers = new TextureRegion[10];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = new TextureRegion(am.get("score.png", Texture.class), 40 * i, 0, 40, 53);
        }

        gsm = new GSM();
        gsm.push(new MenuState(gsm));

        loaded = true;
    }

    private void initPrefs() {
        highScore = Gdx.app.getPreferences("TappyTile");
        if (!highScore.contains("classicEasy")) {
            highScore.putInteger("classicEasy", 0);
        }
        if (!highScore.contains("classicCasual")) {
            highScore.putInteger("classicCasual", 0);
        }
        if (!highScore.contains("classicHard")) {
            highScore.putInteger("classicHard", 0);
        }
    }

	public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        am.finishLoadingAsset("splashscreen.png");
        if (am.isLoaded("splashscreen.png")) {
            if (am.update()) {
                if (!loaded) {
                    finishLoading();
                } else {
                    gsm.update(Gdx.graphics.getDeltaTime());
                    gsm.render(sb);
                }
            } else {
                sb.setProjectionMatrix(cam.combined);
                sb.begin();
                sb.draw(am.get("splashscreen.png", Texture.class), 0, 0, gameWidth, gameHeight);
                sb.end();
            }
        }
    }

    @Override
    public void dispose() {
        sb.dispose();
        am.dispose();
    }
}
