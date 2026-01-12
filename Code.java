// Employee Management System

import java.io.*;
import java.util.*;

// -------------------- B David Raju --------------------
class MainMenu {
  public void menu() {
    System.out.println("\n\t\tEMPLOYEE MANAGEMENT SYSTEM");
    System.out.println("\t\t----------------------------");
    System.out.println("Press 1 : Add Employee Details");
    System.out.println("Press 2 : View Employee Details");
    System.out.println("Press 3 : Remove Employee");
    System.out.println("Press 4 : Update Employee Details");
    System.out.println("Press 5 : Exit\n");
  }
}

// -------------------- Lohith Marneni --------------------
class EmployDetail {
  String name, father_name, email, position, employ_id, employ_salary, employ_contact;

  public void getInfo() {
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter Employee ID: ");
    employ_id = sc.nextLine();
    System.out.print("Enter Name: ");
    name = sc.nextLine();
    System.out.print("Enter Father's Name: ");
    father_name = sc.nextLine();
    System.out.print("Enter Email: ");
    email = sc.nextLine();
    System.out.print("Enter Position: ");
    position = sc.nextLine();
    System.out.print("Enter Contact: ");
    employ_contact = sc.nextLine();
    System.out.print("Enter Salary: ");
    employ_salary = sc.nextLine();
    sc.close();
  }

  public String formatDetails() {
    return employ_id + "," + name + "," + father_name + "," + email + "," + position + "," + employ_contact + ","
        + employ_salary;
  }

  public static String[] headers() {
    return new String[] { "ID", "Name", "Father's Name", "Email", "Position", "Contact", "Salary" };
  }
}

class Employee_Add {
  public void addToFile() {
    Scanner sc = new Scanner(System.in);
    EmployDetail emp = new EmployDetail();
    emp.getInfo();

    try (BufferedWriter writer = new BufferedWriter(new FileWriter("employee.txt", true))) {
      writer.write(emp.formatDetails());
      writer.newLine();
      System.out.println("\nEmployee added successfully!");
    } catch (IOException e) {
      System.out.println("Error writing to file: " + e.getMessage());
    }
    sc.close();
  }
}

class Employee_Show {
  public void showEmployee(String id) {
    try (BufferedReader reader = new BufferedReader(new FileReader("employee.txt"))) {
      String line;
      boolean found = false;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts[0].equals(id)) {
          String[] headers = EmployDetail.headers();
          for (int i = 0; i < headers.length; i++) {
            System.out.println(headers[i] + ": " + parts[i]);
          }
          found = true;
          break;
        }
      }
      if (!found)
        System.out.println("Employee not found.");
    } catch (IOException e) {
      System.out.println("Error reading file.");
    }
  }
}

// --------------------S Rama Sesha Sai --------------------
class Employee_Remove {
  public void removeEmployee(String id) {
    File inputFile = new File("employee.txt");
    File tempFile = new File("temp.txt");

    boolean found = false;

    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

      String line;
      while ((line = reader.readLine()) != null) {
        if (!line.startsWith(id + ",")) {
          writer.write(line);
          writer.newLine();
        } else {
          found = true;
        }
      }

    } catch (IOException e) {
      System.out.println("Error processing file.");
    }

    if (inputFile.delete() && tempFile.renameTo(inputFile)) {
      if (found)
        System.out.println("Employee removed successfully.");
      else
        System.out.println("Employee not found.");
    } else {
      System.out.println("Could not update file.");
    }
  }
}

class CodeExit {
  public void exit() {
    System.out.println("\nThank you for using the EMS. Goodbye!");
    System.exit(0);
  }
}

// -------------------- T Rohan --------------------
class Employee_Update {
  public void updateEmployee(String id, String oldVal, String newVal) {
    File inputFile = new File("employee.txt");
    File tempFile = new File("temp.txt");

    boolean found = false;

    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

      String line;
      while ((line = reader.readLine()) != null) {
        if (line.startsWith(id + ",")) {
          found = true;
          line = line.replace(oldVal, newVal);
        }
        writer.write(line);
        writer.newLine();
      }

    } catch (IOException e) {
      System.out.println("Error updating file: " + e.getMessage());
      return;
    }

    if (inputFile.delete() && tempFile.renameTo(inputFile)) {
      if (found) {
        System.out.println("Employee updated successfully.");
      } else {
        System.out.println("Employee not found.");
      }
    } else {
      System.out.println("Could not update file.");
    }
  }
}

public class EmployManagementSystem {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    MainMenu menu = new MainMenu();
    Employee_Add add = new Employee_Add();
    Employee_Show show = new Employee_Show();
    Employee_Remove remove = new Employee_Remove();
    Employee_Update update = new Employee_Update();
    CodeExit exit = new CodeExit();

    while (true) {
      menu.menu();
      System.out.print("Enter choice: ");
      int choice = Integer.parseInt(sc.nextLine());

      switch (choice) {
        case 1 -> add.addToFile();
        case 2 -> {
          System.out.print("Enter Employee ID: ");
          String id = sc.nextLine();
          show.showEmployee(id);
        }
        case 3 -> {
          System.out.print("Enter Employee ID to remove: ");
          String id = sc.nextLine();
          remove.removeEmployee(id);
        }
        case 4 -> {
          System.out.print("Enter Employee ID to update: ");
          String id = sc.nextLine();
          System.out.print("Enter current value to replace: ");
          String oldVal = sc.nextLine();
          System.out.print("Enter new value: ");
          String newVal = sc.nextLine();
          update.updateEmployee(id, oldVal, newVal);
        }
        case 5 -> exit.exit();
        default -> System.out.println("Invalid choice!");
      }

      System.out.print("\nPress Enter to continue...");
      sc.nextLine();
      sc.close();
    }
  }
}
