package com.project2.telemedicineapi.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequest {
    private int doctorId;
    private int patientId;
    private String date;
    private String time;
}
