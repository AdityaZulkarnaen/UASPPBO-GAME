package core;

import java.io.Serializable;
public class HighScoreEntry implements Serializable, Comparable<HighScoreEntry> {
    private String playerName;
    private int score;

    public HighScoreEntry(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return playerName + ": " + score;
    }

    @Override
    public int compareTo(HighScoreEntry other) {
        return Integer.compare(other.score, this.score);
    }
}