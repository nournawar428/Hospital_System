# Hospital Appointments Management System 🏥

A comprehensive, console-based Java application built to streamline medical center operations, coordinate user role workflows, and manage doctor-patient appointment scheduling with real-time file-based persistence.

---

## 🚀 Key System Features

### 1. Unified Multi-Role Authentication (OOP Architecture)
* **Admin Role:** Exercises complete administrative privilege over the system. Capable of adding doctors, registering patients, handling direct patient-to-doctor assignments, scheduling appointments, conducting systematic database searches, and compiling operations reports.
* **Doctor Role:** Allows medical staff to review personal profiles, evaluate their list of assigned patients, track scheduled sessions, and systematically modify appointment states (`Confirmed`, `Completed`, `Cancelled`).
* **Patient Role:** Empowers patients to verify personal records, consult their assigned doctor's details, book dynamic slots with their designated practitioner, or cancel existing arrangements.

### 2. Business Logic Validation Engine
* **No Assignment, No Booking:** Restricts any patient from initiating an appointment unless officially assigned to a doctor first.
* **Anti-Double Booking:** Validates scheduling queues to prevent a doctor from having two parallel appointments at the same date and time.
* **Status Continuity Rules:** Enforces state rules, such as blocking a previously `Cancelled` appointment from ever being updated to `Completed`.
* **Data Completeness:** Guarantees input parameter compliance by ensuring that appointment dates and times cannot be submitted empty.

### 3. Reporting & Analytics Dashboard
* Aggregates key system metrics including global counts for active doctors and patients.
* Categorizes appointment metrics dynamically by individual status logs.
* Runs sorting logic to extract the **Top 3 Doctors** possessing the highest volume of appointments.

---

## 🛠️ Tech Stack & OOP Paradigms Applied

* **Language:** Java (JDK 8+)
* **Inheritance & Polymorphism:** Leveraged an abstract `User` superclass extended across concrete behavioral subclasses (`Admin`, `Doctor`, `Patient`).
* **Encapsulation:** Absolute state insulation utilizing strict private access fields exposed safely via tailored getter/setter APIs.
* **File Data Persistence:** Robust infrastructure deploying `BufferedReader` and `BufferedWriter` loops to read/write structured text files instantly upon system triggers.
* **Exception Handling:** Structured `try-catch` safety blocks managing `FileNotFoundException` and `IOException` occurrences to achieve graceful operational recovery when text logs are empty or missing.

---

## 📂 Repository Structure

```text
├── Main.java              # System bootstrap & multi-level console menu UI
├── User.java              # Abstract base class enforcing core user identities
├── Admin.java             # Admin behavior routines
├── Doctor.java            # Medical practitioner functionalities & patient tracking
├── Patient.java           # Patient actions & automated scheduling proxies
├── Appointment.java       # Pure entity model encapsulating a booking entry
├── HospitalSystem.java    # Central controller managing core data structures & business logic
├── FileManager.java       # High-performance File I/O middleware
├── doctors.txt            # Local relational flat-file database for Doctor entities
├── patients.txt           # Local relational flat-file database for Patient entities
├── appointments.txt       # Unified scheduling transaction ledger
└── users.txt              # Consolidated login registry storing credentials and authorization scopes
