package com.daose.board.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.daose.board.Board;

/**
 * Created by student on 27/12/15.
 */
public class Button extends Tile {

    private GlyphLayout text;
    private int size;

    //x, y is middle
    public Button(float x, float y, float width, float height) {
        super(x, y, width, height);

        text = new GlyphLayout();
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

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setText(String string, Color color, int size) {
        this.size = size;
        if (size == 128) {
            text.setText(Board.font128, string, color, width, Align.center, true);
        }
        if (size == 64) {
            text.setText(Board.font64, string, color, width, Align.center, true);
        }
        if (size == 32) {
            text.setText(Board.font32, string, color, width, Align.center, true);
        }
    }

    public void drawText(SpriteBatch sb) {
        if (size == 128) {
            Board.font128.draw(sb, text, x - width / 2, y + Board.font128.getCapHeight() / 2);
        }
        if (size == 64) {
            Board.font64.draw(sb, text, x - width / 2, y + Board.font64.getCapHeight() / 2);
        }
        if (size == 32) {
            Board.font32.draw(sb, text, x - width / 2, y + Board.font32.getCapHeight() / 2);
        }
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
