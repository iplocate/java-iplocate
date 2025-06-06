# IPLocate Geolocation Client for Java

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

[![Maven Central](https://img.shields.io/maven-central/v/io.iplocate/java-iplocate.svg?label=Maven%20Central)](https://search.maven.org/artifact/io.iplocate/java-iplocate)

A Java client for the [IPLocate.io](https://iplocate.io) geolocation API. Look up detailed geolocation and threat intelligence data for any IP address:

- **IP geolocation**: IP to country, IP to city, IP to region/state, coordinates, timezone, postal code
- **ASN information**: Internet service provider, network details, routing information
- **Privacy & threat detection**: VPN, proxy, Tor, hosting provider detection
- **Company information**: Business details associated with IP addresses - company name, domain, type (ISP/hosting/education/government/business)
- **Abuse contact**: Network abuse reporting information
- **Hosting detection**: Cloud provider and hosting service detection using our proprietary hosting detection engine

See what information we can provide for [your IP address](https://iplocate.io/what-is-my-ip).

## Getting started

You can make 1,000 free requests per day with a [free account](https://iplocate.io/signup). For higher plans, check out [API pricing](https://www.iplocate.io/pricing).

### Installation

**Maven:**
Add this dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>io.iplocate</groupId>
    <artifactId>java-iplocate</artifactId>
    <version>1.0.0</version>
</dependency>
```

**Gradle:**
Add this to your `build.gradle` file:

```gradle
implementation 'io.iplocate:java-iplocate:1.0.0'
```

### Quick start

```java
package com.example; // Your package name

import io.iplocate.client.IPLocateClient;
import io.iplocate.client.exceptions.IPLocateException;
import io.iplocate.client.model.IPLocateResponse;
import io.iplocate.client.model.PrivacyDetails;

public class IPLocateExample {
    public static void main(String[] args) {
        // Create a new client with your API key
        // Get your free API key from https://iplocate.io/signup
        IPLocateClient client = new IPLocateClient("your-api-key");

        try {
            // Look up an IP address
            IPLocateResponse result = client.lookup("8.8.8.8");

            System.out.println("IP: " + result.getIp());
            if (result.getCountry() != null) {
                System.out.println("Country: " + result.getCountry());
            }
            if (result.getCity() != null) {
                System.out.println("City: " + result.getCity());
            }

            // Check privacy flags
            PrivacyDetails privacy = result.getPrivacy();
            if (privacy != null) {
                System.out.println("Is VPN: " + privacy.isVpn());
                System.out.println("Is Proxy: " + privacy.isProxy());
            }

        } catch (IPLocateException e) {
            System.err.println("IPLocate API Error: " + e.getMessage());
            // For more detailed error handling, catch specific exceptions
            // e.g., IPLocateApiKeyException, IPLocateInvalidIPException, etc.
            e.printStackTrace();
        }
    }
}
```

### Get the country for an IP address

```java
if (result.getCountry() != null && result.getCountryCode() != null) {
    System.out.printf("Country: %s (%s)\n", result.getCountry(), result.getCountryCode());
}
```

### Get the currency code for a country by IP address

```java
if (result.getCurrencyCode() != null) {
    System.out.printf("Currency: %s\n", result.getCurrencyCode());
}
```

### Get the calling code for a country by IP address

```java
if (result.getCallingCode() != null) {
    System.out.printf("Calling code: %s\n", result.getCallingCode());
}
```

## Authentication

Get your free API key from [IPLocate.io](https://iplocate.io/signup), and pass it to the `IPLocateClient` constructor:

```java
IPLocateClient client = new IPLocateClient("your-api-key");
```

## Examples

### IP address geolocation lookup

```java
IPLocateClient client = new IPLocateClient("your-api-key");
try {
    IPLocateResponse result = client.lookup("203.0.113.1");
    if (result.getCountry() != null && result.getCountryCode() != null) {
        System.out.printf("Country: %s (%s)\n", result.getCountry(), result.getCountryCode());
    }
    if (result.getLatitude() != null && result.getLongitude() != null) {
        System.out.printf("Coordinates: %.4f, %.4f\n", result.getLatitude(), result.getLongitude());
    }
} catch (IPLocateException e) {
    System.err.println("Error looking up IP: " + e.getMessage());
}
```

### Get your own IP address information

This will look up the IP address as seen by the IPLocate.io API server.

```java
IPLocateClient client = new IPLocateClient("your-api-key");
try {
    IPLocateResponse result = client.lookupCurrentIp();
    System.out.println("Your IP: " + result.getIp());
    // Access other fields as needed
} catch (IPLocateException e) {
    System.err.println("Error looking up current IP: " + e.getMessage());
}
```

### Check for VPN/Proxy Detection

```java
IPLocateClient client = new IPLocateClient("your-api-key");
try {
    IPLocateResponse result = client.lookup("192.0.2.1"); // Example IP
    PrivacyDetails privacy = result.getPrivacy();

    if (privacy != null) {
        if (privacy.isVpn()) {
            System.out.println("This IP is using a VPN");
        }
        if (privacy.isProxy()) {
            System.out.println("This IP is using a proxy");
        }
        if (privacy.isTor()) {
            System.out.println("This IP is using Tor");
        }
    }
} catch (IPLocateException e) {
    System.err.println("Error during privacy check: " + e.getMessage());
}
```

### AsnDetails and network information

```java
IPLocateClient client = new IPLocateClient("your-api-key");
try {
    IPLocateResponse result = client.lookup("8.8.8.8");
    io.iplocate.client.model.AsnDetails asnInfo = result.getAsn();

    if (asnInfo != null) {
        System.out.println("ASN: " + asnInfo.getAsn());
        System.out.println("ISP: " + asnInfo.getName());
        System.out.println("Network: " + asnInfo.getRoute());
    }
} catch (IPLocateException e) {
    System.err.println("Error fetching ASN info: " + e.getMessage());
}
```

## Response structure

The `io.iplocate.client.model.IPLocateResponse` class contains all available data. The fields are similar to the JSON structure returned by the API.

```java
public class IPLocateResponse {
    public class IPLocateResponse {
    private String ip;
    private String country;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("is_eu")
    private Boolean eu;

    private String city;
    private String continent;
    private Double latitude;
    private Double longitude;

    @JsonProperty("time_zone")
    private String timeZone;

    @JsonProperty("postal_code")
    private String postalCode;

    private String subdivision;

    @JsonProperty("currency_code")
    private String currencyCode;

    @JsonProperty("calling_code")
    private String callingCode;

    private String network;
    private AsnDetails asn;
    private PrivacyDetails privacy;
    private CompanyDetails company;
    private HostingDetails hosting;
    private AbuseDetails abuse;

    // Plus, getter methods for all fields, e.g., getIp(), getCountry(), etc.
}
```

Note: Fields representing objects (like `asn`, `privacy`) or optional scalar values (like `country`, `city`) may be `null` if data is not available for a given IP. Always check for `null` before accessing their properties or methods. Boolean fields like `isEu` will have default values (e.g., `false`) if not explicitly set.

## Error handling

The client throws specific exceptions that extend `IPLocateException` for different error scenarios.

Common API errors and their corresponding Java exceptions:

- `400 Bad Request` (Invalid IP address format): `IPLocateInvalidIPException`
- `403 Forbidden` (Invalid API key): `IPLocateApiKeyException`
- `404 Not Found` (IP address not found): `IPLocateNotFoundException`
- `429 Too Many Requests` (Rate limit exceeded): `IPLocateRateLimitException`
- `5xx Server Error`: `IPLocateServiceException`
- Other API errors: `IPLocateApiException`

All these exceptions extend `IPLocateException`.

## API reference

For complete API documentation, visit [iplocate.io/docs](https://iplocate.io/docs).

## License

This project is licensed under the MIT License - see the `LICENSE` file for details

## Testing

To run tests for this Java library:

**Maven:**

```bash
mvn test
```

**Gradle:**

```bash
gradle test
```

## About IPLocate.io

Since 2017, IPLocate has set out to provide the most reliable and accurate IP address data.

We process 50TB+ of data to produce our comprehensive IP geolocation, IP to company, proxy and VPN detection, hosting detection, ASN, and WHOIS data sets. Our API handles over 15 billion requests a month for thousands of businesses and developers.

- Email: [support@iplocate.io](mailto:support@iplocate.io)
- Website: [iplocate.io](https://iplocate.io)
- Documentation: [iplocate.io/docs](https://iplocate.io/docs)
- Sign up for a free API Key: [iplocate.io/signup](https://iplocate.io/signup)
