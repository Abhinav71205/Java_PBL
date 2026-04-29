package service;

import model.Analysis;
import java.io.*;

public class AnalysisService {

    public Analysis generateAnalysis(String username, int score, int total) {
        return new Analysis(username, score, total);
    }

    
    public void saveAnalysisToFile(String username, int score, int total) {
        try {
            BufferedWriter bw = new BufferedWriter(
                new FileWriter("analysis.txt", true)
            );

            double accuracy = (double) score / total * 100;

            bw.write(username + "," + score + "/" + total + "," + accuracy + "%");
            bw.newLine();

            bw.close();

        } catch (Exception e) {
            System.out.println("Error saving analysis: " + e.getMessage());
        }
    }
}