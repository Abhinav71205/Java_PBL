package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProctorLog {
    String username;
    String event;
    long timestamp;

    public ProctorLog(String username, String event) {
        this.username = username;
        this.event = event;
        this.timestamp = System.currentTimeMillis();
    }

    public void display() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String time = sdf.format(new Date(timestamp));

        System.out.println(username + " | " + event + " | " + time);
    }

    public String toFileString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String time = sdf.format(new Date(timestamp));

        return username + "," + event + "," + time;
    }
}