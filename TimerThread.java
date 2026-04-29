package service;

import javax.swing.*;

public class TimerThread extends Thread {

    private int duration;
    private ProctorService proctor;
    private String username;
    private JLabel timerLabel;

    public TimerThread(int duration, ProctorService proctor, String username, JLabel timerLabel) {
        this.duration = duration;
        this.proctor = proctor;
        this.username = username;
        this.timerLabel = timerLabel;
    }

    public TimerThread(int duration, ProctorService proctor, String username) {
        this.duration = duration;
        this.proctor = proctor;
        this.username = username;
        this.timerLabel = null;
    }

    private String formatTime(int seconds) {
        int hrs = seconds / 3600;
        int mins = (seconds % 3600) / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d:%02d", hrs, mins, secs);
    }

    public void run() {
        try {
            for (int i = duration; i >= 0; i--) {

                if (!proctor.isExamActive()) return;

                int time = i;

                
                if (timerLabel != null) {
                    SwingUtilities.invokeLater(() ->
                        timerLabel.setText("Time: " + formatTime(time))
                    );
                } 
                
                else {
                    System.out.println("⏳ Time left: " + formatTime(time));
                }

                Thread.sleep(1000);
            }

            System.out.println("Time Over!");
            proctor.endExam("Time Expired", username);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}