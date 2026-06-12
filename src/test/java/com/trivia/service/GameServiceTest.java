package com.trivia.service;

import com.trivia.model.GameSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private GameService service;

    @BeforeEach void setUp() {
        service = new GameService();
    }

    @Test void createSessionReturnsValidSession() {
        GameSession s = service.createSession("Carlos");
        assertNotNull(s);
        assertEquals("Carlos", s.getPlayer().getName());
        assertTrue(s.getTotalQuestions() > 0);
    }

    @Test void createSessionWithCustomQuestionCount() {
        GameSession s = service.createSession("Luis", 3);
        assertEquals(3, s.getTotalQuestions());
    }

    @Test void createSessionByCategoryWorks() {
        GameSession s = service.createSessionByCategory("María", "Ciencia");
        assertNotNull(s);
        assertTrue(s.getTotalQuestions() > 0);
    }

    @Test void createSessionByInvalidCategoryThrows() {
        assertThrows(IllegalArgumentException.class, () ->
            service.createSessionByCategory("x", "CategoriaInexistente"));
    }

    @Test void processResultThrowsIfNotFinished() {
        GameSession s = service.createSession("Pedro");
        assertThrows(IllegalStateException.class, () -> service.processResult(s));
    }

    @Test void processResultAfterFinish() {
        GameSession s = service.createSession("Elena", 1);
        s.answerCurrent(0);
        s.finish();
        GameService.ScoreRecord r = service.processResult(s);
        assertNotNull(r);
        assertEquals("Elena", r.playerName());
    }

    @Test void scoreRecordIsPassingAbove50Percent() {
        GameSession s = service.createSession("Juan", 2);
        while (s.hasMoreQuestions()) {
            int correct = s.getCurrentQuestion().getCorrectIndex();
            s.answerCurrent(correct);
        }
        s.finish();
        GameService.ScoreRecord r = service.processResult(s);
        assertTrue(r.isPassing());
    }
}