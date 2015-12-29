package com.daose.board.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.daose.board.Board;

/**
 * Created by student on 27/12/15.
 */
public class Score {

    private int x, y, score, width, height, incremented;
    private String scoreText;

    private float timer;
    private float animationTime;

    public Score(int x, int y) {
        this.x = x;
        this.y = y;
        score = 0;
        scoreText = "0";
        height = 53;
        width = 40;
        animationTime = 0.5f;
        timer = 1;
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
        width = 40 * scoreText.length();
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
        drawIncremented(sb);
    }

    private void drawScore(SpriteBatch sb) {
        for (int i = 0; i < scoreText.length(); i++) {
            char c = scoreText.charAt(i);
            c -= '0';
            int index = (int) c;
            sb.draw(Board.numbers[index], (x - width / 2) + (40 * i), y - height / 2);
        }
    }

    private void drawIncremented(SpriteBatch sb) {
        float yPos;
        if (incremented > 0) {
            sb.setColor(0, 1, 0, (1 - timer / animationTime));
            yPos = y + (timer * 50);
        } else {
            sb.setColor(1, 0, 0, (1 - timer / animationTime));
            yPos = y - (timer * 50);
        }
        String incrementedText = Integer.toString(Math.abs(incremented));
        for (int i = 0; i < incrementedText.length(); i++) {
            char c = incrementedText.charAt(i);
            c -= '0';
            int index = (int) c;
            sb.draw(Board.numbers[index],
                    (x + width / 2) + 10 + (20 * i), yPos, 20, 26);
        }
        sb.setColor(1, 1, 1, 1);
    }
}
