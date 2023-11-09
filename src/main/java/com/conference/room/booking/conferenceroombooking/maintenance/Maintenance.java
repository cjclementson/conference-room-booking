package com.conference.room.booking.conferenceroombooking.maintenance;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.conference.room.booking.conferenceroombooking.config.RoomConfiguration;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class Maintenance {

	private final RoomConfiguration roomConfiguration;

	public boolean isBookingOverMaintenancePeriod(LocalTime startTime, LocalTime endTime) {

		var maintenancePeriods = roomConfiguration.getMaintenancePeriods();

		for (var maintenancePeriod : maintenancePeriods) {

			var maintenancePeriodStartTime = maintenancePeriod.getStartTime();
			var maintenancePeriodEndTime = maintenancePeriod.getEndTime();
			
			/* 
			 *  Maintenance ST					  ET
			 * 				|----------------------| 
			 *  Booking	 |-----|
			 */
			if (startTime.compareTo(maintenancePeriodStartTime) <= 0
					&& endTime.compareTo(maintenancePeriodStartTime) > 0) {

				return true;
			}
			
			/* 
			 *  Maintenance ST					  ET
			 * 				|----------------------| 
			 *  Booking	 		|------------|
			 */			
			if (startTime.compareTo(maintenancePeriodStartTime) >= 0
					&& endTime.compareTo(maintenancePeriodEndTime) <= 0) {

				return true;

			}
			
			 /* 
			 *  Maintenance ST					  ET
			 * 				|----------------------| 
			 *  Booking	 						|-----|
			 */
			if (startTime.compareTo(maintenancePeriodStartTime) >= 0 
					&& endTime.compareTo(maintenancePeriodEndTime) >= 0
					&& startTime.compareTo(maintenancePeriodEndTime) < 0) {

				return true;
			}
		}

		return false;
	}

	public String getFormattedMaintenancePeriods() {

		StringBuilder stringBuilder = new StringBuilder();
		var maintenancePeriods = roomConfiguration.getMaintenancePeriods();

		boolean first = true;
		stringBuilder.append("The maintenance schedule is as followings: ");
		for (var maintenancePeriod : maintenancePeriods) {

			if (!first) {

				stringBuilder.append(", ");
			}

			stringBuilder.append(maintenancePeriod.getStartTime().toString());
			stringBuilder.append(" - ");
			stringBuilder.append(maintenancePeriod.getEndTime());

			first = false;
		}

		return stringBuilder.toString();
	}

}
