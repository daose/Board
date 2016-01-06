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
    private Color scoreColor;

    private float timer;
    private float animationTime;

    public Score() {
        scoreColor = new Color(105f / 255, 204f / 255, 237f / 255, 1);
        scoreLayout = new GlyphLayout(Board.font64, "0", scoreColor, Board.gameWidth, Align.center, true);
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
        scoreLayout.setText(Board.font64, scoreText, scoreColor, Board.gameWidth, Align.center, true);
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

    }
}
