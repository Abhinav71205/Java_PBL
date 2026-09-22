package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import service.*;

public class ExamUI extends JFrame {

    public static boolean systemPopupOpen = false;

    private java.util.List<String[]> questions = new ArrayList<>();
    private int current = 0;
    private int score = 0;

    // Stores selected answer for each question
    // 0 = no answer
    private int[] selectedAnswers;

    private JLabel qLabel, timerLabel;
    private JRadioButton opt1, opt2, opt3, opt4;
    private ButtonGroup group;

    private JButton backBtn;
    private JButton nextBtn;

    private String username;

    private ProctorService proctor;
    private ActivityMonitorThread activityThread;
    private TimerThread timerThread;


    public ExamUI(String username) {
        this.username = username;

        setTitle("Exam");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        proctor = new ProctorService();

        loadQuestions();

        selectedAnswers = new int[questions.size()];

        initUI();
        startThreads();

        setVisible(true);
    }


    private void initUI() {

        setLayout(new BorderLayout());

        JPanel top = new JPanel(new BorderLayout());

        qLabel = new JLabel("Question");
        qLabel.setFont(new Font("Arial", Font.BOLD, 16));

        timerLabel = new JLabel("Time: 03:00:00");
        timerLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        top.add(qLabel, BorderLayout.WEST);
        top.add(timerLabel, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);


        JPanel center = new JPanel(new GridLayout(4, 1));

        opt1 = new JRadioButton();
        opt2 = new JRadioButton();
        opt3 = new JRadioButton();
        opt4 = new JRadioButton();

        group = new ButtonGroup();

        group.add(opt1);
        group.add(opt2);
        group.add(opt3);
        group.add(opt4);

        center.add(opt1);
        center.add(opt2);
        center.add(opt3);
        center.add(opt4);

        add(center, BorderLayout.CENTER);


        // Button panel
        JPanel buttonPanel = new JPanel(new BorderLayout());

        backBtn = new JButton("Back");
        nextBtn = new JButton("Next");

        buttonPanel.add(backBtn, BorderLayout.WEST);
        buttonPanel.add(nextBtn, BorderLayout.EAST);

        add(buttonPanel, BorderLayout.SOUTH);


        // Next / Submit button
        nextBtn.addActionListener(e -> {

            activityThread.updateActivity();

            if (current == questions.size() - 1) {
                handleSubmit();
            } else {
                handleNext();
            }
        });


        // Back button
        backBtn.addActionListener(e -> {

            activityThread.updateActivity();

            handleBack();
        });


        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                activityThread.updateActivity();
            }
        });


        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                activityThread.updateActivity();
            }
        });


        this.addWindowFocusListener(new WindowAdapter() {
            public void windowLostFocus(WindowEvent e) {

                if (proctor.isExamActive() && !systemPopupOpen) {
                    proctor.handleTabSwitch(username);
                }
            }
        });


        showQuestion();
    }


    private void startThreads() {

        timerThread = new TimerThread(
                10800,
                proctor,
                username,
                timerLabel
        );

        timerThread.start();

        activityThread = new ActivityMonitorThread(
                proctor,
                username
        );

        activityThread.start();
    }


    private void loadQuestions() {

        try {

            File file = new File("questions.txt");

            if (!file.exists()) return;

            BufferedReader br =
                    new BufferedReader(new FileReader(file));

            String line;

            while ((line = br.readLine()) != null) {

                String data[] = line.split(",");

                if (data.length == 6) {
                    questions.add(data);
                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showQuestion() {

        if (!proctor.isExamActive() ||
                current >= questions.size()) {

            finishExam();

            return;
        }


        String q[] = questions.get(current);

        qLabel.setText(
                "Q" + (current + 1) + ": " + q[0]
        );

        opt1.setText("1. " + q[1]);
        opt2.setText("2. " + q[2]);
        opt3.setText("3. " + q[3]);
        opt4.setText("4. " + q[4]);

        group.clearSelection();


        // Restore previously selected answer
        if (selectedAnswers[current] == 1) {

            opt1.setSelected(true);

        } else if (selectedAnswers[current] == 2) {

            opt2.setSelected(true);

        } else if (selectedAnswers[current] == 3) {

            opt3.setSelected(true);

        } else if (selectedAnswers[current] == 4) {

            opt4.setSelected(true);
        }


        // Disable Back on first question
        if (current == 0) {

            backBtn.setEnabled(false);

        } else {

            backBtn.setEnabled(true);
        }


        // Change Next to Submit on last question
        if (current == questions.size() - 1) {

            nextBtn.setText("Submit");

        } else {

            nextBtn.setText("Next");
        }
    }


    private void saveCurrentAnswer() {

        int selected = -1;

        if (opt1.isSelected()) selected = 1;

        if (opt2.isSelected()) selected = 2;

        if (opt3.isSelected()) selected = 3;

        if (opt4.isSelected()) selected = 4;


        // If no option is selected, keep it unanswered
        if (selected == -1) {
            return;
        }


        String correct = questions.get(current)[5];


        // Remove old score if old answer was correct
        if (selectedAnswers[current] != 0 &&
                String.valueOf(selectedAnswers[current])
                        .equals(correct)) {

            score--;
        }


        // Save new answer
        selectedAnswers[current] = selected;


        // Add score if new answer is correct
        if (String.valueOf(selected).equals(correct)) {

            score++;
        }
    }


    private void handleNext() {

        if (!proctor.isExamActive()) {

            finishExam();

            return;
        }


        // Save answer if user selected one
        saveCurrentAnswer();


        // Move to next question
        current++;

        showQuestion();
    }


    private void handleBack() {

        if (!proctor.isExamActive()) {

            finishExam();

            return;
        }


        // Save current answer if user selected one
        saveCurrentAnswer();


        if (current > 0) {

            current--;

            showQuestion();
        }
    }


    // ============================
    // SUBMIT CONFIRMATION
    // ============================

    private void handleSubmit() {

        if (!proctor.isExamActive()) {

            finishExam();

            return;
        }


        // Save the answer of the last question
        saveCurrentAnswer();


        systemPopupOpen = true;


        int choice = JOptionPane.showConfirmDialog(
                this,
                "Do you want to submit the exam?",
                "Submit Exam",
                JOptionPane.YES_NO_OPTION
        );


        systemPopupOpen = false;


        if (choice == JOptionPane.YES_OPTION) {

            finishExam();

        } else {

            // User selected NO
            // Stay on the current question
            showQuestion();
        }
    }


    private void finishExam() {

        proctor.endExam("Completed", username);

        try {

            File file = new File("result.txt");

            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedWriter bw =
                    new BufferedWriter(
                            new FileWriter(file, true)
                    );

            if (file.length() > 0) {
                bw.newLine();
            }


            double accuracy =
                    ((double) score /
                            questions.size()) * 100;

            String acc =
                    String.format("%.2f", accuracy);


            String performance;

            if (accuracy >= 80) {

                performance = "Excellent";

            } else if (accuracy >= 50) {

                performance = "Average";

            } else {

                performance = "Needs Improvement";
            }


            bw.write(
                    "User: " + username +
                    " | Score: " + score +
                    "/" + questions.size() +
                    " | Accuracy: " + acc +
                    "%" +
                    " | Performance: " +
                    performance
            );


            bw.close();

            proctor.saveLogsToFile(username);


        } catch (Exception e) {

            e.printStackTrace();
        }


        systemPopupOpen = true;


        JOptionPane.showMessageDialog(
                this,
                "Exam Finished! Score: " + score
        );


        systemPopupOpen = false;


        new DashboardUI(username);

        dispose();
    }
}
