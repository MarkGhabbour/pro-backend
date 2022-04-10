package com.maiia.pro.service;

import com.maiia.pro.entity.Appointment;
import com.maiia.pro.exception.DuplicationException;
import com.maiia.pro.exception.NotFoundException;
import com.maiia.pro.repository.AppointmentRepository;
import com.maiia.pro.repository.AvailabilityRepository;
import com.maiia.pro.repository.PatientRepository;
import com.maiia.pro.repository.PractitionerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProAppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private PractitionerRepository practitionerRepository;

	@Autowired
	private ProAvailabilityService proAvailabilityService;

	public Appointment find(Integer appointmentId) {
		return appointmentRepository.findById(appointmentId).orElseThrow();
	}

	public List<Appointment> findAll() {
		return appointmentRepository.findAll();
	}

	public List<Appointment> findByPractitionerId(Integer practitionerId) {
		return appointmentRepository.findByPractitionerId(practitionerId);
	}

	public Appointment createAppointment(Appointment app) {

		var practitioner = practitionerRepository.findById(app.getPractitionerId());
		var patient = patientRepository.findById(app.getPatientId());

		if (app.getId() != null) {
			var appointment = appointmentRepository.findById(app.getId());
			if (appointment.isPresent())
				throw new DuplicationException("duplicate appointment - " + app.getId());
		}

		if (!patient.isPresent())
			throw new NotFoundException("patient not found - " + app.getPatientId());

		else if (!practitioner.isPresent())
			throw new NotFoundException("practitioner not found - " + app.getPractitionerId());

		var availabilties = proAvailabilityService.generateAvailabilities(app.getPractitionerId());

		var startDate = app.getStartDate();

		boolean appCreated = false;
		var createdApp = new Appointment();

		for (int i = 0; i < availabilties.size(); i++) {
			if (startDate.equals(availabilties.get(i).getStartDate())) {
				createdApp = appointmentRepository.save(app);
				appCreated = true;
			}
		}

		if (!appCreated) {
			throw new NotFoundException("free timeslot with this startdate not available: " + app.getStartDate()
					+ ", for Practitioner with id: " + app.getPractitionerId());
		}

		return createdApp;
	}
}
