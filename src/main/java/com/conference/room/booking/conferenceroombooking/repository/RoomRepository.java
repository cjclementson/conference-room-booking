package com.conference.room.booking.conferenceroombooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conference.room.booking.conferenceroombooking.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
	
}
