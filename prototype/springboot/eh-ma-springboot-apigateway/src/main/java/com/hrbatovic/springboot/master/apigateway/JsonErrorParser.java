package com.hrbatovic.springboot.master.apigateway;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonErrorParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static String parseErrorMessage(String errorResponse) {
        try {
            JsonNode rootNode = objectMapper.readTree(errorResponse);
            return rootNode.path("message").asText();
        } catch (Exception ex) {
            return "An error occurred while processing the response.";
        }
    }
}
