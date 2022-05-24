package com.project2.telemedicineapi.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginDTOTest {

    LoginDTO loginDTO = new LoginDTO("user","pass");
    @Test
    void testEquals() {
        LoginDTO loginDTO = new LoginDTO("user","pass");
        assertEquals(loginDTO,this.loginDTO);
    }

    @Test
    void getUsername() {
        assertEquals("user",loginDTO.getUsername());
    }

    @Test
    void getPassword() {
        assertEquals("pass",loginDTO.getPassword());
    }

    @Test
    void setUsername() {
        loginDTO.setUsername("user2");
        assertEquals("user2",loginDTO.getUsername());
    }

    @Test
    void setPassword() {
        loginDTO.setPassword("pass2");
        assertEquals("pass2",loginDTO.getPassword());
    }

    @Test
    void testToString() {
        assertEquals("LoginDTO(username=user, password=pass)",loginDTO.toString());
    }
}