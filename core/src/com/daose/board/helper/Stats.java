package com.daose.board.helper;

import com.badlogic.gdx.math.MathUtils;
import com.daose.board.states.game.Classic;

/**
 * Created by student on 28/12/15.
 */
public class Stats {

    private int correct;
    private int incorrect;
    private int accuracy;
    private int gameScore;
    private Classic.Difficulty difficulty;
    private float time;

    private boolean bonus;

    public Stats() {
        correct = 0;
        incorrect = 0;
        gameScore = 0;
        time = 0;
        bonus = true;
    }

    public void incTime(float dt) {
        time += dt;
    }

    public float getTotalTime() {
        return time;
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

    public void setBonus(boolean b) {
        bonus = b;
    }

    public boolean getBonusStatus() {
        return bonus;
    }

    public void incrementCorrect() {
        correct++;
    }

    public void incrementIncorrect() {
        incorrect++;
    }

    public int getAccuracy() {
        if (correct + incorrect == 0) return 0;
        System.out.println("Correct: " + correct + ", Incorrect: " + incorrect);
        int total = correct + incorrect;
        float calcPercent;
        calcPercent = (float) correct / total;
        calcPercent *= 100;
        System.out.println("Calc Percent: " + calcPercent);
        accuracy = MathUtils.round(calcPercent);
        System.out.println(accuracy);
        return accuracy;
    }

}
