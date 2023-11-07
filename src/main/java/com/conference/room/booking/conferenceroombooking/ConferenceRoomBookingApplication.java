package com.conference.room.booking.conferenceroombooking;

import java.sql.Time;
import java.time.LocalTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.conference.room.booking.conferenceroombooking.config.RoomConfiguration;
import com.conference.room.booking.conferenceroombooking.entity.Booking;
import com.conference.room.booking.conferenceroombooking.entity.Room;
import com.conference.room.booking.conferenceroombooking.repository.BookingRepository;
import com.conference.room.booking.conferenceroombooking.repository.RoomRepository;

@SpringBootApplication
public class ConferenceRoomBookingApplication  implements CommandLineRunner {
	
	private final RoomRepository roomRepository;
	
	private final BookingRepository bookingRepository;
	
	private final RoomConfiguration roomConfiguration;
	
	public ConferenceRoomBookingApplication(
			RoomRepository roomRepository,
			BookingRepository bookingRepository,
			RoomConfiguration roomConfiguration) {
		
		this.roomRepository = roomRepository;
		this.bookingRepository = bookingRepository;
		this.roomConfiguration = roomConfiguration;
	}

	public static void main(String[] args) {
		SpringApplication.run(ConferenceRoomBookingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		var roomDetails = roomConfiguration.getRoomDetails();
		
		for (var currentRoom : roomDetails) {
			
			Room room = new Room(currentRoom.getName(), currentRoom.getSize());
			roomRepository.save(room);
			
		}
		
		var rooms = roomRepository.findAll();
		
		for (var room : rooms) {
			
			LocalTime morningStartTime = LocalTime.of(9,0,0);
			LocalTime morningEndTime = LocalTime.of(9,15,0);
			Booking morningBooking = new Booking(room,  Time.valueOf(morningStartTime), Time.valueOf(morningEndTime));
			bookingRepository.save(morningBooking);
			
			LocalTime midDayStartTime = LocalTime.of(13,0,0);
			LocalTime midDayEndTime = LocalTime.of(13,15,0);
			Booking midDayBooking = new Booking(room,  Time.valueOf(midDayStartTime), Time.valueOf(midDayEndTime));
			bookingRepository.save(midDayBooking);
			
			LocalTime eveningStartTime = LocalTime.of(17,0,0);
			LocalTime eveningEndTime = LocalTime.of(17,15,0);
			Booking eveningBooking = new Booking(room,  Time.valueOf(eveningStartTime), Time.valueOf(eveningEndTime));
			bookingRepository.save(eveningBooking);
			
		}
		
	}
}
