package com.metalsoft.droidpong;

/**
 * Created by Anthony Ratliff on 8/3/2017.
 */

public class Player {

    private String playerName;
    private int ballHits;
    private double gameRatio;

    public Player(String name, int hits, double ratio){
        if (name != null){
            this.playerName = name;
        } else {
            try {
                throw new Exception("Name cannot be null.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (hits >=0){
            this.ballHits = hits;
        } else {
            try {
                throw new Exception("Hits must be greater than 0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (ratio >=0){
            this.gameRatio = ratio;
        } else {
            try {
                throw new Exception("Ratio must be greater than 0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getBallHits() {
        return ballHits;
    }

    public double getGameRatio() {
        return gameRatio;
    }
}
