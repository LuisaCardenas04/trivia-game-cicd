package com.trivia.model;

public class Player {
    private final String name;
    private int score;
    private int correctAnswers;
    private int totalAnswers;

    public Player(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre del jugador no puede estar vacío");
        }
        this.name = name;
        this.score = 0;
        this.correctAnswers = 0;
        this.totalAnswers = 0;
    }

    public void addPoints(int points) {
        if (points < 0) throw new IllegalArgumentException("Los puntos no pueden ser negativos");
        this.score += points;
        this.correctAnswers++;
        this.totalAnswers++;
    }

    public void addWrongAnswer() {
        this.totalAnswers++;
    }

    public double getAccuracy() {
        if (totalAnswers == 0) return 0.0;
        return (double) correctAnswers / totalAnswers * 100.0;
    }

    public String getRank() {
        if (score >= 100) return "Maestro Trivia";
        if (score >= 60)  return "Experto";
        if (score >= 30)  return "Aprendiz";
        return "Novato";
    }

    public String getName()        { return name; }
    public int getScore()          { return score; }
    public int getCorrectAnswers() { return correctAnswers; }
    public int getTotalAnswers()   { return totalAnswers; }

    @Override
    public String toString() {
        return String.format("Jugador[%s | Puntos: %d | Precisión: %.1f%% | Rango: %s]",
                name, score, getAccuracy(), getRank());
    }
}