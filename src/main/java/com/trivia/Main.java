package com.trivia;

import com.trivia.data.QuestionBank;
import com.trivia.model.GameSession;
import com.trivia.model.Question;
import com.trivia.service.GameService;
import com.trivia.service.ScoreService;
import com.trivia.util.ConsoleUI;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameService gameService = new GameService();
        ScoreService scoreService = new ScoreService();

        ConsoleUI.printBanner();

        System.out.println("Categorías disponibles: " + QuestionBank.getCategories());
        System.out.print("\nIngresa tu nombre: ");
        String name = scanner.nextLine().trim();
        if (name.isBlank()) name = "Jugador";

        System.out.println("\n¿Modo de juego?");
        System.out.println("  1) Todas las categorías (5 preguntas aleatorias)");
        System.out.println("  2) Elegir categoría");
        System.out.print("Opción: ");
        String modeInput = scanner.nextLine().trim();

        GameSession session;
        if ("2".equals(modeInput)) {
            System.out.print("Categoría: ");
            String category = scanner.nextLine().trim();
            try {
                session = gameService.createSessionByCategory(name, category);
            } catch (IllegalArgumentException e) {
                System.out.println("Categoría no encontrada. Iniciando modo aleatorio.");
                session = gameService.createSession(name);
            }
        } else {
            session = gameService.createSession(name);
        }

        System.out.printf("%nSesión %s iniciada. ¡Buena suerte, %s!%n",
            session.getSessionId(), name);

        while (session.hasMoreQuestions()) {
            ConsoleUI.printQuestion(session);
            System.out.print("Tu respuesta (número): ");
            String input = scanner.nextLine().trim();

            int choice;
            try {
                choice = Integer.parseInt(input) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, se contará como incorrecta.");
                choice = -1;
            }

            Question current = session.getCurrentQuestion();
            boolean correct = session.answerCurrent(choice);

            if (correct) {
                ConsoleUI.printCorrect(current);
            } else {
                ConsoleUI.printWrong(current);
            }
        }

        session.finish();
        ConsoleUI.printSummary(session);

        GameService.ScoreRecord record = gameService.processResult(session);
        scoreService.addScore(record);

        System.out.println("\n=== TOP SCORES ===");
        List<GameService.ScoreRecord> top = scoreService.getTopScores(5);
        for (int i = 0; i < top.size(); i++) {
            GameService.ScoreRecord r = top.get(i);
            System.out.printf("%d. %s - %d pts (%.0f%%) [%s]%n",
                i + 1, r.playerName(), r.score(), r.accuracy(), r.rank());
        }

        scanner.close();
    }
}