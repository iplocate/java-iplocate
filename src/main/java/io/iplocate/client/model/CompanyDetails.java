/***
 * @author IPLocate.io.
 * @version 1.0.0
 */

package io.iplocate.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyDetails {
  private String name;
  private String domain;

  @JsonProperty("country_code")
  private String countryCode;

  private String type;

  // Getters and Setters
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "CompanyDetails{" + "name='" + name + '\'' + ", domain='" + domain + '\'' + '}';
  }
}
