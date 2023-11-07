package com.conference.room.booking.conferenceroombooking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Room {
	
	public Room(String room, int size) {
		this.room = room;
		this.size = size;
	}
	
	@Id
	@GeneratedValue
	private long roomId;
	
	@Column(nullable = false)
	private String room;
	
	@Column(nullable = false)
	private int size;
	
}
