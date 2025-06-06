/***
 * @author IPLocate.io.
 * @version 1.0.0
 */

 package io.iplocate.client;

 import java.io.IOException;
 import java.util.Objects;
 import java.util.concurrent.TimeUnit;

 import com.fasterxml.jackson.databind.DeserializationFeature;
 import com.fasterxml.jackson.databind.ObjectMapper;

 import io.iplocate.client.exceptions.IPLocateApiException;
 import io.iplocate.client.exceptions.IPLocateApiKeyException;
 import io.iplocate.client.exceptions.IPLocateException;
 import io.iplocate.client.exceptions.IPLocateInvalidIPException;
 import io.iplocate.client.exceptions.IPLocateNotFoundException;
 import io.iplocate.client.exceptions.IPLocateRateLimitException;
 import io.iplocate.client.exceptions.IPLocateServiceException;
 import io.iplocate.client.model.ErrorResponse;
 import io.iplocate.client.model.IPLocateResponse;
 import okhttp3.HttpUrl;
 import okhttp3.OkHttpClient;
 import okhttp3.Request;
 import okhttp3.Response;
 import okhttp3.ResponseBody;
 
 /**
  * Client for accessing the IPLocate.io API using OkHttp.
  */
 public class IPLocateClient {
     private static final String DEFAULT_BASE_URL = "https://iplocate.io/api";
     private static final int DEFAULT_CONNECT_TIMEOUT_MS = 5000; // 5 seconds
     private static final int DEFAULT_READ_TIMEOUT_MS = 10000; // 10 seconds
     private static final int DEFAULT_CALL_TIMEOUT_MS = 15000; // 15 seconds (total tim for call)
 
     private static final String HEADER_ACCEPT = "Accept";
     private static final String HEADER_USER_AGENT = "User-Agent";
     private static final String CONTENT_TYPE_JSON = "application/json";
     private static final String QUERY_PARAM_API_KEY = "apikey";
     private static final String DEFAULT_USER_AGENT = "IPLocateClient-OkHttp/1.0";
 
     private final String apiKey;
     private final HttpUrl baseUrl;
     private final OkHttpClient httpClient;
     private final ObjectMapper objectMapper;
     private final String userAgent;
 
     /**
      * Creates a new IPLocateClient with the given API key and default settings.
      *
      * @param apiKey Your IPLocate.io API key.
      */
     public IPLocateClient(String apiKey) {
         this(apiKey, DEFAULT_BASE_URL, DEFAULT_CONNECT_TIMEOUT_MS, DEFAULT_READ_TIMEOUT_MS, DEFAULT_CALL_TIMEOUT_MS, DEFAULT_USER_AGENT);
     }
 
     /**
      * Creates a new IPLocateClient with the given API key, custom base URL and default timeouts.
      *
      * @param apiKey  Your IPLocate.io API key.
      * @param baseUrl The base URL for the IPLocate API.
      */
     public IPLocateClient(String apiKey, String baseUrl) {
         this(apiKey, baseUrl, DEFAULT_CONNECT_TIMEOUT_MS, DEFAULT_READ_TIMEOUT_MS, DEFAULT_CALL_TIMEOUT_MS, DEFAULT_USER_AGENT);
     }
 
     /**
      * Creates a new IPLocateClient with full customization.
      *
      * @param apiKey            Your IPLocate.io API key.
      * @param baseUrlStr        The base URL for the IPLocate API 
      * @param connectTimeoutMs  Connection timeout in millis.
      * @param readTimeoutMs     Read timeout in millis.
      * @param callTimeoutMs     Call timeout in millis (total time for an API call).
      * @param userAgent         Custom User-Agent string.
      */
     public IPLocateClient(String apiKey, String baseUrlStr, int connectTimeoutMs, int readTimeoutMs, int callTimeoutMs, String userAgent) {
         if (apiKey == null || apiKey.trim().isEmpty()) {
             throw new IllegalArgumentException("API key cannot be null or empty.");
         }
         if (baseUrlStr == null || baseUrlStr.trim().isEmpty()) {
             throw new IllegalArgumentException("Base URL cannot be null or empty.");
         }
         if (connectTimeoutMs < 0 || readTimeoutMs < 0 || callTimeoutMs < 0) {
             throw new IllegalArgumentException("Timeouts cannot be negative.");
         }
 
         this.apiKey = apiKey;
         HttpUrl parsedBaseUrl = HttpUrl.parse(baseUrlStr);
         if (parsedBaseUrl == null) {
             throw new IllegalArgumentException("Invalid base URL format: " + baseUrlStr);
         }

         if (!parsedBaseUrl.encodedPath().endsWith("/")) {
             this.baseUrl = parsedBaseUrl.newBuilder().addPathSegment("").build();
         } else {
             this.baseUrl = parsedBaseUrl;
         }
 
         this.userAgent = (userAgent == null || userAgent.trim().isEmpty()) ? DEFAULT_USER_AGENT : userAgent;
 
         this.httpClient = new OkHttpClient.Builder()
                 .connectTimeout(connectTimeoutMs, TimeUnit.MILLISECONDS)
                 .readTimeout(readTimeoutMs, TimeUnit.MILLISECONDS)
                 .callTimeout(callTimeoutMs, TimeUnit.MILLISECONDS)
                 .build();
 
         this.objectMapper = new ObjectMapper();
         this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
     }
 
 
     /**
      * Looks up information for the specified IP address.
      *
      * @param ipAddress The IPv4 or IPv6 address to look up.
      * @return An {@link IPLocateResponse} containing the IP information.
      * @throws IPLocateException If an error occurs during the API call.
      */
     public IPLocateResponse lookup(String ipAddress) throws IPLocateException {
         if (ipAddress == null || ipAddress.trim().isEmpty()) {
             throw new IllegalArgumentException("IP address cannot be null or empty.");
         }
         // changed thi to caller handles the exception
         return performLookup(ipAddress);
     }
 
     /**
      * Looks up information for the client's current IP address (as seen by the API server).
      *
      * @return An {@link IPLocateResponse} containing the IP information.
      * @throws IPLocateException If an error occurs during the API call.
      */
     public IPLocateResponse lookupCurrentIp() throws IPLocateException {
         return performLookup(null); // Null or empty path segment for current IP
     }
 
     private IPLocateResponse performLookup(String ipAddressPathSegment) throws IPLocateException {
         HttpUrl.Builder urlBuilder = this.baseUrl.newBuilder();
         urlBuilder.addPathSegments("lookup/");
 
         if (ipAddressPathSegment != null && !ipAddressPathSegment.isEmpty()) {
             urlBuilder.addPathSegments(ipAddressPathSegment);
         }
 
         urlBuilder.addQueryParameter(QUERY_PARAM_API_KEY, apiKey);
         HttpUrl url = urlBuilder.build();
 
         Request request = new Request.Builder()
                 .url(url)
                 .get()
                 .header(HEADER_ACCEPT, CONTENT_TYPE_JSON)
                 .header(HEADER_USER_AGENT, this.userAgent)
                 .build();
 
         try (Response response = httpClient.newCall(request).execute()) {
             if (response.isSuccessful()) {
                 ResponseBody body = response.body();
                 if (body == null) {
                     throw new IPLocateServiceException("Received successful response with empty body.", response.code());
                 }
                 try {
                     return objectMapper.readValue(body.string(), IPLocateResponse.class);
                 } catch (IOException e) { 
                     throw new IPLocateServiceException("Failed to parse successful response: " + e.getMessage(), response.code(), e);
                 }
             } else {
                 handleErrorResponse(response);
                 // Should not be reached as handleErrorResponse is always thrown
                 throw new IPLocateServiceException("Unexpected state after error handling.", response.code());
             }
         } catch (IOException e) {
             throw new IPLocateServiceException("Network error or problem reaching IPLocate API: " + e.getMessage(), -1, e);
         }
     }
 
     private void handleErrorResponse(Response response) throws IPLocateException, IOException {
         int statusCode = response.code();
         String errorBodyString = "";
 
         try (ResponseBody errorBody = response.body()) {
             if (errorBody != null) {
                 errorBodyString = errorBody.string(); // Consume the body
             }
         } catch (IOException e) {
             // This might happen if reading the error body itself fails.
             // We still want to throw based on status code.
             errorBodyString = "Failed to read error response body. Cause: " + e.getMessage();
         }
 
         if (errorBodyString.isEmpty()) {
             errorBodyString = "No error body received from server. Status code: " + statusCode;
         }
 
         // Try to parse as JSON ErrorResponse for 4xx errors
         if (statusCode >= 400 && statusCode < 500) {
             try {
                 ErrorResponse errorResponse = objectMapper.readValue(errorBodyString, ErrorResponse.class);
                 String errorMessage = Objects.toString(errorResponse.getError(), "Unknown API error.");
 
                 switch (statusCode) {
                     case 400: // HttpURLConnection.HTTP_BAD_REQUEST
                         throw new IPLocateInvalidIPException(errorMessage);
                     case 403: // HttpURLConnection.HTTP_FORBIDDEN
                         throw new IPLocateApiKeyException(errorMessage);
                     case 404: // HttpURLConnection.HTTP_NOT_FOUND
                         throw new IPLocateNotFoundException(errorMessage);
                     case 429: // Too Many Requests
                         throw new IPLocateRateLimitException(errorMessage);
                     default:
                         throw new IPLocateApiException("API error: " + errorMessage, statusCode);
                 }
             } catch (IOException e) { // JSON parsing failed, use raw body
                 String baseMessage = "API request failed with status code " + statusCode +
                                      ". Unable to parse error response. Raw error: " + errorBodyString;
                 switch (statusCode) {
                     case 400: throw new IPLocateInvalidIPException(baseMessage);
                     case 403: throw new IPLocateApiKeyException(baseMessage);
                     case 404: throw new IPLocateNotFoundException(baseMessage);
                     case 429: throw new IPLocateRateLimitException(baseMessage);
                     default:  throw new IPLocateApiException(baseMessage, statusCode, e);
                 }
             }
         } else if (statusCode >= 500) { // 5xx errors
             throw new IPLocateServiceException("Server error: " + errorBodyString, statusCode);
         } else {
             // Other unexpected status codes
             throw new IPLocateApiException(
                     "Unexpected HTTP status code: " + statusCode + ". Response: " + errorBodyString, statusCode);
         }
     }
 }