package com.project2.telemedicineapi.controllers;

import com.project2.telemedicineapi.dto.AppointmentRequest;
import com.project2.telemedicineapi.entities.Appointment;
import com.project2.telemedicineapi.entities.Doctor;
import com.project2.telemedicineapi.entities.Patient;
import com.project2.telemedicineapi.exception.UnAuthorizedResponse;
import com.project2.telemedicineapi.exception.UnauthorizedExeption;
import com.project2.telemedicineapi.services.AppointmentService;
import com.project2.telemedicineapi.services.JwtService;
import com.project2.telemedicineapi.services.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppointmentController.class)
public class AppointmentControllerTests {

    @MockBean
    private JwtService jwtService;
    @MockBean
    private AppointmentService appointmentService;
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void shouldReturnInternalError() throws Exception{

        mockMvc.perform(get("/appointment/all").header("Authorization", ""))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Internal Error"));

        mockMvc.perform(get("/appointment/doctor/1").header("Authorization", ""))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Internal Error"));
        mockMvc.perform(get("/appointment/patient/1").header("Authorization", ""))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Internal Error"));
        mockMvc.perform(post("/appointment/request")
                        .header("Authorization", "").contentType("application/json")
                        .content("{\"doctorId\":1 ,\"patientId\": 1,\"date\": \"May 18th, 2022\",\"time\": \"12:00 PM\"}"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Internal Error"));

        mockMvc.perform(put("/appointment/1/seen")
                        .header("Authorization", ""))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Internal Error"));
        mockMvc.perform(put("/appointment/1/confirm")
                        .header("Authorization", ""))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Internal Error"));
        mockMvc.perform(put("/appointment/1/note")
                        .header("Authorization", "")
                        .contentType("application/json")
                        .content("\"note\": \"test\""))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Internal Error"));

    }

    @Test
    public void shouldReturnClientError() throws Exception{

        mockMvc.perform(get("/appointment/all").header("Authorization", "wrongToken"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("You need to Login"));

        mockMvc.perform(get("/appointment/doctor/1").header("Authorization", "wrongToken"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("You need to Login"));
        mockMvc.perform(get("/appointment/patient/1").header("Authorization", "wrongToken"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("You need to Login"));
        mockMvc.perform(post("/appointment/request")
                        .header("Authorization", "wrongToken").contentType("application/json")
                        .content("{\"doctorId\":1 ,\"patientId\": 1,\"date\": \"May 18th, 2022\",\"time\": \"12:00 PM\"}"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("You need to Login"));

        mockMvc.perform(put("/appointment/1/seen")
                        .header("Authorization", "wrongToken"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("You need to Login"));
        mockMvc.perform(put("/appointment/1/confirm")
                        .header("Authorization", "wrongToken"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("You need to Login"));
        mockMvc.perform(put("/appointment/1/note")
                        .header("Authorization", "wrongToken")
                        .contentType("application/json")
                        .content("\"note\": \"test\""))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("You need to Login"));

    }

    @Test
    public void shouldReturnInvalidToken() throws Exception, UnAuthorizedResponse {
        when(jwtService.parseJwt("Token")).thenThrow(UnAuthorizedResponse.class);
        mockMvc.perform(get("/appointment/all").header("Authorization", "wrong Token"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Invalid Token"));

        mockMvc.perform(get("/appointment/doctor/1").header("Authorization", "wrong Token"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Invalid Token"));
        mockMvc.perform(get("/appointment/patient/1").header("Authorization", "wrong Token"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Invalid Token"));
        mockMvc.perform(post("/appointment/request")
                        .header("Authorization", "wrong Token").contentType("application/json")
                        .content("{\"doctorId\":1 ,\"patientId\": 1,\"date\": \"May 18th, 2022\",\"time\": \"12:00 PM\"}"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Invalid Token"));

        mockMvc.perform(put("/appointment/1/seen")
                        .header("Authorization", "Invalid Token"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Invalid Token"));
        mockMvc.perform(put("/appointment/1/confirm")
                        .header("Authorization", "wrong Token"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Invalid Token"));
        mockMvc.perform(put("/appointment/1/note")
                        .header("Authorization", "wrong Token")
                        .contentType("application/json")
                        .content("\"note\": \"test\""))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Invalid Token"));

    }
    @Test
    public void shouldReturnAppointment() throws Exception{
        Doctor doctor2 = new Doctor(2, "Subhana", "password", "Subhana", "Menk", "Cardio surgeon", "+1 6474023789");
        Patient patient1 = new Patient(1, "Ayesha", "password", "Ayesha", "Solanki", "2000-05-12", "+1 6474023382");
        Appointment appointment = new Appointment(1,"May 18th, 2022", "12:00 PM","pending","",doctor2,patient1);
        when(appointmentService.getAppointment(1)).thenReturn(appointment);
        mockMvc.perform(get("/appointment/1")).andExpect(status().isOk());
    }
    @Test
    public void shouldReturnNotFound() throws Exception{
        when(appointmentService.getAppointment(1)).thenThrow();
        mockMvc.perform(get("/appointment/1")).andExpect(status().isNotFound());
    }
}