package com.conference.room.booking.conferenceroombooking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.conference.room.booking.conferenceroombooking.config.RoomConfiguration;
import com.conference.room.booking.conferenceroombooking.entity.Room;
import com.conference.room.booking.conferenceroombooking.repository.RoomRepository;

@SpringBootApplication
public class ConferenceRoomBookingApplication  implements CommandLineRunner {
	
	private final RoomRepository roomRepository;
	
	private final RoomConfiguration roomConfiguration;
	
	public ConferenceRoomBookingApplication(
			RoomRepository roomRepository,
			RoomConfiguration roomConfiguration) {
		
		this.roomRepository = roomRepository;
		this.roomConfiguration = roomConfiguration;
	}

	public static void main(String[] args) {
		SpringApplication.run(ConferenceRoomBookingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// initialize rooms into database from configuration
		var roomDetails = roomConfiguration.getRoomDetails();
		
		for (var currentRoom : roomDetails) {
			
			Room room = new Room(currentRoom.getName(), currentRoom.getSize());
			roomRepository.save(room);			
		}		
	}
}
