package com.livetalk.user.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateConverter {

	public static Date merge(LocalDate date, LocalTime time) throws Exception{
	    ZoneId defaultZoneId = ZoneId.systemDefault();
        System.out.println("System Default TimeZone : " + defaultZoneId);
		LocalDateTime localDateTime = LocalDateTime.of(date,time);
		Instant instant = localDateTime.atZone(defaultZoneId).toInstant();
	    return Date.from(instant);
	}
	public static Date merge(LocalDateTime localDateTime) {
	    ZoneId defaultZoneId = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(defaultZoneId).toInstant();
	    return Date.from(instant);
	}
	
	public static Date convertUtcToNpt(Date date){
		Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR, 5);
        c.add(Calendar.MINUTE, 45);
        return c.getTime();
	}
	
	public static String convertTime(long time){
	    Date date = new Date(time);
	    Format format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
	    return format.format(date);
	}
}
