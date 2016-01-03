package com.daose.board.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.daose.board.Board;
import com.daose.board.ui.Button;

/**
 * Created by student on 29/12/15.
 */
public class ScoreState extends State {

    private Button back;
    private GlyphLayout[] text;

    private int easy, casual, hard;

    public ScoreState(GSM gsm) {
        super(gsm);

        back = new Button(Board.gameWidth / 2, Board.gameHeight / 10, 3 * Board.gameWidth / 4, Board.gameHeight / 8);
        back.setText("back", Color.DARK_GRAY, 32);

        easy = Board.highScore.getInteger("classicEasy");
        casual = Board.highScore.getInteger("classicCasual");
        hard = Board.highScore.getInteger("classicHard");

        text = new GlyphLayout[4];
        text[0] = new GlyphLayout(Board.font64, "HIGH SCORES", Color.DARK_GRAY, Board.gameWidth, Align.center, true);
        text[1] = new GlyphLayout(Board.font32, "Easy: " + easy, Color.DARK_GRAY, Board.gameWidth, Align.center, true);
        text[2] = new GlyphLayout(Board.font32, "Casual: " + casual, Color.DARK_GRAY, Board.gameWidth, Align.center, true);
        text[3] = new GlyphLayout(Board.font32, "Hard: " + hard, Color.DARK_GRAY, Board.gameWidth, Align.center, true);

    }

    public void handleInput() {
        if (Gdx.input.isTouched()) {
            tap.x = Gdx.input.getX();
            tap.y = Gdx.input.getY();
            cam.unproject(tap);
            if (back.contains(tap.x, tap.y)) {
                back.setSelected(true);
            }
        } else if (back.isSelected()) {
            gsm.set(new TransitionState(gsm, this, new MenuState(gsm), TransitionState.TransitionStyle.FADE));
        }
    }

    public void update(float dt) {
        handleInput();
    }

    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        back.render(sb);
        back.drawText(sb);
        drawGlyph(sb, Board.gameHeight);
        sb.end();
    }

    private void drawGlyph(SpriteBatch sb, float y) {
        Board.font64.draw(sb, text[0], 0, y - Board.gameHeight / 10);
        Board.font32.draw(sb, text[1], 0, y - (Board.gameHeight / 4) - (Board.gameHeight / 10));
        Board.font32.draw(sb, text[2], 0, y - (Board.gameHeight / 4) - 2 * (Board.gameHeight / 10));
        Board.font32.draw(sb, text[3], 0, y - (Board.gameHeight / 4) - 3 * (Board.gameHeight / 10));
    }

    public void dispose() {
    }
}
