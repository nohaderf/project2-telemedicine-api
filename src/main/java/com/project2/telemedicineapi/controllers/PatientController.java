package com.project2.telemedicineapi.controllers;



import com.project2.telemedicineapi.dto.LoginDTO;

import com.project2.telemedicineapi.exception.UnAuthorizedResponse;
import com.project2.telemedicineapi.exception.UnauthorizedExeption;

import com.project2.telemedicineapi.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.project2.telemedicineapi.entities.Patient;

import com.project2.telemedicineapi.services.PatientService;


@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    PatientService patientService;
    @Autowired
    JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(PatientController.class);

    /**
     * Sends post request to login as a patient
     * @param dto - login data transfer object
     * @return status and header (for token)
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        try {
            Patient patient = patientService.login(dto);
            String jwt = jwtService.createPatientJwt(patient);
            HttpHeaders reposeHeader = new HttpHeaders();
            reposeHeader.set("token", jwt);
            return ResponseEntity.ok().headers(reposeHeader).body(patient); /* Components oof HttpResponse method
                                                                                1. Status - is the reponse status
                                                                                2. header - header will have the token
                                                                                3. body(optional) in this case we are returning doctor object in response body*/
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();

        }

    }
    /**
     * Sends post request to create a new patient
     * @param incomingPatient - new patient data transfer object
     */
    @PostMapping("/register")
    public ResponseEntity createPatient(@RequestBody Patient incomingPatient) {
        try{
            patientService.createPatient(incomingPatient);
            return ResponseEntity.accepted().build();
        }catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }


    }

    /**
     * Gets patient by id
     * @param id - patient id
     * @param jwt - session token
     */
    @GetMapping("/{id}")
    public ResponseEntity findPatientById(@PathVariable int id,@RequestHeader("Authorization") String jwt) {

        if (!jwt.equals(null) && !jwt.equals("")) {
            try {


                jwt = jwt.split(" ")[1];
                Jws<Claims> token = null;
                try {
                    token = jwtService.parseJwt(jwt);
                    String role = null;
                    try {
                        role = token.getBody().get("user_id").toString().split(" ")[0];
                    } catch (UnauthorizedExeption r) {
                        return ResponseEntity.status(500).body("Invalid User Information");
                    }


                    if (token != null && (role.equals("patient") || role.equals("doctor"))) {
                        return ResponseEntity.ok(patientService.getPatient(id));
                    } else {
                        return ResponseEntity.status(403).body("You are not authorized at this point");
                    }
                } catch (UnAuthorizedResponse e) {
                    return ResponseEntity.status(500).body("Invalid Token");
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                return ResponseEntity.status(400).body("You need to Login");
            }

        }
        return ResponseEntity.status(500).body("Internal Error");

    }
    /**
     * Sends request to update patient profile
     * @param existingPatient - patient instance
     * @param jwt - session token
     * @return status
     */
    @PutMapping("/update")
    public ResponseEntity editPatientProfile(@RequestBody Patient existingPatient,@RequestHeader("Authorization") String jwt) {

        if (!jwt.equals(null) && !jwt.equals("")) {
            try {


                jwt = jwt.split(" ")[1];
                Jws<Claims> token = null;
                try {
                    token = jwtService.parseJwt(jwt);
                    String role = null;
                    try {
                        role = token.getBody().get("user_id").toString().split(" ")[0];
                    } catch (UnauthorizedExeption r) {
                        return ResponseEntity.status(500).body("Invalid User Information");
                    }


                    if (token != null && (role.equals("patient"))) {
                        patientService.createPatient(existingPatient);
                        return ResponseEntity.accepted().build();
                    } else {
                        return ResponseEntity.status(403).body("You are not authorized at this point");
                    }
                } catch (UnAuthorizedResponse e) {
                    return ResponseEntity.status(500).body("Invalid Token");
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                return ResponseEntity.status(400).body("You need to Login");
            }

        }
        return ResponseEntity.status(500).body("Internal Error");


    }
}