package Pack;

import java.util.*;
import java.sql.*;
import java.io.*;
import java.sql.Date;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        Connection con = App.Check();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(
                "\n\t\t * * * * * W E L C O M E  T O  E M P L O Y E E  M A N A G E M E N T   S Y S T E M * * * * *");
        System.out.println();
        
        // Create admin data file if it doesn't exist
        FileCreator fc = new FileCreator();
        fc.createFiles("AdminData.txt",
                "rajurastogi@mail.com\nrr456\nfarhanqureshi@mail.com\nfq789\nbaburao@mail.com\nbr097\nshyampatel@mail.com\nsp646\nmunnikhan@mail.com\nmk149\naadityaroy@mail.com\nar474\ngeetkapoor@mail.com\ngk205");
        
        while (true) {
            try {
                System.out.println("\n\t\t\t\t\t1. Admin Login");
                System.out.println("\t\t\t\t\t2. Employee Login");
                System.out.println("\t\t\t\t\t3. Exit");
                System.out.print("\n\t\t\t\t\tEnter your choice (1-3): ");
                
                if (!sc.hasNextInt()) {
                    System.out.println("\n\t\t\t\tPlease enter a valid number!");
                    sc.next(); // consume invalid input
                    continue;
                }
                
                int loginChoice = sc.nextInt();
                sc.nextLine(); // consume newline
                switch (loginChoice) {
                    case 1: // Admin Login
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.out.println("\n\t\t\t\t\tA D M I N   L O G I N\n");
                        
                        for (int attempts = 0; attempts < 3; attempts++) {
                            System.out.print("\t\t\t\tEnter admin email: ");
                            String adminId = sc.nextLine();
                            System.out.print("\t\t\t\tEnter password: ");
                            String adminPass = sc.nextLine();
                            
                            if (attempts == 1) {
                                System.out.println("\n\t\t\t\tYou have 1 attempt remaining!");
                            }
                            
                            if (loginAdmin(adminId, adminPass)) {
                                System.out.print("\033[H\033[2J");
                                System.out.flush();
                                Admin a = new Admin(adminId);
                                a.adminChoice();
                                break;
                            } else {
                                System.out.println("\n\t\t\t\tInvalid credentials!");
                                if (attempts == 2) {
                                    System.out.println("\n\t\t\t\tMaximum attempts reached. Returning to main menu.");
                                    Thread.sleep(2000);
                                }
                            }
                        }
                        break;
                    
                    case 2: // Employee Login
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.out.println("\n\t\t\t\tE M P L O Y E E   L O G I N\n");
                        
                        for (int attempts = 0; attempts < 3; attempts++) {
                            try {
                                System.out.print("\t\t\t\tEnter employee ID: ");
                                int empId = sc.nextInt();
                                sc.nextLine(); // consume newline
                                
                                System.out.print("\t\t\t\tEnter date of birth (YYYY-MM-DD): ");
                                String dateStr = sc.nextLine();
                                
                                try {
                                    LocalDate dob = LocalDate.parse(dateStr);
                                    Date sqlDob = Date.valueOf(dob);
                                    
                                    if (attempts == 1) {
                                        System.out.println("\n\t\t\t\tYou have 1 attempt remaining!");
                                    }
                                    
                                    if (loginEmployee(empId, sqlDob)) {
                                        System.out.print("\033[H\033[2J");
                                        System.out.flush();
                                        Employee e = new Employee(empId, con);
                                        e.employeeChoice();
                                        break;
                                    } else {
                                        System.out.println("\n\t\t\t\tInvalid employee ID or date of birth!");
                                    }
                                } catch (java.time.format.DateTimeParseException e) {
                                    System.out.println("\n\t\t\t\tInvalid date format! Please use YYYY-MM-DD.");
                                    attempts--; // Don't count invalid date format as an attempt
                                }
                                
                                if (attempts == 2) {
                                    System.out.println("\n\t\t\t\tMaximum attempts reached. Returning to main menu.");
                                    Thread.sleep(2000);
                                }
                                
                            } catch (InputMismatchException e) {
                                System.out.println("\n\t\t\t\tPlease enter a valid employee ID (number).");
                                sc.nextLine(); // clear the invalid input
                                attempts--; // Don't count this as an attempt
                            }
                        }
                        break;
                        
                    case 3: // Exit
                        System.out.println("\n\t\t\t\tThank you for using the Employee Management System!");
                        System.exit(0);
                        
                    default:
                        System.out.println("\n\t\t\t\tPlease enter a number between 1 and 3.");
                }
            } catch (Exception e) {
                System.out.println("\n\t\t\t\tAn error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    static boolean loginEmployee(int id, Date dob) throws Exception {
        Connection con = App.Check();
        String sql = "{? = call loginEmployee(?,?)}";
        CallableStatement cst = con.prepareCall(sql);
        cst.registerOutParameter(1, Types.INTEGER);
        cst.setDate(2, dob);
        cst.setInt(3, id);
        cst.execute();
        boolean result = cst.getBoolean(1);
        return result;
    }

    static boolean loginAdmin(String id, String pass) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("AdminData.txt"));
        String read;
        while ((read = br.readLine()) != null) {
            if (read.equals(id)) {
                read = br.readLine();
                if (read.equals(pass)) {
                    br.close();
                    return true;
                }
            }
        }
        br.close();
        return false;
    }
}
