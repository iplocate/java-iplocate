package io.iplocate.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import io.iplocate.client.exceptions.IPLocateApiKeyException;
import io.iplocate.client.exceptions.IPLocateInvalidIPException;
import io.iplocate.client.exceptions.IPLocateNotFoundException;
import io.iplocate.client.exceptions.IPLocateRateLimitException;
import io.iplocate.client.exceptions.IPLocateServiceException;
import io.iplocate.client.model.IPLocateResponse;

public class IPLocateClientTest {

  @Rule public WireMockRule wireMockRule = new WireMockRule(0);

  private String getTestApiBaseUrl() {
    return "http://localhost:" + wireMockRule.port() + "/api";
  }

  @Test
  public void testSuccessfulLookup() {
    String testApiKey = "test-key";
    String testIp = "8.8.8.8";
    String mockJsonResponse =
        "{\n"
            + "  \"ip\": \"8.8.8.8\",\n"
            + "  \"country\": \"United States\",\n"
            + "  \"country_code\": \"US\",\n"
            + "  \"is_eu\": false,\n"
            + "  \"city\": \"Mountain View\",\n"
            + "  \"continent\": \"North America\",\n"
            + "  \"latitude\": 37.42240,\n"
            + "  \"longitude\": -122.08421,\n"
            + "  \"time_zone\": \"America/Los_Angeles\",\n"
            + "  \"postal_code\": \"94043\",\n"
            + "  \"subdivision\": \"California\",\n"
            + "  \"currency_code\": \"USD\",\n"
            + "  \"calling_code\": \"1\",\n"
            + "  \"network\": \"8.8.8.0/24\",\n"
            + "  \"asn\": {\n"
            + "    \"asn\": \"AS15169\",\n"
            + "    \"name\": \"Google LLC\",\n"
            + "    \"domain\": \"google.com\",\n"
            + "    \"type\": \"hosting\",\n"
            + "    \"country_code\": \"US\",\n"
            + "    \"route\": \"8.8.8.0/24\",\n"
            + "    \"rir\": \"ARIN\",\n"
            + "    \"netname\": \"GOOGLE\"\n"
            + "  },\n"
            + "  \"privacy\": {\n"
            + "    \"is_abuser\": false,\n"
            + "    \"is_anonymous\": false,\n"
            + "    \"is_bogon\": false,\n"
            + "    \"is_hosting\": true,\n"
            + "    \"is_icloud_relay\": false,\n"
            + "    \"is_proxy\": false,\n"
            + "    \"is_tor\": false,\n"
            + "    \"is_vpn\": false\n"
            + "  }\n"
            + "}";

    stubFor(
        get(urlEqualTo("/api/lookup/" + testIp + "?apikey=" + testApiKey))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(mockJsonResponse)));

    IPLocateClient client = new IPLocateClient(testApiKey, getTestApiBaseUrl());
    IPLocateResponse response = client.lookup(testIp);

    assertNotNull(response);
    assertEquals("8.8.8.8", response.getIp());
    assertEquals("United States", response.getCountry());
    assertEquals("US", response.getCountryCode());
    assertFalse(response.isEu());
    assertEquals("Mountain View", response.getCity());
    assertNotNull(response.getAsn());
    assertEquals("AS15169", response.getAsn().getAsn());
    assertTrue(response.getPrivacy().isHosting());
  }

  @Test
  public void testSuccessfulCurrentIpLookup() {
    String testApiKey = "current-ip-key";
    String mockJsonResponse =
        "{\n"
            + "  \"ip\": \"1.2.3.4\",\n"
            + "  \"country\": \"Somewhere\",\n"
            + "  \"country_code\": \"SW\"\n"
            + "}";

    stubFor(
        get(urlEqualTo("/api/lookup/?apikey=" + testApiKey))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(mockJsonResponse)));

    IPLocateClient client = new IPLocateClient(testApiKey, getTestApiBaseUrl());
    IPLocateResponse response = client.lookupCurrentIp();

    assertNotNull(response);
    assertEquals("1.2.3.4", response.getIp());
    assertEquals("Somewhere", response.getCountry());
  }

  @Test(expected = IPLocateApiKeyException.class)
  public void testForbiddenError() {
    String testApiKey = "invalid-key";
    String testIp = "1.1.1.1";
    String errorJson = "{\"error\": \"Unknown token\"}";

    stubFor(
        get(urlEqualTo("/api/lookup/" + testIp + "?apikey=" + testApiKey))
            .willReturn(
                aResponse()
                    .withStatus(403)
                    .withHeader("Content-Type", "application/json")
                    .withBody(errorJson)));

    IPLocateClient client = new IPLocateClient(testApiKey, getTestApiBaseUrl());
    client.lookup(testIp);
  }

  @Test(expected = IPLocateRateLimitException.class)
  public void testRateLimitError() {
    String testApiKey = "rate-limited-key";
    String testIp = "1.1.1.1";
    String errorJson = "{\"error\": \"Rate limit exceeded\"}";

    stubFor(
        get(urlEqualTo("/api/lookup/" + testIp + "?apikey=" + testApiKey))
            .willReturn(
                aResponse()
                    .withStatus(429)
                    .withHeader("Content-Type", "application/json")
                    .withBody(errorJson)));

    IPLocateClient client = new IPLocateClient(testApiKey, getTestApiBaseUrl());
    client.lookup(testIp);
  }

  @Test(expected = IPLocateInvalidIPException.class)
  public void testBadRequestError() {
    String testApiKey = "test-key";
    String testIp = "invalid-ip-format";
    String errorJson = "{\"error\": \"Invalid IP address\"}";

    stubFor(
        get(urlEqualTo("/api/lookup/" + testIp + "?apikey=" + testApiKey))
            .willReturn(
                aResponse()
                    .withStatus(400)
                    .withHeader("Content-Type", "application/json")
                    .withBody(errorJson)));

    IPLocateClient client = new IPLocateClient(testApiKey, getTestApiBaseUrl());
    client.lookup(testIp);
  }

  @Test(expected = IPLocateNotFoundException.class)
  public void testNotFoundError() {
    String testApiKey = "test-key";
    String testIp = "10.0.0.1"; // should return 404 or mor specific error
    String errorJson = "{\"error\": \"IP address not found or private\"}";

    stubFor(
        get(urlEqualTo("/api/lookup/" + testIp + "?apikey=" + testApiKey))
            .willReturn(
                aResponse()
                    .withStatus(404)
                    .withHeader("Content-Type", "application/json")
                    .withBody(errorJson)));

    IPLocateClient client = new IPLocateClient(testApiKey, getTestApiBaseUrl());
    client.lookup(testIp);
  }

  @Test
  public void testServerError() {
    String testApiKey = "any-key";
    String testIp = "1.2.3.4";
    String errorText = "Internal server error";

    stubFor(
        get(urlEqualTo("/api/lookup/" + testIp + "?apikey=" + testApiKey))
            .willReturn(
                aResponse()
                    .withStatus(500)
                    .withHeader("Content-Type", "text/plain")
                    .withBody(errorText)));

    IPLocateClient client = new IPLocateClient(testApiKey, getTestApiBaseUrl());
    try {
      client.lookup(testIp);
      fail("Expected IPLocateServiceException");
    } catch (IPLocateServiceException e) {
      assertEquals(500, e.getStatusCode());
      assertTrue(e.getMessage().contains(errorText));
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullApiKey() {
    new IPLocateClient(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyApiKey() {
    new IPLocateClient("  ");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullIpAddress() {
    IPLocateClient client = new IPLocateClient("test-key", getTestApiBaseUrl());
    client.lookup(null);
  }

  @Test
  public void testSetTimeouts() {
    try {
    new IPLocateClient("test-key", getTestApiBaseUrl(), -1, 2000, 1000, "");
      fail("Expected IllegalArgumentException for negative timeout");
    } catch (IllegalArgumentException e) {
      // Err
    }
    try {
      new IPLocateClient("test-key", getTestApiBaseUrl(), 1000, -1, 1000, "");
      fail("Expected IllegalArgumentException for negative timeout");
    } catch (IllegalArgumentException e) {
      // err
    }
  }
}
