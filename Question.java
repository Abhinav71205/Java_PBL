package model;
public class Question {
    public String question;
    public String op1, op2, op3, op4;
    public char correctAnswer;
    public Question(String q, String o1, String o2, String o3, String o4, char ans) {
        this.question = q;
        this.op1 = o1;
        this.op2 = o2;
        this.op3 = o3;
        this.op4 = o4;
        this.correctAnswer = ans;
    }
}