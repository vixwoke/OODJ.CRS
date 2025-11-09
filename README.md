# OODJ.CRS
Java-based Course Recovery System (CRS) for managing student recovery plans, eligibility checks, and academic reports. Built with OOP principles and NetBeans Swing GUI.

# 📘 Course Recovery System (CRS)

## 🧾 Project Overview
**Course Recovery System (CRS)** is a Java-based desktop application designed to help educational institutions manage students who need to recover failed or incomplete courses.  
It ensures that students can continue their academic journey without delaying graduation by providing a structured recovery plan and tracking system.

This project was developed as part of **CT038-3-2 Object Oriented Development with Java (OODJ)** coursework at **Asia Pacific University (APU)**.

---

## 🧠 Objective
To develop an Object-Oriented Java application that:

- Implements OOP principles (Encapsulation, Inheritance, Polymorphism, and Abstraction)  
- Demonstrates modular and maintainable design using packages and classes  
- Provides a GUI-driven interface built in **NetBeans (Swing)** for better usability  

---

## 👥 Target Users
- **Academic Officers:** Manage course recovery eligibility, progress, and reports  
- **Course Administrators:** Monitor student recovery milestones and academic improvement  
- **Students:** View recovery tasks, deadlines, and updated grades  

---

## ⚙️ Main Features

### 🧩 1. User Management
- Add / Update / Deactivate user accounts  
- Login / Logout with timestamp logging  
- Role-based access control  
- Password reset and recovery  

### 📚 2. Course Recovery Plan
- Display failed modules and components  
- Add / Edit / Remove recovery recommendations  
- Set recovery milestones and deadlines  
- Track progress and grading for recovery tasks  

### 🧮 3. Eligibility Check and Enrolment
- Verify student eligibility to progress (CGPA ≥ 2.0, ≤ 3 failed courses)  
- Display list of students who failed progression criteria  
- Allow enrolment after eligibility confirmation  

### 📊 4. Academic Performance Reporting
- Generate performance reports by semester and year  
- Export reports as PDF using **iText PDF**  
- Include grades, CGPA calculation, and recommendations  

### ✉️ 5. Email Notifications
- Send automatic email reminders and confirmations  
- Notify students of new recovery plans and report availability  
- Implemented using **JavaMail API**

---

## 🏗️ System Design
- GUI designed using **NetBeans Swing GUI Builder**  
- OOP concepts applied in class relationships and modular packages  
- Data stored using text/binary files  
- Integration of third-party libraries:
  - 📧 JavaMail API  
  - 📄 iText PDF  

---

## 🖼️ Use Case Diagram
*(Reference to uploaded diagram)*  
![Use Case Diagram](usecasediagram.jpg)

---

## 🧪 Example Data Files

| File | Description |
|------|--------------|
| `student_information.csv` | Contains student records used for eligibility checks |
| `course_information-APU-LP-0650.csv` | Contains course data and grading details |

---

## 🧰 Development Environment

| Tool | Description |
|------|--------------|
| **Language** | Java |
| **IDE** | Apache NetBeans |
| **Version Control** | Git & GitHub |
| **Libraries** | JavaMail, iTextPDF |
| **Data Storage** | Text/Binary Files |

---

## 🧑‍💻 Team Collaboration
This project follows a **Git-based workflow**:

1. The team leader initializes and pushes the main repository to GitHub.  
2. Members clone the same repository to their PCs.  
3. Each member works on a separate branch (feature-based workflow).  
4. Changes are committed, pushed, and merged into the main branch after review.  

---

## 🚀 How to Run

1. Clone this repository:
   ```bash
   git clone https://github.com/vixwoke/OODJ.CRS.git

## Yes this readme file was created with ChatGPT. You know I don't want to spend time to create this don't you?
