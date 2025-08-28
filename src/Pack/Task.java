package Pack;

import java.util.*;
import java.sql.*;

public class Task {
     static Scanner sc = new Scanner(System.in);
     Connection con;
     int empId;

     Task() {
          try {
               con = App.Check();
          } catch (Exception e) {
               // System.out.println(e.getMessage());
          }
     }

     public Task(int empId) {
          this.empId = empId;
          try {
               con = App.Check();
          } catch (Exception e) {
               // System.out.println(e.getMessage());
          }
     }

     void addTask() throws Exception {
          System.out.print("\t\t\t\tEnter task name : ");
          String taskName = sc.next();
          System.out.print("\t\t\t\tEnter task priority : ");
          int priority = sc.nextInt();
          String sql = "INSERT INTO TASKS(eid,name,priority,status) VALUES (?,?,?,false)";
          PreparedStatement pst = con.prepareStatement(sql);
          pst.setInt(1, empId);
          pst.setString(2, taskName);
          pst.setInt(3, priority);
          int r = pst.executeUpdate();
          System.out.println((r != 0) ? "\t\t\t\tTask Added Successfully!" : "\t\t\t\tFailed to add Task");

     }

     void displayTasks() throws Exception {
          BST bst = new BST();
          String sql = "SELECT * FROM TASKS WHERE EID=?";
          PreparedStatement pst = con.prepareStatement(sql);
          pst.setInt(1, empId);
          ResultSet rs = pst.executeQuery();
          if (rs.isBeforeFirst()) {
               while (rs.next()) {
                    bst.insert(rs.getInt("PRIORITY"), rs.getString("NAME"));
               }
          } else {
               System.out.println("\t\t\t\tNo tasks found\nEnjoy your day off!!");
          }
          bst.display(bst.root);
     }

     void findTask() throws Exception {
          BST bst = new BST();
          String sql = "SELECT * FROM TASKS WHERE EID=?";
          PreparedStatement pst = con.prepareStatement(sql);
          pst.setInt(1, empId);
          ResultSet rs = pst.executeQuery();
          if (rs.isBeforeFirst()) {
               while (rs.next()) {
                    bst.insert(rs.getInt("PRIORITY"), rs.getString("NAME"));
               }
          } else {
               System.out.println("\t\t\t\tNo tasks found\nEnjoy your day off!!");
          }
          bst.findMax(bst.root);
     }

     void turnInTask() throws Exception {
          System.out.print("\t\t\t\tEnter task ID which is to be turned in : ");
          int taskId = sc.nextInt();
          String sql = "{call turnInTask(?,?)}";
          CallableStatement cst = con.prepareCall(sql);
          cst.setInt(1, taskId);
          cst.setBoolean(2, true);
          int r = cst.executeUpdate();
          System.out.println((r != 0) ? "\t\t\t\tTask turned in successfully!" : "\t\t\t\tFailed");
     }

     void getTurnedInTasks() throws Exception {
          Stack s1 = new Stack();
          TaskRetrival tr = new TaskRetrival();
          String sql = "SELECT * FROM TASKS WHERE STATUS=true";
          Statement s = con.createStatement();
          ResultSet rs = s.executeQuery(sql);
          if (rs.isBeforeFirst()) {
               while (rs.next()) {
                    s1.push(new TaskRetrival(rs.getInt(1), rs.getInt(2), rs.getString(3)));
               }
               while (!s1.isEmpty()) {
                    tr.display(s1.pop());
               }
          } else {
               System.out.println("\t\t\t\tNo tasks turned in yet");
          }
     }
}

class BST {
     class Node {
          int priority;
          String name;
          Node left, right;

          public Node(int priority, String name) {
               this.priority = priority;
               this.name = name;
               this.left = null;
               this.right = null;
          }
     }

     Node root = null;

     void insert(int p, String name) {
          root = insertRecursive(p, name, root);
     }

     Node insertRecursive(int p, String name, Node root) {
          if (root == null) {
               root = new Node(p, name);
          } else if (root.priority >= p) {
               root.left = insertRecursive(p, name, root.left);
          } else if (root.priority <= p) {
               root.right = insertRecursive(p, name, root.right);
          }
          return root;
     }

     void display(Node root) {
          if (root != null) {
               displayRecursive(root);
          } else {
               System.out.println("\t\t\t\tNo tasks found!");
          }
     }

     void displayRecursive(Node root) {
          if (root != null) {
               displayRecursive(root.left);
               System.out.println("\t\t\t\tTask Name : " + root.name + "\tTask Priority : " + root.priority);
               displayRecursive(root.right);
          }
     }

     void findMax(Node root) {
          if (root != null) {
               while (root.right != null) {
                    findMax(root.right);
               }
               System.out.println("\t\t\t\tTask Name : " + root.name + "\tTask Priority : " + root.priority);
          } else {
               System.out.println("No data!");
          }
     }

     void findMin(Node root) {
          if (root != null) {
               while (root != null) {
                    findMax(root.left);
               }
          } else {
               System.out.println("\t\t\t\tNo data!");
          }
     }
}

class TaskRetrival {
     int tid, eid;
     String name;

     TaskRetrival() {

     }

     public TaskRetrival(int tid, int eid, String name) {
          this.tid = tid;
          this.eid = eid;
          this.name = name;
     }

     void display(TaskRetrival tr) {
          System.out.println("\t\tTask ID : " + tr.tid + "\tby Employee ID : " + tr.eid + "\t Task Name : " + tr.name);
     }
}

class Stack {
     class Node {
          TaskRetrival tr;
          Node next;

          Node(TaskRetrival tr) {
               this.tr = tr;
               this.next = null;
          }
     }

     Node top = null;

     void push(TaskRetrival tr) {
          if (top == null) {
               top = new Node(tr);
          } else {
               Node n = new Node(tr);
               n.next = top;
               top = n;
          }
     }

     TaskRetrival pop() {
          Node del = top;
          top = top.next;
          return del.tr;
     }

     boolean isEmpty() {
          if (top == null) {
               return true;
          } else {
               return false;
          }
     }
}
