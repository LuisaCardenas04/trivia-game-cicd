package com.trivia.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScoreService {

    private final List<GameService.ScoreRecord> leaderboard = new ArrayList<>();

    public void addScore(GameService.ScoreRecord record) {
        leaderboard.add(record);
    }

    public List<GameService.ScoreRecord> getTopScores(int n) {
        return leaderboard.stream()
            .sorted(Comparator.comparingInt(GameService.ScoreRecord::score).reversed())
            .limit(n)
            .toList();
    }

    public int getTotalGamesPlayed() {
        return leaderboard.size();
    }

    public double getAverageScore() {
        if (leaderboard.isEmpty()) return 0.0;
        return leaderboard.stream()
            .mapToInt(GameService.ScoreRecord::score)
            .average()
            .orElse(0.0);
    }

    public GameService.ScoreRecord getHighScore() {
        return leaderboard.stream()
            .max(Comparator.comparingInt(GameService.ScoreRecord::score))
            .orElse(null);
    }

    public void clearLeaderboard() {
        leaderboard.clear();
    }
}