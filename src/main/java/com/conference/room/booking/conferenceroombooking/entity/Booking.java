package com.conference.room.booking.conferenceroombooking.entity;

import java.sql.Time;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Booking {
	
	public Booking(Room room, Time startTime, Time endTime) {
		this.room = room;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	@Id
	@GeneratedValue
	private long bookingId;
	
	@ManyToOne
	@JoinColumn(name = "roomId")
	private Room room;
	
	private Time startTime;
	
	private Time endTime;

}
