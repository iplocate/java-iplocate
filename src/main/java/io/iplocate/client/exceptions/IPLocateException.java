/***
 * @author IPLocate.io.
 * @version 1.0.0
 */

package io.iplocate.client.exceptions;

public class IPLocateException extends RuntimeException {
  public IPLocateException(String message) {
    super(message);
  }

  public IPLocateException(String message, Throwable cause) {
    super(message, cause);
  }
}
