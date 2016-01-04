package com.daose.board.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.daose.board.Board;

/**
 * Created by student on 27/12/15.
 */
public class Score {

    private int score, incremented;
    private float yPos;

    private GlyphLayout scoreLayout;
    private String scoreText;

    private float timer;
    private float animationTime;

    public Score() {
        scoreLayout = new GlyphLayout(Board.font64, "0", new Color(52f / 255, 152f / 255, 219f / 255, 1), Board.gameWidth, Align.center, true);
        score = 0;
        scoreText = "0";
        animationTime = 0.5f;
        timer = 1;
    }

    public GlyphLayout getLayout() {
        return scoreLayout;
    }

    public void setYPos(float yPos) {
        this.yPos = yPos;
    }

    public void increment(int inc) {
        this.incremented = inc;
        if ((score + inc) < 0) {
            score = 0;
        } else {
            setScore(score + inc);
        }
        timer = 0;
    }

    public void setScore(int newScore) {
        score = newScore;
        scoreText = Integer.toString(score);
        scoreLayout.setText(Board.font64, scoreText, Color.BLACK, Board.gameWidth, Align.center, true);
    }

    public int getScore() {
        return score;
    }

    public void update(float dt) {
        if (timer < animationTime) {
            timer += dt;
            if (timer > animationTime) timer = animationTime;
        }
    }

    public void render(SpriteBatch sb) {
        drawScore(sb);
        //drawIncremented(sb);
    }

    private void drawScore(SpriteBatch sb) {
        Board.font64.draw(sb, scoreLayout, 0, yPos);
    }

    private void drawIncremented(SpriteBatch sb) {
        if (incremented > 0) {
            sb.setColor(0, 1, 0, (1 - timer / animationTime));
            yPos += (timer * 50);
        } else {
            sb.setColor(1, 0, 0, (1 - timer / animationTime));
            yPos += (timer * 50);
        }
        String incrementedText = Integer.toString(Math.abs(incremented));
        /************
        for (int i = 0; i < incrementedText.length(); i++) {
            char c = incrementedText.charAt(i);
            c -= '0';
            int index = (int) c;
            sb.draw(Board.numbers[index],
                    (x + width / 2) + 10 + (20 * i), yPos, 20, 26);
        }
         **************/
        sb.setColor(1, 1, 1, 1);
    }
}
