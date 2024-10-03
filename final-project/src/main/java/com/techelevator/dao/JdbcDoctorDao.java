package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Doctor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcDoctorDao implements DoctorDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcDoctorDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Doctor> getDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors ORDER BY name";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Doctor doctor = mapRowToDoctor(results);
                doctors.add(doctor);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return doctors;
    }

    @Override
    public Doctor getDoctorById(int doctorId) {
        Doctor doctor = null;
        String sql = "SELECT * FROM doctors WHERE doctor_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, doctorId);
            if (results.next()) {
                doctor = mapRowToDoctor(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return doctor;
    }

    @Override
    public Doctor createDoctor(Doctor newDoctor) {
        Doctor doctor = null;
        String insertDoctorSql = "INSERT INTO doctors (name, speciality, availability) " +
                "VALUES (?, ?, ?) RETURNING doctor_id";
        try {
            Integer doctorId = jdbcTemplate.queryForObject(insertDoctorSql, Integer.class,
                    newDoctor.getName(), newDoctor.getSpeciality(), newDoctor.getAvailability());
            doctor = getDoctorById(doctorId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return doctor;
    }

    @Override
    public Doctor updateDoctor(Doctor doctor) {
        String updateDoctorSql = "UPDATE doctors SET name = ?, speciality = ?, availability = ? WHERE doctor_id = ?";
        try {
            jdbcTemplate.update(updateDoctorSql, doctor.getName(), doctor.getSpeciality(),
                    doctor.getAvailability(), doctor.getDoctorId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return getDoctorById(doctor.getDoctorId());
    }

    private Doctor mapRowToDoctor(SqlRowSet rs) {
        Doctor doctor = new Doctor();
        doctor.setDoctorId(rs.getInt("doctor_id"));
        doctor.setName(rs.getString("name"));
        doctor.setSpeciality(rs.getString("speciality"));
        doctor.setAvailability(rs.getString("availability"));
        return doctor;
    }

    @Override
    public List<String> getAvailableSlots() {
        String sql = "SELECT availability FROM doctors";
        return jdbcTemplate.queryForList(sql, String.class);
    }
}