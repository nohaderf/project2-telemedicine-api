package com.project2.telemedicineapi.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="Patients")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

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
    @Column(name="dob")
    private String dob;
    @Column(name="phoneNum")
    private String phoneNum;

    public Patient(String username, String password, String firstName, String lastName, String dob, String phoneNum) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.phoneNum = phoneNum;
    }


}