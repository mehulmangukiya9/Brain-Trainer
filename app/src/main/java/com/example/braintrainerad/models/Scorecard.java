package com.example.braintrainerad.models;

public class Scorecard
{
    private String name;
    private int score;

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

    public Scorecard(String name, int score) {
        this.name = name;
        this.score = score;
    }
}
