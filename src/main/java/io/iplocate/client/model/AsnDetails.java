/***
 * @author IPLocate.io.
 * @version 1.0.0
 */

package io.iplocate.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AsnDetails {
  private String asn;
  private String route;
  private String netname;
  private String name;

  @JsonProperty("country_code")
  private String countryCode;

  private String domain;
  private String type;
  private String rir;

  // Getters and Setters
  public String getAsn() {
    return asn;
  }

  public void setAsn(String asn) {
    this.asn = asn;
  }

  public String getRoute() {
    return route;
  }

  public void setRoute(String route) {
    this.route = route;
  }

  public String getNetname() {
    return netname;
  }

  public void setNetname(String netname) {
    this.netname = netname;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getRir() {
    return rir;
  }

  public void setRir(String rir) {
    this.rir = rir;
  }

  @Override
  public String toString() {
    return "AsnDetails{" + "asn='" + asn + '\'' + ", name='" + name + '\'' + '}';
  }
}
