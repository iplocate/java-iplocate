/***
 * @author IPLocate.io.
 * @version 1.0.0
 */

package io.iplocate.client.exceptions;

public class IPLocateApiException extends IPLocateException {
  private final int statusCode;

  public IPLocateApiException(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  public IPLocateApiException(String message, int statusCode, Throwable cause) {
    super(message, cause);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
