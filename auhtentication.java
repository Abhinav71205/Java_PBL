package service;

import java.io.*;

public class AuthService {
    public boolean isValidUsername(String u) {
        return u.matches(".*[a-zA-Z].*");
    }
    public boolean userExists(String u) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String data[] = line.split(",");
                if (data[0].equals(u)) {
                    br.close();
                    return true;
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error checking user");
        }
        return false;
    }
    public void register(String u, String p, String r) {
        try {
            FileWriter fw = new FileWriter("users.txt", true);
            fw.write(u + "," + p + "," + r + "\n");
            fw.close();
            System.out.println("Registered Successfully");

        } catch (Exception e) {
            System.out.println("Error in registration");
        }
    }
    public boolean login(String u, String p) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                String data[] = line.split(",");

                if (data[0].equals(u) && data[1].equals(p)) {
                    br.close();
                    return true;
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error in login");
        }
        return false;
    }
    public String getUserRole(String u) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                String data[] = line.split(",");

                if (data[0].equals(u)) {
                    br.close();
                    return data[2]; // return role
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error getting role");
        }
        return "";
    }
}
