package com.daose.board.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.daose.board.Board;

/**
 * Created by student on 27/12/15.
 */
public class Button extends Tile {

    public Button(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void render(SpriteBatch sb) {
        if (selected) {
            sb.draw(dark, (x - width / 2), (y - height / 2), width, height);
        } else {
            sb.draw(light, (x - width / 2), (y - height / 2), width, height);
        }
    }

    public boolean contains(float tapX, float tapY) {
        return ((tapX > (x - width / 2)) && (tapX < (x + width / 2)) && (tapY > (y - height / 2)) && (tapY < (y + height / 2)));
    }

    public float getX(){
        return x;
    }

    public void drawText(SpriteBatch sb, String text, int size) {
        if (size == 64) {
            Board.font64.draw(sb, text, x - width / 2, y + 25, width, Align.center, true);
        }
        if (size == 32) {
            Board.font32.draw(sb, text, x - width / 2, y + 10, width, Align.center, true);
        }
    }

    public float getY(){
        return y;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }
}
