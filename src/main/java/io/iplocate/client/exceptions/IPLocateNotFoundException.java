/***
 * @author IPLocate.io.
 * @version 1.0.0
 */

package io.iplocate.client.exceptions;

public class IPLocateNotFoundException extends IPLocateApiException {
  public IPLocateNotFoundException(String message) {
    super(message, 404);
  }
}
