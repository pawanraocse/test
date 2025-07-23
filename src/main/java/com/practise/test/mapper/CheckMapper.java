package com.practise.test.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CheckMapper {

    public static final String AFA_GET_USERS_RESPONSE = "{\n" +
        "    \"users\": [\n" +
        "        {\n" +
        "            \"UserName\": \"barack\",\n" +
        "            \"FullName\": \"barack obama\",\n" +
        "            \"Email\": \"barack@algosec.com\",\n" +
        "            \"AuthenticationType\": \"LOCAL\",\n" +
        "            \"LandingPage\": \"AUTOMATIC\",\n" +
        "            \"Administrator\": true,\n" +
        "            \"FireflowAdmin\": true,\n" +
        "            \"EnableAnalysisFromFile\": true,\n" +
        "            \"EnableGlobalCustomization\": true,\n" +
        "            \"AuthorizedDevices\": [\n" +
        "                {\n" +
        "                    \"ID\": \"ALL_FIREWALLS\",\n" +
        "                    \"Notification\": \"yes\",\n" +
        "                    \"Profile\": \"STANDARD\",\n" +
        "                    \"DisplayName\": \"ALL_FIREWALLS\"\n" +
        "                }\n" +
        "            ],\n" +
        "            \"AllowedActions\": [\n" +
        "                \"REPORT_ALL\",\n" +
        "                \"ACTION_ALL\",\n" +
        "                \"ACTION_VIEWS\"\n" +
        "            ]\n" +
        "        },\n" +
        "        {\n" +
        "            \"UserName\": \"admin\",\n" +
        "            \"FullName\": \"admin\",\n" +
        "            \"Email\": \"aa@algosec.com\",\n" +
        "            \"AuthenticationType\": \"LOCAL\",\n" +
        "            \"LandingPage\": \"AUTOMATIC\",\n" +
        "            \"Administrator\": true,\n" +
        "            \"FireflowAdmin\": true,\n" +
        "            \"EnableAnalysisFromFile\": true,\n" +
        "            \"EnableGlobalCustomization\": true,\n" +
        "            \"AuthorizedDevices\": [\n" +
        "                {\n" +
        "                    \"ID\": \"ALL_FIREWALLS\",\n" +
        "                    \"Notification\": \"yes\",\n" +
        "                    \"Profile\": \"STANDARD\"\n" +
        "                }\n" +
        "            ],\n" +
        "            \"AllowedActions\": [\n" +
        "                \"REPORT_ALL\",\n" +
        "                \"ACTION_ALL\",\n" +
        "                \"ACTION_VIEWS\"\n" +
        "            ]\n" +
        "        }\n" +
        "    ],\n" +
        "    \"status\": true\n" +
        "}";

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        final UsersResponse userObjects = objectMapper.readValue(AFA_GET_USERS_RESPONSE, UsersResponse.class);
        System.out.println("Completed read " + userObjects.getUsers().size());
    }

}
