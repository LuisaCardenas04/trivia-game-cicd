package com.trivia.util;

import com.trivia.model.Question;
import com.trivia.model.GameSession;

import java.util.List;

public class ConsoleUI {

    private static final String RESET  = "\u001B[0m";
    private static final String GREEN  = "\u001B[32m";
    private static final String RED    = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN   = "\u001B[36m";
    private static final String BOLD   = "\u001B[1m";

    public static void printBanner() {
        System.out.println(CYAN + BOLD);
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║       🎯 TRIVIA GAME  v1.0           ║");
        System.out.println("║   CI/CD con Jenkins + SonarQube      ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println(RESET);
    }

    public static void printQuestion(GameSession session) {
        Question q = session.getCurrentQuestion();
        System.out.printf("%n%s[Pregunta %d/%d | %s | %s | %d pts]%s%n",
            BOLD,
            session.getCurrentQuestionNumber(),
            session.getTotalQuestions(),
            q.getCategory(),
            q.getDifficulty(),
            q.getPoints(),
            RESET
        );
        System.out.println(YELLOW + q.getText() + RESET);
        List<String> options = q.getOptions();
        for (int i = 0; i < options.size(); i++) {
            System.out.printf("  %d) %s%n", i + 1, options.get(i));
        }
    }

    public static void printCorrect(Question q) {
        System.out.println(GREEN + "✔ ¡Correcto! +" + q.getPoints() + " puntos" + RESET);
    }

    public static void printWrong(Question q) {
        System.out.println(RED + "✘ Incorrecto. La respuesta era: " + q.getCorrectAnswer() + RESET);
    }

    public static void printSummary(GameSession session) {
        System.out.println("\n" + CYAN + session.getSummary() + RESET);
    }
}