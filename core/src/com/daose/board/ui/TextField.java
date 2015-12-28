package com.daose.board.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by student on 27/12/15.
 */
//Font: http://www.geocities.jp/kitschlabo/
public class TextField {

    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    BitmapFont titleFont;


    String text;
    private int x, y, size;

    public TextField(String text, int size, int x, int y, boolean shadow){
        generator = new FreeTypeFontGenerator(Gdx.files.internal("android/assets/baby blocks.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = Color.BLACK;
        if(shadow) {
            parameter.shadowColor = new Color(0, 0, 0, 0.25f);
            parameter.shadowOffsetX = 1;
            parameter.shadowOffsetY = 1;
        }
        titleFont = generator.generateFont(parameter);
        generator.dispose();

        this.x = x;
        this.y = y;
        this.text = text;
        this.size = size;
    }

    //draws from upper left corner
    public void render(SpriteBatch sb){
        titleFont.draw(sb, text, x - (text.length() * size/4), y + size);
    }
}
