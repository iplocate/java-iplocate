/***
 * @author IPLocate.io.
 * @version 1.0.0
 */

package io.iplocate.client.model;

public class HostingDetails {
  private String provider;
  private String domain;
  private String network;
  private String region;
  private String service;

  // Getters and Setters
  public String getProvider() {
    return provider;
  }

  public void setProvider(String provider) {
    this.provider = provider;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getNetwork() {
    return network;
  }

  public void setNetwork(String network) {
    this.network = network;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }

  @Override
  public String toString() {
    return "HostingDetails{"
        + "provider='"
        + provider
        + '\''
        + ", service='"
        + service
        + '\''
        + '}';
  }
}
