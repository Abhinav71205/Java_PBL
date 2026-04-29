package ui;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ViewResultUI extends JFrame {

    public ViewResultUI() {
        setTitle("All Student Results");
        setupUI();
        showAllResults();
        setVisible(true);
    }

    public ViewResultUI(String username) {
        setTitle("My Result");
        setupUI();
        showUserResult(username);
        setVisible(true);
    }

    private void setupUI() {
        setSize(700, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Results", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);
    }

    private void showAllResults() {

        JTextArea area = new JTextArea();
        area.setEditable(false);

        try {
            File file = new File("result.txt");

            if (!file.exists()) {
                area.setText("No results found.");
            } else {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                boolean empty = true;

                while ((line = br.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        area.append(line + "\n\n");
                        empty = false;
                    }
                }

                if (empty) {
                    area.setText("No results found.");
                }

                br.close();
            }

        } catch (Exception e) {
            area.setText("Error reading file.");
        }

        add(new JScrollPane(area), BorderLayout.CENTER);

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
            new AdminUI("admin");
            dispose();
        });

        JPanel bottom = new JPanel();
        bottom.add(backBtn);
        add(bottom, BorderLayout.SOUTH);
    }

    private void showUserResult(String username) {

        JTextArea area = new JTextArea();
        area.setEditable(false);

        try {
            File file = new File("result.txt");

            if (!file.exists()) {
                area.setText("No results found.");
            } else {

                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                boolean found = false;

                while ((line = br.readLine()) != null) {

                    if (line.contains("User: " + username)) {
                        area.append(line + "\n");
                        found = true;
                    }
                }

                if (!found) {
                    area.setText("No result found for you.");
                }

                br.close();
            }

        } catch (Exception e) {
            area.setText("Error reading file.");
        }

        add(new JScrollPane(area), BorderLayout.CENTER);

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
            new DashboardUI(username);
            dispose();
        });

        JPanel bottom = new JPanel();
        bottom.add(backBtn);
        add(bottom, BorderLayout.SOUTH);
    }
}