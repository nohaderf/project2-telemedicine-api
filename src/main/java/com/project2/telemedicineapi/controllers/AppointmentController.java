package com.project2.telemedicineapi.controllers;

import com.project2.telemedicineapi.dto.AppointmentRequest;
import com.project2.telemedicineapi.exception.UnAuthorizedResponse;
import com.project2.telemedicineapi.exception.UnauthorizedExeption;
import com.project2.telemedicineapi.services.AppointmentService;
import com.project2.telemedicineapi.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/appointment")
public class AppointmentController {
    final Logger logger = LoggerFactory.getLogger(AppointmentController.class);
    @Autowired
    AppointmentService appointmentService;
    @Autowired
    JwtService jwtService;
    /**
     * Get all appointments
     * @return all appointment instances
     */
    @GetMapping("/all")
    public ResponseEntity getAllAppointments(@RequestHeader("Authorization") String jwt){
        logger.info("Get all appointments request");
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


                    if (token != null && (role.equals("doctor"))) {
                        logger.info("Get all appointments: {}",appointmentService.getAll());
                        return ResponseEntity.ok(appointmentService.getAll());
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
     * Get all appointments associated with doctor ID
     * @param id - doctor id
     * @return appointment instances
     */

    @GetMapping("doctor/{id}")
    public ResponseEntity getAppointmentByDoctor(@PathVariable int id,@RequestHeader("Authorization") String jwt){
        logger.info("Get all appointments associated with doctor id request");
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


                    if (token != null && (role.equals("doctor"))) {
                        logger.info("Get all appointments associated with doctor id: {}",appointmentService.getAppointmentsByDoctorId(id));
                        return ResponseEntity.ok(appointmentService.getAppointmentsByDoctorId(id));
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
     * Get all appointments associated with patient ID
     * @param id - patient id
     * @return appointment instances
     */
    @GetMapping("patient/{id}")
    public ResponseEntity getAppointmentByPatient(@PathVariable int id,@RequestHeader("Authorization") String jwt){
        logger.info("Get all appointments associated with patient id request ");
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
                        logger.info("Get all appointments associated with patient id: {}",appointmentService.getAppointmentsByPatientId(id));
                        return ResponseEntity.ok(appointmentService.getAppointmentsByPatientId(id));
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
     * Sends post request to make a new appointment
     * @param newAppointment - new appointment DTO
     */
    @PostMapping("/request")
    public ResponseEntity createAppointment(@RequestBody AppointmentRequest newAppointment,@RequestHeader("Authorization") String jwt) {
        logger.info("Appointment request");
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
                        logger.info("Appointment created!");
                        appointmentService.createAppointment(newAppointment);
                        return ResponseEntity.status(201).build();
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
     * Get appointment by ID
     * @param id - appointment id
     * @return appointment instance
     */
    @GetMapping("{id}")
    public ResponseEntity findAppointmentById(@PathVariable int id) {
        try{
            return ResponseEntity.ok(appointmentService.getAppointment(id));
        }catch (Exception ex){
            return ResponseEntity.notFound().build();
        }

    }
    /**
     * Sends put request to update appointment status
     * @param id appointment id
     * @param jwt jwt token
     * @return status code
     */
    @PutMapping("/{id}/seen")
    public ResponseEntity seenStatus(@PathVariable int id,@RequestHeader("Authorization") String jwt){
        if (!jwt.equals(null) && !jwt.equals("")) {
            try {

                System.out.println(jwt);
                jwt = jwt.split(" ")[1];
                System.out.println(jwt);
                Jws<Claims> token = null;
                try {
                    token = jwtService.parseJwt(jwt);
                    String role = null;
                    try {
                        role = token.getBody().get("user_id").toString().split(" ")[0];
                    } catch (UnauthorizedExeption r) {
                        return ResponseEntity.status(500).body("Invalid User Information");
                    }
                    System.out.println(jwt);

                    if (token != null && (role.equals("doctor"))) {
                        logger.info("Patient marked as seen!");
                        appointmentService.updateStatus(id,"seen");
                        return ResponseEntity.status(202).build();
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
     * Sends put request to update appointment status
     * @param id appointment id
     * @param jwt jwt token
     * @return status code
     */
    @PutMapping("/{id}/confirm")
    public ResponseEntity confirmStatus(@PathVariable int id,@RequestHeader("Authorization") String jwt){
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


                    if (token != null && (role.equals("doctor"))) {
                        logger.info("Appointment confirmed!");
                        appointmentService.updateStatus(id,"confirmed");
                        return ResponseEntity.status(202).build();
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
     * Sends put request to update appointment consultation note
     * @param  note - note data transfer object
     * @param id - appointment id
     */
    @PutMapping("/{id}/note")
    public ResponseEntity addNote(@PathVariable int id,@RequestBody String note, @RequestHeader("Authorization") String jwt){
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


                    if (token != null && (role.equals("doctor"))) {
                        logger.info("Note successfully added!");
                        appointmentService.addNote(id,note);
                        return ResponseEntity.status(202).build();
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
     * Delete request to delete appointment
     * @param id - appointment id
     */
    @DeleteMapping("/request/{id}")
    public void deleteAppointment(@PathVariable int id) {
        appointmentService.deleteAppointment(id);
        logger.info("Appointment successfully deleted!");
    }

}