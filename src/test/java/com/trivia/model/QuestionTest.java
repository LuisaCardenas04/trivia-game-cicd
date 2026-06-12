package com.trivia.model;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    private Question makeQuestion() {
        return new Question(
            "¿Cuál es la capital de Francia?",
            List.of("Londres", "Madrid", "París", "Roma"),
            2, "Geografía", Question.Difficulty.EASY
        );
    }

    @Test void correctAnswerReturnsTrue() {
        assertTrue(makeQuestion().isCorrect(2));
    }

    @Test void wrongAnswerReturnsFalse() {
        assertFalse(makeQuestion().isCorrect(0));
    }

    @Test void getCorrectAnswerReturnsText() {
        assertEquals("París", makeQuestion().getCorrectAnswer());
    }

    @Test void easyQuestionGives10Points() {
        assertEquals(10, makeQuestion().getPoints());
    }

    @Test void mediumQuestionGives20Points() {
        Question q = new Question("?", List.of("A","B"), 0, "Cat", Question.Difficulty.MEDIUM);
        assertEquals(20, q.getPoints());
    }

    @Test void hardQuestionGives30Points() {
        Question q = new Question("?", List.of("A","B"), 0, "Cat", Question.Difficulty.HARD);
        assertEquals(30, q.getPoints());
    }

    @Test void constructorThrowsOnInvalidIndex() {
        assertThrows(IllegalArgumentException.class, () ->
            new Question("?", List.of("A","B"), 5, "Cat", Question.Difficulty.EASY));
    }

    @Test void constructorThrowsOnTooFewOptions() {
        assertThrows(IllegalArgumentException.class, () ->
            new Question("?", List.of("A"), 0, "Cat", Question.Difficulty.EASY));
    }

    @Test void constructorThrowsOnNullOptions() {
        assertThrows(IllegalArgumentException.class, () ->
            new Question("?", null, 0, "Cat", Question.Difficulty.EASY));
    }

    @Test void toStringContainsCategoryAndDifficulty() {
        String s = makeQuestion().toString();
        assertTrue(s.contains("Geografía"));
        assertTrue(s.contains("EASY"));
    }
}