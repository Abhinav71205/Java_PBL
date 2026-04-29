	package ui;
	
	import javax.swing.*;
	import java.awt.*;
	
	public class MainMenuUI extends JFrame {
	
	    public MainMenuUI() {
	        setTitle("Online Exam System");
	        setSize(400, 250);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        JPanel panel = new JPanel(new GridLayout(3, 1, 15, 15));
	        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
	        JLabel title = new JLabel("Welcome", JLabel.CENTER);
	        title.setFont(new Font("Arial", Font.BOLD, 20));
	        JButton loginBtn = new JButton("Login");
	        JButton signupBtn = new JButton("Sign Up");
	        panel.add(title);
	        panel.add(loginBtn);
	        panel.add(signupBtn);
	
	        add(panel);
	
	        loginBtn.addActionListener(e -> {
	            new LoginUI();
	            dispose();
	        });
	
	        signupBtn.addActionListener(e -> {
	            new SignupUI();
	            dispose();
	        });
	
	        setVisible(true);
	    }
	    public static void main(String[] args) {
	        new MainMenuUI();
	    }
	}