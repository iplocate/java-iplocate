/***
 * @author IPLocate.io.
 * @version 1.0.0
 */

package io.iplocate.client.exceptions;

public class IPLocateServiceException extends IPLocateApiException {
  public IPLocateServiceException(String message, int statusCode) {
    super(message, statusCode);
  }

  public IPLocateServiceException(String message, int statusCode, Throwable cause) {
    super(message, statusCode, cause);
  }
}
