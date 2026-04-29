package ui;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class AddQuestionUI extends JFrame {

    private JTextField q, o1, o2, o3, o4, ans;

    public AddQuestionUI() {

        setTitle("Add Question");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Add Question", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        q = new JTextField();
        o1 = new JTextField();
        o2 = new JTextField();
        o3 = new JTextField();
        o4 = new JTextField();
        ans = new JTextField();

        panel.add(new JLabel("Question:")); panel.add(q);
        panel.add(new JLabel("Option 1:")); panel.add(o1);
        panel.add(new JLabel("Option 2:")); panel.add(o2);
        panel.add(new JLabel("Option 3:")); panel.add(o3);
        panel.add(new JLabel("Option 4:")); panel.add(o4);
        panel.add(new JLabel("Correct Answer (1-4):")); panel.add(ans);

        add(panel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        JButton saveBtn = new JButton("Save");
        JButton backBtn = new JButton("Back");

        btnPanel.add(saveBtn);
        btnPanel.add(backBtn);

        add(btnPanel, BorderLayout.SOUTH);

        saveBtn.addActionListener(e -> {

            String question = q.getText().trim();
            String op1 = o1.getText().trim();
            String op2 = o2.getText().trim();
            String op3 = o3.getText().trim();
            String op4 = o4.getText().trim();
            String answer = ans.getText().trim();

            if (question.isEmpty() || op1.isEmpty() || op2.isEmpty()
                    || op3.isEmpty() || op4.isEmpty() || answer.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }

            if (!answer.matches("[1-4]")) {
                JOptionPane.showMessageDialog(this, "Correct Answer must be 1 to 4");
                return;
            }

            try {
            	File file = new File(System.getProperty("user.dir") + "/questions.txt");

                if (!file.exists()) {
                    file.createNewFile();
                }

                BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));

                if (file.length() > 0) {
                    bw.newLine();
                }

                bw.write(question + "," + op1 + "," + op2 + "," + op3 + "," + op4 + "," + answer);

                bw.close();

                JOptionPane.showMessageDialog(this, "Question Saved!");

                q.setText("");
                o1.setText("");
                o2.setText("");
                o3.setText("");
                o4.setText("");
                ans.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving question!");
                ex.printStackTrace();
            }
        });

        backBtn.addActionListener(e -> {
            new AdminUI("admin");
            dispose();
        });

        setVisible(true);
    }
}