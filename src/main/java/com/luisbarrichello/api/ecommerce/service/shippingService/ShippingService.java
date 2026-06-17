package com.luisbarrichello.api.ecommerce.service.shippingService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luisbarrichello.api.ecommerce.dto.shippingService.ShippingRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ShippingService {
    @Value("${api.melhorEnvio.url}")
    private String melhorEnvioApiUrl;

    @Value("${api.melhorenvio.token}")
    private String accessToken;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String calculateShipping(ShippingRequestDTO shippingRequestDTO) {
        try {
            String requestBody = convertToJson(shippingRequestDTO);
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(melhorEnvioApiUrl))
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            HttpResponse<String> response = null;

            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error calculating shipping:" + e.getMessage());
        }
    }

    private String convertToJson(ShippingRequestDTO shippingRequestDTO) {
        try {
            return objectMapper.writeValueAsString(shippingRequestDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao converter ShippingRequestDTO para JSON: " + e.getMessage());
        }
    }
}
