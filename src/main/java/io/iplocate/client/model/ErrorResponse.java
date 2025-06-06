/***
 * @author IPLocate.io.
 * @version 1.0.0
 */

package io.iplocate.client.model;

public class ErrorResponse {
  private String error;

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  @Override
  public String toString() {
    return "ErrorResponse{" + "error='" + error + '\'' + '}';
  }
}
