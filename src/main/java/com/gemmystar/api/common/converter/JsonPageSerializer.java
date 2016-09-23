package com.gemmystar.api.common.converter;

import java.io.IOException;

import org.springframework.data.domain.PageImpl;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonPageSerializer extends JsonSerializer<PageImpl> {
	
	@Override
	public void serialize(PageImpl value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		gen.writeStartObject();
        gen.writeNumberField("totalElements",value.getTotalElements());
        gen.writeNumberField("numberOfElements",value.getNumberOfElements());
        gen.writeFieldName("content");
        serializers.defaultSerializeValue(value.getContent(), gen);
        
        gen.writeNumberField("totalPages", value.getTotalPages());
        gen.writeBooleanField("first", value.isFirst());
        gen.writeBooleanField("last", value.isLast());
        gen.writeNumberField("number", value.getNumber());
        gen.writeNumberField("size", value.getSize());
        gen.writeEndObject();
		
	}

}
