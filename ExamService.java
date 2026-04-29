package service;

import java.io.*;
import java.util.*;
import model.Question;
import model.Analysis;

public class ExamService {

    public ArrayList<Question> questions = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public void loadQuestions() {
        questions.clear();
        try {
            BufferedReader br = new BufferedReader(new FileReader("questions.txt"));
            String line;

            while ((line = br.readLine()) != null) {

                String data[] = line.split(",");

                if (data.length < 6) continue;

                Question q = new Question(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        data[4],
                        data[5].charAt(0)
                );

                questions.add(q);
            }

            br.close();

        } catch (Exception e) {
            System.out.println("Error loading questions: " + e.getMessage());
        }
    }

    public void startExam(String username) {

        if (questions.size() == 0) {
            System.out.println("No questions available! Contact admin.");
            return;
        }

        int totalQuestions = Math.min(50, questions.size());

        ArrayList<Character> userAnswers = new ArrayList<>();
        ArrayList<Character> correctAnswers = new ArrayList<>();

        ProctorService proctor = new ProctorService();

        TimerThread timer = new TimerThread(10800, proctor, username);
        ActivityMonitorThread monitor = new ActivityMonitorThread(proctor, username);
        LoggerThread logger = new LoggerThread(proctor);

        timer.start();
        monitor.start();
        logger.start();

        System.out.println("\n===== EXAM STARTED =====");

        for (int i = 0; i < totalQuestions; i++) {

            if (!proctor.isExamActive()) break;

            Question q = questions.get(i);

            System.out.println("\nQ" + (i + 1) + ": " + q.question);
            System.out.println("A) " + q.op1);
            System.out.println("B) " + q.op2);
            System.out.println("C) " + q.op3);
            System.out.println("D) " + q.op4);

            long qStart = System.currentTimeMillis();

            char ans;

            while (true) {
                System.out.print("Your answer (A/B/C/D or X to exit): ");

                try {
                    ans = sc.next().toUpperCase().charAt(0);

                    if (ans == 'A' || ans == 'B' || ans == 'C' || ans == 'D' || ans == 'X') {
                        break;
                    } else {
                        System.out.println("Invalid input! Try again.");
                    }

                } catch (Exception e) {
                    System.out.println("Invalid input! Enter A/B/C/D/X.");
                    sc.next();
                }
            }

            long qEnd = System.currentTimeMillis();
            long answerTime = (qEnd - qStart) / 1000;

            monitor.updateActivity();

            if (answerTime < 2) {
                proctor.handleAudit(username, "Answered too quickly");
            }

            if (ans == 'X') {
                proctor.handleTabSwitch(username);
                break;
            }

            userAnswers.add(ans);
            correctAnswers.add(q.correctAnswer);
        }

        if (proctor.isExamActive()) {
            proctor.endExam("Exam Completed", username);
        }

        EvaluationService eval = new EvaluationService();

        int score = eval.calculateScore(correctAnswers, userAnswers);
        double percentage = eval.calculatePercentage(score, totalQuestions);
        String status = eval.getResultStatus(percentage);

        System.out.println("\n===== RESULT =====");
        System.out.println("Score: " + score + "/" + totalQuestions);
        System.out.println("Percentage: " + percentage + "%");
        System.out.println("Status: " + status);

        AnalysisService analysisService = new AnalysisService();

        Analysis report = analysisService.generateAnalysis(username, score, totalQuestions);
        report.display();

        analysisService.saveAnalysisToFile(username, score, totalQuestions);

        proctor.showLogs();
        proctor.saveLogsToFile(username);

        saveResult(username, score, totalQuestions);

        System.out.println("\n===== EXAM ENDED =====");
    }

    public void addQuestion() {
        try {
            FileWriter fw = new FileWriter("questions.txt", true);

            sc.nextLine();

            System.out.print("Enter Question: ");
            String q = sc.nextLine();

            System.out.print("Option A: ");
            String o1 = sc.nextLine();

            System.out.print("Option B: ");
            String o2 = sc.nextLine();

            System.out.print("Option C: ");
            String o3 = sc.nextLine();

            System.out.print("Option D: ");
            String o4 = sc.nextLine();

            char ans;

            while (true) {
                System.out.print("Correct Answer (A/B/C/D): ");
                ans = sc.next().toUpperCase().charAt(0);

                if (ans == 'A' || ans == 'B' || ans == 'C' || ans == 'D') {
                    break;
                } else {
                    System.out.println("Invalid input!");
                }
            }

            fw.write(q + "," + o1 + "," + o2 + "," + o3 + "," + o4 + "," + ans + "\n");

            fw.close();

            System.out.println("Question Added Successfully!");

        } catch (Exception e) {
            System.out.println("Error adding question: " + e.getMessage());
        }
    }

    public void saveResult(String username, int score, int total) {
        try {
            FileWriter fw = new FileWriter("result.txt", true);
            fw.write(username + "," + score + "/" + total + "\n");
            fw.close();
        } catch (Exception e) {
            System.out.println("Error saving result: " + e.getMessage());
        }
    }
}