package com.project2.telemedicineapi.exception;

public class PatientNotFound extends RuntimeException{
    public PatientNotFound(String message){super(message);}
}