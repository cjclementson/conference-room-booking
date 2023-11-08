package com.conference.room.booking.conferenceroombooking.exception;

public class NoRoomsAvailableException extends RuntimeException {
	
	public NoRoomsAvailableException(String message) {
        super(message);
    }

}