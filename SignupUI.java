package ui;

import javax.swing.*;
import java.awt.*;
import service.AuthService;

public class SignupUI extends JFrame {

    private JTextField usernameField, adminKeyField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private AuthService authService;

    public SignupUI() {
        authService = new AuthService();

        setTitle("Sign Up");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Sign Up", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        roleBox = new JComboBox<>(new String[]{"student", "admin"});
        adminKeyField = new JTextField();

        JButton signupBtn = new JButton("Register");
        JButton backBtn = new JButton("Back");

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Role:"));
        panel.add(roleBox);
        panel.add(new JLabel("Admin Key:"));
        panel.add(adminKeyField);
        panel.add(signupBtn);
        panel.add(backBtn);

        add(panel);

        signupBtn.addActionListener(e -> {
            String res = authService.register(
                    usernameField.getText(),
                    new String(passwordField.getPassword()),
                    roleBox.getSelectedItem().toString(),
                    adminKeyField.getText());

            JOptionPane.showMessageDialog(this, res);
        });

        backBtn.addActionListener(e -> {
            new MainMenuUI();
            dispose();
        });

        setVisible(true);
    }
}