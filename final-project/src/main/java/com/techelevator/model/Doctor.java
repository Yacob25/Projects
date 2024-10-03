package com.techelevator.model;


import java.util.Objects;


    public class Doctor {

        private int doctorId;
        private String name;
        private String speciality;
        private String availability;

        public Doctor() { }

        public Doctor(int doctorId, String name, String speciality, String availability) {
            this.doctorId = doctorId;
            this.name = name;
            this.speciality = speciality;
            this.availability = availability;
        }

        public Doctor(String name, String speciality, String availability) {
            this(0, name, speciality, availability);
        }

        public int getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(int doctorId) {
            this.doctorId = doctorId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSpeciality() {
            return speciality;
        }

        public void setSpeciality(String speciality) {
            this.speciality = speciality;
        }

        public String getAvailability() {
            return availability;
        }

        public void setAvailability(String availability) {
            this.availability = availability;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Doctor doctor = (Doctor) o;
            return doctorId == doctor.doctorId &&
                    Objects.equals(name, doctor.name) &&
                    Objects.equals(speciality, doctor.speciality) &&
                    Objects.equals(availability, doctor.availability);
        }

        @Override
        public int hashCode() {
            return Objects.hash(doctorId, name, speciality, availability);
        }

        @Override
        public String toString() {
            return "Doctor{" +
                    "doctorId=" + doctorId +
                    ", name='" + name + '\'' +
                    ", speciality='" + speciality + '\'' +
                    ", availability='" + availability + '\'' +
                    '}';
        }
    }
