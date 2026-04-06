package main;

import java.util.Scanner;
import service.AuthService;
import service.ExamService;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AuthService auth = new AuthService();
        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            int ch;
            while (true) {
                try {
                    ch = Integer.parseInt(sc.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.print("Enter valid number: ");
                }
            }
            if (ch == 1) {
                String u;
                while (true) {
                    System.out.print("Username: ");
                    u = sc.nextLine();
                    if (!auth.isValidUsername(u)) {
                        System.out.println("Invalid username!");
                    } else if (auth.userExists(u)) {
                        System.out.println("Username already exists!");
                    } else {
                        break;
                    }
                }
                System.out.print("Password: ");
                String p = sc.nextLine();
                System.out.print("Role (ADMIN/STUDENT): ");
                String r = sc.nextLine().toUpperCase();
                if (r.equals("ADMIN")) {
                    System.out.print("Enter Admin Key: ");
                    String key = sc.nextLine();
                    if (!key.equals("admin123")) {
                        System.out.println("Invalid key! Registered as STUDENT.");
                        r = "STUDENT";
                    }
                }
                auth.register(u, p, r);
            }
            else if (ch == 2) {
                System.out.print("Username: ");
                String u = sc.nextLine();
                System.out.print("Password: ");
                String p = sc.nextLine();
                if (auth.login(u, p)) {
                    System.out.println("Login Successful");
                    String role = auth.getUserRole(u);
                    ExamService exam = new ExamService();
                    if (role.equals("ADMIN")) {
                        System.out.println("Welcome Admin!");
                        while (true) {
                            System.out.println("\n1. Add Question");
                            System.out.println("2. Logout");
                            int choice;
                            while (true) {
                                try {
                                    choice = Integer.parseInt(sc.nextLine());
                                    break;
                                } catch (Exception e) {
                                    System.out.print("Enter valid number: ");
                                }
                            }
                            if (choice == 1) {
                                exam.addQuestion();
                            } else {
                                break;
                            }
                        }
                    } else {
                        System.out.println("Welcome Student!");
                        while (true) {
                            System.out.println("\n1. Start Exam");
                            System.out.println("2. Logout");
                            int choice;
                            while (true) {
                                try {
                                    choice = Integer.parseInt(sc.nextLine());
                                    break;
                                } catch (Exception e) {
                                    System.out.print("Enter valid number: ");
                                }
                            }
                            if (choice == 1) {
                                System.out.println("Starting Exam...");
                                exam.loadQuestions();                     
                                exam.startExam(u);
                            } else {
                                break;
                            }
                        }
                    }
                } else {
                    System.out.println("Invalid User");
                }
            }
            else {
                break;
            }
        }
        sc.close();
    }
}
