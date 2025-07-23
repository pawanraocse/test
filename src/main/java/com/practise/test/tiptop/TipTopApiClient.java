package com.practise.test.tiptop;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Pawan
 */
public class TipTopApiClient {

    public static final String PUT = "PUT";
    private static final String AUTH_URL = "https://api.ms.digital-nirvana.com/prod/api-v1-0-0/login";
    private static final String PROCESS_URL = "https://api.ms.digital-nirvana.com/prod/api-v1-0-0/process";
    private static final String JOBS_URL = "https://api.ms.digital-nirvana.com/prod/api-v1-0-0/jobs";
    private static final String PRE_SIGNED_URL_ENDPOINT = "https://api.ms.digital-nirvana.com/prod/api-v1-0-0/pre-signed-url";

    private static final String USERNAME = "<your-username>";
    private static final String PASSWORD = "<your-password>";
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE_VALUE = "application/json";
    public static final String POST = "POST";
    public static final String ACCEPT = "Accept";
    public static final String GET = "GET";
    public static final String BEARER = "Bearer ";
    public static final String PRE_SIGNED_URL_CONSTANT = "pre_signed_url";
    public static final String JOB_ID_CONSTANT = "job_Id";
    public static final String DATA_CONSTANT = "data";
    public static final String PROCESS_LIST_CONSTANT = "process_list";
    private static final int MAX_REFRESH_ATTEMPTS = 5;
    public static final int BUFFER_SIZE = 4096;
    private int refreshAttempts = 0;

    private AuthResponse authResponse = null;
    private List<TipTipProcessInfo> processList = null;

    private static final ThreadLocal<ObjectMapper> objectMapperThreadLocal = ThreadLocal.withInitial(ObjectMapper::new);
    private static final String S3_PATH_CONSTANT = "s3_path";

    public static void main(String[] args) {
        TipTopApiClient apiClient = new TipTopApiClient();

        try {
            AuthResponse authResponse = apiClient.authenticateAndGetToken();
            System.out.println("Access Token: " + authResponse.accessToken);
            System.out.println("Auth ID: " + authResponse.authId);

            apiClient.getProcessList(USERNAME);

            PreSignedURLResponse preSignedUrl = apiClient.getPreSignedUrl("example.mp4");
            System.out.println("Pre-Signed URL: " + preSignedUrl);

            File mediaFile = new File("<path-to-your-media-file>");
            apiClient.uploadMediaFile(preSignedUrl.pre_signed_url, mediaFile);

            long jobId = apiClient.createJob(192,
                    "test-02", "uploads/jobs/2021-09-02/26/test_02.mp4",  8, "");
            System.out.println("Job created with ID: " + jobId);

            int jobId_live = apiClient.createEventJob(192, "My Event",
                "2023-12-31T23:59:59Z", "https://example.com", "1234",
                "123456", "987654321");
            System.out.println("Event job created with ID: " + jobId_live);

            List<TipTopJobDetails> jobDetailsList = apiClient.getAllJobDetails(1, 10, "id", "DESC");
            System.out.println("Jobs Details: " + jobDetailsList);

            TipTopJobDetails jobDetails1 = apiClient.getJobDetails(123);
            System.out.println("Job Details: " + jobDetails1);

            apiClient.downloadJobResult(12345, "");
            int jobId_number = 12345;
            Future<TipTopJobDetails> jobDetailsFuture = apiClient.executeAsyncApiToStatusCheck(jobId_number);
            Future<Void> downloadFuture = apiClient.downloadJobResultAsync(jobId_number, "");

            TipTopJobDetails jobDetails = jobDetailsFuture.get();
            System.out.println("Job Details: " + jobDetails);

            downloadFuture.get();
            System.out.println("Download completed successfully.");

        } catch (IOException e) {
            System.err.println("An error occurred during API call: " + e.getMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            throw new RuntimeException("Execution exception occurred: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted exception occurred: " + e.getMessage(), e);
        }
    }

    public Future<TipTopJobDetails> executeAsyncApiToStatusCheck(long jobId) {
        return executorService.submit(() -> {
            return getJobDetails( jobId);
        });
    }

    public Future<Void> downloadJobResultAsync(long jobId, String filePath) {
        return executorService.submit(() -> {
            downloadJobResult( jobId, filePath);
            return null;
        });
    }

    public long uploadFile(Path filePath) throws IOException {
        if (this.authResponse == null) {
            this.authenticateAndGetToken();
        } else {
            refreshToken();
        }
        String fileName = filePath.getFileName().toString();
        PreSignedURLResponse preSignedURLResponse = getPreSignedUrl(fileName);
        uploadMediaFile(preSignedURLResponse.pre_signed_url, filePath.toFile());
        processListForUser();
        TipTipProcessInfo tipTipProcessInfo = this.processList.get(0);
        int processId = tipTipProcessInfo.getId();
        return createJob(processId, fileName, preSignedURLResponse.s3_path, null, null);
    }

    public String checkJobStatus(long jobId) throws IOException {
        TipTopJobDetails jobDetails = getJobDetails(jobId);
        return jobDetails.getJobStatus();
    }


    private void processListForUser() throws IOException {
        if (this.processList == null) {
            this.processList = getProcessList(USERNAME);
        }
    }

    /*Authentication APi's start*/
    private AuthResponse authenticateAndGetToken() throws IOException {
        URL url = new URL(AUTH_URL);
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(POST);
            connection.setRequestProperty(CONTENT_TYPE, CONTENT_TYPE_VALUE);
            connection.setRequestProperty(ACCEPT, CONTENT_TYPE_VALUE);
            String authString = USERNAME + ":" + PASSWORD;
            String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes());
            connection.setRequestProperty(AUTHORIZATION, "Basic " + encodedAuthString);
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = objectMapperThreadLocal.get();
                this.authResponse = objectMapper.readValue(connection.getInputStream(), AuthResponse.class);
                return this.authResponse;
            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                throw new UnauthorizedAccessException("Unauthorized access. Please check your credentials.");
            } else {
                throw new IOException("Token refresh failed with status code: " + responseCode);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void refreshToken() throws IOException {
        //if (refreshAttempts >= MAX_REFRESH_ATTEMPTS) {
         //   throw new IOException("Max token refresh attempts reached");
      //  }
        refreshAttempts++;
        URL url = new URL(AUTH_URL);
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(POST);
            connection.setRequestProperty(CONTENT_TYPE, CONTENT_TYPE_VALUE);
            connection.setRequestProperty(ACCEPT, CONTENT_TYPE_VALUE);
            String authString = USERNAME + ":" + PASSWORD;
            String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes());
            connection.setRequestProperty(AUTHORIZATION, "Basic " + encodedAuthString);
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = objectMapperThreadLocal.get();
                this.authResponse = objectMapper.readValue(connection.getInputStream(), AuthResponse.class);
            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                throw new UnauthorizedAccessException("Unauthorized access. Please check your credentials.");
            } else {
                throw new IOException("Token refresh failed with status code: " + responseCode);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /*Authentication Api end*/

    private PreSignedURLResponse getPreSignedUrl(String fileName) throws IOException {
        HttpURLConnection connection = null;
        try {
            String apiUrl = String.format("%s?action=upload-file&file_name=%s&auth_id=%s", PRE_SIGNED_URL_ENDPOINT, fileName, this.authResponse.authId);
            URL url = new URL(apiUrl);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(GET);
            connection.setRequestProperty(AUTHORIZATION, BEARER + this.authResponse.accessToken);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    String jsonResponse = response.toString();
                    return parsePreSignedUrl(jsonResponse);
                }
            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                refreshToken();
                return getPreSignedUrl(fileName);
            } else {
                throw new IOException("Failed to get pre-signed URL. Response code: " + responseCode);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private PreSignedURLResponse parsePreSignedUrl(String jsonResponse) {
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        String preSignedUrl = Optional.ofNullable(jsonObject.getAsJsonObject(DATA_CONSTANT))
            .map(dataObject -> dataObject.get(PRE_SIGNED_URL_CONSTANT))
            .map(JsonElement::getAsString)
            .orElse(null);
        String s3Path = Optional.ofNullable(jsonObject.getAsJsonObject(DATA_CONSTANT))
            .map(dataObject -> dataObject.get(S3_PATH_CONSTANT))
            .map(JsonElement::getAsString)
            .orElse(null);
        return new PreSignedURLResponse(preSignedUrl, s3Path);
    }

    private void uploadMediaFile(String preSignedUrl, File mediaFile) throws IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(preSignedUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(PUT);
            connection.setRequestProperty(CONTENT_TYPE, "application/octet-stream");
            connection.setDoOutput(true);

            try (OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
                 InputStream inputStream = new BufferedInputStream(Files.newInputStream(mediaFile.toPath()))) {
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Media file uploaded successfully.");
            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                refreshToken();
                uploadMediaFile(preSignedUrl, mediaFile);
            } else {
                throw new IOException("Failed to upload media file. Response code: " + responseCode);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private List<TipTipProcessInfo> getProcessList(String username) throws IOException {
        String urlString = PROCESS_URL + "?username=" + username + "&data=processList";
        URL url = new URL(urlString);
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(GET);
            connection.setRequestProperty(AUTHORIZATION, BEARER + this.authResponse.accessToken);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                final ObjectMapper objectMapper = objectMapperThreadLocal.get();
                JsonNode rootNode = objectMapper.readTree(connection.getInputStream());
                JsonNode processList = rootNode.path(DATA_CONSTANT).path(PROCESS_LIST_CONSTANT);
                List<TipTipProcessInfo> processes = new ArrayList<>();
                for (JsonNode processNode : processList) {
                    TipTipProcessInfo process = new TipTipProcessInfo();
                    process.setId(processNode.path("id").asInt());
                    process.setProcessName(processNode.path("process_name").asText());
                    process.setTranceProcess(processNode.path("trance_process").asText());
                    process.setAtType(processNode.path("at_type").asText());

                    JsonNode mediaInputFormats = processNode.path("media_input_formats");
                    for (JsonNode mediaInputFormatNode : mediaInputFormats) {
                        TipTopMediaInputFormat mediaInputFormat = new TipTopMediaInputFormat();
                        mediaInputFormat.setId(mediaInputFormatNode.path("id").asInt());
                        mediaInputFormat.setName(mediaInputFormatNode.path("name").asText());
                        process.addMediaInputFormat(mediaInputFormat);
                    }
                    processes.add(process);
                }
                return processes;
            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                refreshToken();
                return getProcessList(username);
            } else {
                throw new IOException("Failed to fetch process list. Response code: " + responseCode);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private long createJob(int processId, String assetName, String assetSource
            ,Integer tat, String clientUniqueId) throws IOException {
        URL url = new URL(JOBS_URL);
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(POST);
            connection.setRequestProperty(AUTHORIZATION, BEARER + this.authResponse.accessToken);
            connection.setRequestProperty(CONTENT_TYPE, CONTENT_TYPE_VALUE);
            connection.setDoOutput(true);

            String jsonPayload = "{\"process_id\":" + processId +
                ",\"action\":\"s3_upload\"" +
                ",\"asset_name\":\"" + assetName + "\"" +
                ",\"asset_source\":\"" + assetSource + "\"" +
                    ",\"auth_id\":" + this.authResponse.authId +
                (tat != null ? ",\"tat\":" + tat : "") +
                (clientUniqueId != null ? ",\"client_unique_id\":\"" + clientUniqueId + "\"" : "") +
                "}";

            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(jsonPayload.getBytes());
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                final ObjectMapper objectMapper = objectMapperThreadLocal.get();
                JsonNode rootNode = objectMapper.readTree(connection.getInputStream());
                return rootNode.path(DATA_CONSTANT).path(JOB_ID_CONSTANT).asLong();
            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                refreshToken();
                return createJob(processId, assetName, assetSource,
                        tat, clientUniqueId);
            } else {
                throw new IOException("Failed to create job. Response code: " + responseCode);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private int createEventJob(int processId, String eventTitle,
                               String eventUtcDateTime, String eventUrl, String eventPasscode,
                               String eventDialInNumber, String eventDialInPasscode) throws IOException {
        URL url = new URL(JOBS_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(POST);
        connection.setRequestProperty(CONTENT_TYPE, CONTENT_TYPE_VALUE);
        connection.setRequestProperty(AUTHORIZATION, BEARER + this.authResponse.accessToken);
        connection.setDoOutput(true);

        ObjectMapper objectMapper = objectMapperThreadLocal.get();
        ObjectNode payloadNode = objectMapper.createObjectNode()
            .put("process_id", processId)
            .put("action", "event");

        ObjectNode eventDetailsNode = objectMapper.createObjectNode()
            .put("event_title", eventTitle)
            .put("event_utc_date_time", eventUtcDateTime)
            .put("event_url", eventUrl)
            .put("event_passcode", eventPasscode)
            .put("event_dial_in_number", eventDialInNumber)
            .put("event_dial_in_passcode", eventDialInPasscode);

        payloadNode.set("event_details", eventDetailsNode);

        try (OutputStream outputStream = connection.getOutputStream();
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
            objectMapper.writeValue(writer, payloadNode);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            JsonNode rootNode = objectMapper.readTree(connection.getInputStream());
            return rootNode.path(DATA_CONSTANT).path(JOB_ID_CONSTANT).asInt();
        } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
            refreshToken();
            return createEventJob(processId, eventTitle, eventUtcDateTime,
                eventUrl, eventPasscode, eventDialInNumber, eventDialInPasscode);
        } else {
            throw new IOException("Failed to create event job. Response code: " + responseCode);
        }
    }

    private List<TipTopJobDetails> getAllJobDetails(int page, int limit, String colName, String sort) throws IOException {
        String urlString = JOBS_URL + "?auth_id=" + this.authResponse.authId +
            "&page=" + page +
            "&limit=" + limit +
            "&col_name=" + colName +
            "&sort=" + sort;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(GET);
        connection.setRequestProperty(AUTHORIZATION, BEARER + this.authResponse.accessToken);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return parseJobDetails(response.toString());
        } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
            refreshToken();
            return getAllJobDetails(page, limit, colName, sort);
        } else {
            throw new IOException("Failed to fetch job details. Response code: " + responseCode);
        }
    }

    private List<TipTopJobDetails> parseJobDetails(String jsonResponse) throws IOException {
        List<TipTopJobDetails> jobDetailsList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        JsonNode itemsNode = rootNode.get("data").get("items");
        for (JsonNode itemNode : itemsNode) {
            TipTopJobDetails jobDetails = new TipTopJobDetails();
            jobDetails.setProcessName(itemNode.get("process_name").asText());
            jobDetails.setId(itemNode.get("id").asLong());
            jobDetails.setTat(itemNode.get("tat").asDouble());
            jobDetails.setUsername(itemNode.get("username").asText());
            jobDetails.setCompany(itemNode.get("company").asText());
            jobDetails.setUserId(itemNode.get("user_id").asLong());
            jobDetails.setDisplayName(itemNode.get("display_name").asText());
            jobDetails.setAssetName(itemNode.get("asset_name").asText());
            jobDetails.setMediaDuration(itemNode.get("media_duration").asDouble());
            jobDetails.setJobStatus(itemNode.get("job_status").asText());
            jobDetails.setCreatedAt(itemNode.get("created_at").asText());
            jobDetails.setUpdatedAt(itemNode.get("updated_at").asText());
            jobDetails.setErrorMessage(itemNode.get("error_message").asText());
            jobDetails.setTotalPrice(itemNode.get("total_price").asDouble());

            // Parse services
            List<TipTopService> services = new ArrayList<>();
            JsonNode servicesNode = itemNode.get("services");
            if (servicesNode != null) {
                for (JsonNode serviceNode : servicesNode) {
                    TipTopService service = new TipTopService();
                    service.setJobId(serviceNode.get("job_id").asLong());
                    service.setStatus(serviceNode.get("status").asText());
                    service.setErrorMessage(serviceNode.get("error_message").asText());
                    service.setOutputType(serviceNode.get("output_type").asInt());
                    service.setId(serviceNode.get("id").asLong());
                    service.setServiceName(serviceNode.get("service_name").asText());
                    service.setServiceType(serviceNode.get("service_type").asText());
                    services.add(service);
                }
            }
            jobDetails.setServices(services);

            jobDetailsList.add(jobDetails);
        }
        return jobDetailsList;
    }

    private TipTopJobDetails getJobDetails(long jobId) throws IOException {
        HttpURLConnection connection = null;
        try {
            String urlString = JOBS_URL + "?auth_id=" + this.authResponse.authId + "&id=" + jobId;
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(GET);
            connection.setRequestProperty(AUTHORIZATION, BEARER + this.authResponse.accessToken);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return parseJobDetails(response.toString()).get(0);
            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                refreshToken();
                return getJobDetails(jobId);
            } else {
                throw new IOException("Failed to fetch job details. Response code: " + responseCode);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void downloadJobResult(long jobId, String saveFilePath) throws IOException {
        String urlString = PRE_SIGNED_URL_ENDPOINT + "?action=download-file&id=" + jobId;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(GET);
        connection.setRequestProperty(AUTHORIZATION, BEARER + this.authResponse.accessToken);

        int responseCode = connection.getResponseCode();
        try {
            if (responseCode == HttpURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(connection.getInputStream());
                String preSignedUrl = rootNode.path(DATA_CONSTANT).path(PRE_SIGNED_URL_CONSTANT).asText();
                saveFileFromS3(preSignedUrl, saveFilePath);
            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                refreshToken();
                downloadJobResult(jobId, saveFilePath);
            } else {
                throw new IOException("Failed to download job result. Response code: " + responseCode);
            }
        } finally {
            connection.disconnect();
        }
    }

    private void saveFileFromS3(String preSignedUrl, String saveFilePath) throws IOException {
        URL url = new URL(preSignedUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(GET);
        connection.setDoOutput(false);
        connection.connect();

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Failed to download file from S3. Status code: " + responseCode);
        }
        try (InputStream inputStream = connection.getInputStream();
             OutputStream outputStream = Files.newOutputStream(Paths.get(saveFilePath))) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            connection.disconnect();
            throw e;
        } finally {
            connection.disconnect();
        }
    }


    private static class AuthResponse {

        private final String accessToken;
        private final String authId;

        public AuthResponse(String accessToken, String authId) {
            this.accessToken = accessToken;
            this.authId = authId;
        }
    }

    private static class PreSignedURLResponse {
        private final String pre_signed_url;
        private final String s3_path;

        private PreSignedURLResponse(String preSignedUrl, String s3Path) {
            pre_signed_url = preSignedUrl;
            s3_path = s3Path;
        }
    }

}

