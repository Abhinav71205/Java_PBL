package model;
public class Result {
    String username;
    int score;
    int totalQuestions;
    public Result(String u, int s, int t) {
        username = u;
        score = s;
        totalQuestions = t;
    }
    public void display() {
        System.out.println("\n----- RESULT -----");
        System.out.println("User: " + username);
        System.out.println("Score: " + score + "/" + totalQuestions);
    }
}