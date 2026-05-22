import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class FileManager {
    // Add this helper at the top of FileManager class
    private static String getPath(String filename) {
        String dir = System.getProperty("user.dir");
        return dir + java.io.File.separator + filename;
    }

    // LOAD DOCTORS
    public static void loadDoctors(ArrayList<Doctor> doctors) {

        try (BufferedReader br = new BufferedReader(new FileReader(getPath("doctors.txt")))) {

            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty())
                    continue;

                String[] data = line.split(",");

                if (data.length < 7)
                    continue;

                String id = data[0].trim();
                String name = data[1].trim();
                String username = data[2].trim();
                String password = data[3].trim();
                String specialization = data[4].trim();
                String department = data[5].trim();
                String phoneNumber = data[6].trim();

                Doctor doctor = new Doctor(id, name, username, password, specialization, department, phoneNumber);

                doctors.add(doctor);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Doctors file not found.");
        } catch (IOException e) {
            System.out.println("Error reading doctors file:" + e.getMessage());
        } 

    }

    // SAVE DOCTORS
    public static void saveDoctors(ArrayList<Doctor> doctors) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(getPath("doctors.txt")))) {

            for (Doctor d : doctors) {

                bw.write(
                        d.getId() + "," +
                                d.getName() + "," +
                                d.getUsername() + "," +
                                d.getPassword() + "," +
                                d.getSpecialization() + "," +
                                d.getDepartment() + "," +
                                d.getPhoneNumber());

                bw.newLine();
            }

            System.out.println("Doctors saved successfully.");

        } catch (IOException e) {
            System.out.println("Error writing doctors file.");
        }
    }

    // LOAD PATIENTS
    public static void loadPatients(ArrayList<Patient> patients, ArrayList<Doctor> doctors) {
        try (BufferedReader br = new BufferedReader(new FileReader(getPath("patients.txt")))) {

            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty())
                    continue;

                String[] data = line.split(",");

                if (data.length < 8)
                    continue;

                String id = data[0].trim();
                String name = data[1].trim();
                String username = data[2].trim();
                String password = data[3].trim();
                int age = Integer.parseInt(data[4].trim()); 
                String gender = data[5].trim();
                String phone = data[6].trim();
                String docId = data[7].trim();

                Patient patient = new Patient(id, name, username, password, age, gender, phone);

                for (Doctor d : doctors) {
                    if (d.getId().equals(docId)) {
                        patient.setAssignedDoctor(d);
                        d.addPatient(patient.getName());
                        break;
                    }
                }
                patients.add(patient);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Patients file not found.");
        } catch (IOException e) {
            System.out.println("Error reading patients file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid data format in patients file.");
        }
    }

    // SAVE PATIENTS
    public static void savePatients(ArrayList<Patient> patients) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(getPath("patients.txt")))) {

            for (Patient p : patients) {

                String docId;
                if (p.getAssignedDoctor() != null) {
                    docId = p.getAssignedDoctor().getId();
                } else {
                    docId = "null";
                }

                bw.write(p.getId() + "," +
                        p.getName() + "," +
                        p.getUsername() + "," +
                        p.getPassword() + "," +
                        p.getAge() + "," +
                        p.getGender() + "," +
                        p.getPhoneNumber() + "," +
                        docId);
                bw.newLine();

            }
            System.out.println("Patients saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving patients file.");
        }

    }

    // LOAD APPOINTMENTS
    public static void loadAppointments(ArrayList<Appointment> appointments, ArrayList<Patient> patients,
            ArrayList<Doctor> doctors) {
        try (BufferedReader br = new BufferedReader(new FileReader(getPath("appointments.txt")))) {

            String line;
            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty())
                    continue;

                String[] data = line.split(",");

                if (data.length < 6)
                    continue;

                String id = data[0].trim();
                String patientId = data[1].trim();
                String doctorId = data[2].trim();
                String date = data[3].trim();
                String time = data[4].trim();
                String status = data[5].trim();

                Patient patient = null;
                Doctor doctor = null;

                // FIND PATIENT
                for (Patient p : patients) {
                    if (p.getId().equals(patientId)) {
                        patient = p;
                        break;
                    }
                }

                // FIND DOCTOR
                for (Doctor d : doctors) {
                    if (d.getId().equals(doctorId)) {
                        doctor = d;
                        break;
                    }
                }

                if (patient != null && doctor != null) {

                    Appointment appointment = new Appointment(
                            id,
                            patient,
                            doctor,
                            date,
                            time,
                            status);

                    appointments.add(appointment);
                    patient.addAppointment(appointment);
                    doctor.addAppointment(appointment);

                    try {
                        int num = Integer.parseInt(id.substring(1));

                        if (num >= HospitalSystem.appcounter) {
                            HospitalSystem.appcounter = num + 1;
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid appointment ID format in appointments file.");
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Appointments file not found.");
        } catch (IOException e) {
            System.out.println("Error reading appointments file.");
        }
    }

    // SAVE APPOINTMENTS
    public static void saveAppointments(ArrayList<Appointment> appointments) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(getPath("appointments.txt")))) {

            for (Appointment a : appointments) {

                bw.write(a.getId() + "," +
                        a.getPatient().getId() + "," +
                        a.getDoctor().getId() + "," +
                        a.getDate() + "," +
                        a.getTime() + "," +
                        a.getStatus());
                bw.newLine();
            }
            System.out.println("Appointments saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving appointments file.");
        }
    }

    // LOAD USERS
    public static void loadUsers(
            ArrayList<User> allUsers,
            ArrayList<Doctor> doctors,
            ArrayList<Patient> patients,
            Admin admin) {

        try (BufferedReader br = new BufferedReader(new FileReader(getPath("users.txt")))) {

            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty())
                    continue;

                String[] data = line.split(",");

                if (data.length < 4)
                    continue;

                String username = data[0].trim();
                String password = data[1].trim();
                String role = data[2].trim();
                String id = data[3].trim();

                // ADMIN
                if (role.equalsIgnoreCase("Admin")) {
                    allUsers.add(admin);
                }
                // DOCTOR
                else if (role.equalsIgnoreCase("Doctor")) {

                    for (Doctor d : doctors) {
                        if (d.getId().equals(id)) {
                            allUsers.add(d);
                            break;
                        }
                    }
                }

                // PATIENT
                else if (role.equalsIgnoreCase("Patient")) {

                    for (Patient p : patients) {
                        if (p.getId().equals(id)) {
                            allUsers.add(p);
                            break;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Users file not found.");
        } catch (IOException e) {
            System.out.println("Error reading users file.");
        }
    }

    // SAVE USERS
    public static void saveUsers(ArrayList<Doctor> doctors, ArrayList<Patient> patients, Admin admin) {
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(getPath("users.txt")))) {

            // ADMIN
            if (admin != null) {
                bw.write(admin.getUsername() + "," + admin.getPassword() + ",Admin," + admin.getId());
                bw.newLine();
            }

            // DOCTOR
            for (Doctor d : doctors) {
                bw.write(d.getUsername() + "," + d.getPassword() + ",Doctor," + d.getId());
                bw.newLine();
            }

            // PATIENT
            for (Patient p : patients) {
                bw.write(p.getUsername() + "," + p.getPassword() + ",Patient," + p.getId());
                bw.newLine();
            }

            System.out.println("Users file updated successfully.");

        } catch (IOException e) {
            System.out.println("Error saving users file: " + e.getMessage());
        }
    }
}