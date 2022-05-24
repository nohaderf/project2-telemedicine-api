package com.project2.telemedicineapi.services;

import com.project2.telemedicineapi.dto.LoginDTO;
import com.project2.telemedicineapi.entities.Doctor;
import com.project2.telemedicineapi.exception.BadParameterxception;
import com.project2.telemedicineapi.exception.DoctorsNotFound;
import com.project2.telemedicineapi.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class DoctorService {
    @Autowired
    DoctorRepository doctorrepository;

    /**
     *
     * @return return doctor object to the controller
     * @throws DoctorsNotFound if there is no doctor list found throws the exception
     * @List provide the collection of doctors list
     */
    public List<Doctor> getalldoctors() throws DoctorsNotFound {

        List<Doctor> doctors = doctorrepository.findAll();
        return doctors;
    }

    /**
     *
     *get doctor by id
     * @param id
     * @return
     */
    public Doctor getDoctorById(int id){
        return doctorrepository.getById(id);
    }

    /**
     * doctor login to match info with the db and return a doctor
     * @param dto
     * @return
     * @throws BadParameterxception
     */
    public Doctor login(LoginDTO dto) throws BadParameterxception {
        if (dto.getUsername().trim().equals("") || dto.getPassword().trim().equals("")) {
            throw new BadParameterxception("Invalid input"); //need to add different exception
        }
        Doctor doctor = doctorrepository.findByUsernameAndPassword(dto.getUsername().trim(), dto.getPassword().trim());
        if (doctor == null) {
            throw new DoctorsNotFound("Invalid username or password");
        }
        return doctor;
    }

}