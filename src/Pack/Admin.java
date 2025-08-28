package Pack;

import java.util.*;
import java.sql.*;
import java.sql.Date;
//import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Admin {
     Scanner sc = new Scanner(System.in);
     String adminId;

     public Admin(String adminId) {
          this.adminId = adminId;
          try {
               System.out.println();
               System.out.println();
               System.out.println();
               System.out.println();
               System.out.println();
               System.out.println("\t\t\t\t\tL O A D I N G . . . ");
               Thread.sleep(3000);
               System.out.print("\033[H\033[2J");
               System.out.flush();

          } catch (Exception e) {
               System.out.println(e);
          }
     }

     void adminChoice() throws Exception {
          Connection c = App.Check();
          System.out.println();
          System.out.println("\t\t\t\t\t Admin ID:" + adminId);
          System.out.println();
          System.out.println();
          System.out.println();
          boolean adminLoop = true;
          while (adminLoop) {
               System.out.println(
                         "\t\t\t\t\t   1.Add Employee\n\t\t\t\t\t   2.Search Employee\n\t\t\t\t\t   3.See Employee List\n\t\t\t\t\t   4.Remove Employee");
               System.out.println(
                         "\t\t\t\t\t   5.Assign Tasks\n\t\t\t\t\t   6.See top employee\n\t\t\t\t\t   7.See Turned in Tasks");
               System.out.print("\t\t\t\t\t   8.Exit\n\t\t\t\t\t   Enter you choice:");
               int adminChoice = sc.nextInt();
               switch (adminChoice) {
                    case 1:
                         System.out.print("\033[H\033[2J");
                         System.out.flush();
                         System.out.println("\t\t\t\t\tEMPLOYEE DETAILS");
                         System.out.print("\t\t\t\tFirstname of the employee : ");
                         String empFname = sc.next();
                         System.out.print("\t\t\t\tLastname of employee : ");
                         String empLname = sc.next();
                         String empName = empFname + " " + empLname;
                         boolean mnoVerifier = false;
                         String empMno = null;
                         int mnoCounter = 0;
                         while (!mnoVerifier) {
                              System.out.print("\t\t\t\tContact number : ");
                              empMno = sc.next();
                              mnoVerifier = mnoChecker(empMno);
                              if (mnoCounter > 2) {
                                   System.out.println(
                                             "You are being redirected to home page due to multiple unsuccessful attempts");
                                   empMno = null;
                                   mnoVerifier = true;
                                   break;
                              }
                              mnoCounter++;
                         }
                         boolean emVerifier = false;
                         String empMail = null;
                         int emCounter = 0;
                         while (!emVerifier) {
                              System.out.print("\t\t\t\tEmail address : ");
                              empMail = sc.next();
                              emVerifier = emChecker(empMail);
                              if (emCounter > 2) {
                                   System.out.println(
                                             "You are being redirected to home page due to multiple unsuccessful attempts");
                                   empMail = null;
                                   emVerifier = true;
                                   break;
                              }
                              emCounter++;
                         }
                         System.out.print("\t\t\t\tEnter date of birth (YYYY-MM-DD) : ");
                         String date = sc.next();
                         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                         Date dob = Date.valueOf(LocalDate.parse(date, formatter));
                         System.out.print("\t\t\t\tEnter department of joining : ");
                         String dept = sc.next();
                         double points = 0;
                         System.out.print("\t\t\t\tEnter salary of employee : ");
                         double salary = sc.nextDouble();
                         String data = empName + "\n" + empMno + "\n" + empMail + "\n" + dob + "\n" + salary;
                         FileCreator fc = new FileCreator();
                         fc.createFiles(empName + ".txt", data);
                         String insertEmployee = "INSERT INTO EMPLOYEE(name,number,email,dob,dept,points,salary) VALUES(?,?,?,?,?,?,?)";
                         PreparedStatement pst = c.prepareStatement(insertEmployee);
                         pst.setString(1, empName);
                         pst.setString(2, empMno);
                         pst.setString(3, empMail);
                         pst.setDate(4, dob);
                         pst.setString(5, dept);
                         pst.setDouble(6, points);
                         pst.setDouble(7, salary);
                         int r = pst.executeUpdate();
                         try {
                              System.out.println("\t\t\t\t\tLOADING...");
                              Thread.sleep(3000);
                              System.out.print("\033[H\033[2J");
                              System.out.flush();

                         } catch (Exception e) {
                              System.out.println(e);
                         }
                         if (r != 0) {
                              System.out.println("Employee addedd successfully!");
                         } else {
                              System.out.println("Failed to add!!");
                         }
                         break;
                    case 2:
                         System.out.print("\033[H\033[2J");
                         System.out.flush();
                         boolean searchEmp = false;
                         int sId = 0;
                         System.out.print("\t\t\t\tEnter employee ID to be searched for : ");
                         sId = sc.nextInt();
                         String empSeachSql = "SELECT * FROM EMPLOYEE WHERE ID=?";
                         PreparedStatement pst1 = c.prepareStatement(empSeachSql);
                         pst1.setInt(1, sId);
                         ResultSet rs = pst1.executeQuery();
                         while (rs.next()) {
                              if (sId == rs.getInt(1)) {
                                   System.out.println("\t\t\t\tEmployee ID : " + rs.getInt(1));
                                   System.out.println("\t\t\t\tEmployee Name : " + rs.getString(2));
                                   System.out.println("\t\t\t\tEmployee Number : " + rs.getString(3));
                                   System.out.println("\t\t\t\tEmployee Email : " + rs.getString(4));
                                   System.out.println("\t\t\t\tEmployee DOB : " + rs.getDate(5));
                                   System.out.println("\t\t\t\tEmployee Department : " + rs.getString(6));
                                   System.out.println("\t\t\t\tEmployee Points : " + rs.getDouble(7));
                                   System.out.println("\t\t\t\tEmployee Salary : " + rs.getDouble(8));
                                   searchEmp = true;
                                   break;
                              }
                         }
                         if (searchEmp == false) {
                              System.out.println("\t\t\t\tNO SUCH EMPLOYEE EXISTS PLEASE CHECK ID!!");
                         }
                         break;
                    case 3:
                         System.out.print("\033[H\033[2J");
                         System.out.flush();
                         String listSql = "SELECT * FROM EMPLOYEE";
                         PreparedStatement pst2 = c.prepareStatement(listSql);
                         ResultSet rs1 = pst2.executeQuery();
                         if (rs1.isBeforeFirst()) {
                              while (rs1.next()) {
                                   System.out.println("\t\t\t\t**********************************************");
                                   System.out.println("\t\t\t\tEmployee ID : " + rs1.getInt(1));
                                   System.out.println("\t\t\t\tEmployee Name : " + rs1.getString(2));
                                   System.out.println("\t\t\t\tEmployee Number : " + rs1.getString(3));
                                   System.out.println("\t\t\t\tEmployee Email : " + rs1.getString(4));
                                   System.out.println("\t\t\t\tEmployee DOB : " + rs1.getDate(5));
                                   System.out.println("\t\t\t\tEmployee Department : " + rs1.getString(6));
                                   System.out.println("\t\t\t\tEmployee Points : " + rs1.getDouble(7));
                                   System.out.println("\t\t\t\tEmployee Salary : " + rs1.getDouble(8));
                                   System.out.println("\t\t\t\t**********************************************");
                                   System.out.println();
                              }
                         } else {
                              System.out.println("\t\t\t\tNO EMPLOYEES TO DISPLAY!!");
                         }
                         break;
                    case 4:
                         System.out.print("\033[H\033[2J");
                         System.out.flush();
                         boolean removeEmp = false;
                         int rId = 0;
                         System.out.print("\t\t\t\tEnter employee ID to be removed : ");
                         rId = sc.nextInt();
                         String empRemoveSql = "SELECT * FROM EMPLOYEE WHERE ID=?";
                         PreparedStatement pst3 = c.prepareStatement(empRemoveSql);
                         pst3.setInt(1, rId);
                         ResultSet rs2 = pst3.executeQuery();
                         while (rs2.next()) {
                              if (rId == rs2.getInt(1)) {
                                   System.out.println("\t\t\t\tEmployee ID : " + rs2.getInt(1));
                                   System.out.println("\t\t\t\tEmployee Name : " + rs2.getString(2));
                                   System.out.println("\t\t\t\tEmployee Number : " + rs2.getString(3));
                                   System.out.println("\t\t\t\tEmployee Email : " + rs2.getString(4));
                                   System.out.println("\t\t\t\tEmployee DOB : " + rs2.getDate(5));
                                   System.out.println("\t\t\t\tEmployee Department : " + rs2.getString(6));
                                   System.out.println("\t\t\t\tEmployee Points : " + rs2.getDouble(7));
                                   System.out.println("\t\t\t\tEmployee Salary : " + rs2.getDouble(8));
                                   System.out.println();
                                   System.out.print("\t\t\t\tAre you sure you want to remove(Y/N) : ");
                                   char permission = sc.next().charAt(0);
                                   if (permission == 'Y' || permission == 'y') {
                                        String removeEmpSql = "DELETE FROM EMPLOYEE WHERE ID=?";
                                        PreparedStatement pst4 = c.prepareStatement(removeEmpSql);
                                        pst4.setInt(1, rId);
                                        int r1 = pst4.executeUpdate();
                                        System.out.println((r1 > 0) ? "\t\t\t\tEMPLOYEE DELTED SUCCESSFULLY!!"
                                                  : "\t\t\t\tSORRY, UNABLE TO DELETE EMPLOYEE!!");
                                        break;
                                   } else {
                                        System.out.println("\t\t\t\tOKAY, BYE UNTIL NEXT VISIT!!!");
                                   }
                                   removeEmp = true;
                                   break;
                              }
                         }
                         if (removeEmp == false) {
                              System.out.println("\t\t\t\tNO SUCH EMPLOYEE EXISTS PLEASE CHECK ID!!");
                         }
                         break;
                    case 5:
                         System.out.print("\t\t\t\tEnter employee Id to whom you want to assign task : ");
                         int empId = sc.nextInt();
                         Task t = new Task(empId);
                         t.addTask();
                         break;
                    case 6:
                         String sql6 = "SELECT * FROM EMPLOYEE ORDER BY POINTS DESC LIMIT 1";
                         PreparedStatement pst6 = c.prepareStatement(sql6);
                         ResultSet rs6 = pst6.executeQuery();
                         if (rs6.isBeforeFirst()) {
                              while (rs6.next()) {
                                   System.out.println("\t\t\t\t**********************************************");
                                   System.out.println("\t\t\t\tEmployee ID : " + rs6.getInt(1));
                                   System.out.println("\t\t\t\tEmployee Name : " + rs6.getString(2));
                                   System.out.println("\t\t\t\tEmployee Number : " + rs6.getString(3));
                                   System.out.println("\t\t\t\tEmployee Email : " + rs6.getString(4));
                                   System.out.println("\t\t\t\tEmployee DOB : " + rs6.getDate(5));
                                   System.out.println("\t\t\t\tEmployee Department : " + rs6.getString(6));
                                   System.out.println("\t\t\t\tEmployee Points : " + rs6.getDouble(7));
                                   System.out.println("\t\t\t\tEmployee Salary : " + rs6.getDouble(8));
                                   System.out.println("\t\t\t\t**********************************************");
                                   System.out.println();
                              }
                         } else {
                              System.out.println("\t\t\t\tSorry,couldn't fetch data!");
                         }
                         break;
                    case 7:
                         Task tk = new Task();
                         tk.getTurnedInTasks();
                         break;
                    case 8:
                         Main.main(null);
                         adminLoop = false;
                         break;
                    default:
                         System.out.println("\t\t\t\tInvalid Choice!");
               }
          }
     }

     boolean mnoChecker(String mno) {
          if (mno.length() == 10) {
               if (mno.startsWith("9") || mno.startsWith("8") || mno.startsWith("7")) {
                    char[] mnoCheck = mno.toCharArray();
                    for (char c : mnoCheck) {
                         if (!Character.isDigit(c)) {
                              return false;
                         }
                    }
                    return true;
               } else {
                    System.out.println("\t\t\t\tInvalid mobile number!");
                    return false;
               }
          } else {
               System.out.println("\t\t\t\tCheck length of the number entered!");
               return false;
          }
     }

     boolean emChecker(String mail) {
          if (mail.length() > 5) {
               if (mail.endsWith("@mail.com") || mail.endsWith("@gmail.com") || mail.endsWith("@yahoomail.com")) {
                    return true;
               } else {
                    System.out.println("\t\t\t\tINVALID MAIL SYNTAX!!");
                    return false;
               }
          } else {
               System.out.println("\t\t\t\tCheck length of the mail entered!");
               return false;
          }
     }
}
