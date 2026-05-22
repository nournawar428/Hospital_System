import java.util.ArrayList;

public class Patient extends User {
    private int age;
    private String gender;
    private Doctor assignedDoctor;

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }
    
    private ArrayList<Appointment> myAppointments = new ArrayList<>(); 

    public Patient(String id, String name, String username, String password, int age, String gender, String phoneNumber) {
        super(id, name, username, password, phoneNumber);
        this.age = age;
        this.gender = gender;
    }

    public void addAppointment(Appointment app) {
        this.myAppointments.add(app);
    }

    public void viewMyAppointments() {
        if (myAppointments.isEmpty()) {
            System.out.println("No appointments found for you.");
        } else {
            System.out.println("--- Your Appointments ---");
            for (Appointment app : myAppointments) {
                System.out.println("ID: " + app.getId() + " | Date: " + app.getDate() + 
                                   " | Time: " + app.getTime() + " | Status: " + app.getStatus());
            }
        }
    }

    @Override
    public void viewProfile() {
        System.out.println("--- Patient Profile ---");
        super.viewProfile();
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        if (assignedDoctor != null) {
            System.out.println("Assigned Doctor: " + assignedDoctor.getName());
        }
    }

    public void bookAppointment(String date, String time) {
        if (assignedDoctor == null) {
            System.out.println("Please select a doctor first.");
        } else {
            HospitalSystem.bookAppointment(this, assignedDoctor, date, time);
        }
    }

    public void cancelMyAppointment(String appointmentId) {
        HospitalSystem.cancelAppointment(appointmentId, this);
    }

    public void setAssignedDoctor(Doctor assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }

    public Doctor getAssignedDoctor() {
        return assignedDoctor;
    }

    public ArrayList<Appointment> getMyAppointments() {
        return myAppointments;
    }
}