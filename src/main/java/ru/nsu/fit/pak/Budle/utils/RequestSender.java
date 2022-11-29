package ru.nsu.fit.pak.Budle.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class RequestSender {
    public Map<String, Object> sendUCaller(String phoneNumber) throws IOException {
        if (phoneNumber.charAt(0) != '7') {
            phoneNumber = phoneNumber.substring(1);
        }
        StringBuilder url = new StringBuilder();
        System.out.println(phoneNumber);
        url.append("https://api.ucaller.ru/v1.0/initCall?");
        url.append("phone=").append(phoneNumber);
        url.append("&voice=").append("true");
        url.append("&key=1vvjxSFMby9xJx783gk31AT7UDPEHBdI");
        url.append("&service_id=317622");
        URL obj = new URL(url.toString());
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map
                = objectMapper.readValue(response.toString(), new TypeReference<>() {
        });

        System.out.println(map);
        return map;
    }
}
