package com.project2.telemedicineapi.entities;

import lombok.*;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;

@Entity
@Table(name= "Doctors")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor{

    @Id
    @Column(name = "id", columnDefinition = "AUTO_INCREMENT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="firstName")
    private String firstName;
    @Column(name="lastName")
    private String lastName;
    @Column(name="specialization")
    private String specialization;
    @Column(name="phoneNum")
    private String phoneNum;

    public Doctor(String username, String password, String firstName, String lastName, String specialization, String phoneNum) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.phoneNum = phoneNum;
    }
}