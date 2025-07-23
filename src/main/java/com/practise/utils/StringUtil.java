package com.practise.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.util.function.Function;
import java.util.function.Predicate;

public class StringUtil {

    public static void main(String[] args) {
       // String jsonString = "{\"requestData\":{\"prompt\":\"Analyze the provided data and identify the well-known enterprise-based software applications mentioned in the Subject. Give a software application name for each ID which is best suited, their corresponding ID, Reason for this name. Explain why you consider this application name to be a valid one in the 'Reason' column. Consider only those application names for which you can guarantee as globally known applications. Do not give response for statements which does not refer to some well known application. Generate the response in CSV format with headers id, application, reason. Below are the statements in csv format with headers ID,Subject :\\\\n### Input:\\\\n${input}\\\\n### Response:\",\"temperature\":0.75,\"input\":\"1,1|AppViz Change Request for Test|Test\\n2,AppViz Change Request for tmp\",\"topP\":1,\"maxTokens\":800,\"model\":\"ai21.j2-ultra-v1\",\"tenantId\":\"1111112\",\"date\":\"2024-02-15T03:27:55.098Z\",\"output\":\"\\nID,Application,Reason\\n1,Test,\\\"Test is globally known as a software application.\\\"\\n2,tmp,\\\"tmp is not recognized as a globally known software application.\\\"\"}}";
        //final String s = parseString(jsonString, "requestData","output");
        //final String valueFromJson = getValueFromJson(jsonString, "requestData", "output");
        //System.out.println(valueFromJson);
        //String val = "tRuE";
        //final Boolean booleanValue = toBooleanValue(val, false);
        //System.out.println(booleanValue);
        System.out.println(checkReplaceRegex("tcp-100-200"));
        System.out.println(checkReplaceRegex("tcp/100-200"));
        System.out.println(checkReplaceRegex("tcp/-100-200"));
        System.out.println(checkReplaceRegex("tcp/-100/200"));
    }

    static String checkReplaceRegex(String input) {
        int index = input.indexOf('-');
        if (index > 0) { // Ensure that there's a hyphen after "tcp"
            String tcpPart = input.substring(0, index); // Extract the "tcp" part
            String remainingPart = input.substring(index); // Extract the remaining part
            if (index + 1 < input.length() && Character.isDigit(input.charAt(index + 1))) {
                if (tcpPart.contains("/")) {
                    return input; // If "tcp" already contains a slash, return the input unchanged
                }
                // Append a slash to the "tcp" part and rejoin with the remaining part
                return tcpPart + "/" + remainingPart.substring(1);
            }
        }
        return input; // Return the input unchanged if there's no hyphen after "tcp" or no number after "-"
    }

    static Boolean toBooleanValue(String stringValue, Boolean defaultValue) {
        return convertValue(stringValue, s -> s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false"), Boolean::valueOf, defaultValue);
    }

    static <T> T convertValue(String stringValue, Predicate<String> condition, Function<String, T> converter, T defaultValue) {
        return !stringValue.isEmpty() && condition.test(stringValue) ? converter.apply(stringValue) : defaultValue;
    }

    public static String getValueFromJson(String jsonString, String... keys) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(jsonString);
            for (String key : keys) {
                rootNode = rootNode.get(key);
                if (rootNode == null) {
                    throw new IllegalArgumentException("Key '" + key + "' not found");
                }
            }
            return rootNode.asText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String parseString(String str, String key, String key2) {
        JSONObject jsonObject = new JSONObject(str);
        JSONObject requestData = jsonObject.getJSONObject(key);
        return requestData.getString(key2);
    }

}
