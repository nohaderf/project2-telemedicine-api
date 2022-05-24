package com.project2.telemedicineapi.services;

import com.project2.telemedicineapi.dto.LoginDTO;
import com.project2.telemedicineapi.entities.Doctor;
import com.project2.telemedicineapi.entities.Patient;
import com.project2.telemedicineapi.exception.BadParameterxception;
import com.project2.telemedicineapi.exception.DoctorsNotFound;
import com.project2.telemedicineapi.exception.PatientNotFound;
import com.project2.telemedicineapi.repositories.DoctorRepository;
import com.project2.telemedicineapi.repositories.PatientRepository;
import com.project2.telemedicineapi.services.DoctorService;
import com.project2.telemedicineapi.services.PatientService;
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
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService = new PatientService();

    @Test
    public void positiveGetAllDoctor() {
        Patient patient1 = new Patient(1, "Ayesha", "password", "Ayesha", "Solanki", "2000-05-12", "+1 6474023382");
        Patient patient2 = new Patient(2, "Subhana", "password", "Subhana", "Menk", "1998-04-18", "+1 6474023789");
        Patient patient3 = new Patient(3, "Khadija", "password", "Khadija", "Patel", "1999-04-17", "+1 6474021234");

        List<Patient> patients = new ArrayList<>();
        patients.add(patient1);
        patients.add(patient2);
        patients.add(patient3);

        when(patientRepository.findAll()).thenReturn(patients);
        List<Patient> actual = patientService.getAllPatients();
        Assertions.assertEquals(patients, actual);
    }

    @Test
    public void negativeGetAllDoctors() {

        given(patientRepository.findAll()).willReturn(Collections.emptyList());
        List<Patient> actual = patientService.getAllPatients();
        assertThat(actual).isEmpty();
        assertThat(actual.size()).isEqualTo(0);

    }

    @Test
    public void positiveLogin() throws BadParameterxception {
        Patient patient1 = new Patient(1, "Ayesha", "password", "Ayesha", "Solanki", "2000-05-12", "+1 6474023382");
        LoginDTO dto = new LoginDTO("Ayesha", "password");

        when(patientRepository.findByUsernameAndPassword(eq("Ayesha"), eq("password"))).thenReturn(patient1);

        Patient actual = patientService.login(dto);
        Patient expected = new Patient(1, "Ayesha", "password", "Ayesha", "Solanki", "2000-05-12", "+1 6474023382");


        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void negativeLogin() throws BadParameterxception {
        LoginDTO dto = new LoginDTO("", "password");
        Assertions.assertThrows(BadParameterxception.class, () -> {
            patientService.login(dto);
        });
    }

    @Test
    public void negativeInvalidUserNameAnsPassword() {
        Patient patient = null;
        LoginDTO dto = new LoginDTO("Ayesha", "password");
        when(patientRepository.findByUsernameAndPassword(eq("Ayesha"), eq("password"))).thenReturn(patient);
        Assertions.assertThrows(PatientNotFound.class, () -> {
            patientService.login(dto);
        });


    }

    @Test
    public void positivegetPatientTest() {
        Patient patient1 = new Patient(1, "Ayesha", "password", "Ayesha", "Solanki", "2000-05-12", "+1 6474023382");
        when(patientRepository.getById(eq(1))).thenReturn(patient1);
        Patient actual = patientService.getPatient(1);
        Assertions.assertEquals(patient1, actual);
    }
    @Test
    public void negativePatientTest() {
        Patient patient=null;
        when(patientRepository.getById(eq(1))).thenReturn(patient);
        Patient actual = patientService.getPatient(1);
        Assertions.assertEquals(patient, actual);
    }



}