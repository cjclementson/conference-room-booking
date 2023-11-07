package com.conference.room.booking.conferenceroombooking.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "conference-room")
@Data
public class RoomConfiguration {
    
	private List<RoomDetails> roomDetails;

    @Data
    public static class RoomDetails {

        private String name;
        private int size;
        
    }
}
