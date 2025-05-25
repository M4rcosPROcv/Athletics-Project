# 🎓 Student Athlete Management System (JavaFX Edition)

This is the original version of the Student Athlete Management System — a desktop application built with **JavaFX** and **MySQL**. It was designed to help manage student athlete records such as academic performance, eligibility status, and team assignments.

> ⚠️ This project is no longer under active development and is being replaced by a modern Android + Spring Boot version. However, this version remains a useful reference for JavaFX desktop development.

---

## 🧩 Features

- 👤 Add, edit, and delete student athlete records
- 🗂️ Search and filter athletes by sport or academic status
- 🔐 Simple local login authentication
- 💾 Connected to MySQL using raw JDBC
- 🎨 Styled using JavaFX + CSS

---

## 🛠️ Tech Stack

- **JavaFX** — User Interface
- **JDBC** — Database connection
- **MySQL** — Relational database backend
- **CSS** — Custom UI styling

---

## 📁 Project Structure

```plaintext
src/
└── main/
    ├── java/com/
    │   ├── DAO/                    # Data Access Objects and interfaces
    │   │   ├── Database.java
    │   │   ├── StudentDAO.java
    │   │   ├── StudentDAOIntf.java
    │   │   └── UserDAO.java
    │   ├── models/
    │   │   └── Student.java        # Student entity class
    │   ├── portal/
    │   │   ├── App.java            # Application launcher
    │   │   └── Main.java           # Main entry point
    │   └── StudentUI/
    │       ├── DashboardWindow.java
    │       ├── ForgotPasswordScene.java
    │       ├── LoginWindow.java
    │       └── SceneController.java
    └── resources/
        ├── stylesheets/
        │   ├── dashboardWindowDark.css
        │   ├── dashboardWindowLight.css
        │   ├── forgotPasswordWindow.css
        │   └── loginWindow.css
        ├── config.json             # Config file for DB or app settings
        └── icon.png                # Application icon
```
---

## 🧪 Example Functionalities

- Add a new athlete:
  - First name, last name, sport, GPA, eligibility
- Login system (basic credential check from `users` table)
- Editable data tables
- GUI with multiple views: Dashboard, Athlete List, Add New, etc.

---

## ⚙️ Setup Instructions

1. Install Java (JDK 11 or later) and MySQL Server
2. Create a MySQL database:
   ```sql
   CREATE DATABASE student_athletes;
3. Import the provided schema (in /resources/sql/ if included)

4. Update DB connection settings in: /config.json
5. Build and run the app using your favorite IDE (e.g., IntelliJ, Eclipse)

---

📸 Screenshots

![image](https://github.com/user-attachments/assets/1fc6ac12-5056-444a-b176-f6e269d74a38)
![image](https://github.com/user-attachments/assets/7c42bac5-7574-4a07-8d8f-3b2e428b8442)
![image](https://github.com/user-attachments/assets/d37690fd-77cf-4781-abe4-d20835a212d0)
![image](https://github.com/user-attachments/assets/faed919a-5c56-474a-b389-2afdb7fc2a5e)
![image](https://github.com/user-attachments/assets/296c2616-a960-43f8-8c95-31641fdd1f5a)

---

🧠 Learning Objectives
This version of the project was originally created to learn:

- Desktop UI design with JavaFX
- Working with JDBC for database interaction
- Building simple CRUD apps using SQL and Java
- CSS styling in JavaFX apps

---

📌 Status
This JavaFX version is no longer the main focus of development. It has been replaced by a Spring Boot backend and an Android mobile app, currently being built in a private repository

---

📄 License
MIT License
