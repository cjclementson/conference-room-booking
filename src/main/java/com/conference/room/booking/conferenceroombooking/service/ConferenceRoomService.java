package com.conference.room.booking.conferenceroombooking.service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.conference.room.booking.conferenceroombooking.entity.Booking;
import com.conference.room.booking.conferenceroombooking.entity.Room;
import com.conference.room.booking.conferenceroombooking.model.BookedRoom;
import com.conference.room.booking.conferenceroombooking.repository.BookingRepository;
import com.conference.room.booking.conferenceroombooking.repository.RoomRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConferenceRoomService {

	private final RoomRepository roomRepository;

	private final BookingRepository bookingRepository;

	public Optional<BookedRoom> bookRoom(LocalTime startTime, LocalTime endTime, int attendees) {

		var rooms = roomRepository.findAll();
		var bookings = bookingRepository.findBookingsByStartTimeAndEndTime(Time.valueOf(startTime),
				Time.valueOf(endTime));

		Map<Long, Room> availableRooms = new HashMap<>();
		
		for (var room : rooms) {

			boolean roomFound = false;
			for (var booking : bookings) {

				if (booking.getRoom().getRoomId() == room.getRoomId()) {

					roomFound = true;
					break;
				}
			}

			if (!roomFound) {
				availableRooms.put(room.getRoomId(), room);
			}
		}
		
		if(availableRooms.isEmpty()) {
			
			return Optional.empty();
		}
		
		long bestFit = -1;
		int difference = Integer.MAX_VALUE;
		for(var room : rooms) {
			
			if(availableRooms.containsKey(room.getRoomId())) {
				int d = room.getSize() - attendees;
				
				if(d > 0 && d < difference) {
					difference = d;
					bestFit = room.getRoomId();
				}
			}			
		}
		
		if(bestFit != -1) {
			
			var r = availableRooms.get(bestFit);
			
			Optional<BookedRoom> bookedRoom = Optional.of(
					new BookedRoom(r.getRoom(), startTime, endTime));
			
			Booking booking = new Booking(r, Time.valueOf(startTime), Time.valueOf(endTime));
			bookingRepository.save(booking);
			
			return bookedRoom;
		}
		
		return Optional.empty();
	}

	public List<String> getAvailableRooms(LocalTime startTime, LocalTime endTime) {

		var rooms = roomRepository.findAll();
		var bookings = bookingRepository.findBookingsByStartTimeAndEndTime(Time.valueOf(startTime),
				Time.valueOf(endTime));

		List<String> availableRooms = new ArrayList<>();

		for (var room : rooms) {

			boolean roomFound = false;
			for (var booking : bookings) {

				if (booking.getRoom().getRoomId() == room.getRoomId()) {

					roomFound = true;
					break;
				}
			}

			if (!roomFound) {
				availableRooms.add(room.getRoom());
			}
		}

		return availableRooms;
	}

}
