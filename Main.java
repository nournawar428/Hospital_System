import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);
    static HospitalSystem system = new HospitalSystem();

    static Admin admin;

    public static void main(String[] args) {

        admin = new Admin("A001", "System Admin", "admin", "1234", "01000000000");
        System.out.println("Loading data... Please wait.");
        FileManager.loadDoctors(system.getDoctors());
        FileManager.loadPatients(system.getPatients(), system.getDoctors());

        FileManager.loadAppointments(HospitalSystem.getAppointments(), system.getPatients(), system.getDoctors());
        System.out.println("Data loaded successfully!");
       
        boolean exit = false;

        while (!exit) {

            System.out.println("\n========== Hospital Management System ==========");
            System.out.println("1. Login as Admin");
            System.out.println("2. Login as Doctor");
            System.out.println("3. Login as Patient");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            String choice = input.nextLine();

            switch (choice) {
                case "1":
                    adminMenu();
                    break;
                case "2":
                    doctorMenu();
                    break;
                case "3":
                    patientMenu();
                    break;
                case "4":
                    exit = true;
                    System.out.println("Thank you for using the system.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ADMIN MENU
    public static void adminMenu() {

        System.out.print("Enter Username: ");
        String username = input.nextLine();

        System.out.print("Enter Password: ");
        String password = input.nextLine();

        if (!admin.getUsername().equals(username)
                || !admin.getPassword().equals(password)) {

            System.out.println("Invalid admin credentials.");
            return;
        }

        boolean back = false;

        while (!back) {

            System.out.println("\n========== Admin Menu ==========");
            System.out.println("1. Add Doctor");
            System.out.println("2. Add Patient");
            System.out.println("3. Assign Patient To Doctor");
            System.out.println("4. View All Doctors");
            System.out.println("5. View All Patients");
            System.out.println("6. Search Doctor");
            System.out.println("7. Search Patient");
            System.out.println("8. Create Appointment");
            System.out.println("9. View All Appointments");
            System.out.println("10. Generate Reports");
            System.out.println("11. Save Data");
            System.out.println("12. Back");

            System.out.print("Enter your choice: ");

            String choice = input.nextLine();

            switch (choice) {

                case "1":
                    addDoctor();
                    break;

                case "2":
                    addPatient();
                    break;

                case "3":
                    assignPatient();
                    break;

                case "4":
                    system.ViewAllDoctors();
                    break;

                case "5":
                    system.ViewAllPatients();
                    break;

                case "6":
                    searchDoctor();
                    break;

                case "7":
                    searchPatient();
                    break;

                case "8":
                    createAppointment();
                    break;

                case "9":
                    system.ViewAllAppointments();
                    break;
                case "10":
                    reportsMenu();
                    break;

                case "11":

                    FileManager.saveDoctors(system.getDoctors());

                    FileManager.savePatients(system.getPatients());

                    FileManager.saveAppointments(
                            HospitalSystem.getAppointments());

                    FileManager.saveUsers(
                            system.getDoctors(),
                            system.getPatients(),
                            admin);

                    System.out.println("All data saved successfully.");
                    break;

                case "12":
                    back = true;
                    break;
            }
        }
    }

    // DOCTOR MENU
    public static void doctorMenu() {

        System.out.print("Enter Username: ");
        String username = input.nextLine();

        System.out.print("Enter Password: ");
        String password = input.nextLine();

        Doctor doctor = null;

        for (Doctor d : system.getDoctors()) {

            if (d.getUsername().equals(username)
                    && d.getPassword().equals(password)) {

                doctor = d;
                break;
            }
        }

        if (doctor == null) {

            System.out.println("Invalid doctor credentials.");
            return;
        }
        boolean back = false;

        while (!back) {

            System.out.println("\n========== Doctor Menu ==========");
            System.out.println("1. View Profile");
            System.out.println("2. View Assigned Patients");
            System.out.println("3. View My Appointments");
            System.out.println("4. Update Appointment Status");
            System.out.println("5. Back");

            System.out.print("Enter your choice: ");

            String choice = input.nextLine();

            switch (choice) {

                case "1":
                    doctor.viewProfile();
                    break;

                case "2":
                    doctor.viewAssignedPatients();
                    break;
                case "3":
                    doctor.viewMyAppointments();
                    break;
                case "4":

                    System.out.print("Enter Appointment ID: ");
                    String appId = input.nextLine();

                    System.out.print("Enter New Status: ");
                    String status = input.nextLine();

                    HospitalSystem.updateAppointment(
                            appId,
                            status,
                            doctor);

                    break;

                case "5":
                    back = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // PATIENT MENU
    public static void patientMenu() {

        System.out.print("Enter Username: ");
        String username = input.nextLine();

        System.out.print("Enter Password: ");
        String password = input.nextLine();

        Patient patient = null;

        for (Patient p : system.getPatients()) {

            if (p.getUsername().equals(username)
                    && p.getPassword().equals(password)) {

                patient = p;
                break;
            }
        }

        if (patient == null) {

            System.out.println("Invalid patient credentials.");
            return;
        }
        boolean back = false;

        while (!back) {

            System.out.println("========== Patient Menu ==========");
            System.out.println("1. View Profile");
            System.out.println("2. View Assigned Doctor");
            System.out.println("3. View My Appointments");
            System.out.println("4. Book Appointment");
            System.out.println("5. Cancel Appointment");
            System.out.println("6. Back");

            System.out.print("Enter your choice: ");

            String choice = input.nextLine();

            switch (choice) {

                case "1":
                    patient.viewProfile();
                    break;
                case "2":
                    if (patient.getAssignedDoctor() != null) {
                        System.out.println("Assigned Doctor: " + patient.getAssignedDoctor().getName());
                    } else {
                        System.out.println("No doctor assigned yet.");
                    }
                    break;
                case "3":
                    patient.viewMyAppointments();
                    break;
                case "4":

                    System.out.print("Enter Appointment Date: ");
                    String date = input.nextLine();

                    System.out.print("Enter Appointment Time: ");
                    String time = input.nextLine();

                    if (patient.getAssignedDoctor() == null) {

                        System.out.println("Patient has no assigned doctor.");
                        break;
                    }

                    HospitalSystem.bookAppointment(
                            patient,
                            patient.getAssignedDoctor(),
                            date,
                            time);

                    break;

                case "5":

                    System.out.print("Enter Appointment ID: ");
                    String appId = input.nextLine();

                    HospitalSystem.cancelAppointment(
                            appId,
                            patient);

                    break;

                case "6":
                    back = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // REPORTS MENU
    public static void reportsMenu() {

        system.generateFullReport();
        System.out.println("\nPress Enter to return to Main Menu...");
        input.nextLine();
    }

    // ADD DOCTOR
    public static void addDoctor() {

        System.out.print("Enter Doctor ID: ");
        String id = input.nextLine();

        System.out.print("Enter Doctor Name: ");
        String name = input.nextLine();

        System.out.print("Enter Username: ");
        String username = input.nextLine();

        System.out.print("Enter Password: ");
        String password = input.nextLine();

        System.out.print("Enter Specialization: ");
        String specialization = input.nextLine();

        System.out.print("Enter Department: ");
        String department = input.nextLine();

        System.out.print("Enter Phone Number: ");
        String phone = input.nextLine();

        Doctor doctor = new Doctor(
                id,
                name,
                username,
                password,
                specialization,
                department,
                phone);

        system.add_doctor(doctor);
    }

    // ADD PATIENT
    public static void addPatient() {

        try {

            System.out.print("Enter Patient ID: ");
            String id = input.nextLine();

            System.out.print("Enter Patient Name: ");
            String name = input.nextLine();

            System.out.print("Enter Username: ");
            String username = input.nextLine();

            System.out.print("Enter Password: ");
            String password = input.nextLine();

            System.out.print("Enter Age: ");

            int age = Integer.parseInt(input.nextLine());
            if (age <= 0) {
                System.out.println("Invalid age.");
                return;
            }

            System.out.print("Enter Gender: ");
            String gender = input.nextLine();

            System.out.print("Enter Phone Number: ");
            String phone = input.nextLine();

            Patient patient = new Patient(
                    id,
                    name,
                    username,
                    password,
                    age,
                    gender,
                    phone);

            system.add_patient(patient);

        } catch (Exception e) {

            System.out.println("Invalid age input.");
        }
    }

    // ASSIGN PATIENT
    public static void assignPatient() {

        System.out.print("Enter Patient ID: ");
        String patientId = input.nextLine();

        System.out.print("Enter Doctor ID: ");
        String doctorId = input.nextLine();

        system.AssignPatientToDoctor(patientId, doctorId);
    }

    // SEARCH DOCTOR
    public static void searchDoctor() {

        System.out.print("Enter Doctor ID: ");
        String id = input.nextLine();

        Doctor doctor = system.FindDoctor(id);

        if (doctor != null) {
            doctor.viewProfile();
        } else {
            System.out.println("Doctor not found.");
        }
    }

    // SEARCH PATIENT
    public static void searchPatient() {

        System.out.print("Enter Patient ID: ");
        String id = input.nextLine();

        Patient patient = system.FindPatient(id);

        if (patient != null) {
            patient.viewProfile();
        } else {
            System.out.println("Patient not found.");
        }
    }

    // CREATE APPOINTMENT
    public static void createAppointment() {

        System.out.print("Enter Patient ID: ");
        String patientId = input.nextLine();

        System.out.print("Enter Doctor ID: ");
        String doctorId = input.nextLine();

        Patient patient = system.FindPatient(patientId);
        Doctor doctor = system.FindDoctor(doctorId);

        if (patient == null || doctor == null) {

            System.out.println("Patient or Doctor not found.");
            return;
        }

        System.out.print("Enter Appointment Date: ");
        String date = input.nextLine();

        System.out.print("Enter Appointment Time: ");
        String time = input.nextLine();
        
        HospitalSystem.bookAppointment(
                patient,
                doctor,
                date,
                time);
    }

}