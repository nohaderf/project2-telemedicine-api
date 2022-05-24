package com.project2.telemedicineapi.controllers;

import com.project2.telemedicineapi.dto.LoginDTO;
import com.project2.telemedicineapi.entities.Doctor;
import com.project2.telemedicineapi.entities.Patient;
import com.project2.telemedicineapi.exception.BadParameterxception;
import com.project2.telemedicineapi.exception.UnAuthorizedResponse;
import com.project2.telemedicineapi.services.DoctorService;
import com.project2.telemedicineapi.services.JwtService;
import com.project2.telemedicineapi.services.PatientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
public class PatientControllerTests {

    @MockBean
    private JwtService jwtService;
    @MockBean
    private PatientService patientService;
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void shouldReturnPatient() throws Exception{
        Patient patient1 = new Patient(1, "Ayesha", "password", "Ayesha", "Solanki", "2000-05-12", "+1 6474023382");
        LoginDTO loginDTO = new LoginDTO("Ayesha","password");
        String jwt = "testToken";
        when(patientService.login(loginDTO)).thenReturn(patient1);
        when(jwtService.createPatientJwt(patient1)).thenReturn(jwt);

        mockMvc.perform(post("/patient/login")
                        .contentType("application/json")
                        .content("{\"username\": \"Ayesha\",\"password\": \"password\" }"))
                .andExpect(status().isOk())
                .andExpect(header().string("Token",jwt));

    }
    @Test
    public void shouldReturnNotfound() throws Exception{
        LoginDTO loginDTO = new LoginDTO("Ayesha","");
        when(patientService.login(loginDTO)).thenThrow();
        mockMvc.perform(post("/patient/login")
                        .contentType("application/json")
                        .content("{\"username\": \"Ayesha\",\"password\": \" \" }"))
                .andExpect(status().isNotFound());

    }
    @Test
    public void shouldAcceptPatient() throws Exception{
        mockMvc.perform(post("/patient/register")
                        .contentType("application/json")
                        .content("{\"username\": \"Ayesha\",\"password\": \"password\", \"firstName\":\"Ayesha\",\"lastName\":\"Solnaki\",\"dob\":\"2000-05-12\",\"phoneNum\":\"+16474023382\" }"))
                .andExpect(status().isAccepted());

    }
    @Test
    public void shouldRejectPatient() throws Exception{
        mockMvc.perform(post("/patient/register")
                        .contentType("application/json")
                        .content("wrongContent"))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void shouldReturnInternalError() throws Exception{

        mockMvc.perform(get("/patient/1").header("Authorization", ""))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Internal Error"));

        mockMvc.perform(put("/patient/update").header("Authorization", "")
                        .contentType("application/json")
                        .content("{\"id\":1 ,\"username\": \"Ayesha\",\"password\": \"password\", \"firstName\":\"Ayesha\",\"lastName\":\"Solnaki\",\"dob\":\"2000-05-12\",\"phoneNum\":\"+16474023382\" }"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Internal Error"));

    }

    @Test
    public void shouldReturnloginNedded() throws Exception{

        mockMvc.perform(get("/patient/1").header("Authorization", "wrongToken"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("You need to Login"));

        mockMvc.perform(put("/patient/update").header("Authorization", "wrongToken")
                        .contentType("application/json")
                        .content("{\"id\":1 ,\"username\": \"Ayesha\",\"password\": \"password\", \"firstName\":\"Ayesha\",\"lastName\":\"Solnaki\",\"dob\":\"2000-05-12\",\"phoneNum\":\"+16474023382\" }"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("You need to Login"));

    }

    @Test
    public void shouldReturnInvalidToken() throws Exception, UnAuthorizedResponse {

        when(jwtService.parseJwt("Token")).thenThrow(UnAuthorizedResponse.class);
        mockMvc.perform(get("/patient/1").header("Authorization", "wrong Token"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Invalid Token"));

        mockMvc.perform(put("/patient/update").header("Authorization", "wrong Token")
                        .contentType("application/json")
                        .content("{\"id\":1 ,\"username\": \"Ayesha\",\"password\": \"password\", \"firstName\":\"Ayesha\",\"lastName\":\"Solnaki\",\"dob\":\"2000-05-12\",\"phoneNum\":\"+16474023382\" }"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Invalid Token"));

    }

//    @Test
//    public void shouldReturnClientError() throws Exception {
//        when(patientService.getAllPatients()).thenReturn(Collections.emptyList());
//        mockMvc.perform(get("/doctor/getAllPatients").header("Authorization", "wrongtoken"))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string("You need to Login"));
//
//        when(patientService.getAllPatients()).thenReturn(Collections.emptyList());
//        mockMvc.perform(get("/doctor/getAllDoctors").header("Authorization", "wrongtoken"))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().string("You need to Login"));
//
//    }
}