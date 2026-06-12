package com.trivia.data;

import com.trivia.model.Question;
import com.trivia.model.Question.Difficulty;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {

    public static List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();

        // TECNOLOGÍA
        questions.add(new Question(
            "¿Qué significa el acrónimo CPU?",
            List.of("Central Processing Unit", "Computer Personal Unit", "Control Program Utility", "Core Processing Utility"),
            0, "Tecnología", Difficulty.EASY
        ));
        questions.add(new Question(
            "¿En qué año fue fundada la empresa Apple Inc.?",
            List.of("1972", "1976", "1980", "1984"),
            1, "Tecnología", Difficulty.MEDIUM
        ));
        questions.add(new Question(
            "¿Qué lenguaje de programación creó Guido van Rossum?",
            List.of("Java", "Ruby", "Python", "Perl"),
            2, "Tecnología", Difficulty.EASY
        ));
        questions.add(new Question(
            "¿Cuál es la complejidad temporal del algoritmo QuickSort en el peor caso?",
            List.of("O(n log n)", "O(n)", "O(n²)", "O(log n)"),
            2, "Tecnología", Difficulty.HARD
        ));
        questions.add(new Question(
            "¿Qué protocolo se usa para transferir páginas web de forma segura?",
            List.of("FTP", "HTTP", "HTTPS", "SMTP"),
            2, "Tecnología", Difficulty.EASY
        ));

        // CIENCIA
        questions.add(new Question(
            "¿Cuál es el símbolo químico del oro?",
            List.of("Go", "Or", "Au", "Ag"),
            2, "Ciencia", Difficulty.EASY
        ));
        questions.add(new Question(
            "¿A qué velocidad viaja la luz en el vacío?",
            List.of("150,000 km/s", "300,000 km/s", "500,000 km/s", "1,000,000 km/s"),
            1, "Ciencia", Difficulty.MEDIUM
        ));
        questions.add(new Question(
            "¿Cuántos huesos tiene el cuerpo humano adulto?",
            List.of("186", "206", "226", "246"),
            1, "Ciencia", Difficulty.MEDIUM
        ));
        questions.add(new Question(
            "¿Qué científico formuló la teoría de la relatividad?",
            List.of("Isaac Newton", "Nikola Tesla", "Albert Einstein", "Stephen Hawking"),
            2, "Ciencia", Difficulty.EASY
        ));
        questions.add(new Question(
            "¿Cuál es el número atómico del carbono?",
            List.of("4", "6", "8", "12"),
            1, "Ciencia", Difficulty.MEDIUM
        ));

        // GEOGRAFÍA
        questions.add(new Question(
            "¿Cuál es el país más grande del mundo por superficie?",
            List.of("China", "Estados Unidos", "Rusia", "Canadá"),
            2, "Geografía", Difficulty.EASY
        ));
        questions.add(new Question(
            "¿Cuál es la capital de Australia?",
            List.of("Sídney", "Melbourne", "Brisbane", "Canberra"),
            3, "Geografía", Difficulty.MEDIUM
        ));
        questions.add(new Question(
            "¿En qué continente se encuentra el río Nilo?",
            List.of("Asia", "América del Sur", "África", "Europa"),
            2, "Geografía", Difficulty.EASY
        ));
        questions.add(new Question(
            "¿Cuál es el océano más grande del planeta?",
            List.of("Atlántico", "Índico", "Ártico", "Pacífico"),
            3, "Geografía", Difficulty.EASY
        ));
        questions.add(new Question(
            "¿Qué país tiene forma de bota?",
            List.of("España", "Italia", "Grecia", "Portugal"),
            1, "Geografía", Difficulty.EASY
        ));

        // HISTORIA
        questions.add(new Question(
            "¿En qué año llegó Cristóbal Colón a América?",
            List.of("1488", "1492", "1498", "1502"),
            1, "Historia", Difficulty.EASY
        ));
        questions.add(new Question(
            "¿Cuánto duró la Primera Guerra Mundial?",
            List.of("2 años", "4 años", "6 años", "8 años"),
            1, "Historia", Difficulty.MEDIUM
        ));
        questions.add(new Question(
            "¿Quién fue el primer presidente de los Estados Unidos?",
            List.of("Abraham Lincoln", "Thomas Jefferson", "George Washington", "John Adams"),
            2, "Historia", Difficulty.EASY
        ));
        questions.add(new Question(
            "¿En qué año cayó el Muro de Berlín?",
            List.of("1985", "1987", "1989", "1991"),
            2, "Historia", Difficulty.MEDIUM
        ));
        questions.add(new Question(
            "¿Qué civilización construyó Machu Picchu?",
            List.of("Maya", "Azteca", "Inca", "Olmeca"),
            2, "Historia", Difficulty.MEDIUM
        ));

        return questions;
    }

    public static List<Question> getByCategory(String category) {
        return getAllQuestions().stream()
            .filter(q -> q.getCategory().equalsIgnoreCase(category))
            .toList();
    }

    public static List<Question> getByDifficulty(Difficulty difficulty) {
        return getAllQuestions().stream()
            .filter(q -> q.getDifficulty() == difficulty)
            .toList();
    }

    public static List<String> getCategories() {
        return getAllQuestions().stream()
            .map(Question::getCategory)
            .distinct()
            .sorted()
            .toList();
    }
}