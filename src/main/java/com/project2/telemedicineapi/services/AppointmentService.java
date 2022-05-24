package com.project2.telemedicineapi.services;

import com.project2.telemedicineapi.dto.AppointmentRequest;
import com.project2.telemedicineapi.entities.Appointment;
import com.project2.telemedicineapi.entities.Doctor;
import com.project2.telemedicineapi.entities.Patient;
import com.project2.telemedicineapi.helpers.NotificationClient;
import com.project2.telemedicineapi.repositories.AppointmentRepository;
import com.project2.telemedicineapi.repositories.DoctorRepository;
import com.project2.telemedicineapi.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    AppointmentRepository appointmentRepository;
    DoctorRepository doctorRepository;
    PatientRepository patientRepository;
    NotificationClient notificationClient;

    @Autowired
    void setAppointmentRepository(AppointmentRepository appointmentRepository){
        this.appointmentRepository= appointmentRepository;
    }
    @Autowired
    void setDoctorRepository(DoctorRepository doctorRepository){
        this.doctorRepository=doctorRepository;
    }
    @Autowired
    void setPatientRepository(PatientRepository patientRepository){
        this.patientRepository= patientRepository;
    }

    void setNotificationClient(NotificationClient notificationClient){this.notificationClient = notificationClient;}



    /**
     * Create appointment
     * @param newAppointment - new appointment data transfer object
     */
    public void createAppointment(AppointmentRequest newAppointment) {
        Appointment appointment = new Appointment();
        Patient patient = patientRepository.getById(newAppointment.getPatientId());
        Doctor doctor = doctorRepository.getById(newAppointment.getDoctorId());
        appointment.setStatus("pending");
        appointment.setDate(newAppointment.getDate());
        appointment.setTime(newAppointment.getTime());
        appointment.setNote("");
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointmentRepository.save(appointment);
        notificationClient.callPostEmail(doctor.getPhoneNum(),"Hey " + doctor.getUsername() + ", you have a new appointment request from "+  patient.getUsername() );
    }

    /**
     * Get all appointments
     */
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    /**
     * Get appointment associated with doctor id
     * @param id - doctor id
     */
    public List<Appointment> getAppointmentsByDoctorId(int id) {
        return appointmentRepository.getAppointmentByDoctorId(id);

    }
    /**
     * Get appointments associated with patient id
     * @param id - patient id
     */
    public List<Appointment> getAppointmentsByPatientId(int id) {
        return appointmentRepository.getAppointmentByPatientId(id);

    }

    /**
     * Get appointment by id
     * @param id - appointment id
     */
    public Appointment getAppointment(int id) {
        return appointmentRepository.getById(id);

    }
    /**
     * Update appointment status
     * @param status
     */
    public void updateStatus(int id, String status){
        Appointment appointment = getAppointment(id);
        appointment.setStatus(status);
        NotificationClient notificationClient= new NotificationClient();
        notificationClient.callPostEmail(appointment.getPatient().getPhoneNum(),"Hey " + appointment.getPatient().getUsername() + ", your appointment on " + appointment.getDate()  +  " at " +  appointment.getTime()  +  " has been " +appointment.getStatus() +" by "+  appointment.getDoctor().getUsername() );
        appointmentRepository.save(appointment);
    }
    /**
     * Add note to appointment with associated id
     * @param id - appointment id
     * @param note - consult note
     */
    public void addNote(int id,String note){
        Appointment appointment = getAppointment(id);
        appointment.setNote(note);
        appointmentRepository.save(appointment);
    }

    /**
     * Delete appointment with id
     * @param id - appointment id
     */
    public void deleteAppointment(int id) {
        appointmentRepository.deleteById(id);
    }
}