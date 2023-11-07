package com.conference.room.booking.conferenceroombooking.controller;

import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conference.room.booking.conferenceroombooking.config.RoomConfiguration;
import com.conference.room.booking.conferenceroombooking.model.BookingRequest;
import com.conference.room.booking.conferenceroombooking.service.ConferenceRoomService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/rooms")
@AllArgsConstructor
public class BookingController {
	
	private static final Logger LOG = LoggerFactory.getLogger(BookingController.class);
	
	private final ConferenceRoomService conferenceRoomService;
	private final RoomConfiguration roomConfiguration;
	
	@PostMapping("/booking")
	public ResponseEntity<?> booking (@RequestBody BookingRequest bookingRequest) {
		
		LOG.info(bookingRequest.getStartTime().toString());
		LOG.info(bookingRequest.getEndTime().toString());
		LOG.info(String.valueOf(bookingRequest.getAttendees()));
		
		var bookedRoom = conferenceRoomService.bookRoom(
				bookingRequest.getStartTime(), 
				bookingRequest.getEndTime(), 
				bookingRequest.getAttendees());
		
		
		var conf = roomConfiguration.getRoomDetails();
		
		for(int x = 0; x < conf.size(); x++) {
			LOG.info(conf.get(x).getName());
			LOG.info(String.valueOf(conf.get(x).getSize()));
		}
		
		if(bookedRoom.isPresent()) {
			
			return ResponseEntity.ok(bookedRoom);			
		}
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/availability")
	public ResponseEntity<?> availability (
			@RequestParam("startTime") LocalTime startTime, 
			@RequestParam("endTime") LocalTime endTime) {
		
		var rooms = conferenceRoomService.getAvailableRooms(startTime, endTime);
		return ResponseEntity.ok(rooms);
	}

}
