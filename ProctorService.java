package service;

import model.ProctorLog;
import java.util.*;
import java.io.*;
import javax.swing.*;
import ui.ExamUI;

public class ProctorService {

    private List<ProctorLog> logs = new ArrayList<>();
    private boolean examActive = true;

    private int afkWarnings = 0;
    private int auditWarnings = 0;

    public synchronized void logEvent(String username, String event) {
        logs.add(new ProctorLog(username, event));
    }

    public boolean isExamActive() {
        return examActive;
    }

    public void endExam(String reason, String username) {
        logEvent(username, "AUTO SUBMIT: " + reason);
        examActive = false;
    }

    public void handleAFK(String username) {
        afkWarnings++;

        if (afkWarnings < 3) {
            ExamUI.systemPopupOpen = true;
            JOptionPane.showMessageDialog(null,
                    " AFK Warning (" + afkWarnings + "/3)\nNo mouse activity detected!");
            ExamUI.systemPopupOpen = false;

            logEvent(username, "AFK Warning " + afkWarnings);
        } else {
            ExamUI.systemPopupOpen = true;
            JOptionPane.showMessageDialog(null,
                    "❌ AFK limit exceeded.\nExam will be auto submitted.");
            ExamUI.systemPopupOpen = false;

            endExam("AFK Limit Exceeded", username);
        }
    }

    public void handleAudit(String username, String reason) {
        if (auditWarnings == 0) {
            auditWarnings++;

            ExamUI.systemPopupOpen = true;
            JOptionPane.showMessageDialog(null,
                    "Suspicious Activity Detected:\n" + reason);
            ExamUI.systemPopupOpen = false;

            logEvent(username, "Audit: " + reason);
        }
    }

    public void handleTabSwitch(String username) {
        ExamUI.systemPopupOpen = true;
        JOptionPane.showMessageDialog(null,
                "Tab switching detected.\nExam will be auto submitted.");
        ExamUI.systemPopupOpen = false;

        endExam("Tab Switching", username);
    }

    public void showLogs() {
        System.out.println("\n--- PROCTOR LOGS ---");
        for (ProctorLog log : logs) {
            log.display();
        }
    }

    public void saveLogsToFile(String username) {
        try {
            BufferedWriter bw = new BufferedWriter(
                new FileWriter(username + "_proctor_logs.txt", true)
            );

            bw.write("\n===== NEW EXAM SESSION =====\n");

            for (ProctorLog log : logs) {
                bw.write(log.toFileString());
                bw.newLine();
            }

            bw.write("===== END SESSION =====\n");

            bw.close();

        } catch (Exception e) {
            System.out.println("Error saving logs: " + e.getMessage());
        }
    }
}