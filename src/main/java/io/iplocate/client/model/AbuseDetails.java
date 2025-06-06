/***
 * @author IPLocate.io.
 * @version 1.0.0
 */

package io.iplocate.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AbuseDetails {
  private String address;

  @JsonProperty("country_code")
  private String countryCode;

  private String email;
  private String name;
  private String network;
  private String phone;

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNetwork() {
    return network;
  }

  public void setNetwork(String network) {
    this.network = network;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public String toString() {
    return "AbuseDetails{" + "name='" + name + '\'' + ", email='" + email + '\'' + '}';
  }
}
