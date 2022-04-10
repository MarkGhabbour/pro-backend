package com.maiia.pro.service;

import com.maiia.pro.entity.Appointment;
import com.maiia.pro.entity.Availability;
import com.maiia.pro.entity.TimeSlot;
import com.maiia.pro.repository.AppointmentRepository;
import com.maiia.pro.repository.AvailabilityRepository;
import com.maiia.pro.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProAvailabilityService {

	private static int appointmentDurationInMinutes = 15;

	@Autowired
	private AvailabilityRepository availabilityRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private TimeSlotRepository timeSlotRepository;

	public List<Availability> findByPractitionerId(Integer practitionerId) {
		return availabilityRepository.findByPractitionerId(practitionerId);
	}

	public List<Availability> generateAvailabilities(Integer practitionerId) {
		// TODO : implement this
		List<TimeSlot> timeSlotList = timeSlotRepository.findByPractitionerIdOrderByStartDateAsc(practitionerId);
		List<Appointment> bookedAppointmentList = appointmentRepository.findByPractitionerId(practitionerId);

		List<Availability> freeAvailabilities = new ArrayList<Availability>();

		int bookedAppointmentPointer = 0;

		for (int i = 0; i < timeSlotList.size(); i++) {
			TimeSlot timeslot = timeSlotList.get(i);

			int j = 0;

			LocalDateTime startDateTime = timeslot.getStartDate();
			LocalDateTime endDateTime = timeslot.getEndDate();

			while (endDateTime.isAfter(startDateTime)) {
				// generate possible availabilities time from timeslot
				Availability freeAvailability = Availability.builder()
						.startDate(startDateTime)
						.endDate(startDateTime.plusMinutes(appointmentDurationInMinutes)).practitionerId(practitionerId)
						.build();

				if (bookedAppointmentPointer < bookedAppointmentList.size()) {
					Appointment bookedAppointment = bookedAppointmentList.get(bookedAppointmentPointer);
					if (bookedAppointment.getStartDate().equals(freeAvailability.getStartDate())
							&& bookedAppointment.getEndDate().equals(freeAvailability.getEndDate()))
						bookedAppointmentPointer++;
					else {
						freeAvailabilities.add(freeAvailability);
					}
				} else {
					freeAvailabilities.add(freeAvailability);
				}
				j++;
				startDateTime=startDateTime.plusMinutes(appointmentDurationInMinutes);
			}
		}

		// return freeAvailabilities;
		return freeAvailabilities;

	}
}
