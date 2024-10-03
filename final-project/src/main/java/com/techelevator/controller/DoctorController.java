package com.techelevator.controller;

import com.techelevator.dao.DoctorDao;
import com.techelevator.model.Doctor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;

@RestController
@RequestMapping("/doctors")
@PreAuthorize("permitAll")
public class DoctorController {

    private final DoctorDao doctorDao;


    public DoctorController(DoctorDao doctorDao) {
        this.doctorDao = doctorDao;
    }

    @GetMapping
    public List<Doctor> getAllDoctor (){
        return doctorDao.getDoctors();
    }

    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable int id){
        return doctorDao.getDoctorById(id);
    }
    @PostMapping
    public Doctor create (@RequestBody Doctor newDoctor){
        return doctorDao.createDoctor(newDoctor);
    }

    @PutMapping("/{id}")
    public Doctor update(@PathVariable int id, @RequestBody Doctor doctor){
        doctor.setDoctorId(id);
        return doctorDao.updateDoctor(doctor);

    }

    @GetMapping("/availability")
    public List<String> getAvailableSlots() {
        return doctorDao.getAvailableSlots();
    }

}
