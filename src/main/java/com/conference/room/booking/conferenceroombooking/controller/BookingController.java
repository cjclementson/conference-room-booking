package com.conference.room.booking.conferenceroombooking.controller;

import java.time.LocalTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conference.room.booking.conferenceroombooking.exception.InvalidNumberOfAttendessException;
import com.conference.room.booking.conferenceroombooking.exception.MaintenancePeriodConflictException;
import com.conference.room.booking.conferenceroombooking.exception.NoRoomsAvailableException;
import com.conference.room.booking.conferenceroombooking.message.MessageResponse;
import com.conference.room.booking.conferenceroombooking.model.BookingRequest;
import com.conference.room.booking.conferenceroombooking.service.ConferenceRoomService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/rooms")
@AllArgsConstructor
public class BookingController {

	private final ConferenceRoomService conferenceRoomService;

	@PostMapping("/booking")
	public ResponseEntity<?> booking(@RequestBody BookingRequest bookingRequest) {

		try {
			
			var bookedRoom = conferenceRoomService.bookRoom(
					bookingRequest.getStartTime(), 
					bookingRequest.getEndTime(),
					bookingRequest.getAttendees());

			return ResponseEntity.ok(bookedRoom.get());

		} catch (MaintenancePeriodConflictException | NoRoomsAvailableException | InvalidNumberOfAttendessException e) {

			return ResponseEntity.ok(new MessageResponse(e.getMessage()));
		}
	}

	@GetMapping("/availability")
	public ResponseEntity<?> availability(@RequestParam("startTime") LocalTime startTime,
			@RequestParam("endTime") LocalTime endTime) {

		try {
			var rooms = conferenceRoomService.getAvailableRooms(startTime, endTime);
			return ResponseEntity.ok(rooms);

		} catch (MaintenancePeriodConflictException | NoRoomsAvailableException e) {

			return ResponseEntity.ok(new MessageResponse(e.getMessage()));
		}
	}

}
