import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

// Honestly took some help from ChatGPT like i don't know about how to implement Time in java.

public class Main {
    public static void main(String[] args) {
        // Create a hospital
        List<Doctor> doctors = new ArrayList<>();
        Doctor doctor1 = new Doctor("Dr. Smith", "Cardiologist", "123-456-7890", new boolean[]{true, false, true, false, true, false, true, false}); // Assuming availability for 8 hours
        doctors.add(doctor1);
        Hospital hospital = new Hospital("General Hospital", "123 Main St", doctors);
        
        // Create a patient
        Patient patient = new Patient("John Doe", 30, "johndoe@example.com", "Hypertension");
        
        // Schedule an appointment
        AppointmentScheduler scheduler = new AppointmentScheduler();
        LocalDateTime appointmentTime = LocalDateTime.now().plusDays(1).withHour(7); // Tomorrow at 9:00 AM
        boolean appointmentScheduled = scheduler.scheduleAppointment(doctor1, patient, appointmentTime);
        if (appointmentScheduled) {
            System.out.println("Appointment scheduled successfully!");
        } else {
            System.out.println("Failed to schedule appointment. Doctor not available or time slot already booked.");
        }
    }
}

class Patient {
    private String name;
    private int age;
    private String contactInfo;
    private String medicalHistory;
    
    public Patient(String name, int age, String contactInfo, String medicalHistory) {
        this.name = name;
        this.age = age;
        this.contactInfo = contactInfo;
        this.medicalHistory = medicalHistory;
    }
    
    // Getters and setters
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String getContactInfo() {
        return contactInfo;
    }
    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
}

class Doctor {
    private String name;
    private String specialty;
    private String contactInfo;
    private boolean[] availability;
    
    public Doctor(String name, String specialty, String contactInfo, boolean[] availability) {
        this.name = name;
        this.specialty = specialty;
        this.contactInfo = contactInfo;
        this.availability = availability;
    }
   
    
    // Getters and setters
    public String getName() {
        return name;
    }
    public String getSpecialty() {
        return specialty;
    }
    public boolean[] getAvailability() {
        return availability;
    }
    public String getContactInfo() {
        return contactInfo;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
    public void setAvailability(boolean[] availability) {
        this.availability = availability;
    }

    // Method to check if the doctor is available at a given time
    public boolean isAvailable(LocalDateTime time) {
        int hour = time.getHour();
       // Ensure that the hour is within the bounds of the availability array
       if (hour < 9 || hour > 16) {
           return false;
       }
       return availability[hour - 9]; // Adjusting the index to match the availability array
       }

}

class Hospital {
    private String name;
    private String address;
    private List<Doctor> doctors;
    private List<Appointment> appointments;
    
    public Hospital(String name, String address, List<Doctor> doctors) {
        this.name = name;
        this.address = address;
        this.doctors = doctors;
        this.appointments = new ArrayList<>();
    }
    
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }
    //Getter and Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public List<Doctor> getDoctors() {
        return doctors;
    }
    public List<Appointment> getAppointments() {
        return appointments;
    }
}

class Appointment {
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime appointmentTime;
    
    public Appointment(Doctor doctor, Patient patient, LocalDateTime appointmentTime) {
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentTime = appointmentTime;
    }
    
    // Getters and setters
    public Doctor getDoctor() {
        return doctor;
    }
    public Patient getPatient() {
        return patient;
    }
    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}

class AppointmentScheduler {
    private List<Appointment> appointments;
    
    public AppointmentScheduler() {
        this.appointments = new ArrayList<>();
    }
    
    public boolean scheduleAppointment(Doctor doctor, Patient patient, LocalDateTime appointmentTime) {
        // Check doctor's availability
        if (!doctor.isAvailable(appointmentTime)) {
            return false;
        }
        
        // Check for any conflicts with existing appointments
        for (Appointment appointment : appointments) {
            if (appointment.getDoctor().equals(doctor) && 
                appointment.getAppointmentTime().equals(appointmentTime)) {
                return false;
            }
        }
        
        // If everything is fine, create the appointment
        Appointment appointment = new Appointment(doctor, patient, appointmentTime);
        appointments.add(appointment);
        return true;
    }
}
