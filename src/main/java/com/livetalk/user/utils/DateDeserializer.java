package com.livetalk.user.utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DateDeserializer extends JsonDeserializer<Date> {
	
	@Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException  {
        return  DateConverter.merge(LocalDateTime.parse(jsonParser.getText(), DateTimeFormatter.ofPattern("EE, MMM dd, yyyy, hh:mm a")));
    }
}
