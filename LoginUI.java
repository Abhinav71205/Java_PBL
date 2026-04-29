package ui;

import javax.swing.*;
import java.awt.*;
import service.AuthService;

public class LoginUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private AuthService authService;

    public LoginUI() {
        authService = new AuthService();

        setTitle("Login");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Login", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        JButton backBtn = new JButton("Back");

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginBtn);
        panel.add(backBtn);

        add(panel, BorderLayout.CENTER);

        loginBtn.addActionListener(e -> {
            String role = authService.login(usernameField.getText(), new String(passwordField.getPassword()));

            if (role != null) {
                if (role.equalsIgnoreCase("admin"))
                    new AdminUI(usernameField.getText());
                else
                    new DashboardUI(usernameField.getText());

                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login");
            }
        });

        backBtn.addActionListener(e -> {
            new MainMenuUI();
            dispose();
        });

        setVisible(true);
    }
}