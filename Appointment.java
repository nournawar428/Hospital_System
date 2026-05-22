public class Appointment {

    private String id;

    private Patient patient;

    private Doctor doctor;

    private String date;

    private String time;

    private String status;

    public Appointment(String id,Patient patient, Doctor doctor, String date, String time, String status) {

        this.id = id;

        this.patient = patient;

        this.doctor = doctor;

        this.date = date;

        this.time = time;

        this.status = status;

    }

    public String getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void updatestatus(String newStatus) {

        status = newStatus;

        System.out.println("Appointment status updated to: " + status);

    }

    public String record() {

        return "Appointment booked!\n " + id + "," + patient.getId() + "," + doctor.getId() + "," + date + "," + time
                + "," + status;
    }

    public void cancel() {

        status = "Cancelled";

        System.out.println("Appointment cancelled.");

    }

}