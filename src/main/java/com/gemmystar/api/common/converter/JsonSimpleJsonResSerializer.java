/**
 * 
 */
package com.gemmystar.api.common.converter;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.gemmystar.api.common.model.SimpleJsonResponse;

/**
 * @author Administrator
 *
 */
public class JsonSimpleJsonResSerializer extends JsonSerializer<SimpleJsonResponse> {

	@Override
	public void serialize(SimpleJsonResponse value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		gen.writeStartObject();
        gen.writeBooleanField("success", value.isSuccess());
        gen.writeStringField("msg", value.getMsg());
        gen.writeFieldName("data");
        serializers.defaultSerializeValue(value.getData(), gen);
        gen.writeEndObject();
		
	}

}
