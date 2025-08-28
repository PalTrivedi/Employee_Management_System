package Pack;

import java.sql.*;
import java.util.*;

public class Employee {
     static Scanner sc = new Scanner(System.in);
     int empId;
     Connection con;

     Employee(int empId, Connection con) {
          this.empId = empId;
          this.con = con;
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

     void employeeChoice() throws Exception {
          increasePoints();
          boolean eLoop = true;
          while (eLoop) {
               System.out.println(
                         "\t\t\t\t1.See all tasks\n\t\t\t\t2.See task with closest deadline\n\t\t\t\t3.Turn in Task");
               System.out.println("\t\t\t\t4.Exit");
               System.out.print("Enter your choice : ");
               int choice = sc.nextInt();
               switch (choice) {
                    case 1:
                         Task t = new Task(empId);
                         t.displayTasks();
                         break;
                    case 2:
                         Task t1 = new Task(empId);
                         t1.findTask();
                         break;
                    case 3:
                         Task t2 = new Task(empId);
                         t2.turnInTask();
                         increasePoints();
                         break;
                    case 4:
                         Main.main(null);
                         eLoop = false;
                         break;
                    default:
                         System.out.println("\t\t\t\tEnter valid choice!");
                         break;
               }
          }
     }

     void increasePoints() throws Exception {
          String sql = "{call increasePoints(?)}";
          CallableStatement cst = con.prepareCall(sql);
          cst.setInt(1, empId);
          cst.executeUpdate();
     }
}
