package com.conference.room.booking.conferenceroombooking.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.conference.room.booking.conferenceroombooking.entity.Booking;
import com.conference.room.booking.conferenceroombooking.entity.Room;
import com.conference.room.booking.conferenceroombooking.exception.InvalidNumberOfAttendessException;
import com.conference.room.booking.conferenceroombooking.exception.MaintenancePeriodConflictException;
import com.conference.room.booking.conferenceroombooking.exception.NoRoomsAvailableException;
import com.conference.room.booking.conferenceroombooking.maintenance.Maintenance;
import com.conference.room.booking.conferenceroombooking.message.ErrorMessages;
import com.conference.room.booking.conferenceroombooking.model.BookedRoom;
import com.conference.room.booking.conferenceroombooking.repository.BookingRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConferenceRoomService {

	private final BookingRepository bookingRepository;

	private final Maintenance maintenance;

	public Optional<BookedRoom> bookRoom(LocalTime startTime, LocalTime endTime, int attendees) {

		if (attendees <= 1) {
			throw new InvalidNumberOfAttendessException(ErrorMessages.INVALID_NUMBER_OF_ATTENDEES);
		}

		if (maintenance.isBookingOverMaintenancePeriod(startTime, endTime)) {

			String message = ErrorMessages.MAINTENANCE_PERIOD_CONFLICT + " "
					+ maintenance.getFormattedMaintenancePeriods();
			throw new MaintenancePeriodConflictException(message);
		}

		var availableRooms = bookingRepository.findAvailableRoomsByStartTimeAndEndTime(Date.valueOf(LocalDate.now()),
				Time.valueOf(startTime), Time.valueOf(endTime));

		if (availableRooms.isEmpty()) {

			throw new NoRoomsAvailableException(ErrorMessages.NO_ROOMS_AVAILABLE);
		}

		var bestRoom = getBestRoomForAttendees(availableRooms, attendees);

		if (bestRoom.isPresent()) {

			Booking booking = new Booking(bestRoom.get(), Time.valueOf(startTime), Time.valueOf(endTime));
			bookingRepository.save(booking);

			Optional<BookedRoom> bookedRoom = Optional.of(new BookedRoom(bestRoom.get().getRoom(), startTime, endTime));

			return bookedRoom;
		}

		throw new NoRoomsAvailableException(ErrorMessages.NO_ROOMS_AVAILABLE_FOR_NUMBER_OF_ATTENDEES);
	}

	public List<String> getAvailableRooms(LocalTime startTime, LocalTime endTime) {

		if (maintenance.isBookingOverMaintenancePeriod(startTime, endTime)) {

			String message = ErrorMessages.NO_ROOMS_AVAILABLE_DURING_MAINTENANCE_PERIOD + " "
					+ maintenance.getFormattedMaintenancePeriods();
			throw new MaintenancePeriodConflictException(message);
		}

		var availableRooms = bookingRepository.findAvailableRoomsByStartTimeAndEndTime(Date.valueOf(LocalDate.now()),
				Time.valueOf(startTime), Time.valueOf(endTime));

		if (availableRooms.isEmpty()) {

			throw new NoRoomsAvailableException(ErrorMessages.NO_ROOMS_AVAILABLE);
		}

		return availableRooms.stream().map(room -> room.getRoom()).collect(Collectors.toList());
	}

	private Optional<Room> getBestRoomForAttendees(List<Room> availableRooms, int attendees) {

		Optional<Room> bestRoom = Optional.empty();
		int difference = Integer.MAX_VALUE;

		for (var availableRoom : availableRooms) {

			int localDifference = availableRoom.getSize() - attendees;

			if (localDifference >= 0 && localDifference < difference) {
				difference = localDifference;
				bestRoom = Optional.of(availableRoom);
			}
		}

		return bestRoom;
	}

}
