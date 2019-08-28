package org.utkuozdemir;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class MappingJackson2HttpMessageWithContentLengthConverter extends MappingJackson2HttpMessageConverter {
	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {

		JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
		// The following has been deprecated as late as Jackson 2.2 (April 2013);
		// preserved for the time being, for Jackson 2.0/2.1 compatibility.
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		@SuppressWarnings("deprecation")
		JsonGenerator jsonGenerator =
				this.getObjectMapper().getJsonFactory().createJsonGenerator(byteArrayOutputStream, encoding);

		// A workaround for JsonGenerators not applying serialization features
		// https://github.com/FasterXML/jackson-databind/issues/12
		if (this.getObjectMapper().isEnabled(SerializationFeature.INDENT_OUTPUT)) {
			jsonGenerator.useDefaultPrettyPrinter();
		}

		try {
			this.getObjectMapper().writeValue(jsonGenerator, object);

			//--> set content length
			outputMessage.getHeaders().setContentLength(byteArrayOutputStream.size());
			byteArrayOutputStream.writeTo(outputMessage.getBody());
			byteArrayOutputStream.flush();
		}
		catch (JsonProcessingException ex) {
			throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
		}	}
}