package com.maiia.pro.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.maiia.pro.entity.Appointment;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
    List<Appointment> findByPractitionerId(Integer practitionerId);
    List<Appointment> findAll();
    List<Appointment> findByPractitionerIdOrderByStartDateAsc(Integer practitionerId);
}
