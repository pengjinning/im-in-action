package io.xiaper.longpolling.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Component
public class MessagePayloadUtil {

    private List<MessagePayload> deserialisePayloadList(final String sourceJson) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(sourceJson, objectMapper.getTypeFactory().constructCollectionType(List.class, MessagePayload.class));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private MessagePayload deserialisePayload(final String sourceJson) {
        try {
            return new ObjectMapper().readValue(sourceJson, MessagePayload.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String serialisePayload(final List<MessagePayload> messagePayload) {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(messagePayload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public String getMessagePayload() {
        try {
            final List<MessagePayload> messagePayload = new ArrayList<MessagePayload>();
            messagePayload.add(deserialisePayload(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(new MessagePayload())));
            return serialisePayload(messagePayload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String appendMessagePayload(final String sourceJson, final String appendJson) {
        final List<MessagePayload> sourcePayloadList = deserialisePayloadList(sourceJson);
        if (appendJson != null) {
            final List<MessagePayload> appendPayloadList = deserialisePayloadList(appendJson);
            return serialisePayload(
                    Stream.concat(
                            sourcePayloadList.stream(),
                            appendPayloadList.stream()
                    ).collect(toList()));
        }
        return sourceJson;
    }

    static class MessagePayload {

        private final DataFactory DATA_FACTORY = new DataFactory();

        private String address;
        private String city;
        private String email;
        private String businessName;
        private String name;

        public MessagePayload() {
            this.address = DATA_FACTORY.getAddress();
            this.city = DATA_FACTORY.getCity();
            this.email = DATA_FACTORY.getEmailAddress();
            this.businessName = DATA_FACTORY.getBusinessName();
            this.name = DATA_FACTORY.getName();
        }
    }
}
