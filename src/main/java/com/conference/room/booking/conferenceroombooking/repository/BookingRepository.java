package com.conference.room.booking.conferenceroombooking.repository;

import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.conference.room.booking.conferenceroombooking.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	
	@Query("SELECT b FROM Booking b WHERE b.startTime >= :startTime AND b.endTime <= :endTime")
	List<Booking> findBookingsByStartTimeAndEndTime(@Param("startTime") Time startTime, @Param("endTime") Time endTime);

}
