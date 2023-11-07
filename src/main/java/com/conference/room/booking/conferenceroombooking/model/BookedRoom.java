package com.conference.room.booking.conferenceroombooking.model;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BookedRoom {
	
	String room;
	
	LocalTime startTime;
	
	LocalTime endTime;

}
