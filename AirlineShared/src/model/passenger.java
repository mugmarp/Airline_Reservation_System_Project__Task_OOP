/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Passenger DTO - shared between client and server.
 * Uses LocalDate for modern date handling; serialization uses ISO-8601 string.
 */
public class passenger implements Serializable {

    private static final long serialVersionUID = 1L;

    private int passengerId;
    private String firstName, lastName, gender, nationality, passportNumber, email, phoneNumber;
    private LocalDate dateOfBirth;

    // Constructor for creating NEW passengers from UI
    public passenger(String firstName, String lastName, String gender, LocalDate dateOfBirth,
                     String nationality, String passportNumber, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.passportNumber = passportNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Constructor for reading from DB (includes passengerId)
    public passenger(int passengerId, String firstName, String lastName, String gender,
                     LocalDate dateOfBirth, String nationality, String passportNumber,
                     String email, String phoneNumber) {
        this(firstName, lastName, gender, dateOfBirth, nationality, passportNumber, email, phoneNumber);
        this.passengerId = passengerId;
    }

    // Getters
    public int getPassengerId() { return passengerId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getGender() { return gender; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getNationality() { return nationality; }
    public String getPassportNumber() { return passportNumber; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }

    // Setters (for deserialization/DB mapping)
    public void setPassengerId(int passengerId) { this.passengerId = passengerId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setGender(String gender) { this.gender = gender; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public void setPassportNumber(String passportNumber) { this.passportNumber = passportNumber; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}