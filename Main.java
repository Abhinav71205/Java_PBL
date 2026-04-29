package main;

import java.util.Scanner;
import java.io.*;
import service.AuthService;
import service.ExamService;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AuthService auth = new AuthService();
        ExamService exam = new ExamService();
        while (true) {
            System.out.println("\n===== ONLINE EXAM SYSTEM =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
            
                case 1:        
                    System.out.print("Enter username: ");
                    String regUser = sc.nextLine();
                    System.out.print("Enter password: ");
                    String regPass = sc.nextLine();
                    System.out.print("Enter role (student/admin): ");
                    String role = sc.nextLine();
                    String adminKey = "";
                    if (role.equalsIgnoreCase("admin")) {
                        System.out.print("Enter admin secret key: ");
                        adminKey = sc.nextLine();
                    }
                    String result = auth.register(regUser, regPass, role, adminKey);
                    if (result.equals("SUCCESS")) {
                        System.out.println("✅ Registration successful!");
                    } else {
                        System.out.println("❌ " + result);
                    }
                    break;
                    
                case 2:
                    System.out.print("Enter username: ");
                    String username = sc.nextLine();
                    System.out.print("Enter password: ");
                    String password = sc.nextLine();
                    String userRole = auth.login(username, password);
                    if (userRole != null) {
                        System.out.println("✅ Login successful! Role: " + userRole);       
                        if (userRole.equalsIgnoreCase("admin")) {
                            while (true) {
                                System.out.println("\n--- ADMIN PANEL ---");
                                System.out.println("1. Add Question");
                                System.out.println("2. View Results");
                                System.out.println("3. View Questions");
                                System.out.println("4. Logout");
                                System.out.print("Enter choice: ");
                                int adminChoice = sc.nextInt();
                                sc.nextLine();
                                switch (adminChoice) {
                                    case 1:
                                        exam.addQuestion();
                                        break;
                                    case 2:
                                        viewResults();
                                        break;
                                    case 3:
                                        viewQuestions();
                                        break;
                                    case 4:
                                        System.out.println("Logging out...");
                                        break;
                                    default:
                                        System.out.println("Invalid choice!");
                                }
                                if (adminChoice == 4) break;
                            }

                        }
                        else {
                            while (true) {
                                System.out.println("\n--- STUDENT PANEL ---");
                                System.out.println("1. Start Exam");
                                System.out.println("2. Logout");
                                System.out.print("Enter choice: ");
                                int studentChoice = sc.nextInt();
                                sc.nextLine();
                                switch (studentChoice) {
                                    case 1:
                                        exam.loadQuestions();
                                        exam.startExam(username);
                                        break;
                                    case 2:
                                        System.out.println("Logging out...");
                                        break;
                                    default:
                                        System.out.println("Invalid choice!");
                                }
                                if (studentChoice == 2) break;
                            }
                        }

                    } else {
                        System.out.println("❌ Invalid credentials!");
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    public static void viewResults() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("result.txt"));
            String line;
            System.out.println("\n--- ALL RESULTS ---");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error reading results: " + e.getMessage());
        }
    }   
    public static void viewQuestions() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("questions.txt"));
            String line;
            int count = 1;
            System.out.println("\n--- ALL QUESTIONS ---");
            while ((line = br.readLine()) != null) {
                String data[] = line.split("\\|");
                if (data.length < 6) continue;
                System.out.println("\nQ" + count + ": " + data[0]);
                System.out.println("A) " + data[1]);
                System.out.println("B) " + data[2]);
                System.out.println("C) " + data[3]);
                System.out.println("D) " + data[4]);
                System.out.println("Correct: " + data[5]);
                count++;
            }
            br.close();

        } catch (Exception e) {
            System.out.println("Error reading questions: " + e.getMessage());
        }
    }
}