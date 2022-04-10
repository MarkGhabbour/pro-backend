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

	public static int getAppointmentDurationInMinutes() {
		return appointmentDurationInMinutes;
	}

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
		List<Appointment> bookedAppointmentList = appointmentRepository
				.findByPractitionerIdOrderByStartDateAsc(practitionerId);

		List<Availability> freeAvailabilities = new ArrayList<Availability>();

		int bookedAppointmentPointer = 0;

		for (int i = 0; i < timeSlotList.size(); i++) {
			TimeSlot timeslot = timeSlotList.get(i);

			LocalDateTime startDateTime = timeslot.getStartDate();
			LocalDateTime endDateTime = timeslot.getEndDate();

			while (endDateTime.isAfter(startDateTime)) {
				// generate possible availabilities from timeSlot
				Availability freeAvailability = Availability.builder().startDate(startDateTime)
						.endDate(startDateTime.plusMinutes(appointmentDurationInMinutes)).practitionerId(practitionerId)
						.build();

				if (bookedAppointmentPointer < bookedAppointmentList.size()) {
					Appointment bookedAppointment = bookedAppointmentList.get(bookedAppointmentPointer);

					boolean isAfterStart = bookedAppointment.getStartDate().equals(freeAvailability.getStartDate())
							|| bookedAppointment.getStartDate().isAfter(freeAvailability.getStartDate());

					boolean isBeforeEnd = bookedAppointment.getStartDate().isBefore(freeAvailability.getEndDate());

					//check if appointment startTime is between availability start and end
					if (isAfterStart && isBeforeEnd) {
						bookedAppointmentPointer++;
						startDateTime = bookedAppointment.getEndDate();
					} else {
						freeAvailabilities.add(freeAvailability);
						startDateTime = startDateTime.plusMinutes(appointmentDurationInMinutes);
					}
				} else {
					freeAvailabilities.add(freeAvailability);
					startDateTime = startDateTime.plusMinutes(appointmentDurationInMinutes);
				}

			}
		}

		return freeAvailabilities;

	}
}
