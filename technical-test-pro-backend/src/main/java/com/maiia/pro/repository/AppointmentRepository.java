package com.maiia.pro.repository;

import com.maiia.pro.entity.Appointment;
import com.maiia.pro.entity.Patient;
import com.maiia.pro.entity.TimeSlot;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
    List<Appointment> findByPractitionerId(Integer practitionerId);
    List<Appointment> findAll();
    List<Appointment> findByPractitionerIdOrderByStartDateAsc(Integer practitionerId);
}
