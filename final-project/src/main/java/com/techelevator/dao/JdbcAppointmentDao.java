package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Appointment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAppointmentDao implements AppointmentDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAppointmentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Appointment> getAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments ORDER BY appointment_time";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Appointment appointment = mapRowToAppointment(results);
                appointments.add(appointment);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return appointments;
    }

    @Override
    public Appointment getAppointmentById(int appointmentId) {
        Appointment appointment = null;
        String sql = "SELECT * FROM appointments WHERE appointment_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, appointmentId);
            if (results.next()) {
                appointment = mapRowToAppointment(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return appointment;
    }
    @Override
    public Appointment createAppointment(Appointment newAppointment) {
        Appointment appointment = null;
        String insertAppointmentSql = "INSERT INTO appointments (patient_id, doctor_id, appointment_time, status) " +
                "VALUES (?, ?, ?, ?) RETURNING appointment_id";
        try {
            Integer appointmentId = jdbcTemplate.queryForObject(insertAppointmentSql, Integer.class,
                    newAppointment.getPatientId(), newAppointment.getDoctorId(),
                    newAppointment.getAppointmentTime(), newAppointment.getStatus());
            appointment = getAppointmentById(appointmentId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return appointment;
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        String updateAppointmentSql = "UPDATE appointments SET patient_id = ?, doctor_id = ?, appointment_time = ?, status = ? WHERE appointment_id = ?";
        try {
            jdbcTemplate.update(updateAppointmentSql, appointment.getPatientId(), appointment.getDoctorId(),
                    appointment.getAppointmentTime(), appointment.getStatus(), appointment.getAppointmentId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return getAppointmentById(appointment.getAppointmentId());
    }

    @Override
    public int deleteAppointment(int appointmentId) {
        String deleteAppointmentSql = "DELETE FROM appointments WHERE appointment_id = ?";
        try {
            return jdbcTemplate.update(deleteAppointmentSql, appointmentId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public List<Appointment> getAppointmentsByPatientId(int patientId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE patient_id = ? ORDER BY appointment_time";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, patientId);
            while (results.next()) {
                Appointment appointment = mapRowToAppointment(results);
                appointments.add(appointment);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorId(int doctorId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE doctor_id = ? ORDER BY appointment_time";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, doctorId);
            while (results.next()) {
                Appointment appointment = mapRowToAppointment(results);
                appointments.add(appointment);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return appointments;
    }

    private Appointment mapRowToAppointment(SqlRowSet rs) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(rs.getInt("appointment_id"));
        appointment.setPatientId(rs.getInt("patient_id"));
        appointment.setDoctorId(rs.getInt("doctor_id"));
        appointment.setAppointmentTime(rs.getTimestamp("appointment_time"));
        appointment.setStatus(rs.getString("status"));
        return appointment;
    }
}