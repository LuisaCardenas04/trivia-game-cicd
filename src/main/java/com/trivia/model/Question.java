package com.trivia.model;

import java.util.List;

public class Question {
    private final String text;
    private final List<String> options;
    private final int correctIndex;
    private final String category;
    private final Difficulty difficulty;

    public enum Difficulty { EASY, MEDIUM, HARD }

    public Question(String text, List<String> options, int correctIndex,
                    String category, Difficulty difficulty) {
        if (options == null || options.size() < 2) {
            throw new IllegalArgumentException("Una pregunta necesita al menos 2 opciones");
        }
        if (correctIndex < 0 || correctIndex >= options.size()) {
            throw new IllegalArgumentException("El índice de respuesta correcta es inválido");
        }
        this.text = text;
        this.options = List.copyOf(options);
        this.correctIndex = correctIndex;
        this.category = category;
        this.difficulty = difficulty;
    }

    public boolean isCorrect(int answerIndex) {
        return answerIndex == correctIndex;
    }

    public String getCorrectAnswer() {
        return options.get(correctIndex);
    }

    public String getText()           { return text; }
    public List<String> getOptions()  { return options; }
    public int getCorrectIndex()      { return correctIndex; }
    public String getCategory()       { return category; }
    public Difficulty getDifficulty() { return difficulty; }

    public int getPoints() {
        return switch (difficulty) {
            case EASY   -> 10;
            case MEDIUM -> 20;
            case HARD   -> 30;
        };
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s", category, difficulty, text);
    }
}