package com.daose.board.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.daose.board.Board;

/**
 * Created by student on 27/12/15.
 */
public class Tile {

    //Tile properties
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float currentWidth;
    protected float currentHeight;
    protected boolean selected;
    protected boolean showAnswer;

    private float timer;
    private float animationTime;

    //Tile textures
    protected TextureRegion light, dark;

    public Tile(float x, float y, float width, float height) {

        int border = Board.gameWidth / 60;
        this.x = x;
        this.y = y;
        this.currentWidth = 0;
        this.currentHeight = 0;
        this.width = width - border;
        this.height = height - border;
        timer = 0;
        selected = false;
        animationTime = 0.4f;

        dark = Board.tileTexture[0];
        light = Board.tileTexture[1];

    }

    public void update(float dt) {
        if (currentWidth < width && currentHeight < height) {
            timer += dt;
            if (timer > 0) {
                currentWidth = (timer / animationTime) * width;
                currentHeight = (timer / animationTime) * height;
            }
            if (currentWidth > width) {
                currentWidth = width;
            }
            if (currentHeight > height) {
                currentHeight = height;
            }
        }
    }

    //(x, y) is center
    public void render(SpriteBatch sb) {
        if (showAnswer) {
            sb.setColor(0, 1, 0, 1);
            sb.draw(dark, (x - currentWidth / 2), (y - currentHeight / 2), currentWidth, currentHeight);
            sb.setColor(1, 1, 1, 1);
        } else if (selected) {
            sb.draw(dark, (x - currentWidth / 2), (y - currentHeight / 2), currentWidth, currentHeight);
        } else {
            sb.draw(light, (x - currentWidth / 2), (y - currentHeight / 2), currentWidth, currentHeight);
        }
    }

    public void setShowAnswer(boolean b) {
        showAnswer = b;
    }

    public void setSelected(boolean b) {
        selected = b;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setTimer(float t) {
        timer = t;
    }
}
