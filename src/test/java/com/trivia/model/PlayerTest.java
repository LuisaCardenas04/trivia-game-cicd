package com.trivia.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    @BeforeEach void setUp() {
        player = new Player("Ana");
    }

    @Test void initialScoreIsZero() {
        assertEquals(0, player.getScore());
    }

    @Test void addPointsIncreasesScore() {
        player.addPoints(20);
        assertEquals(20, player.getScore());
    }

    @Test void addPointsNegativeThrows() {
        assertThrows(IllegalArgumentException.class, () -> player.addPoints(-5));
    }

    @Test void accuracyIsZeroWithNoAnswers() {
        assertEquals(0.0, player.getAccuracy());
    }

    @Test void accuracyCalculatedCorrectly() {
        player.addPoints(10);
        player.addWrongAnswer();
        assertEquals(50.0, player.getAccuracy(), 0.01);
    }

    @Test void rankNovato() {
        assertEquals("Novato", player.getRank());
    }

    @Test void rankAprendiz() {
        player.addPoints(30);
        assertEquals("Aprendiz", player.getRank());
    }

    @Test void rankExperto() {
        player.addPoints(60);
        assertEquals("Experto", player.getRank());
    }

    @Test void rankMaestro() {
        player.addPoints(100);
        assertEquals("Maestro Trivia", player.getRank());
    }

    @Test void emptyNameThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Player(""));
    }

    @Test void blankNameThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Player("   "));
    }

    @Test void toStringContainsName() {
        assertTrue(player.toString().contains("Ana"));
    }
}