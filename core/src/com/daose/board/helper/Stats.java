package com.daose.board.helper;

import com.badlogic.gdx.math.MathUtils;
import com.daose.board.states.game.Classic;

/**
 * Created by student on 28/12/15.
 */
public class Stats {

    private int correct;
    private int incorrect;
    private int completed;
    private int accuracy;
    private int gameScore;
    private Classic.Difficulty difficulty;

    public Stats() {
        correct = 0;
        incorrect = 0;
        completed = 0;
        gameScore = 0;
    }

    public void setDifficulty(Classic.Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Classic.Difficulty getDifficulty() {
        return difficulty;
    }

    public void setGameScore(int newScore) {
        gameScore = newScore;
    }

    public int getGameScore() {
        return gameScore;
    }

    public int getCompleted() {
        return completed;
    }

    public void incrementCorrect() {
        correct++;
    }

    public void incrementIncorrect() {
        incorrect++;
    }

    public int getAccuracy() {
        float calcPercent = (float) (correct / (correct + incorrect) * 100);
        accuracy = MathUtils.round(calcPercent);
        return accuracy;
    }

    public void incrementCompleted() {
        completed++;
    }
}
