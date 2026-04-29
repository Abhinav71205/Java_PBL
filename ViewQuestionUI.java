package ui;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ViewQuestionUI extends JFrame {

    public ViewQuestionUI() {

        setTitle("View Questions");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("All Questions", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        JTextArea area = new JTextArea();
        area.setEditable(false);

        File file = new File(System.getProperty("user.dir") + "/questions.txt");

        try {
            if (!file.exists()) file.createNewFile();

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            boolean empty = true;

            while ((line = br.readLine()) != null) {

                empty = false;

                String data[] = line.split(",");

                
                if (data.length < 6) {
                    area.append("Corrupted Question Data\n\n");
                    continue;
                }

                area.append("Q: " + data[0] + "\n");
                area.append("1. " + data[1] + "\n");
                area.append("2. " + data[2] + "\n");
                area.append("3. " + data[3] + "\n");
                area.append("4. " + data[4] + "\n");
                area.append("Answer: " + data[5] + "\n\n");
            }

            if (empty) {
                area.setText("No questions found.");
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace(); 
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

        setVisible(true);
    }
}