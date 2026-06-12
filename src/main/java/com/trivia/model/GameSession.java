package com.trivia.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GameSession {
    private final String sessionId;
    private final Player player;
    private final List<Question> questions;
    private final List<Boolean> answers;
    private final LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean finished;

    public GameSession(String sessionId, Player player, List<Question> questions) {
        this.sessionId = sessionId;
        this.player = player;
        this.questions = new ArrayList<>(questions);
        this.answers = new ArrayList<>();
        this.startTime = LocalDateTime.now();
        this.finished = false;
    }

    public boolean answerCurrent(int optionIndex) {
        if (finished) throw new IllegalStateException("La sesión ya ha finalizado");
        int current = answers.size();
        if (current >= questions.size()) throw new IllegalStateException("No hay más preguntas");

        Question q = questions.get(current);
        boolean correct = q.isCorrect(optionIndex);
        answers.add(correct);

        if (correct) {
            player.addPoints(q.getPoints());
        } else {
            player.addWrongAnswer();
        }
        return correct;
    }

    public void finish() {
        this.finished = true;
        this.endTime = LocalDateTime.now();
    }

    public boolean hasMoreQuestions() {
        return !finished && answers.size() < questions.size();
    }

    public Question getCurrentQuestion() {
        if (!hasMoreQuestions()) return null;
        return questions.get(answers.size());
    }

    public int getCurrentQuestionNumber() { return answers.size() + 1; }
    public int getTotalQuestions()        { return questions.size(); }
    public String getSessionId()          { return sessionId; }
    public Player getPlayer()             { return player; }
    public boolean isFinished()           { return finished; }

    public String getSummary() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format(
            "=== RESUMEN DE SESIÓN ===\n" +
            "ID: %s\n" +
            "Jugador: %s\n" +
            "Inicio: %s\n" +
            "Fin: %s\n" +
            "Preguntas: %d/%d correctas\n" +
            "Puntuación final: %d\n" +
            "Precisión: %.1f%%\n" +
            "Rango obtenido: %s",
            sessionId, player.getName(),
            startTime.format(fmt),
            endTime != null ? endTime.format(fmt) : "En curso",
            player.getCorrectAnswers(), questions.size(),
            player.getScore(),
            player.getAccuracy(),
            player.getRank()
        );
    }
}