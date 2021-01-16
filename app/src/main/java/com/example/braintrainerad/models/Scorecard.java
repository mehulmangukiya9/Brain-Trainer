package com.example.braintrainerad.models;

public class Scorecard
{
    private String name;
    private int score;
    private int gameTime;

    public Scorecard() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getGameTime() {
        return gameTime;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    public Scorecard(String name, int score, int time) {
        this.name = name;
        this.score = score;
        this.gameTime = time;
    }
}
