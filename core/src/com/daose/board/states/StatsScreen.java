package com.daose.board.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.daose.board.Board;
import com.daose.board.helper.Stats;
import com.daose.board.ui.Button;
import com.daose.board.ui.Score;
import com.daose.board.ui.Tile;

/**
 * Created by student on 28/12/15.
 */
public class StatsScreen extends State {

    private Stats stats;
    private Score finalScore;
    private int gradingScheme;
    private float percentage;
    private float accuracy;
    private String grade;
    private State prev;
    private float totalTime;

    private Tile timeLine;

    //0.5 seconds each, acuuracy, time elapsed, score, bonus, stamp, (retry + main menu)
    private float timer;
    private boolean showAccuracy, showTime, showScore, showBonus;

    private Button retry, menu;

    public StatsScreen(GSM gsm, Stats stat, State prev) {
        super(gsm);
        this.stats = stat;
        showAccuracy = showTime = showScore = showBonus = false;
        finalScore = new Score(Board.WIDTH / 2, Board.HEIGHT / 2 - 50);
        finalScore.setScore(stats.getGameScore());
        this.prev = prev;

        switch (stat.getDifficulty()) {
            case EASY:
                gradingScheme = 180;
                break;
            case NORMAL:
                gradingScheme = 775;
                break;
            case HARD:
                gradingScheme = 2000;
                break;
        }
        grade = calculateGrade();
        accuracy = stats.getAccuracy();
        totalTime = stats.getTotalTime();

        retry = new Button(Board.WIDTH / 4, 200, 200, 100);
        menu = new Button(Board.WIDTH / 4, 95, 200, 100);

    }


    private String calculateGrade() {
        String str;
        percentage = (float) finalScore.getScore() / gradingScheme;
        percentage *= 100;
        if (percentage > 90) {
            str = "A";
        } else if (percentage > 80) {
            str = "B";
        } else if (percentage > 70) {
            str = "C";
        } else str = "D";
        return str;
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            tap.x = Gdx.input.getX();
            tap.y = Gdx.input.getY();
            cam.unproject(tap);
            if (retry.contains(tap.x, tap.y)) {
                retry.setSelected(true);
                gsm.set(new TransitionState(gsm, this, prev, TransitionState.TransitionStyle.FADE));
            } else if (menu.contains(tap.x, tap.y)) {
                gsm.set(new TransitionState(gsm, this, new MenuState(gsm), TransitionState.TransitionStyle.FADE));
            }
        }
    }

    public void update(float dt) {
        if (timer < 4) {
            timer += dt;
        }
        if (timer > 0.5)
            showTime = true;
        if (timer > 1)
            showAccuracy = true;
        if (timer > 1.5)
            showBonus = true;
        handleInput();
    }

    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        finalScore.render(sb);
        retry.render(sb);
        menu.render(sb);
        retry.drawText(sb, "retry?", 64);
        menu.drawText(sb, "menu", 64);

        if (showTime) {
            String timeElapsed = "Time Elapsed: " + totalTime;
            Board.font32.draw(sb, timeElapsed, 30, 750);
        }

        sb.end();
    }

    public void dispose() {
    }
}
