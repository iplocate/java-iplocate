/***
 * @author IPLocate.io.
 * @version 1.0.0
 */

package io.iplocate.client.exceptions;

public class IPLocateRateLimitException extends IPLocateApiException {
  public IPLocateRateLimitException(String message) {
    super(message, 429);
  }
}
