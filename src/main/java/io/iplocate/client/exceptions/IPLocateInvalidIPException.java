/***
 * @author IPLocate.io.
 * @version 1.0.0
 */

package io.iplocate.client.exceptions;

public class IPLocateInvalidIPException extends IPLocateApiException {
  public IPLocateInvalidIPException(String message) {
    super(message, 400);
  }
}
