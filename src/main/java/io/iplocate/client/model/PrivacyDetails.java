/***
 * @author IPLocate.io.
 * @version 1.0.0
 */

package io.iplocate.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PrivacyDetails {
  @JsonProperty("is_abuser")
  private boolean abuser;

  @JsonProperty("is_anonymous")
  private boolean anonymous;

  @JsonProperty("is_bogon")
  private boolean bogon;

  @JsonProperty("is_hosting")
  private boolean hosting;

  @JsonProperty("is_icloud_relay")
  private boolean icloudRelay;

  @JsonProperty("is_proxy")
  private boolean proxy;

  @JsonProperty("is_tor")
  private boolean tor;

  @JsonProperty("is_vpn")
  private boolean vpn;

  // Getters and Setters
  public boolean isAbuser() {
    return abuser;
  }

  public void setAbuser(boolean abuser) {
    this.abuser = abuser;
  }

  public boolean isAnonymous() {
    return anonymous;
  }

  public void setAnonymous(boolean anonymous) {
    this.anonymous = anonymous;
  }

  public boolean isBogon() {
    return bogon;
  }

  public void setBogon(boolean bogon) {
    this.bogon = bogon;
  }

  public boolean isHosting() {
    return hosting;
  }

  public void setHosting(boolean hosting) {
    this.hosting = hosting;
  }

  public boolean isIcloudRelay() {
    return icloudRelay;
  }

  public void setIcloudRelay(boolean icloudRelay) {
    this.icloudRelay = icloudRelay;
  }

  public boolean isProxy() {
    return proxy;
  }

  public void setProxy(boolean proxy) {
    this.proxy = proxy;
  }

  public boolean isTor() {
    return tor;
  }

  public void setTor(boolean tor) {
    this.tor = tor;
  }

  public boolean isVpn() {
    return vpn;
  }

  public void setVpn(boolean vpn) {
    this.vpn = vpn;
  }

  @Override
  public String toString() {
    return "PrivacyDetails{" + "vpn=" + vpn + ", proxy=" + proxy + ", tor=" + tor + '}';
  }
}
