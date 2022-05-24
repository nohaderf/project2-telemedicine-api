package com.project2.telemedicineapi.repositories;

import com.project2.telemedicineapi.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    List<Appointment> getAppointmentByPatientId(int id);

    List<Appointment> getAppointmentByDoctorId(int id);


}