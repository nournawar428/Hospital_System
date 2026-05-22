import java.util.ArrayList;

public class Doctor extends User {
private ArrayList<Appointment> myAppointments = new ArrayList<>();
    private String specialization;
    private String department;
    private ArrayList<String> assignedPatients;

    public Doctor(
            String id,
            String name,
            String username,
            String password,
            String specialization,
            String department,
            String phoneNumber) {

        super(id, name, username, password, phoneNumber);

        this.specialization = specialization;
        this.department = department;
        this.assignedPatients = new ArrayList<>();
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getDepartment() {
        return department;
    }

    public ArrayList<String> getAssignedPatients() {
        return assignedPatients;
    }

    public void addPatient(String patientName) {
        assignedPatients.add(patientName);
    }

    @Override
    public void viewProfile() {
        super.viewProfile();

        System.out.println("Specialization: " + specialization);
        System.out.println("Department: " + department);
    }
    public void addAppointment(Appointment app) {
        this.myAppointments.add(app);
    }

    public void viewMyAppointments() {
        System.out.println("--- Doctor's Appointments ---");
        if (myAppointments.isEmpty()) {
            System.out.println("No appointments scheduled.");
            return;
        }
        for (Appointment app : myAppointments) {
            System.out.println("ID: " + app.getId() + " | Patient: " + app.getPatient().getName() +
                               " | Date: " + app.getDate() + " | Time: " + app.getTime() + 
                               " | Status: " + app.getStatus());
        }
    }
    public void viewAssignedPatients() {

        System.out.println("Assigned Patients:");

        if (assignedPatients.isEmpty()) {
            System.out.println("No patients assigned.");
            return;
        }

        for (String patient : assignedPatients) {
            System.out.println(patient);
        }
    }

    public void updateAppointment(
            String appointmentId,
            String newStatus) {

        HospitalSystem.updateAppointment(
                appointmentId,
                newStatus,
                this
        );
    }
}