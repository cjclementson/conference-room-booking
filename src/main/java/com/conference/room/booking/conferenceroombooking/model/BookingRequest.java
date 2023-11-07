package com.conference.room.booking.conferenceroombooking.model;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingRequest {
	
	LocalTime startTime;
	
	LocalTime endTime;
	
	int attendees;

}
