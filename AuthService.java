package service;

import java.io.*;

public class AuthService {

    private static final String ADMIN_KEY = "admin123"; // 🔐 secret key

    
    public boolean userExists(String username) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                String data[] = line.split(",");

                if (data[0].equals(username)) {
                    br.close();
                    return true;
                }
            }

            br.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return false;
    }

    
    public boolean isValidUsername(String username) {
        return username.matches("[a-zA-Z][a-zA-Z0-9]*");
    }

    
    public boolean isStrongPassword(String password) {

        if (password.length() < 6) return false;

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isLowerCase(c)) hasLower = true;
            if (Character.isDigit(c)) hasDigit = true;
        }

        return hasUpper && hasLower && hasDigit;
    }

    
    public String register(String username, String password, String role, String adminKey) {

        
        if (!isValidUsername(username)) {
            return "Username must start with letters and can contain digits.";
        }

        
        if (!isStrongPassword(password)) {
            return "Password must contain uppercase, lowercase and digit (min 6 chars).";
        }

        
        if (!role.equalsIgnoreCase("student") && !role.equalsIgnoreCase("admin")) {
            return "Role must be either 'student' or 'admin'.";
        }

        
        if (role.equalsIgnoreCase("admin")) {
            if (!adminKey.equals(ADMIN_KEY)) {
                return "Invalid admin secret key!";
            }
        }

        
        if (userExists(username)) {
            return "User already exists!";
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true));
            bw.write(username + "," + password + "," + role.toLowerCase());
            bw.newLine();
            bw.close();

            return "SUCCESS";

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }


    public String login(String username, String password) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                String data[] = line.split(",");

                if (data[0].equals(username) && data[1].equals(password)) {
                    br.close();
                    return data[2]; // 🔥 return role
                }
            }

            br.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }
}