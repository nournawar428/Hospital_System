public class Admin extends User {

    public Admin(
            String id,
            String name,
            String username,
            String password,
            String phoneNumber) {

        super(id, name, username, password, phoneNumber);
    }

    public void createAppointment(
            String date,
            String time,
            Patient patient,
            Doctor doctor) {

        if (patient.getAssignedDoctor() == null) {

            System.out.println("Patient does not have an assigned doctor.");
            return;
        }

        if (!doctor.getId().equals(
                patient.getAssignedDoctor().getId())) {

            System.out.println("Patient is not assigned to this doctor.");
            return;
        }

        HospitalSystem.bookAppointment(
                patient,
                doctor,
                date,
                time
        );
    }
}