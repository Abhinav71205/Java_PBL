package ui;

import javax.swing.*;
import java.awt.*;

public class AdminUI extends JFrame {

    private String username;

    public AdminUI(String username) {
        this.username = username;

        setTitle("Admin Panel");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();

        setVisible(true);
    }

    private void initUI() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel title = new JLabel("Admin Dashboard - " + username, JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JButton addQuestionBtn = new JButton("Add Question");
        JButton viewQuestionBtn = new JButton("View Questions");
        JButton viewResultBtn = new JButton("View Results");
        JButton logoutBtn = new JButton("Logout");

        panel.add(title);
        panel.add(addQuestionBtn);
        panel.add(viewQuestionBtn);
        panel.add(viewResultBtn);
        panel.add(logoutBtn);

        add(panel);

        addQuestionBtn.addActionListener(e -> {
            new AddQuestionUI();
            dispose();
        });

        viewQuestionBtn.addActionListener(e -> {
            new ViewQuestionUI();
            dispose();
        });

        viewResultBtn.addActionListener(e -> {
            new ViewResultUI();  
            dispose();
        });

        logoutBtn.addActionListener(e -> {
            new MainMenuUI();
            dispose();
        });
    }
}