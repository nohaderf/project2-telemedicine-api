package com.project2.telemedicineapi.dto;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    private String phoneNumber;
    private String message;
}