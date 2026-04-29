package service;

public class ActivityMonitorThread extends Thread {

    private ProctorService proctor;
    private String username;
    private long lastActivityTime;
    private static final int AFK_LIMIT = 10;

    public ActivityMonitorThread(ProctorService proctor, String username) {
        this.proctor = proctor;
        this.username = username;
        this.lastActivityTime = System.currentTimeMillis();
    }

    public void updateActivity() {
        lastActivityTime = System.currentTimeMillis();
    }

    public void run() {
        try {
            while (proctor.isExamActive()) {
                long currentTime = System.currentTimeMillis();
                long idleTime = (currentTime - lastActivityTime) / 1000;

                if (idleTime > AFK_LIMIT) {
                    proctor.handleAFK(username);
                    lastActivityTime = currentTime;
                }

                Thread.sleep(10000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}