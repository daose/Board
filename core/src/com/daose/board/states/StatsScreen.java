package com.daose.board.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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
    private int inc, realScore;
    private float percentage, gradingScheme;
    private int accuracy;
    private char grade;
    private State prev;
    private float totalTime;

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
        finalScore = new Score();
        finalScore.setScore(stats.getGameScore());
        this.prev = prev;
        realScore = finalScore.getScore();

        switch (stat.getDifficulty()) {
            case EASY:
                gradingScheme = 250;
                inc = 50;
                break;
            case NORMAL:
                gradingScheme = 725;
                inc = 100;
                break;
            case HARD:
                gradingScheme = 1600;
                inc = 200;
                break;
        }
        accuracy = stats.getAccuracy();
        totalTime = stats.getTotalTime();
        bonus = stats.getBonusStatus();

        retry = new Button(Board.gameWidth / 2, Board.gameHeight / 5, Board.gameWidth / 2, Board.gameHeight / 8);
        menu = new Button(Board.gameWidth / 2, Board.gameHeight / 10, Board.gameWidth / 2, Board.gameHeight / 8);
        retry.setText("retry", Color.DARK_GRAY, 64);
        menu.setText("menu", Color.DARK_GRAY, 64);

        timeElapsed = new GlyphLayout(Board.font32, "time elapsed: " + totalTime);
        timeLine = new Tile(Board.gameWidth / 2, Board.gameHeight - 134, (int) (timeElapsed.width + 50), 10);

        accuracyText = new GlyphLayout(Board.font32, "accuracy: " + accuracy + "%");
        accuracyLine = new Tile(Board.gameWidth / 2, Board.gameHeight - 209, (int) (accuracyText.width + 50), 10);

        bonusText = new GlyphLayout(Board.font32, "BONUS");
        bonusLine = new Tile(Board.gameWidth / 2, Board.gameHeight - 284, (int) (bonusText.width + 50), 10);

        if (bonus) {
            bonusDesc = new GlyphLayout(Board.font32, "no skips!");
            percentage = ((finalScore.getScore() + inc) / gradingScheme);
            realScore += inc;
        } else {
            bonusDesc = new GlyphLayout(Board.font32, "none");
            percentage = (finalScore.getScore() / gradingScheme);
        }


        grade = calculateGrade();
        saveScore();

        timeLine.setSelected(true);
        accuracyLine.setTimer(-0.25f);
        accuracyLine.setSelected(true);
        bonusLine.setTimer(-0.5f);
        bonusLine.setSelected(true);

    }

    private void saveScore() {
        switch (stats.getDifficulty()) {
            case EASY:
                if (Board.highScore.getInteger("classicEasy") < realScore)
                    Board.highScore.putInteger("classicEasy", realScore);
                break;
            case NORMAL:
                if (Board.highScore.getInteger("classicCasual") < realScore)
                    Board.highScore.putInteger("classicCasual", realScore);
                break;
            case HARD:
                if (Board.highScore.getInteger("classicHard") < realScore)
                    Board.highScore.putInteger("classicHard", realScore);
                break;
        }
        Board.highScore.flush();
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
        retry.drawText(sb);
        menu.drawText(sb);


        if (showLine) {
            timeLine.render(sb);
            accuracyLine.render(sb);
            bonusLine.render(sb);
        }

        if (showInfo) {
            Board.font32.draw(sb, timeElapsed, Board.gameWidth / 2 - timeElapsed.width / 2, Board.gameHeight - 100);
            Board.font32.draw(sb, accuracyText, Board.gameWidth / 2 - accuracyText.width / 2, Board.gameHeight - 175);
        }
        if (showBonus) {
            Board.font32.draw(sb, bonusText, Board.gameWidth / 2 - bonusText.width / 2, Board.gameHeight - 250);
            Board.font32.draw(sb, bonusDesc, Board.gameWidth / 2 - bonusDesc.width / 2, Board.gameHeight - 290);
        }

        if (showGrade) {
            switch (grade) {
                case 'A':
                    sb.draw(Board.stampA, Board.gameWidth / 2 - Board.gameWidth / 3, Board.gameHeight / 2, 2 * Board.gameWidth / 3, 2 * Board.gameWidth / 3);
                    break;
                case 'B':
                    sb.draw(Board.stampB, Board.gameWidth / 2, -50);
                    break;
                case 'C':
                    sb.draw(Board.stampC, Board.gameWidth / 2, -50);
                    break;
                case 'F':
                    sb.draw(Board.stampF, Board.gameWidth / 2, -50);
            }
        }

        sb.end();
    }

    public void dispose() {
    }
}
