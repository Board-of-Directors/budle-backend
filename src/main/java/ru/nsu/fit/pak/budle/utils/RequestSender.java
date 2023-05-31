package ru.nsu.fit.pak.budle.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


@Component
public class RequestSender {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public Map<String, Object> sendUCaller(String phoneNumber) {

        if (!phoneNumber.startsWith("7")) {
            phoneNumber = phoneNumber.substring(1);
        }

        String requestString = "https://api.ucaller.ru/v1.0/initCall?" +
                "phone=" + phoneNumber +
                "&voice=" + "false" +
                "&key=1vvjxSFMby9xJx783gk31AT7UDPEHBdI" +
                "&service_id=317622";

        Map<String, Object> map = null;

        try {
            logger.info("Request to UCaller API was sent");
            URL request = new URL(requestString);
            HttpURLConnection connection = (HttpURLConnection) request.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            logger.info("Response was received");
            ObjectMapper objectMapper = new ObjectMapper();
            map = objectMapper.readValue(
                    response.toString(), new TypeReference<>() {
                    });
            logger.debug("Response was: " + response);
            connection.disconnect();

        } catch (IOException exception) {
            logger.warn(exception.getMessage());
        }
        return map;
    }
}
