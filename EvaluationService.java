package service;

import java.util.*;

public class EvaluationService {
    public int calculateScore(ArrayList<Character> correctAnswers, ArrayList<Character> userAnswers) {
        int score = 0;
        for (int i = 0; i < correctAnswers.size(); i++) {
            if (correctAnswers.get(i) == userAnswers.get(i)) {
                score++;
            }
        }
        return score;
    }
    public double calculatePercentage(int score, int totalQuestions) {
        return (score * 100.0) / totalQuestions;
    }
    public String getResultStatus(double percentage) {
        if (percentage >= 40) {
            return "PASS";
        } else {
            return "FAIL";
        }
    }
}