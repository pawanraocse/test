package com.practise.saas;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class SaasTest {

    public static void main(String[] args) {
        SaasTest saasTest = new SaasTest();
        saasTest.createSaasURL();
       // System.out.println(saasTest.isCloudEnabled());
       // System.out.println(saasTest.isCloudEnabled());
       // final String s = saasTest.fetchAppVizURL(false);
       // System.out.println(s);
    }

    private static final String PROTOCOL = "https://";
    private static final String SAAS_URL_SUFFIX = ".app.algosec.com/";
    private static final String COM_ALGOSAAS_LOGIN = "https://us.app.algosec.com/algosaas/login";
    private final Map<String, String> urlSessionMap = new HashMap<>();


    Map<String, String> sessionMap = new HashMap<>();

    String CLOUDFLOW_CONFIG_JSON = "C:\\prototype\\Task\\kafka.json";
    //String backupFile = "/home/afa/.fa/kafka/cloudflow/config.json.orig";

    String CLOUDFLOW_CONFIG_JSON_ORIG = "C:\\prototype\\Task\\kafka.json.orig";
    String protocol = "https://";
    String urlSuffix = ".app.algosec.com/";

    private long appVizConfigFileLastModified;
    private boolean appVizCloudEnabledParamsInConfig;
    public boolean isCloudEnabled() {
        File configFile = null;
        if (new File(CLOUDFLOW_CONFIG_JSON_ORIG).exists()) {
            configFile = new File(CLOUDFLOW_CONFIG_JSON_ORIG);
        } else if (new File(CLOUDFLOW_CONFIG_JSON).exists()) {
            configFile = new File(CLOUDFLOW_CONFIG_JSON);
        }

        if (configFile != null) {
            final long lastModified = configFile.lastModified();
            if (lastModified > appVizConfigFileLastModified) {
                appVizConfigFileLastModified = lastModified;
                JSONParser parser = new JSONParser();
                try {
                    Object obj = parser.parse(new FileReader(configFile));
                    JSONObject jsonObject = (JSONObject) obj;
                    if (jsonObject != null && jsonObject.containsKey("avc-sendParams")) {
                        final JSONObject avcSendParams = (JSONObject) jsonObject.get("avc-sendParams");
                        if (avcSendParams != null && avcSendParams.containsKey("topic")) {
                            final String topic = ((String) avcSendParams.get("topic")).trim();
                            appVizCloudEnabledParamsInConfig = !topic.isEmpty();
                        }
                    }
                } catch (IOException | ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return appVizCloudEnabledParamsInConfig;
    }

    private void createSaasURL() {
        boolean isAppVizCloudEnabled = true;
        String product = "appviz";
        String url = "https://us.app.algosec.com/algosaas/login";

        final String hostProductKey = "host_" + product;

        if (sessionMap.containsKey(hostProductKey)) {
            url = sessionMap.get(hostProductKey);
        } else {
            if (new File(CLOUDFLOW_CONFIG_JSON_ORIG).exists()) {
                CLOUDFLOW_CONFIG_JSON = CLOUDFLOW_CONFIG_JSON_ORIG;
            } else if (!new File(CLOUDFLOW_CONFIG_JSON).exists()) {
                CLOUDFLOW_CONFIG_JSON = null;
            }
            if (CLOUDFLOW_CONFIG_JSON != null && isAppVizCloudEnabled) {
                JSONParser parser = new JSONParser();
                try {
                    Object obj = parser.parse(new FileReader(CLOUDFLOW_CONFIG_JSON));
                    JSONObject jsonObject = (JSONObject) obj;

                    if (jsonObject != null && jsonObject.containsKey("kafkaHosts")) {
                        final JSONArray kafkaHosts = (JSONArray) jsonObject.get("kafkaHosts");
                        final String hostPort = (String) kafkaHosts.get(0);
                        final String[] hostPortArray = hostPort.split(":");
                        if (hostPortArray.length > 0) {
                            String host = hostPortArray[0];
                            String[] hostComponent = host.split("\\.");

                            if (hostComponent.length > 1) {
                                String regionOrEnv = hostComponent[1];
                                url = protocol + regionOrEnv + urlSuffix + product;
                            }

                            final JSONObject customerParams = (JSONObject) jsonObject.get("customerParams");
                            if (customerParams != null && customerParams.containsKey("tenantId")) {
                                final String tenantId = ((String) customerParams.get("tenantId")).trim();
                                if (!tenantId.isEmpty()) {
                                    url = url + "?tenantId=" + tenantId;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                sessionMap.put(hostProductKey, url);
            } else {
                if (isAppVizCloudEnabled) {
                    url = "https://us.app.algosec.com/algosaas/login";
                } else {
                    url = "/BusinessFlow";
                    sessionMap.put(hostProductKey, url);
                }
            }
        }
        System.out.println("final url " + url);
        String linkTemplate = "/BusinessFlow/application/%d/rule_application_dashboard";

        final String replace = url.replace("/appviz", linkTemplate);
        System.out.println(replace);
    }


    private String fetchAppVizURL(boolean considerOnlyAppVizCloudEnabledFlag) {
        final String product = "appviz";
        final boolean isAppVizCloudEnabled = true;
        String url;
        final String hostProductKey = "host_" + product;

        if (urlSessionMap.containsKey(hostProductKey)) {
            url = urlSessionMap.get(hostProductKey);
        } else {
            url = COM_ALGOSAAS_LOGIN;
            String fileToRead = null;
            if (Files.exists(Paths.get(CLOUDFLOW_CONFIG_JSON_ORIG))) {
                fileToRead = CLOUDFLOW_CONFIG_JSON_ORIG;
            } else if (Files.exists(Paths.get(CLOUDFLOW_CONFIG_JSON))) {
                fileToRead = CLOUDFLOW_CONFIG_JSON;
            }
            if (fileToRead != null && isAppVizCloudEnabled) {
                final JSONParser parser = new JSONParser();
                url = parseCloudFlowConfigFileToGetTheURL(product, url, fileToRead, parser);
                urlSessionMap.put(hostProductKey, url);
            } else {
                if (isAppVizCloudEnabled) {
                    url = COM_ALGOSAAS_LOGIN;
                } else {
                    url = "/BusinessFlow";
                    urlSessionMap.put(hostProductKey, url);
                }
            }
        }
        System.out.println("final url is: " + url);
        return url;
    }

    private static String parseCloudFlowConfigFileToGetTheURL(final String product, String url, final String fileToRead, final JSONParser parser) {
        try {
            final JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(fileToRead));
            url = parseCloudFlowJsonToCreateURL(product, url, jsonObject);
        } catch (Exception e) {
            System.out.println("Failed to saas config file");
        }
        return url;
    }

    private static String parseCloudFlowJsonToCreateURL(final String product, String url, final JSONObject jsonObject) {
        if (jsonObject != null && jsonObject.containsKey("kafkaHosts")) {
            final JSONArray kafkaHosts = (JSONArray) jsonObject.get("kafkaHosts");
            final String hostPort = (String) kafkaHosts.get(0);
            url = splitHostPortToGetURL(product, url, jsonObject, hostPort);
        }
        return url;
    }

    private static String splitHostPortToGetURL(final String product, String url, final JSONObject jsonObject, final String hostPort) {
        final String[] hostPortArray = hostPort.split(":");
        if (hostPortArray.length > 0) {
            final String host = hostPortArray[0];
            url = appendProtocolRegionToHostInURL(product, url, host);
            System.out.println("url constructed is: " + url);
            url = checkAndAppendTenantId(url, jsonObject);
        }
        return url;
    }

    private static String appendProtocolRegionToHostInURL(final String product, String url, final String host) {
        final String[] hostComponent = host.split("\\.");
        if (hostComponent.length > 1) {
            final String regionOrEnv = hostComponent[1];
            url = PROTOCOL + "feature-cs-6844" + ".dev.cloudflow.algosec.com/" + product;
        }
        return url;
    }

    private static String checkAndAppendTenantId(String url, final JSONObject jsonObject) {
        final JSONObject customerParams = (JSONObject) jsonObject.get("customerParams");
        if (customerParams != null) {
            System.out.println(customerParams.toJSONString());
        }
        if (customerParams != null && customerParams.containsKey("tenantId")) {
            final String tenantId = ((String) customerParams.get("tenantId")).trim();
            if (!tenantId.isEmpty()) {
                url = url + "?tenantId=" + tenantId;
            }
        }
        return url;
    }

}
