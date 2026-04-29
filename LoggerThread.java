package service;

public class LoggerThread extends Thread {

    private ProctorService proctor;

    public LoggerThread(ProctorService proctor) {
        this.proctor = proctor;
    }

    public void run() {
        try {
            while (proctor.isExamActive()) {
                Thread.sleep(15000);
                System.out.println("[Logger] System running...");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}