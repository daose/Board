package com.daose.board.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.daose.board.Board;
import com.daose.board.helper.Stats;
import com.daose.board.ui.Score;
import com.daose.board.ui.TextField;

/**
 * Created by student on 28/12/15.
 */
public class StatsScreen extends State {

    private Stats stats;
    private Score finalScore;
    private TextField gradeText, accuracyText, gradeTitle;
    private int gradingScheme;
    private float percentage;
    private String grade;

    public StatsScreen(GSM gsm, Stats stat) {
        super(gsm);
        this.stats = stat;

        finalScore = new Score(Board.WIDTH / 2, Board.HEIGHT / 2 - 50);
        finalScore.setScore(stats.getGameScore());

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
        gradeTitle = new TextField("grade", 80, Board.WIDTH / 2 + 10, Board.HEIGHT / 2 + 200, true);
        gradeText = new TextField(grade, 126, Board.WIDTH / 2 - 30, Board.HEIGHT / 2 + 50, true);

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
            gsm.set(new TransitionState(gsm, this, new MenuState(gsm), TransitionState.TransitionStyle.FADE));
        }
    }

    public void update(float dt) {
        handleInput();
    }

    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        finalScore.render(sb);
        gradeText.render(sb);
        gradeTitle.render(sb);
        sb.end();
    }

    public void dispose() {
        gradeText.dispose();
        gradeTitle.dispose();
    }
}
