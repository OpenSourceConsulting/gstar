
package com.gemmystar.api.common.converter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * <pre>
 * 
 * </pre>
 * @author Bongjin Kwon
 * @version 1.0
 */
@Component
public class JsonDateSerializer extends JsonSerializer<Date> {
	
	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@Override
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		String formattedDate = df.format(value);
		
		jgen.writeString(formattedDate);
		
	}
	
	public static String format(Date date) {
		return df.format(date);
	}

}
//end of JsonDateSerializer.java