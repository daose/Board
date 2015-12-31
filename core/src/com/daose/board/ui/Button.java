package com.daose.board.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.daose.board.Board;

/**
 * Created by student on 27/12/15.
 */
public class Button extends Tile {

    public Button(float x, float y, float width, float height) {
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

    public Button[] createButtons(int num, float x, float y) {
        Button[] buttons = new Button[num];
        for (int i = 0; i < buttons.length; i++) {
            float yPos = y / 2 / buttons.length / 2 + (i * y / 2 / buttons.length);
            buttons[i] = new Button(x / 2, yPos, (3 / 4) * x, 100);
        }
        return buttons;
    }
}
