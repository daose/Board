package com.daose.board.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.daose.board.Board;
import com.daose.board.ui.Button;

/**
 * Created by student on 29/12/15.
 */
public class ScoreState extends State {

    private Button back;
    private GlyphLayout highScoreText, easyText, casualText, hardText;
    private GlyphLayout[] text;

    private int easy, casual, hard;

    public ScoreState(GSM gsm) {
        super(gsm);

        back = new Button(Board.gameWidth / 2, 100, 400, 100);

        easy = Board.highScore.getInteger("classicEasy");
        casual = Board.highScore.getInteger("classicCasual");
        hard = Board.highScore.getInteger("classicHard");

        text = new GlyphLayout[4];
        text[0] = highScoreText = new GlyphLayout(Board.font64, "HIGH SCORES");
        text[1] = easyText = new GlyphLayout(Board.font32, "Easy: " + easy);
        text[2] = casualText = new GlyphLayout(Board.font32, "Casual: " + casual);
        text[3] = hardText = new GlyphLayout(Board.font32, "Hard: " + hard);

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
        back.drawText(sb, "back", 64);
        drawGlyph(sb, Board.gameWidth / 2, Board.gameHeight - 100);
        sb.end();
    }

    private void drawGlyph(SpriteBatch sb, float x, float y) {
        for (int i = 0; i < text.length; i++) {
            if (i == 0)
                Board.font64.draw(sb, text[i], x - text[i].width / 2, y - (75 * i));
            else
                Board.font32.draw(sb, text[i], x - text[i].width / 2, y - 100 - (75 * i));
        }
    }

    public void dispose() {
    }
}
