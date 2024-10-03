package com.techelevator.dao;

import com.techelevator.model.Appointment;

import java.util.List;

public interface AppointmentDao {

    List<Appointment> getAppointments();

    Appointment getAppointmentById(int appointmentId);

    Appointment createAppointment(Appointment newAppointment);

    Appointment updateAppointment(Appointment appointment);

    int deleteAppointment(int appointmentId);

    List<Appointment> getAppointmentsByPatientId(int patientId);

    List<Appointment> getAppointmentsByDoctorId(int doctorId);
}