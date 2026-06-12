package com.trivia.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScoreServiceTest {

    private ScoreService scoreService;

    @BeforeEach void setUp() {
        scoreService = new ScoreService();
    }

    private GameService.ScoreRecord record(String name, int score, double acc) {
        return new GameService.ScoreRecord("ID1", name, score, 3, 5, acc, "Experto");
    }

    @Test void emptyLeaderboardReturnsZeroGames() {
        assertEquals(0, scoreService.getTotalGamesPlayed());
    }

    @Test void addScoreIncreasesCount() {
        scoreService.addScore(record("A", 50, 80));
        assertEquals(1, scoreService.getTotalGamesPlayed());
    }

    @Test void averageScoreCalculated() {
        scoreService.addScore(record("A", 40, 80));
        scoreService.addScore(record("B", 60, 90));
        assertEquals(50.0, scoreService.getAverageScore(), 0.01);
    }

    @Test void topScoresAreSortedDescending() {
        scoreService.addScore(record("A", 30, 60));
        scoreService.addScore(record("B", 90, 90));
        scoreService.addScore(record("C", 60, 75));
        var top = scoreService.getTopScores(3);
        assertEquals(90, top.get(0).score());
        assertEquals(60, top.get(1).score());
        assertEquals(30, top.get(2).score());
    }

    @Test void highScoreReturnsMax() {
        scoreService.addScore(record("A", 20, 50));
        scoreService.addScore(record("B", 80, 90));
        assertEquals(80, scoreService.getHighScore().score());
    }

    @Test void highScoreNullWhenEmpty() {
        assertNull(scoreService.getHighScore());
    }

    @Test void clearLeaderboardResetsCount() {
        scoreService.addScore(record("A", 50, 70));
        scoreService.clearLeaderboard();
        assertEquals(0, scoreService.getTotalGamesPlayed());
    }
}