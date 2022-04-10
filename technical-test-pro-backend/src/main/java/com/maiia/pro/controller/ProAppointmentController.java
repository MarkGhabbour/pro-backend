package com.maiia.pro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maiia.pro.entity.Appointment;
import com.maiia.pro.service.ProAppointmentService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping(value = "/appointments", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProAppointmentController {
	@Autowired
	private ProAppointmentService proAppointmentService;
	
	@ApiOperation(value = "Get appointments by practitionerId")
	@GetMapping("/{practitionerId}")
	public List<Appointment> getAppointmentsByPractitioner(@PathVariable final Integer practitionerId) {
		return proAppointmentService.findByPractitionerId(practitionerId);
	}

	@ApiOperation(value = "Get all appointments")
	@GetMapping
	public List<Appointment> getAppointments() {
		return proAppointmentService.findAll();
	}

	@ApiOperation(value = "Create appointment")
	@PostMapping
	public Appointment createAppointment(@RequestBody Appointment app) {
		return proAppointmentService.createAppointment(app);
	}

}
