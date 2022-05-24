package com.project2.telemedicineapi.controllers;

import com.project2.telemedicineapi.dto.LoginDTO;
import com.project2.telemedicineapi.entities.Doctor;
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

@WebMvcTest(DoctorController.class)
public class DoctorControllerTests {

    @MockBean
    private DoctorService doctorService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private PatientService patientService;
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void shouldReturnDoctor() throws Exception{
        Doctor doctor1 = new Doctor(1, "Ayesha", "password", "Ayesha", "Solanki", "Neuro surgeon", "+1 6474023382");
        LoginDTO loginDTO = new LoginDTO("Ayesha","password");
        String jwt = "testToken";
        when(doctorService.login(loginDTO)).thenReturn(doctor1);
        when(jwtService.createJwt(doctor1)).thenReturn(jwt);

        mockMvc.perform(post("/doctor/login")
                        .contentType("application/json")
                        .content("{\"username\": \"Ayesha\",\"password\": \"password\" }"))
                .andExpect(status().isOk())
                .andExpect(header().string("Token",jwt));

    }
    @Test
    public void shouldReturnOk() throws Exception{
        when(doctorService.getalldoctors()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/doctor/getAllDoctors")).andExpect(status().isOk());
    }
    @Test
    public void shouldReturnNotfound() throws Exception{
        LoginDTO loginDTO = new LoginDTO("Ayesha","");
        when(doctorService.login(loginDTO)).thenThrow();
        mockMvc.perform(post("/doctor/login")
                        .contentType("application/json")
                        .content("{\"username\": \"Ayesha\",\"password\": \" \" }"))
                .andExpect(status().isNotFound());
        when(doctorService.getalldoctors()).thenThrow();
        mockMvc.perform(get("/doctor/getAllDoctors")).andExpect(status().isNotFound());

    }
    @Test
    public void shouldReturnInternalError() throws Exception{

        mockMvc.perform(get("/doctor/getAllPatients").header("Authorization", ""))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Internal Error"));

    }
    @Test
    public void shouldReturnClientError() throws Exception {
        when(patientService.getAllPatients()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/doctor/getAllPatients").header("Authorization", "wrongtoken"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("You need to Login"));


    }

    @Test
    public void shouldReturnInvalidToken() throws Exception, UnAuthorizedResponse {

        when(jwtService.parseJwt("Token")).thenThrow(UnAuthorizedResponse.class);
        when(patientService.getAllPatients()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/doctor/getAllPatients").header("Authorization", "wrong Token"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Invalid Token"));



    }

}