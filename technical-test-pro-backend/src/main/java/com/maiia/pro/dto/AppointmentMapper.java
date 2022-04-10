package com.maiia.pro.dto;

import org.springframework.stereotype.Component;

import com.maiia.pro.entity.Appointment;
import com.maiia.pro.service.ProAvailabilityService;

@Component
public class AppointmentMapper {

	public Appointment toAppointment(AppointmentCreationDTO appointmentCreationDTO) {
		return Appointment.builder().patientId(appointmentCreationDTO.getPatientId())
				.practitionerId(appointmentCreationDTO.getPractitionerId())
				.startDate(appointmentCreationDTO.getStartDate()).endDate(appointmentCreationDTO.getStartDate()
						.plusMinutes(ProAvailabilityService.getAppointmentDurationInMinutes()))
				.build();
	}

}
