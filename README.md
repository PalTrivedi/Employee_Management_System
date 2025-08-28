# Employee Management System

A comprehensive Java-based Employee Management System that allows administrators to manage employee records efficiently. The system provides features for adding, updating, deleting, and searching employee information in a user-friendly console interface.

## ✨ Features

- **Employee Management**
  - Add new employees with details
  - Update existing employee information
  - Delete employee records
  - Search for employees by ID or name
  - View all employee records

- **Data Storage**
  - Persistent data storage using text files
  - Data backup and recovery

- **User Interface**
  - Console-based menu system
  - Input validation
  - Clear data presentation

## 🚀 Prerequisites

- Java Development Kit (JDK) 8 or higher
- MySQL Connector/J (included in the `lib` folder)
- MySQL Server (for database operations)

## 📂 Project Structure

```
Employee_Management_System/
├── bin/              # Compiled .class files
├── lib/              # External libraries (MySQL Connector/J)
├── src/              # Java source files
│   └── Pack/         # Package containing all Java classes
│       ├── Admin.java    # Admin interface implementation
│       ├── App.java      # Main application class
│       ├── BST.java      # Binary Search Tree implementation
│       ├── Employee.java # Employee class definition
│       └── Node.java     # Node class for BST
├── AdminData.txt     # Admin credentials storage
└── employee_management_system.sql  # Database schema
```

## 🛠️ Setup Instructions

1. **Database Setup**
   - Import the database schema from `employee_management_system.sql` to your MySQL server
   - Update database connection details in the source code if needed

2. **Compilation**
   ```bash
   javac -d bin -cp "lib/*" src/Pack/*.java
   ```

3. **Execution**
   ```bash
   java -cp "bin;lib/*" Pack.App
   ```

## 🧑‍💻 Usage

1. **Login**
   - Use the default admin credentials (username: admin, password: admin) or create a new admin account

2. **Main Menu**
   - **1. Add Employee**: Add a new employee to the system
   - **2. View Employees**: List all employees
   - **3. Search Employee**: Find an employee by ID or name
   - **4. Update Employee**: Modify employee details
   - **5. Delete Employee**: Remove an employee record
   - **6. Exit**: Close the application

## 📝 Notes

- The system uses a binary search tree (BST) for efficient data management
- All employee data is persisted to the database
- Regular backups are recommended for data safety

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
