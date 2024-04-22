package ru.dragomirov.models;

import java.util.HashMap;
import java.util.Map;

public class Score {
    private Map<Players, Integer> scores = new HashMap<>();

    public Score(Players player1, Players player2) {
        scores.put(player1, 0);
        scores.put(player2, 0);
    }

    public void incrementScore(Players player) {
        scores.put(player, getNextScore(scores.get(player)));
    }

    private int getNextScore(int currentScore) {
        switch (currentScore) {
            case 0:
                return 15;
            case 15:
                return 30;
            case 30:
                return 40;
            default:
                throw new IllegalArgumentException("Unexpected score: " + currentScore);
        }
    }

    public int getScore(Players player) {
        return scores.get(player);
    }
}
