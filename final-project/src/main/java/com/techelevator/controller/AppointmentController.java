package com.techelevator.controller;


import com.techelevator.dao.AppointmentDao;
import com.techelevator.model.Appointment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@PreAuthorize("permitAll")
public class AppointmentController {
    private final AppointmentDao appointmentDao;

    public AppointmentController (AppointmentDao appointmentDao){
        this.appointmentDao = appointmentDao;
    }

    @GetMapping
    public List<Appointment> getAllAppointment (){
        return appointmentDao.getAppointments();
    }

    @GetMapping ("/{id}")
    public Appointment getAppointmentById(@PathVariable int id){
        return appointmentDao.getAppointmentById(id);

    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public Appointment create (@RequestBody Appointment newAppointment){
        return appointmentDao.createAppointment(newAppointment);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public  Appointment update(@PathVariable int id, @RequestBody Appointment appointment){
        appointment.setAppointmentId(id);
        return appointmentDao.updateAppointment(appointment);
    }


    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{appointmentId}")
    public void deleteAppointment(@PathVariable int appointmentId){
        appointmentDao.deleteAppointment(appointmentId);
    }



    @GetMapping("/patient/{patientId}")
    public List<Appointment> getAppointmentsByPatientId(@PathVariable int patientId) {
        return appointmentDao.getAppointmentsByPatientId(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getAppointmentsByDoctorId(@PathVariable int doctorId) {
        return appointmentDao.getAppointmentsByDoctorId(doctorId);
    }

}
