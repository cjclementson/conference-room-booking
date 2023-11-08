package com.conference.room.booking.conferenceroombooking.repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.conference.room.booking.conferenceroombooking.entity.Booking;
import com.conference.room.booking.conferenceroombooking.entity.Room;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	
	/* The query below looks at the 4 scenarios illustrated below 
	 * ST and ET is an existing booking time range in the database
	 * 
	 * 		ST					  ET
	 * 		|----------------------| 
	 *   |--A--|	|--B--|		|--C--|
	 * 	  		
	 * |---------------D---------------|
	 * 
	 * A - if the requested time range overlaps the ST of an existing booking
	 * B - if the requested time range is within the ST and ET of an existing booking
	 * C - if the requested time range overlaps the ET of an existing booking
	 * D - if the requested time range is bigger than an existing booking
	 * 
	 * if any of the case above are met, that particular room is not available
	 * for the requested time range.
	 * 
	 * The date ensures that previous bookings do not affect the current day's bookings
	 * The code currently always passes the current date, but including the date opens
	 * up the possibility of booking for tomorrow or in the future.
	 * 
	 */
	@Query("SELECT r FROM Room r WHERE r.roomId NOT IN "
			+ "("
			+ "SELECT "
				+ "b.room.roomId FROM Booking b "
			+ "WHERE "
				+ "(b.date = :date) AND ("
				+ "(b.startTime >= :startTime AND b.startTime < :endTime) OR "
				+ "(b.endTime > :startTime AND b.endTime <= :endTime) OR "
				+ "(b.startTime >= :startTime AND b.endTime <= :endTime) OR "
				+ "(b.startTime <= :startTime AND b.endTime >= :endTime))"
				+ ")")
	List<Room> findAvailableRoomsByStartTimeAndEndTime(
			@Param("date") Date date, 
			@Param("startTime") Time startTime, 
			@Param("endTime") Time endTime);

}
