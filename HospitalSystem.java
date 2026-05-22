import java.util.ArrayList;

public class HospitalSystem {

    private ArrayList<Doctor> doctors = new ArrayList<>();
    private ArrayList<Patient> patients = new ArrayList<>();

    private static ArrayList<Appointment> appointments = new ArrayList<>();

    static int appcounter = 1;

    // ADD DOCTOR
    public void add_doctor(Doctor d) {

        for (Doctor doctor : doctors) {
            if (doctor.getId().equals(d.getId())) {
                System.out.println("Doctor ID already exists.");
                return;
            }
        }

        doctors.add(d);
        System.out.println("Doctor added successfully");

        FileManager.saveDoctors(doctors);
    }

    // ADD PATIENT
    public void add_patient(Patient p) {

        for (Patient patient : patients) {
            if (patient.getId().equals(p.getId())) {
                System.out.println("Patient ID already exists.");
                return;
            }
        }

        patients.add(p);
        System.out.println("Patient added successfully");

        FileManager.savePatients(patients);
    }

    // FIND DOCTOR
    public Doctor FindDoctor(String id) {

        for (Doctor d : doctors) {
            if (d.getId().equals(id)) {
                return d;
            }
        }

        return null;
    }

    // FIND PATIENT
    public Patient FindPatient(String id) {

        for (Patient p : patients) {
            if (p.getId().equals(id)) {
                return p;
            }
        }

        return null;
    }

    // ASSIGN PATIENT
    public void AssignPatientToDoctor(String patientId, String doctorId) {

        Patient p = FindPatient(patientId);
        Doctor d = FindDoctor(doctorId);

        if (p != null && d != null) {

            p.setAssignedDoctor(d);
            d.addPatient(p.getName());

            System.out.println("Patient assigned successfully");
            FileManager.savePatients(patients);

        } else {
            System.out.println("Patient or Doctor not found!");
        }
    }

    // VIEW ALL DOCTORS
    public void ViewAllDoctors() {

        System.out.println("---- All Doctors ----");

        for (Doctor d : doctors) {
            d.viewProfile();
            System.out.println("------------------------");
        }
    }

    // VIEW ALL PATIENTS
    public void ViewAllPatients() {
        System.out.println("---- All Patients ----");

        if (patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        for (Patient p : patients) {
            p.viewProfile();
            System.out.println("------------------------");
        }
    }

    // VIEW ALL APPOINTMENTS
    public void ViewAllAppointments() {
        System.out.println("---- All Appointments ----");

        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }

        for (Appointment a : appointments) {
            System.out.println("ID: " + a.getId() +
                    " | Patient: " + a.getPatient().getName() +
                    " | Doctor: " + a.getDoctor().getName() +
                    " | Date: " + a.getDate() +
                    " | Time: " + a.getTime() +
                    " | Status: " + a.getStatus());
        }
    }

    // BOOK APPOINTMENT
    public static void bookAppointment(
            Patient patient,
            Doctor doctor,
            String date,
            String time) {

        if (date == null || date.trim().isEmpty()) {
            System.out.println("Date is required to book the appointment.");
            return;
        }

        if (time == null || time.trim().isEmpty()) {
            System.out.println("Time is required to book the appointment.");
            return;
        }

        if (patient.getAssignedDoctor() == null) {
            System.out.println("Patient must be assigned to a doctor first.");
            return;
        }

        if (!checkValidity(doctor, date, time)) {
            return;
        }

        String id = "A" + String.format("%03d", appcounter++);

        Appointment appointment = new Appointment(
                id,
                patient,
                doctor,
                date,
                time,
                "Confirmed");

        appointments.add(appointment);
        patient.addAppointment(appointment);
        doctor.addAppointment(appointment);
        System.out.println(appointment.record());
        FileManager.saveAppointments(appointments);
    }

    // CHECK VALIDITY
    public static boolean checkValidity(
            Doctor doctor,
            String date,
            String time) {

        for (Appointment a : appointments) {

            if (a.getDoctor().getId().equals(doctor.getId())
                    &&
                    a.getDate().equals(date)
                    &&
                    a.getTime().equals(time)) {

                System.out.println("This time is not available.");
                return false;
            }
        }

        return true;
    }

    // UPDATE APPOINTMENT
    public static void updateAppointment(
            String appointmentId,
            String newStatus,
            Doctor doctor) {

        Appointment appointment = null;

        for (Appointment a : appointments) {

            if (a.getId().equals(appointmentId)) {

                if (!a.getDoctor().getId().equals(doctor.getId())) {

                    System.out.println("You cannot update another doctor's appointment.");
                    return;
                }

                appointment = a;
                break;
            }
        }

        if (appointment == null) {
            System.out.println("Appointment not found.");
            return;
        }

        if (appointment.getStatus().equalsIgnoreCase("Cancelled")) {

            System.out.println("Cannot update cancelled appointment.");
            return;
        }

        if (!newStatus.equalsIgnoreCase("Confirmed") &&
                !newStatus.equalsIgnoreCase("Completed") &&
                !newStatus.equalsIgnoreCase("Cancelled")) {

            System.out.println("Invalid status.");
            return;
        }

        appointment.setStatus(newStatus);

        System.out.println(
                "Appointment "
                        + appointmentId
                        + " updated to "
                        + newStatus);

        FileManager.saveAppointments(appointments);
    }

    // CANCEL APPOINTMENT
    public static void cancelAppointment(
            String appointmentId,
            Patient patient) {

        Appointment appointment = null;

        for (Appointment a : appointments) {

            if (a.getId().equals(appointmentId)) {

                if (!a.getPatient().getId().equals(patient.getId())) {

                    System.out.println("You cannot cancel another patient's appointment.");
                    return;
                }

                appointment = a;
                break;
            }
        }

        if (appointment == null) {

            System.out.println("Appointment not found.");
            return;
        }

        if (appointment.getStatus().equals("Cancelled")) {

            System.out.println("Appointment already cancelled.");
            return;
        }

        appointment.cancel();

        FileManager.saveAppointments(appointments);

        System.out.println("Appointment cancelled successfully.");
    }

    // REPORTS
    public void generateFullReport() {
        System.out.println("--- Hospital System Report ---");

        long confirmed = appointments.stream().filter(a -> a.getStatus().equalsIgnoreCase("Confirmed")).count();
        long completed = appointments.stream().filter(a -> a.getStatus().equalsIgnoreCase("Completed")).count();
        long cancelled = appointments.stream().filter(a -> a.getStatus().equalsIgnoreCase("Cancelled")).count();

        System.out.println("Total Confirmed: " + confirmed);
        System.out.println("Total Completed: " + completed);
        System.out.println("Total Cancelled: " + cancelled);
        System.out.println("Total Doctors: " + doctors.size());
        System.out.println("Total Patients: " + patients.size());
        System.out.println("\n--- Top 3 Doctors by Appointments ---");
        doctors.stream()
                .sorted((d1, d2) -> {
                    long count1 = appointments.stream().filter(a -> a.getDoctor().getId().equals(d1.getId())).count();
                    long count2 = appointments.stream().filter(a -> a.getDoctor().getId().equals(d2.getId())).count();
                    return Long.compare(count2, count1);
                })
                .limit(3)
                .forEach(d -> {
                    long count = appointments.stream().filter(a -> a.getDoctor().getId().equals(d.getId())).count();
                    System.out.println("Doctor: " + d.getName() + " | Appointments: " + count);
                });
    }

    // GETTERS

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public static ArrayList<Appointment> getAppointments() {
        return appointments;
    }
}