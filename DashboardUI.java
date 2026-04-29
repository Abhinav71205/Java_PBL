package ui;

import javax.swing.*;
import java.awt.*;

public class DashboardUI extends JFrame {

    private String username;

    public DashboardUI(String username) {
        this.username = username;

        setTitle("Student Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();

        setVisible(true);
    }

    private void initUI() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel welcomeLabel = new JLabel("Welcome, " + username, JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JButton startExamBtn = new JButton("Start Exam");
        JButton viewResultBtn = new JButton("View Result");
        JButton logoutBtn = new JButton("Logout");

        panel.add(welcomeLabel);
        panel.add(startExamBtn);
        panel.add(viewResultBtn);
        panel.add(logoutBtn);

        add(panel);

        
        startExamBtn.addActionListener(e -> {
            new ExamUI(username);  
            dispose();              
        });

        
        viewResultBtn.addActionListener(e -> {
        	new ViewResultUI(username);   
            dispose();
        });

        
        logoutBtn.addActionListener(e -> {
            new MainMenuUI();
            dispose();
        });
    }
}