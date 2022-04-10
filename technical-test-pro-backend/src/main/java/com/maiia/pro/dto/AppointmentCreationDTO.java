package com.maiia.pro.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentCreationDTO {

	private Integer patientId;
	private Integer practitionerId;
	private LocalDateTime startDate;
	
}
