package com.daose.board.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.daose.board.Board;
import com.daose.board.ui.Score;

/**
 * Created by student on 04/01/16.
 */
public class EndGameState extends State {

    private State prev;
    private Score score;

    private char grade;

    public EndGameState(GSM gsm, State prev, Score score) {
        super(gsm);
        this.prev = prev;
        this.score = new Score();
        this.score.setScore(score.getScore());
        this.score.setYPos(Board.gameHeight / 4);

        if (score.getScore() > 30) {
            grade = 'A';
        } else if (score.getScore() > 25) {
            grade = 'B';
        } else if (score.getScore() > 10) {
            grade = 'C';
        } else {
            grade = 'F';
        }

    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            gsm.set(new TransitionState(gsm, this, new MenuState(gsm), TransitionState.TransitionStyle.FADE));
        }
    }

    public void update(float dt) {
        handleInput();
    }

    public void dispose() {

    }

    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        renderGrade(sb);
        score.render(sb);
        //renderGrade(sb);
        sb.end();
    }

    private void renderGrade(SpriteBatch sb) {
        switch (grade) {
            case 'A':
                sb.draw(Board.stampA, Board.gameWidth / 2 - Board.gameWidth / 3, Board.gameHeight / 2, 2 * Board.gameWidth / 2, 2 * Board.gameWidth / 3);
                break;
            case 'B':
                sb.draw(Board.stampB, Board.gameWidth / 2 - Board.gameWidth / 3, Board.gameHeight / 2, 2 * Board.gameWidth / 3, 2 * Board.gameWidth / 3);
                break;
            case 'C':
                sb.draw(Board.stampC, Board.gameWidth / 2 - Board.gameWidth / 3, Board.gameHeight / 2, 2 * Board.gameWidth / 3, 2 * Board.gameWidth / 3);
                break;
            case 'F':
                sb.draw(Board.stampF, Board.gameWidth / 2 - Board.gameWidth / 3, Board.gameHeight / 2, 2 * Board.gameWidth / 3, 2 * Board.gameWidth / 3);
                break;
        }
    }

}
