package com.trivia.service;

import com.trivia.data.QuestionBank;
import com.trivia.model.GameSession;
import com.trivia.model.Player;
import com.trivia.model.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class GameService {

    private static final int DEFAULT_QUESTIONS = 5;

    public GameSession createSession(String playerName) {
        return createSession(playerName, DEFAULT_QUESTIONS);
    }

    public GameSession createSession(String playerName, int numQuestions) {
        Player player = new Player(playerName);
        List<Question> pool = new ArrayList<>(QuestionBank.getAllQuestions());
        Collections.shuffle(pool);
        List<Question> selected = pool.subList(0, Math.min(numQuestions, pool.size()));
        String sessionId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return new GameSession(sessionId, player, selected);
    }

    public GameSession createSessionByCategory(String playerName, String category) {
        Player player = new Player(playerName);
        List<Question> pool = new ArrayList<>(QuestionBank.getByCategory(category));
        if (pool.isEmpty()) {
            throw new IllegalArgumentException("No hay preguntas para la categoría: " + category);
        }
        Collections.shuffle(pool);
        String sessionId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return new GameSession(sessionId, player, pool);
    }

    public ScoreRecord processResult(GameSession session) {
        if (!session.isFinished()) {
            throw new IllegalStateException("La sesión no ha finalizado");
        }
        Player p = session.getPlayer();
        return new ScoreRecord(
            session.getSessionId(),
            p.getName(),
            p.getScore(),
            p.getCorrectAnswers(),
            p.getTotalAnswers(),
            p.getAccuracy(),
            p.getRank()
        );
    }

    public record ScoreRecord(
        String sessionId,
        String playerName,
        int score,
        int correct,
        int total,
        double accuracy,
        String rank
    ) {
        public boolean isPassing() { return accuracy >= 50.0; }

        @Override public String toString() {
            return String.format(
                "ScoreRecord{player='%s', score=%d, accuracy=%.1f%%, rank='%s', passing=%b}",
                playerName, score, accuracy, rank, isPassing()
            );
        }
    }
}