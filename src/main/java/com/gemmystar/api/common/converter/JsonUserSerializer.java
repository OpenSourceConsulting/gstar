/**
 * 
 */
package com.gemmystar.api.common.converter;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.gemmystar.api.common.util.WebUtil;
import com.gemmystar.api.user.domain.GstarUser;

/**
 * @author Administrator
 *
 */
public class JsonUserSerializer extends JsonSerializer<GstarUser> {

	@Override
	public void serialize(GstarUser value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		gen.writeStartObject();
		
		gen.writeNumberField("id", value.getId());
		gen.writeStringField("name", value.getName());
		gen.writeStringField("nickname", value.getNickname());
		
		/*
		if (WebUtil.hasRole("ROLE_ADMIN")) {
			gen.writeStringField("email", value.getEmail());
			gen.writeStringField("genderCd", value.getGenderCd());
			gen.writeNumberField("age", value.getAge());
			gen.writeStringField("bankCmpyCd", value.getBankCmpyCd());
			gen.writeStringField("bankAccount", value.getBankAccount());
			gen.writeStringField("fcmToken", value.getFcmToken());
			gen.writeBooleanField("mobileNoti", value.isMobileNoti());
			gen.writeBooleanField("emailNoti", value.isEmailNoti());
			gen.writeStringField("mobileAppVer", value.getMobileAppVer());
			gen.writeStringField("createDate", JsonDateSerializer.format(value.getCreateDt()));
		}
		*/
		
		gen.writeEndObject();
	}

}
