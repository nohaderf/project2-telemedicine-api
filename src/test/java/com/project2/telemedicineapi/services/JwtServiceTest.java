package com.project2.telemedicineapi.services;

import com.project2.telemedicineapi.entities.Doctor;
import com.project2.telemedicineapi.entities.Patient;
import com.project2.telemedicineapi.exception.UnAuthorizedResponse;
import com.project2.telemedicineapi.repositories.AppointmentRepository;
import com.project2.telemedicineapi.repositories.DoctorRepository;
import com.project2.telemedicineapi.repositories.PatientRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtServiceTest {
    private JwtService jwtService;
    private static JwtService instance;
    private Key key;

    @BeforeEach
    public void initBeforeTest(){
        byte[] secret = "my_secret_passwordhkjhkgkgjhghjgkjgkgfgftufutfdsfsdfsdfgsdgsdgdgsdg".getBytes();
        key = Keys.hmacShaKeyFor(secret);
        jwtService = new JwtService();
    }
    @Test
    void createJwt() {
        Doctor doctor = new Doctor(1, "Ayesha", "password", "Ayesha", "Solanki", "Neuro surgeon", "+1 6474023382");
        String jwt = Jwts.builder().setSubject(doctor.getUsername())
                .claim("user_id", "doctor "+doctor.getId())
                .signWith(key)
                .compact();
        String actual = jwtService.createJwt(doctor);
        Assertions.assertEquals(jwt,actual);


    }

    @Test
    void createPatientJwt() {
        Patient patient = new Patient(1, "Ayesha", "password", "Ayesha", "Solanki", "2000-05-12", "+1 6474023382");
        String jwt = Jwts.builder().setSubject(patient.getUsername())
                .claim("user_id", "patient "+patient.getId())
                .signWith(key)
                .compact();
        String actual = jwtService.createPatientJwt(patient);
        Assertions.assertEquals(jwt,actual);

    }
    @Test
    void parseJwt() throws UnAuthorizedResponse {
        String jwt = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdWJoYW5hIiwidXNlcl9pZCI6ImRvY3RvciAxIn0.xGPQcxXVSH7IRhoS887AH9Hr75PFH38Jzm7Q7NpaT9cjTYw0pnr1gsT7sDfzA7iOL8pFAvm0Qob1hk31_lQJHA";
        Jws<Claims> token = Jwts.parserBuilder().setSigningKey(key)
                .build()
                .parseClaimsJws(jwt);
        String role = token.getBody().get("user_id").toString().split(" ")[0];
        Jws<Claims> actual = jwtService.parseJwt(jwt);
        String actualRole = actual.getBody().get("user_id").toString().split(" ")[0];
        Assertions.assertEquals(role,actualRole);
    }
    @Test
    void negativeParseJwt(){
        String jwt = "eyJhbGciOiJIUzUxMJzm7Q7NpaT9cjTYw0pnr1gsT7sDfzA7iOL8pFAvm0Qob1hk31_lQJHA";
        Assertions.assertThrows(UnAuthorizedResponse.class,()->{
            jwtService.parseJwt(jwt);
        });

    }
}