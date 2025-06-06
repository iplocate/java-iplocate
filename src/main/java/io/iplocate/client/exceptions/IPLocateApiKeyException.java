/***
 * @author IPLocate.io.
 * @version 1.0.0
 */

package io.iplocate.client.exceptions;

public class IPLocateApiKeyException extends IPLocateApiException {
  public IPLocateApiKeyException(String message) {
    super(message, 403);
  }
}
