package com.daose.board.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
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
    private int gradingScheme, inc;
    private float percentage;
    private int accuracy;
    private char grade;
    private State prev;
    private int totalTime;

    private Tile timeLine, accuracyLine, bonusLine;

    private float timer;
    private boolean showInfo, showBonus, showGrade, showLine, toIncrement, bonus;

    private Button retry, menu;

    private GlyphLayout timeElapsed, accuracyText, bonusText, bonusDesc;

    public StatsScreen(GSM gsm, Stats stat, State prev) {
        super(gsm);
        this.stats = stat;
        showInfo = showBonus = showGrade = bonus = false;
        toIncrement = true;
        finalScore = new Score(Board.WIDTH / 2, Board.HEIGHT / 2 - 50);
        finalScore.setScore(stats.getGameScore());
        this.prev = prev;

        switch (stat.getDifficulty()) {
            case EASY:
                gradingScheme = 285;
                inc = 50;
                break;
            case NORMAL:
                gradingScheme = 700;
                inc = 100;
                break;
            case HARD:
                gradingScheme = 2000;
                inc = 200;
                break;
        }
        accuracy = stats.getAccuracy();
        totalTime = MathUtils.round(stats.getTotalTime());
        bonus = stats.getBonusStatus();

        retry = new Button(Board.WIDTH / 4, 200, 200, 100);
        menu = new Button(Board.WIDTH / 4, 95, 200, 100);

        timeElapsed = new GlyphLayout(Board.font32, "time elapsed: " + totalTime);
        timeLine = new Tile(Board.WIDTH / 2, Board.HEIGHT - 134, (int) (timeElapsed.width + 50), 10);

        accuracyText = new GlyphLayout(Board.font32, "accuracy: " + accuracy + "%");
        accuracyLine = new Tile(Board.WIDTH / 2, Board.HEIGHT - 209, (int) (accuracyText.width + 50), 10);

        bonusText = new GlyphLayout(Board.font32, "BONUS");
        bonusLine = new Tile(Board.WIDTH / 2, Board.HEIGHT - 284, (int) (bonusText.width + 50), 10);

        if (bonus) {
            bonusDesc = new GlyphLayout(Board.font32, "no skips!");
            percentage = (float) finalScore.getScore() + inc / gradingScheme;
        } else {
            bonusDesc = new GlyphLayout(Board.font32, "none");
            percentage = (float) finalScore.getScore() / gradingScheme;
        }

        grade = calculateGrade();

        timeLine.setSelected(true);
        accuracyLine.setTimer(-0.25f);
        accuracyLine.setSelected(true);
        bonusLine.setTimer(-0.5f);
        bonusLine.setSelected(true);

    }


    private char calculateGrade() {
        char str;

        percentage *= 100;
        if (percentage > 90) {
            str = 'A';
        } else if (percentage > 70) {
            str = 'B';
        } else if (percentage > 50) {
            str = 'C';
        } else str = 'F';
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
                prev.dispose();
                gsm.set(new TransitionState(gsm, this, new MenuState(gsm), TransitionState.TransitionStyle.FADE));
            }
        }
    }

    public void update(float dt) {
        if (timer < 4) {
            timer += dt;
        }
        if (timer > 0.5) {
            showLine = true;
            timeLine.update(dt);
            accuracyLine.update(dt);
            bonusLine.update(dt);
        }
        if (timer > 1.5)
            showInfo = true;
        if (timer > 2) {
            showBonus = true;
            finalScore.update(dt);
            if (toIncrement && bonus) {
                finalScore.increment(inc);
                toIncrement = false;
            }
        }
        if (timer > 3.5) {
            showGrade = true;
        }
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
        if (showGrade) {
            switch (grade) {
                case 'A':
                    sb.draw(Board.stampA, Board.WIDTH / 2, -50);
                    break;
                case 'B':
                    sb.draw(Board.stampB, Board.WIDTH / 2, -50);
                    break;
                case 'C':
                    sb.draw(Board.stampC, Board.WIDTH / 2, -50);
                    break;
                case 'F':
                    sb.draw(Board.stampF, Board.WIDTH / 2, -50);
            }
        }

        if (showLine) {
            timeLine.render(sb);
            accuracyLine.render(sb);
            bonusLine.render(sb);
        }

        if (showInfo) {
            Board.font32.draw(sb, timeElapsed, Board.WIDTH / 2 - timeElapsed.width / 2, Board.HEIGHT - 100);
            Board.font32.draw(sb, accuracyText, Board.WIDTH / 2 - accuracyText.width / 2, Board.HEIGHT - 175);
        }
        if (showBonus) {
            Board.font32.draw(sb, bonusText, Board.WIDTH / 2 - bonusText.width / 2, Board.HEIGHT - 250);
            Board.font32.draw(sb, bonusDesc, Board.WIDTH / 2 - bonusDesc.width / 2, Board.HEIGHT - 290);
        }

        sb.end();
    }

    public void dispose() {
    }
}
