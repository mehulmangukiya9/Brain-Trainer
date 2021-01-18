package com.example.braintrainerad.models;

import com.google.gson.annotations.SerializedName;

public class RetrofitScorecard
{
    @SerializedName("name")
    private String name;

    @SerializedName("score")
    private int score;

    @SerializedName("gameTime")
    private int gameTime;

    public RetrofitScorecard() {
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

    public RetrofitScorecard(String name, int score, int time) {
        this.name = name;
        this.score = score;
        this.gameTime = time;
    }
}
