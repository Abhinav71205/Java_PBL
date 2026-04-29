package model;

public class Analysis {
    String username;
    int score;
    int totalQuestions;
    double accuracy;

    public Analysis(String username, int score, int totalQuestions) {
        this.username = username;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.accuracy = (double) score / totalQuestions * 100;
    }

    public void display() {
        System.out.println("\n--- ANALYSIS REPORT ---");
        System.out.println("User: " + username);
        System.out.println("Score: " + score + "/" + totalQuestions);
        System.out.println("Accuracy: " + accuracy + "%");

        if (accuracy >= 80) {
            System.out.println("Performance: Excellent");
        } else if (accuracy >= 50) {
            System.out.println("Performance: Average");
        } else {
            System.out.println("Performance: Needs Improvement");
        }
    }
}