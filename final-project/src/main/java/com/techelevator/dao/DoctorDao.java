package com.techelevator.dao;

import com.techelevator.model.Doctor;

import java.util.List;

public interface DoctorDao {

    List<Doctor> getDoctors();

    Doctor getDoctorById(int doctorId);

    Doctor createDoctor(Doctor newDoctor);

    Doctor updateDoctor(Doctor doctor);

    List<String> getAvailableSlots();
}