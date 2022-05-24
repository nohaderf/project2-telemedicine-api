package com.project2.telemedicineapi.services;

import com.project2.telemedicineapi.dto.LoginDTO;
import com.project2.telemedicineapi.entities.Doctor;
import com.project2.telemedicineapi.exception.BadParameterxception;
import com.project2.telemedicineapi.exception.DoctorsNotFound;
import com.project2.telemedicineapi.repositories.DoctorRepository;
import com.project2.telemedicineapi.services.DoctorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService = new DoctorService();

    @Test
    public void positiveGetAllDoctor() {
        Doctor doctor1 = new Doctor(1, "Ayesha", "password", "Ayesha", "Solanki", "Neuro surgeon", "+1 6474023382");
        Doctor doctor2 = new Doctor(2, "Subhana", "password", "Subhana", "Menk", "Cardio surgeon", "+1 6474023789");
        Doctor doctor3 = new Doctor(3, "Khadija", "password", "Khadija", "Patel", "Pediatrician", "+1 6474021234");

        List<Doctor> doctors = new ArrayList<>();
        doctors.add(doctor1);
        doctors.add(doctor2);
        doctors.add(doctor3);

        when(doctorRepository.findAll()).thenReturn(doctors);
        List<Doctor> actual = doctorService.getalldoctors();
        Assertions.assertEquals(doctors, actual);
    }

    @Test
    public void negativeGetAllDoctors() {

        given(doctorRepository.findAll()).willReturn(Collections.emptyList());
        List<Doctor> actual = doctorService.getalldoctors();
        assertThat(actual).isEmpty();
        assertThat(actual.size()).isEqualTo(0);

    }

    @Test
    public void positiveLogin() throws BadParameterxception {
        Doctor doctor1 = new Doctor(1, "Ayesha", "password", "Ayesha", "Solanki", "Neuro surgeon", "+1 6474023382");
        LoginDTO dto = new LoginDTO("Ayesha", "password");

        when(doctorRepository.findByUsernameAndPassword(eq("Ayesha"), eq("password"))).thenReturn(doctor1);

        Doctor actual = doctorService.login(dto);
        Doctor expected = new Doctor(1, "Ayesha", "password", "Ayesha", "Solanki", "Neuro surgeon", "+1 6474023382");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void negativeLogin() throws BadParameterxception {
        LoginDTO dto = new LoginDTO("", "password");
        Assertions.assertThrows(BadParameterxception.class, () -> {
            doctorService.login(dto);
        });
    }

    @Test
    public void negativeInvalidUserNameAnsPassword() {
        Doctor doctor = null;
        LoginDTO dto = new LoginDTO("Ayesha", "password");
        when(doctorRepository.findByUsernameAndPassword(eq("Ayesha"), eq("password"))).thenReturn(doctor);
        Assertions.assertThrows(DoctorsNotFound.class, () -> {
            doctorService.login(dto);
        });


    }


}