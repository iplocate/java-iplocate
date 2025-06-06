/***
 * @author IPLocate.io.
 * @version 1.0.0
 */

package io.iplocate.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IPLocateResponse {
  private String ip;
  private String country;

  @JsonProperty("country_code")
  private String countryCode;

  @JsonProperty("is_eu")
  private Boolean eu; // Use Boolean object for potential null

  private String city;
  private String continent;
  private Double latitude; // Use Double object for potential null
  private Double longitude; // Use Double object for potential null

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

  // Getters and Setters
  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public Boolean isEu() {
    return eu;
  } // Jackson handles "is" prefix for boolean getters

  public void setEu(Boolean eu) {
    this.eu = eu;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getContinent() {
    return continent;
  }

  public void setContinent(String continent) {
    this.continent = continent;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public String getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getSubdivision() {
    return subdivision;
  }

  public void setSubdivision(String subdivision) {
    this.subdivision = subdivision;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public String getCallingCode() {
    return callingCode;
  }

  public void setCallingCode(String callingCode) {
    this.callingCode = callingCode;
  }

  public String getNetwork() {
    return network;
  }

  public void setNetwork(String network) {
    this.network = network;
  }

  public AsnDetails getAsn() {
    return asn;
  }

  public void setAsn(AsnDetails asn) {
    this.asn = asn;
  }

  public PrivacyDetails getPrivacy() {
    return privacy;
  }

  public void setPrivacy(PrivacyDetails privacy) {
    this.privacy = privacy;
  }

  public CompanyDetails getCompany() {
    return company;
  }

  public void setCompany(CompanyDetails company) {
    this.company = company;
  }

  public HostingDetails getHosting() {
    return hosting;
  }

  public void setHosting(HostingDetails hosting) {
    this.hosting = hosting;
  }

  public AbuseDetails getAbuse() {
    return abuse;
  }

  public void setAbuse(AbuseDetails abuse) {
    this.abuse = abuse;
  }

  @Override
  public String toString() {
    return "IPLocateResponse{"
        + "ip='"
        + ip
        + '\''
        + ", country='"
        + country
        + '\''
        + ", city='"
        + city
        + '\''
        + '}';
  }
}
